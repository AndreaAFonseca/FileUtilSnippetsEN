package com.aaf.fileutilsnippets


/*
 * File Name: Sealed
 * Author: Andréa A. Fonsêca
 * Creation Date: 2024-04-29
 * Description: This file is part of an Android project: FileUtilSnippets.
 * Contains implementations, classes, and/or functionalities related to
 Sealed Class and Sealed Interface.
 * This project aims to be a practical guide to learning how to use sealed
 classes and sealed interfaces, with examples and use cases.
 *
 * Copyright (c) 2024 Andréa A. Fonsêca
 * Licensed under the MIT License.
 * See the LICENSE file for details.
**/

/*
* * Sealed Class: Abstract classes that restrict inheritance to a finite
*                 and predefined set of subclasses (regular or sealed),
*                 known at compile time. In other words, sealed defines
*                 explicitly which classes can inherit from the sealed
*                 class, preventing runtime errors caused by invalid types.
*
* Data: It has a data structure, STORES OBJECTS in different states.
*       Unlike regular classes, it works with object references.
*
* Characteristics: - Allows the creation of nested classes (organizes the code).
*                  - Guarantees type safety (compiler verifies the type of the object).
*                  - Facilitates the implementation of design patterns (can model complex behaviors in an organized and reusable way).
* (State Machines, Visitor Pattern e Expressões Discriminated Union)
*                  - Adds specific logic for each data type.
*                  - Models different states of an object in a safe and organized way.
*
* Utilities: - Handles different types of errors (widely used with APIs).)
*            - Centralizes subclasses (facilitates code organization and management).
*            - Models class hierarchy (represents complex relationships between different types of objects in a clear and intuitive way).
*            - Makes it easier to create objects that can be sent to the screen and controlled more easily.
*            - Can have more configured values.
*            - Communicates if something went wrong. (facilitates debugging and error correction).
*            - Allows a complex hierarchy of related types.
*            - Defines abstract classes with a finite number of subclasses known at compile time.
*            - Ensures that the code is always dealing with a valid type, preventing runtime errors and making the code more robust.
*            - Handles secure and exhaustive types.
*            - Organizes and manages code efficiently.
*            - Ensures that objects instantiated from the sealed class belong to one of the defined subclasses.
*
* Advantages: - Can have methods to process.
*             - Type Safety: Prevents runtime errors caused by invalid types.
*             - Conciseness: Code is more readable and less repetitive.
*             - Expressiveness: Allows modeling class hierarchies in a clear and intuitive way.
*             - Exhaustiveness: Ensures that all cases are handled. The Kotlin compiler checks
*                               that all possible subclasses are covered (avoids omissions and
*                               ensures that all cases are handled).
*             - Organization: Code is more structured and easier to maintain, as it groups related
*                             types into a single structure, promoting a consistent and standardized
*                             coding style, making the code easier to read, maintain, and reuse.
*             - Improved Testability: Facilitates unit tests, as each subclass can be tested separately
*                                     and ensures that all cases are covered.
*
* Benefits: - Increased code reliability: Reduces the likelihood of errors and failures.
*           - Easier maintenance: Organization makes the code easier to understand and modify.
*           - Improved readability: Code organization makes it more self-explanatory.
*           - Code reusability: Allows the creation of reusable and reliable components.
*           - Reduces runtime type errors: Handles all possible cases preventing app crashes.
*           - Robust and reliable code: Handles all scenarios appropriately.
*           - Improved testability.
*
* Disadvantages: - Learning curve: Challenging for beginners.
*                - Rigidity: Class structure must be defined at compile time.
*                - Incompatible with Java.
* */

/*
* * Sealed Interface: Interface that can have finite implementations known at
*                     compile time, similar to SEALED CLASS, but DOES NOT ALLOW INHERITANCE.
*                    Suitable for DEFINING common behaviors among subclasses.
*
* * WHEN TO USE:
* * SEALED-> MORE DYNAMIC DATA (does not work only with constants like enums).
*         -> DIFFERENT STATES.
*         -> MODEL CLASS HIERARCHIES WITH SUBCLASSES AND INHERITANCE.
*
* ENUMS-> when you need to represent finite sets of constant values.
*
* SEALED INTERFACE-> when you need to define common behaviors between subclasses without inheritance.
* */

/*Sealed vs enums : SIMILARTIES
* Sealed An extension of enums, offering greater flexibility and robustness.
*        A middle ground between classes and enums.
*/

//Comparing Classes, Enums, and Sealed Classes: A Restaurant Analogy
/*//CLASS: - Can create various types of restaurants with unique characteristics.
  //       - Allows adding new types in the future.
  //       - Less compiler safety: Does not guarantee that valid dishes are served.

class Restaurant(val name: String, val culinaryType: String, val address: String) {
//Allow flexibility in implementation and inheritance
    private val availableDishes: List<String> = listOf("Pizza Margherita", "Lasagna Bolognese")

    fun serveDish(dishName: String) {
        if (dishesAvailables.contains(dishName)) {
            println("Serving dish: $disheName")
        } else {// AVOIDING runtime errors caused by invalid types.
        // REQUIRES CAREFUL HANDLING TO PREVENT COMPILE-TIME ERRORS (SAFETY)
            println("Error: Dish $dishName is not available")
        }
    }
}

fun main() {
    val italianRestaurant = Restaurant("La Cucina", "Italiana", "Rua Augusta, 123")

    italianRestaurant.serveDish("Pizza Margherita") // Works
    italianRestaurant.serveDish("Lasagna Bolognese") // Works
    italianRestaurant.servirPrato("Sushi") // rints error message, TYPE DOES NOT EXIST
} // OUTPUT: Serving dish: Margherita Pizza + Serving dish: Lasagna Bolognese + Error: Sushi dish is not available
*/

/*
ENUM: - security, compiler guarantees only valid dishes are ordered (avoids compilation errors).
//      - access dishes clearly and concisely using enums
//      - new dish types can be added by modifying the enum
//      - does not have types such as complex features or variation form restaurant to restaurant

enum class Menu(val name: String) {
// Restricted to a fixed set of values, limiting flexibility and not allowing methods.
// GUARANTEES TYPE SAFETY WITHIN THE FIXED SET

    PIZZA_MARGHERITA("Pizza Margherita"),
    LASAGNA_BOLOGNESE("Lasanha de Carne")
}

class Restaurant(val name: String, val culinary: String, val address: String) {

    fun serveDish(menu: Menu) {
        try {
            Menu.valueOf(menu.name)
            println("Serving dish: ${menu.name}")
        } catch (e: IllegalArgumentException) {
            println("Error: Dish ${menu.name} unavailable")
        }
    }
}

fun main() {// Runtime error when value doesn't exist
    val italianRestaurant = Restaurant("La Cucina", "Italiana", "Rua Augusta, 123")

    italianRestaurant.serveDish(Menu.PIZZA_MARGHERITA) // Works
    italianRestaurant.serveDish(Menu.LASAGNA_BOLOGNESE) // Works
    italianRestaurant.serveDish(Menu.valueOf("SUSHI")) // Illegal argument error
} // OUTPUT: WITHOUT 'sushi' = Serving dish: PIZZA_MARGHERITA + Serving dish: LASAGNA_BOLOGNESE
  // OUTPUT: WITH 'sushi' =  NO constant enum  com.aaf.fileutilsnippets.Menu.SUSHI and crashes

*/

/* SEALED: - combines flexibility and security, can define a predefined set of dish
//           types with the possibility of adding new types in the future
//         - can model dishes with complex characteristics or varying according to the type of restaurant
//         - avoids runtime errors: compiler ensures that only valid dish types are selected
//         - groups dish types in a clear way


sealed class Dish(val name: String) {//Combines the safety of enums with the flexibility of classes:
    //Finite set of subclasses known at compile time.
    //can add new dish types
    class PizzaMargherita(name: String) : Dish(name) {//inheritance
        fun prepare() {//Permits methods in subclasses.
            println(‘Preparing Margherita pizza’)
        }
    }

    class LasagnaBolognese(name: String) : Dish(name) {//inheritance
        fun prepare() {//Permits methods in subclasses.
            println(‘Preparing lasagna bolognese’
        }
    }

     abstract class DishWithMass(name: String) : Dish(name) {//inheritance
        abstract value typeDough: String
        abstract val sauce: String

        fun prepare() {
            println(‘Preparing $typeDough with $sauce’)
        }
    }

    class PennePomodoro(name: String) : DishWithMasta(name) {
        override val typePasta = ‘Penne’
        override val sauce = ‘Tomato’
    }

    class SpaghettiCarbonara(name: String) : DishWithMass(name) {
        override val typeDish = ‘Spaghetti’
        override val sauce = ‘Carbonara’
    }
}

class Restaurant(val name: String, val typeCulinary: String, val address: String) {

    fun serveDish(dish: Dish) {
        when (dish) {
            is Dish.PizzaMargherita -> dish.prepare()
            is Dish.LasagnaBolognese -> dish.prepare()
            is Dish.PennePomodoro -> dish.prepare()
            is Dish.SpaghettiCarbonara -> dish.prepare()
            else -> println(‘Dish not available’)
        }
    }
}

fun main() {//Guarantee that all cases are handled (completeness).
    val italianRestaurant = Restaurant(‘La Cucina’, ‘Italiana’, ‘Rua Augusta, 123’)

    italianRestaurant.serveDish(Dish.PizzaMargherita("Pizza Margherita")) // Works
    italianRestaurant.serveDish(Dish.LasagnaBolognese("Lasagna Bolognese")) // Works
    italianRestaurant.serveDish(Dish.PennePomodoro("Penne with Pomodoro")) // Works
    italianRestaurant.serveDish(Dish.SpaghettiCarbonara("Spaghetti Carbonara")) // Works
    //ensures that only valid dish types are selected
   // italianRestaurant.serveDish(Dish.Sushi("Sushi"))// *Compilation error: No Dish type matches the name ‘Sushi’
}
*/

/*
sealed class-> works with object references(= regular classes)
//         difers from enum - uses primitive types or fixed set of constants
//         similar to classes - allows storing state and behavior in its subclasses
//PRIMARY DIFFERENCE: subclass restriction, ensuring type safety (avods runtime errors)

sealed class Result {  //defines result types
    data class Success(val value: Int) : Result()
    data class Error(val message: String) : Result()
}

fun processResult(result: Result): Unit {  //receives object and response matches type
    when (result) {
        is Result.Success -> println("Sucaess: ${result.value}")
        is Resultado.Erro -> println("Error: ${result.message}")
    }
}

fun main() {
    // create objects
    val result1 = Result.Success(10)
    val result2 = Result.Error("An error occured during processing.")

    //call function for each object
    processResult(result1)
    processResult(result2)
}

*/

/*
* * Sealed vs Enums Differences
* Purpose: sealed-> models class hierarchies with subclasses at compile time
*           enum-> represents a finite set os constant values.
* Subclasses: sealed-> custom. Closed(must declare all subclasses)
*             enum-> pre-defined values. Open(can have underclared subclasses)
* Inheritance: sealed-> supports multiple inheritance
*          enum-> does not multiple inheritance
* Functions and Properties: sealed-> can have functions and properties
*                           enum-> canot have functions and properties
* Objects: sealed-> can be objects
*          enum-> cannot be objects
* Exhaustiveness: sealed-> compiler ensures all cases are handled
*                 enum-> compiler does not ensure exhaustiviness
*/

/*
* *WHEN TO USE:
*      sealed-> e.g.: modeling UI states, network types, error handling.
*                   - needs inheritance, interfaces, or polymorphism
*                   - needs to ensure all possible cases are handled (exhaustiveness).
*                   - needs functions/properties in subclasses.
*                   - desires to follow design patterns.
*                   - flexibility to add new subsclasses or implement specific methods for each subclass.
*
*      enum-> e.g.: representing colors, sizes, user preferences.
*                   - needs to represnt a simple and finite set of constant values.
*                   - does NOT need inheritance or functions/properties in sublcasses.
*                   - cumbersome to add
*                   - code simplicity and performance(compiled into Java)
* */

/*
* * SEALED Use Cases:
*   - modeling complex class hierarchies with multiple inheritance
*   - implementing state machines or visitor patterns
*   - representing data types with finite and well-defined values
*   - managing errors and exceptions ina safe and organized manner
*   - creating secure and reliable APIs
* */

/*
// Close set of subclasses
sealed class Animal { // defines animal types, ensuring only valid types
    object Dog : Animal()
    object Cat : Animal()
    object Bird : Animal()
}
// adaptable code for different scenarios
fun feedAnimal(animal: Animal) { // inheritance takes object and displays a message
    when (animal) {
        is Animal.Dog -> println("The dog is eating meat.")
        is Animal.Cat -> println("The cat is eating food.")
        is Animal.Bird -> println("The bird is eating seeds.")
    }
}

fun main() {
    // Creating animals
    val dog = Animal.Dog
    val cat = Animal.Cat
    val bird = Animal.Bird

    // Feeding each animal
    feedAnimal(dog)
    feedAnimal(cat)
    feedAnimal(bird)
}
*/

/*
UI state modeling: example: Loading Screen, Error Screen, Success Screen.
sealed class NetworkResponse {
    data class Success(val data: Any) : NetworkResponse()
    data class Failure(val error: String) : NetworkResponse()
    object Loading : NetworkResponse()
}

fun processData(data: Any) {
    // Processes the data received in tge success response
    // Example: update the UI with the data
    println("Sucess with data")
}

fun showError(error: String) {
    // Displays the error message
    // Example: show a Toast or AlertDialog
    println("Failure with data")
}

fun showLoading() {
    // Displays a loading indicator
    // Example: show a ProgressBar or a shimmer
    println("Loading data")
}

fun myFunctionThatReceivesResponse(response: NetworkResponse) {
    when (response) {
        is NetworkResponse.Success -> processData(response.data)
        is NetworkResponse.Failure -> showError(response.error)
        NetworkResponse.Carregando -> showLoading()
    }
}

fun main() {
    // Example of sealed class usage
    val response1 = NetworkResponse.Success("Data received successfully!")
    val response2 = NetworkResponse.Failure("An error occured while processing the request.")
    val response3 = NetworkResponse.Loading

    myFunctionThatReceivesResponse(response1)
    myFunctionThatReceivesResponse(response2)
    myFunctionThatReceivesResponse(response3)
}
*/

/*
// State Machines-> perfect for system states. Can define a finite and well-defined set of possible
//                  states and transitions between them.

sealed class ScreenState {
    object Loading : ScreenState()
    object ContentDisplayed : ScreenState()
    object LoadingError : ScreenState()
}

fun processScreenState(state: ScreenState) {
    when (state) {
        ScreenState.Loading -> showLoading()
        ScreenState.ContentDisplayed -> showContent()
        ScreenState.LoadinggError -> showError()
    }
}

fun showLoading() {
    // Display loading indicator (ProgressBar, etc.)
    println("loading screen")
}

fun showContent() {
    // Display screen content(list of items, etc.)
    println("showing list")
}

fun showError() {
    // display error message (Toast, AlertDialog, etc.)
    println("error processing screen")
}

fun main() {
    // Creating screen state objects
    val state1 = ScreenState.Loading
    val state2 = ScreenState.ContentDisplayed
    val state3 = ScreenState.LoadingError

    // Processing screen states
    processScreenState(state1)
    processScreenState(state2)
    processScreenState(state3)
}

*/

/*
// Visitor Pattern-> elagantly and safely implements

sealed class Element { //defines element types = 'Number' e 'Text'
    data class Number(val value: Int) : Element()
    data class Text(val mesage: String) : Element()
}

//abstract class defines 'accept', to interact with the visitor in a specific way
interface Visitor {   //defines the 'visit' method that each subclass MUST implement to interact with the elements
    fun visitNumber(number: Element.Number): Int
    fun visitText(text: Element.Text): String
}

//each subclass implements its own 'accept' to interact with the visitor in a specific way
class SumVisitor : Visitor {   //implements for summing and showing text
    override fun visitNumber(number: Element.Number): Int {
        return number.value
    }

    override fun visitText(text: Element.Text): String {
        return text.mesage
    }
}

fun main() {
    // Creation of elements
    val number1 = Element.Number(10)
    val number2 = Element.Number(5)
    val text = Element.Text("Hello, world!")

    // Creation od the visitor
    val sumVisitor = SumVisitor()

    // Sum of elements using the visitor
    val sum = sumVisitor.visitNumber(number1) + sumVisitor.visitNumber(number2)
    println("Sum of numbers: $sum")

    // Demonstration of behavior difference with text
    println("Sum of text: ${sumVisitor.visitText(text)}") // Returns 0, as defined in the visitor implementation
}

*/

/*
// Discriminated Union(DUs)-> equivalent to DUs in other languages.
//              - allows representing values of different types safely and concisely
//              - compiler ensuresthat only one of the possible types is stored in a
//                Sealed Class variable, avoiding runtime errors

sealed class Result {   // defines result types
    data class Success(val value: Int) : Result()
    data class Error(val mesage: String) : Result()
}


fun processResult(result: Result): Unit {   //receives the object and displays the mesage according to the result
    when (result) {   // facilitates the analysis of the result type and prints correctly
        is Result.Success -> println("Success: ${result.value}")
        is Result.Error -> println("Error: ${result.mesage}")
    }
}

fun main() {
    //create objects
    val result1 = Result.Success(10)
    val result2 = Result.Error("An error occured during processing.")

    processResult(result1)
    processResult(result2)
}

*/

/*
//enum A Better Choice
 //both sealed and enum have the result
sealed class RequestResponse {//Stores objects (different states)
    //Inner class HAS an INHERITANCE realationship with the class itself
    //subclasses inherit from the class
    object Success: RequestResponse()
    object Error: RequestResponse()
}
enum class RequestResponseEnum( ) {//Stores constants (fixed state)
    SUCCESS, ERROR
}
//Can only configure a single value, a constant that cannot be changed
//enum class RequestResponseEnum(val status: Boolean ) {//Stores constants (fixed state)
//    SUCCESS(true), ERROR(false), LOADING(true) }

fun main(){
    //Sealed
    val requestStatus: RequestResponse = RequestResponse.Success
    //allows different checks
    when( requestStatus ){
        RequestResponse.Success -> println("Success Sealed")
        RequestResponse.Error -> println("Error Sealed")
    }
    //Enum
    val requestStatusEnum = RequestResponseEnum.SUCCESS
    //allows different checks
    when( requestStatusEnum ){
        RequestResponseEnum.SUCCESS -> println("Success")
        RequestResponseEnum.ERROR -> println("Error")
    }
}
*/

// sealed A Better Choice = works: DYNAMIC DATA(not only constant values like enum); DIFFERENT STATES
sealed class RequestResponse0 {
    //Storea objects (different states), subclasses inherit form the class
    //nedd to send the list if it exists
    //within 'status' could use 'enum' to set different
    //error states that could occur
    class Success(var status: String, var itemList: List<String>) : RequestResponse0()
    class Error(var status: String): RequestResponse0()
    object None: RequestResponse0()

}
enum class RequestResponseEnum( ) {//Stores constants (fixed state)
SUCCESS, ERROR
}

fun main() {
    //Sealed
    val requestStatus: RequestResponse0 = RequestResponse0.Success(
        "400", listOf("Andréa", "Marco")
    )
    /*val requestStatus: RequestResponse0 = RequestResponse0.Erro( "200" )
    val requestStatus: RequestResponse0 = RequestResponse0.None
    */

    //Allow different checks
    when (requestStatus) {//usa 'is' porque 'Sucesso' e 'Erro' são classes, está testando instancias
        is RequestResponse0.Success ->
            println("Success Sealed: ${requestStatus.status} lista: ${requestStatus.itemList}")
        is RequestResponse0.Error ->
            println("Erro Sealed status: ${requestStatus.status}")
        //object não instancia e n~so usa 'is'
        RequestResponse0.None ->
            println("None")
    }
}