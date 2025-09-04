package com.example.baubapchallenge.fakedata

import com.example.baubapchallenge.domain.models.DepositAccount
import com.example.baubapchallenge.domain.models.UserData

const val ANY_USER_ID = "MaKNkzR0ySOFAd9PS4IFxnW5rpX2"
const val ANY_USER_PHONE = "5502098476"
const val ANY_USER_CURP = "MADX910209HDFRLL09"
const val ANY_PASSWORD = "Admin1234_1"
const val ANY_PASSWORD_HASHED = "aa0a7b7bafab98dd708804630febd4eeb79cdd3b8152a6ac856620eca9525050"

const val ANY_PHONE = "5512345678"
const val ANY_CURP = "AELM930630HDFLLG01"
const val ANY_PIN = "1234"

const val ANY_INVALID_PHONE = "550209"
const val ANY_INVALID_CURP = "MADX91"
const val ANY_INVALID_PIN = "12"

fun givenUserData() = UserData(
    phone = "+5212345678",
    email = "correo@baubap.com",
    name = "Juan Perez",
    curp = "AELM930630HDFLLG01",
    address = "Popayan Caunca",
    depositAccounts = listOf(
        DepositAccount("Afirme", "Tarjeta débito", "1327639849280480"),
        DepositAccount("BBVA BANCOMER", "Tarjeta débito", "1327639849280480"),
    )
)