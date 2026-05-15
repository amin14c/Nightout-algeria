package com.nightout.algeria.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nightout.algeria.ui.viewmodel.AuthViewModel

@Composable
fun DetailScreen(
    venueId: String,
    onNavigateBack: () -> Unit
) {
    Scaffold(
        topBar = {
            // Add TopAppBar
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "Detail Screen for Venue ID: $venueId")
            Button(onClick = onNavigateBack, modifier = Modifier.padding(top = 16.dp)) {
                Text("Go Back")
            }
        }
    }
}

@Composable
fun ProfileScreen(
    onNavigateBack: () -> Unit,
    onNavigateToAddVenue: () -> Unit,
    onSignOut: () -> Unit,
    viewModel: AuthViewModel = hiltViewModel()
) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        Text("Profile Screen", style = MaterialTheme.typography.titleLarge)
        Button(onClick = onNavigateToAddVenue, modifier = Modifier.padding(top = 16.dp)) {
            Text("Add a Venue")
        }
        Button(
            onClick = {
                // Here we would call repository direct logout, but for now we trust the top level
                onSignOut()
            },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Logout")
        }
        Button(onClick = onNavigateBack, modifier = Modifier.padding(top = 16.dp)) {
            Text("Go Back")
        }
    }
}

@Composable
fun AddVenueScreen(
    onNavigateBack: () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        Text("Add Venue (Pending Admin Approval)", style = MaterialTheme.typography.titleLarge)
        Button(onClick = onNavigateBack, modifier = Modifier.padding(top = 16.dp)) {
            Text("Submit & Go Back")
        }
    }
}

@Composable
fun AdminDashboardScreen(
    onSignOut: () -> Unit,
    onVenueClick: (String) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        Text("Admin Dashboard (for amin14c@gmail.com)", style = MaterialTheme.typography.titleLarge)
        Button(onClick = onSignOut, modifier = Modifier.padding(top = 16.dp)) {
            Text("Logout")
        }
    }
}
