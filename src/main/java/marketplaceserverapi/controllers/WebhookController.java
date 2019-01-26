package marketplaceserverapi.controllers;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import marketplaceserverapi.models.Product;
import marketplaceserverapi.services.MarketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.InvalidKeyException;
import java.security.InvalidParameterException;

@RestController
@RequestMapping("/webhook")
public class WebhookController {

    @Autowired
    @Qualifier("marketService")
    private MarketService marketService;

    @RequestMapping(method = RequestMethod.POST)
    public String routeRequest(@RequestBody String requestBody) throws InvalidParameterException, InvalidKeyException {
        JsonObject jsonObject = new JsonParser().parse(requestBody).getAsJsonObject();
        String intentType = jsonObject.getAsJsonObject("queryResult").getAsJsonObject("intent").get("displayName").getAsString();
        switch (intentType) {
            case "PurchaseProduct": return purchaseProduct(jsonObject);
        }
        return "{'fulfillmentText': ' nothing happened '}";
    }

    public String purchaseProduct(JsonObject jsonObject) throws InvalidKeyException {
        String productTitle = jsonObject.getAsJsonObject("queryResult").getAsJsonObject("parameters").get("product").getAsString();
        Product product = marketService.purchaseProduct(productTitle);
        if (product == null) return "{'fulfillmentText': ' " + productTitle + " does not exist'}";
        return "{'fulfillmentText': ' you bought one " + productTitle + " '}";
    }
}
