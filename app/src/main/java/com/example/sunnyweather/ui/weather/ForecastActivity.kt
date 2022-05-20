package com.example.sunnyweather.ui.weather

import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.sunnyweather.MainActivity
import com.example.sunnyweather.R
import com.example.sunnyweather.logic.model.Weather
import com.example.sunnyweather.logic.model.getSky
import java.text.SimpleDateFormat
import java.util.*


class ForecastActivity : AppCompatActivity() {
    val viewModel by lazy { ViewModelProviders.of(this).get(WeatherViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forecast)

        val MoreBtn : Button = findViewById(R.id.more_btn)
        MoreBtn.setVisibility(View.GONE)

        // 从Intent中取出经 纬度坐标和地区名称，并赋值到WeatherViewModel的相应变量中
        if (viewModel.locationLng.isEmpty()) {
            viewModel.locationLng = intent.getStringExtra("location_lng") ?: ""
        }
        if (viewModel.locationLat.isEmpty()) {
            viewModel.locationLat = intent.getStringExtra("location_lat") ?: ""
        }
        if (viewModel.placeName.isEmpty()) {
            viewModel.placeName = intent.getStringExtra("place_name") ?: ""
        }


        viewModel.foreStep="15"
        viewModel.refreshWeather(viewModel.locationLng, viewModel.locationLat,viewModel.foreStep)

        // 调整天数显示的逻辑
        val foreText : TextView = findViewById(R.id.forecastText)
        val Btn5 : Button = findViewById(R.id.btn5)
        var step = ""
        Btn5.setOnClickListener {
            viewModel.foreStep="15"
            viewModel.refreshWeather(viewModel.locationLng, viewModel.locationLat,viewModel.foreStep)
        }

        val Btn10 : Button = findViewById(R.id.btn10)
        Btn10.setOnClickListener {
            viewModel.foreStep="30"
            viewModel.refreshWeather(viewModel.locationLng, viewModel.locationLat,viewModel.foreStep)
        }

        val Btn15 : Button = findViewById(R.id.btn15)
        Btn15.setOnClickListener {
            viewModel.foreStep="60"
            viewModel.refreshWeather(viewModel.locationLng, viewModel.locationLat,viewModel.foreStep)
        }

        // 对weatherLiveData对象进行观察，当获取到服务器返回的天气数据时，就调用 showWeatherInfo()方法进行解析与展示
        viewModel.weatherLiveData.observe(this, Observer { result ->
            val weather = result.getOrNull()
            if (weather != null) {
                foreText.text = viewModel.foreStep + "天预报"

                val forecastLayout : LinearLayout = findViewById(R.id.forecastLayout)
                forecastLayout.removeAllViews()
                val daily = weather.daily
                val days = daily.skycon.size
                Log.d("days sum",days.toString())
                for (i in 0 until days) {
                    val skycon = daily.skycon[i]
                    val temperature = daily.temperature[i]
                    val sky = getSky(skycon.value)

                    val view = LayoutInflater.from(this)
                        .inflate(R.layout.forecast_item, forecastLayout, false)
                    val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                    view.findViewById<TextView>(R.id.dateInfo).text =
                        simpleDateFormat.format(skycon.date)
                    view.findViewById<ImageView>(R.id.skyIcon).setImageResource(sky.icon)
                    view.findViewById<TextView>(R.id.skyInfo).text = sky.info
                    val tempText = "${temperature.min.toInt()} ~ ${temperature.max.toInt()} ℃"
                    view.findViewById<TextView>(R.id.temperatureInfo).text = tempText
                    // 动态添加到父布局中
                    forecastLayout.addView(view)
                }
            } else {
                Toast.makeText(this, "无法成功获取天气信息", Toast.LENGTH_SHORT).show()
                result.exceptionOrNull()?.printStackTrace()
            }

        })


        }

    }

