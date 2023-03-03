package com.example.mschedule.screen

import android.content.Context
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.mschedule.entity.*
import com.example.mschedule.ui.theme.MScheduleTheme
import java.util.*


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    state: MutableState<TextFieldValue>,
    navController: NavController = rememberNavController(),
    back: () -> Unit,
) {
    TextField(
        value = state.value,
        onValueChange = {
            state.value = it
        },
        modifier = Modifier.fillMaxWidth(),
        textStyle = TextStyle(color = Color.Black, fontSize = 18.sp),
        leadingIcon = {
            Icon(
                Icons.Default.Search,
                contentDescription = "",
                modifier = Modifier
                    .padding(15.dp)
                    .size(24.dp)
            )
        },
        trailingIcon = {
            IconButton(
                onClick = {
                    back()
                }
            ) {
                Icon(
                    Icons.Default.Close,
                    contentDescription = "",
                    modifier = Modifier
                        .size(25.dp)
                )
            }
        },
        placeholder = { Text("輸入關鍵字搜尋") },
        singleLine = true,
        shape = RectangleShape, // The TextFiled has rounded corners top left and right by default
        colors = TextFieldDefaults.textFieldColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            cursorColor = Color.Black,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        )
    )

}

@Composable
fun ItemList(state: MutableState<TextFieldValue>, context: Context,navController: NavController= rememberNavController()) {
    val searchVM = SearchViewModel()
    val searchList = searchVM.searchList
    var filteredItems: MutableList<ScheduleItem>
    Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.tertiaryContainer)) {
        LazyColumn(modifier = Modifier
            .fillMaxWidth()
            .padding(top = 130.dp)) {
            val searchedText = state.value.text
            if (searchedText.isNotEmpty()) {
                filteredItems = db_Search(searchedText, context)
                itemsIndexed(filteredItems ) { idx, filteredItem ->
                    Row(
                        modifier = Modifier
                            .clickable(onClick = {
                                db_AddHistory(searchedText,context)
                                navController.navigate("Edit/$idx")
                            })
                            .height(57.dp)
                            .fillMaxWidth()
                    ) {
                        Text(text = "—  ${filteredItem.title.value}",
                            fontSize = 18.sp,
                            color = Color.Black,
                            modifier = Modifier.padding(start = 28.dp))
                    }
                }
            }else{
                db_History(context)
                itemsIndexed(searchList ) { idx, Item ->
                    Row(
                        modifier = Modifier
                            .clickable(onClick = {
                                state.value = TextFieldValue(searchList[idx])
                            })
                            .height(57.dp)
                            .fillMaxWidth()
                    ) {
                        Text(text = "—  $Item",
                            fontSize = 18.sp,
                            color = Color.Black,
                            modifier = Modifier.padding(start = 28.dp))
                    }
                }
            }
        }
        Spacer(modifier = Modifier.padding(2.dp))
    }
}


@Composable
fun ItemListItem(Item:ScheduleItem, navController: NavController,idx:Int) {
    Row(
        modifier = Modifier
            .clickable(onClick = {navController.navigate("Edit/$idx")})
            .height(57.dp)
            .fillMaxWidth()
    ) {
        Text(text = "—  ${Item.title.value}",
            fontSize = 18.sp,
            color = Color.Black,
            modifier = Modifier.padding(start = 28.dp))
    }
}

@Preview
@Composable
fun SearchPreview() {
    MScheduleTheme {
        Surface {
            val textState = remember { mutableStateOf(TextFieldValue("")) }
            Column {
                SearchScreen(textState) {}
                ItemList(state = textState, LocalContext.current,navController = rememberNavController())
            }
        }
    }
}