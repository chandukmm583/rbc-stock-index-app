package com.rbc.digital;
import java.io.*;
import java.util.stream.Collectors;

import org.springframework.http.client.ClientHttpResponse;

public class ResponseResults {
    private final ClientHttpResponse theResponse;
    private final String body;

    ResponseResults(final ClientHttpResponse response) throws IOException {
        this.theResponse = response;
        this.body =  new BufferedReader(new InputStreamReader(response.getBody())).lines().collect(Collectors.joining("\n"));;
    }

    ClientHttpResponse getTheResponse() {
        return theResponse;
    }

    String getBody() {
        return body;
    }
}
