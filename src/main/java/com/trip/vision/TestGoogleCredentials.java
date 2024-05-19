package com.trip.vision;

import java.io.IOException;

import com.google.auth.oauth2.GoogleCredentials;

public class TestGoogleCredentials {
    public static void main(String[] args) {
        try {
            GoogleCredentials credentials = GoogleCredentials.getApplicationDefault();
            System.out.println("Credentials successfully obtained: " + credentials);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
