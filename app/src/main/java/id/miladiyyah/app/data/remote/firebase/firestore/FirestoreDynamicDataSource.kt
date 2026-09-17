package id.miladiyyah.app.data.remote.firebase.firestore

import com.google.firebase.firestore.FirebaseFirestore
import id.miladiyyah.app.data.remote.firebase.FirebaseDocument
import id.miladiyyah.app.data.remote.firebase.FirebaseDynamicDataSource
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlinx.coroutines.suspendCancellableCoroutine

class FirestoreDynamicDataSource(
    private val firestore: FirebaseFirestore,
) : FirebaseDynamicDataSource {

    override suspend fun getDocuments(
        collection: String,
    ): List<FirebaseDocument> =
        suspendCancellableCoroutine { continuation ->
            firestore.collection(collection)
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val documents = task.result.documents.map { document ->
                            FirebaseDocument(
                                id = document.id,
                                fields = document.data.orEmpty().mapValues { (_, value) ->
                                    value?.toString()
                                },
                            )
                        }
                        continuation.resume(documents)
                    } else {
                        continuation.resumeWithException(
                            task.exception
                                ?: IllegalStateException("Firestore read failed"),
                        )
                    }
                }
        }
}
