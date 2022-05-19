package com.example.sunnyweather.ui.place

import androidx.lifecycle.*
import com.example.sunnyweather.logic.Repository
import com.example.sunnyweather.logic.model.Place


class PlaceViewModel : ViewModel() {
    val placeList = ArrayList<Place>()

    // liveData
    private val searchLiveData = MutableLiveData<String>()

    val placeLiveData = Transformations.switchMap(searchLiveData) { query ->
        Repository.searchPlaces(query)
        //za
        //UI   username    nickname
    }

    fun searchPlaces(query: String) {
        searchLiveData.value = query
    }

    fun savePlace(place: Place) = Repository.savePlace(place)
    fun getSavedPlace() = Repository.getSavedPlace()
    fun isPlaceSaved() = Repository.isPlaceSaved()

}