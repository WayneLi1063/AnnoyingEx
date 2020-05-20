package com.example.annoyingex.manager

import android.content.Context
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.annoyingex.Messages
import com.google.gson.Gson

class ApiManager(context: Context) {

    private val queue: RequestQueue = Volley.newRequestQueue(context)

    fun getMessages(onUserInfoReady: (Messages) -> Unit, onError: (() -> Unit)? = null) {
        val exMessageURL =
            "https://raw.githubusercontent.com/echeeUW/codesnippets/master/ex_messages.json"

        val request = StringRequest(
            Request.Method.GET, exMessageURL,
            { response ->
                // success

                val gson = Gson()
                val messages = gson.fromJson(response, Messages::class.java)

                onUserInfoReady(messages)

            }, {
                // error
                onError?.invoke()
            }
        )
        queue.add(request)
    }

}