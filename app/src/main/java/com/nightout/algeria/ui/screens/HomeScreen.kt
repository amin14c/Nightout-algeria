package com.nightout.algeria.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.nightout.algeria.data.model.Venue
import com.nightout.algeria.ui.theme.NeonGold
import com.nightout.algeria.ui.theme.NeonPurple
import com.nightout.algeria.ui.theme.SurfaceDark

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onVenueClick: (String) -> Unit,
    onNavigateToMap: () -> Unit,
    onNavigateToProfile: () -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    val filters = listOf("Tous", "Bar", "Night Club", "Lounge", "Cave", "Rooftop", "Ouvert")
    var selectedFilter by remember { mutableStateOf(filters.first()) }

    // Mock Data
    val venues = listOf(
        Venue(id = "1", name = "Neon Club", defaultType = "Night Club", rating = 4.8f, priceRange = "$$$", isOpen = true),
        Venue(id = "2", name = "Sky Lounge", defaultType = "Rooftop", rating = 4.5f, priceRange = "$$", isOpen = true),
        Venue(id = "3", name = "Cave Rouge", defaultType = "Cave", rating = 4.2f, priceRange = "$", isOpen = false)
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Night Out", color = NeonPurple, fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background),
                actions = {
                    IconButton(onClick = onNavigateToMap) {
                        Icon(Icons.Default.Map, contentDescription = "Map", tint = NeonGold)
                    }
                    IconButton(onClick = onNavigateToProfile) {
                        Icon(Icons.Default.Person, contentDescription = "Profile", tint = MaterialTheme.colorScheme.onBackground)
                    }
                }
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->
        Column(modifier = Modifier.padding(padding).fillMaxSize()) {
            
            // Search Bar
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                placeholder = { Text("Search venues...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                shape = RoundedCornerShape(16.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    containerColor = SurfaceDark,
                    unfocusedBorderColor = Color.Transparent,
                    focusedBorderColor = NeonPurple
                )
            )

            // Filters
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                items(filters) { filter ->
                    FilterChip(
                        selected = selectedFilter == filter,
                        onClick = { selectedFilter = filter },
                        label = { Text(filter) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = NeonPurple.copy(alpha = 0.2f),
                            selectedLabelColor = NeonPurple
                        )
                    )
                }
            }

            // Venues List
            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(venues) { venue ->
                    VenueCard(venue = venue, onClick = { onVenueClick(venue.id) })
                }
            }
        }
    }
}

@Composable
fun VenueCard(venue: Venue, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(16.dp),
                spotColor = NeonPurple.copy(alpha = 0.5f) // Neon shadow
            )
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceDark)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            // Placeholder for Image
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .background(Color.DarkGray)
            )
            
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = venue.name,
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Text(
                        text = "★ ${venue.rating}",
                        color = NeonGold,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = venue.defaultType, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Text(text = venue.priceRange, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = if (venue.isOpen) "OUVERT" else "FERMÉ",
                    color = if (venue.isOpen) NeonPurple else MaterialTheme.colorScheme.error,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp
                )
            }
        }
    }
}
