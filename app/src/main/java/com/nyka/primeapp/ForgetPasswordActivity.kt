package com.nyka.primeapp

import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_forget_password.*
import org.json.JSONObject


class ForgetPasswordActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forget_password)
        noActionbar()

        val btnForgetPassword= findViewById<Button>(R.id.btnForgetPassword)
        btnForgetPassword.setOnClickListener(){
            forgetPassword()
        }

    }
    private fun forgetPassword(){
        val uName = edFUsername.editText?.text.toString()
        val jsonUsername = JSONObject();
        jsonUsername.put("username",uName)
        //val requestForgetPassword="https://www.themoviedb.org/account/request-reset-password?"

//        apiController.httppost(requestForgetPassword,jsonUsername){
//            Toast.makeText(applicationContext,"Please check your email "+uName,Toast.LENGTH_LONG).show()
//        }

        val queue: RequestQueue = Volley.newRequestQueue(this)
        val url = "https://www.themoviedb.org/account/request-reset-password"
        val postRequest: StringRequest =
            object : StringRequest(
                Method.POST, url,
                Response.Listener { response -> // response
                    Log.d("Response", response)
                },
                Response.ErrorListener { // error
                    Log.d("Error.Response", "Error")
                }
            ) {
                override fun getParams(): Map<String, String> {
                    val params: MutableMap<String, String> =
                        HashMap()
                    params["username"] = "nyka"
                    return params
                }
            }
        queue.add(postRequest)

    }
}
