package id.miladiyyah.app.data.remote

import id.miladiyyah.app.domain.model.CalendarEvent
import java.time.LocalDate

class DefaultGeneralCalendarRemoteDataSource : GeneralCalendarRemoteDataSource {
    override suspend fun getEvents(
        startDate: LocalDate,
        endDate: LocalDate,
    ): List<CalendarEvent> = emptyList()
}
