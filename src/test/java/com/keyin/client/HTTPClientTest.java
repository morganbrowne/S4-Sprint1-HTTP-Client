package com.keyin.client;

import com.keyin.domain.Airport;
import com.keyin.http.cli.AirportClientApplication;
import com.keyin.http.client.RESTClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class HTTPClientTest {
    @Mock
    private RESTClient mockRESTClient;

    @Test
    public void testGenerateAirportReport() {
        AirportClientApplication httpRestCLIApplicationUnderTest = new AirportClientApplication();

        Airport stJohnsAirport = new Airport();
        stJohnsAirport.setCode("YYT");
        stJohnsAirport.setName("St. John's Airport");
        stJohnsAirport.setId("1");

        List<Airport> airportList = new ArrayList<Airport>();
        airportList.add(stJohnsAirport);

        Mockito.when(mockRESTClient.getAllAirports()).thenReturn(airportList);

        httpRestCLIApplicationUnderTest.setRestClient(mockRESTClient);

        Assertions.assertTrue(httpRestCLIApplicationUnderTest.generateAirportReport().contains("YYT"));
    }


    public void _testGenerateAirportReportWithError() {
        AirportClientApplication httpRestCLIApplicationUnderTest = new AirportClientApplication();

        RESTClient restClient = new RESTClient();
        restClient.setServerURL("http://localhost:8080/airports");

        httpRestCLIApplicationUnderTest.setRestClient(restClient);

        Assertions.assertTrue(httpRestCLIApplicationUnderTest.generateAirportReport().contains("YYT"));
    }
}