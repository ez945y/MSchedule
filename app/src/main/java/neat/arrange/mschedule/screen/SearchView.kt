package neat.arrange.mschedule.screen

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import neat.arrange.mschedule.R
import neat.arrange.mschedule.entity.*
import java.util.*


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    state: MutableState<TextFieldValue>,
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
fun ItemList(state: MutableState<TextFieldValue>, context: Context,navController: NavController= rememberNavController(),back: () -> Unit) {
    val searchVM = SearchViewModel()
    val searchList = searchVM.searchList
    var filteredItems: MutableList<ScheduleItem>
    Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.tertiaryContainer)) {
        LazyColumn(modifier = Modifier
            .fillMaxWidth()
            .padding(top = 130.dp)) {
            val searchedText = state.value.text

            if (searchedText.isNotEmpty()) {
                filteredItems = dbSearch(searchedText, context)
                itemsIndexed(filteredItems ) { idx, filteredItem ->
                    Row(
                        modifier = Modifier
                            .clickable(onClick = {
                                dbAddhistory(searchedText, context)
                                navController.navigate("Edit/$idx")
                            })
                            .height(57.dp)
                            .fillMaxWidth()
                    ) {
                        Icon(
                            Icons.Default.Search,
                            contentDescription = "",
                            modifier = Modifier
                                .padding(start = 16.dp, top = 3.dp)
                                .size(25.dp)
                        )
                        Text(text = "${filteredItem.title.value}",
                            fontSize = 16.sp,
                            color = Color.Black,
                            modifier = Modifier
                                .padding(start = 18.dp)
                                .size(294.dp, 25.dp))
                    }
                }
            }else{
                dbHistory(context)
                itemsIndexed(searchList ) { idx, Item ->
                    Row(
                        modifier = Modifier
                            .clickable(onClick = {
                                state.value = TextFieldValue(searchList[idx])
                            })
                            .height(57.dp)
                            .fillMaxWidth()
                    ) {
                        Icon(
                            painterResource(id = R.drawable.history),
                            contentDescription = "",
                            modifier = Modifier
                                .padding(start = 16.dp, top = 3.dp)
                                .size(25.dp)
                        )
                        Text(text = "$Item",
                            fontSize = 16.sp,
                            color = Color.Black,
                            modifier = Modifier
                                .padding(start = 18.dp)
                                .size(294.dp, 25.dp))
                        Icon(
                            Icons.Default.Close,
                            contentDescription = "",
                            modifier = Modifier
                                .padding(top = 3.dp)
                                .size(25.dp)
                                .clickable {
                                    dbDeleteHistory(Item, context)
                                    back()
                                }
                        )
                    }
                }
            }
        }
        Spacer(modifier = Modifier.padding(2.dp))
    }
}
