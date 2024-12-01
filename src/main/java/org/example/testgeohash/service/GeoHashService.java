package org.example.testgeohash.service;

import ch.hsr.geohash.GeoHash;
import ch.hsr.geohash.WGS84Point;
import org.springframework.stereotype.Service;
import org.springframework.context.event.EventListener;
import org.springframework.boot.context.event.ApplicationReadyEvent;

@Service
public class GeoHashService {

    private static final double EARTH_RADIUS_KM = 6371.0; // Средний радиус Земли в километрах
    private final NominatimService nominatimService = new NominatimService();

    public double[] decodeGeoHash(String geohash) {
        GeoHash geoHash = GeoHash.fromGeohashString(geohash);
        WGS84Point point = geoHash.getOriginatingPoint();
        return new double[] { point.getLatitude(), point.getLongitude() };
    }

    public double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        // Перевод координат из градусов в радианы
        double lat1Rad = Math.toRadians(lat1);
        double lon1Rad = Math.toRadians(lon1);
        double lat2Rad = Math.toRadians(lat2);
        double lon2Rad = Math.toRadians(lon2);
        // Разница широт и долгот
        double deltaLat = lat2Rad - lat1Rad;
        double deltaLon = lon2Rad - lon1Rad;
        // Формула Haversine
        double a = Math.sin(deltaLat / 2) * Math.sin(deltaLat / 2) +
                Math.cos(lat1Rad) * Math.cos(lat2Rad) *
                        Math.sin(deltaLon / 2) * Math.sin(deltaLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        // Расстояние
        return EARTH_RADIUS_KM * c;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void print() {
        String geohashFirst = "geohash1";
        String geohashSecond = "geohash2";
        double[] coordinatesFirst = decodeGeoHash(geohashFirst);
        double[] coordinatesSecond = decodeGeoHash(geohashSecond);
        double distance = calculateDistance(
                coordinatesFirst[0], coordinatesFirst[1],
                coordinatesSecond[0], coordinatesSecond[1]
        );
        System.out.println("Latitude: " + coordinatesFirst[0] + ", Longitude: " + coordinatesFirst[1]);
        System.out.println("Latitude: " + coordinatesSecond[0] + ", Longitude: " + coordinatesSecond[1]);
        System.out.println("Distance between points: " + distance + " km");
        System.out.println("Distance between points: " + nominatimService.getCityByCoordinates(coordinatesFirst[0], coordinatesFirst[1]) + " km");
        System.out.println("Distance between points: " + nominatimService.getCityByCoordinates(coordinatesSecond[0], coordinatesSecond[1]) + " km");
    }
}



