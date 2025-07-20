package net.example.demo.rest.util;

import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;
import java.net.URI;

@Component
public class RestErrorHandler implements ResponseErrorHandler {

    @Override
    public boolean hasError(ClientHttpResponse httpResponse) throws IOException {
        return httpResponse.getStatusCode().value() == 404;
    }

    @Override
    public void handleError(URI url, HttpMethod method, ClientHttpResponse response) {
        // suppress error handling for 404 - not found
    }

}
