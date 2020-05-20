package com.example.annoyingex.manager

import android.content.Context
import androidx.work.Constraints
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.example.annoyingex.SendThemMessagesWorker
import java.util.concurrent.TimeUnit

class AEWorkManager(context: Context) {

    private var workManager = WorkManager.getInstance(context)

    companion object {
        const val ANNOYING_EX_REQ_TAG = "WHY_ARE_YOU_IGNORING_ME?!!!!"
    }

    fun keepsOnSendingThemMessages() {
        if (!isExSendingThemMessages()) {
            val constraints = Constraints.Builder()
                .setRequiresCharging(true)
                .build()

            val workRequest =
                PeriodicWorkRequestBuilder<SendThemMessagesWorker>(20, TimeUnit.MINUTES)
                    .setInitialDelay(5, TimeUnit.SECONDS)
                    .setConstraints(constraints)
                    .addTag(ANNOYING_EX_REQ_TAG)
                    .build()

            workManager.enqueue(workRequest)
        }
    }

    // TODO: Create another separate worker that runs every 2 days that fetches JSON only when the device’s
    // TODO: battery is not too low and is connected to a network.

    private fun isExSendingThemMessages(): Boolean {
        return when (workManager.getWorkInfosByTag(ANNOYING_EX_REQ_TAG).get().firstOrNull()?.state) {
            WorkInfo.State.RUNNING,
            WorkInfo.State.ENQUEUED -> true
            else -> false
        }
    }

    fun stopSending() {
        workManager.cancelAllWorkByTag(ANNOYING_EX_REQ_TAG)
    }
}