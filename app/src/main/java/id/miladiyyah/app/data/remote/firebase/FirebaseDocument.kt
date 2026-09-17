package id.miladiyyah.app.data.remote.firebase

data class FirebaseDocument(
    val id: String,
    val fields: Map<String, String?> = emptyMap(),
)
