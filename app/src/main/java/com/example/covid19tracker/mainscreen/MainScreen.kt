package com.example.covid19tracker.mainscreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.covid19tracker.model.Data

@Composable
fun Covid(modifier: Modifier = Modifier, reports:List<Data>){

    LazyColumn(){
        items(reports){
            report-> Results(modifier,report)

        }
    }
}

@Composable
fun Results(modifier:Modifier=Modifier,report: Data){
    Card(
        modifier.padding(20.dp,0.dp,20.dp,0.dp),
    ){
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Date: ${report.date}", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Confirmed Cases: ${report.confirmed}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Deaths: ${report.deaths}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Recovered: ${report.recovered}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Confirmed Difference: ${report.confirmed_diff}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Deaths Difference: ${report.deaths_diff}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Recovered Difference: ${report.recovered_diff}", style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Region Details", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Region ISO: ${report.region.iso}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Region Name: ${report.region.name}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Province: ${report.region.province}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Latitude: ${report.region.lat}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Longitude: ${report.region.long}", style = MaterialTheme.typography.bodyMedium)

            if (report.region.cities.isNotEmpty()) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = "Cities:", style = MaterialTheme.typography.titleMedium)
                report.region.cities.forEach { city ->
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "City Name: ${city.name}", style = MaterialTheme.typography.bodyMedium)
                    Text(text = "Date: ${city.date}", style = MaterialTheme.typography.bodyMedium)
                    Text(text = "FIPS: ${city.fips}", style = MaterialTheme.typography.bodyMedium)
                    Text(text = "Latitude: ${city.lat}", style = MaterialTheme.typography.bodyMedium)
                    Text(text = "Longitude: ${city.long}", style = MaterialTheme.typography.bodyMedium)
                    Text(text = "Confirmed Cases: ${city.confirmed}", style = MaterialTheme.typography.bodyMedium)
                    Text(text = "Deaths: ${city.deaths}", style = MaterialTheme.typography.bodyMedium)
                    Text(text = "Confirmed Difference: ${city.confirmed_diff}", style = MaterialTheme.typography.bodyMedium)
                    Text(text = "Deaths Difference: ${city.deaths_diff}", style = MaterialTheme.typography.bodyMedium)
                }
            }
        }
    }
}