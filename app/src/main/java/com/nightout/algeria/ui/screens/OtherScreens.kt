package com.nightout.algeria.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.nightout.algeria.ui.viewmodel.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    venueId: String,
    onNavigateBack: () -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    var selectedTab by remember { mutableIntStateOf(0) }
    val tabs = listOf("About", "Reviews", "Photos")
    val listState = rememberLazyListState()

    val isFabExtended by remember { derivedStateOf { listState.firstVisibleItemIndex == 0 } }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            LargeTopAppBar(
                title = { Text("Neon Club & Lounge") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { /* Share */ }) {
                        Icon(Icons.Default.Share, contentDescription = "Share")
                    }
                    IconButton(onClick = { /* Bookmark */ }) {
                        Icon(Icons.Default.FavoriteBorder, contentDescription = "Bookmark")
                    }
                },
                scrollBehavior = scrollBehavior,
                colors = TopAppBarDefaults.largeTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    scrolledContainerColor = MaterialTheme.colorScheme.surface
                )
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                text = { Text("Book Now") },
                icon = { Icon(Icons.Default.DateRange, contentDescription = null) },
                onClick = { /* Book */ },
                expanded = isFabExtended,
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            )
        }
    ) { padding ->
        LazyColumn(
            state = listState,
            contentPadding = padding,
            modifier = Modifier.fillMaxSize()
        ) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                ) {
                    AsyncImage(
                        model = "https://images.unsplash.com/photo-1566417713940-fe7c737a9ef2?q=80&w=600&auto=format&fit=crop",
                        contentDescription = "Hero",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                    Box(modifier = Modifier.fillMaxSize().background(Color.Black.copy(alpha = 0.3f)))
                }
            }
            item {
                TabRow(
                    selectedTabIndex = selectedTab,
                    containerColor = MaterialTheme.colorScheme.surface,
                    contentColor = MaterialTheme.colorScheme.primary
                ) {
                    tabs.forEachIndexed { index, title ->
                        Tab(
                            selected = selectedTab == index,
                            onClick = { selectedTab = index },
                            text = { Text(title) }
                        )
                    }
                }
            }
            item {
                AnimatedContent(targetState = selectedTab, label = "tabs") { index ->
                    when (index) {
                        0 -> Column(Modifier.padding(16.dp)) {
                            Text("Description", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                            Spacer(Modifier.height(8.dp))
                            Text(
                                text = "Experience the best nightlife in Algiers with top DJs, signature cocktails, and an unforgettable Neon atmosphere. Open everyday from 10 PM to 4 AM.",
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Spacer(Modifier.height(16.dp))
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.LocationOn, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                                Spacer(Modifier.width(8.dp))
                                Text("Sidi Yahia, Hydra, Algiers")
                            }
                        }
                        1 -> Column(Modifier.padding(16.dp)) {
                            Text("Ratings", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                            Text("★ 4.8 (120 reviews)", color = MaterialTheme.colorScheme.secondary)
                        }
                        2 -> Column(Modifier.padding(16.dp)) {
                            Text("Photos", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                            Text("Photo gallery coming soon.", color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                    }
                }
            }
            item { Spacer(Modifier.height(80.dp)) } // FAB spacing
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    onNavigateBack: () -> Unit,
    onNavigateToAddVenue: () -> Unit,
    onSignOut: () -> Unit,
    viewModel: AuthViewModel = hiltViewModel()
) {
    var showLogoutDialog by remember { mutableStateOf(false) }

    if (showLogoutDialog) {
        AlertDialog(
            onDismissRequest = { showLogoutDialog = false },
            title = { Text("Logout") },
            text = { Text("Are you sure you want to log out?") },
            confirmButton = {
                TextButton(onClick = { showLogoutDialog = false; onSignOut() }) {
                    Text("Logout", color = MaterialTheme.colorScheme.error)
                }
            },
            dismissButton = {
                TextButton(onClick = { showLogoutDialog = false }) { Text("Cancel") }
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Profile") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) { Icon(Icons.Default.ArrowBack, contentDescription = "Back") }
                },
                actions = { LanguageSelector() }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(padding).verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(24.dp))
            // Avatar
            Box(contentAlignment = Alignment.BottomEnd) {
                Box(
                    modifier = Modifier.size(100.dp).clip(CircleShape).background(MaterialTheme.colorScheme.primary),
                    contentAlignment = Alignment.Center
                ) {
                    Text("A", style = MaterialTheme.typography.displayMedium, color = MaterialTheme.colorScheme.onPrimary)
                }
                IconButton(
                    onClick = { /* Update photo */ },
                    modifier = Modifier.size(32.dp).background(MaterialTheme.colorScheme.secondary, CircleShape)
                ) {
                    Icon(Icons.Default.Edit, contentDescription = "Edit", modifier = Modifier.size(16.dp), tint = MaterialTheme.colorScheme.onSecondary)
                }
            }
            Spacer(Modifier.height(16.dp))
            Text("Amin M.", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
            Text("@amin_14c", color = MaterialTheme.colorScheme.onSurfaceVariant)

            // Stats Row
            Row(
                modifier = Modifier.fillMaxWidth().padding(32.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                StatItem(label = "Friends", value = "128")
                StatItem(label = "Check-ins", value = "45")
                StatItem(label = "Reviews", value = "12")
            }

            // Settings List
            Card(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Column {
                    SettingsItem(icon = Icons.Outlined.FavoriteBorder, title = "Saved Venues", onClick = {})
                    SettingsItem(icon = Icons.Outlined.Notifications, title = "Notifications", onClick = {})
                    SettingsItem(icon = Icons.Outlined.AddCircle, title = "Add a Venue", onClick = onNavigateToAddVenue)
                    SettingsItem(icon = Icons.Outlined.Settings, title = "App Settings", onClick = {})
                }
            }

            Spacer(Modifier.height(24.dp))
            OutlinedButton(
                onClick = { showLogoutDialog = true },
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp).height(50.dp),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.error)
            ) {
                Icon(Icons.Default.ExitToApp, contentDescription = null)
                Spacer(Modifier.width(8.dp))
                Text("Logout")
            }
            Spacer(Modifier.height(24.dp))
        }
    }
}

@Composable
fun StatItem(label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = value, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
        Text(text = label, color = MaterialTheme.colorScheme.onSurfaceVariant, style = MaterialTheme.typography.labelMedium)
    }
}

@Composable
fun SettingsItem(icon: androidx.compose.ui.graphics.vector.ImageVector, title: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().clickable(onClick = onClick).padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
        Spacer(Modifier.width(16.dp))
        Text(title, style = MaterialTheme.typography.titleMedium, modifier = Modifier.weight(1f))
        Icon(Icons.Default.KeyboardArrowRight, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationsScreen(
    onNavigateBack: () -> Unit
) {
    var notifications by remember { 
        mutableStateOf(listOf(
            "New club 'Oasis' just opened near you!",
            "Your venue review got 5 likes.",
            "Reminder: Neon Party starts tonight at 10 PM.",
            "You have a new friend request."
        )) 
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Notifications") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) { Icon(Icons.Default.ArrowBack, contentDescription = "Back") }
                }
            )
        }
    ) { padding ->
        if (notifications.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(Icons.Outlined.Notifications, contentDescription = null, modifier = Modifier.size(64.dp), tint = MaterialTheme.colorScheme.onSurfaceVariant)
                    Spacer(Modifier.height(16.dp))
                    Text("No new notifications", color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
        } else {
            LazyColumn(modifier = Modifier.fillMaxSize().padding(padding)) {
                items(notifications, key = { it }) { notif ->
                    SwipeToDismissBox(
                        state = rememberSwipeToDismissBoxState(
                            confirmValueChange = {
                                if (it == SwipeToDismissBoxValue.EndToStart || it == SwipeToDismissBoxValue.StartToEnd) {
                                    notifications = notifications.filter { item -> item != notif }
                                    true
                                } else false
                            }
                        ),
                        backgroundContent = {
                            Box(
                                modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.error).padding(16.dp),
                                contentAlignment = Alignment.CenterEnd
                            ) {
                                Icon(Icons.Default.Delete, contentDescription = "Delete", tint = Color.White)
                            }
                        }
                    ) {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                        ) {
                            Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                                Box(modifier = Modifier.size(8.dp).clip(CircleShape).background(MaterialTheme.colorScheme.primary))
                                Spacer(Modifier.width(16.dp))
                                Text(notif)
                            }
                            HorizontalDivider()
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddVenueScreen(
    onNavigateBack: () -> Unit
) {
    var venueName by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var selectedType by remember { mutableStateOf("Club") }
    val types = listOf("Club", "Bar", "Lounge", "Restaurant")

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add Venue") },
                navigationIcon = { IconButton(onClick = onNavigateBack) { Icon(Icons.Default.ArrowBack, contentDescription = null) } }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text("Provide details for the new venue. An admin will review your submission before it appears on the map.", color = MaterialTheme.colorScheme.onSurfaceVariant)
            
            OutlinedTextField(
                value = venueName,
                onValueChange = { venueName = it },
                label = { Text("Venue Name") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Description") },
                modifier = Modifier.fillMaxWidth().height(120.dp),
                maxLines = 4
            )

            Text("Venue Type", fontWeight = FontWeight.Bold)
            Row(modifier = Modifier.horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                types.forEach { type ->
                    FilterChip(
                        selected = selectedType == type,
                        onClick = { selectedType = type },
                        label = { Text(type) }
                    )
                }
            }

            Text("Location Pin", fontWeight = FontWeight.Bold)
            Card(
                modifier = Modifier.fillMaxWidth().height(150.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                    Text("Tap to select location on Map", color = MaterialTheme.colorScheme.primary)
                }
            }

            Spacer(Modifier.height(16.dp))
            Button(
                onClick = { /* Submit */ onNavigateBack() },
                modifier = Modifier.fillMaxWidth().height(50.dp)
            ) {
                Text("Submit for Review")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminDashboardScreen(onSignOut: () -> Unit, onVenueClick: (String) -> Unit) {
    var selectedTab by remember { mutableIntStateOf(0) }
    val tabs = listOf("Pending", "Approved", "Users")

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Admin Dashboard") },
                actions = { IconButton(onClick = onSignOut) { Icon(Icons.Default.ExitToApp, contentDescription = null) } }
            )
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).fillMaxSize()) {
            TabRow(selectedTabIndex = selectedTab) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        text = { Text(title) }
                    )
                }
            }

            AnimatedContent(targetState = selectedTab, label = "AdminTabs") { index ->
                when (index) {
                    0 -> LazyColumn(contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                        items(3) { item ->
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                            ) {
                                Column(modifier = Modifier.padding(16.dp)) {
                                    Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                                        Text("Neon Lights Lounge", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
                                        Box(modifier = Modifier.background(MaterialTheme.colorScheme.errorContainer, RoundedCornerShape(4.dp)).padding(horizontal = 6.dp, vertical = 2.dp)) {
                                            Text("NEW", fontSize = androidx.compose.ui.unit.TextUnit(10f, androidx.compose.ui.unit.TextUnitType.Sp), color = MaterialTheme.colorScheme.onErrorContainer)
                                        }
                                    }
                                    Spacer(Modifier.height(4.dp))
                                    Text("Submitted by: user123", color = MaterialTheme.colorScheme.onSurfaceVariant, style = MaterialTheme.typography.bodySmall)
                                    Spacer(Modifier.height(12.dp))
                                    Row(horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()) {
                                        OutlinedButton(onClick = { /* Reject */ }, modifier = Modifier.padding(end = 8.dp)) {
                                            Text("Reject", color = MaterialTheme.colorScheme.error)
                                        }
                                        Button(onClick = { /* Approve */ }) {
                                            Text("Approve")
                                        }
                                    }
                                }
                            }
                        }
                    }
                    1 -> LazyColumn(contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                        items(5) { item ->
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                            ) {
                                Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                                    Box(modifier = Modifier.size(40.dp).background(MaterialTheme.colorScheme.primary, CircleShape))
                                    Spacer(Modifier.width(16.dp))
                                    Column {
                                        Text("Approved Venue #$item", fontWeight = FontWeight.Bold)
                                        Text("Active", color = MaterialTheme.colorScheme.primary, style = MaterialTheme.typography.bodySmall)
                                    }
                                }
                            }
                        }
                    }
                    2 -> Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("User Management Coming Soon")
                    }
                }
            }
        }
    }
}
