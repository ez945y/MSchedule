package neat.arrange.mschedule.entity

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import neat.arrange.mschedule.MainActivity
import java.util.*


class BootUpReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
//        throw new UnsupportedOperationException("Not yet implemented");

        /* 同一個接收者可以收多個不同行為的廣播，所以可以判斷收進來的行為為何，再做不同的動作 */
        if (intent.action == "android.intent.action.BOOT_COMPLETED") {
            /* 收到廣播後要做的事 */

            //建立通知發布鬧鐘
            val cal: Calendar = GregorianCalendar(TimeZone.getTimeZone("GMT+8:00")) //取得時間
            for (i in 0..9) {
                cal.add(Calendar.MINUTE, 1) //加一分鐘
                cal.set(Calendar.SECOND, 0) //設定秒數為0
                if (context != null) {
                    add_alarm(context, cal)
                } //MainActivity.add_alarm(context, cal)
            }
        }
    }
}