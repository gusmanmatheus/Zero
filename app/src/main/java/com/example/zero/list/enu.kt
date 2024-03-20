package com.example.zero.list

enum class Gender(val gender: String) {
    MALE("M"),
    FEMALE("F"),
    INDEFINED("I")
}

fun String.toGender(): Gender {
    return when {
        this.uppercase() == "M" -> Gender.MALE
        this.uppercase() == "F" -> Gender.FEMALE
        else -> Gender.INDEFINED

    }
}