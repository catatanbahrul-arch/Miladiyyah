package id.miladiyyah.app.domain.repository

import id.miladiyyah.app.domain.model.CalendarEvent
import java.time.LocalDate

interface CalendarEventRepository {
    suspend fun getEvents(
        startDate: LocalDate,
        endDate: LocalDate,
    ): List<CalendarEvent>
}
