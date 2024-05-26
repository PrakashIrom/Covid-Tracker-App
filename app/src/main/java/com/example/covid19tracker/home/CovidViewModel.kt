package com.example.covid19tracker.home

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.covid19tracker.api.CovidApi
import com.example.covid19tracker.model.Data
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
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
    data class Success(val data: List<Data>): UIState // holds specific data
}


class CovidViewModel: ViewModel() {

    var auth: FirebaseAuth = Firebase.auth
    var email = mutableStateOf("")
    var password =  mutableStateOf("")
    var confirmPassword = mutableStateOf("")
    var showErrorDialog = mutableStateOf(false)
    var showSuccessDialog = mutableStateOf(false)
    var showErrorMessage = mutableStateOf("")

    private val _covidUIState = MutableStateFlow<UIState>(UIState.Loading)
    val covidUIState: StateFlow<UIState> = _covidUIState

    init{
        doTrack() // when creating the instance of the ViewModel class it is called
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

fun login(navController: NavHostController) {
            val auth: FirebaseAuth = com.google.firebase.Firebase.auth

    if (email.value.isEmpty() || password.value.isEmpty()) {
        showErrorMessage.value = "Please enter your email and password."
        showErrorDialog.value = true
        return
    }
    auth.signInWithEmailAndPassword(email.value, password.value)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        navController.navigate("home")
                    } else {
                        showErrorMessage.value = "Login failed. Please try again."
                        showErrorDialog.value = true
                    }
                }.addOnFailureListener{
                    exception ->
                    when (exception) {
                        is FirebaseAuthInvalidCredentialsException -> {
                            showErrorMessage.value = "Invalid password. Please try again."
                            showErrorDialog.value = true
                        }
                        is FirebaseAuthInvalidUserException -> {
                            showErrorMessage.value = "No account found with this email. Please sign up first."
                            showErrorDialog.value = true
                        }
                        is FirebaseNetworkException -> {
                            showErrorMessage.value = "Network error. Please check your connection and try again."
                            showErrorDialog.value = true
                        }
                        else -> {
                            showErrorMessage.value = "An unexpected error occurred: ${exception.message}"
                            showErrorDialog.value = true
                        }
                    }

                }
    }

    fun createAccount(){

        if (email.value.isEmpty() || password.value.isEmpty()) {
            showErrorMessage.value = "Please enter your email and password."
            showErrorDialog.value = true
            return
        }
        else if(password.value!=confirmPassword.value){
            showErrorMessage.value = "Password does not match!"
            showErrorDialog.value = true
            return
        }

        auth.createUserWithEmailAndPassword(email.value, password.value)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    showSuccessDialog.value = true

                } else {
                    showErrorMessage.value="Sign In failed try again!"
                    showErrorDialog.value = true
                }
            }.addOnFailureListener { exception ->
                if (exception is FirebaseAuthUserCollisionException) {
                    showErrorMessage.value = "The email address is already in use by another account."
                    showErrorDialog.value = true
                } else {
                    showErrorMessage.value = "An error occurred: ${exception.message}"
                    showErrorDialog.value = true
                }
            }
    }
}