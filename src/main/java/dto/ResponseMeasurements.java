package dto;

import java.util.List;

public class ResponseMeasurements {
    List<MeasurementsDTO> measurements;

    public List<MeasurementsDTO> getMeasurements() {
        return measurements;
    }

    public void setMeasurements(List<MeasurementsDTO> measurements) {
        this.measurements = measurements;
    }
}
