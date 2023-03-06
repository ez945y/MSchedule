package neat.arrange.mschedule.screen

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import neat.arrange.mschedule.R
import neat.arrange.mschedule.entity.dbCheck
import neat.arrange.mschedule.entity.formatterGobal
import neat.arrange.mschedule.entity.globalDate
import neat.arrange.mschedule.entity.sdf
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*


@Composable
fun calenderScreen(
    yearNum: MutableState<Int>,
    monthNum: MutableState<Int>,
    dayNum: MutableState<Int>,
    context: Context,
    localDate: MutableState<LocalDate>,
    month: ArrayList<ArrayList<String>>,
    state: ScrollableState,
    change: MutableState<Int>,
) {
    val formatterMonth = DateTimeFormatter.ofPattern("MM", Locale.TAIWAN)
    val formatterYear = DateTimeFormatter.ofPattern("yyyy", Locale.TAIWAN)
    val formatterDay = DateTimeFormatter.ofPattern("dd", Locale.TAIWAN)
    yearNum.value = localDate.value.format(formatterYear).toInt()
    monthNum.value = localDate.value.format(formatterMonth).toInt()
    dayNum.value = localDate.value.format(formatterDay).toInt()
    Card(
        modifier = Modifier
            .padding(8.dp)
            .padding(vertical = 15.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.tertiaryContainer)) {
        Row(Modifier
            .padding(top = 10.dp, bottom = 10.dp, start = 30.dp, end = 30.dp)
            .fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Icon(Icons.Filled.ArrowBack,
                modifier = Modifier.clickable { monthNum.value -= 1
                    localDate.value = sdf.parse("${yearNum.value}-${monthNum.value}-${dayNum.value}").toInstant().atZone(
                        ZoneId.systemDefault()).toLocalDate()
                    globalDate.value = localDate.value.format(formatterGobal)},
                contentDescription = null)
            Text(text = "${yearNum.value}年 ${monthNum.value} 月",
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(start = 30.dp, end = 30.dp)
                    .clickable {
                        datePicker(true, context, localDate.value, onDateSelect = {
                            localDate.value = it
                        })
                    })
            Icon(Icons.Filled.ArrowForward,
                modifier = Modifier.clickable { monthNum.value += 1
                    localDate.value = sdf.parse("${yearNum.value}-${monthNum.value}-${dayNum.value}").toInstant().atZone(
                        ZoneId.systemDefault()).toLocalDate()
                    globalDate.value = localDate.value.format(formatterGobal)},
                contentDescription = null)
        }
        Divider(color = MaterialTheme.colorScheme.secondary,
            thickness = 1.dp,
            modifier = Modifier.padding(horizontal = 18.dp))
        WeekItem()
        //日曆
        LazyColumn(modifier = Modifier
            .padding(horizontal = 8.dp)
            .scrollable(
                state = state, orientation = Orientation.Horizontal,
            )) {
            items(month) { m ->
                Divider(color = MaterialTheme.colorScheme.secondary,
                    thickness = 1.dp,
                    modifier = Modifier
                        .padding(top = 4.dp)
                        .padding(horizontal = 8.dp))
                DateItem(
                    m,
                    localDate,
                    yearNum.value.toString(),
                    monthNum.value.toString(),
                    context,
                    change)
            }
        }
        Divider(color = MaterialTheme.colorScheme.secondary,
            thickness = 1.dp,
            modifier = Modifier
                .padding(horizontal = 18.dp)
                .padding(bottom = 32.dp))
    }
}


@Composable
fun WeekItem(
) {
    val weeks = listOf("日", "一", "二", "三", "四", "五", "六")
    Card(
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .padding(top = 2.dp),
    ) {
        LazyRow(modifier = Modifier
            .padding(8.dp)
            .padding(vertical = 2.dp)
            .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically) {
            itemsIndexed(weeks) { idx, week ->
                Text(text = week, textAlign = TextAlign.Center, color = if (idx == 0 || idx == 6) {
                    Color.Red
                } else {
                    Color.Unspecified
                })
            }
        }
    }
}

@Composable
fun DateItem(
    week: List<String>,
    localDate: MutableState<LocalDate>,
    year: String,
    month: String,
    context: Context,
    change: MutableState<Int>,
) {
    Card {
        LazyRow(modifier = Modifier
            .padding(horizontal = 8.dp)
            .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically) {
            itemsIndexed(week) { idx, date ->
                if(date !="40") {
                    Column(horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.clickable {
                            localDate.value =
                                sdf.parse("${year}-${month}-${date}").toInstant().atZone(
                                    ZoneId.systemDefault()).toLocalDate()
                            globalDate.value = localDate.value.format(formatterGobal)
                            change.value = 1
                        }) {
                        Text(text = date,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(top = 2.dp),
                            color = if (idx == 0 || idx == 6) {
                                Color.Red
                            } else {
                                Color.Unspecified
                            })
                        Column {
                            Box(modifier = Modifier.size(40.dp, 40.dp),
                                contentAlignment = Alignment.Center) {

                                if (dbCheck(sdf.parse("$year-$month-$date").toInstant()
                                        .atZone(ZoneId.systemDefault()).toLocalDate(), context)
                                ) {
                                    Icon(painterResource(id = R.drawable.check),
                                        null,
                                        modifier = Modifier.size(12.dp))
                                }

                            }
                        }

                    }
                }else{
                    Card{
                        Box(modifier = Modifier.size(40.dp, 40.dp))
                    }

                }
            }
        }
    }
}
