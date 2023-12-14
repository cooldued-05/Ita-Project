package com.laimis.tusplanner.login

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mad.notesibk.repository.AuthRepository
import kotlinx.coroutines.launch

//Source
//https://www.youtube.com/watch?v=n7tUmLP6pdo&list=PLUPcj46QWTDUaeVc7qkO_9CADrnxfTvbO&index=16
class loginViewModel(
    private val repository: AuthRepository = AuthRepository()
):ViewModel() {
    val currentUser = repository.currentUser
    val hasUser : Boolean
        get() = repository.hasUser()

    var loginUIState by mutableStateOf(LoginUIState())
        private set

    fun onUserNameChange(userName: String){
        loginUIState = loginUIState.copy(userName = userName)
    }
    fun onPasswordNameChange(password: String){
        loginUIState = loginUIState.copy(password = password)
    }
    fun onUserNameChangeSignUP(userName: String){
        loginUIState = loginUIState.copy(userNameSignUp = userName)
    }
    fun onPasswordChangeSignup(password: String){
        loginUIState = loginUIState.copy(passwordSignUp = password)
    }
    fun onConfirmPasswordChange(password: String){
        loginUIState = loginUIState.copy(confirmPasswordSignUp = password)
    }

    private fun validateLoginForm() =
        loginUIState.userName.isNotBlank() &&
                loginUIState.password.isNotBlank()

    private fun validateSignUpForm() =
        loginUIState.userName.isNotBlank() &&
                loginUIState.password.isNotBlank() &&
                loginUIState.confirmPasswordSignUp.isNotBlank()

    fun createUser(context: Context) = viewModelScope.launch {
        try{
            if(!validateSignUpForm()){
                throw IllegalArgumentException("email and password cannot be empty")
            }
            loginUIState = loginUIState.copy(isLoading = true)
            if (loginUIState.passwordSignUp !=
                loginUIState.confirmPasswordSignUp){
                throw  IllegalArgumentException(
                    "Password do not match"
                )
            }

            loginUIState = loginUIState.copy(signUpError = null)
            repository.createUser(
                loginUIState.userNameSignUp,
                loginUIState.passwordSignUp
            ) {isSuccessful ->
                if (isSuccessful){
                    Toast.makeText(
                        context,
                        "success login",
                        Toast.LENGTH_SHORT
                    ).show()
                    loginUIState = loginUIState.copy(isSuccessfulLogin = true)
                } else{
                    Toast.makeText(
                        context,
                        "Failed login",
                        Toast.LENGTH_SHORT
                    ).show()
                    loginUIState = loginUIState.copy(isSuccessfulLogin = false)
                }

            }


        }catch(e:Exception){
            loginUIState = loginUIState.copy(signUpError =e.localizedMessage )
            e.printStackTrace()
        }finally {
            loginUIState = loginUIState.copy(isLoading = false)
        }

    }

    fun loginUser(context: Context) = viewModelScope.launch {
        try{
            if(!validateLoginForm()){
                throw IllegalArgumentException("email and password cannot be empty")
            }
            loginUIState = loginUIState.copy(isLoading = true)

            loginUIState = loginUIState.copy(loginError = null)
            repository.login(
                loginUIState.userName,
                loginUIState.password
            ) {isSuccessful ->
                if (isSuccessful){
                    Toast.makeText(
                        context,
                        "success login",
                        Toast.LENGTH_SHORT
                    ).show()
                    loginUIState = loginUIState.copy(isSuccessfulLogin = true)
                } else{
                    Toast.makeText(
                        context,
                        "Failed login",
                        Toast.LENGTH_SHORT
                    ).show()
                    loginUIState = loginUIState.copy(isSuccessfulLogin = false)
                }

            }


        }catch(e:Exception){
            loginUIState = loginUIState.copy(loginError =e.localizedMessage )
            e.printStackTrace()
        }finally {
            loginUIState = loginUIState.copy(isLoading = false)
        }

    }
}
data class LoginUIState(
    val userName: String = "",
    val password: String = "",
    val userNameSignUp: String = "",
    val passwordSignUp: String = "",
    val confirmPasswordSignUp: String = "",
    val isLoading: Boolean  = false,
    val isSuccessfulLogin: Boolean = false,
    val signUpError :String? = null,
    val loginError: String? = null
)