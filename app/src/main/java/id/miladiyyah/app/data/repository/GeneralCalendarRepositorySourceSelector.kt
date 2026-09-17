package id.miladiyyah.app.data.repository

import android.content.Context
import id.miladiyyah.app.domain.repository.CalendarEventRepository

/**
 * Explicit source-selection boundary for the general calendar repository.
 *
 * This boundary chooses an existing repository implementation. It does not
 * activate itself from startup/UI and does not define production configuration.
 */
enum class GeneralCalendarRepositorySourceMode {
    LOCAL_EMPTY_BASELINE,
    FIREBASE_COLLECTION,
    CACHE_WRAPPED,
}

object GeneralCalendarRepositorySourceSelector {

    fun create(
        context: Context,
        sourceMode: GeneralCalendarRepositorySourceMode,
        collectionName: String? = null,
    ): CalendarEventRepository? {
        return when (sourceMode) {
            GeneralCalendarRepositorySourceMode.LOCAL_EMPTY_BASELINE ->
                DefaultCalendarEventRepository()

            GeneralCalendarRepositorySourceMode.FIREBASE_COLLECTION -> {
                val normalizedCollectionName = collectionName?.trim()
                require(!normalizedCollectionName.isNullOrEmpty()) {
                    "collectionName is required for FIREBASE_COLLECTION"
                }
                FirebaseGeneralCalendarRepositoryFactory.create(
                    context = context,
                    collectionName = normalizedCollectionName,
                )
            }

            GeneralCalendarRepositorySourceMode.CACHE_WRAPPED -> {
                val normalizedCollectionName = collectionName?.trim()
                require(!normalizedCollectionName.isNullOrEmpty()) {
                    "collectionName is required for CACHE_WRAPPED"
                }

                val sourceRepository =
                    FirebaseGeneralCalendarRepositoryFactory.create(
                        context = context,
                        collectionName = normalizedCollectionName,
                    ) ?: return null

                CalendarEventRepositoryComposition.create(
                    context = context,
                    sourceRepository = sourceRepository,
                    cacheMode = CalendarEventCacheMode.REMOTE_WITH_LOCAL_FALLBACK,
                )
            }
        }
    }
}
