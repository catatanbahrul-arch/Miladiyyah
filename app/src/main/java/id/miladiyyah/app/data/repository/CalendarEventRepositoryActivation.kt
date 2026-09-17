package id.miladiyyah.app.data.repository

import android.content.Context
import id.miladiyyah.app.domain.repository.CalendarEventRepository

/**
 * Explicit activation policy for the general calendar repository.
 *
 * This is an activation boundary only. It is intentionally not called
 * by startup, UI, or MiladiyyahApp in Fase 28.
 */
enum class CalendarEventCacheMode {
    REMOTE_ONLY,
    REMOTE_WITH_LOCAL_FALLBACK,
}

object CalendarEventRepositoryActivation {

    fun create(
        context: Context,
        sourceRepository: CalendarEventRepository,
        cacheMode: CalendarEventCacheMode,
    ): CalendarEventRepository {
        return when (cacheMode) {
            CalendarEventCacheMode.REMOTE_ONLY -> sourceRepository
            CalendarEventCacheMode.REMOTE_WITH_LOCAL_FALLBACK ->
                CachedCalendarEventRepositoryFactory.create(
                    context = context,
                    sourceRepository = sourceRepository,
                )
        }
    }
}
