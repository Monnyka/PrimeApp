package com.nyka.primeapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import kotlinx.android.synthetic.main.custom_dialog_popup.*
import org.json.JSONObject
import java.util.prefs.BackingStoreException

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_main)

        val ivUser =findViewById<ImageView>(R.id.ivUser)
        ivUser.setOnClickListener(){
            val subTitle = "Are you sure you want to log out?"
            showDialog(subTitle,"Log Out",false)
        }
    }
    //function
    private var doubleBackToExitPressedOnce = false
    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            finishAffinity()
            return
        }
        this.doubleBackToExitPressedOnce = true
        Toast.makeText(applicationContext, "Tap back again to exit...", Toast.LENGTH_SHORT).show()
        Handler().postDelayed(Runnable { doubleBackToExitPressedOnce = false }, 2000)
    }

}
