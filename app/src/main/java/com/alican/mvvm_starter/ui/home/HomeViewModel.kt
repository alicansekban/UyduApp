package com.alican.mvvm_starter.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.murgupluoglu.request.RESPONSE
import com.murgupluoglu.request.request
import com.alican.mvvm_starter.data.remote.webservice.WebService
import com.alican.mvvm_starter.util.SingleLiveEvent
import com.alican.mvvm_starter.base.BaseResponse
import com.alican.mvvm_starter.data.local.AppDatabase
import com.alican.mvvm_starter.data.local.model.SatelliteDetailEntity
import com.alican.mvvm_starter.data.local.model.SatelliteModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.Flow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val webService: WebService,
    private val db: AppDatabase

) : ViewModel() {
    val dataResponse: SingleLiveEvent<RESPONSE<BaseResponse<Any>>> =
        SingleLiveEvent()

    val finish = MutableLiveData(false)

    fun getData() {
        dataResponse.request({ webService.getData() })
    }
    fun searchWithQuery(searchQuery:String) =  db.satelliteDao().getWithQuery(searchQuery)

    fun insertDataToDataBase(list: List<SatelliteModel>) {
        viewModelScope.launch(Dispatchers.IO) {
            db.satelliteDao().insertSatelliteData(list)
            finish.postValue(true)
        }
    }
    fun insertSatelliteDetailToDatabase(list:List<SatelliteDetailEntity>){
        viewModelScope.launch(Dispatchers.IO) {
            db.satelliteDetailDao().insertData(list)
        }
    }
    fun getAllSatellite() = db.satelliteDao().getAllSatellite()
    fun getSatelliteDetail(id:Int) = db.satelliteDetailDao().getDetailById(id)
}
