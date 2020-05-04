package com.nyka.primeapp

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import org.json.JSONObject

@SuppressLint("Registered")

open class BaseActivity : AppCompatActivity() {


    val service = ServiceVolley()
    val apiController = APIController(service)

    val SHAREDPREFS = "sharedPrefs"
    val saveSessionId = ""
    var loadSessionId = ""

    fun noActionbar() {
        window.decorView.systemUiVisibility =
            android.view.View.SYSTEM_UI_FLAG_LAYOUT_STABLE or android.view.View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        window.statusBarColor = android.graphics.Color.TRANSPARENT
    }

    fun loadData() {
        val sharedPreferences = getSharedPreferences(SHAREDPREFS, Context.MODE_PRIVATE)
        loadSessionId = sharedPreferences.getString(saveSessionId, "");
    }

    fun saveData(save: String) {
        val sharedPreferences = getSharedPreferences(SHAREDPREFS, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString(saveSessionId, save)
        editor.apply()
    }


    val requestToken = "authentication/token/new?api_key=1469231605651a4f67245e5257160b5f"
    val requestLoginSession =
        "authentication/token/validate_with_login?api_key=1469231605651a4f67245e5257160b5f"
    val endpointVerifySession =
        "authentication/session/new?api_key=1469231605651a4f67245e5257160b5f"
    val requestDeleteSession = "authentication/session?api_key=1469231605651a4f67245e5257160b5f"
    val requestForgetPassword = "https://www.themoviedb.org/account/request-reset-password"

    fun logOut() {
        val jsonSessionID = JSONObject()
        jsonSessionID.put("session_id", loadSessionId)
        apiController.delete(requestDeleteSession, jsonSessionID) {
            saveData("")
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    fun showDialog(title: String, buttonText: String,isHideCancelButton: Boolean){
        val dialog = MaterialDialog(this)
            .noAutoDismiss()
            .customView(R.layout.custom_dialog_popup)
        val subTitle = dialog.findViewById<TextView>(R.id.subTitle)
        subTitle.text=title
        val buttonPositive = dialog.findViewById<TextView>(R.id.positive_button)
        buttonPositive.text=buttonText
        val buttonCancel = dialog.findViewById<TextView>(R.id.negative_button)
        val ivIcon = dialog.findViewById<ImageView>(R.id.alertIcon)
        if(buttonPositive.text=="Log Out"){
            ivIcon.setImageResource(R.drawable.ic_log_out)
        }
        buttonPositive.setOnClickListener(){
            if(buttonPositive.text=="OK") {
                dialog.dismiss()
            }
            if(buttonPositive.text=="Log Out"){
                ivIcon.setImageResource(R.drawable.ic_log_out)
                logOut()
            }
        }
        buttonCancel.setOnClickListener(){
            dialog.dismiss()
        }
        if(isHideCancelButton){

        }

        dialog.show(){
            cornerRadius(10f)
        }
    }
}