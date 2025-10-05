package com.dcs.clearscore

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.dcs.clearscore.common.UIState
import com.dcs.clearscore.presentation.composable.CreditScoreCircle
import com.dcs.clearscore.presentation.viewmodel.ClearScoreViewModel
import com.dcs.clearscore.ui.theme.ClearscoreTheme
import com.dcs.core.domain.model.ClearScoreDomainModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ClearscoreTheme {
                ClearScoreScreen()
            }
        }
    }
}

@Composable
fun ClearScoreScreen(
    viewModel: ClearScoreViewModel = hiltViewModel()
) {
    val state by viewModel.creditScore.collectAsState()
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Box(
            modifier = Modifier.fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            when(state) {
                is UIState.Initial -> {
                    Text("Ready to load Credit Score")
                }
                is UIState.Loading -> {
                    CircularProgressIndicator()
                }
                is UIState.Error -> {
                    val errorMessage = (state as UIState.Error).message
                    Text(
                        text = errorMessage,
                        color = MaterialTheme.colorScheme.error
                    )
                }
                is UIState.Success -> {
                    val creditScore = (state as UIState.Success<ClearScoreDomainModel>).data
                    CreditScoreCircle(
                        score = creditScore.creditReportInfo.score,
                        maxScore = creditScore.creditReportInfo.maxScoreValue
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ClearscoreTheme {

    }
}