package com.app.randomhcht.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.app.randomhcht.model.Car
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable
fun ChangeCarIdScreen(
    onCancelButtonClicked: () -> Unit = {},
    appViewModel: AppViewModel = viewModel(),
    carsList: List<Car>
) {
    Column(modifier = Modifier.padding(8.dp)) {
        Text(text = "List of all cars with their numbers", style = MaterialTheme.typography.titleMedium)
        LazyColumn(modifier = Modifier
            .weight(5f)
            .fillMaxWidth()) {
            items(carsList.sortedBy { it.ID }) {
                Text(text = "${it.ID} - ${stringResource(id = it.name)}")
            }
        }
        CarIdPicker(appViewModel, modifier = Modifier.weight(2f), onCancelButtonClicked = onCancelButtonClicked)
    }
}


@Composable
fun CarIdPicker(
    appViewModel: AppViewModel,
    onCancelButtonClicked: () -> Unit,
    modifier: Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        val values = remember { (1..35).map { it.toString() } }
        val valuesPickerState1 = rememberPickerState()
        val valuesPickerState2 = rememberPickerState()
        var swapped: Boolean by remember { mutableStateOf(false) }
        val coroutineScope = rememberCoroutineScope()

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = "Car 1", modifier = Modifier
                    .padding(top = 16.dp)
                    .fillMaxWidth()
                    .wrapContentWidth())
                Picker(
                    state = valuesPickerState1,
                    items = values,
                    visibleItemsCount = 3,
                    modifier = Modifier.weight(0.3f),
                    textModifier = Modifier.padding(8.dp),
                    textStyle = TextStyle(fontSize = 32.sp)
                )
            }
            Spacer(modifier = Modifier.width(4.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(text = "Car 2", modifier = Modifier
                    .padding(top = 16.dp)
                    .fillMaxWidth()
                    .wrapContentWidth())
                Picker(
                    state = valuesPickerState2,
                    items = values,
                    visibleItemsCount = 3,
                    modifier = Modifier.weight(0.3f),
                    textModifier = Modifier.padding(8.dp),
                    textStyle = TextStyle(fontSize = 32.sp)
                )
            }
            Column(
                Modifier
                    .fillMaxHeight()
                    .wrapContentHeight()
                    .padding(start = 4.dp)
            ) {
                Spacer(modifier = Modifier.weight(0.5f))
                Button(
                    onClick = {
                        appViewModel.swapCarsIds(
                            valuesPickerState1.selectedItem.toInt(),
                            valuesPickerState2.selectedItem.toInt()
                        )
                        coroutineScope.launch {
                            swapped = true
                            delay(2500)
                            swapped=false
                        }
                    }) {
                    Text(text = "SWAP")
                }
                AnimatedVisibility(visible = swapped) {
                    Text(text = "SWAPPED", color = MaterialTheme.colorScheme.primary)
                }
                Spacer(modifier = Modifier.weight(0.5f))
                Button(
                    onClick = onCancelButtonClicked
                ) {
                    Text(text = "DONE")
                }
                Spacer(modifier = Modifier.weight(1f))
            }
        }
    }
}