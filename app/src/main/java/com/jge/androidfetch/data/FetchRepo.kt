package com.jge.androidfetch.data

import com.jge.androidfetch.domain.FetchItem
import kotlinx.coroutines.flow.Flow

interface FetchRepo {
    suspend fun getFetchItems(): Flow<List<FetchItem>>
}