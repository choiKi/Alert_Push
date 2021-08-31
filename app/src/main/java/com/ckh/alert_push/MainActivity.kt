package com.ckh.alert_push

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.google.firebase.messaging.FirebaseMessaging

class MainActivity : AppCompatActivity() {

    private val resultText:TextView by lazy { findViewById(R.id.resultText) }
    private val fireBaseToken:TextView by lazy { findViewById(R.id.fireBaseToken) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initFirebase()
    }
    private fun initFirebase() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if(task.isSuccessful) {
                fireBaseToken.text = task.result
            }
        }
    }
}