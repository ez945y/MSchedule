package neat.arrange.mschedule.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import neat.arrange.mschedule.entity.add_alarm
import neat.arrange.mschedule.entity.createNotificationChannel
import java.util.*

@Composable
fun Help(
) {
    val context = LocalContext.current
    val channelId = "MyTestChannel"
    LaunchedEffect(Unit) {
        createNotificationChannel(channelId, context)
    }
    val cal: Calendar = GregorianCalendar(TimeZone.getTimeZone("GMT+8:00")) //取得時間

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize(),
    ) {
        Text(
            "Notifications in Jetpack Compose",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 100.dp)
        )

        // simple notification button with tap action
        Button(onClick = {
            //cal.add(Calendar.MONTH, 1)
            cal.add(Calendar.MINUTE, 1) //加一分鐘
            cal[Calendar.SECOND] = 0 //設定秒數為0
            add_alarm(context, cal)
        }, modifier = Modifier.padding(top = 16.dp)) {
            Text(text = "Simple Notification + Tap Action")
        }
    }

}

