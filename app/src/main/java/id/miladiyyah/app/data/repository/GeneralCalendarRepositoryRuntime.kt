package id.miladiyyah.app.data.repository

import android.content.Context
import id.miladiyyah.app.domain.repository.CalendarEventRepository

/**
 * Application-facing runtime entry point for the General Calendar repository.
 *
 * Default behavior is deliberately local-empty:
 * - no Firebase access;
 * - no Room wrapping;
 * - no startup network;
 * - no production collection configuration.
 *
 * A caller must explicitly choose a non-default source mode to activate
 * Firebase-backed or cache-wrapped behavior.
 */
object GeneralCalendarRepositoryRuntime {

    fun create(
        context: Context,
        sourceMode: GeneralCalendarRepositorySourceMode =
            GeneralCalendarRepositorySourceMode.LOCAL_EMPTY_BASELINE,
        collectionName: String? = null,
    ): CalendarEventRepository? {
        return GeneralCalendarRepositorySourceSelector.create(
            context = context,
            sourceMode = sourceMode,
            collectionName = collectionName,
        )
    }
}
