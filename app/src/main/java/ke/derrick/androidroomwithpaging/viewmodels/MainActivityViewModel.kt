package ke.derrick.androidroomwithpaging.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import ke.derrick.androidroomwithpaging.data.RandomTextDao

class MainActivityViewModel(private val randomTextDao: RandomTextDao): ViewModel() {
    val allRandomTexts = Pager(
        config = PagingConfig(
            pageSize = 50,
            enablePlaceholders = true,
            maxSize = 200
        )
    ) {
        randomTextDao.allRandomTexts
    } .flow
        .cachedIn(viewModelScope)
}

class MainActivityViewModelFactory(private val randomTextDao: RandomTextDao): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainActivityViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainActivityViewModel(randomTextDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}