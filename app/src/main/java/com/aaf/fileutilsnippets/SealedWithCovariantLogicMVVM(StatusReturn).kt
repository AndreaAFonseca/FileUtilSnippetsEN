package com.aaf.fileutilsnippets


/*
 * File Name: SealedWithCovariantLogicMVVM(StatusReturn)
 * Author: Andréa A. Fonsêca
 * Creation Date: 2024-05-02
 * Description: This file is part of an Android project: FileUtilSnippets.
 * Contains implementations, classes, and/or functionalities related to 
   sealed classes, generic type 'out T' (covariant).
 * This project aims to demonstrate an effective and flexible way of
   managing different types of responses, using sealed classes and generic
   types. The implementation of the 'STATUS RETURN' design pattern and the
   use of the MVVM pattern contribute to the organization and readability
   of the code.
 *
 * Copyright (c) 2024 Andréa A. Fonsêca
 * Licensed under the MIT License.
 * See the LICENSE file for details.
**/

/*
* Approach to manage different types of responses,
* Using: sealed classes, generic types (out T)
* Advantages:
* - List Type Flexibility: enables the use of various data types
*           in lists, such as String, Int, User, etc., without requiring
*           modification of the RequestStatus class.
* - Code Reusability: The core logic of the RequestStatus class
*           can be reused in different parts of the project, enhancing
*           code organization and readability.
*  - Type Safety and Compilation: GENERIC TYPE: ensures thar the types
*           used in the list are compatible with the specified type
*           in the 'RequestStatus' class preventing compilation and runtime errors.
*           MISSING CASE DETECTION: Kotlin compiler checks if all
*           possible casesof the sealed class(RequestStatus)
*           are handled in the 'when' code, avoiding omision errors.
*  - Generic Error Handling/Manipulation: FLEXIBLES ERROR MESSAGES:
*           can return messages according to the app´s needs
*           (String, Int, custom ErrorCode)
*
* STRATEGY:
* - Focused on CREATING a SEALED CLASS, defining 2 states: 'SUCCESS' and 'ERROR'
*'success' has a generic type to represent the type of data returned
*'error' contains an error message of type String.
*
* - REPOSITORY(UserRepository): returns different types of list(Strings e Integers)
*                               in different calls
* - VIEWMODEL(UserViewModel): interacts with the repository and handles
*                             the different responses.
* - Handing different types of error messages, such as Strings, Intergers or,
*           other custom types
* - <out T>(generic type) used in the sealed class RequestStatus is
*             considered 'covariant', allowing the types that can be
*             used in the 'Success' class to be subtypes of the type
*             speciied in the declaration of RequestStatus. For instance, in
*             the RequestStatus<String> declaration, only objects of type
*             String ou seus s subtypes(CharSequence) can be used in the
*             Success class, ensuring type safety and preventing compitation
*             errors. However, if the declaration of RequestStatus<CHANGE>
*             occurs, the subtypes also change accordingly to the need. This
*             makes the RequestStatus class reusable in different parts of the
*             project(flexible and secure).
*
* - StatusReturn is the design pattern used to manage different types of responses
*                in asynchronous operations.
*
* - MVVM(Model-View-ViewModel) architecture:
*                   ViewModel = UserViewModel interacts with the
*                   Repository = UserRepository
*
* */

/* could have various types of list = List<String> - List<User> - List<School> in:

sealed class RequestStatus {
    class Success(var list: List<String>) : RequestStatus()
    class Error(var errorMessage: String) : RequestStatus() }

* To REUSE 'RequestStatus' the list HAS TO BE GENERIC, so '<out T>' is used,
by modifying the class YOU WILL BE ABLE TO store generic objects based on the 'out' pattern,
that is, store objects(different states) = sealed and establish an 'out' convariant realtionship(
sub + super can be assigned to super, INHERITANCE PATTERN), and it can be 'in' if necessary.

sealed class RequestStatus<out T> {// follows the inheritance pattern
// You need to pass the type 'T' and the list becomes 'T'
class Success<T>(var data: T) : RequestStatus<T>()
    //'errorMessage' will be generic(either text or number)
    // 'Nothing', Kotlin´s type for passing nothing
    class Error(var errorMessage: String) : RequestStatus<Nothing>() }

* To make the error generic, use the same logic as success:

class Error<T>(var errorMessage: T) : RequestStatus<T>()

* so the error messge ca be anything: number, error code, String

* 'ERROR'-> NORMALLY standardized, code, or specific type,to be handled in the interface.
In the given example, the error is already being handled, so it can already be a direct String.
E.g..: I retrieve the error message form Firebase/API/Library, translate it, and display a new text.

* cwith this, 'data' - can be of ANY TYPE as long as it RESPECTS THE INHERITANCE RELATIONSHIP

** Changing the REPOSITORY:
From:
class UserRepository(){
    fun saveUser( user: User, requestCallback: (RequestStatus) -> Unit ){
        val list = listOf("andréa", "marco", "maria")
        //val status = RequestStatus.Sucesso(list)
        val status = RequestStatus.Error(10, "API communication error")
        requestCallback.invoke( status )    } }
To:
    1 - You need define which data type you will work with: it can be any type
    fun saveUser(
    user: User,
    requestCallback: (RequestStatus<List<DATA TYPE?>>) -> Unit ){

    2 - The list now generic 'T' (DATA TYPE?)
    class Success<DATA TYPE?>(var data: DATA TYPE?) : RequestStatus<DATA TYPE?>()

    3 - Error now has only 1 parameter
    val status = RequestStatus.Error("API communication error")

    *** ViewModel CHANGE:
    From: only changes 'RequestStatus'
    class UserViewModel {
        fun saveUser(user: User, requestCallback: (RequestStatus) -> Unit){
            val userRepository = UserRepository()
            val userList = userRepository.save() }   }

    TO: will now receive the 'List<DATA TYPE?>'
    class UserViewModel {
        fun saveUser(
            user: User,
            requestCallback: (RequestStatus<List<String>>) -> Unit){
            val userRepository = UserRepository()
            val userList = userRepository.save()
            }
    }
 */

// *Defines a sealed class with generic type (out T)
//Benifit: can interchange the list type as desired: Strings, nnumbers, WITHOUT
//         MODIFYING the 'sealed' class
//Can be utilized across the ENTIRE project (reusable), due to its logic being:
sealed class RequestStatus<out T> {//follows the inheritance pattern
// *Classes representing different types of responses
class Success<T>(var data: T) : RequestStatus<T>()
    // *could be generic for enhanced flexibility
    class Error(var errorMessage: String) : RequestStatus<Nothing>()//'Nothing',to pass nothing
    //could be include a 'loading' state setup to indicate operation progress
}

data class User1(val name: String)

// *Implements the repository with different types of lists (strings and integers)
class UserRepository1(){
    //Strings
    /*fun saveUser( user: User, requestCallback: (RequestStatus<<List<String>>) -> Unit ){
        val list = listOf("andréa", "marco", "maria")
        val status = RequestStatus<.Success(list)
        //val status = RequestStatus<.Error("API communication error")
        requestCallback.invoke( status )
    }*/
    //Integers
    fun saveUser( user: User1, requestCallback: (RequestStatus<List<Int>>) -> Unit ){
        val list = listOf(1, 10, 60)
        val status = RequestStatus.Success(list)
        //val status = RequestStatus.Error("API communication error")
        requestCallback.invoke( status )
    }
}

// *Implements ViewModel to interact with therepository
class UserViewModel1 {
    //Strings
    /*fun saveUser(user: User, requestCallback: (RequestStatus<List<String>>) -> Unit){
        val userRepository = UserRepository()
        val listaUsers = userRepository.salvarUser( user, retornoRequisicao)
    }*/
    //Int
    fun saveUser(user: User1, requestCallback: (RequestStatus<List<Int>>) -> Unit){
        val userRepository = UserRepository1()
        val userList = userRepository.saveUser( user, requestCallback)
    }
}

// *simula o uso do ViewModel e trata diferentes respostas
fun main(){ // Simulando ambiente da Activity
    val user = User1("Andréa")
    val userViewModel = UserViewModel1()
    userViewModel.saveUser( user ) { RequestStatus->
        when (RequestStatus){
            //Sucesso retorna qualquer tipo de dado
            is RequestStatus.Success -> println("Sucesso lista: ${RequestStatus.data}")
            //Erro retorna a msg que é String, mas poderia ser genérica também
            is RequestStatus.Error -> println("Erro: ${RequestStatus.errorMessage}")
        }
    }
}

// 138 a 196
