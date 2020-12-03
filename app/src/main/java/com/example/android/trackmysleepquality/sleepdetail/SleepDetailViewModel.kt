package com.example.android.trackmysleepquality.sleepdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.trackmysleepquality.database.SleepDatabaseDao
import com.example.android.trackmysleepquality.database.SleepNight
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SleepDetailViewModel(
        private val sleepNightKey: Long = 0L,
        private val database: SleepDatabaseDao) : ViewModel() {

    val night = MutableLiveData<SleepNight>(SleepNight())

    private val _navigateToSleepTracker = MutableLiveData<Boolean?>()

    val navigateToSleepTracker: LiveData<Boolean?>
        get() = _navigateToSleepTracker

    init {
        initializeNight()
    }

    fun doneNavigating() {
        _navigateToSleepTracker.value = null
    }

    fun onClose() {
        _navigateToSleepTracker.value = true
    }

    private fun initializeNight() {
        viewModelScope.launch {
            getNightFromDatabase()
        }
    }

    private suspend fun getNightFromDatabase() {
        withContext(Dispatchers.IO) {
            night.postValue(database.get(sleepNightKey))
        }
    }
}

