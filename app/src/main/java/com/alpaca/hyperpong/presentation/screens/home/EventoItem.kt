package com.alpaca.hyperpong.presentation.screens.home

import android.content.res.Configuration
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.alpaca.hyperpong.R
import com.alpaca.hyperpong.domain.model.Evento
import com.alpaca.hyperpong.ui.theme.HyperPongTheme

@Composable
fun EventoItem(evento: Evento?, onItemClicked: (String) -> Unit) {
    evento?.let {
        ListItem(
            modifier = Modifier.clickable { onItemClicked(evento.id) },
            colors = ListItemDefaults.colors(containerColor = MaterialTheme.colorScheme.onPrimary),
            leadingContent = {
                AsyncImage(
                    modifier = Modifier.sizeIn(
                        maxHeight = 56.dp,
                        maxWidth = 56.dp
                    ),
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(evento.imagem)
                        .crossfade(true)
                        .build(),
                    contentScale = ContentScale.FillBounds,
                    contentDescription = "Imagem do evento ${evento.nome}"
                )
            },
            headlineContent = { Text(text = evento.nome) },
            supportingContent = { Text(text = evento.dataInicioFormatada) },
            trailingContent = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_bolt_sharp),
                    tint = evento.statusEvento.getColor(),
                    contentDescription = "Ícone Inscrições Abertas"
                )
            }
        )
        HorizontalDivider()
    }
}

@Preview(name = "Light Mode", showBackground = true)
@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun EventoItemPreview() {
    HyperPongTheme {
        Surface {
            EventoItem(
                evento = Evento(id = "1", nome = "Touch my body", dataInicio = "2001-05-14"),
                onItemClicked = {})
        }
    }
}

@Composable
fun ShimmerEffect(modifier: Modifier = Modifier) {
    val transition = rememberInfiniteTransition(label = "")
    val alphaAnim by transition.animateFloat(
        initialValue = 1f, targetValue = 0f, animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 500, easing = FastOutLinearInEasing
            ), repeatMode = RepeatMode.Reverse
        ), label = ""
    )
    ShimmerItem(
        modifier = modifier,
        alpha = alphaAnim
    )
}

@Composable
fun ShimmerItem(
    modifier: Modifier = Modifier,
    alpha: Float
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        color = MaterialTheme.colorScheme.onPrimary,
        shape = RoundedCornerShape(size = 20.dp)
    ) {
        Row(
            modifier = Modifier.padding(all = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .alpha(alpha = alpha)
                    .size(60.dp)
                    .background(color = Color.LightGray)
            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .alpha(alpha = alpha)
                        .fillMaxWidth(fraction = 0.7f)
                        .height(height = 20.dp)
                        .clip(RoundedCornerShape(size = 10.dp))
                        .background(color = Color.LightGray)
                )
                Box(
                    modifier = Modifier
                        .alpha(alpha = alpha)
                        .fillMaxWidth(fraction = 0.2f)
                        .height(height = 20.dp)
                        .clip(RoundedCornerShape(size = 10.dp))
                        .background(color = Color.LightGray)
                )
            }
            Box(
                modifier = Modifier
                    .alpha(alpha = alpha)
                    .size(24.dp)
                    .clip(shape = RoundedCornerShape(percent = 50))
                    .background(color = Color.LightGray)
            )
        }
    }
}

@Preview(name = "Light Mode", showBackground = true)
@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun ShimmerItemPreview() {
    HyperPongTheme {
        Surface {
            ShimmerEffect()
        }
    }
}