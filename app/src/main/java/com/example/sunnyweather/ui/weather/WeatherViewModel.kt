package com.example.sunnyweather.ui.weather

import androidx.lifecycle.*
import com.example.sunnyweather.logic.Repository
import com.example.sunnyweather.logic.model.Location

class WeatherViewModel : ViewModel() {

    private val locationLiveData = MutableLiveData<Location>()

    var locationLng = ""

    var locationLat = ""

    var placeName = ""

    var foreStep= ""

    val weatherLiveData = Transformations.switchMap(locationLiveData) { location ->
        Repository.refreshWeather(location.lng, location.lat, location.step)
    }

    fun refreshWeather(lng: String, lat: String, step: String) {
        locationLiveData.value = Location(lng, lat, step)
    }

}