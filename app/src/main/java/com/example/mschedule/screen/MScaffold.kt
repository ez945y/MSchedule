package com.example.mschedule.screen

import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.myapplication7.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MTopBar(
    title: String,
    onSearchBarClick: () -> Unit,
    icon: String = "s",
    onButtonClicked: () -> Unit,
) {
    CenterAlignedTopAppBar(
        title = {
            Text(modifier = Modifier.clickable {}, text = title, textAlign = TextAlign.Center)

        },
        navigationIcon = {
            IconButton(onClick = { onButtonClicked() }) {
                Icon(painter = painterResource(R.drawable.menu), null)
            }
        },
        actions = {
            IconButton(onClick = { onSearchBarClick() } //do something
            ) {
                if (icon == "s") {
                    Icon(Icons.Filled.Search, null)
                    Icon(Icons.Filled.Settings, null)
                }else {
                    Icon(Icons.Filled.Close, null)
                }
            }
        },

        )
}

@Composable
fun MBottomBar(onAddScheduleClick:()->Unit) {
    BottomAppBar {
        IconButton(onClick = { /*TODO*/ }, modifier = Modifier.weight(1f)) {
            Icon(Icons.Filled.Edit, null)
        }
        IconButton(onClick = { onAddScheduleClick()}, modifier = Modifier.weight(1f)) {
            Icon(Icons.Filled.Add, null)
        }
        IconButton(onClick = { /*TODO*/ }, modifier = Modifier.weight(1f)) {
            Icon(Icons.Filled.Share, null)
        }
    }
    Divider(
        color = MaterialTheme.colorScheme.secondary,
        thickness = 1.dp,
    )
}