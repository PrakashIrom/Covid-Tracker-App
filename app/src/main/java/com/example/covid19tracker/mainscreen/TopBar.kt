package com.example.covid19tracker.mainscreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.covid19tracker.R
import com.example.covid19tracker.model.Data

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(modifier: Modifier,data: List<Data>, navController: NavController){
    Scaffold (topBar = {
        TopAppBar(title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(Icons.Filled.ArrowBack,contentDescription = "navigate back",modifier= Modifier.clickable { navController.navigate("signin") }.size(20.dp))
                Spacer(modifier = Modifier.size(8.dp))
                Text(
                    "Covid Tracker"
                )
                Spacer(modifier = Modifier.size(8.dp))
                Icon(
                    painter = painterResource(R.drawable.covid),
                    contentDescription = "Corona Icon",
                    modifier = Modifier.size(30.dp),
                    tint = Color.Unspecified
                )
            }
        }
        )
    }){innerPadding-> // avoids overlapping
        Covid(modifier.padding(innerPadding),data)
    }
}