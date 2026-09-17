package id.miladiyyah.app.data.local

import id.miladiyyah.app.domain.model.CalendarEvent
import java.time.LocalDate

/**
 * Local cache boundary for general calendar events.
 *
 * This interface intentionally knows only the domain model and standard
 * library types. It does not depend on Room classes or Firebase.
 */
interface CalendarEventLocalDataSource {

    suspend fun getEvents(
        startDate: LocalDate,
        endDate: LocalDate,
    ): List<CalendarEvent>

    suspend fun upsertEvents(
        events: List<CalendarEvent>,
    )

    suspend fun deleteEvents(
        startDate: LocalDate,
        endDate: LocalDate,
    )

    suspend fun clear()
}
