package com.aaf.fileutilsnippets


/*
 * File Name: SealedLogicMVVM
 * Author: Andréa A. Fonsêca
 * Creation Date: 2024-04-30
 * Description: This file is part of an Android project: FileUtilSnippets.
 * Contains implementations, classes, and/or functionalities related to 
 SEALED CLASS in MVVM architecture projects without useCase.
 * This project aims to exemplify the use of Sealed Class to manage
 the return of requests through responsibility layers. A sealed
 class represents different states of the return (success, error, etc.) and
 encapsulates relevant data in each case.
 *
 * Copyright (c) 2024 Andréa A. Fonsêca
 * Licensed under the MIT License.
 * See the LICENSE file for details.
**/

/*
OBJECTIVE of the sealed class: to easily control what is diplayed,
WITHOUT having to validate in all layers.
*In this file::
- MODEL = 'User' - independente of View and ViewModel
- VIEW = 'fun main()' - interacts with ViewModel, without depending on Repository
- Controller = 'UserViewModel' and 'UserRepository'
- ViewModel = 'UserViewModel', encapsulates business logic and exposes methods to
the View, using the sealed class to manage the request return..
- Repository = 'UserRepository' performs persistence operations and uses the
sealed class to encapsulate the request result.
- UseCase = omitted, modularizes business logic
- communication between layers is done through callbacks, wich can lead to
coupling between classes.

* The 'screen'(fun main) DIRECTLY HANDLES THE STATUS, creates a lambda that
expects 'RequestResponse1  ->'
* The viewModel ONLY fowards the function:
'requestResult1: (RequestResponse1) -> Unit'
* In the repository IT CONFIURES WHETHER IT SUCCEEDED OR NOT, receives the lambda, as
parameter and stores the result in a val, which will be used in the
invoke(execution of the lambda), being able to store more than one value and
the repository or useCase can change the return using the invoke and this is
handled directly in the activity.
* Does not need to return anything, already does everything, but if you want you can return
a true/false, because the repository itself receives the sealed class, configures
a value for it and there in the Activity is where the handling is done, WITHOUT NEEDING
TO PASS THE RETURN FROM: repository to UseCase, then to viewModel
and only then to the activity.

** the logic is inverted: passes the function that handles the error in the ACTIVITY,
which will send it to the viewModel/UseCase/Repository and the repository
configures the return of the function in the 'invoke'
** the sealed class configures different states, which can be
stored in the repository val.

*** Takes an object/function in the activity, passes it to the viewModel,
which will pass IT to the repository. In the repository, it changes the
object being used in the activity. Simpler because
it doesn't need to keep returning and passing the 'result' through
the layers, just passing the lambda function that is executed
in the viewModel.
*/

sealed class RequestResponse1 {//stores objects representing different states
class Success(var status: String, var itemList: List<String>) : RequestResponse1()
    class Error(var status: String) : RequestResponse1()
    //object None : RequestResponse1()

    // 'status' can have a range of values controlled by an 'enum' with multiple constants managing the status
    /*enum class RequestResponseEnum( ) {// stores constants representing fixed states
      SUCCESS, ERROR
      }*/
}

data class User(private val name: String) {}

class UserRepository {
    // saves data from another class (UserViewModel) due to the lambda
    fun save( user: User, requestResult1: (RequestResponse1) -> Unit ){
    // saves data and uses the 'invoke' method to call the function execution
    // When making an API call, a 'RequestResponse1' instance can be created representing either 'Error' or 'Success',
    // both work regardless of which one is used

    val result = RequestResponse1.Success("400", listOf("Andréa", "Marco"))
    //val retorno = RetornoRequisicao1.Erro("400")
    requestResult1.invoke( result )
    }
}

// Allows communication between repository and viewModel using the sealed class

class UserViewModel {
// enables a lambda to be included within the activity
fun addUser( user: User, requestResult1: (RequestResponse1) -> Unit ){
    // repository created here for educational puposes = NOT CREATED HERE in real-life scenarios
    val userRepository = UserRepository() // NO useCase to simplify
    //passes the result to the lambda
    userRepository.save( user, requestResult1 )
}
}

fun main() {

val userViewModel = UserViewModel()

//simulates actions INSIDE THE ACTIVITY
val user = User("Andréa")// interface data
// The following lambda function is needed within the activity, using a LAMBDA and
// passing the lambda function 'result' to the viewModel, already containing the requestResult parameter

userViewModel.addUser( user ){ RequestResponse1 ->
    //com isso consegue controlar o estado da aplicação usando o RetornoRequisicao1
    //o estado da aplicação é passado direto para a função lambda
    //pode retornar uma lista também
    when(RequestResponse1){
        is RequestResponse1.Success ->
            println("Success Sealed: ${RequestResponse1.status} list: ${RequestResponse1.itemList}")
        is RequestResponse1.Error ->
            println("Erro Sealed status: ${RequestResponse1.status}")
        // object does not instantiate and only uses 'is'
        // RequestResponse1.None -> println("None")
    }
}
}
