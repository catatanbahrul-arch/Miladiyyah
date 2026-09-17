package id.miladiyyah.app.data.local.room

import android.content.Context
import androidx.room.Room

/**
 * Process-local singleton access point for the Room cache database.
 *
 * This provider is intentionally not called from startup/UI in Fase 23.
 */
object RoomDatabaseProvider {

    @Volatile
    private var instance: MiladiyyahDatabase? = null

    fun get(
        context: Context,
    ): MiladiyyahDatabase {
        return instance ?: synchronized(this) {
            instance ?: Room.databaseBuilder(
                context.applicationContext,
                MiladiyyahDatabase::class.java,
                "miladiyyah_cache.db",
            ).build().also { instance = it }
        }
    }
}
