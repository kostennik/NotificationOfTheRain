package awsHandler;


import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import config.PropertyValues;
import lombok.SneakyThrows;
import message.Pushover;
import weather.Weather;

import java.util.Map;

public class LambdaMethodHandler implements RequestHandler<Map<String,String>, String> {
    private final PropertyValues properties;
    private final String pushoverTokenKey;
    private final String pushoverUserKey;
    private final String accuweatherApiKey;
    private final String accuweatherLanguage;
    private final String accuweatherDetails;
    private final String accuweatherMetric;

    {
        properties = new PropertyValues("config.properties");
        pushoverTokenKey = properties.getPropValue("pushover.tokenKey");
        pushoverUserKey = properties.getPropValue("pushover.userKey");
        accuweatherApiKey = properties.getPropValue("accuweather.apiKey");
        accuweatherLanguage = properties.getPropValue("accuweather.language");
        accuweatherDetails = properties.getPropValue("accuweather.details");
        accuweatherMetric = properties.getPropValue("accuweather.metric");
    }

    @Override
    public String handleRequest(Map<String,String> event, Context context) {

        Pushover pushover = new Pushover(pushoverTokenKey);
        try {
            Weather weather = Weather.getWeather(
                    accuweatherApiKey,
                    accuweatherLanguage,
                    accuweatherDetails,
                    accuweatherMetric);

            if (willBeRain(weather)) {
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

    private boolean willBeRain(Weather weatherCurrent) {
        //...
        return true;
        }
    }

}
