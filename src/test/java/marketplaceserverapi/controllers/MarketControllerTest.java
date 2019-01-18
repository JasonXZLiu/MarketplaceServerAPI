package marketplaceserverapi.controllers;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class MarketControllerTest {

    @Test
    public void givenProductToFind_whenProductTitleIsRetrieved_thenBlankJSONIsReceived() throws IOException {
        // Given
        String title = "Pants";
        HttpUriRequest request = new HttpGet( "http://localhost:8080/market/" + title );

        // when
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);

        // then
        HttpEntity entity = httpResponse.getEntity();
        String content = EntityUtils.toString(entity);
        assertEquals(content, "");
    }
}
