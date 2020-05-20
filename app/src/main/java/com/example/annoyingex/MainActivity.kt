package com.example.annoyingex

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val application = (application as AnnoyingExApplication)
        val workManager = application.workManager

        btnInit.setOnClickListener {
            workManager.keepsOnSendingThemMessages()
        }

        btnStop.setOnClickListener {
            workManager.stopSending()
        }
    }
}
