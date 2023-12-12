package dev.rodrigovasconcelos.financasapp.exceptionhandler;

import dev.rodrigovasconcelos.financasapp.exception.BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<Problem> handle(Exception e) {
        Problem problem = Problem.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .title("Ocorreu um erro")
                .detail(e.getMessage())
                .userMessage("Ocorreu um erro ao processar a sua requisição")
                .timestamp(OffsetDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(problem);
    }

    @ExceptionHandler(value = BusinessException.class)
    public ResponseEntity<Object> businessExceptionHandle(BusinessException e) {
        Problem problem = Problem.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .title("Ocorreu uma falha")
                .detail(e.getMessage())
                .userMessage("Ocorreu um erro ao processar a sua requisição")
                .timestamp(OffsetDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problem);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        StringBuilder message = new StringBuilder();
        for (Map.Entry<String, String> erro : errors.entrySet()) {
            message.append("O campo ").append(erro.getKey()).append(" é inválido: ")
                    .append(erro.getValue()).append("\n");
        }

        Problem problem = Problem.builder()
                .title("Campos inválidos")
                .detail(message.toString())
                .status(HttpStatus.BAD_REQUEST.value())
                .userMessage("Campos inválidos: " + errors)
                .timestamp(OffsetDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problem);
    }
}
