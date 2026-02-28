package com.challenge.shared.helpers

object CPFValidator {
    fun isValid(cpf: String): Boolean {
        val cleanCpf = cpf.filter { it.isDigit() }
        if (cleanCpf.length != 11) return false
        if (cleanCpf.all { it == cleanCpf[0] }) return false
        val digits = cleanCpf.map { it.toString().toInt() }
        val firstSum = (0 until 9).sumOf { i -> digits[i] * (10 - i) }
        val firstCheck = (firstSum * 10 % 11).let { if (it == 10) 0 else it }
        if (firstCheck != digits[9]) return false
        val secondSum = (0 until 10).sumOf { i -> digits[i] * (11 - i) }
        val secondCheck = (secondSum * 10 % 11).let { if (it == 10) 0 else it }
        return secondCheck == digits[10]
    }
}