package connector;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by ralemy on 8/8/16.
 * Reads Itemsense credentials and base from config file
 */
@ConfigurationProperties(prefix="itemsense",ignoreUnknownFields = true)
@Component
public class ItemsenseProperties {
    public String baseUrl;
    public String username;
    public String password;

    public ItemsenseProperties() {
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
