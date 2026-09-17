package id.miladiyyah.app.data.remote.firebase

interface FirebaseDynamicDataSource {
    suspend fun getDocuments(
        collection: String,
    ): List<FirebaseDocument>
}
