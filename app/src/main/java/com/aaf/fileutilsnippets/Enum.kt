package com.aaf.fileutilsnippets


/*
 * File Name: Enum
 * Author: Andréa A. Fonsêca
 * Creation Date: 2024-04-26
 * Description: This file is part of an Android project: FileUtilSnippets.
 * Contains implementations, classes, and/or functionalities related to 
 the Enum class concept, highlighting advantages and showcasing use cases.
 *
 * Copyright (c) 2024 Andréa A. Fonsêca
 * Licensed under the MIT License.
 * See the LICENSE file for details.
**/


/* Enums: Evolution of 'object', a class with a special data type that allows
 *        defining a set of constant and named values. It's a powerful and versatile
 *        tool that can be used to improve code quality, readability, and reliability.
 *
 * Data: Pre-defined constants separated by commas. Constants with constructors
 *       are considered objects and can store additional data and have specific
 *       behaviors.
 *
 * Characteristics: Immutability of values and the ability to associate additional
 *                  data with constants.
 *
 * Utility: Representing sets of discrete values such as colors, states, or file
 *          types. Its most basic use is 'type-safe enums', ensuring type safety.
 *          For example, representing days of the week, months of the year, or access
 *          levels in a system.
 *
 * Advantages: Facilitates working with constants, allowing efficient control and
 *             testing of values. It can have methods that manage, validate, or
 *             process its values. Its goal is not to have methods that perform
 *             actions. Reduces errors in managing states.
 *
 * Benefits: Provides type safety, improves code readability and organization, and
 *           reduces errors in managing states.
 */

/*
 // -* Evolution object -> enum. Both (object/enum) have the same RESULT
 // -* Advantage of 'enum' is direct access, while 'object' needs to be referenced.

object Constants {// Has to pass the value, evolved to 'enum'
    const val IN_PREPARATION = "in_preparation"
}

//Evolution: no longer need to pass the value, can access for testing
enum class OrderStatusEnum {// Stores constants (fixed state, will not change)
AWAITING_PAYMENT, PROCESSING_PAYMENT, IN_PACKAGING, SHIPPING, RECEIVED
}

fun main() {
    //'object'
    val status = Constants.IN_PREPARATION
    if (status == Constants.IN_PREPARATION) {
        println("in preparation")
        println(status)
    }
    // RUN => in preparation   +   in_preparation

    //'enum', no need to instantiate
    val status1 = OrderStatusEnum.RECEIVED
    if( status1 == OrderStatusEnum.RECEIVED){// do something if order received
        println("received")
        println(status1)
    }
    // RUN => received   +   RECEIVED
}
*/

/*
* Class vs Enum
* Objective: CLASS-> Represents an OBJECT with state (attributes that store data) and
*                    behavior (methods that define the actions the object can perform).
*            ENUM-> Defines a FIXED and ORDERED set of named constants. Useful for
*                   representing related values that don't change during execution.
*
* Attributes: CLASS-> Can have an unlimited number of attributes, public or private.
*             ENUM-> Can only have CONSTANT attributes, typically to store metadata
*                    about the constants
*
* Methods: CLASS-> Can have an unlimited number of methods, public or private, that
*                  define the object's behavior.
*          ENUM-> Can have static methods (within the companion object) to aid in
*                 constant manipulation, but are NOT GENERALLY used to perform actions.
*
* Mutability: CLASS-> Attributes can be MUTABLE (values can be changed after object creation).
*             ENUM-> Constants are IMMUTABLE (values CANNOT be changed after definition).
*
* Inheritance: CLASS-> Can inherit from another class, reusing its attributes and methods.
*              ENUM-> CANNOT inherit from other classes or enums.
*
* When to Use: CLASS: - When you need to represent an object with state and behavior.
*                     - When you need to create instances of an object with different values.
*                     - When you need to inherit from an existing class.
*              Enum: - When you have a fixed set of related values that don't change.
*                    - When you want to improve code readability and type safety.
*                    - When you need to use switch statements to handle different cases.
* */

//Identifying Class and Enum
// Classes can be used to perform something
class Order { // Represents the Order object with state (id, orderedItem, status)

    private val id: Int = 0
    private val orderedItem: String = ""
    private val status: OrderStatus = OrderStatus.WAITING_FOR_PAYMENT

    fun executeSomething() { // behavior (executeSomething)
        println("Order ID: $id") // accesses id
        println("Ordered Item: $orderedItem") // accesses orderedItem
        println("Order Status: $status") // accesses status from the enum
        println("Class Order function: executed something")
    }
}

// Controls the status of the order, fixed state, not going to change
// the enum can have methods to manage the constants
enum class OrderStatus {// Controls the order status, defines a FIXED and ORDERED set of CONSTANTS representing different statuses of an order
//IMMUTABLE CONSTANTS ensure that the order status CANNOT BE ACCIDENTALLY CHANGED
WAITING_FOR_PAYMENT, PROCESSING_PAYMENT, BEING_PREPARED, SHIPPING, RECEIVED;

    // to have methods for constant management use:
    companion object {// NOT INTENDED FOR EXECUTING ACTIONS
        // the methods CAN return the status types, MANAGE THE CONSTANTS
        fun executeSomething() {
            println("companion object: executed something")
        }
    }
}

fun main() {
    OrderStatus.executeSomething() // Calls companion object method
    var order = Order() // Creates an Order object
    //order.orderedItem = "Green Shirt" // Sets itemPedido (not shown in output)
    order.executeSomething() // Calls class method
}// RUN => companion object: executed something + Order ID: 0 +
// Ordered Item: + Order Status: WAITING_FOR_PAYMENT +
// Class Order function: executed something

/*
//Some use cases class Order and enum class OrderStatus:

        class Order { // the Order object
        private val id: Int = 0
        private val orderedItem: String = ""
        private val status: OrderStatus = OrderStatus.WAITING_FOR_PAYMENT

        fun executeSomething() { // object behavior, able to access attributes
            println("Order ID: $id")
            println("Ordered Item: $orderedItem")
            println("Order Status: $status")
            println("Order class function: executed something")
        }
    }

        enum class OrderStatus {// defines CONSTANTS for different order statuses
            //IMMUTABLE ensure that the order status CANNOT BE ACCIDENTALLY CHANGED
            WAITING_FOR_PAYMENT, PROCESSING_PAYMENT, BEING_PREPARED, SHIPPING, RECEIVED;

        companion object {// methods CAN return status types, MANAGE THE CONSTANTS
            fun executeSomething() {
            println("companion object: executed something")
            }
        }
    }

***  Using 'companion object' of the enum, DOES NOT ALTER the status:
* 1- Check if an order is in a specific status:
when: VALIDATING BUSINESS RULES, DISPLAYING information for the CURRENT STATUS.
        fun Order.isOrderWaitingForPayment(): Boolean {
            return status == OrderStatus.WAITING_FOR_PAYMENT
        }

2- Obtain the next possible status:
when: implementing status progression logic in an organized manner.
     fun OrderStatus.nextStatus(): OrderStatus? {
         return when (this) {
            OrderStatus.WAITING_FOR_PAYMENT -> OrderStatus.PROCESSING_PAYMENT
            OrderStatus.PROCESSING_PAYMENT -> OrderStatus.BEING_PREPARED
            OrderStatus.BEING_PREPARED -> OrderStatus.SHIPPING
            OrderStatus.SHIPPING -> OrderStatus.RECEIVED
            else -> null
        }
     }

* 3- Convert a string to the corresponding status:
when: handling data from APIs, databases.
     fun stringToOrderStatus(statusStr: String): OrderStatus? {
         return OrderStatus.values().find { it.name == statusStr }
     }

* 4- Get all statuses as a list:
when: displaying filter options, menus, or other interfaces.
     val allOrderStatuses: List<OrderStatus> = OrderStatus.values().toList()

* 5- Get the index of a status:
when: specific comparisons or calculations.
     fun OrderStatus.ordinalIndex(): Int {
         return ordinal
     }

*** Modifying the 'status' attribute (immutable) of the Order class (modifying object status)
* 1- Using the 'setter' Method is DISCOURAGED, especially in production.
Violates encapsulation and makes the code less flexible, but directly alters
the status within the object.

* 2- Using a Method that returns a new Order object with the modified status.
Useful in some situations but generates MEMORY GARBAGE.
ADVANTAGE: maintains encapsulation and allows for creating a status history.
DISADVANTAGE: besides garbage, it may be more complex to implement.
RECOMMENDATION: track status history or need a new object for another purpose.

class Order {
    // ... (other attributes and methods)

    fun modifyStatusTo(newStatus: OrderStatus): Order {
      return Order(id, orderedItem, newStatus)
  }
  // ... (other methods)
}

* 3- Using Auxiliary Variables.
To store the 'current' status is SIMPLE, but does NOT modify the object's status itself.
ADVANTAGE: maintains encapsulation and is simple to implement.
DISADVANTAGE: does not modify the object's status itself, causes CONFUSION
              if not properly documented.
RECOMMENDATION: needs to display the 'current' status in different parts of
                the code, but does not need to modify the object's status itself.

class Order {
// ... (other attributes and methods)

    var currentStatus: OrderStatus = OrderStatus.WAITING_FOR_PAYMENT

   fun modifyStatusTo(newStatus: OrderStatus) {
       currentStatus = newStatus
   }

  // ... (other methods)
}
*/

/* Other examples that can be used in various parts of the code, such
*  as in comparisons, switch statements, or to generate lists of options for the user.*/
/*
* 1- Controlling states of graphical user interfaces (GUIs):
*    Imagine a button has 3 states: ACTIVE, INACTIVE, SELECTED.
*    The 'enum' will represent these states:
*
*       enum class ButtonState {
*           ACTIVE, INACTIVE, SELECTED }
*
*     A variable of the type ButtonState could be used and updated according to user actions:
*
*       var buttonState: ButtonState = ButtonState.INACTIVE
*       fun onClickBotao() {
*           when (buttonState) {
*               ButtonState.ACTIVE -> {
*                   // Deactivate THE BUTTON
*                   buttonState = ButtonState.INACTIVE
*               }
*               ButtonState.INACTIVE -> {
*                   // Activate the button
*                   buttonState = ButtonState.ACTIVE
*               }
*               ButtonState.SELECTED -> {
*               // Perform specific action for selected button
*               }
*           }
*       }
*
* */
/*
* 2- Storing Application Settings:
*    User-defined settings: language, theme, notifications
*    An 'enum' can store these settings:
*
*       enum class AppSetting {
*            LANGUAGE_PORTUGUESE, LANGUAGE_ENGLISH,
*            THEME_LIGHT, THEME_DARK,
*            NOTIFICATIONS_ENABLED, NOTIFICATIONS_DISABLED
*       }
*
*       To load and save app settings, you can use a configuration file or a database.
*/
/* 3- Validating Form Input Data:
*     An 'enum' represents the different types of data a user can enter:
*     name/email/date of birth/phone number
*
*       enum class DataType {
*           NAME, EMAIL, DATE_OF_BIRTH, PHONE_NUMBER
*       }
*
*      When validating input data, you can check if the entered data type is valid:
*
*       fun validateInput(value: String, dataType: DataType): Boolean {
*           when (dataType) {
*               DataType.NAME -> {
*                   // Validate if the value is a valid name
*               }
*               DataType.EMAIL -> {
*                   // Validate if the value is a valid email
*               }
*               // ... other data types
*          }
*       }
*/
/*
* 4- Representing Days of the Week, Months of the Year, and System Access Levels:
*    Enums can effectively represent sets of fixed and ordered values, such as:
*
*       enum class DayOfWeek {
*           SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY
*       }
*
*       enum class Month {
*           JANUARY, FEBRUARY, MARCH, APRIL, MAY, JUNE,
*           JULY, AUGUST, SEPTEMBER, OCTOBER, NOVEMBER, DECEMBER
*       }
*
*       enum class NAccessLevel {
*           USER, ADMINISTRATOR, SUPER_ADMINISTRATOR
*       }
*/

/*
*'Enums' vs. Boolean Variables for Managing Simple States:
*   Advantages of Enums:
*       Type Safety: Enums ensure that the value is one of the defined values, preventing invalid values.
*       Readability: Enums make the code more clear and self-explanatory.
*       Organization: Enums group related values together, improving code organization and maintainability.
*       Error Reduction: Enums help reduce errors by preventing typos and invalid values.
*   Disadvantages of Enums:
*       Verbosity: Enums can make the code more verbose compared to using boolean variables.
*       Memory Overhead: Enums may consume slightly more memory than boolean variables.
*
*   Advantages of Boolean Variables':
*       Conciseness: Boolean variables can make the code more concise and easier to read in some cases.
*       Memory Efficiency: Boolean variables occupy less memory space compared to enums.
*   Disadvantages of Boolean Variables:
*       Reduced Type Safety: Boolean variables are less type-safe, making the code more prone to errors.
*       Reduced Clarity: Boolean variables can make the code less clear, especially when the meaning of
*                        true and false values is not immediately apparent.
*/

/* Enums' vs. Bit Flags:
* Bit Flags: An efficient way to represent a set of related states
*            using a single integer value. Each bit in the value can
*            represent a different state.
* Example: A file permission system has read, write, and execute permissions.
*          Here's how to represent them using bit flags:
*
*       const val PERMISSION_READ = 1
*       const val PERMISSION_WRITE = 2
*       const val PERMISSION_EXECUTE = 4
*
*       fun hasReadPermission(permissions: Int): Boolean {
*           return permissions and PERMISSION_READ > 0
*       }
*
*       // Similar functions for write and execute permissions
*
* Advantages of Bit Flags:
*   Efficient memory usage: single integer represents multiple states.
*   Faster operations compared to enums
*
* Disadvantages of Bit Flags:
*   Less readable for those unfamiliar with them
*   Debugging errors related to bit flags can be harder
*
* When to Use:
* ENUMS-> code readability and organization IMPORTANT
* Bit Flags-> ememory efficiency and speed are crucial.
*/

/* 'Enums' vs. Constant Tables
* Enums: A structured way to define a set of named constants with predefined values.
*        They improve code readability and provide type safety.(days of the week)
*
* Constant Tables: A way to store fixed values using a map data structure.
*                  They offer flexibility in adding, removing, or modifying constants.
*                  A traditional way to store fixed values in an application.
*
*       val MARITAL_STATUSES = mapOf(
*           "SINGLE" to 1,
*           "MARRIED" to 2,
*           "WIDOWED" to 3,
*           "DIVORCED" to 4
*       )
*
* Advantages of Constant Tables:
*   Flexibility: It's easy to add, remove, or modify constants within the table.
*   Code Independence: Constants are stored in a central location and are not
*                      directly embedded in the code.
*
* Disadvantages of Constant Tables:
*   Less Readable: Code using constant tables can be less readable.
*   Verbosity: The constant table can occupy more space in the code.
*
* When to Use Constant Tables:
* ENUMS-> readability and organization an type safety are IMPORTANT.
* CONSTANT TABLES-> flexibility and code independence are IMPORTANT.
* */

