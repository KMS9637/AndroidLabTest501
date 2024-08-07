package com.busanit501.androidlabtest501.ch10_dialog_permission

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.RemoteInput
import com.busanit501.androidlabtest501.databinding.ActivityCh10MainBinding

// 4대 컴포넌트, 브로드캐스트 리시버,
// 뉴 -> Other -> Broadcast Reciever 선택. 만들어야,
// 매니패스트에 자동 등록.
class ReplyReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        // This method is called when the BroadcastReceiver is receiving an Intent broadcast.
        // 알림 창에서, 원격지 채팅 메세지를 받아서 임시로 저장하는 상수
        val replyText = RemoteInput.getResultsFromIntent(intent)?.getCharSequence("key_text_reply")

        val manager = context.getSystemService(AppCompatActivity.NOTIFICATION_SERVICE) as NotificationManager
        val builder : NotificationCompat.Builder


        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channelId = "one-channel"
            val channelName = "My Channel One"
            val channel = NotificationChannel(
                channelId,channelName,NotificationManager.IMPORTANCE_HIGH
            )
            manager.createNotificationChannel(channel)

            builder = NotificationCompat.Builder(context, channelId)
        } else {
            builder = NotificationCompat.Builder(context)
        }
            builder.setSmallIcon(android.R.drawable.ic_notification_overlay)
            builder.setWhen(System.currentTimeMillis())
            builder.setContentTitle("Content Title")
            builder.setContentText("Content Massage : ${replyText}")


            manager.notify(11, builder.build())
        }


    }
