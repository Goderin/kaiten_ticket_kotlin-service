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

    @ExceptionHandler(KaitenException::class)
    @ResponseBody
    fun handleKaitenExceptions(ex: KaitenException): ErrorResponse {
        return createErrorResponseEntity(ex, ex.statusCode?: HttpStatus.INTERNAL_SERVER_ERROR)
    }

    @ExceptionHandler(EntityIllegalArgumentException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    fun handleEntityIllegalArgumentException(ex: EntityIllegalArgumentException): ErrorResponse {
        return createErrorResponseEntity(ex, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(CreateTicketException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    fun handleCreateTicketException(ex: CreateTicketException): ErrorResponse {
        return createErrorResponseEntity(ex, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(GetConfigException::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    fun handleGetConfigException(ex: GetConfigException): ErrorResponse {
        return createErrorResponseEntity(ex, HttpStatus.INTERNAL_SERVER_ERROR)
    }

    private fun createErrorResponseEntity(ex: BaseException, httpStatus: HttpStatus): ErrorResponse {
        return ErrorResponse(
            message = ex.message.toString(),
            error = httpStatus.reasonPhrase,
            status = httpStatus.value()
        )
    }
}