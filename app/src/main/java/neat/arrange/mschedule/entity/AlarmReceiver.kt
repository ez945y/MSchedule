package neat.arrange.mschedule.entity

import android.R
import android.app.*
import android.content.BroadcastReceiver
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Context.ALARM_SERVICE
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import neat.arrange.mschedule.MainActivity
import java.util.*


class AlarmReceiver : BroadcastReceiver() {
    //    建立notificationManager與notification物件
    private var notificationManager: NotificationManager? = null
    private var notification: Notification? = null

    override fun onReceive(context: Context, intent: Intent) {
        val channelId = "MyTestChannel"
        val notificationId = 0
        createNotificationChannel(channelId, context)
        val bData = intent.extras
        if (bData!!["title"] == "activity_app") {
            Log.d("我成功了","Yes")
            //val notifyIntent = Intent(context, MainActivity::class.java)
            //val pendingIntent = PendingIntent.getActivity(context, 0, notifyIntent, 0)

            //        執行通知
            //broadcastNotify(context, pendingIntent)

            showSimpleNotificationWithTapAction(
                context,
                channelId,
                notificationId,
                "Simple notification + Tap action",
                "This simple notification will open an activity on tap."
            )
        }

    }

    //     建立通知方法
    private fun broadcastNotify(context: Context, pendingIntent: PendingIntent) {
        notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        //        建立通知物件內容
        notification = Notification.Builder(context)
            .setWhen(System.currentTimeMillis())
            .setSmallIcon(R.drawable.ic_dialog_alert)
            .setContentTitle("訊息")
            .setContentText("測試測試")
            .setContentIntent(pendingIntent)
            .setVibrate(longArrayOf(0, 100, 200, 300, 400, 500))
            .build()

        //        發送通知
        notificationManager!!.notify(NOTIFICATION_ID, notification)
    }

    companion object {
        //    建立能辨識通知差別的ID
        private const val NOTIFICATION_ID = 0
    }
}
fun createNotificationChannel(channelId: String, context: Context) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val name = "MyTestChannel"
        val descriptionText = "My important test channel"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(channelId, name, importance).apply {
            description = descriptionText
        }

        val notificationManager: NotificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}

fun showSimpleNotificationWithTapAction(
    context: Context,
    channelId: String,
    notificationId: Int,
    textTitle: String,
    textContent: String,
    priority: Int = NotificationCompat.PRIORITY_DEFAULT,
) {
    val intent = Intent(context, MainActivity::class.java).apply {
        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    }

    val pendingIntent: PendingIntent = PendingIntent.getActivity(context, 0, intent, 0)

    val builder = NotificationCompat.Builder(context, channelId)
        .setSmallIcon(R.drawable.ic_lock_idle_alarm)
        .setContentTitle(textTitle)
        .setContentText(textContent)
        .setPriority(priority)
        .setContentIntent(pendingIntent)
        .setAutoCancel(true)

    with(NotificationManagerCompat.from(context)) {
        notify(notificationId, builder.build())
    }
}

fun add_alarm(context: Context, cal: Calendar) {
    Log.d(TAG,
        "alarm add time: " + java.lang.String.valueOf(cal.get(Calendar.MONTH)) + "." + java.lang.String.valueOf(
            cal.get(Calendar.DATE)) + " " + java.lang.String.valueOf(cal.get(Calendar.HOUR_OF_DAY)) + ":" + cal.get(
            Calendar.MINUTE) + ":" + cal.get(Calendar.SECOND))
    val intent = Intent(context, AlarmReceiver::class.java)
    // 以日期字串組出不同的 category 以添加多個鬧鐘
    intent.addCategory("ID." + java.lang.String.valueOf(cal.get(Calendar.MONTH)) + "." + java.lang.String.valueOf(
        cal.get(Calendar.DATE)) + "-" + java.lang.String.valueOf(
        cal.get(Calendar.HOUR_OF_DAY)) + "." + java.lang.String.valueOf(cal.get(Calendar.MINUTE)) + "." + java.lang.String.valueOf(
        cal.get(Calendar.SECOND)))

    val AlarmTimeTag =
        "Alarmtime " + java.lang.String.valueOf(cal.get(Calendar.HOUR_OF_DAY)) + ":" + java.lang.String.valueOf(
            cal.get(Calendar.MINUTE)) + ":" + java.lang.String.valueOf(cal.get(Calendar.SECOND))
    intent.putExtra("title", "activity_app")
    intent.putExtra("time", AlarmTimeTag)
    val pi = PendingIntent.getBroadcast(context, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT)
    val am = context.getSystemService(ALARM_SERVICE) as AlarmManager
    am[AlarmManager.RTC_WAKEUP, cal.getTimeInMillis()] = pi //註冊鬧鐘
}