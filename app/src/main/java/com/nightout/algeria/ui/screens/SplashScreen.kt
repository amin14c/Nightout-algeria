package com.nightout.algeria.ui.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nightout.algeria.ui.theme.NeonGold
import com.nightout.algeria.ui.theme.NeonPurple
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    onNavigateToHome: () -> Unit,
    onNavigateToLogin: () -> Unit
) {
    var startAnimation by remember { mutableStateOf(false) }
    val alphaAnim = animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(durationMillis = 1500)
    )

    LaunchedEffect(key1 = true) {
        startAnimation = true
        delay(2000L)
        // Check Auth State here (simulate for now)
        onNavigateToLogin()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.alpha(alphaAnim.value)
        ) {
            // Placeholder for Martini Glass Icon - using a generic shape/text
            Text(
                text = "🍸",
                fontSize = 80.sp,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Text(
                text = "NIGHT OUT",
                color = NeonPurple,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 2.sp
            )
            Text(
                text = "ALGERIA",
                color = NeonGold,
                fontSize = 24.sp,
                fontWeight = FontWeight.Light,
                letterSpacing = 4.sp
            )
        }
    }
}
