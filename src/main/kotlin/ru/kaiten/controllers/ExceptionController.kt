package ru.kaiten.controllers

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus
import ru.kaiten.dto.ErrorResponse
import ru.kaiten.exception.*

@ControllerAdvice
class ExceptionController {

    @ExceptionHandler(AuthException::class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    fun handleAuthException(ex: AuthException): ErrorResponse {
        return createErrorResponseEntity(ex, HttpStatus.UNAUTHORIZED)
    }

    @ExceptionHandler(ClientNotFoundException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    fun handleEntityIllegalArgumentException(ex: ClientNotFoundException): ErrorResponse {
        return createErrorResponseEntity(ex, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(CreateEntryException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    fun handleCreateTicketException(ex: CreateEntryException): ErrorResponse {
        return createErrorResponseEntity(ex, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(InvalidArgumentException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    fun handleEntityIllegalArgumentException(ex: InvalidArgumentException): ErrorResponse {
        return createErrorResponseEntity(ex, HttpStatus.BAD_REQUEST)
    }

    private fun createErrorResponseEntity(ex: BaseException, httpStatus: HttpStatus): ErrorResponse {
        return ErrorResponse(
            message = ex.message.toString(),
            error = httpStatus.reasonPhrase,
            status = httpStatus.value()
        )
    }
}