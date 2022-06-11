package com.example.gitapp.framework

import androidx.paging.PagingState
import androidx.paging.rxjava2.RxPagingSource
import com.example.core.domain.Repo
import com.example.gitapp.framework.network.GithubService
import com.example.gitapp.util.asRepoDomain
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class RepositoryPageSourse constructor(val GitSevise: GithubService, val userName: String) : RxPagingSource<Int, Repo>() {

    override fun getRefreshKey(state: PagingState<Int, Repo>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, Repo>> {
        val page: Int = params.key ?: 1
        val pageSize = params.loadSize

        return GitSevise
            .getRepoPerPage(userName, page, pageSize)
            .map { it.map { item -> item.asRepoDomain() } }
            .subscribeOn(Schedulers.io())
            .map {
                LoadResult.Page(
                    data = it,
                    prevKey = if (page == 1) null else page - 1,
                    nextKey = if (it.size < pageSize)null else page + 1
                )
            }
    }
}
