package com.example.midterm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.midterm.domain.model.Hobby
import com.example.midterm.presentation.detail.DetailViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class SecondActivity : ComponentActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Получаем ID хобби из Intent
        val hobbyId = intent.getIntExtra("hobbyId", -1)
        
        // Используем Koin для получения ViewModel с параметром
        val viewModel: DetailViewModel by viewModel { parametersOf(hobbyId) }

        setContent {
            val state by viewModel.state.collectAsState()

            DetailScreen(
                isLoading = state.isLoading,
                hobby = state.hobby,
                error = state.error,
                onBack = { finish() }
            )
        }
    }
}

@Composable
fun DetailScreen(
    isLoading: Boolean,
    hobby: Hobby?,
    error: String?,
    onBack: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.back),
            contentDescription = "Фон",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            when {
                isLoading -> {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .background(Color.White.copy(alpha = 0.7f))
                            .padding(16.dp)
                    )
                }
                error != null -> {
                    Box(
                        modifier = Modifier
                            .background(Color.White.copy(alpha = 0.7f))
                            .padding(16.dp)
                    ) {
                        Text(
                            text = "Ошибка: $error",
                            color = MaterialTheme.colorScheme.error,
                            textAlign = TextAlign.Center
                        )
                    }
                }
                hobby != null -> {
                    Box(
                        modifier = Modifier
                            .background(Color.White.copy(alpha = 0.7f))
                            .padding(16.dp)
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = hobby.title,
                                fontSize = 24.sp,
                                textAlign = TextAlign.Center
                            )
                            Spacer(modifier = Modifier.height(16.dp))

                            hobby.imageResId?.let { resId ->
                                Image(
                                    painter = painterResource(id = resId),
                                    contentDescription = hobby.title,
                                    modifier = Modifier
                                        .size(150.dp)
                                        .align(Alignment.CenterHorizontally)
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                            }

                            Text(
                                text = hobby.description,
                                textAlign = TextAlign.Center
                            )
                            
                            Spacer(modifier = Modifier.height(8.dp))
                            
                            Text(
                                text = "Приоритет: ${hobby.priority}",
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = onBack) {
                Text(text = "Назад")
            }
        }
    }
}
