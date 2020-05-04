package com.nyka.primeapp

import com.android.volley.VolleyError
import org.json.JSONObject

class APIController constructor(serviceInjection: IService): IService {
    private val service: IService = serviceInjection

    override fun post(path: String, params: JSONObject, completionHandler: (response: JSONObject?) -> Unit) {
        service.post(path, params, completionHandler)
    }

    override fun get(path: String,completionHandler: (response: JSONObject?) -> Unit){
        service.get(path,completionHandler)
    }

    override fun delete(path: String,params: JSONObject,completionHandler: (response: JSONObject?) -> Unit){
        service.delete(path,params,completionHandler)
    }
}