package com.example.baubapchallenge.core.extensions

import androidx.core.text.isDigitsOnly

fun String.Companion.empty() = ""

fun String.isValidPhone() = this.length == 10 && this.isDigitsOnly()

fun String.isValidCurp(): Boolean {
    val regex = Regex("^[A-Z]{4}\\d{6}[HM][A-Z]{5}[A-Z0-9]{2}\$")
    return this.length == 18 && regex.matches(this)
}

fun String.isValidPin() =
    this.length == 4 && this.isDigitsOnly()
