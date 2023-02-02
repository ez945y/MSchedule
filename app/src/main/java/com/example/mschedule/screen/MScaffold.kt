package com.example.mschedule.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.myapplication7.R
import java.time.LocalDate


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MTopBar(
    onSearchBarClick: () -> Unit,
    icon: String = "s",
    change:MutableState<Int> = mutableStateOf(1),
    onButtonClicked: () -> Unit,
) {
    CenterAlignedTopAppBar(
        title = { },
        navigationIcon = {
            IconButton(onClick = { onButtonClicked() }) {
                Icon(painter = painterResource(R.drawable.menu),
                    null,
                    modifier = Modifier.size(24.dp))
            }
        },
        actions = {

            if (icon == "s") {
                Row {
                    IconButton (onClick = { if(change.value!=2){change.value =2}else{change.value =1} } //do something
                    ) {
                        Icon(painterResource(id = R.drawable.calendar),
                            null,
                            modifier = Modifier.size(24.dp).padding(top = 3.dp))

                    }
                    IconButton(onClick = { onSearchBarClick() } //do something
                    ) {
                        Icon(Icons.Filled.Search,
                            null,
                            modifier = Modifier
                                .size(30.dp)
                                .padding(top = 3.dp))
                    }
                    IconButton (onClick = { onSearchBarClick() } //do something
                            ) {
                        Icon(painterResource(id = R.drawable.setting),
                            null,
                            modifier = Modifier.size(24.dp).padding(top = 3.dp))

                    }
                }
            } else {
                IconButton(onClick = { onSearchBarClick() } //do something
                ) {
                    Icon(Icons.Filled.Close, null, modifier = Modifier.size(24.dp))
                }
            }

        },

        )
}

@Composable
fun MBottomBar(onAddScheduleClick: () -> Unit) {
    BottomAppBar {
        IconButton(onClick = { /*TODO*/ }, modifier = Modifier.weight(1f)) {
            Icon(Icons.Filled.Edit, null)
        }
        IconButton(onClick = { onAddScheduleClick() }, modifier = Modifier.weight(1f)) {
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