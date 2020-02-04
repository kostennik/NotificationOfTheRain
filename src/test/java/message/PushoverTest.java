package message;

import config.PropertyValues;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PushoverTest {
private PropertyValues properties;
private String tokenKey;
private String userKey;

@BeforeEach
    public void init() {
        properties = new PropertyValues("config.properties");
        tokenKey = properties.getPropValue("pushover.tokenKey");
        userKey = properties.getPropValue("pushover.userKey");
    }
    @Test
    public void sendMessage() throws Exception {
        Pushover pushover = new Pushover(tokenKey);
        pushover.sendMessage(userKey, "Title", "Message");

    }


}