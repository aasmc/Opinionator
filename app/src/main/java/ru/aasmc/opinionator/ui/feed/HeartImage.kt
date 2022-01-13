package ru.aasmc.opinionator.ui.feed

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.aasmc.opinionator.R

enum class HeartAnimationState {
    Hidden,
    Shown
}

@Composable
fun HeartImage(
    heartAnimationState: MutableState<HeartAnimationState>
) {
    val transition = updateTransition(
        targetState = heartAnimationState.value,
        label = "Heart Transition"
    )

    val heartSize by transition.animateDp(
        label = "Heart Size",
        transitionSpec = {
            if (HeartAnimationState.Shown isTransitioningTo HeartAnimationState.Hidden) {
                tween(durationMillis = 300)
            } else {
                keyframes {
                    durationMillis = 1600
                    0.0.dp at 0 with FastOutSlowInEasing
                    130.dp at 400 with FastOutSlowInEasing
                    100.dp at 550 with FastOutSlowInEasing
                    100.dp at 900
                    130.dp at 1000 with FastOutSlowInEasing
                    100.dp at 1300 with FastOutSlowInEasing
                }
            }
        }
    ) { state ->
        when (state) {
            HeartAnimationState.Hidden -> 0.dp
            HeartAnimationState.Shown -> 100.dp
        }
    }

    val outlineAlpha by transition.animateFloat(
        label = "Outline Alpha Animation",
        transitionSpec = {
            tween(700)
        }
    ) { state ->
        when (state) {
            HeartAnimationState.Hidden -> 0.7f
            HeartAnimationState.Shown -> 0f
        }
    }

    val outlineRadiusSize by transition.animateDp(
        label = "Outline Size Animation",
        transitionSpec = {
            tween(durationMillis = 500)
        }
    ) { state ->
        when (state) {
            HeartAnimationState.Hidden -> 0.dp
            HeartAnimationState.Shown -> 400.dp
        }
    }

    val radius = LocalDensity.current.run { outlineRadiusSize.toPx() }

    if (transition.currentState == transition.targetState) {
        heartAnimationState.value = HeartAnimationState.Hidden
    }

    Box(modifier = Modifier
        .drawBehind {
            val lightExplosionColor = 0XFFF0E68C
            if (transition.currentState != HeartAnimationState.Shown) {
                drawCircle(Color(lightExplosionColor), radius = radius, alpha = outlineAlpha)
            }
        }) {

        Image(
            painter = painterResource(id = R.drawable.favorite),
            contentDescription = "Heart Animation",
            colorFilter = ColorFilter.tint(Color.Red),
            modifier = Modifier
                .size(heartSize)
        )
    }
}

@Preview
@Composable
private fun HeartImagePreview() {
    val state = remember {
        mutableStateOf(HeartAnimationState.Hidden)
    }

    LaunchedEffect(key1 = "LaunchAnimation") {
        state.value = HeartAnimationState.Shown
    }

    Box(
        modifier = Modifier.size(300.dp),
        contentAlignment = Alignment.Center
    ) {
        HeartImage(heartAnimationState = state)
    }
}















