package id.miladiyyah.app.data.local.room

import id.miladiyyah.app.domain.model.CalendarEvent

/**
 * Explicit mapping boundary between the domain CalendarEvent and Room.
 *
 * Room remains a data/local concern. The domain model does not import
 * Room annotations or Room classes.
 */
object CalendarEventRoomMapper {

    /**
     * Converts a valid domain event into a Room cache entity.
     *
     * A null/blank title is not inserted into cache because the Room
     * entity requires a non-null title.
     */
    fun CalendarEvent.toEntityOrNull(): CalendarEventEntity? {
        val normalizedId = id?.trim()
        val normalizedDateIso = dateIso.trim()
        val normalizedTitle = title.trim()

        if (normalizedId.isNullOrEmpty()) return null
        if (normalizedDateIso.isNullOrEmpty()) return null
        if (normalizedTitle.isNullOrEmpty()) return null

        return CalendarEventEntity(
            id = normalizedId,
            dateIso = normalizedDateIso,
            title = normalizedTitle,
            description = description?.trim()?.ifEmpty { null },
            category = category?.trim()?.ifEmpty { null },
            isHoliday = isHoliday,
            sourceUrl = sourceUrl?.trim()?.ifEmpty { null },
        )
    }

    fun CalendarEventEntity.toDomain(): CalendarEvent {
        return CalendarEvent(
            id = id,
            dateIso = dateIso,
            title = title,
            description = description,
            category = category,
            isHoliday = isHoliday,
            sourceUrl = sourceUrl,
        )
    }

    fun List<CalendarEvent>.toEntityList(): List<CalendarEventEntity> =
        mapNotNull { it.toEntityOrNull() }

    fun List<CalendarEventEntity>.toDomainList(): List<CalendarEvent> =
        map { it.toDomain() }
}
