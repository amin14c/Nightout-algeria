package com.nightout.algeria.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nightout.algeria.data.model.Venue
import com.nightout.algeria.data.repository.NightOutRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: NightOutRepository
) : ViewModel() {

    private val _venues = MutableStateFlow<List<Venue>>(emptyList())
    val venues: StateFlow<List<Venue>> = _venues

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    init {
        loadVenues()
    }

    fun loadVenues() {
        viewModelScope.launch {
            _isLoading.value = true
            _venues.value = repository.getApprovedVenues()
            _isLoading.value = false
        }
    }
}
