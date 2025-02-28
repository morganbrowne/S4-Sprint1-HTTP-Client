package com.keyin.http.cli;

import com.keyin.domain.Airport;
import com.keyin.http.client.RESTClient;

import java.util.List;

public class AirportClientApplication {

    private RESTClient restClient;

    public String generateAirportReport() {
        List<Airport> airports = getRestClient().getAllAirports();

        StringBuffer report = new StringBuffer();

        for (Airport airport : airports) {
            report.append(airport.getName());
            report.append(" - ");
            report.append(airport.getCode());

            if (airports.indexOf(airport) != (airports.size() - 1)) {
                report.append(",");
            }
        }

        System.out.println(report.toString());

        return report.toString();
    }

    private void listGreetings() {
        System.out.println(getRestClient().getResponseFromHTTPRequest());
    }

    public RESTClient getRestClient() {
        if (restClient == null) {
            restClient = new RESTClient();
        }

        return restClient;
    }

    public void setRestClient(RESTClient restClient) {
        this.restClient = restClient;
    }

    public static void main(String[] args) {
        // Check if the arguments are passed
        if (args.length == 0) {
            System.out.println("Error: No server URL provided.");
            return;  // Exit the program if no arguments are provided
        }

        // Print the arguments
        for (String arg : args) {
            System.out.println(arg);
        }

        // Initialize the application
        AirportClientApplication cliApp = new AirportClientApplication();

        // Get the server URL (first argument)
        String serverURL = args[0];

        if (serverURL != null && !serverURL.isEmpty()) {
            RESTClient restClient = new RESTClient();
            restClient.setServerURL(serverURL);

            cliApp.setRestClient(restClient);

            // Perform actions based on the URL
            if (serverURL.contains("greeting")) {
                cliApp.listGreetings();
            } else {
                cliApp.generateAirportReport();
            }
        }
    }
}
