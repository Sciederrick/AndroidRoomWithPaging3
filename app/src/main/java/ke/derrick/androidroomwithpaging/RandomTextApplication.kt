package ke.derrick.androidroomwithpaging

import android.app.Application
import ke.derrick.androidroomwithpaging.data.RandomTextDatabase

class RandomTextApplication : Application() {
    val database: RandomTextDatabase by lazy { RandomTextDatabase.getInstance(this) }
}