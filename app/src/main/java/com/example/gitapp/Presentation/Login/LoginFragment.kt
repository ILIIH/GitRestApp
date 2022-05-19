package com.example.gitapp.Presentation.Login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.core.Data.GithubService
import com.example.core.Domain.User
import com.example.gitapp.Presentation.ViewModelFactory
import com.example.gitapp.R
import com.example.gitapp.databinding.FragmentLoginBinding
import com.example.gitapp.di.MyApplication
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.*
import javax.inject.Inject





class LoginFragment : Fragment() {

    @Inject
    lateinit var  retroServise : GithubService ;

    @Inject
    lateinit var vmFactory : ViewModelFactory
    lateinit var ViewModel : LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val bindidg = FragmentLoginBinding.inflate(inflater)

        (requireActivity().applicationContext as MyApplication).appComponent.LoginInject(this)


        ViewModel =  ViewModelProvider(this,vmFactory).get(LoginViewModel::class.java)

        println("START ---> ")

      /*   retroServise.getUser("ILIIH").subscribeOn(Schedulers.io()).subscribe(
            {next ->
                println("NEXT ---> "  + next.login)},
            {error -> println("ERROR ---> " + error.message) });
*/
        retroServise.Autintificate("token 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").subscribeOn(Schedulers.io()).subscribe(
            {next ->
                println("NEXT ---> "  + next.login)},
            {error -> println("ERROR ---> " + error.localizedMessage) });



        bindidg.EnterButton.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_profileFragment)

        }

        return bindidg.root
    }


}