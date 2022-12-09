package ke.derrick.androidroomwithpaging.data

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query

@Dao
interface RandomTextDao {
    @get:Query("SELECT * FROM random_text")
    val allRandomTexts: PagingSource<Int, RandomText>
}