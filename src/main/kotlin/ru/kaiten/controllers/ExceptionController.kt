package ru.kaiten.controllers

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus
import ru.kaiten.entity.ErrorResponseEntity
import ru.kaiten.exception.*

@ControllerAdvice
class ExceptionController {

    @ExceptionHandler(BaseException::class)
    @ResponseBody
    fun handleKaitenExceptions(ex: KaitenException): ErrorResponseEntity {
        return createErrorResponseEntity(ex, ex.statusCode?: HttpStatus.INTERNAL_SERVER_ERROR)
    }

    @ExceptionHandler(EntityIllegalArgumentException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    fun handleEntityIllegalArgumentException(ex: EntityIllegalArgumentException): ErrorResponseEntity {
        return createErrorResponseEntity(ex, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(CreateTicketException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    fun handleCreateTicketException(ex: CreateTicketException): ErrorResponseEntity {
        return createErrorResponseEntity(ex, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(GetConfigException::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    fun handleGetConfigException(ex: GetConfigException): ErrorResponseEntity {
        return createErrorResponseEntity(ex, HttpStatus.INTERNAL_SERVER_ERROR)
    }

    private fun createErrorResponseEntity(ex: BaseException, httpStatus: HttpStatus): ErrorResponseEntity {
        return ErrorResponseEntity(
            message = ex.message.toString(),
            error = httpStatus.reasonPhrase,
            status = httpStatus.value()
        )
    }
}