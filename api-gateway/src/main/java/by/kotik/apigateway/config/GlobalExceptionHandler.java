package by.kotik.apigateway.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import dto.ErrorResponse;
import exception.AppException;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Configuration
public class GlobalExceptionHandler implements ErrorWebExceptionHandler {

    private final ObjectMapper objectMapper;

    public GlobalExceptionHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable e) {
        ServerHttpResponse response = exchange.getResponse();
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        ErrorResponse errorResponse = getErrorResponse(e);
        return getResponse(response, errorResponse);
    }

    private ErrorResponse getErrorResponse(Throwable e) {
        int status;
        String error;
        String message;

        if (e instanceof AppException) {
            AppException appException = (AppException) e;
            status = appException.getCode();
            error = appException.getError();
            message = appException.getMessage();
        } else {
            status = 500;
            error = "Api Gateway Error";
            message = "Something went wrong";
        }
        return new ErrorResponse(status, error, message);
    }

    private Mono<Void> getResponse(ServerHttpResponse response, ErrorResponse errorResponse) {
        try {
            byte[] bytes = objectMapper.writeValueAsBytes(errorResponse);
            DataBuffer buffer = response.bufferFactory().wrap(bytes);
            response.setStatusCode(HttpStatusCode.valueOf(errorResponse.getCode()));
            return response.writeWith(Mono.just(buffer));
        } catch (Exception e) {
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
            return response.setComplete();
        }
    }
}
