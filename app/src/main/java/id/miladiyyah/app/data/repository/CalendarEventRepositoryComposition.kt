package id.miladiyyah.app.data.repository

import android.content.Context
import id.miladiyyah.app.domain.repository.CalendarEventRepository

/**
 * Application-facing composition boundary for the calendar repository.
 *
 * The application should call this boundary when repository composition
 * is eventually activated. Fase 30 deliberately does not wire it into
 * startup or UI.
 */
object CalendarEventRepositoryComposition {

    fun create(
        context: Context,
        sourceRepository: CalendarEventRepository,
        cacheMode: CalendarEventCacheMode,
    ): CalendarEventRepository {
        return CalendarEventRepositoryActivation.create(
            context = context,
            sourceRepository = sourceRepository,
            cacheMode = cacheMode,
        )
    }
}
