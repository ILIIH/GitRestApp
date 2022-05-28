package com.example.gitapp.framework

import androidx.paging.PagingState
import androidx.paging.rxjava2.RxPagingSource
import com.example.gitapp.framework.network.GithubService
import com.example.gitapp.framework.network.RepoNetworkEntity
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RepositoryPageSourse @Inject constructor(val GitSevise: GithubService) : RxPagingSource<Int, RepoNetworkEntity>() {

    override fun getRefreshKey(state: PagingState<Int, RepoNetworkEntity>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, RepoNetworkEntity>> {
        val page: Int = params.key ?: 1
        val pageSize = 3

        return GitSevise
            .getRepoPerPage("ILIIH", page, pageSize)
            .subscribeOn(Schedulers.io())
            .map {
                LoadResult.Page(
                    data = it,
                    prevKey = if (page == 1) null else page - 1,

                    nextKey = if (page == 1) null else page.toInt() + 1
                )
            }
    }
}
