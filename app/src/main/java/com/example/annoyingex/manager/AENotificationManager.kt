package com.example.annoyingex.manager

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.annoyingex.AnnoyingExApplication
import com.example.annoyingex.R
import kotlin.random.Random

class AENotificationManager(private val context: Context) {

    private val notificationManagerCompat = NotificationManagerCompat.from(context)
    private val apiManager = (context as AnnoyingExApplication).apiManager
    private lateinit var listOfMessages: List<String>

    companion object {
        const val ANNOYING_EX_CHANNEL_ID = "OOOOOOMMMMMMMMMMGGGGGGGGGG"
    }

    init {
        createAnnoyingEx()

        // TODO: Store Messages in application
        apiManager.getMessages({ messages ->
            listOfMessages = messages.messages
            Toast.makeText(context, "Successfully fetched messages.", Toast.LENGTH_SHORT).show()
        }, {
            listOfMessages = listOf("unable to retrieve message")
            Toast.makeText(context, "Error occurred when fetching messages.", Toast.LENGTH_SHORT)
                .show()
        })
    }

    // TODO: When a user clicks on the notification, it should launches your MainActivity

    // TODO: when a user launches the MainActivity from the notification, it should display
    // TODO: the text that was in the message/notification somewhere in the activity.

    fun createAnnoyingNotifications() {
        val notification = NotificationCompat.Builder(context, ANNOYING_EX_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_announcement_black_24dp)
            .setContentTitle("The person you hate and love")
            .setContentText(listOfMessages[Random.nextInt(0, listOfMessages.size)])
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setCategory(NotificationCompat.CATEGORY_MESSAGE)
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