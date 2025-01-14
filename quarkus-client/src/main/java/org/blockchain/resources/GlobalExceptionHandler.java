package org.blockchain.resources;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.core.Response;
import org.blockchain.utils.Constants;
import org.jboss.resteasy.reactive.server.ServerExceptionMapper;

import io.quarkus.logging.Log;
import org.web3j.protocol.exceptions.TransactionException;

/**
 * @author Alexandru Dinis
 *
 * Global exception handler class. Uses @{ObjectMapper} to convert the response to json
 */
public class GlobalExceptionHandler {
    private final ObjectMapper objectMapper;

    public GlobalExceptionHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @ServerExceptionMapper(TransactionException.class)
    public Response handleTransactionException(TransactionException ex) {
        String errorMessage = ex.getMessage();

        try {
            if (errorMessage.contains(Constants.TOPIC_ALREADY_EXISTING_EXCEPTION_FROM_BLOCKCHAIN)) {
                errorMessage = objectMapper.writeValueAsString(Constants.TOPIC_ALREADY_EXISTING_EXCEPTION_FROM_BLOCKCHAIN);
            } else {
                errorMessage = objectMapper.writeValueAsString(ex.getMessage());
            }
        } catch (JsonProcessingException jsonException) {
            Log.error("Error message could not be processed as JSON because of {}", jsonException.getMessage(),
                    jsonException);
        }

        return Response.status(Response.Status.BAD_REQUEST)
                .entity(errorMessage).build();
    }
}

