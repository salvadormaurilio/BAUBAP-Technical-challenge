package com.example.baubapchallenge.domain.models

data class UserData(
    val phone: String,
    val email: String,
    val name: String,
    val curp: String,
    val address: String,
    val depositAccounts: List<DepositAccount>
)

data class DepositAccount(
    val bankName: String,
    val type: String,
    val clabe: String
)