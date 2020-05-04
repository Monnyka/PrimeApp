package com.nyka.primeapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONObject


class LoginActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        noActionbar()
        loadData()

        if (loadSessionId != "") {
            val intent = Intent(this@LoginActivity, MainActivity::class.java)
            startActivity(intent)
        }

        val btnLogin = findViewById<Button>(R.id.btnLogin)
        btnLogin?.setOnClickListener() {
            apiController.get(requestToken) { response ->
                val token = response?.getString("request_token");
                val userName = edUsername.editText?.text.toString()
                val password = edPassword.editText?.text.toString()
                val jsonLogin = JSONObject()
                jsonLogin.put("username", userName)
                jsonLogin.put("password", password)
                jsonLogin.put("request_token", token)
                apiController.post(requestLoginSession, jsonLogin) { rLoginResponse ->
                    val requestToken = rLoginResponse?.optString("request_token")
                    if (requestToken.isNullOrEmpty()) {
                        val statusMessage = "" + rLoginResponse?.optString("status_message")
                        showDialog(statusMessage,"OK",true)
                    } else {
                        val jsonRequestToken = JSONObject()
                        jsonRequestToken.put("request_token", requestToken)
                        apiController.post(
                            endpointVerifySession,
                            jsonRequestToken
                        ) { rTokenResponse ->
                            val sessionIdText: String =
                                rTokenResponse?.getString("session_id") as String
                            saveData(sessionIdText);
                            val intent = Intent(this@LoginActivity, MainActivity::class.java)
                            startActivity(intent)
                        }
                    }
                }
            }

        }

        val lbForgetPassword = findViewById<TextView>(R.id.txtForgetPassword)
        lbForgetPassword.setOnClickListener() {
            val intent = Intent(this@LoginActivity, ForgetPasswordActivity::class.java)
            startActivity(intent)
        }

        val lbRegister = findViewById<TextView>(R.id.txtRegister)
        lbRegister.setOnClickListener() {
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }

}
