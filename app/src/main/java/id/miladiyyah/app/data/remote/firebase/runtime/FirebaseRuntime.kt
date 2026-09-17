package id.miladiyyah.app.data.remote.firebase.runtime

import android.content.Context
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore

object FirebaseRuntime {

    fun firestoreOrNull(
        context: Context,
    ): FirebaseFirestore? {
        val app = FirebaseApp.getApps(context).firstOrNull() ?: return null
        return FirebaseFirestore.getInstance(app)
    }

    fun isConfigured(
        context: Context,
    ): Boolean =
        FirebaseApp.getApps(context).isNotEmpty()
}
