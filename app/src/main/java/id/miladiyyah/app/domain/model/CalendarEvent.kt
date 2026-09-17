package id.miladiyyah.app.domain.model

data class CalendarEvent(
    val id: String? = null,
    val dateIso: String,
    val title: String,
    val description: String? = null,
    val category: String? = null,
    val isHoliday: Boolean = false,
    val sourceUrl: String? = null,
)
