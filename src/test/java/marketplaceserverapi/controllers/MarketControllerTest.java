package marketplaceserverapi.controllers;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class MarketControllerTest {

    @Test
    public void givenProductToFind_whenProductTitleIsRetrieved_then500IsReceived() throws IOException {
        // Given
        String title = "hello";
        HttpUriRequest request = new HttpGet( "http://localhost:8080/market/" + title );

        // when
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);

        // then
        assertEquals(500, httpResponse.getStatusLine().getStatusCode());
    }
}
