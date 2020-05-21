package com.example.annoyingex.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.annoyingex.AnnoyingExApplication
import com.example.annoyingex.R
import com.example.annoyingex.manager.AENotificationManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val application = (application as AnnoyingExApplication)
        val workManager = application.workManager

        tvPrevMessageContent.text = intent.getStringExtra(AENotificationManager.ANNOYING_EX_KEY)

        btnInit.setOnClickListener {
            workManager.keepsOnSendingThemMessages()
        }

        btnStop.setOnClickListener {
            workManager.stopSending()
        }
    }
}
