package com.nightout.algeria.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nightout.algeria.data.model.User
import com.nightout.algeria.data.model.Venue
import com.nightout.algeria.data.repository.NightOutRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AdminViewModel @Inject constructor(
    private val repository: NightOutRepository
) : ViewModel() {

    private val _pendingVenues = MutableStateFlow<List<Venue>>(emptyList())
    val pendingVenues = _pendingVenues.asStateFlow()

    private val _approvedVenues = MutableStateFlow<List<Venue>>(emptyList())
    val approvedVenues = _approvedVenues.asStateFlow()

    private val _users = MutableStateFlow<List<User>>(emptyList())
    val users = _users.asStateFlow()

    private val _stats = MutableStateFlow(AdminStats())
    val stats = _stats.asStateFlow()

    init {
        refreshData()
    }

    fun refreshData() {
        viewModelScope.launch {
            _pendingVenues.value = repository.getPendingVenues()
            _approvedVenues.value = repository.getApprovedVenues()
            _users.value = repository.getAllUsers()
            
            _stats.value = AdminStats(
                totalVenues = repository.getVenueCount(),
                totalUsers = repository.getUserCount(),
                pendingApprovals = repository.getVenueCount("pending")
            )
        }
    }

    fun approveVenue(venueId: String) {
        viewModelScope.launch {
            repository.updateVenueStatus(venueId, "approved")
            refreshData()
        }
    }

    fun rejectVenue(venueId: String) {
        viewModelScope.launch {
            repository.updateVenueStatus(venueId, "rejected")
            refreshData()
        }
    }

    fun deleteVenue(venueId: String) {
        viewModelScope.launch {
            repository.deleteVenue(venueId)
            refreshData()
        }
    }
    
    fun suspendVenue(venueId: String) {
        viewModelScope.launch {
            repository.updateVenueStatus(venueId, "suspended")
            refreshData()
        }
    }

    fun promoteUser(userId: String) {
        viewModelScope.launch {
            repository.updateUserRole(userId, "admin")
            refreshData()
        }
    }

    fun blockUser(userId: String) {
        viewModelScope.launch {
            repository.updateUserRole(userId, "blocked")
            refreshData()
        }
    }
}

data class AdminStats(
    val totalVenues: Int = 0,
    val totalUsers: Int = 0,
    val pendingApprovals: Int = 0
)
