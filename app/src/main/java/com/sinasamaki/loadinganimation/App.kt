package com.sinasamaki.loadinganimation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@ExperimentalFoundationApi
@Composable
fun App() {

    val scope = rememberCoroutineScope()
    var isRefreshing by remember { mutableStateOf(false) }
    val refresh = remember {
        {
            scope.launch {
                isRefreshing = true
                delay(3000)
                isRefreshing = false
            }
        }
    }

    CustomPullToRefresh(
        isRefreshing = isRefreshing,
        onRefresh = { refresh() }
    ) {
        LazyColumn(
            contentPadding = PaddingValues(vertical = 10.dp)
        ) {
            items(100) { index ->
                ListItem(index = index)
            }
        }
    }
}

@ExperimentalFoundationApi
@Composable
fun App2() {

    val scope = rememberCoroutineScope()
    var isRefreshing by remember { mutableStateOf(false) }
    val refresh = remember {
        {
            scope.launch {
                isRefreshing = true
                delay(3000)
                isRefreshing = false
            }
        }
    }

    FancyPullToRefresh(
        isRefreshing = isRefreshing,
        onRefresh = { refresh() }
    ) {
        LazyColumn(
            contentPadding = PaddingValues(vertical = 10.dp)
        ) {
            items(100) { index ->
                ListItem(index = index)
            }
        }
    }
}

@Composable
fun ListItem(index: Int) {
    Column(
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {

        Box(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth()
        ) {

            Image(
                modifier = Modifier
                    .clip(RoundedCornerShape(5.dp))
                    .width(130.dp)
                    .aspectRatio(3 / 2f),
                contentScale = ContentScale.Crop,
                painter = rememberAsyncImagePainter(
                    model = "https://picsum.photos/id/${(index + 2) * 5}/200/300",
                ),
                contentDescription = "image_$index"
            )

            Column(
                modifier = Modifier.padding(horizontal = 16.dp),
            ) {
                if (index % 3 == 0)
                    Text(
                        text = "TOP RATED",
                        style = MaterialTheme.typography.caption,
                        color = MaterialTheme.colors.primary,
                    )
                Text(
                    text = when (index) {
                        5 -> "This is Mambo..."
                        else -> "This is $index"
                    },
                    style = MaterialTheme.typography.h1,
                )
                Text(
                    text = when (index) {
                        5 -> "... number five"
                        else -> "This is the body of the number $index"
                    },
                    style = MaterialTheme.typography.body1,
                )

            }
        }
        Box(modifier = Modifier.height(8.dp))
        Divider()

    }
}