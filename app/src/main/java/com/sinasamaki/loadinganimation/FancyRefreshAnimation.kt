package com.sinasamaki.loadinganimation

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import com.sinasamaki.loadinganimation.ui.theme.SwatchA
import com.sinasamaki.loadinganimation.ui.theme.SwatchB
import com.sinasamaki.loadinganimation.ui.theme.SwatchC

@Composable
fun FancyRefreshAnimation(
    modifier: Modifier = Modifier,
    isRefreshing: () -> Boolean,
    willRefresh: () -> Boolean,
    offsetProgress: () -> Float,
) {

    Row(
        modifier = modifier
            .padding(16.dp)
            .height(80.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Box(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f), contentAlignment = Alignment.TopCenter
        ) {
            CircleWithRing(
                modifier = Modifier
                    .size(30.dp),
                isRefreshing = isRefreshing(),
                willRefresh = willRefresh(),
                offsetProgress = offsetProgress(),
                shape = RoundedCornerShape(10.dp),
                color = SwatchB,
            )
        }

        Box(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f), contentAlignment = Alignment.BottomCenter
        ) {
            CircleWithRing(
                modifier = Modifier
                    .size(30.dp),
                isRefreshing = isRefreshing(),
                willRefresh = willRefresh(),
                offsetProgress = offsetProgress(),
                shape = RoundedCornerShape(10.dp),
                color = SwatchA,
            )
        }

        Box(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f), contentAlignment = Alignment.Center
        ) {
            CircleWithRing(
                modifier = Modifier
                    .size(30.dp),
                isRefreshing = isRefreshing(),
                willRefresh = willRefresh(),
                offsetProgress = offsetProgress(),
                shape = RoundedCornerShape(10.dp),
                color = SwatchC,
            )
        }

        Box(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f), contentAlignment = Alignment.BottomCenter
        ) {
            CircleWithRing(
                modifier = Modifier
                    .size(30.dp),
                isRefreshing = isRefreshing(),
                willRefresh = willRefresh(),
                offsetProgress = offsetProgress(),
                shape = RoundedCornerShape(10.dp),
                color = SwatchB,
            )
        }

        Box(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f), contentAlignment = Alignment.TopCenter
        ) {
            CircleWithRing(
                modifier = Modifier
                    .size(30.dp),
                isRefreshing = isRefreshing(),
                willRefresh = willRefresh(),
                offsetProgress = offsetProgress(),
                shape = RoundedCornerShape(10.dp),
                color = SwatchA,
            )
        }

    }

}

@Composable
fun CircleWithRing(
    modifier: Modifier = Modifier,
    isRefreshing: Boolean,
    willRefresh: Boolean,
    offsetProgress: Float,
    shape: Shape = CircleShape,
    color: Color = Color.Yellow,
) {

    val scale by animateFloatAsState(
        targetValue = if (willRefresh) 1f else .8f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioHighBouncy,
        )
    )
    val rotateTransition = rememberInfiniteTransition()

    val rotation by when {
        isRefreshing -> rotateTransition.animateFloat(
            initialValue = 45f,
            targetValue = 180f,
            animationSpec = infiniteRepeatable(
                animation = tween(1000, easing = FastOutSlowInEasing),
                repeatMode = RepeatMode.Reverse,
            )
        )
        else -> remember { mutableStateOf(45f) }
    }

    Box(modifier = modifier.scale(scale)) {
        Box(
            modifier = Modifier
                .rotate(-rotation)
                .align(Alignment.Center)
                .scale(offsetProgress * 1.5f)
                .border(
                    width = 15.dp * (1f - offsetProgress),
                    shape = shape,
                    color = color
                )
                .fillMaxSize()
        )

        Box(
            modifier = Modifier
                .rotate(rotation)
                .align(Alignment.Center)
                .scale(offsetProgress)
                .clip(shape)
                .background(color = color)
                .fillMaxSize()
        )
    }
}