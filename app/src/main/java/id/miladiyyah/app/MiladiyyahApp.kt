package id.miladiyyah.app

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import id.miladiyyah.app.ui.screens.calendar.CalendarScreen

@Composable
fun MiladiyyahApp() {
    MaterialTheme {
        CalendarScreen()
    }
}
