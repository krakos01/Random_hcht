package com.app.randomhcht.ui.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.app.randomhcht.model.Car
import com.app.randomhcht.ui.AppViewModel
import com.randomhcht.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun ChangeCarIdScreen(
    onDoneButtonClicked: () -> Unit,
    appViewModel: AppViewModel = viewModel(),
    carsList: SnapshotStateList<Car>
) {
    val selectedCars: MutableList<Car> = remember { mutableStateListOf() }

    Scaffold(
        bottomBar = {
            BottomBarWithButtons(
                selectedCars = selectedCars,
                appViewModel = appViewModel,
                onDoneButtonClicked = onDoneButtonClicked
            ) }
    ) {
        Column(
            modifier = Modifier.padding(it)
        ) {
            CarsCardsAfterChange(
                selectedCars = selectedCars,
                modifier = Modifier,
            )
            CarIdCardList(
                modifier = Modifier,
                myCars = carsList,
                selectedCars = selectedCars
            )
        }
    }
}

@Composable
fun CarIdCard(
    modifier: Modifier = Modifier,
    car: Car,
    onClick: (Car) -> Unit
) {

    var isClicked by remember { mutableStateOf(false) }
    val borderModifier = if (isClicked) {
        Modifier.border(2.dp, MaterialTheme.colorScheme.tertiary)
    }
    else { modifier }
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
                text = car.id.toString(),
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
    selectedCars: MutableList<Car>
) {
    Text(
        text = stringResource(id = R.string.list_of_all_cars),
        style = MaterialTheme.typography.titleMedium,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentWidth()
    )

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier
    ) {
        items(myCars.sortedBy { it.id }) {
            CarIdCard(
                modifier = modifier,
                car = it,
                onClick = { car ->
                    if (selectedCars.contains(car)) {
                        selectedCars.remove(car)
                    }
                    else  {
                        selectedCars.add(car)
                    }
                }
            )
        }
    }
}

@Composable
fun BottomBarWithButtons(
    modifier: Modifier = Modifier,
    selectedCars: MutableList<Car>,
    appViewModel: AppViewModel,
    onDoneButtonClicked: () -> Unit
) {
    val context = LocalContext.current
    
    var swapped: Boolean by remember { mutableStateOf(false) }
    var buttonText: String by remember { mutableStateOf(context.resources.getString(R.string.swap_button)) }
    val openDialog = remember { mutableStateOf(false)  }
    val hideDialog = { openDialog.value = false}
    val coroutineScope = rememberCoroutineScope()

    BottomAppBar(
        modifier = modifier.height(ButtonDefaults.MinHeight*3),
        containerColor = Color.Transparent
    ) {
        Column {
            Button(
                modifier = Modifier.fillMaxWidth(),
                shape = CardDefaults.shape,
                onClick = {
                    if (selectedCars.size == 2) {
                        appViewModel.swapCarsIds(
                            selectedCars[0].id,
                            selectedCars[1].id
                        )
                        coroutineScope.launch {
                            swapped = true
                            buttonText = context.resources.getString(R.string.swap_button_after_click)
                            delay(2500)
                            buttonText = context.resources.getString(R.string.swap_button)
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
                Text(text = stringResource(id = R.string.done_button))
            }
        }
    }
    if (openDialog.value) SwapErrorAlertDialog(hideDialog)
}

@Composable
fun CarsCardsAfterChange(
    modifier: Modifier,
    selectedCars: MutableList<Car>,
) {
    Column(
        modifier = modifier
            .padding(bottom = 10.dp)
            .height(160.dp)
    ) {
        Text(
            text = stringResource(id = R.string.after_swap),
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth()
        )
        if (selectedCars.size!=2) {
            Text(
                text = stringResource(id = R.string.select_two_cars),
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentWidth()
            )
        }
        else {
            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                CarCard(modifier.weight(1f), car = selectedCars[0].copy(id = selectedCars[1].id))
                CarCard(modifier.weight(1f), car = selectedCars[1].copy(id = selectedCars[0].id))
            }
        }
    }
}


@Composable
fun SwapErrorAlertDialog(onDismissRequest: () -> Unit, modifier: Modifier = Modifier) {
    AlertDialog(
        onDismissRequest = { onDismissRequest() },
        title = { Text(text = stringResource(id = R.string.select_two_cars)) },
        text = { Text(text = stringResource(id = R.string.no_more_no_less))},
        modifier = modifier,
        confirmButton = {
            TextButton(
                onClick = onDismissRequest,
            ) {
                Text(text = stringResource(id = R.string.ok_button))
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
