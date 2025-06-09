package com.swapnil.journal.service;

import com.swapnil.journal.apiresponse.WeatherResponse;
import com.swapnil.journal.cache.AppCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class WeatherService {


    @Value("${weather.api.key}")
    private String apikey;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private RedisService redisService;


    @Autowired
    private AppCache appCache;

    public WeatherResponse getWeather(String city) {

       WeatherResponse weatherResponse= redisService.get("weather_of_" + city ,WeatherResponse.class);
       if(weatherResponse != null){
           return weatherResponse;
       }else {


           String encodedCity = city.replace(" ", "%20");


           String finalApi = appCache.App_Cache.get("weather.api.key").replace("CITY", encodedCity).replace("API_KEY", apikey);
           ResponseEntity<WeatherResponse> response = restTemplate.exchange(finalApi, HttpMethod.GET, null, WeatherResponse.class);
           WeatherResponse body = response.getBody();
           if(body != null){
               redisService.set("weather_of_" + city ,body,300l);
           }
           return body;
       }
    }

}
