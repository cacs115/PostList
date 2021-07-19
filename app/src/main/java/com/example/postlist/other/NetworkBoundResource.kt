package com.example.postlist.other

import com.example.postlist.data.Post
import kotlinx.coroutines.flow.*

fun <ResultType, RequestType> networkBoundResource(
    query: () -> Flow<ResultType>,
    fetch: suspend () -> RequestType,
    saveFetchResult: suspend (RequestType) -> Unit,
    shouldFetch: (ResultType) -> Boolean = { true }
) = flow {
    val data = query().first()

    val flow = if (shouldFetch(data)) {
        emit(Resource.loading(data))

        try {
            saveFetchResult(fetch())
            query().map { Resource.success(it) }
        } catch (throwable: Throwable) {
            query().map { Resource.error(throwable.message.toString(), it) }
        }
    } else {
        query().map { Resource.success(it) }
    }

    emitAll(flow)
}