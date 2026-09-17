package id.miladiyyah.app.data.repository

import id.miladiyyah.app.data.remote.DefaultGeneralCalendarRemoteDataSource
import id.miladiyyah.app.data.remote.GeneralCalendarRemoteDataSource
import id.miladiyyah.app.domain.model.CalendarEvent
import id.miladiyyah.app.domain.repository.CalendarEventRepository
import java.time.LocalDate

class DefaultCalendarEventRepository(
    private val remoteDataSource: GeneralCalendarRemoteDataSource =
        DefaultGeneralCalendarRemoteDataSource(),
) : CalendarEventRepository {

    override suspend fun getEvents(
        startDate: LocalDate,
        endDate: LocalDate,
    ): List<CalendarEvent> = remoteDataSource.getEvents(
        startDate = startDate,
        endDate = endDate,
    )
}
