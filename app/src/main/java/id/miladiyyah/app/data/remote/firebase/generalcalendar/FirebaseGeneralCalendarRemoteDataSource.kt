package id.miladiyyah.app.data.remote.firebase.generalcalendar

import android.content.Context
import id.miladiyyah.app.data.remote.GeneralCalendarPayloadMapper
import id.miladiyyah.app.data.remote.GeneralCalendarRemoteDataSource
import id.miladiyyah.app.data.remote.GeneralCalendarRemoteItem
import id.miladiyyah.app.data.remote.firebase.FirebaseDocument
import id.miladiyyah.app.data.remote.firebase.firestore.FirestoreDynamicDataSource
import id.miladiyyah.app.data.remote.firebase.runtime.FirebaseRuntime
import id.miladiyyah.app.domain.model.CalendarEvent
import java.time.LocalDate

class FirebaseGeneralCalendarRemoteDataSource(
    private val context: Context,
    private val collectionName: String,
) : GeneralCalendarRemoteDataSource {

    override suspend fun getEvents(
        startDate: LocalDate,
        endDate: LocalDate,
    ): List<CalendarEvent> {
        if (collectionName.isBlank() || startDate.isAfter(endDate)) {
            return emptyList()
        }

        val firestore = FirebaseRuntime.firestoreOrNull(context) ?: return emptyList()
        val source = FirestoreDynamicDataSource(firestore)

        return source.getDocuments(collectionName)
            .asSequence()
            .mapNotNull { document ->
                mapDocument(document)
            }
            .filter { event ->
                val date = runCatching { LocalDate.parse(event.dateIso) }.getOrNull()
                date != null && !date.isBefore(startDate) && !date.isAfter(endDate)
            }
            .toList()
    }

    private fun mapDocument(
        document: FirebaseDocument,
    ): CalendarEvent? {
        val fields = document.fields

        val item = GeneralCalendarRemoteItem(
            id = fields["id"]?.trim()?.takeIf { it.isNotEmpty() } ?: document.id,
            dateIso = fields["dateIso"].orEmpty(),
            title = fields["title"],
            description = fields["description"],
            category = fields["category"],
            isHoliday = parseBoolean(fields["isHoliday"]),
            sourceUrl = fields["sourceUrl"],
        )

        return GeneralCalendarPayloadMapper.map(item)
    }

    private fun parseBoolean(
        value: String?,
    ): Boolean? =
        when (value?.trim()?.lowercase()) {
            "true", "1", "yes", "y" -> true
            "false", "0", "no", "n" -> false
            else -> null
        }
}
