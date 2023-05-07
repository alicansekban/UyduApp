package com.alican.mvvm_starter.ui.detail

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alican.mvvm_starter.data.local.AppDatabase
import com.alican.mvvm_starter.data.local.model.PositionsItem
import com.alican.mvvm_starter.data.local.model.SatelliteDetailEntity
import com.alican.mvvm_starter.data.local.model.SatelliteModel
import com.alican.mvvm_starter.data.local.model.SatellitePositionModel
import com.alican.mvvm_starter.util.utils.loadJSONFromAssets
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val db: AppDatabase
) : ViewModel() {

    init {
        getData()
    }

    private fun getData() {
        viewModelScope.launch {
            val json = context.loadJSONFromAssets("positions.json")
            val data =
                Gson().fromJson(json, SatellitePositionModel::class.java)
            data.list?.filterNotNull()?.let {
                db.satellitePositionsDao().insertAll(it)
            }
        }

        viewModelScope.launch(Dispatchers.IO) {
            db.satelliteDetailDao().getSatelliteCount().takeIf { it == 0 }?.let {
                val satelliteJsonArray =
                    context.loadJSONFromAssets("satellite_detail.json")

                val gson = Gson()
                val type = object : TypeToken<List<SatelliteDetailEntity>>() {}.type
                val data = gson.fromJson<List<SatelliteDetailEntity>>(
                    satelliteJsonArray,
                    type
                )

                data?.let {
                    db.satelliteDetailDao().insertData(it)
                }
            }
        }
    }

    var collect = true

    fun getPositions(id: Int): Flow<PositionsItem> {
        return db.satellitePositionsDao().getPositionsById(id)
            .transform {
                val positions = it?.positions

                while (collect) {
                    positions?.forEach {
                        emit(it)
                        delay(3000)
                    }
                }
            }
    }

    fun getSatelliteDetail(id: Int) = db.satelliteDetailDao().getDetailById(id)

}