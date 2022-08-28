package com.restproject3.learningprog.util;


import com.restproject3.learningprog.model.Sensor;

import java.util.List;
import java.util.function.Function;


public class RequestMeasurements  {

    private float value;
    private Sensor sensor;

    List<Float> measurements;


    public List<Float> getMeasurements() {
        return measurements;
    }

    public void setMeasurements(List<Float> measurements) {
        this.measurements = measurements;
    }

    private boolean raining;

    public RequestMeasurements( Sensor sensor) {
     
        this.sensor = sensor;   }

    public RequestMeasurements(float value, boolean raining,Sensor sensor) {
        this.sensor = sensor;
        this.value = value;
        this.raining = raining;
    }

    public  float getValue() {
        return value;
    }

    public  void setValue(float value) {
        this.value = value;
    }

    public boolean isRaining() {
        return raining;
    }

    public void setRaining(boolean raining) {
        this.raining = raining;
    }

    public Sensor getSensor() {
        return sensor;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }

    public RequestMeasurements() {    }



}
