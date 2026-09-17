package id.miladiyyah.app.data.repository

import id.miladiyyah.app.data.files.FileDataSource
import id.miladiyyah.app.data.remote.RestDataSource
import id.miladiyyah.app.data.spreadsheet.SpreadsheetDataSource
import id.miladiyyah.app.domain.model.CalendarData
import id.miladiyyah.app.domain.repository.CalendarRepository

/**
 * Composition root for data access.
 * No real fetching or provider selection is performed in Fase 2D.
 */
class DefaultCalendarRepository(
    private val restDataSource: RestDataSource,
    private val spreadsheetDataSource: SpreadsheetDataSource,
    private val fileDataSource: FileDataSource,
) : CalendarRepository {

    /**
     * Provider-neutral placeholder until the real data source contract is
     * supplied. Fase 2D must not invent network, spreadsheet, or file data.
     */
    override suspend fun getCalendarData(): List<CalendarData> = emptyList()
}
