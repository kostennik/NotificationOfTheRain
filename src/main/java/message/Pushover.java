package message;

import http.HttpClient;

public class Pushover {
    private final String token;
    private final HttpClient httpClient = new HttpClient();

    public Pushover(String token) {
        this.token = token;

    }

    public String  sendMessage(String user, String  title, String message) throws Exception {

        StringBuilder request = new StringBuilder();
        request.append("token=").append(token)
                .append("&user=").append(user)
                .append("&device=droid")
                .append("&title=").append(title)
                .append("&message=").append(message);

        String url = "https://api.pushover.net/1/messages.json";
        String contentType = "application/x-www-form-urlencoded";

        //send data
        String response = httpClient.sendPost(url, request.toString(), contentType);
        return response;
    }
}
