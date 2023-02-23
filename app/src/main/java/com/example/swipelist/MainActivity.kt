package com.example.swipelist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.VectorPainter
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.swipelist.ui.theme.SwipeListTheme

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
                    SwipeListScreen(carDataList = CarsDataSource.getCarsList())
                }
            }
        }
    }
}

@Composable
fun SwipeListScreen(modifier: Modifier = Modifier, carDataList: List<CarDataItem>) {

    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        items(carDataList) { cardDataItem ->
            DraggableCarComponent(carDataItem = cardDataItem)
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
        modifier = modifier.fillMaxWidth().fillMaxHeight(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End
    ) {

        Icon(
            modifier = Modifier
                .size(32.dp),
            imageVector = Icons.Filled.Favorite,
            contentDescription = "Add Favorite"
        )
        Spacer(modifier = Modifier.size(16.dp))
        Icon(
            modifier = Modifier
                .size(32.dp),
            imageVector = Icons.Filled.Delete,
            contentDescription = "Add Favorite"
        )
        Spacer(modifier = Modifier.size(16.dp))
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
        SwipeListScreen(carDataList = CarsDataSource.getCarsList())
    }
}
