package id.miladiyyah.app.domain.repository

import id.miladiyyah.app.domain.model.CalendarData

/**
 * Domain-facing repository contract.
 *
 * The domain asks for calendar data without knowing whether the eventual
 * provider is REST, Spreadsheet, File, or a combination of them.
 */
interface CalendarRepository {
    suspend fun getCalendarData(): List<CalendarData>
}
