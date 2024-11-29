package org.example.testgeohash.service;


import ch.hsr.geohash.GeoHash;
import ch.hsr.geohash.WGS84Point;
import org.springframework.stereotype.Service;

@Service
public class GeoHashService {

    public static double[] decodeGeoHash(String geohash) {
        GeoHash geoHash = GeoHash.fromGeohashString(geohash);
        WGS84Point point = geoHash.getOriginatingPoint();
        return new double[] { point.getLatitude(), point.getLongitude() };
    }
}

