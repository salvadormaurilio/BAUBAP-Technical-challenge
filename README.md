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

## 💾 Storage[[[](url)](url)](url)

**Firestore** was used to simulate and manage user authentication.

<img width="2201" height="390" alt="image" src="https://github.com/user-attachments/assets/962565d4-aab2-4472-a137-e499865e07a9" />

## 🧪 UI Tests

### 👋 Welcome
<video src="https://github.com/user-attachments/assets/a5f252ae-30eb-42f6-8b36-e769ce381d01" width="360" controls></video> 

---

###  📝 Sign Up

| Success | Error Fields | Error |
|-|-|-|
| <video src="https://github.com/user-attachments/assets/6a17d6d8-de56-49bc-82a7-74dfe6c2d6ed" width="360" controls></video>  | <video src="https://github.com/user-attachments/assets/d6cdb8e2-8bfe-4bcc-b742-4b55e3e28d59" width="600"> width="360" controls></video> | <video src="https://github.com/user-attachments/assets/ae92045a-a5eb-4dec-8194-16739775396b" width="600"> width="360" controls></video> |

---






###  🔐 Sign In

| Success | Error Fields | Error |
|-|-|-|
| <video src="https://github.com/user-attachments/assets/6558a8e2-d1af-4890-8ee9-4d0a1f835e36" width="360" controls></video>  | <video src="https://github.com/user-attachments/assets/1735fb83-1ce5-48fa-8a88-eab65b79c976" width="600"> width="360" controls></video> | <video src="https://github.com/user-attachments/assets/33ee002c-3b94-40d3-bce1-4e926281c38a" width="600"> width="360" controls></video> |

---


###   User Data

<video src="https://github.com/user-attachments/assets/47c54679-b3ca-4ef6-8e74-4811a63696fe" width="360" controls></video>

---

## 🧪 How & Test the Project
You can download a version of the application from **Firebase App Distribution** at the following link:  

👉 [Download App](https://appdistribution.firebase.dev/i/76b3aa2f7b65f011)

---

## 🧪 Run Unit Tests

To run the unit tests, use the following command:

```bash
./gradlew testDebugUnitTes
```


