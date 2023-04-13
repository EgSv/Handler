package ru.startandroid.develop.handler

import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView


class MainActivity : Activity() {
    val LOG_TAG = "myLogs"
    var h: Handler? = null
    var tvInfo: TextView? = null
    var btnStart: Button? = null

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tvInfo = findViewById<View>(R.id.tvInfo) as TextView
        btnStart = findViewById<View>(R.id.btnStart) as Button
        h = object : Handler() {
            override fun handleMessage(msg: Message) {
                // обновляем TextView
                tvInfo!!.text = "Закачано файлов: " + msg.what
                if (msg.what == 10) btnStart!!.isEnabled = true
            }
        }
    }

    fun onClick(v: View) {
        when (v.id) {
            R.id.btnStart -> {
                btnStart!!.isEnabled = false
                val t = Thread {
                    var i = 1
                    while (i <= 10) {
                        downloadFile()
                        h!!.sendEmptyMessage(i)
                        Log.d(LOG_TAG, "i = $i")
                        i++
                    }
                }
                t.start()
            }
            R.id.btnTest -> Log.d(LOG_TAG, "test")
            else -> {}
        }
    }

    fun downloadFile() {

        try {
            Thread.sleep(1000)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }
}