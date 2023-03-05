package com.example.swipelist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SwipeListViewModel : ViewModel() {

    private var mutableScreenState: MutableStateFlow<SwipeListState> = MutableStateFlow(
        initialState()
    )
    val screenState: StateFlow<SwipeListState> = mutableScreenState.asStateFlow()

    init {
        viewModelScope.launch {
            CarsDataSource.getCarsList().collect { carsList ->
                mutableScreenState.value = screenState.value.copy(
                    isLoading = false,
                    carsData = carsList
                )
            }
        }
    }

    private fun initialState() = SwipeListState(
        isLoading = true,
        carsData = emptyList()
    )

    fun onDeleteClick(cardId: String) {
        viewModelScope.launch {
            CarsDataSource.onDeleteOperation(cardId)
        }
    }

    fun onFavoriteClick(cardId: String) {
        viewModelScope.launch {
            screenState.value.carsData.find { it.id == cardId }?.let {
                CarsDataSource.onFavoriteOperation(cardId, it.isFavorite.not())
            }
        }
    }

}

data class SwipeListState(
    val isLoading: Boolean,
    val carsData: List<CarDataItem>
)