package com.nyka.primeapp

import com.android.volley.VolleyError
import org.json.JSONObject

interface IService {
        fun post(path: String, params: JSONObject, completionHandler: (response: JSONObject?) -> Unit)
        fun get(path: String, completionHandler: (response: JSONObject?) -> Unit)
        fun delete(path: String, params: JSONObject, completionHandler: (response: JSONObject?) -> Unit)
}