package marketplaceserverapi.controllers;

import marketplaceserverapi.services.MarketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/webhook")
public class WebhookController {

    @Autowired
    @Qualifier("marketService")
    private MarketService marketService;

    // will have to change (can't just buy using product title now) -> need to add search

//    @RequestMapping(method = RequestMethod.POST)
//    public String routeRequest(@RequestBody String requestBody) throws InvalidParameterException, InvalidKeyException {
//        JsonObject jsonObject = new JsonParser().parse(requestBody).getAsJsonObject();
//        String intentType = jsonObject.getAsJsonObject("queryResult").getAsJsonObject("intent").get("displayName").getAsString();
//        switch (intentType) {
//            case "PurchaseProduct": return purchaseProduct(jsonObject);
//        }
//        return "{'fulfillmentText': ' nothing happened '}";
//    }

//    public String purchaseProduct(JsonObject jsonObject) throws InvalidKeyException {
//        String productId = jsonObject.getAsJsonObject("queryResult").getAsJsonObject("parameters").get("product").getAsString();
//        Product product = marketService.purchaseProduct(Integer.parseInt(productId));
//        if (product == null) return "{'fulfillmentText': ' " + productId + " does not exist'}";
//        return "{'fulfillmentText': ' you bought one " + productId + " '}";
//    }
}
