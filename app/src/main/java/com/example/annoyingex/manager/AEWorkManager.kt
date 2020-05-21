package com.example.annoyingex.manager

import android.content.Context
import androidx.work.*
import com.example.annoyingex.worker.FetchThemMessagesWorker
import com.example.annoyingex.worker.SendThemMessagesWorker
import java.util.concurrent.TimeUnit

class AEWorkManager(context: Context) {

    private var workManager = WorkManager.getInstance(context)

    companion object {
        const val ANNOYING_EX_REQ_TAG = "WHY_ARE_YOU_IGNORING_ME?!!!!"
        const val FETCH_TAG = "OH BOI HERE WE GO AGAIN"
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
            // Can alternatively use enqueueUniquePeriodicWork(TAG, ExistingPeriodicWorkPolicy, workRequest)
            // and ExistingPeriodicWorkPolicy.KEEP to remove the need to call isExSendingThemMessages()
        }
    }

    fun fetchThemMessages() {
//      if (isFetchingTaskScheduled()) {
//          stopFetching()
//      }

        val constraintsFetch = Constraints.Builder()
            .setRequiresBatteryNotLow(true)
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val workRequestFetch =
            PeriodicWorkRequestBuilder<FetchThemMessagesWorker>(2, TimeUnit.DAYS)
                .setInitialDelay(2, TimeUnit.DAYS)
                .setConstraints(constraintsFetch)
                .addTag(FETCH_TAG)
                .build()

//      workManager.enqueue(workRequestFetch)

//      Using alternative method
        workManager.enqueueUniquePeriodicWork(
            "Fetch",
            ExistingPeriodicWorkPolicy.REPLACE,
            workRequestFetch
        )

    }

    private fun isExSendingThemMessages(): Boolean {
        return when (workManager.getWorkInfosByTag(ANNOYING_EX_REQ_TAG).get().firstOrNull()?.state) {
            WorkInfo.State.RUNNING,
            WorkInfo.State.ENQUEUED -> true
            else -> false
        }
    }

//  private fun isFetchingTaskScheduled(): Boolean {
//      return when (workManager.getWorkInfosByTag(FETCH_TAG).get().firstOrNull()?.state) {
//          WorkInfo.State.RUNNING,
//          WorkInfo.State.ENQUEUED -> true
//          else -> false
//      }
//  }

    fun stopSending() {
        workManager.cancelAllWorkByTag(ANNOYING_EX_REQ_TAG)
    }

//  private fun stopFetching() {
//      workManager.cancelAllWorkByTag(FETCH_TAG)
//  }
}