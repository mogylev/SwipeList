package com.example.swipelist

import android.app.DatePickerDialog
import android.graphics.Bitmap
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.example.swipelist.ui.theme.SwipeListTheme
import androidx.lifecycle.viewmodel.compose.viewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SwipeListTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    SwipeListRoute()
                }
            }
        }
    }
}

@Composable
fun SwipeListRoute(
    viewModel: SwipeListViewModel = viewModel()
) {
    val screenState by viewModel.screenState.collectAsState()
    SwipeListScreen(
        modifier = Modifier.fillMaxSize(),
        state = screenState,
        onDeleteClick = viewModel::onDeleteClick,
        onFavoriteClick = viewModel::onFavoriteClick
    )
}

@Composable
fun SwipeListScreen(
    modifier: Modifier = Modifier,
    state: SwipeListState,
    onDeleteClick: (cardId: String) -> Unit,
    onFavoriteClick: (cardId: String) -> Unit
) {

    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        items(state.carsData) { cardDataItem ->
            DraggableCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                carDataItem = cardDataItem,
                onDeleteClick = onDeleteClick,
                onFavoriteClick = onFavoriteClick
            )
        }
    }

}

@Composable
fun ActionRow(
    modifier: Modifier = Modifier,
    onDeleteClick: () -> Unit = {},
    onAddFavoriteClick: () -> Unit = {}
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End
    ) {

        Icon(
            modifier = Modifier
                .size(32.dp)
                .clickable {
                    onAddFavoriteClick.invoke()
                },
            imageVector = Icons.Filled.Favorite,
            contentDescription = "Add Favorite"
        )
        Spacer(modifier = Modifier.size(16.dp))
        Icon(
            modifier = Modifier
                .size(32.dp)
                .clickable {
                    onDeleteClick.invoke()
                },
            imageVector = Icons.Filled.Delete,
            contentDescription = "Add Favorite"
        )
        Spacer(modifier = Modifier.size(16.dp))
    }

}

@Composable
fun DraggableCard(
    modifier: Modifier = Modifier,
    carDataItem: CarDataItem,
    onDeleteClick: (cardId: String) -> Unit,
    onFavoriteClick: (cardId: String) -> Unit
) {
    ResizableHeightBox(modifier = modifier) {
        ActionRow(modifier = modifier.fillMaxWidth())
        DraggableCarItem(modifier = modifier.fillMaxWidth(), carDataItem = carDataItem)
    }
}

/**
 * Component that equalizes the height of all child components
 * (according to the highest child) and put them on top of each other
 */
@Composable
fun ResizableHeightBox(modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    SubcomposeLayout(modifier = modifier) { constraints ->

        val contentPlaceables = subcompose(1, content).map {
            it.measure(constraints)
        }

        val maxSize = contentPlaceables.fold(IntSize.Zero) { currentMax, placeable ->
            IntSize(
                width = maxOf(currentMax.width, placeable.width),
                height = maxOf(currentMax.height, placeable.height)
            )
        }

        val resizedPlaceables = subcompose(2, content).map {
            it.measure(
                constraints.copy(minHeight = maxSize.height)
            )
        }

        layout(maxSize.width, maxSize.height) {
            resizedPlaceables.forEach {
                it.place(0, 0)
            }
        }
    }
}

@Preview(
    showBackground = true,
    device = Devices.PIXEL_4,
    showSystemUi = true
)
@Composable
fun DefaultPreview() {
    SwipeListTheme {
        SwipeListScreen(
            modifier = Modifier.fillMaxSize(),
            state = SwipeListState(
                isLoading = false,
                carsData = emptyList()
            ),
            onFavoriteClick = {},
            onDeleteClick = {}
        )
    }
}
