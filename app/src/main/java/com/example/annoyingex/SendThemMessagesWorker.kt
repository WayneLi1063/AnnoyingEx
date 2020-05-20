package com.example.annoyingex

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

class SendThemMessagesWorker(context: Context, workParams: WorkerParameters) :
    Worker(context, workParams) {

    private val notificationManager =
        (applicationContext as AnnoyingExApplication).notificationManager

    override fun doWork(): Result {
        notificationManager.createAnnoyingNotifications()

        return Result.success()
    }

}