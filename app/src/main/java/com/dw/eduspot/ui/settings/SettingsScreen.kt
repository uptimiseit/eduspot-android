package com.dw.eduspot.ui.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.dw.eduspot.domain.model.ThemeMode
import com.dw.eduspot.ui.theme.ThemeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onBack: () -> Unit,
    viewModel: ThemeViewModel = hiltViewModel()
) {
    val themeMode by viewModel.themeMode.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Settings") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxWidth()
        ) {

            Text(
                text = "Theme",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(12.dp))

            ThemeOption(
                title = "Light",
                selected = themeMode == ThemeMode.LIGHT,
                onClick = { viewModel.updateTheme(ThemeMode.LIGHT) }
            )

            ThemeOption(
                title = "Dark",
                selected = themeMode == ThemeMode.DARK,
                onClick = { viewModel.updateTheme(ThemeMode.DARK) }
            )

            ThemeOption(
                title = "System default",
                selected = themeMode == ThemeMode.SYSTEM,
                onClick = { viewModel.updateTheme(ThemeMode.SYSTEM) }
            )
        }
    }
}


@Composable
private fun ThemeOption(
    title: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = title)

        RadioButton(
            selected = selected,
            onClick = onClick
        )
    }
}