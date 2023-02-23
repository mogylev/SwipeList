package com.example.swipelist

object CarsDataSource {

    private var carsList : List <CarDataItem> = emptyList()

    init {
        carsList = listOf(
            CarDataItem(
                id = java.util.UUID.randomUUID().toString(),
                model = "Kia Sportage",
                description = "SUV with good clirens",
                url = "some url",
                isFavorite = true
            ),
            CarDataItem(
                id = java.util.UUID.randomUUID().toString(),
                model = "Reno Megane",
                description = "Buisness class for rich people",
                url = "some url",
                isFavorite = false
            )
        )
    }

    fun getCarsList() = carsList
}