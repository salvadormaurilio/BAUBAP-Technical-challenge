# 🏦 Baubap Technical Challenge

This project simulates **account registration** and **login** for **Baubap**, using either the **CURP** or the **user’s phone number**, and includes the following screens:

- **Welcome Screen**  
  Allows navigation to **Sign In** or **Sign Up**.  

- **Sign Up**  
  Requests the **CURP**, **Phone Number**, and **Access PIN** fields.  
  Field validations are performed, and registration is simulated with **Firestore**.  

- **Sign In**  
  Requests either the **CURP** or **Phone Number** along with the **Access PIN**.  
  Field validations are also performed, and login is simulated with **Firestore**.  

- **Home**  
  Displays the user’s information and the available deposit accounts.  
  
---

## 📌 Technologies & Concepts Used

### 🛠 Technologies

- **Kotlin**
- **Coroutines** (including Flow)
- **Hilt** for dependency injection
- **Jetpack Compose** for UI
- **Retrofit** for networking
- **Firestore** to simulate user authentication.

### 🧪 Testing

- **JUnit**
- **Mockito**
- **Hamcrest**
- **Coroutines Test**
- **Turbine**


### 💡 Architecture & Patterns

- **Clean Architecture**
- **Clean Code**
- **SOLID principles**
- **MVI**
- **Repository pattern**

---


## 🧪 UI Tests



---

## 🧪 How to Run & Test the Project

To run the project, make sure to add the following entry to your `local.properties` file:

```properties
MAPS_API_KEY=AIzaSyBpUVcthDLDdubUlpGQL54Xacn0CGINU-Y
```

Also You can build app or run the Unit Tests and Integration Tests with:

```
./gradlew testDebugUnitTest            # Run unit tests
./gradlew connectedDebugAndroidTest    # Run instrumentation tests
```




