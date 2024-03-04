package com.github.bytehole

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.github.boybeak.bytehole.channel.Channel
import com.github.bytehole.ui.theme.ByteHoleTheme

class MainActivity : ComponentActivity() {

    private val channel = Channel("255.255.255.255", 8888)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ByteHoleTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainView(channel)
                }
            }
        }
    }
}

@Composable
fun MainView(channel: Channel) {
    Log.d("MainView", "RUNNING --->")

    var openState by rememberSaveable {
        mutableStateOf(channel.isOpened)
    }
    var messageToSend by rememberSaveable { mutableStateOf("") }

    val context = LocalContext.current

    Column {
        Button(onClick = {
            channel.open()
            openState = channel.isOpened
        }) {
            Text(text = "Open:${openState}")
        }
        TextField(
            value = messageToSend,
            placeholder = {
                Text(text = "Input message to send")
            },
            onValueChange = {
                messageToSend = it
            }
        )
        Button(onClick = {
            Thread {
                channel.send(messageToSend)
            }.start()
        }) {
            Text(text = "Send")
        }
        Button(onClick = {
            channel.close()
        }) {
            Text(text = "Close")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    val channel = Channel("255.255.255.255", 8888)
    ByteHoleTheme {
        MainView(channel)
    }
}