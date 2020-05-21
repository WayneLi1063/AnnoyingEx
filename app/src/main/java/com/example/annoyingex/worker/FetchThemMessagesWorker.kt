package com.example.annoyingex.worker

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.annoyingex.AnnoyingExApplication

class FetchThemMessagesWorker(context: Context, workParams: WorkerParameters) :
    Worker(context, workParams) {

    private val application = (context as AnnoyingExApplication)
    private val apiManager = application.apiManager
    private val notificationManager = application.notificationManager

    override fun doWork(): Result {
        apiManager.getMessages({ messages ->
            notificationManager.listOfMessages = messages.messages
        }, {
            notificationManager.listOfMessages = listOf("unable to retrieve message")
        })

        return Result.success()
    }
}
