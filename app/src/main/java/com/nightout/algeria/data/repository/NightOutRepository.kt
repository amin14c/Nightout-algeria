package com.nightout.algeria.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.nightout.algeria.data.model.User
import com.nightout.algeria.data.model.Venue
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NightOutRepository @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) {
    fun getCurrentUser() = auth.currentUser

    suspend fun login(email: String, pass: String): Result<Unit> {
        return try {
            auth.signInWithEmailAndPassword(email, pass).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun register(name: String, email: String, pass: String): Result<Unit> {
        return try {
            val result = auth.createUserWithEmailAndPassword(email, pass).await()
            val userId = result.user?.uid ?: throw Exception("User null")
            
            val role = if (email.lowercase() == "amin14c@gmail.com") "admin" else "user"
            val userProfile = User(id = userId, name = name, email = email, role = role)
            
            firestore.collection("users").document(userId).set(userProfile).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getApprovedVenues(): List<Venue> {
        return try {
            val snapshot = firestore.collection("venues")
                .whereEqualTo("status", "approved")
                .get()
                .await()
            snapshot.toObjects(Venue::class.java)
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun addVenue(venue: Venue): Result<Unit> {
        return try {
            val docRef = firestore.collection("venues").document()
            val newVenue = venue.copy(id = docRef.id, status = "pending")
            docRef.set(newVenue).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun logout() {
        auth.signOut()
    }
}
