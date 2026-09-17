package id.miladiyyah.app.data.files

/**
 * File boundary only. The concrete origin and file format are deliberately
 * not assumed at this stage.
 */
interface FileDataSource {
    suspend fun read(path: String): ByteArray
}
