package com.zunaidhossain.fyi_its_anonymous.Config;

import java.util.Random;
import java.util.UUID;

public class Base62Util {
    private static final String BASE62 = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    // Encode a positive long value to a base62 string
    public static String encode(long value) {
        StringBuilder base62 = new StringBuilder();
        if (value == 0) {
            return "0";
        }
        while (value > 0) {
            base62.append(BASE62.charAt((int) (value % 62)));
            value /= 62;
        }
        return base62.reverse().toString();
    }

    // Generate a unique alphanumeric link
    public static String generateUniqueLink() {
        Random random = new Random();
        long timestamp = System.currentTimeMillis();
        long randomValue = Math.abs(random.nextLong());  // Ensure positive random value
        String base62Part = encode(timestamp + randomValue);

        // Add extra randomness with a few characters from UUID
        String uuidPart = UUID.randomUUID().toString().substring(0, 5); // Take first 5 chars of UUID
        return base62Part + uuidPart;
    }
}