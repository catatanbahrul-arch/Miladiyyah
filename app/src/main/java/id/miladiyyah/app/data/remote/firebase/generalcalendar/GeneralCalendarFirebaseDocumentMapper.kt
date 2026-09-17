package id.miladiyyah.app.data.remote.firebase.generalcalendar

import id.miladiyyah.app.data.remote.GeneralCalendarPayloadMapper
import id.miladiyyah.app.data.remote.GeneralCalendarRemoteItem
import id.miladiyyah.app.data.remote.firebase.FirebaseDocument
import id.miladiyyah.app.domain.model.CalendarEvent

object GeneralCalendarFirebaseDocumentMapper {

    fun map(
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

    fun mapAll(
        documents: List<FirebaseDocument>,
    ): List<CalendarEvent> =
        documents.mapNotNull(::map)

    private fun parseBoolean(
        value: String?,
    ): Boolean? =
        when (value?.trim()?.lowercase()) {
            "true", "1", "yes", "y" -> true
            "false", "0", "no", "n" -> false
            else -> null
        }
}
