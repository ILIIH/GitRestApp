package com.example.auth

import androidx.arch.core.executor.ArchTaskExecutor
import androidx.arch.core.executor.TaskExecutor
import androidx.lifecycle.MutableLiveData
import com.example.auth.loginFragment.LoginViewModel
import com.example.core.domain.User
import com.example.core.domain.helpers.ErrorEntity
import com.example.core.domain.helpers.Result
import com.example.gitapp.framework.GithubRepository
import com.example.gitapp.framework.network.GithubService
import com.example.gitapp.framework.network.UserNetworkEntity
import io.reactivex.Single
import org.hamcrest.CoreMatchers.instanceOf
import org.junit.Assert.assertThat
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
class AuthintificateTest {

    var gitRepository = mock<GithubRepository>()
    var gitServise = mock<GithubService>()

    @AfterEach
    fun afterEach() {
        Mockito.reset(gitRepository)
    }

    @Test
    fun `should autintificate with right credentials`() {
        val RightToken = "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"
        val RightLogin = "ILIIH"

        val userTest = User("test", 0, 0, RightLogin, 0)
        Mockito.`when`(gitRepository.authenticate(any())).thenReturn(Single.just(userTest))
        val viewModel = LoginViewModel(gitRepository)

        viewModel.autintificate(RightToken, RightLogin)

        val actual = viewModel.user.value

        Assertions.assertEquals(Result.Success(userTest), actual)
    }

    @Test
    fun `should not autintificate with wrong credentials`() {
        val RightToken = "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"
        val RightLogin = "ILIIH"

        val userTest = User("test", 0, 0, "WrongLogin", 0)
        Mockito.`when`(gitRepository.authenticate(any())).thenReturn(Single.just(userTest))
        val viewModel = LoginViewModel(gitRepository)

        viewModel.autintificate(RightToken, RightLogin)

        val actual = viewModel.user.value as Result

        assertThat(actual, instanceOf(Result.Error::class.java))
        assertThat((actual as Result.Error).error, instanceOf(ErrorEntity.Credentials::class.java))
    }

    @Test
    fun `should get correct data from auth`() {
        var actualData = MutableLiveData<User>()
        val TestTocken = "Qudfn127dscsQ"
        val userTest = UserNetworkEntity("test", 0, 0, "WrongLogin", 0)
        Mockito.`when`(gitServise.autintificate(any())).thenReturn(Single.just(userTest))

        val repo = GithubRepository(gitServise)
        val actual = repo.authenticate(TestTocken).subscribe { item ->
            Assertions.assertEquals(item, userTest)
        }
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
