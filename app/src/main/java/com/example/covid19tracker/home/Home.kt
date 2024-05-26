package com.example.covid19tracker.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.covid19tracker.R
import com.example.covid19tracker.mainscreen.TopBar
import com.example.covid19tracker.model.Data

@Composable
fun HomeScreen(modifier: Modifier = Modifier.fillMaxSize(), viewModel: CovidViewModel,navController: NavController){

    val covidUIState by viewModel.covidUIState.collectAsState()

    when (val current =  covidUIState) //smart cast is not possible becoz covidUIState has a custom setter
    {
        is UIState.Loading -> Loading()
        is UIState.Error -> Error()
        is UIState.Success -> {
           Success(modifier,current.data,navController)
        }
    }

}

@Composable
fun Loading(modifier: Modifier = Modifier){
    Image(painter = painterResource(id = R.drawable.loading_img),
        contentDescription = "Loading Icon",
        modifier = modifier.size(200.dp)
    )
}

@Composable
fun Error(modifier: Modifier  = Modifier){
    Image(painter= painterResource(id = R.drawable.ic_connection_error),
        contentDescription = "Error Icon",
        modifier = modifier.size(200.dp)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Success(modifier: Modifier = Modifier,data: List<Data>,navController: NavController){
    TopBar(modifier,data,navController)
}