package com.nightout.algeria.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nightout.algeria.data.model.Review
import com.nightout.algeria.data.model.Venue
import com.nightout.algeria.data.repository.NightOutRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VenueDetailViewModel @Inject constructor(
    private val repository: NightOutRepository
) : ViewModel() {

    private val _venue = MutableStateFlow<Venue?>(null)
    val venue = _venue.asStateFlow()

    private val _reviews = MutableStateFlow<List<Review>>(emptyList())
    val reviews = _reviews.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    fun loadVenue(venueId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _venue.value = repository.getVenueById(venueId)
            _reviews.value = repository.getReviewsForVenue(venueId)
            _isLoading.value = false
        }
    }

    fun addReview(venueId: String, rating: Float, comment: String) {
        viewModelScope.launch {
            val user = repository.getCurrentUser()
            if (user != null) {
                val review = Review(
                    venueId = venueId,
                    userId = user.uid,
                    userName = user.displayName ?: user.email ?: "Anonymous",
                    rating = rating,
                    comment = comment
                )
                val result = repository.addReview(review)
                if (result.isSuccess) {
                    _reviews.value = repository.getReviewsForVenue(venueId) // reload
                }
            }
        }
    }
}
