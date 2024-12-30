package com.obrio.test.presentation.model.stateHolder

/**
 * A generic interface that represents the state of a data object.
 * It can be used to hold the state of data fetching, including loading, data, and error states.
 *
 * @param T The type of the data being held.
 *
 * Properties:
 * - isLoading: Boolean that indicates whether the data is currently being loaded.
 * - data: The actual data of type [T], or null if there is no data.
 * - error: A string message that represents any error that occurred, or an empty string if no error occurred.
 */
interface StateHolder<T> {
    val isLoading: Boolean get() = false
    val data: T?
    val error: String get() = ""
}
