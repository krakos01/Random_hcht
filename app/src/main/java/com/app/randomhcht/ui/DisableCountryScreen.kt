package com.app.randomhcht.ui

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.app.randomhcht.model.Country


@Preview(showSystemUi = true)
@Composable
fun DisableCountriesScreen() {
    OptionsScreen()
}

@Composable
fun DisableCountriesScreen(
    onDoneButtonClicked: () -> Unit = {}
) {
    for (ct in Country.values()) {
        Text(text = ct.name)
    }
    Spacer(modifier = Modifier.height(10.dp))
    
    Button(onClick = onDoneButtonClicked) {
        Text(text = "I'm done")
    }
}