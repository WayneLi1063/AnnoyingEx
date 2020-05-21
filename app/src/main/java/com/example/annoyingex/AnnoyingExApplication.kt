package com.example.annoyingex

import android.app.Application
import com.example.annoyingex.manager.AENotificationManager
import com.example.annoyingex.manager.AEWorkManager
import com.example.annoyingex.manager.ApiManager


class AnnoyingExApplication : Application() {

    lateinit var apiManager: ApiManager
    lateinit var notificationManager: AENotificationManager
    lateinit var workManager: AEWorkManager

    override fun onCreate() {
        super.onCreate()

        apiManager = ApiManager(this)
        workManager = AEWorkManager(this)
        notificationManager = AENotificationManager(this)
    }


}