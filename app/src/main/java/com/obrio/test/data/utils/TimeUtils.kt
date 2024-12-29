package com.obrio.test.data.utils

import android.util.Log
import com.obrio.test.utils.AppLogger.Data.TIME_UTILS_TAG

object TimeUtils {
    /**
     * Helper function to check if the given time is more than an hour ago.
     *
     * @param lastFetchTime The timestamp of the last fetch in milliseconds.
     * @return `true` if more than an hour has passed since the given time, `false` otherwise.
     */
     fun isMoreThanAnHourAgo(lastFetchTime: Long): Boolean {
        val currentTime = System.currentTimeMillis()
        val oneHourInMillis = 60 * 60 * 1000 // 1 hour in milliseconds
        val timeDifference = currentTime - lastFetchTime
        Log.d(TIME_UTILS_TAG, "isMoreThanAnHourAgo: timeDifference between current and fetched $timeDifference")
        return  timeDifference > oneHourInMillis
    }
}