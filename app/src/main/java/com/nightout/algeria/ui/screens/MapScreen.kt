package com.nightout.algeria.ui.screens

import android.preference.PreferenceManager
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapScreen(
    onNavigateBack: () -> Unit,
    onVenueClick: (String) -> Unit
) {
    val context = LocalContext.current
    var mapView: MapView? by remember { mutableStateOf(null) }
    
    // Initialize osmdroid configuration using the application context
    remember {
        Configuration.getInstance().load(context, PreferenceManager.getDefaultSharedPreferences(context))
        Configuration.getInstance().userAgentValue = context.packageName
        true
    }

    Box(modifier = Modifier.fillMaxSize()) {
        AndroidView(
            factory = { ctx ->
                MapView(ctx).apply {
                    mapView = this
                    setTileSource(TileSourceFactory.MAPNIK)
                    setMultiTouchControls(true)
                    val mapController = controller
                    mapController.setZoom(15.0)
                    val startPoint = GeoPoint(36.7538, 3.0588) // Algiers
                    mapController.setCenter(startPoint)

                    // Mock Venue Markers
                    listOf(
                        Pair(GeoPoint(36.7538, 3.0588), "Neon Club & Lounge"),
                        Pair(GeoPoint(36.7638, 3.0688), "The Rooftop"),
                        Pair(GeoPoint(36.7438, 3.0488), "Jazz Bar Alger")
                    ).forEach { (point, title) ->
                        val marker = Marker(this)
                        marker.position = point
                        marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                        marker.title = title
                        marker.setOnMarkerClickListener { m, _ ->
                            m.showInfoWindow()
                            true
                        }
                        overlays.add(marker)
                    }
                }
            },
            modifier = Modifier.fillMaxSize()
        )
        
        // Search Bar Overlay
        Card(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .fillMaxWidth()
                .padding(top = 48.dp, start = 16.dp, end = 16.dp),
            shape = MaterialTheme.shapes.large,
            elevation = CardDefaults.cardElevation(8.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Row(
                modifier = Modifier.padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onNavigateBack) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                }
                Text("Search area...", color = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.weight(1f))
                IconButton(onClick = { /* Search */ }) {
                    Icon(Icons.Default.Search, contentDescription = "Search")
                }
            }
        }

        // My Location FAB
        FloatingActionButton(
            onClick = {
                // Simulate flying to current location
                val myLocation = GeoPoint(36.7538, 3.0588)
                mapView?.controller?.animateTo(myLocation, 16.0, 1000L)
            },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 32.dp, end = 16.dp),
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        ) {
            Icon(Icons.Default.LocationOn, contentDescription = "My Location")
        }
    }
}
