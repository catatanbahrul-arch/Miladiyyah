package id.miladiyyah.app.data.remote

/**
 * REST boundary only. No URL, authentication, endpoint path, or JSON schema
 * is hard-coded here because the real API contract has not been supplied yet.
 */
interface RestDataSource {
    suspend fun get(path: String, query: Map<String, String> = emptyMap()): String
}
