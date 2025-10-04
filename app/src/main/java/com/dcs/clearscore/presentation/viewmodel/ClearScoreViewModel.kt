package com.dcs.clearscore.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dcs.clearscore.common.UIState
import com.dcs.core.domain.DomainResult
import com.dcs.core.domain.model.ClearScoreDomainModel
import com.dcs.core.domain.usecase.ClearScoreUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ClearScoreViewModel @Inject constructor(
    private val clearScoreUsecase: ClearScoreUsecase,
    private val autoLoad: Boolean = true
) : ViewModel() {

    private val _creditScore = MutableStateFlow<UIState<ClearScoreDomainModel>>(UIState.Initial)
    val creditScore = _creditScore.asStateFlow()

    init {
        if (autoLoad) {
            getCreditScore()
        }
    }

    fun getCreditScore() {
        _creditScore.value = UIState.Loading
        viewModelScope.launch {
            when (val result = clearScoreUsecase.getCreditScore()) {
                is DomainResult.Success -> {
                    _creditScore.value = UIState.Success(result.data)
                }
                is DomainResult.Error -> {
                    _creditScore.value = UIState.Error(result.exception.message ?: "Failed to fetch Credit Score")
                }
            }
        }
    }
}