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
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.nightout.algeria.ui.viewmodel.AuthViewModel
import com.nightout.algeria.ui.viewmodel.AdminViewModel
import com.nightout.algeria.data.model.Venue
import com.nightout.algeria.ui.viewmodel.AddVenueViewModel
import com.nightout.algeria.ui.viewmodel.SubmissionState
import com.nightout.algeria.ui.viewmodel.VenueDetailViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    venueId: String,
    onNavigateBack: () -> Unit,
    viewModel: VenueDetailViewModel = hiltViewModel()
) {
    LaunchedEffect(venueId) {
        viewModel.loadVenue(venueId)
    }

    val venue by viewModel.venue.collectAsState()
    val reviews by viewModel.reviews.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    var selectedTab by remember { mutableIntStateOf(0) }
    val tabs = listOf("About", "Reviews", "Photos")
    val listState = rememberLazyListState()

    val isFabExtended by remember { derivedStateOf { listState.firstVisibleItemIndex == 0 } }

    var showReviewDialog by remember { mutableStateOf(false) }
    var reviewRating by remember { mutableFloatStateOf(5f) }
    var reviewComment by remember { mutableStateOf("") }

    if (showReviewDialog) {
        AlertDialog(
            onDismissRequest = { showReviewDialog = false },
            title = { Text("Write a Review") },
            text = {
                Column {
                    Text("Rating: ${reviewRating.toInt()} Stars")
                    Slider(
                        value = reviewRating,
                        onValueChange = { reviewRating = it },
                        valueRange = 1f..5f,
                        steps = 3
                    )
                    OutlinedTextField(
                        value = reviewComment,
                        onValueChange = { reviewComment = it },
                        label = { Text("Comment") },
                        modifier = Modifier.fillMaxWidth().height(100.dp)
                    )
                }
            },
            confirmButton = {
                Button(onClick = {
                    viewModel.addReview(venueId, reviewRating, reviewComment)
                    showReviewDialog = false
                    reviewComment = ""
                    reviewRating = 5f
                }) {
                    Text("Submit")
                }
            },
            dismissButton = {
                TextButton(onClick = { showReviewDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            LargeTopAppBar(
                title = { Text(venue?.name ?: "Loading...") },
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
        if (isLoading) {
            Box(modifier = Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else if (venue == null) {
            Box(modifier = Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
                Text("Venue not found")
            }
        } else {
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
                            model = if (venue?.imageUrls?.isNotEmpty() == true) venue!!.imageUrls.first() else "https://images.unsplash.com/photo-1566417713940-fe7c737a9ef2?q=80&w=600&auto=format&fit=crop",
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
                    when (selectedTab) {
                        0 -> Column(Modifier.padding(16.dp)) {
                                Text("About", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                                Spacer(Modifier.height(8.dp))
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(Icons.Default.LocationOn, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                                    Spacer(Modifier.width(8.dp))
                                    Text("${venue?.address}, ${venue?.city}")
                                }
                                Spacer(Modifier.height(8.dp))
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(Icons.Default.Info, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                                    Spacer(Modifier.width(8.dp))
                                    Text("Type: ${venue?.defaultType}")
                                }
                                Spacer(Modifier.height(8.dp))
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(Icons.Default.CheckCircle, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                                    Spacer(Modifier.width(8.dp))
                                    Text("Status: ${if(venue?.isOpen == true) "Open" else "Closed"}")
                                }
                                if(venue?.times?.isNotEmpty() == true) {
                                    Spacer(Modifier.height(8.dp))
                                    Text("Hours: ${venue?.times}")
                                }
                                if(venue?.phoneNumber?.isNotEmpty() == true) {
                                    Spacer(Modifier.height(8.dp))
                                    Text("Phone: ${venue?.phoneNumber}")
                                }
                                Spacer(Modifier.height(16.dp))
                                Text("Features:", fontWeight = FontWeight.Bold)
                                if(venue?.hasVip == true) Text("• VIP Area Available")
                                if(venue?.hasDj == true) Text("• Live DJ")
                                Text("• Price: ${venue?.priceRange}")
                                if(venue?.entryFee?.isNotEmpty() == true) Text("• Entry: ${venue?.entryFee}")
                                if(venue?.dressCode?.isNotEmpty() == true) Text("• Dress Code: ${venue?.dressCode}")
                                if(venue?.ageLimit?.isNotEmpty() == true) Text("• Age: ${venue?.ageLimit}+")
                            }
                        1 -> Column(Modifier.padding(16.dp)) {
                                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                                    Column {
                                        Text("Ratings & Reviews", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                                        Text("★ ${venue?.rating} (${reviews.size} reviews)", color = MaterialTheme.colorScheme.secondary)
                                    }
                                    Button(onClick = { showReviewDialog = true }) {
                                        Text("Write Review")
                                    }
                                }
                                Spacer(Modifier.height(16.dp))
                                if (reviews.isEmpty()) {
                                    Text("No reviews yet. Be the first!", color = MaterialTheme.colorScheme.onSurfaceVariant)
                                } else {
                                    reviews.forEach { review ->
                                        Card(
                                            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                                        ) {
                                            Column(modifier = Modifier.padding(12.dp)) {
                                                Row(verticalAlignment = Alignment.CenterVertically) {
                                                    Text(review.userName, fontWeight = FontWeight.Bold)
                                                    Spacer(Modifier.width(8.dp))
                                                    Row {
                                                        repeat(review.rating.toInt()) {
                                                            Icon(Icons.Default.Star, contentDescription = null, tint = Color(0xFFFFD700), modifier = Modifier.size(16.dp))
                                                        }
                                                    }
                                                }
                                                Spacer(Modifier.height(4.dp))
                                                Text(review.comment)
                                            }
                                        }
                                    }
                                }
                            }
                        2 -> Column(Modifier.padding(16.dp)) {
                                Text("Photos", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                                Text("Photo gallery coming soon.", color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                        else -> {}
                    }
                }
                item { Spacer(Modifier.height(80.dp)) } // FAB spacing
            }
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
                    Card(
                        modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                    ) {
                        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                            Box(modifier = Modifier.size(8.dp).clip(CircleShape).background(MaterialTheme.colorScheme.primary))
                            Spacer(Modifier.width(16.dp))
                            Text(notif)
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
    onNavigateBack: () -> Unit,
    viewModel: AddVenueViewModel = hiltViewModel()
) {
    var venueName by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }
    var times by remember { mutableStateOf("") }
    var priceRange by remember { mutableStateOf("$") }
    var entryFee by remember { mutableStateOf("") }
    var dressCode by remember { mutableStateOf("") }
    var ageLimit by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    
    var hasVip by remember { mutableStateOf(false) }
    var hasDj by remember { mutableStateOf(false) }

    var selectedType by remember { mutableStateOf("Club") }
    val types = listOf("Club", "Bar", "Lounge", "Restaurant", "Rooftop", "Liquor Store")

    val state by viewModel.submissionState.collectAsState()

    LaunchedEffect(state) {
        if (state is SubmissionState.Success) {
            onNavigateBack()
        }
    }

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
            Text("Provide comprehensive details for the new venue. An admin will review your submission before it appears on the map.", color = MaterialTheme.colorScheme.onSurfaceVariant)
            
            OutlinedTextField(
                value = venueName,
                onValueChange = { venueName = it },
                label = { Text("Venue Name") },
                modifier = Modifier.fillMaxWidth()
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

            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                OutlinedTextField(
                    value = address,
                    onValueChange = { address = it },
                    label = { Text("Address") },
                    modifier = Modifier.weight(1f)
                )
                OutlinedTextField(
                    value = city,
                    onValueChange = { city = it },
                    label = { Text("City") },
                    modifier = Modifier.weight(1f)
                )
            }
            
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                OutlinedTextField(
                    value = times,
                    onValueChange = { times = it },
                    label = { Text("Working Hours (e.g., 22:00 - 04:00)") },
                    modifier = Modifier.weight(1f)
                )
                OutlinedTextField(
                    value = phoneNumber,
                    onValueChange = { phoneNumber = it },
                    label = { Text("Phone Number") },
                    modifier = Modifier.weight(1f)
                )
            }

            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                OutlinedTextField(
                    value = priceRange,
                    onValueChange = { priceRange = it },
                    label = { Text("Price Range ($, $$, $$$)") },
                    modifier = Modifier.weight(1f)
                )
                OutlinedTextField(
                    value = entryFee,
                    onValueChange = { entryFee = it },
                    label = { Text("Entry Fee") },
                    modifier = Modifier.weight(1f)
                )
            }
            
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                OutlinedTextField(
                    value = dressCode,
                    onValueChange = { dressCode = it },
                    label = { Text("Dress Code") },
                    modifier = Modifier.weight(1f)
                )
                OutlinedTextField(
                    value = ageLimit,
                    onValueChange = { ageLimit = it },
                    label = { Text("Age Limit") },
                    modifier = Modifier.weight(1f)
                )
            }

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(checked = hasVip, onCheckedChange = { hasVip = it })
                    Text("Has VIP Area")
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(checked = hasDj, onCheckedChange = { hasDj = it })
                    Text("Has DJ")
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
                onClick = {
                    viewModel.submitVenue(
                        Venue(
                            name = venueName,
                            defaultType = selectedType,
                            address = address,
                            city = city,
                            times = times,
                            priceRange = priceRange,
                            entryFee = entryFee,
                            dressCode = dressCode,
                            ageLimit = ageLimit,
                            phoneNumber = phoneNumber,
                            hasVip = hasVip,
                            hasDj = hasDj
                        )
                    )
                },
                modifier = Modifier.fillMaxWidth().height(50.dp),
                enabled = state !is SubmissionState.Loading
            ) {
                if (state is SubmissionState.Loading) {
                    CircularProgressIndicator(modifier = Modifier.size(24.dp), color = MaterialTheme.colorScheme.onPrimary)
                } else {
                    Text("Submit for Review")
                }
            }
            
            if (state is SubmissionState.Error) {
                Text((state as SubmissionState.Error).message, color = MaterialTheme.colorScheme.error)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminDashboardScreen(
    onSignOut: () -> Unit,
    onVenueClick: (String) -> Unit,
    viewModel: AdminViewModel = hiltViewModel()
) {
    val pendingVenues by viewModel.pendingVenues.collectAsState()
    val approvedVenues by viewModel.approvedVenues.collectAsState()
    val users by viewModel.users.collectAsState()
    val stats by viewModel.stats.collectAsState()

    var selectedTab by remember { mutableIntStateOf(0) }
    val tabs = listOf("Overview", "Pending", "Approved", "Users")

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Admin Dashboard") },
                actions = { IconButton(onClick = onSignOut) { Icon(Icons.Default.ExitToApp, contentDescription = null) } }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { /* Broadcast Notification */ }, containerColor = MaterialTheme.colorScheme.primaryContainer) {
                Icon(Icons.Default.Notifications, contentDescription = "Broadcast Notification", tint = MaterialTheme.colorScheme.onPrimaryContainer)
            }
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).fillMaxSize()) {
            ScrollableTabRow(
                selectedTabIndex = selectedTab,
                edgePadding = 0.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        text = { Text(title) }
                    )
                }
            }

            when (selectedTab) {
                0 -> Column(
                    modifier = Modifier.fillMaxSize().padding(16.dp).verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text("Dashboard Overview", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                        Card(modifier = Modifier.weight(1f), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text("Total Venues", color = MaterialTheme.colorScheme.onPrimaryContainer, style = MaterialTheme.typography.labelMedium)
                                Text("${stats.totalVenues}", color = MaterialTheme.colorScheme.onPrimaryContainer, style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
                            }
                        }
                        Card(modifier = Modifier.weight(1f), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text("Total Users", color = MaterialTheme.colorScheme.onSecondaryContainer, style = MaterialTheme.typography.labelMedium)
                                Text("${stats.totalUsers}", color = MaterialTheme.colorScheme.onSecondaryContainer, style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                    Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.tertiaryContainer)) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text("Pending Approvals", color = MaterialTheme.colorScheme.onTertiaryContainer, style = MaterialTheme.typography.labelMedium)
                            Text("${stats.pendingApprovals}", color = MaterialTheme.colorScheme.onTertiaryContainer, style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
                        }
                    }
                }
                1 -> LazyColumn(contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    if (pendingVenues.isEmpty()) {
                        item {
                            Text("No pending venues.", color = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.padding(16.dp))
                        }
                    } else {
                        items(pendingVenues) { venue ->
                            Card(
                                    modifier = Modifier.fillMaxWidth(),
                                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                                    onClick = { onVenueClick(venue.id) }
                                ) {
                                    Column(modifier = Modifier.padding(16.dp)) {
                                        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                                            Text(venue.name, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
                                            Box(modifier = Modifier.background(MaterialTheme.colorScheme.errorContainer, RoundedCornerShape(4.dp)).padding(horizontal = 6.dp, vertical = 2.dp)) {
                                                Text("PENDING", fontSize = 10.sp, color = MaterialTheme.colorScheme.onErrorContainer, fontWeight = FontWeight.Bold)
                                            }
                                        }
                                        Spacer(Modifier.height(8.dp))
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Icon(Icons.Default.LocationOn, contentDescription = null, modifier = Modifier.size(14.dp), tint = MaterialTheme.colorScheme.primary)
                                            Spacer(Modifier.width(4.dp))
                                            Text("Lat: ${venue.latitude}, Lng: ${venue.longitude}", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                        }
                                        Spacer(Modifier.height(4.dp))
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Icon(Icons.Default.Info, contentDescription = null, modifier = Modifier.size(14.dp), tint = MaterialTheme.colorScheme.secondary)
                                            Spacer(Modifier.width(4.dp))
                                            Text("Type: ${venue.defaultType}", color = MaterialTheme.colorScheme.onSurfaceVariant, style = MaterialTheme.typography.bodySmall)
                                        }
                                        Spacer(Modifier.height(16.dp))
                                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                            OutlinedButton(
                                                onClick = { viewModel.rejectVenue(venue.id) }, 
                                                modifier = Modifier.weight(1f),
                                                colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.error)
                                            ) {
                                                Icon(Icons.Default.Clear, contentDescription = null, modifier = Modifier.size(18.dp))
                                                Spacer(Modifier.width(4.dp))
                                                Text("Reject")
                                            }
                                            Button(
                                                onClick = { viewModel.approveVenue(venue.id) }, 
                                                modifier = Modifier.weight(1f),
                                                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                                            ) {
                                                Icon(Icons.Default.Check, contentDescription = null, modifier = Modifier.size(18.dp))
                                                Spacer(Modifier.width(4.dp))
                                                Text("Approve")
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    2 -> Column(modifier = Modifier.fillMaxSize()) {
                        var searchQuery by remember { mutableStateOf("") }
                        OutlinedTextField(
                            value = searchQuery,
                            onValueChange = { searchQuery = it },
                            modifier = Modifier.fillMaxWidth().padding(16.dp),
                            placeholder = { Text("Search approved venues...") },
                            leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
                            singleLine = true,
                            shape = RoundedCornerShape(12.dp)
                        )
                        LazyColumn(contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                            val filteredVenues = approvedVenues.filter { it.name.contains(searchQuery, ignoreCase = true) }
                            if (filteredVenues.isEmpty()) {
                                item { Text("No approved venues found.", modifier = Modifier.padding(16.dp)) }
                            } else {
                                items(filteredVenues) { venue ->
                                    var expanded by remember { mutableStateOf(false) }
                                    Card(
                                        modifier = Modifier.fillMaxWidth(),
                                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                                        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                                        onClick = { onVenueClick(venue.id) }
                                    ) {
                                        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                                            Box(
                                                modifier = Modifier.size(48.dp).background(MaterialTheme.colorScheme.primaryContainer, RoundedCornerShape(8.dp)),
                                                contentAlignment = Alignment.Center
                                            ) {
                                                Icon(Icons.Default.Star, contentDescription = null, tint = MaterialTheme.colorScheme.onPrimaryContainer)
                                            }
                                            Spacer(Modifier.width(16.dp))
                                            Column(modifier = Modifier.weight(1f)) {
                                                Text(venue.name, fontWeight = FontWeight.Bold, maxLines = 1, style = MaterialTheme.typography.titleMedium)
                                                Text("Active • ${venue.rating} ★", color = MaterialTheme.colorScheme.primary, style = MaterialTheme.typography.bodySmall)
                                            }
                                            Box {
                                                IconButton(onClick = { expanded = true }) {
                                                    Icon(Icons.Default.MoreVert, contentDescription = "More actions")
                                                }
                                                DropdownMenu(
                                                    expanded = expanded,
                                                    onDismissRequest = { expanded = false }
                                                ) {
                                                    DropdownMenuItem(
                                                        text = { Text("Edit") },
                                                        onClick = { expanded = false },
                                                        leadingIcon = { Icon(Icons.Default.Edit, contentDescription = null) }
                                                    )
                                                    DropdownMenuItem(
                                                        text = { Text("Suspend") },
                                                        onClick = { 
                                                            expanded = false
                                                            viewModel.suspendVenue(venue.id)
                                                        },
                                                        leadingIcon = { Icon(Icons.Default.Info, contentDescription = null) } 
                                                    )
                                                    DropdownMenuItem(
                                                        text = { Text("Delete", color = MaterialTheme.colorScheme.error) },
                                                        onClick = { 
                                                            expanded = false
                                                            viewModel.deleteVenue(venue.id)
                                                        },
                                                        leadingIcon = { Icon(Icons.Default.Delete, contentDescription = null, tint = MaterialTheme.colorScheme.error) }
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    3 -> Column(modifier = Modifier.fillMaxSize()) {
                        var userSearch by remember { mutableStateOf("") }
                        OutlinedTextField(
                            value = userSearch,
                            onValueChange = { userSearch = it },
                            modifier = Modifier.fillMaxWidth().padding(16.dp),
                            placeholder = { Text("Search users...") },
                            leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
                            singleLine = true,
                            shape = RoundedCornerShape(12.dp)
                        )
                        LazyColumn(contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                            val filteredUsers = users.filter { it.name.contains(userSearch, ignoreCase = true) || it.email.contains(userSearch, ignoreCase = true) }
                            if (filteredUsers.isEmpty()) {
                                item { Text("No users found.", modifier = Modifier.padding(16.dp)) }
                            } else {
                                items(filteredUsers) { user ->
                                    var expanded by remember { mutableStateOf(false) }
                                    Card(
                                        modifier = Modifier.fillMaxWidth(),
                                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                                        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                                    ) {
                                        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                                            Box(
                                                modifier = Modifier.size(48.dp).background(MaterialTheme.colorScheme.primaryContainer, CircleShape),
                                                contentAlignment = Alignment.Center
                                            ) {
                                                Text(user.name.take(1).uppercase(), color = MaterialTheme.colorScheme.onPrimaryContainer, fontWeight = FontWeight.Bold)
                                            }
                                            Spacer(Modifier.width(16.dp))
                                            Column(modifier = Modifier.weight(1f)) {
                                                Text(user.name, fontWeight = FontWeight.Bold, maxLines = 1, style = MaterialTheme.typography.titleMedium)
                                                Text("Role: ${user.role} • ${user.email}", color = MaterialTheme.colorScheme.secondary, maxLines = 1, style = MaterialTheme.typography.bodySmall)
                                            }
                                            Box {
                                                IconButton(onClick = { expanded = true }) {
                                                    Icon(Icons.Default.MoreVert, contentDescription = "More actions")
                                                }
                                                DropdownMenu(
                                                    expanded = expanded,
                                                    onDismissRequest = { expanded = false }
                                                ) {
                                                    DropdownMenuItem(
                                                        text = { Text("Make Admin") },
                                                        onClick = { 
                                                            expanded = false
                                                            viewModel.promoteUser(user.id)
                                                        },
                                                        leadingIcon = { Icon(Icons.Default.Star, contentDescription = null) }
                                                    )
                                                    DropdownMenuItem(
                                                        text = { Text("Block User", color = MaterialTheme.colorScheme.error) },
                                                        onClick = { 
                                                            expanded = false
                                                            viewModel.blockUser(user.id)
                                                        },
                                                        leadingIcon = { Icon(Icons.Default.Clear, contentDescription = null, tint = MaterialTheme.colorScheme.error) }
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    else -> {}
                }
        }
    }
}
