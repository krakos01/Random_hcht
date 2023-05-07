package com.app.randomhcht.ui.test

import com.app.randomhcht.data.NO_OF_RACES
import com.app.randomhcht.ui.AppUiState
import com.app.randomhcht.ui.AppViewModel
import org.junit.Assert.*
import org.junit.Test

class AppViewModelTest {
    private val viewModel = AppViewModel()

    @Test
    fun appViewModel_NextRaceClicked_NumberOfRacesIncreasedAndCarsChangedAndTrackChanged() {
        val previousAppUiState = viewModel.uiState.value
        viewModel.nextRace()
        val currentAppUiState = viewModel.uiState.value

        // Assert that nextRace method updates currentRace counter correctly.
        assertEquals(2, currentAppUiState.currentRace)
        //Assert that cars are changed.
        assertNotEquals(currentAppUiState.currentP1Car, previousAppUiState.currentP1Car)
        assertNotEquals(currentAppUiState.currentP2Car, previousAppUiState.currentP2Car)
        // Assert that track is changed.
        assertNotEquals(currentAppUiState.currentTrack, previousAppUiState.currentTrack)
    }

    @Test
    fun appViewModel_DrawAgainClicked_CarsChangedAndTrackNotChangedAndNumberOfRacesNotChanged() {
        val previousAppUiState = viewModel.uiState.value
        viewModel.drawCars()
        val currentAppUiState = viewModel.uiState.value

        // Assert that cars are changed.
        assertNotEquals(currentAppUiState.currentP1Car, previousAppUiState.currentP1Car)
        assertNotEquals(currentAppUiState.currentP2Car, previousAppUiState.currentP2Car)

        // Assert that track remained the same.
        assertEquals(currentAppUiState.currentTrack, previousAppUiState.currentTrack)
        // Assert that currentRace counter is unchanged.
        assertEquals(currentAppUiState.currentRace, previousAppUiState.currentRace)
    }

    @Test
    fun appViewModel_Initialization_FirstRaceLoaded() {
        val appUiState = viewModel.uiState.value

        // Assert that current race counter is set to 1.
        assertEquals(1, appUiState.currentRace)
        // Assert that cars are not null.
        assertNotNull(appUiState.currentP1Car)
        assertNotNull(appUiState.currentP2Car)
        //Assert that track is not null.
        assertNotNull(appUiState.currentTrack)
        // Assert that game is not over.
        assertFalse(appUiState.isGameOver)
    }

    @Test
    fun appViewModel_AllRacesDone_UiStateUpdatedCorrectly() {
        var currentAppUiState: AppUiState
        var expectedNumberOfRaces = 1

        repeat(NO_OF_RACES) {
            currentAppUiState = viewModel.uiState.value

            // Assert that after each "Next Race" button number of races is updated
            assertEquals(expectedNumberOfRaces, currentAppUiState.currentRace)

            viewModel.nextRace()
            expectedNumberOfRaces++
        }
        currentAppUiState = viewModel.uiState.value

        assertEquals(currentAppUiState.currentRace, NO_OF_RACES)
        assertTrue(currentAppUiState.isGameOver)
    }
}