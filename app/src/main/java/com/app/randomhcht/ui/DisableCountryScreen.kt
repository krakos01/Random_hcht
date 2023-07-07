package com.app.randomhcht.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Preview
@Composable
fun DisableCountriesScreen(
    onDoneButtonClicked: () -> Unit = {}
) {
    Column() {
        Text(text = "Nothing to see here", style = MaterialTheme.typography.bodyMedium)
        Button(onClick = onDoneButtonClicked) {
            Text(text = "OK")
        }
    }
}