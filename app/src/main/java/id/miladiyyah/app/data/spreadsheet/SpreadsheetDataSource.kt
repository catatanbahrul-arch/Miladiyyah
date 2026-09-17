package id.miladiyyah.app.data.spreadsheet

/**
 * Spreadsheet boundary only. The concrete provider (for example Google Sheets)
 * and exact table/range schema will be attached after the real source is defined.
 */
interface SpreadsheetDataSource {
    suspend fun read(range: String): List<Map<String, String>>
}
