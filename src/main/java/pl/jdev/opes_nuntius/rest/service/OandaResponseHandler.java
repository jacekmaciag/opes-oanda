package pl.jdev.opes_nuntius.rest.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import pl.jdev.opes_commons.domain.instrument.Candlestick;
import pl.jdev.opes_commons.domain.instrument.CandlestickGranularity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
class OandaResponseHandler {
    static <T> Object unpack(String responseBody, Class<T> componentType, String wrapper) {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode json;
        Object destObject = null;
        try {
            json = mapper.readTree(responseBody);
//      TODO: handle lasttransaciton      long lastTransactionId = Optional.ofNullable(json.get("lastTransactionID")).get().asLong();
            JsonNode wrappedNode = json.get(wrapper);
            if (wrappedNode.isArray()) {
                System.out.println("Array!");
                destObject = new ArrayList<T>();
                Iterator<JsonNode> iterator = wrappedNode.elements();
                while (iterator.hasNext()) {
                    Object el = mapper.treeToValue(iterator.next(), componentType);
                    ((ArrayList) destObject).add(el);
                }
            } else {
                destObject = mapper.treeToValue(wrappedNode, componentType);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(destObject);
        return destObject;
    }

    static List<Candlestick> unpackCandles(String responseBody) {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode json;
        List<Candlestick> candles = new ArrayList<>();
        try {
            json = mapper.readTree(responseBody);
            String instrument = json.get("instrument").asText();
            CandlestickGranularity granularity = CandlestickGranularity.valueOf(json.get("granularity").asText());
            Iterator<JsonNode> iterator = json.get("candles").elements();
            while (iterator.hasNext()) {
                Candlestick candle = mapper.treeToValue(iterator.next(), Candlestick.class);
                candle.setInstrument(instrument);
                candle.setGranularity(granularity);
                candles.add(candle);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return candles;
    }
}