package com.jge.androidfetch.data

import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FetchRepoImpl @Inject constructor(val fetchApi: FetchApi) : FetchRepo {
    override suspend fun getFetchItems()= flow {
        emit(fetchApi.getListOfIds())
    }
}