package org.example.testgeohash;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static org.example.testgeohash.service.GeoHashService.decodeGeoHash;

@SpringBootApplication
public class TestGeoHashApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestGeoHashApplication.class, args);
        String geohash = "efkejne";
        double[] coordinates = decodeGeoHash(geohash);
        System.out.println("Latitude: " + coordinates[0] + ", Longitude: " + coordinates[1]);
    }

}
