package id.miladiyyah.app.data.remote.firebase

class DefaultFirebaseDynamicDataSource : FirebaseDynamicDataSource {
    override suspend fun getDocuments(
        collection: String,
    ): List<FirebaseDocument> = emptyList()
}
