package com.harshjoshi.stopwatch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.with
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PauseCircleFilled
import androidx.compose.material.icons.filled.PlayCircleFilled
import androidx.compose.material.icons.filled.StopCircle
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.harshjoshi.stopwatch.ui.theme.StopWatchTheme

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StopWatchTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainApp(viewModel = viewModel)
                }
            }
        }
    }
}

@Composable
fun MainApp(viewModel: MainViewModel) {
    MainApp(
        viewModel.isPlaying.value,
        viewModel.seconds.value,
        viewModel.minutes.value,
        viewModel.hours.value,
        { viewModel.start() },
        { viewModel.pause() },
        { viewModel.stop() }
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
private fun MainApp(
    isPlaying: Boolean,
    seconds: String,
    minutes: String,
    hours: String,
    onStart: ()->Unit = {},
    onPause: ()->Unit = {},
    onStop: ()->Unit = {},

){
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    text = "Compose Stop-Watch") },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                )
            )
        }
    ) {pv->
        Column(
            modifier = Modifier
                .padding(pv)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.weight(1f))
            Row {
                AnimatedContent(targetState = hours, label = "",
                    transitionSpec = { slideInVertically() + fadeIn() with slideOutVertically() + fadeOut() }
                ) { hours ->
                    Text(
                        fontSize = 60.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary,
                        text = hours
                    )
                }

                Text(
                    fontSize = 60.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary, text = " : "
                )

                AnimatedContent(targetState = minutes, label = "",
                    transitionSpec = { slideInVertically() + fadeIn() with slideOutVertically() + fadeOut() }
                ) { minutes ->
                    Text(
                        fontSize = 60.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary, text = minutes
                    )
                }

                Text(
                    fontSize = 60.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary, text = " : "
                )

                AnimatedContent(targetState = seconds, label = "",
                    transitionSpec = { slideInVertically() + fadeIn() with slideOutVertically() + fadeOut() }
                ) { seconds ->
                    Text(
                        fontSize = 60.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary, text = seconds
                    )
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.background(MaterialTheme.colorScheme.primary, RoundedCornerShape(50))
            ){
                AnimatedContent(targetState = isPlaying, label = "") {
                    if (it) {
                        IconButton(onClick = onPause) {
                            Icon(
                                tint = MaterialTheme.colorScheme.background,
                                modifier = Modifier.size(40.dp),
                                imageVector = Icons.Filled.PauseCircleFilled,
                                contentDescription = null
                            )
                        }
                    } else {
                        IconButton(onClick = onStart) {
                            Icon(
                                tint = MaterialTheme.colorScheme.background,
                                modifier = Modifier.size(40.dp),
                                imageVector = Icons.Filled.PlayCircleFilled,
                                contentDescription = null
                            )
                        }
                    }
                }
                IconButton(onClick = onStop) {
                    Icon(
                        tint = MaterialTheme.colorScheme.background,
                        modifier = Modifier.size(40.dp),
                        imageVector = Icons.Filled.StopCircle,
                        contentDescription = null)
                }
            }
            Spacer(modifier = Modifier.height(32.dp))
        }
    }

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    StopWatchTheme {
        MainApp(isPlaying = false, seconds = "00", minutes = "00", hours = "00")
    }
}