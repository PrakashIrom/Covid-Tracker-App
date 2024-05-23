package com.example.covid19tracker.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.covid19tracker.api.APIService
import com.example.covid19tracker.api.CovidApi
import com.example.covid19tracker.model.Data
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface UIState {
    object Loading: UIState
    object Error: UIState
    data class Success(val data: List<Data>): UIState
}


class CovidViewModel: ViewModel() {

    private val _covidUIState = MutableStateFlow<UIState>(UIState.Loading)
    val covidUIState: StateFlow<UIState> = _covidUIState

    init{
        doTrack()
    }

    private fun doTrack(){
        viewModelScope.launch {
            _covidUIState.value = UIState.Loading
            _covidUIState.value = try{
                val response = CovidApi.retrofitService.getReports()
                UIState.Success(response.data)
            }
            catch (e: IOException) {
                UIState.Error
            } catch (e: HttpException) {
                UIState.Error
            }
        }
    }
}