package com.example.sunnyweather.logic.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// 单例类：构建出的Retrofit对象是全局通用的，只需要在 调用create()方法时针对不同的Service接口传入相应的Class类型即可
object ServiceCreator {

    private const val BASE_URL = "https://api.caiyunapp.com"

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

//    val appService = retrofit.create(MyGsonService::class.java)
    fun <T> create(serviceClass: Class<T>): T = retrofit.create(serviceClass)

    inline fun <reified T> create(): T = create(T::class.java)

}
