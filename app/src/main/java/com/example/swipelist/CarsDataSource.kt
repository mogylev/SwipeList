package com.example.swipelist

import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.first


object CarsDataSource {

    private val carsFlow = MutableSharedFlow<List<CarDataItem>>(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    init {
        val list  = listOf(
            CarDataItem(
                id = java.util.UUID.randomUUID().toString(),
                model = "Kia Sportage",
                description = "SUV with good clirens",
                url = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTdjFumkXWR45KtrHhNU4dt2zHpFMK1ya-GeA&usqp=CAU",
                isFavorite = true
            ),
            CarDataItem(
                id = java.util.UUID.randomUUID().toString(),
                model = "Renault Megane",
                description = "Buisness class for rich people",
                url = "https://auto.24tv.ua/resources/photos/news/201806/47069ba72dbe-5cc9-4530-9a13-eac24c6910ef.jpg?1528559488000&fit=cover&w=1200&h=675&q=65",
                isFavorite = false
            ),
            CarDataItem(
                id = java.util.UUID.randomUUID().toString(),
                model = "SMART",
                description = "Compact car for town",
                url = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSOZez5QKLsosD8wD" +
                        "-h15nzxBDDeZ7sh5s6aOH5JxU-qgLsPl0ah-jyLGq3UBJ0IdyPLrk&usqp=CAU",
                isFavorite = false
            )
        )
        carsFlow.tryEmit(list)
    }

     fun getCarsList():Flow<List<CarDataItem>> {
        return carsFlow
    }

    suspend fun onFavoriteOperation(carId: String, favoriteState: Boolean) {
        delay(500) // some successfull api operation
        val currentList = carsFlow.first()
        val updatedList = currentList.map {
            if (it.id == carId) {
                it.copy(isFavorite = favoriteState)
            } else {
                it.copy()
            }
        }
        carsFlow.emit(updatedList)
    }

    suspend fun onDeleteOperation(carId: String){
        delay(500) // some successfull api operation
        val currentList = carsFlow.first()
        val updatedList = currentList.filterNot { it.id == carId }
        carsFlow.emit(updatedList)
    }
}