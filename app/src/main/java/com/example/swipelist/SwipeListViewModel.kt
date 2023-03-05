package com.example.swipelist

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class SwipeListViewModel : ViewModel() {


    private var _screenState: MutableStateFlow<SwipeListState> = MutableStateFlow(
        initialState()
    )
    val screenState : StateFlow<SwipeListState> = _screenState.asStateFlow()

    private fun initialState() = SwipeListState(
        isLoading = true,
        carsData = emptyList()
    )

    fun onDeleteClick(cardId: String) {
        TODO("Not yet implemented")
    }

    fun onFavoriteClick(cardId: String) {
        TODO("Not yet implemented")
    }

}
data class SwipeListState(
    val isLoading: Boolean,
    val carsData: List<CarDataItem>
)