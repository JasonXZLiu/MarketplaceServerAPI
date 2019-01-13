package marketplaceserverapi.controllers;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class MarketAdminControllerTest {

    @Test
    public void givenProductToAddToMarket_whenProductInfoIsRetrieved_thenNewMarketIsReceived() throws IOException {

        // given
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost("http://localhost:8080/admin/market/add");

        // when
        String json = "[ { \"id\": 4, \"title\": \"Perfect_Shopify\", \"price\": 100, \"inventoryCount\": 100 } ]";
        StringEntity entity = new StringEntity(json);
        httpPost.setEntity(entity);
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-type", "application/json");

        // test
        CloseableHttpResponse response = client.execute(httpPost);
        assertEquals(200, response.getStatusLine().getStatusCode());
        client.close();
    }
}
