package id.miladiyyah.app.data.repository

import android.content.Context
import id.miladiyyah.app.data.remote.firebase.generalcalendar.FirebaseGeneralCalendarRemoteDataSource
import id.miladiyyah.app.domain.repository.CalendarEventRepository

object FirebaseGeneralCalendarRepositoryFactory {

    fun create(
        context: Context,
        collectionName: String,
    ): CalendarEventRepository? {
        if (collectionName.isBlank()) {
            return null
        }

        val remoteDataSource = FirebaseGeneralCalendarRemoteDataSource(
            context = context,
            collectionName = collectionName,
        )

        return DefaultCalendarEventRepository(remoteDataSource)
    }
}
