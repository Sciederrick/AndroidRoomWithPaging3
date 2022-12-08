package ke.derrick.androidroomwithpaging.data

import android.content.Context
import androidx.room.Room
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [RandomText::class], exportSchema = false, version = 1)
abstract class RandomTextDatabase : RoomDatabase() {
    abstract fun randomTextDao(): RandomTextDao

    companion object {
        @Volatile
        private var instance: RandomTextDatabase? = null

        fun getInstance(context: Context): RandomTextDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): RandomTextDatabase {
            return Room.databaseBuilder(context, RandomTextDatabase::class.java, "random_text_db")
                .fallbackToDestructiveMigration()
                .build()
        }

    }
}




