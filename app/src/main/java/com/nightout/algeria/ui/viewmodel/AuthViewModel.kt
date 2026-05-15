package com.nightout.algeria.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nightout.algeria.data.repository.NightOutRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: NightOutRepository
) : ViewModel() {

    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> = _authState

    fun login(email: String, pass: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            val result = repository.login(email, pass)
            if (result.isSuccess) {
                _authState.value = AuthState.Success(email == "amin14c@gmail.com")
            } else {
                _authState.value = AuthState.Error(result.exceptionOrNull()?.message ?: "Login Error")
            }
        }
    }

    fun register(name: String, email: String, pass: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            val result = repository.register(name, email, pass)
            if (result.isSuccess) {
                _authState.value = AuthState.Success(email == "amin14c@gmail.com")
            } else {
                _authState.value = AuthState.Error(result.exceptionOrNull()?.message ?: "Registration Error")
            }
        }
    }

    fun checkUserSession(): Boolean {
        return repository.getCurrentUser() != null
    }
}

sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    data class Success(val isAdmin: Boolean) : AuthState()
    data class Error(val message: String) : AuthState()
}
