package com.example.mschedule.screen

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.example.mschedule.entity.ScheduleViewModel
import com.example.mschedule.entity.SearchViewModel
import com.example.mschedule.entity.db_Search
import com.example.mschedule.ui.theme.MScheduleTheme
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
        placeholder = {Text("輸入關鍵字搜尋")},
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
fun ItemList(state: MutableState<TextFieldValue>,context: Context) {

    val searchVM = SearchViewModel()
    val searchList = searchVM.searchList
    var filteredItems:  MutableList<String>
    Card(colors =CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.tertiaryContainer)) {
        LazyColumn(modifier = Modifier.fillMaxWidth().padding(top=70.dp)) {
            val searchedText = state.value.text
            filteredItems = if (searchedText.isEmpty()){
                searchList
            } else {
                val resultList = db_Search(searchedText,context)
                resultList
            }
            items(filteredItems) { filteredItem ->
                ItemListItem(
                    ItemText = filteredItem,
                    onItemClick = { /*Click event code needs to be implement*/
                    }
                )
            }
        }
        Spacer(modifier = Modifier.padding(2.dp))
    }
}


@Composable
fun ItemListItem(ItemText: String, onItemClick: (String) -> Unit) {
    Row(
        modifier = Modifier
            .clickable(onClick = { onItemClick(ItemText) })
            .height(57.dp)
            .fillMaxWidth()
    ) {
        Text(text = "—  $ItemText", fontSize = 18.sp, color = Color.Black, modifier = Modifier.padding(start = 28.dp))
    }
}

@Preview
@Composable
fun SearchPreview() {
    MScheduleTheme {
        Surface {
            val textState = remember { mutableStateOf(TextFieldValue("")) }
            Column {
                SearchScreen(textState){}
                ItemList(state = textState,LocalContext.current)
            }
        }
    }
}