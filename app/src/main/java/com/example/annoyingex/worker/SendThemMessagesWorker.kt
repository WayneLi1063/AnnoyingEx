package com.example.annoyingex.worker

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.annoyingex.AnnoyingExApplication

class SendThemMessagesWorker(context: Context, workParams: WorkerParameters) :
    Worker(context, workParams) {

    private val notificationManager = (context as AnnoyingExApplication).notificationManager

    override fun doWork(): Result {
        notificationManager.createAnnoyingNotifications()

        return Result.success()
    }

}