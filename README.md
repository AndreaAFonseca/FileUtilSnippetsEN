# Advanced Android Development: Enums, Sealed Classes, Covariant and Contravariant

This repository contains practical examples and detailed notes on advanced programming concepts in Android using **Kotlin**, 
focusing on Enums, Sealed Classes, Covariant, and Contravariant.

## Content
Examples in Java have been included to highlight the applicability of the concepts covered in Kotlin.

- **Enum.kt**: 
A comprehensive guide on enums, a powerful ally for organizing and protecting your software.
Demonstrates how to use them to ensure readability, reliability, and security.

`Kotlin:`
```kotlin
enum class Color { RED, BLUE, GREEN }

fun main() {
  val myColor: Color = RED
  println("My favorite color is $myColor")
}
```
`Java:`
```java
enum Color { RED, BLUE, GREEN }

public class EnumExample {
  public static void main(String[] args) {
    Color myColor = Color.RED;
    System.out.println("My favorite color is " + myColor);
  }
}
```
- **Sealed.kt**:
Concept and demonstration of the use of Sealed Classes in practical examples and comparison with classes and enums.
Some covered uses: Modeling UI states, State Machines, Visitor Pattern, DUs (equivalence).
  
`Kotlin:`
```kotlin
sealed class Shape {
  data class Circle(val radius: Double) : Shape()
  data class Rectangle(val width: Double, val height: Double) : Shape()
  object Square : Shape()
}
 ```  
`Java:`
The Sealed Classes feature was introduced in Java 17 (JEP 409) as a preview feature and as of the finalization of this study, it was still in development.

- **CovariantVsContravariant.kt**:
Exploration of the concepts of Covariant and Contravariant in **Kotlin**, with practical examples.
Discusses the advantages and disadvantages of each concept.

`Kotlin:` <br>
**_Covariant<out>:_** allows the return type to be more specific than the type declared in the interface.<br>
```kotlin
interface Covariant<out T> {
    fun get(): T
}

open class SuperType
class SubType : SuperType()

fun main() {
    val covariant: Covariant<SuperType> = object : Covariant<SubType> {
        override fun get(): SubType {
            // Returns a SubType object
            return SubType()
        }
    }

    val subType: SubType = covariant.get() // Compiles and works!
    val superType: SuperType = covariant.get() // Compiles and works!
}
```
**_Contravariant<in>:_** Allows the parameter type to be more generic than the type declared in the interface.<br>
```kotlin
interface Contravariant<in T> {
    fun receive(value: T)
}

open class SuperType
class SubType : SuperType()

fun main() {
    val contravariant: Contravariant<SuperType> = object : Contravariant<SubType> {
        override fun receive(value: SubType) {
            // Receives a SubType object
            println("Received a SubType: $value")
        }
    }

    contravariant.receive(SubType()) // Works!
    contravariant.receive(SuperType()) // Works!
}
```

`Java:`
Although it shares the concepts of **_covariant_** and **_contravariant_**, Java does not have native 
support for **_them_** in interfaces, but it is possible to simulate these behaviors using _generics_ and _wildcards_
with special attention to the involved types, their runtime restrictions.

**_Covariant<out>:_**
```java
interface Covariante<T> {
    T get();
}

class SuperType {
}

class SubType extends SuperType {
}

class Main {
    public static void main(String[] args) {
        Covariant<SuperType> covariant = new Covariant<SubType>() {
            @Override
            public SubType get() {
                return new SubType();
            }
        };

        SubType subType = covariant.get(); // Compiles and works!
        SuperType superType = covariant.get(); // Compiles and works!

        // Compilation error at runtime:
        // ((SubType) superType).especificSubTypeMethod();
    }
}
```

**_Contravariant<in>:_** allows the parameter type to be more generic than the type declared in the interface.<br>
`Java:`
 ```java
 interface Contravariant<in T> {
    void receive(T value);
}

class SuperType {
}

class SubType extends SuperType {
}

class Main {
    public static void main(String[] args) {
        Contravariant<SuperType> contravariant = new Contravariant<SubType>() {
            @Override
            public void receive(SubType value) {
                System.out.println("Received a SubType: " + value);
            }
        };

        contravariant.receive(new SubType()); // Works!
        contravariant.receive(new SuperType()); // Works!
    }
}
```

- **SealedLogicMVVM.kt**:
This file explores the use of Sealed Classes as a way to manage the return of requests in
different layers of an MVVM project. Through the Sealed Class, it is possible to control the
flow of data and error handling between layers, simplifying communication and avoiding coupling.

`Kotlin:`
```kotlin
sealed class RequestResult<T> {
    data class Success<T>(val data: T) : RequestResult<T>()
    data class Error(val message: String) : RequestResult<T>()
}

class MyViewModel {
    fun fetchData(callback: (RequestResult<List<String>>) -> Unit) {
        // Perform the request and set the result
        val result = if (success) {
            RequestResult.Success(dataFromRequest)
        } else {
            RequestResult.Error("Error fetching data")
        }

        // Call the callback with the result
        callback(result)
    }
}

fun main() {
    val viewModel = MyViewModel()

    viewModel.fetchData { result ->
        when (result) {
            is RequestResult.Success -> {
                // Process data successfully
                println(result.data)
            }
            is RequestResult.Error -> {
                // Handle the error
                println("Error: ${result.mesage}")
            }
        }
    }
}
```
`Java:` despite the absence of native support, it is possible to simulate the behavior of the **_Sealed Class_**
in Java, but it requires more code and boilerplate compared to Kotlin.
```java
interface RequestResult<T> {
    void handle(Callback<T> callback);
}

class Success<T> implements RequestResult<T> {
    private final T data;

    public Success(T data) {
        this.data = data;
    }

    @Override
    public void handle(Callback<T> callback) {
        callback.onSuccess(data);
    }
}

class Erro implements RequestResult<Object> {
    private final String message;

    public Error(String message) {
        this.message = message;
    }

    @Override
    public void handle(Callback<Object> callback) {
        callback.onError(message);
    }
}

class MyViewModel {
    public void fetchData(Callback<List<String>> callback) {
        // Perform the request and set the result
        RequestResult<List<String>> result;
        if (success) {
            result = new Success<>(dataFromRequest);
        } else {
            result = new Error("Error fetching data");
        }

        // Call the callback with the result
        result.handle(callback);
    }
}

public class Main {
    public static void main(String[] args) {
        MyViewModel viewModel = new MyViewModel();

        viewModel.fetchData(new Callback<List<String>>() {
            @Override
            public void onSuccess(List<String> data) {
                System.out.println("Received data: " + data);
            }

            @Override
            public void onError(String message) {
                System.out.println("Error: " + message);
            }
        });
    }
}
```
- **SealedWithCovariantLogicMVVM(StatusReturn).kt**:
Approach to manage different types of responses in asynchronous operations using MVVM architecture,
sealed class, and covariant generic types, ensuring type safety, code reuse, generic error handling,
improving code organization, and readability. 

Scenario: An application that queries data from different APIs and needs to manage the responses in a generic and reusable way. 
`Kotlin:`
```kotlin
// Define the sealed class with a generic types (out T)
sealed class RequestStatus<out T> {
    // Classes representing different types of responses
    class Success<T>(var data: T) : RequestStatusT>()
    class Error(var errorMessage: String) : RequestStatus<Nothing>()
}

// Defines the interface for the repository
interface Repository<T> {
    fun fetchData(callback: (RequestStatus<T>) -> Unit)
}

// Concrete implementation of the repository to fetch data from API
class ApiRepository<T> : Repository<T> {
    override fun fetchData(callback: (RequestStatus<T>) -> Unit) {
        // Simulate API call
        val data = generateRandomData<T>() // Function to generaterandom data of type T

        //  Send the response to the callback
        if (data != null) {
            callback(RequestStatus.Success(data))
        } else {
            callback(RequestStatus.Error("Error fetching data from API"))
        }
    }
}

// Function to generate random data of type T
fun <T> generateRandomData(): T? {
    // Implementation logic for generating random data
    // Example: generate a User object, Product, etc.
    return null // Returns null if data cannot be generated
}

// Define the ViewModel
class MainViewModel {
    private val repository: ApiRepository<String> = ApiRepository()

    fun fetchData(callback: (RequestStatus<String>) -> Unit) {
        repository.fetchData(callback)
    }
}

// Example of using the ViewModel
fun main() {
    val viewModel = MainViewModel()

    viewModel.fetchData { status ->
        when (status) {
            is RequestStatus.Success -> {
                val data = status.data
                // Process data successfully
                println("Received data: $data")
            }
            is RequestStatus.Error -> {
                val errorMensage = status.errorMessage
                // Display error message
                println("Error: $errorMessage")
            }
        }
    }
}
```
`Java:`
```java
// Define the sealed class with a generic type (out T)
sealed class RequestStatus<T> {
    // Classes representing differents types of responses
    static class Success<T>(T data) extends RequestStatus<T> {
        // ...
    }

    static class Error(String errorMessage) extends RequestStatus<Nothing> {
        // ...
    }
}

// Define the interface for the repository
interface Repository<T> {
    void fetchData(Consumer<RequestStatus<T>> callback);
}

// Concrete implementation of the repository to fetch data from API
class ApiRepository<T> implements Repository<T> {
    @Override
    public void fetchData(Consumer<RequestStatus<T>> callback) {
        // Simulate API call
        T data = genereRandomData(); // Function to generate random data of type T

        // ESend the response to the callback
        if (data != null) {
            callback.accept(new RequestStatus.Success<>(data));
        } else {
            callback.accept(new RequestStatus.Errro("Error fetching data from API"));
        }
    }
}

// Function to generate random data type of type T
public <T> T generateRandomData() {
    // Implementation logic for generating random data
    // Example: generate a User object, Product, etc.
    return null; // Returns null if daa cannot be generated
}

// Define the ViewModel
class MainViewModel {
    private final ApiRepository<String> repository = new ApiRepository<>();

    public void fetchData(Consumer<RequestStatus<String>> callback) {
        repository.fetchData(callback);
    }
}

// Example of using the ViewModel
public static void main(String[] args) {
    MainViewModel viewModel = new MainViewModel();

    viewModel.fetchData(status -> {
        if (status instanceof RequestStatus.Success) {
            String data = ((RequestStatus.Sucsess<String>) status).getData();
            // Process the data sucessfully
            System.out.println("Received data: " + data);
        } else if (status instanceof RequestStatus.Error) {
            String errorMessage = ((RequestStatus.Error) status).getErrorMessage();
            // Display error message
            System.out.println("Erroe: " + errorMessage);
        }
    });
}
```
## Usage
This repository can be used as a starting point to implement Enums, Sealed Classes, Covariant and Contravariant in your Android projects.

## Contribution
Feel free to contribute with improvements, bug fixes, or new features. Open an issue or submit a pull request!

## License
This project is licensed under the MIT License. Read the [LICENSE](https://github.com/AndreaAFonseca/FileUtilSnippetsEN/blob/master/LICENSE)
file for details on rights and limitations.

## Note on Project Origin
This project was originally created in Portuguese and later translated into English for wider accessibility and understanding.

## Author
Andréa Alessandra Fonsêca
