package com.sinasamaki.loadinganimation

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.LocalOverScrollConfiguration
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlin.math.roundToInt

@ExperimentalFoundationApi
@Composable
fun CustomPullToRefresh(
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

    val indicator = remember { 50.dp }
    val indicatorPx = remember { with(density) { indicator.toPx() } }


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

                Box(
                    modifier = Modifier
                        .offset { IntOffset(0, -indicatorPx.roundToInt()) }
                        .offset { IntOffset(y = animatedOffset, x = 0) }
                        .background(
                            color = when {
                                willRefresh -> Color.Magenta
                                state.isRefreshing -> Color.Green
                                else -> Color.DarkGray
                            },
                        )
                        .size(indicator)
                )

            }
        ) {

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colors.surface)
            ) {
                content()
            }

        }
    }
}