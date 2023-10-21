package com.alpaca.hyperpong.presentation.common

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandIn
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.shrinkOut
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ItemIconeTexto(modifier: Modifier = Modifier, icone: Painter, texto: String) {
    AnimatedContent(
        targetState = texto,
        label = "ItemIconeTexto Animated Content",
        transitionSpec = {
            (scaleIn(animationSpec = tween()) + fadeIn()).togetherWith(scaleOut(animationSpec = tween()) + fadeOut())
        }
    ) {
        Row(
            modifier = modifier,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Icon(
                painter = icone,
                contentDescription = ""
            )
            Text(
                text = it,
                fontSize = 12.sp
            )
        }
    }
}