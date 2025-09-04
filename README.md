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

## 🧪 How & Test the Project
You can download a version of the application from **Firebase App Distribution** at the following link:  

👉 [Download App](https://appdistribution.firebase.dev/i/76b3aa2f7b65f011)

---

## 🧪 Run Unit Tests

To run the unit tests, use the following command:

```bash
./gradlew testDebugUnitTes



