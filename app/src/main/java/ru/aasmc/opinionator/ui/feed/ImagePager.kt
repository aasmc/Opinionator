package ru.aasmc.opinionator.ui.feed

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.*
import kotlin.math.absoluteValue
import androidx.compose.ui.util.lerp

@ExperimentalPagerApi
@Composable
fun ImagePager(
    images: List<Int>
) {
    if (images.isNotEmpty()) {
        val pagerState = rememberPagerState(pageCount = images.size)
        Column {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(top = 16.dp)
            ) { page ->
                Image(
                    painter = painterResource(id = images[page]),
                    contentDescription = "Post image $page",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .padding(8.dp)
                        .animatePageChange(this, page)
                )
            }
            HorizontalPagerIndicator(
                pagerState = pagerState,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(4.dp)
            )
        }
    }
}

@ExperimentalPagerApi
private fun Modifier.animatePageChange(pagerScope: PagerScope, page: Int): Modifier {
    return this.graphicsLayer {
        val pageOffset = pagerScope.calculateCurrentOffsetForPage(page).absoluteValue
        val scale = lerp(
            start = 0.85f,
            stop = 1f,
            fraction = 1f - pageOffset
        )

        val updateAlpha = lerp(
            start = 0.5f,
            stop = 1f,
            fraction = 1f - pageOffset
        )

        scaleX = scale
        scaleY = scale
        alpha = updateAlpha
    }
}

























