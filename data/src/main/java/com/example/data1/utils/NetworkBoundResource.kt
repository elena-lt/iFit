package com.example.ifit.data.network

import com.example.domain.utils.Resource
import kotlinx.coroutines.flow.*


inline fun <ResultType, RequestType> networkBoundResource(
    crossinline query: () -> Flow<ResultType>,
    crossinline fetch: suspend () -> RequestType, //the list of runs from firestore
    crossinline saveFetchResult: suspend (RequestType) -> Unit, //saving data from firestore into local db
    crossinline shouldFetch: (ResultType) -> Boolean = { true }
) = flow {
    val data = query().first()

    val flow = if (shouldFetch(data)) {
         //ini loading state
        //emit(Resource.LOADING(data))

        try {
            saveFetchResult(fetch())
            query().map { Resource.SUCCESS(it) }
        } catch (t: Throwable) {
            //emit from local database
            query().map { Resource.ERROR(t.message, it) }
        }

    }else {
        query().map { Resource.SUCCESS(it) }
    }

    emitAll(flow)
}