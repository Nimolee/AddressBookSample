package com.nimolee.addressbooksample.tools

fun String.toCapitalize(): String {
    return replaceFirst(first(), first().toUpperCase())
}