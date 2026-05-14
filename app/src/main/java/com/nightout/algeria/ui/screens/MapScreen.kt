package com.nightout.algeria.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun MapScreen(
    onNavigateBack: () -> Unit,
    onVenueClick: (String) -> Unit
) {
    // Algiers coordinates as center
    val algiers = LatLng(36.7538, 3.0588)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(algiers, 12f)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState
        ) {
            Marker(
                state = MarkerState(position = algiers),
                title = "Algiers Center",
                snippet = "Focus on nightlife here"
            )
        }
        
        // Simple back button overlay
        Button(
            onClick = onNavigateBack,
            modifier = Modifier.align(Alignment.TopStart)
        ) {
            Text("Back")
        }
    }
}
