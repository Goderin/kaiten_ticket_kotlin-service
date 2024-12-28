package ru.kaiten.exception

import org.springframework.http.HttpStatus

class KaitenException(message: String?, val statusCode: HttpStatus?) : BaseException(message)