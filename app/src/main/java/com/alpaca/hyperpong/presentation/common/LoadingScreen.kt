package com.alpaca.hyperpong.presentation.common

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.alpaca.hyperpong.R
import com.alpaca.hyperpong.ui.theme.HyperPongTheme
import com.alpaca.hyperpong.util.Constantes.loadingTexts
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun LoadingScreen() {
    var animateTrigger by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = Unit) {
        launch {
            animateTrigger = true
        }
    }

    AnimatedVisibility(
        visible = animateTrigger,
        enter = scaleIn()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.surface)
                .padding(all = 24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.loading))
            var count by remember {
                mutableIntStateOf(0)
            }

            LaunchedEffect(Unit) {
                while (true) {
                    delay(1500)
                    count = (count + 1) % loadingTexts.size
                }
            }
            AnimatedContent(
                targetState = count,
                transitionSpec = {
                    if (targetState > initialState) {
                        (slideInVertically { height -> height } + fadeIn()).togetherWith(
                            slideOutVertically { height -> -height } + fadeOut())
                    } else {
                        (slideInVertically { height -> -height } + fadeIn()).togetherWith(
                            slideOutVertically { height -> height } + fadeOut())
                    }.using(
                        SizeTransform(clip = false)
                    )
                }, label = ""
            ) { targetCount ->
                Text(
                    modifier = Modifier.padding(vertical = 24.dp),
                    text = loadingTexts[targetCount],
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 24.sp,
                )
            }
            LottieAnimation(
                modifier = Modifier.heightIn(max = 300.dp),
                composition = composition,
                iterations = LottieConstants.IterateForever
            )
        }
    }
}

@Preview
@Composable
fun LoadingScreenPrev() {
    HyperPongTheme {
        LoadingScreen()
    }
}