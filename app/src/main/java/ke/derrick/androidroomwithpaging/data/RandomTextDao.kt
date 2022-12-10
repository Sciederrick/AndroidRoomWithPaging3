package ke.derrick.androidroomwithpaging.data

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface RandomTextDao {
    @get:Query("SELECT * FROM random_text")
    val allRandomTexts: PagingSource<Int, RandomText>

    @Insert
    suspend fun insert(randomText: RandomText)

    @Insert
    suspend fun insertMany(vararg randomText: RandomText)

    @Update
    suspend fun update(randomText: RandomText)

    @Delete
    suspend fun delete(randomText: RandomText)
}