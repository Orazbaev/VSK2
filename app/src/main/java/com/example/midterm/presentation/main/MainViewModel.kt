package com.example.midterm.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.midterm.domain.model.Hobby
import com.example.midterm.domain.usecase.GetAllHobbiesUseCase
import com.example.midterm.domain.usecase.InsertInitialHobbiesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class MainViewModel(
    private val getAllHobbiesUseCase: GetAllHobbiesUseCase,
    private val insertInitialHobbiesUseCase: InsertInitialHobbiesUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(MainState())
    val state: StateFlow<MainState> = _state.asStateFlow()

    init {
        loadHobbies()
    }

    private fun loadHobbies() {
        viewModelScope.launch {
            getAllHobbiesUseCase()
                .catch { e ->
                    _state.value = _state.value.copy(
                        isLoading = false,
                        error = e.message ?: "Неизвестная ошибка"
                    )
                }
                .collect { hobbies ->
                    if (hobbies.isEmpty()) {
                        insertInitialHobbies()
                    } else {
                        _state.value = _state.value.copy(
                            isLoading = false,
                            hobbies = hobbies
                        )
                    }
                }
        }
    }

    private fun insertInitialHobbies() {
        viewModelScope.launch {
            try {
                insertInitialHobbiesUseCase()
                // После вставки данных, они будут автоматически загружены через Flow
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = e.message ?: "Ошибка при добавлении начальных данных"
                )
            }
        }
    }
}

data class MainState(
    val isLoading: Boolean = true,
    val hobbies: List<Hobby> = emptyList(),
    val error: String? = null
)
