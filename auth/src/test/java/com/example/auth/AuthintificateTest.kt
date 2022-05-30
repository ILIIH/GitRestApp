package com.example.auth

import com.example.core.domain.User
import com.example.core.domain.helpers.Result
import com.example.gitapp.framework.GithubRepository
import io.reactivex.Observable
import io.reactivex.Single
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertInstanceOf
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock

class AuthintificateTest {

    val gitRepository = mock<GithubRepository>()
    @AfterEach
    fun afterEach() {
        Mockito.reset(gitRepository)
    }

    @BeforeEach
    fun beforeEach() {
    }
    
    @Test
    fun `should not autintificate with wrong credentials`() {

        val RightToken = "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"
        val RightLogin = "ILIIH"

        Mockito.`when`(gitRepository.autentificate(RightToken, RightLogin)).thenReturn(
            Single.just(
                Result.Success(User("",12,1,"",1))
            )
        )
        val viewModel = LoginViewModel(gitRepository)


        viewModel.autintificate(RightToken, RightLogin)

        val actual = viewModel.user
        assertInstanceOf(Result.Success::class.java, actual)
    }

    @Test
    fun `should autintificate with right credentials`() {
    }
}
