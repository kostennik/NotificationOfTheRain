package weather;

import com.jayway.jsonpath.JsonPath;
import http.HttpClient;
import lombok.*;

@Data
@Getter
@Setter
public class Weather {
    private static HttpClient httpClient = new HttpClient();
    double tempMin;
    double tempMax;
    String descriptionDay;
    String descriptionNight;


    public Weather(double tempMin, double tempMax, String descriptionDay, String descriptionNight) {
        this.tempMin = tempMin;
        this.tempMax = tempMax;
        this.descriptionDay = descriptionDay;
        this.descriptionNight = descriptionNight;
    }


    public static Weather getWeather(String url, String apiKey, String language, Boolean details, Boolean metric) throws Exception {

        String request = url + "?apikey=" + apiKey + "&language=" + language + "&details=" + details + "&metric=" + metric;
        String response = httpClient.sendGet(request);

        //parsing json-file with weather
        double tempMin = JsonPath.read(response, "$.DailyForecasts[1].Temperature.Minimum.Value");
        double tempMax = JsonPath.read(response, "$.DailyForecasts[1].Temperature.Maximum.Value");
        String descriptionDay = JsonPath.read(response, "$.DailyForecasts[1].Day.LongPhrase");
        String descriptionNight = JsonPath.read(response, "$.DailyForecasts[1].Night.LongPhrase");

        return new Weather(
                tempMin, tempMax, descriptionDay, descriptionNight
        );
    }
}
