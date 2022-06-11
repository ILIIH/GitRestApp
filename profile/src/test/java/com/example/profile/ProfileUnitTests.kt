package com.example.profile

import androidx.arch.core.executor.ArchTaskExecutor
import androidx.arch.core.executor.TaskExecutor
import androidx.paging.PagingData
import com.example.core.domain.Repo
import com.example.core.domain.User
import com.example.gitapp.framework.GithubRepository
import com.example.profile.profile.profileFragment.ProfileViewModel
import io.reactivex.Flowable
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.AfterEachCallback
import org.junit.jupiter.api.extension.BeforeEachCallback
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.extension.ExtensionContext
import org.mockito.Mockito
import org.mockito.kotlin.any
import org.mockito.kotlin.mock

@ExtendWith(InstantExecutorExtension::class)
class ProfileUnitTest {

    var gitRepository = mock<GithubRepository>()

    @AfterEach
    fun afterEach() {
        Mockito.reset(gitRepository)
    }

    @Test
    fun `should receive same repo paged lists`() {

        val repo1 = Repo(1, "TestName", "TestFullName", "TestDescription", "", 2, 2, "")
        val dataset = PagingData.from(listOf(repo1, repo1, repo1, repo1, repo1))
        val user = User("", 1, 1, "TestLogin", 1)

        Mockito.`when`(gitRepository.getRepository(any())).thenReturn(Flowable.just(dataset))
        val viewModel = ProfileViewModel(gitRepository)

        viewModel.setsUser(user)

        val actual = viewModel.repo.value
        Assertions.assertEquals(actual, dataset)
    }

    @Test
    fun `should receive not same repo paged lists`() {

        val repo1 = Repo(1, "TestName", "TestFullName", "TestDescription", "", 2, 2, "")
        val dataset = PagingData.from(listOf(repo1, repo1, repo1, repo1, repo1))
        val user = User("", 1, 1, "TestLogin", 1)

        Mockito.`when`(gitRepository.getRepository(any())).thenReturn(Flowable.just(dataset))
        val viewModel = ProfileViewModel(gitRepository)

        viewModel.setsUser(user)

        val dataset2 = PagingData.from(listOf(repo1, repo1, repo1, repo1))

        val actual = viewModel.repo.value
        Assertions.assertNotEquals(actual, dataset2)
    }

    @Test
    fun `should receive current user data`() {
        val testUser = User("", 1, 1, "TestLogin", 1)
        val viewModel = ProfileViewModel(gitRepository)

        val repo1 = Repo(1, "TestName", "TestFullName", "TestDescription", "", 2, 2, "")
        val dataset = PagingData.from(listOf(repo1, repo1, repo1, repo1, repo1))
        Mockito.`when`(gitRepository.getRepository(any())).thenReturn(Flowable.just(dataset))

        viewModel.setsUser(testUser)
        val actual = viewModel.user.value
        Assertions.assertEquals(actual, testUser)
    }
}
class InstantExecutorExtension : BeforeEachCallback, AfterEachCallback {

    override fun beforeEach(context: ExtensionContext?) {
        ArchTaskExecutor.getInstance()
            .setDelegate(object : TaskExecutor() {
                override fun executeOnDiskIO(runnable: Runnable) = runnable.run()

                override fun postToMainThread(runnable: Runnable) = runnable.run()

                override fun isMainThread(): Boolean = true
            })
    }

    override fun afterEach(context: ExtensionContext?) {
        ArchTaskExecutor.getInstance().setDelegate(null)
    }
}
