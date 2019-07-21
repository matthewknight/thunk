package Extras

class AsyncTaskResult<T : Any> {
    var exception: Exception? = null
    lateinit var result: T

    fun hasException(): Boolean {
        return exception != null
    }
}