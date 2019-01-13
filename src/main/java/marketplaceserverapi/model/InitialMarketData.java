package marketplaceserverapi.model;

import com.google.gson.Gson;

import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * InitialMarketData loads in default data from a JSON file on to the market.
 * This can be replaced in the future with a database.
 *
 * @author Jason Liu
 */
public class InitialMarketData {
    private static List<Product> products;

    public InitialMarketData() throws IOException {
        Gson gson = new Gson();
        products = Arrays.asList(gson.fromJson(new FileReader("marketData.json"), Product[].class));
    }

    public static List<Product> getProducts() {
        return products;
    }
}
