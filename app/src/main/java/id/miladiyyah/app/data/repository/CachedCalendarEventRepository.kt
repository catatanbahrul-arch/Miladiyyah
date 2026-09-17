package id.miladiyyah.app.data.repository

import id.miladiyyah.app.data.local.CalendarEventLocalDataSource
import id.miladiyyah.app.domain.model.CalendarEvent
import id.miladiyyah.app.domain.repository.CalendarEventRepository
import java.time.LocalDate

/**
 * Repository decorator that keeps the existing repository as the source
 * of truth and uses Room-backed local data only as a fallback.
 *
 * This class is an explicit activation boundary. It is not wired into
 * startup, UI, or the existing DefaultCalendarEventRepository in Fase 26.
 */
class CachedCalendarEventRepository(
    private val sourceRepository: CalendarEventRepository,
    private val localDataSource: CalendarEventLocalDataSource,
) : CalendarEventRepository {

    override suspend fun getEvents(
        startDate: LocalDate,
        endDate: LocalDate,
    ): List<CalendarEvent> {
        if (startDate.isAfter(endDate)) return emptyList()

        return try {
            val remoteEvents = sourceRepository.getEvents(
                startDate = startDate,
                endDate = endDate,
            )

            if (remoteEvents.isEmpty()) {
                localDataSource.deleteEvents(
                    startDate = startDate,
                    endDate = endDate,
                )
            } else {
                localDataSource.upsertEvents(remoteEvents)
            }

            remoteEvents
        } catch (_: Exception) {
            localDataSource.getEvents(
                startDate = startDate,
                endDate = endDate,
            )
        }
    }
}
