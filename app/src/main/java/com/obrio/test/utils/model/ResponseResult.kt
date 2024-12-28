package com.obrio.test.utils.model

/**
 * A sealed class representing the result of an operation.
 *
 * This class is typically used to encapsulate success, error, or loading states
 * in a type-safe and expressive manner. It is useful for handling asynchronous
 * data loading or processing workflows in a clean way.
 *
 * Variants:
 * - [Success]: Represents a successful operation and contains the resulting data.
 * - [Error]: Represents a failed operation and contains an error message.
 * - [Loading]: Represents a loading or in-progress state without any additional data.
 *
 * @param T The type of data expected in case of a successful operation.
 */
sealed class ResponseResult<out T> {
    data class Success<out T>(val data: T) : ResponseResult<T>()
    data class Error(val message: String) : ResponseResult<Nothing>()
    data object Loading : ResponseResult<Nothing>()
}
