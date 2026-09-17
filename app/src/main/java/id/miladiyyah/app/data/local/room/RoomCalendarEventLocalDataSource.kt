package id.miladiyyah.app.data.local.room

import id.miladiyyah.app.data.local.CalendarEventLocalDataSource
import id.miladiyyah.app.domain.model.CalendarEvent
import java.time.LocalDate

/**
 * Room-backed implementation of the local calendar cache boundary.
 *
 * This class performs only local data access and mapping.
 * It does not know about Firebase, repository activation, startup, or UI.
 */
class RoomCalendarEventLocalDataSource(
    private val dao: CalendarEventDao,
) : CalendarEventLocalDataSource {

    override suspend fun getEvents(
        startDate: LocalDate,
        endDate: LocalDate,
    ): List<CalendarEvent> {
        if (startDate.isAfter(endDate)) return emptyList()

        return with(CalendarEventRoomMapper) {
            dao.getByDateRange(
                startDate = startDate.toString(),
                endDate = endDate.toString(),
            ).toDomainList()
        }
    }

    override suspend fun upsertEvents(
        events: List<CalendarEvent>,
    ) {
        if (events.isEmpty()) return

        with(CalendarEventRoomMapper) {
            dao.upsertAll(events.toEntityList())
        }
    }

    override suspend fun deleteEvents(
        startDate: LocalDate,
        endDate: LocalDate,
    ) {
        if (startDate.isAfter(endDate)) return

        dao.deleteByDateRange(
            startDate = startDate.toString(),
            endDate = endDate.toString(),
        )
    }

    override suspend fun clear() {
        dao.clearAll()
    }
}
