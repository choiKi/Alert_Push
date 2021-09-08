package com.ckh.alert_push


import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import java.lang.Double.valueOf


class MyFireBaseMessagingService:FirebaseMessagingService() {

    override fun onNewToken(p0: String) {
        super.onNewToken(p0)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        createNotificationChannel()
        val title = remoteMessage.data["title"]
        val message = remoteMessage.data["message"]
        val type = remoteMessage.data["type"]?.let {NotificationType.valueOf(it)}
        type ?: return

        NotificationManagerCompat.from(this)
            .notify(type.id,createNotification(type,title,message))
    }
    private fun createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){ //오레오 버전
            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME,NotificationManager.IMPORTANCE_DEFAULT)
            channel.description = CHANNEL_DESCRIPTION

            (getSystemService(Context.NOTIFICATION_SERVICE)as NotificationManager).createNotificationChannel(channel)
        }

    }
    private fun createNotification(type:NotificationType?,title:String?,message:String?): Notification {
        val notificationBuilder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_baseline_notifications_24)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        when(type) {
            NotificationType.NORMAL -> Unit
            NotificationType.EXPANDABLE ->  {
                NotificationCompat.BigTextStyle().bigText("😀 😃 😄 😁 😆 😅")
            }
            NotificationType.CUSTOM ->{
                notificationBuilder
                    .setStyle(NotificationCompat.DecoratedCustomViewStyle())
                    .setCustomContentView(
                        RemoteViews(
                            packageName,
                            R.layout.view_custom_notification
                        ).apply {
                            setTextViewText(R.id.title,title)
                            setTextViewText(R.id.message,message)
                        }
                    )
            }

        }
        return notificationBuilder.build()
    }

    companion object {
        private  const val  CHANNEL_NAME = "emoji"
        private  const val  CHANNEL_DESCRIPTION = "emoji 를 위한채널"
        private  const val  CHANNEL_ID = "Channel ID"
    }
}