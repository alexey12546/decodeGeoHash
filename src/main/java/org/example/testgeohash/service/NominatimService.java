package org.example.testgeohash.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Locale;

@Service
public class NominatimService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public String getCityByCoordinates(double latitude, double longitude) {
        String url = String.format(
                Locale.US,
                "https://nominatim.openstreetmap.org/reverse?lat=%f&lon=%f&format=json&addressdetails=1",
                latitude, longitude
        );

        String response = restTemplate.getForObject(url, String.class);
        return parseCityFromResponse(response);
    }

    private String parseCityFromResponse(String response) {
        try {
            JsonNode jsonNode = objectMapper.readTree(response);
            JsonNode address = jsonNode.get("address");
            if (address.has("city")) {
                return address.get("city").asText();
            } else if (address.has("town")) {
                return address.get("town").asText();
            } else if (address.has("village")) {
                return address.get("village").asText();
            } else {
                return "City not found";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Error parsing response";
        }
    }
}
