package id.miladiyyah.app.data.repository

import android.content.Context
import id.miladiyyah.app.data.local.CalendarEventLocalDataSource
import id.miladiyyah.app.data.local.room.RoomCalendarEventLocalDataSource
import id.miladiyyah.app.data.local.room.RoomDatabaseProvider
import id.miladiyyah.app.domain.repository.CalendarEventRepository

/**
 * Explicit composition boundary for the cache-aware repository.
 *
 * The caller supplies an existing CalendarEventRepository. That existing
 * repository remains the source of truth; this factory only adds the
 * Room-backed local fallback around it.
 *
 * This factory is intentionally not wired to startup or UI in Fase 27.
 */
object CachedCalendarEventRepositoryFactory {

    fun create(
        context: Context,
        sourceRepository: CalendarEventRepository,
    ): CalendarEventRepository {
        val localDataSource: CalendarEventLocalDataSource =
            RoomCalendarEventLocalDataSource(
                dao = RoomDatabaseProvider
                    .get(context)
                    .calendarEventDao(),
            )

        return CachedCalendarEventRepository(
            sourceRepository = sourceRepository,
            localDataSource = localDataSource,
        )
    }
}
