package id.miladiyyah.app.data.repository

import id.miladiyyah.app.domain.model.CalendarEvent
import id.miladiyyah.app.domain.repository.CalendarEventRepository
import java.time.LocalDate

class DefaultCalendarEventRepository : CalendarEventRepository {
    override suspend fun getEvents(
        startDate: LocalDate,
        endDate: LocalDate,
    ): List<CalendarEvent> = emptyList()
}
