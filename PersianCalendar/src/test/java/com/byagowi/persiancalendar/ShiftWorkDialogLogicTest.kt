package com.byagowi.persiancalendar

import com.byagowi.persiancalendar.entities.ShiftWorkRecord
import com.byagowi.persiancalendar.ui.calendar.dialogs.ShiftWorkDialogState
import org.junit.Assert.*
import org.junit.Test

class ShiftWorkDialogLogicTest {

    @Test
    fun addEmptyOnEmptyState() {
        val input = listOf<ShiftWorkRecord>()
        val expected = listOf(ShiftWorkRecord("", 0))
        assertReducerIntegrity(expected, input)
    }

    @Test
    fun removeEmptyRowsOfState() {
        val input = listOf(
            ShiftWorkRecord("a", 1),
            ShiftWorkRecord("b", 1),
            ShiftWorkRecord("", 0),
            ShiftWorkRecord(" ", 0), // We consider blank empty as well
            ShiftWorkRecord("c", 0), // Not empty as type has some value
            ShiftWorkRecord("", 1), // Not empty as type has some value
            ShiftWorkRecord(" ", 1), // Not empty as type has some value
            ShiftWorkRecord("", 0)
        )
        val expected = listOf(
            ShiftWorkRecord("a", 1),
            ShiftWorkRecord("b", 1),
            ShiftWorkRecord("c", 0),
            ShiftWorkRecord("", 1),
            ShiftWorkRecord(" ", 1),
            ShiftWorkRecord("", 0)
        )
        assertReducerIntegrity(expected, input)
    }

    @Test
    fun addEndingEmptyRowToState() {
        val input = listOf(
            ShiftWorkRecord("a", 1),
            ShiftWorkRecord("b", 1),
            ShiftWorkRecord("c", 1),
        )
        val expected = listOf(
            ShiftWorkRecord("a", 1),
            ShiftWorkRecord("b", 1),
            ShiftWorkRecord("c", 1),
            ShiftWorkRecord("", 0)
        )
        assertReducerIntegrity(expected, input)
    }

    private fun assertReducerIntegrity(
        expected: List<ShiftWorkRecord>, input: List<ShiftWorkRecord>
    ) {
        val actual = input.toMutableList()
        assertTrue(ShiftWorkDialogState.ensureBodyStateIntegrity(actual)) // changes the state
        assertEquals(expected, actual)
        assertFalse(ShiftWorkDialogState.ensureBodyStateIntegrity(actual)) // doesn't change the state
    }
}