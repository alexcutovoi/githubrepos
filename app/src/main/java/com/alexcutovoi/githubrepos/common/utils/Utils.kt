package com.alexcutovoi.githubrepos.common.utils

import java.util.regex.Pattern

class Utils {
    companion object {
        fun validateEmail(email: String): Boolean {
            val emailRegexPattern = "[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\\\.[A-Za-z]{2,64}"
            val emailPattern = Pattern.compile(emailRegexPattern)
            return emailPattern.matcher(removeSpaces(email)).matches()
        }

        fun removeSpaces(target: String): String {
            return target.replace(Regex("\\s"), "")
        }
    }
}