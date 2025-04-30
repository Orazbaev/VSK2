package com.example.midterm

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.midterm.domain.model.Hobby
import com.example.midterm.presentation.main.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {
    // Используем Koin для получения ViewModel
    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val state by viewModel.state.collectAsState()

            MainScreen(
                isLoading = state.isLoading,
                hobbies = state.hobbies,
                error = state.error,
                onHobbyClick = { hobby ->
                    val intent = Intent(this, SecondActivity::class.java).apply {
                        putExtra("hobbyId", hobby.id)
                    }
                    startActivity(intent)
                }
            )
        }
    }
}

@Composable
fun MainScreen(
    isLoading: Boolean,
    hobbies: List<Hobby>,
    error: String?,
    onHobbyClick: (Hobby) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Мои хобби", fontSize = 24.sp)
        Spacer(modifier = Modifier.height(16.dp))

        when {
            isLoading -> {
                CircularProgressIndicator()
            }
            error != null -> {
                Text(text = "Ошибка: $error", color = MaterialTheme.colorScheme.error)
            }
            hobbies.isEmpty() -> {
                Text(text = "Нет данных о хобби")
            }
            else -> {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(hobbies) { hobby ->
                        HobbyItem(hobby = hobby, onClick = { onHobbyClick(hobby) })
                    }
                }
            }
        }
    }
}

@Composable
fun HobbyItem(hobby: Hobby, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = hobby.title,
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.height(8.dp))

            hobby.imageResId?.let { resId ->
                Image(
                    painter = painterResource(id = resId),
                    contentDescription = hobby.title,
                    modifier = Modifier
                        .size(100.dp)
                        .align(Alignment.CenterHorizontally)
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            Text(
                text = hobby.description,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 2
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Приоритет: ${hobby.priority}",
                style = MaterialTheme.typography.bodySmall
            )

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = onClick,
                modifier = Modifier.align(Alignment.End)
            ) {
                Text(text = "Подробнее")
            }
        }
    }
}
