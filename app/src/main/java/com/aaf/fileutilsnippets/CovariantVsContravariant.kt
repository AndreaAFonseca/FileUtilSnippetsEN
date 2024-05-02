package com.aaf.fileutilsnippets


/*
 * File Name: CovariantVsContravariant
 * Author: Andréa A. Fonsêca
 * Creation Date: 2024-04-26
 * Description: This file is part of an Android project: FileUtilSnippets.
 * Contains implementations, classes and/or functionality for the illustration
 of the concepts of covariance and contravariance.
 *
 * Copyright (c) 2024 Andréa A. Fonsêca
 * Licensed under the MIT License.
 * See the LICENSE file for details.
**/

/*Concepts
*   Covariant = <out> -> This refers to programming with 'out', where it moves from the
* innermost type (SubType) outward to the SuperType. SubType can be assigned to a SuperType,
* following the inheritance pattern. It emphasizes that the super type is not equivalent to
* the sub type. More specific types can be used in contexts where more general types are expected.
*
*   Contravariant = <in> -> This refers to programming with 'in', which is the inverse of
* covariant. It moves from outside to inside (like a circle). Here, a SuperType can be
* assigned to a SubType. It's an exception to assign a SuperType to a SubType due to potential
* method discrepancies. It's crucial to ensure that the super type and sub type are the same in
* this context, contrary to the usual pattern. This is commonly used in libraries for flexibility,
* where more general types can be used in contexts requiring more specific types. This flexibility
* is essential for handling different data types and allows the supertype to take on specific
* responsibilities of the subtype, ensuring compatibility and avoiding information loss.
*
*   Choosing Between Them -> The choice between covariant and contravariant depends on
* specific needs and the application context.
*
*   DIFFERENCES:
*   covariant<out>(default): This accepts a parameter T and can return a value of
* type T or a type derived from T.
*
*   contravariant<in>: This accepts a parameter T and can only return values of
* type T or ancestral types of T.
*/

/*
//INHERITEANCE PATTERN - convariant<out> in action
open class SuperType //Vehicle
class SubType : SuperType() //Car, Motorcycle


open class Vehicle //SuperType
class Car : Vehicle() //SubType


fun main() {
    val vehicle: Vehicle = Car()
    //doesn't go from inside to outside
    //val car: Car = Vehicle()
}
*/

/*// HOW THEY WORK:
interface Covariant<out T> //By default, allows a subtype to be assigned to a supertype, but inverse doesn't work
interface Contravariant<in T> //Ensures they have the same methods (equivalence)


open class SuperType //Represents a Vehicle
class SubType : SuperType() //Represents a Car or Motorcycle


open class Vehicle //Represents a SuperType
class Car : Vehicle() //Represents a SubType


fun main() {
// COVARIANT (out) - DEFAULT
    //val covariant: Covariant<SuperType> = object : Covariant<SuperType>{}
//Allows assigning any type and subtype:
// - If the Covariant<out T> interface has 'T' as 'Vehicle'. DEFAULT
   //val covariant: Covariant<Vehicle> = object : Covariant<Car>{}
   //val covariant: Covariant<Vehicle> = object : Covariant<Vehicle>{}
//Cannot assign supertype to subtype:
// - If the Covariant<out T> interface has 'T' as 'Car'.
  //val covariant: Covariant<Car> = object : Covariant<Car>{}
  //val covariant: Covariant<Car> = object : Covariant<Vehicle>{}//CANNOT DO


// CONTRAVARIANT( in ) - EXCEPTION
    //val contravariant: Contravariant<Car> = object : Contravariant<Vehicle>{}
//Allows assigning supertype to subtype and vice versa. MUST BE EQUAL
   //val contravariant: Contravariant<Car> = object : Contravariant<Car>{}
   //val contravariant: Contravariant<Car> = object : Contravariant<Vehicle>{}
}*/

/* PROBLEM WITH COVARIANT<out> - default
interface Covariant<out T> //By default, allows a subtype to be assigned to a supertype, but inverse doesn't work
interface Contravariant<in T> //Ensures they have the same methods (equivalence)


open class Vehicle{
   fun brakeVehicle(){}
} //SuperType = Vehicle
class Car : Vehicle(){
   fun brakeCar(){}
} //SubType = Car, Motorcycle


fun main() {
   //PROBLEM WITH covariant<out> - It is NOT expected for the subtype to have methods
   val covariant: Covariant<Car> = object : Covariant<Vehicle>{}
   //When you are SURE that superType and subType both have the same things (are equal)
   val contravariant: Contravariant<Car> = object : Contravariant<Vehicle>{}
}*/

/*//CONTRAVARIANT<in> - EXCEPTION - goes the opposite way
//Can associate the supertype with the subtype - EXCEPTION
interface Contravariant<in T> //Ensures they have the same methods (equivalence)


open class Vehicle{ //SuperType = Vehicle
   open fun brake(){}
}
class Car : Vehicle(){ //SubType = Car, Motorcycle
   override fun brake(){}
}


fun main() {
// MAKE SURE that superType and subType both have the same things (are equal)
   val contravariant: Contravariant<Car> = object : Contravariant<Vehicle>{}
}*/

/*//EXAMPLES OF CONTRAVARIANT<in> - when you want something to receive one thing or another.
 //It's fundamental that the involved types are compatible to avoid losing information
 //during data manipulation; this can be achieved with static or dynamic checks.


interface Covariant<out T> //This is what the standard does, subtype assigned to a supertype, INVERSE DOESN'T WORK
interface Contravariant<in T> //Ensure they have the same methods (are equivalent)


open class SuperType //Vehicle
class SubType : SuperType() //Car, Motorcycle


open class Vehicle{ //SuperType = Vehicle
   open fun brake(){}
}
class Car : Vehicle(){ //SubType = Car, Motorcycle
   override fun brake(){}
}


fun main(){
   //val list: List<String> = listOf("andréa", "marco", 1)
   // Cannot add the number because it expects a String, and 'List' uses 'out', i.e., covariant
   //val list: List<Car> = listOf(Car(), Vehicle()) // ERROR
   // Similarly, List remains covariant (supertype cannot be assigned to subtype)
   //val list: List<Vehicle> = listOf(Car(), Vehicle()) //OK


   //Most lists work with COVARIANT(generic lists usually use convariant<out>)
   //val listInvariant: MutableList<Vehicle> = mutableListOf(Car(), Vehicle())//OK


   //CREATING CONTRAVARIANT LIST <in>
   val contravariant: Contravariant<Car> = object : Contravariant<Vehicle>{}
   val listContravariant: Contravariant<Car> = object : Contravariant<Vehicle>{}
   val listContravariant1: Contravariant<Car> = object : Contravariant<Car>{}
}*/

/*
* Use Cases:
* - Any class can use the concepts covariant<out> and contravariant<in>
* - Generic lists typically use covariant<out>(default): a list of a specific
* type (subtype) can be assigned to a variable expecting a list of a more
* general type (supertype).
* However, they can also be contravariant<in> as in some libraries that use
* them for specific cases.
* - Generic functions can use the concepts covariant<out> and contravariant<in>,
* allowing them to accept or return different types of data depending on the context.
*/

/*
// You need to specify INHERITANCE for 'T'
class Draw<T>(private var data: T){
   fun execute(){
      data.execute()
   }
}*/
/*// Specifying Inheritance
class Draw<T: Square>(private var data: T){// 'T' type is so generic that it has NO METHODS
   fun execute(){
       data.execute()//needs to specify inheritance for 'T'
   }
}
//When executed, it DOES NOT KNOW what's inside - needs the SUPER CLASS
class Square(){
   fun execute(){
       println("Drawing Square")
   }
}
class Circle(){
   fun execute(){
       println("Drawing Circle")
   }
}*/


//Creating the SUPER CLASS
//open class Shape(){} //to understand what's inside, but this Super Class needs a
//method, so it transforms into an INTERFACE
/*interface Shape{
   fun execute(){


   }
}
//ACCEPTS ANY TYPE AS LONG AS IT INHERITS FROM 'Shape'
class Draw<T: Shape>(private var data: T){//'T' now inherits from 'Shape'
   fun execute(){
   //data// defined inheritance for 'T' using the super class
   //using INTERFACE now can access the method of the super class
   data.execute()
   }
}*/
/* These two classes have been updated to use the interface - overriding the methods.
class Square() : Shape(){// inherit from
   fun execute(){
       println("Drawing Square")
   }
}
class Circle() : Shape(){//inheritance
   fun execute(){
       println("Drawing Circle")
   }
}*/
/*
class Square() : Shape{//INTERFACE
   override fun execute(){
   println("Drawing Square")
   }
}
class Circle() : Shape{//INTERFACE
override fun execute(){
   println("Drawing Circle")
   }
}


fun main(){
   - DOES NOT DISPLAYS ANYTHING
   val shape: Shape> = Shape(Square())
   shape.execute() // DOES NOT DISPLAY ANYTHING
}*/

//EXPLAINING '<in T>' (contravariant) and '<out T>' (covariant)
// defines the type that will be received, indicating what association will be made
interface Shape{
    fun execute(){
    }
}

//Default:
// a) covariant `<out T>` - does not draw as a supertype
// b) contravariant `<in T>` - draws like a supertype
//class Draw<in T: Shape>(private var data: T){
// with <out> covariant, does NOT draw like as supertype (DEFAULT),
// with <in> contravariant, DRAWS like a supertype
class Draw<out T: Shape>(private var data: T){//DEFAULT, draws as supertype
    fun execute(){
        data.execute()
    }
}


class Square() : Shape{// INTERFACE IMPLEMENTED
    override fun execute(){
        println("Drawing Square")
    }
}

class Circle() : Shape{//INTERFACE IMPLEMENTED
    override fun execute(){
        println("Drawing Circle")
    }
}

fun main(){
    // Default with (covariant)'out' <Square> ONLY GETS 'Square'
    val shape: Draw<Square> = Draw(Square())
    shape.execute()
    //DOESN'T HAPPEN BECAUSE IT'S USING THE INTERFACE, THE CASE OF THE SUB REBIRTH SUPER
    //ROUTINE TASKS - PREFER TO USE THE INTERFACE - instead of inheritance, like this:
    // val form: Draw<Square> = Draw(Square())
    // val shape: Draw<Circle> = Draw(Circle())
    // val shape: Draw<Shape> = Draw(Square())
    // val shape: Draw<Shape> = Draw(Circle())

}

