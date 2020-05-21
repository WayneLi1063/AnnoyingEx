package com.example.annoyingex.manager

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.annoyingex.AnnoyingExApplication
import com.example.annoyingex.R
import com.example.annoyingex.activity.MainActivity
import kotlin.random.Random

class AENotificationManager(private val context: Context) {

    private val notificationManagerCompat = NotificationManagerCompat.from(context)
    private val application = (context as AnnoyingExApplication)
    private val apiManager = application.apiManager
    private val workManager = application.workManager
    lateinit var listOfMessages: List<String>

    companion object {
        const val ANNOYING_EX_CHANNEL_ID = "OOOOOOMMMMMMMMMMGGGGGGGGGG"
        const val ANNOYING_EX_KEY = "I AM INEVITABLE"
    }

    init {
        createAnnoyingEx()
        apiManager.getMessages({ messages ->
            listOfMessages = messages.messages
        }, {
            listOfMessages = listOf("unable to retrieve message")
        })
        workManager.fetchThemMessages()
    }

    fun createAnnoyingNotifications() {
        val chosenOne = listOfMessages[Random.nextInt(0, listOfMessages.size)]

        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra(ANNOYING_EX_KEY, chosenOne)
        }

        val pendingIntent =
            PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)


        val notification = NotificationCompat.Builder(context, ANNOYING_EX_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_announcement_black_24dp)
            .setContentTitle("The person you hate and love")
            .setContentText(chosenOne)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setCategory(NotificationCompat.CATEGORY_MESSAGE)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        notificationManagerCompat.notify(Random.nextInt(), notification)
    }

    private fun createAnnoyingEx() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = context.resources.getString(R.string.channel_name)
            val descriptionText = context.resources.getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(ANNOYING_EX_CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            notificationManagerCompat.createNotificationChannel(channel)
        }
    }

}