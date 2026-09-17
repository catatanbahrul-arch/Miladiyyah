package id.miladiyyah.app.data.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CalendarEventDao {

    @Query(
        """
        SELECT * FROM calendar_events
        WHERE dateIso BETWEEN :startDate AND :endDate
        ORDER BY dateIso ASC, id ASC
        """,
    )
    suspend fun getByDateRange(
        startDate: String,
        endDate: String,
    ): List<CalendarEventEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(
        items: List<CalendarEventEntity>,
    )

    @Query(
        """
        DELETE FROM calendar_events
        WHERE dateIso BETWEEN :startDate AND :endDate
        """,
    )
    suspend fun deleteByDateRange(
        startDate: String,
        endDate: String,
    )

    @Query("DELETE FROM calendar_events")
    suspend fun clearAll()
}
