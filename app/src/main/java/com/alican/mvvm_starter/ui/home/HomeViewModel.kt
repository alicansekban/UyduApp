package com.alican.mvvm_starter.ui.home

import androidx.lifecycle.ViewModel
import com.alican.mvvm_starter.data.local.AppDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val db: AppDatabase

) : ViewModel() {
    fun searchWithQuery(searchQuery:String) =  db.satelliteDao().getWithQuery(searchQuery)
}
