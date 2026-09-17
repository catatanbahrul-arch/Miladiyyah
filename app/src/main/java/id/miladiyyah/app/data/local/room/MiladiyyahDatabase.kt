package id.miladiyyah.app.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [CalendarEventEntity::class],
    version = 1,
    exportSchema = false,
)
abstract class MiladiyyahDatabase : RoomDatabase() {
    abstract fun calendarEventDao(): CalendarEventDao
}
