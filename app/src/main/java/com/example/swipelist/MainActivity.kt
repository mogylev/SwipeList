package com.example.swipelist

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
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
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
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        items(key = { it.id }, items = state.carsData) { cardDataItem ->
            DraggableCarContainer(
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
fun DraggableCarContainer(
    modifier: Modifier = Modifier,
    carDataItem: CarDataItem,
    onDeleteClick: (cardId: String) -> Unit,
    onFavoriteClick: (cardId: String) -> Unit
) {
    Box(modifier = modifier.height(IntrinsicSize.Max)) {
        ActionRow(
            modifier = modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            isFavorite = carDataItem.isFavorite,
            onAddFavoriteClick = {
                onFavoriteClick.invoke(carDataItem.id)
            },
            onDeleteClick = {
                onDeleteClick.invoke(carDataItem.id)
            }
        )
        DraggableCarItem(
            modifier = modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            carDataItem = carDataItem
        )
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
