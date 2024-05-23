package com.example.covid19tracker.home

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.covid19tracker.api.APIService
import com.example.covid19tracker.api.CovidApi
import com.example.covid19tracker.model.Data
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
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

    var auth: FirebaseAuth = Firebase.auth
    var email = mutableStateOf("")
    var password =  mutableStateOf("")
    var confirmPassword = mutableStateOf("")
    var showErrorDialog = mutableStateOf(false)
    var showSuccessDialog = mutableStateOf(false)

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

    fun createAccount(navController: NavHostController){
        if (password.value == confirmPassword.value && (email.value.isNotEmpty() && email.value.isNotEmpty())) {
            auth.createUserWithEmailAndPassword(email.value, password.value)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        showSuccessDialog.value = true

                    } else {
                        showErrorDialog.value = true
                        navController.navigate("signin")
                    }
                }
        } else {
            showErrorDialog.value = true
        }
    }

    fun login(navController: NavHostController) {
        if (email.value.isEmpty() || password.value.isEmpty()) {
            showErrorDialog.value = true
        } else {
            val auth: FirebaseAuth = com.google.firebase.Firebase.auth
            auth.signInWithEmailAndPassword(email.value, password.value)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        navController.navigate("home")
                    } else {
                        showErrorDialog.value = true
                    }
                }
        }
    }
}