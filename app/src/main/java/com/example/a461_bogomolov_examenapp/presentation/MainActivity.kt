package com.example.a461_bogomolov_examenapp.presentation

import android.content.ContentProviderOperation.newCall
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.a461_bogomolov_examenapp.R
import com.example.a461_bogomolov_examenapp.databinding.ActivityMainBinding
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var okclient = OkHttpClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.buttonNext.setOnClickListener {
            semdRequest()
        }
    }

    private fun semdRequest() {
        val request = Request.Builder().url(URL).build()
        okclient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
            }

            override fun onResponse(call: Call, response: Response) {
                val json = response.body()?.string()
//                Log.d("Response", JSONObject(json).getJSONArray("products")
//                    .getJSONObject(0).get("description").toString())
                val txt = (json?.let { it1 ->
                    JSONObject(it1).getJSONArray("products").getJSONObject(0).get("description")
                }).toString()

                runOnUiThread {
                    binding.tvResponse.text = txt
                }
            }
        })
    }

    companion object {

        private const val URL = "https://dummyjson.com/products"
    }
}