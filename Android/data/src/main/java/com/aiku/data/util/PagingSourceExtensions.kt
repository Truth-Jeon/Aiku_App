package com.aiku.data.util

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import kotlinx.coroutines.flow.Flow

// 공통 PagingSource 확장 함수
fun <Value : Any> createPager(
    pageSize: Int = 10,
    loadData: suspend (page: Int) -> List<Value>
): Flow<PagingData<Value>> {
    return Pager(
        config = PagingConfig(pageSize = pageSize, enablePlaceholders = false),
        pagingSourceFactory = {
            object : PagingSource<Int, Value>() {
                override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Value> {
                    val page = params.key ?: 1
                    return try {
                        val data = loadData(page)
                        LoadResult.Page(
                            data = data,
                            prevKey = if (page == 1) null else page - 1,
                            nextKey = if (data.size < pageSize + 1) null else page + 1
                        )
                    } catch (e: Exception) {
                        LoadResult.Error(e)
                    }
                }

                override fun getRefreshKey(state: PagingState<Int, Value>): Int? {
                    return state.anchorPosition?.let { anchorPosition ->
                        val anchorPage = state.closestPageToPosition(anchorPosition)
                        anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
                    }
                }
            }
        }
    ).flow
}

