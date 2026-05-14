package com.nightout.algeria.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.nightout.algeria.ui.screens.*

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Login : Screen("login")
    object Register : Screen("register")
    object Home : Screen("home")
    object Map : Screen("map")
    object Profile : Screen("profile")
    object AdminDashboard : Screen("admin_dashboard")
    object AddVenue : Screen("add_venue")
    object Detail : Screen("detail/{venueId}") {
        fun createRoute(venueId: String) = "detail/$venueId"
    }
}

@Composable
fun NightOutNavGraph() {
    val navController = rememberNavController()
    
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        composable(Screen.Splash.route) {
            SplashScreen(
                onNavigateToHome = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                },
                onNavigateToLogin = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                }
            )
        }
        
        composable(Screen.Login.route) {
            LoginScreen(
                onNavigateToRegister = { navController.navigate(Screen.Register.route) },
                onNavigateToHome = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                },
                onNavigateToAdmin = {
                    navController.navigate(Screen.AdminDashboard.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                }
            )
        }
        
        composable(Screen.Register.route) {
            RegisterScreen(
                onNavigateBack = { navController.popBackStack() },
                onNavigateToHome = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                }
            )
        }
        
        composable(Screen.Home.route) {
            HomeScreen(
                onVenueClick = { venueId ->
                    navController.navigate(Screen.Detail.createRoute(venueId))
                },
                onNavigateToMap = { navController.navigate(Screen.Map.route) },
                onNavigateToProfile = { navController.navigate(Screen.Profile.route) }
            )
        }
        
        composable(Screen.Map.route) {
            MapScreen(
                onNavigateBack = { navController.popBackStack() },
                onVenueClick = { venueId ->
                    navController.navigate(Screen.Detail.createRoute(venueId))
                }
            )
        }
        
        composable(Screen.Detail.route) { backStackEntry ->
            val venueId = backStackEntry.arguments?.getString("venueId") ?: return@composable
            DetailScreen(
                venueId = venueId,
                onNavigateBack = { navController.popBackStack() }
            )
        }
        
        composable(Screen.Profile.route) {
            ProfileScreen(
                onNavigateBack = { navController.popBackStack() },
                onNavigateToAddVenue = { navController.navigate(Screen.AddVenue.route) },
                onSignOut = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }
        
        composable(Screen.AddVenue.route) {
            AddVenueScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
        
        composable(Screen.AdminDashboard.route) {
            AdminDashboardScreen(
                onSignOut = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(0) { inclusive = true }
                    }
                },
                onVenueClick = { venueId ->
                    navController.navigate(Screen.Detail.createRoute(venueId))
                }
            )
        }
    }
}
