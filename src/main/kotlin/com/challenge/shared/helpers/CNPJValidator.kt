package com.challenge.shared.helpers

object CNPJValidator {
    private val weights1 = intArrayOf(5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2)
    private val weights2 = intArrayOf(6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2)

    fun isValid(cnpj: String): Boolean {
        val cleanCnpj = cnpj.filter { it.isDigit() }
        if (cleanCnpj.length != 14) return false
        if (cleanCnpj.all { it == cleanCnpj[0] }) return false
        val digits = cleanCnpj.map { it.toString().toInt() }
        val sum1 = (0 until 12).sumOf { i -> digits[i] * weights1[i] }
        val firstCheck = (sum1 % 11).let { if (it < 2) 0 else 11 - it }
        if (firstCheck != digits[12]) return false
        val sum2 = (0 until 13).sumOf { i -> digits[i] * weights2[i] }
        val secondCheck = (sum2 % 11).let { if (it < 2) 0 else 11 - it }
        return secondCheck == digits[13]
    }
}