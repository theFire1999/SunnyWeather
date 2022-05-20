package com.example.sunnyweather.ui.weather

import android.os.AsyncTask
import android.widget.TextView
import android.widget.Toast

class ForecastAysnc : AsyncTask<Unit, Int, Boolean>() {
    override fun onPreExecute() {

    }

    override fun doInBackground(vararg params: Unit?) = try {

        true
    } catch (e: Exception) {
        false
    }

    override fun onProgressUpdate(vararg values: Int?) {
        // 在这里更新下载进度 progressDialog.setMessage("Downloaded ${values[0]}%")
    }
    override fun onPostExecute(result: Boolean) {

    }
}