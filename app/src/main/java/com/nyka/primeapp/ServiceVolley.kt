package com.nyka.primeapp

import android.util.Log
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyLog
import com.android.volley.toolbox.JsonObjectRequest
import org.json.JSONObject
import java.nio.charset.StandardCharsets

class ServiceVolley : IService {
    val TAG = ServiceVolley::class.java.simpleName
    val basePath = "https://api.themoviedb.org/3/"

    override fun post(path: String, params: JSONObject, completionHandler: (response: JSONObject?) -> Unit
    ) {
        val jsonObjReq = object : JsonObjectRequest(Request.Method.POST, basePath + path, params,
            Response.Listener<JSONObject> { response ->
                Log.d(TAG, "/post request OK! Response: $response")
                completionHandler(response)
            },
            Response.ErrorListener{ error ->
                Log.d(TAG, "/post request fail! Error: ${error.message}")
                val responseBody = String(error.networkResponse.data, StandardCharsets.UTF_8)
                val data = JSONObject(responseBody)
                val message = data.optString("status_message")
                val json=JSONObject()
                json.put("status_message",message)
                completionHandler(json)
            }) {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val headers = HashMap<String, String>()
                headers["Content-Type"] = "application/json"
                return headers
            }
        }
        BackendVolley.instance?.addToRequestQueue(jsonObjReq, TAG)
    }

    override fun get(path: String, completionHandler: (response: JSONObject?) -> Unit) {
        val jsonObjectReq = object : JsonObjectRequest(Request.Method.GET, basePath + path, null,
            Response.Listener<JSONObject> { response ->
                Log.d(TAG, "/get request OK! Response: $response")
                completionHandler(response)
            },
            Response.ErrorListener { error ->
                completionHandler(null)
                VolleyLog.e(TAG, "/get request fail! Error: ${error.message}")
            }){
        }
        BackendVolley.instance?.addToRequestQueue(jsonObjectReq, TAG)
    }

    override fun delete(path: String, params: JSONObject,completionHandler: (response: JSONObject?) -> Unit){
        val jsonObjectReq = object : JsonObjectRequest(Request.Method.DELETE,basePath+path,params,
            Response.Listener<JSONObject>{response ->
                Log.d(TAG, "/Delete request OK! Response: $response")
                completionHandler(response)

        },Response.ErrorListener { error ->
                completionHandler(null)
                VolleyLog.e(TAG, "/post request fail! Error: ${error.message}")
            }){

        }
        BackendVolley.instance?.addToRequestQueue(jsonObjectReq,TAG)
    }


}

