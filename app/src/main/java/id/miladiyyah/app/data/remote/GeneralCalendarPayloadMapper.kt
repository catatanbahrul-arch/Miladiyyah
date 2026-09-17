package id.miladiyyah.app.data.remote

import id.miladiyyah.app.domain.model.CalendarEvent
import java.time.LocalDate
import java.time.format.DateTimeParseException

object GeneralCalendarPayloadMapper {

    fun map(
        item: GeneralCalendarRemoteItem,
    ): CalendarEvent? {
        val date = try {
            LocalDate.parse(item.dateIso.trim())
        } catch (_: DateTimeParseException) {
            return null
        }

        val title = item.title?.trim().orEmpty()
        if (title.isEmpty()) {
            return null
        }

        val normalizedId = item.id?.trim()?.takeIf { it.isNotEmpty() }
        val normalizedDescription = item.description?.trim()?.takeIf { it.isNotEmpty() }
        val normalizedCategory = item.category?.trim()?.takeIf { it.isNotEmpty() }
        val normalizedSourceUrl = item.sourceUrl?.trim()?.takeIf { it.isNotEmpty() }

        return CalendarEvent(
            id = normalizedId,
            dateIso = date.toString(),
            title = title,
            description = normalizedDescription,
            category = normalizedCategory,
            isHoliday = item.isHoliday ?: false,
            sourceUrl = normalizedSourceUrl,
        )
    }

    fun mapAll(
        items: List<GeneralCalendarRemoteItem>,
    ): List<CalendarEvent> =
        items.mapNotNull(::map)
}
