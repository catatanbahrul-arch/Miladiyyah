package id.miladiyyah.app.data.local.room

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Local cache representation of a general calendar event.
 *
 * This is cache data only. It is not a source of truth and contains
 * no provider-specific Firebase/REST/Spreadsheet fields.
 */
@Entity(
    tableName = "calendar_events",
    indices = [
        Index(value = ["dateIso"]),
    ],
)
data class CalendarEventEntity(
    @PrimaryKey
    val id: String,
    val dateIso: String,
    val title: String,
    val description: String? = null,
    val category: String? = null,
    val isHoliday: Boolean = false,
    val sourceUrl: String? = null,
)
