package awsHandler;


import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import config.PropertyValues;
import message.Pushover;
import weather.Weather;

import java.util.Map;

public class LambdaMethodHandler implements RequestHandler<Map<String, String>, String> {
    private final PropertyValues properties;
    private final String pushoverTokenKey;
    private final String pushoverUserKey;
    private final String accuweatherApiKey;
    private final String accuweatherCityCode;
    private final String accuweatherLanguage;
    private final String accuweatherDetails;
    private final String accuweatherMetric;

    {
        properties = new PropertyValues("config.properties");
        pushoverTokenKey = properties.getPropValue("pushover.tokenKey");
        pushoverUserKey = properties.getPropValue("pushover.userKey");
        accuweatherApiKey = properties.getPropValue("accuweather.apiKey");
        accuweatherCityCode = properties.getPropValue("accuweather.locationKey");
        accuweatherLanguage = properties.getPropValue("accuweather.language");
        accuweatherDetails = properties.getPropValue("accuweather.details");
        accuweatherMetric = properties.getPropValue("accuweather.metric");
    }

    @Override
    public String handleRequest(Map<String, String> event, Context context) {

        Pushover pushover = new Pushover(pushoverTokenKey);
        try {
            Weather weather = Weather.getWeather(
                    accuweatherCityCode,
                    accuweatherApiKey,
                    accuweatherLanguage,
                    accuweatherDetails,
                    accuweatherMetric);
            String [] keyWords = {"opady", "deszcz", "burze"};

            if (isMatch(weather, keyWords)) {
                String status = pushover.sendMessage(pushoverUserKey, "Weź parasol!",
                        "Pogoda na jutro - popołudnie: " + weather.getDescriptionDay() +
                                "; w nocy: " + weather.getDescriptionNight() + ", temperatura: " +
                                weather.getTempMin() + "C, - " + weather.getTempMax() + "C");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "200 OK";
    }

    public boolean isMatch(Weather weather, String ... keyWords) {
        boolean found = false;

        for (String item : keyWords) {
            if ((weather.getDescriptionDay() + " " + weather.getDescriptionNight()).contains(item)) {
                found = true;
                break;
            }
        }
        return found;
    }
}

