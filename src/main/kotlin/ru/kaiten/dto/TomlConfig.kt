package ru.kaiten.dto

data class TomlConfig(
   val defaultUrl: String,
   val fernetKey: String,
   val clientId: String,
   val clientSecret: String,
   val authUrl: String,
   val profileUrl: String
)
