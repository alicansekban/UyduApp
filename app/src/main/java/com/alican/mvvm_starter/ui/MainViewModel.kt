package com.alican.mvvm_starter.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alican.mvvm_starter.data.local.AppDatabase
import com.alican.mvvm_starter.data.local.model.SatelliteModel
import com.alican.mvvm_starter.data.model.SatelliteListItem
import com.alican.mvvm_starter.util.Constant
import com.alican.mvvm_starter.util.FilesName
import com.alican.mvvm_starter.util.utils.loadJSONFromAssets
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val db: AppDatabase
) : ViewModel() {

    fun getData() {
        viewModelScope.launch(Dispatchers.IO) {
            db.satelliteDao().getSatelliteCount().takeIf { it == 0 }?.let {
                val satelliteJsonArray =
                    context.loadJSONFromAssets(FilesName.SATELLITE_LIST)

                val gson = Gson()
                val type = object : TypeToken<List<SatelliteModel>>() {}.type
                val data = gson.fromJson<List<SatelliteModel>>(
                    satelliteJsonArray,
                    type
                )

                data?.let {
                    db.satelliteDao().insertSatelliteData(it)
                }
            }
        }
    }
}