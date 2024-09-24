package com.jge.androidfetch.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import com.jge.androidfetch.AndroidFetchApplication
import com.jge.androidfetch.domain.FetchItem
import com.jge.androidfetch.ui.theme.AndroidFetchTheme
import javax.inject.Inject

class MainActivity : ComponentActivity() {
    @Inject
    lateinit var viewModelFactory: FetchViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val application = applicationContext as AndroidFetchApplication
        application.applicationComponent.inject(this)
        setContent {
            AndroidFetchTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val viewModel: FetchViewModel = ViewModelProvider(this, viewModelFactory)[FetchViewModel::class.java]
                    val dataState = viewModel.uiState.collectAsState()

                    when(dataState.value){
                        is FetchViewModel.FetchUIState.Success -> {
                            val fetchItems = (dataState.value as FetchViewModel.FetchUIState.Success).fetchItems.sortedWith(
                                compareBy<FetchItem> { it.listId }.thenBy { extractNumbers(it.name) }
                            )
                            LazyColumn(modifier = Modifier.padding(16.dp)){
                                itemsIndexed(fetchItems.toList(), key = {_, item -> item.id}){ index, item->
                                    if(!item.name.isNullOrEmpty()){
                                        Text(modifier = Modifier.fillMaxWidth()
                                            .padding(
                                                top = 8.dp,
                                                bottom = 8.dp
                                            ),
                                            text = "Name: "+ item.name+ " ID: "+item.id + " ListID: "+item.listId)
                                        Divider(modifier = Modifier
                                            .fillMaxWidth()
                                            .background(color = Color.LightGray))
                                    }
                                }
                            }
                        }

                        is FetchViewModel.FetchUIState.Error -> {
                            Greeting("Error")

                        }

                        is FetchViewModel.FetchUIState.Loading -> {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator()
                            }
                        }
                    }
                }
            }
        }
    }
}

fun extractNumbers(name:String?):String{
    return name?.split(" ")?.joinToString(" ") { part ->
        part.replace(Regex("\\d+"),
        String.format("%05d", part.toIntOrNull() ?: 0)
        )
    } ?: ""
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AndroidFetchTheme {
        Greeting("Android")
    }
}