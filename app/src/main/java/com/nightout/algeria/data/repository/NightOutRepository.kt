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

    suspend fun resetPassword(email: String): Result<Unit> {
        return try {
            auth.sendPasswordResetEmail(email).await()
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

    suspend fun getPendingVenues(): List<Venue> {
        return try {
            val snapshot = firestore.collection("venues")
                .whereEqualTo("status", "pending")
                .get()
                .await()
            snapshot.toObjects(Venue::class.java)
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun updateVenueStatus(venueId: String, status: String): Result<Unit> {
        return try {
            firestore.collection("venues").document(venueId).update("status", status).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun deleteVenue(venueId: String): Result<Unit> {
        return try {
            firestore.collection("venues").document(venueId).delete().await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getAllUsers(): List<User> {
        return try {
            val snapshot = firestore.collection("users").get().await()
            snapshot.toObjects(User::class.java)
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun updateUserRole(userId: String, role: String): Result<Unit> {
        return try {
            firestore.collection("users").document(userId).update("role", role).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getVenueCount(status: String? = null): Int {
        return try {
            val query = if (status != null) {
                firestore.collection("venues").whereEqualTo("status", status)
            } else {
                firestore.collection("venues")
            }
            query.get().await().size()
        } catch (e: Exception) {
            0
        }
    }

    suspend fun getUserCount(): Int {
        return try {
            firestore.collection("users").get().await().size()
        } catch (e: Exception) {
            0
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
