package id.miladiyyah.app.domain.model

/**
 * Internal, source-neutral calendar data contract.
 *
 * This model intentionally describes only the generic calendar information
 * the application can safely own before a real REST/Spreadsheet/File schema
 * is supplied. It contains no provider-specific field names, IDs, URLs,
 * credentials, or network details.
 */
data class CalendarData(
    val id: String? = null,
    val dateIso: String,
    val title: String? = null,
    val description: String? = null,
    val category: String? = null,
)
