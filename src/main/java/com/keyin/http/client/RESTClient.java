package com.keyin.http.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.keyin.domain.Airport;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class RESTClient {
    private String serverURL;
    private HttpClient client;

    public String getResponseFromHTTPRequest() {
        String responseBody = "";
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(serverURL)).build();

        try {
            HttpResponse<String> response = getClient().send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 200) {
                System.out.println("Status Code: " + response.statusCode());
            }

            responseBody = response.body();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return responseBody;
    }

    public List<Airport> getAllAirports() {
        List<Airport> airports = new ArrayList<>();

        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(serverURL)).build();

        try {
            HttpResponse<String> response = getClient().send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                System.out.println("***** " + response.body());
            } else {
                System.out.println("Error Status Code: " + response.statusCode());
            }

            airports = buildAirportListFromResponse(response.body());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return airports;
    }

    public List<Airport> buildAirportListFromResponse(String response) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(response);
            JsonNode airportsNode = rootNode.path("_links").path("airports");

            if (!airportsNode.isMissingNode()) {
                String airportsUrl = airportsNode.path("href").asText();
                return fetchAirportsFromUrl(airportsUrl);
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public List<Airport> fetchAirportsFromUrl(String url) {
        List<Airport> airports = new ArrayList<>();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();

        try {
            HttpResponse<String> response = getClient().send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                airports = objectMapper.readValue(response.body(), new TypeReference<List<Airport>>() {});
            } else {
                System.out.println("Error fetching airports: " + response.statusCode());
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return airports;
    }

    public String getServerURL() {
        return serverURL;
    }

    public void setServerURL(String serverURL) {
        this.serverURL = serverURL;
    }

    public HttpClient getClient() {
        if (client == null) {
            client = HttpClient.newHttpClient();
        }
        return client;
    }
}
