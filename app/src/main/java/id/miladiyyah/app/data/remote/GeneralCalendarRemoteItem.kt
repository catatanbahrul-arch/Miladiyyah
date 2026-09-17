package id.miladiyyah.app.data.remote

data class GeneralCalendarRemoteItem(
    val id: String? = null,
    val dateIso: String,
    val title: String?,
    val description: String? = null,
    val category: String? = null,
    val isHoliday: Boolean? = null,
    val sourceUrl: String? = null,
)
