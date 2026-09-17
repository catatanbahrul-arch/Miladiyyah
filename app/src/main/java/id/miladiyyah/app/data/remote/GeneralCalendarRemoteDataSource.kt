package id.miladiyyah.app.data.remote

import id.miladiyyah.app.domain.model.CalendarEvent
import java.time.LocalDate

interface GeneralCalendarRemoteDataSource {
    suspend fun getEvents(
        startDate: LocalDate,
        endDate: LocalDate,
    ): List<CalendarEvent>
}
