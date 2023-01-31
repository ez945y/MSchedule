package com.example.mschedule.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
                Icon(painter = painterResource(R.drawable.menu), null,modifier=Modifier.size(24.dp))
            }
        },
        actions = {
            IconButton(onClick = { onSearchBarClick() } //do something
            ) {
                if (icon == "s") {
                    Row {
                        Icon(Icons.Filled.Search, null,modifier = Modifier.size(24.dp).padding(end=5.dp))
                        Icon(painterResource(id = R.drawable.setting), null,modifier = Modifier.padding(top=3.dp))
                    }
                }else {
                    Icon(Icons.Filled.Close, null,modifier=Modifier.size(24.dp))
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