package com.sinasamaki.loadinganimation

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.LocalOverScrollConfiguration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import java.lang.Float.min
import kotlin.math.roundToInt

@ExperimentalFoundationApi
@Composable
fun FancyPullToRefresh(
    modifier: Modifier = Modifier,
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    content: @Composable () -> Unit,
) {

    val pullState = rememberSwipeRefreshState(isRefreshing = isRefreshing)
    var offset by remember { mutableStateOf(0) }
    val animatedOffset by animateIntAsState(
        targetValue = offset,
        animationSpec = spring(stiffness = Spring.StiffnessMediumLow)
    )

    val density = LocalDensity.current
    val trigger = remember { 120.dp }
    val triggerPx = remember { with(density) { trigger.toPx() } }


    CompositionLocalProvider(
        LocalOverScrollConfiguration provides null
    ) {
        SwipeRefresh(
            modifier = modifier,
            state = pullState,
            onRefresh = onRefresh,
            refreshTriggerDistance = trigger,
            indicator = { state, _ ->

                val willRefresh = state.indicatorOffset.roundToInt() > triggerPx
                offset = state.indicatorOffset.roundToInt() + if (willRefresh) 100 else 0

                offset = when {
                    willRefresh -> triggerPx.roundToInt() + (state.indicatorOffset.roundToInt() * .1f).roundToInt()
                    state.isRefreshing -> triggerPx.roundToInt()
                    else -> state.indicatorOffset.roundToInt()
                }
            }
        ) {
            Box(
                Modifier
                    .background(color = MaterialTheme.colors.background)

            ) {

                FancyRefreshAnimation(
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .fillMaxWidth(),
                    isRefreshing = { pullState.isRefreshing },
                    willRefresh = { offset > triggerPx },
                    offsetProgress = { min(animatedOffset / triggerPx, 1f) }
                )

                val scale by animateFloatAsState(
                    targetValue = if (offset > triggerPx) .95f else 1f,
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                    )
                )

                Box(modifier = Modifier
                    .scale(scale)
                    .offset { IntOffset(x = 0, y = animatedOffset) }
                    .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
                    .fillMaxSize()
                    .background(MaterialTheme.colors.surface)
                ) {
                    content()
                }
            }
        }
    }
}