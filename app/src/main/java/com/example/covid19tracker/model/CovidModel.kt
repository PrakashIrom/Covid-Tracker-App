package com.example.covid19tracker.model

import kotlinx.serialization.Serializable

@Serializable
data class City(
    val name: String,
    val date: String,
    val fips: Int,
    val lat: String,
    val long: String,
    val confirmed: Int,
    val deaths: Int,
    val confirmed_diff: Int,
    val deaths_diff: Int,
    val last_update: String
)

@Serializable
data class Region(
    val iso: String,
    val name: String,
    val province: String,
    val lat: String,
    val long: String,
    val cities: List<City>
)

@Serializable
data class Data(
    val date: String,
    val confirmed: Int,
    val deaths: Int,
    val recovered: Int,
    val confirmed_diff: Int,
    val deaths_diff: Int,
    val recovered_diff: Int,
    val last_update: String,
    val active: Int,
    val active_diff: Int,
    val fatality_rate: Double,
    val region: Region
)

@Serializable
data class ApiResponse(
    val data: List<Data>
)
