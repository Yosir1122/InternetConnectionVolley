package com.example.internetconnectionvolley

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.internetconnectionvolley.databinding.ActivityMainBinding
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    lateinit var networkHelper: NetworkHelper
    lateinit var requestQueue: RequestQueue
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        networkHelper = NetworkHelper(this)
        if (networkHelper.isNetworkConnected()) {
            binding.tv.text = "Internet connected"
        } else {
            binding.tv.text = "Internet not connected"
        }
        requestQueue = Volley.newRequestQueue(this)
        getObjectClass()
    }

    fun getObjectClass() {
        val jsonObject = JsonObjectRequest(Request.Method.GET, "http://ip.jsontest.com", null,
            object : Response.Listener<JSONObject> {
                override fun onResponse(response: JSONObject?) {
                    binding.tvInfo.text = response.toString()
                }
            },
            object : Response.ErrorListener {
                override fun onErrorResponse(error: VolleyError?) {
                    Toast.makeText(this@MainActivity, "Error", Toast.LENGTH_SHORT).show()
                }
            }
        )
        requestQueue.add(jsonObject)
        /*Be sure to write in AndroidManifest and add to application
                android:usesCleartextTraffic="true"*/
    }
}