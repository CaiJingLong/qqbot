package actions.interfaces

interface OnEventAction<T> {

    suspend fun invoke(event: T)

}