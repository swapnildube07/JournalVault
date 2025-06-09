package com.swapnil.journal.apiresponse;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class WeatherResponse{

    private Current current;





    @Data
    public class Current{
        private String observation_time;
        private int temperature;
        private List<String> weather_descriptions;




    }




}
