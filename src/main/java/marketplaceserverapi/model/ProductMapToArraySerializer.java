package marketplaceserverapi.model;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.util.Map;

/**
 * ProductMapToArraySerializer is used to serialize the items (HashMap) in Cart.
 *
 * @author Jason Liu
 */
public class ProductMapToArraySerializer extends JsonSerializer<Map<?, ?>> {

    @Override
    public void serialize(Map<?, ?> value, JsonGenerator generator,
                          SerializerProvider serializers) throws IOException,
            JsonProcessingException {
        generator.writeStartArray();
        for (Map.Entry<?, ?> entry : value.entrySet()){
            generator.writeStartObject();
            generator.writeObjectField("product", entry.getKey());
            generator.writeObjectField("numberToPurchase", entry.getValue());
            generator.writeEndObject();
        }
        generator.writeEndArray();
    }
}
