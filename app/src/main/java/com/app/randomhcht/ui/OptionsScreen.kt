package com.app.randomhcht.ui

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.app.randomhcht.data.Datasource
import com.app.randomhcht.data.Datasource.carOptions
import com.app.randomhcht.data.Datasource.setNumberOfRaces
import com.app.randomhcht.data.Datasource.trackOptions
import com.example.randomhcht.R


@Preview(showSystemUi = true)
@Composable
fun OptionsPreview() {
    OptionsScreen()
}

@Composable
fun OptionsScreen(
    onDoneButtonClicked: () -> Unit = {}
) {
    val context = LocalContext.current
    var number by remember { mutableStateOf("") }
    var selectedTrackValue by rememberSaveable { mutableStateOf("") }
    var selectedCarValue by rememberSaveable { mutableStateOf("") }

    Column(modifier = Modifier.padding(5.dp)) {
        Text(
            text = stringResource(id = R.string.Tracks),
            style = MaterialTheme.typography.headlineSmall
        )

        trackOptions.forEach { item ->
            ListItem(
                headlineContent = { Text(text = stringResource(id = item)) },
                trailingContent = {
                    // todo: Dokończyć
                    if (item != trackOptions.last()) {
                        TextField(
                            value = number,
                            onValueChange = { value ->
                                if (value.length <= 2) {
                                    number = value.filter { it.isDigit() }
                                    setNumberOfRaces(value.toInt())
                                }
                            },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            singleLine = true,
                            modifier = Modifier.width(50.dp)
                        )
                    }
                    else {
                        // todo: Show dialog with all countries to select
                    }
                }
            )
        }
        Divider()
        Spacer(Modifier.height(5.dp))
        Text(
            text = stringResource(id = R.string.Cars),
            style = MaterialTheme.typography.headlineSmall
        )
        carOptions.forEach { item ->
            ListItem(
                headlineContent = { Text(text = stringResource(id = item.first)) },
                trailingContent = {
                    val checkedState = remember { mutableStateOf(false) }
                    Checkbox(
                        checked = checkedState.value,
                        onCheckedChange = {
                            selectedCarValue = context.resources.getString(item.first)
                            checkedState.value = it
                        }
                ) },
            )
        }
        Divider()
        Spacer(Modifier.height(5.dp))

        Text(
            text = stringResource(id = R.string.Races),
            style = MaterialTheme.typography.headlineSmall
        )
        ListItem(
            headlineContent = { Text(text = stringResource(id = R.string.NumberOfRaces)) },
            trailingContent = { TextField(
                value = number,
                onValueChange = { value ->
                    if (value.length <= 2) {
                        number = value.filter { it.isDigit() }
                        setNumberOfRaces(value.toInt())
                    }
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
                modifier = Modifier.width(50.dp)
            ) },
        )
    }
}


@Composable
fun ListItem(withCheckbox: Boolean, withTextField: Boolean) {

}

