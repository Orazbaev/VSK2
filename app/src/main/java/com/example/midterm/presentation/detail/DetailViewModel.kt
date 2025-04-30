package com.example.midterm.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.midterm.domain.model.Hobby
import com.example.midterm.domain.usecase.GetHobbyByIdUseCase
import com.example.midterm.widget.WidgetRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DetailViewModel(
    private val getHobbyByIdUseCase: GetHobbyByIdUseCase,
    private val widgetRepository: WidgetRepository,
    private val hobbyId: Int
) : ViewModel() {

    private val _state = MutableStateFlow(DetailState())
    val state: StateFlow<DetailState> = _state.asStateFlow()

    init {
        loadHobbyDetails(hobbyId)
    }

    fun loadHobbyDetails(hobbyId: Int) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            try {
                val hobby = getHobbyByIdUseCase(hobbyId)
                if (hobby != null) {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        hobby = hobby
                    )
                    // Сохраняем последнее просмотренное хобби для виджета
                    widgetRepository.saveLastViewedHobby(hobby.title)
                } else {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        error = "Хобби не найдено"
                    )
                }
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = e.message ?: "Неизвестная ошибка"
                )
            }
        }
    }
}

data class DetailState(
    val isLoading: Boolean = true,
    val hobby: Hobby? = null,
    val error: String? = null
)
