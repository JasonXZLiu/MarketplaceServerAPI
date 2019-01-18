package marketplaceserverapi.controllers;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.security.InvalidKeyException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MarketAdminControllerTest {

    @Test
    public void givenProductToAddToMarket_whenProductInfoIsRetrieved_thenNewMarketIsReceived() throws IOException, InvalidKeyException {

        // given
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost("http://localhost:8080/admin/market/add");

        // when
        String json = "[ { \"title\": \"Table\", \"price\": 3000, \"inventoryCount\": 20 } ]";
        StringEntity entity = new StringEntity(json);
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-type", "application/json");
        httpPost.setEntity(entity);

        // test
        CloseableHttpResponse response = client.execute(httpPost);
        assertEquals(200, response.getStatusLine().getStatusCode());
        client.close();
    }

    @Test
    public void givenProductToDeleteFromMarket_whenProductTitleIsRetrieved_thenNewMarketIsReceived() throws IOException {

        // given
        HttpUriRequest request = new HttpDelete("http://localhost:8080/admin/market/delete/" + "Table");

        // when
        HttpResponse response = HttpClientBuilder.create().build().execute(request);

        // test
        assertEquals(200, response.getStatusLine().getStatusCode());
    }

}
