package com.nightout.algeria.data.model

data class User(
    val id: String = "",
    val name: String = "",
    val email: String = "",
    val role: String = "user", // "admin" or "user"
    val favorites: List<String> = emptyList(),
    val language: String = "ar"
)

data class Venue(
    val id: String = "",
    val name: String = "",
    val defaultType: String = "", // "Bar", "Night Club", "Lounge", "Cave", "Rooftop"
    val address: String = "",
    val city: String = "",
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val rating: Float = 0f,
    val imageUrls: List<String> = emptyList(),
    val isOpen: Boolean = true,
    val priceRange: String = "", // "$", "$$", "$$$"
    val times: String = "",
    val entryFee: String = "",
    val dressCode: String = "",
    val ageLimit: String = "",
    val hasVip: Boolean = false,
    val hasDj: Boolean = false,
    val phoneNumber: String = "",
    val reviewIds: List<String> = emptyList(),
    val status: String = "approved" // "approved", "pending", "rejected", "hidden"
)

data class Review(
    val id: String = "",
    val venueId: String = "",
    val userId: String = "",
    val userName: String = "",
    val rating: Float = 5f,
    val comment: String = "",
    val timestamp: Long = System.currentTimeMillis()
)
