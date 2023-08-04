package com.app.randomhcht.ui.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.app.randomhcht.model.Car
import com.app.randomhcht.ui.AppViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun ChangeCarIdScreen(
    onDoneButtonClicked: () -> Unit,
    appViewModel: AppViewModel = viewModel(),
    carsList: SnapshotStateList<Car>
) {
    val selectedCars: MutableList<Car> = remember { mutableStateListOf() }
        
    Column(modifier = Modifier.padding(8.dp)) {
        CarsCardsAfterChange(
            selectedCars = selectedCars,
            modifier = Modifier.weight(2f)
        )
        CarIdCardList(
            modifier = Modifier.weight(7f),
            myCars = carsList,
            selectedCars = selectedCars,
            appViewModel = appViewModel,
            onDoneButtonClicked = onDoneButtonClicked
        )
    }
}

@Composable
fun CarIdCard(
    modifier: Modifier = Modifier,
    car: Car,
    selectedCars: MutableList<Car>,
    onClick: (Car) -> Unit = {}
) {

    var isClicked by remember { mutableStateOf(false) }
    val borderModifier = if (isClicked) {
        Modifier.border(2.dp, MaterialTheme.colorScheme.tertiary)
    }
    else {Modifier}
    Card(
        shape = RoundedCornerShape(4.dp),
        modifier = borderModifier
            .padding(4.dp)
            .clickable {
                isClicked = !isClicked
                onClick(car)
            }) {
        Row(horizontalArrangement = Arrangement.spacedBy(6.dp), modifier = Modifier.padding(2.dp)) {
            Text(
                text = car.ID.toString(),
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = stringResource(id = car.name),
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}


@Composable
fun CarIdCardList(
    modifier: Modifier = Modifier,
    myCars: SnapshotStateList<Car>,
    selectedCars: MutableList<Car>,
    appViewModel: AppViewModel,
    onDoneButtonClicked: () -> Unit
) {
    var swapped: Boolean by remember { mutableStateOf(false) }
    var buttonText: String by remember { mutableStateOf("SWAP") }
    val openDialog = remember { mutableStateOf(false)  }
    val hideDialog = { openDialog.value = false}
    val coroutineScope = rememberCoroutineScope()

    Text(
        text = "List of all cars",
        style = MaterialTheme.typography.titleMedium,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentWidth()
    )

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier
    ) {
        items(myCars.sortedBy { it.ID }) {
            CarIdCard(
                modifier = modifier,
                car = it,
                selectedCars = selectedCars,
                onClick = { car ->
                    if (selectedCars.contains(car)) {
                        selectedCars.remove(car)
                    }
                    else {// if (selectedCars.size<2) {
                        selectedCars.add(car)
                    }
                }
            )
        }
    }

    Column(
        modifier = Modifier.padding(4.dp)
    ) {
        Button(
            modifier = Modifier.fillMaxWidth(),
            shape = CardDefaults.shape,
            onClick = {
                if (selectedCars.size == 2) {
                    appViewModel.swapCarsIds(
                        selectedCars[0].ID,
                        selectedCars[1].ID
                    )
                    coroutineScope.launch {
                        swapped = true
                        buttonText = "SWAPPED"
                        delay(2500)
                        buttonText = "SWAP"
                    }
                }
                else openDialog.value=true
            }) {
            Text(text = buttonText)
        }
        Button(
            modifier = Modifier.fillMaxWidth(),
            shape = CardDefaults.shape,
            onClick = onDoneButtonClicked
        ) {
            Text(text = "DONE")
        }
    }
    if (openDialog.value) SwapErrorAlertDialog(hideDialog)
}


@Composable
fun CarsCardsAfterChange(
    modifier: Modifier,
    selectedCars: MutableList<Car>,
) {
    Column(modifier = modifier) {
        Text(
            text = "After swapping",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth()
        )
        if (selectedCars.size!=2) {
            Text(
                text = "Select two cars",
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentWidth()
            )
        }
        else {
            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                CarCard(modifier.weight(1f), car = selectedCars[0].copy(ID = selectedCars[1].ID))
                CarCard(modifier.weight(1f), car = selectedCars[1].copy(ID = selectedCars[0].ID))
            }
        }
    }
}


@Composable
fun SwapErrorAlertDialog(onDismissRequest: () -> Unit, modifier: Modifier = Modifier) {
    AlertDialog(
        onDismissRequest = { onDismissRequest() },
        title = { Text(text = "Select 2 cars") },
        text = { Text(text = "No more, no less")},
        modifier = modifier,
        confirmButton = {
            TextButton(
                onClick = onDismissRequest,
            ) {
                Text(text = "OK")
            }
        }
    )
}


@Preview(showSystemUi = true, showBackground = true)
@Composable
fun ChangeScreenPreview() {
    val vm: AppViewModel = viewModel()
    ChangeCarIdScreen(carsList = vm.myCars, onDoneButtonClicked = {})
}
