package com.nightout.algeria.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nightout.algeria.data.model.Venue
import com.nightout.algeria.data.repository.NightOutRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddVenueViewModel @Inject constructor(
    private val repository: NightOutRepository
) : ViewModel() {

    private val _submissionState = MutableStateFlow<SubmissionState>(SubmissionState.Idle)
    val submissionState = _submissionState.asStateFlow()

    fun submitVenue(venue: Venue) {
        viewModelScope.launch {
            _submissionState.value = SubmissionState.Loading
            val result = repository.addVenue(venue.copy(status = "pending"))
            if (result.isSuccess) {
                _submissionState.value = SubmissionState.Success
            } else {
                _submissionState.value = SubmissionState.Error(result.exceptionOrNull()?.message ?: "Unknown error")
            }
        }
    }

    fun resetState() {
        _submissionState.value = SubmissionState.Idle
    }
}

sealed class SubmissionState {
    object Idle : SubmissionState()
    object Loading : SubmissionState()
    object Success : SubmissionState()
    data class Error(val message: String) : SubmissionState()
}
