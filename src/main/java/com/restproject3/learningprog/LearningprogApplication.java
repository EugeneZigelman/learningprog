package com.restproject3.learningprog;


import com.fasterxml.jackson.core.JsonProcessingException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.restproject3.learningprog.model.Sensor;
import com.restproject3.learningprog.util.RequestMeasurements;


import dto.MeasurementsDTO;
import dto.ResponseMeasurements;
import org.json.simple.parser.ParseException;
import org.knowm.xchart.QuickChart;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;

import org.springframework.context.annotation.Bean;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class LearningprogApplication {

    static final RestTemplate restTemplate = new RestTemplate();
    static Sensor sensor = new Sensor();
    static Random rand = new Random();
    static HttpHeaders headers = new HttpHeaders();

    static ObjectMapper mapper = new ObjectMapper();

    private static RequestMeasurements requestMeasurements;


    public static void main(String[] args) throws IOException, ParseException {


        SpringApplication.run(LearningprogApplication.class, args);
        headers.setContentType(MediaType.APPLICATION_JSON);
        sensor.setName("Sensor9");

        try {
            addSensor(sensor);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        createRequests();

        List<Float> temperatures = getTemperatures();
        Diagram(temperatures);

    }

    private static void addSensor(Sensor sensor) throws JsonProcessingException {

        HttpEntity<String> entity = new HttpEntity<String>(mapper.writeValueAsString(sensor), headers);

        String res = restTemplate.exchange(
                "http://localhost:8080/sensors/registration", HttpMethod.POST, entity, String.class).getBody();
        System.out.println(res);
    }

    public static void createRequests() {

        for (int i = 0; i < 1000; i++) {
            RequestMeasurements requestMeasurements =
                    new RequestMeasurements(rand.nextFloat(0, 100), rand.nextBoolean(), sensor);

            createMeasurements(requestMeasurements);
        }
    }

    private static void createMeasurements(RequestMeasurements requestMeasurements) {

        HttpEntity<RequestMeasurements> entity = new HttpEntity<RequestMeasurements>(requestMeasurements, headers);

        String res = restTemplate.exchange(
                "http://localhost:8080/measurements/add", HttpMethod.POST, entity, String.class).getBody();
        System.out.println(res);
    }

    private static List<Float> getTemperatures() {

        ResponseMeasurements measurementsResponse =
                restTemplate.getForObject("http://localhost:8080/measurements", ResponseMeasurements.class);

        if (measurementsResponse == null || measurementsResponse.getMeasurements() == null)
            return Collections.emptyList();

        return measurementsResponse.getMeasurements().stream().map(MeasurementsDTO::getValue)
                .collect(Collectors.toList());
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    private static void Diagram(List<Float> temperatures) {
        double[] xData = IntStream.range(0, temperatures.size()).asDoubleStream().toArray();
        double[] yData = temperatures.stream().mapToDouble(x -> x).toArray();

        XYChart chart = QuickChart.getChart("Temperatures",
                "X", "Y", "temperature", xData, yData);

        new SwingWrapper(chart).displayChart();
    }

}
