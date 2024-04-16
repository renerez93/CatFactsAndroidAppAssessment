package com.example.a1stapp_trying

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class MainActivity : ComponentActivity() {

    private val mainActivityViewModel by viewModels<MainActivityViewModel> ()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen(mainActivityViewModel)
        }
    }
}

// - - - - - - - - - - - - - - - - - - -
// Here starts the UI Section of the APP
@SuppressLint("UnrememberedMutableState")
@Composable
fun MainScreen(mainActivityViewModel: MainActivityViewModel?){

    mainActivityViewModel?.contador?.observeAsState()?.value
    mainActivityViewModel?.TheCatFact?.observeAsState()?.value

    // Getting the new fact
    fun getnewfact() {

        // This get from the API a new CatFact
        runBlocking {
            launch {
                mainActivityViewModel?.newFact()
            }
        }

    }

    // Getting previous fact
    fun getoldfact() {

        // This get an old CatFact
        runBlocking {
            launch {
                mainActivityViewModel?.prevFact()
            }
        }

    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Content of the Column
        // ↓ This ROW is for the Counter
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Fact: ${mainActivityViewModel?.contador?.value} / ${mainActivityViewModel?.contadorTotal?.value}")
        }

        // ↓ This ROW is for the CatFacts Text
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(30.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "${mainActivityViewModel?.TheCatFact?.value}",
                fontSize = 20.sp,
                textAlign = TextAlign.Center
            )
        }

        // ↓ This ROW is for the Buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(onClick = { getoldfact() }) {
                Text(text = "Previous CatFact")
            }
            Button(onClick = { getnewfact() }) {
                Text(text = "New/Next CatFact")
            }
        }
        // End of the content of the column

    }
}

// - - - - - - - - - - - - - - - - - - - - - - - - - -
// This is the preview setting for the Compose Preview
@Preview(showBackground = true)
@Composable
fun DefaultPreview(){
    MainScreen(null)
}