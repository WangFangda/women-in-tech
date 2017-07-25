package womenintech.api.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author fangda.wang
 */
@ConfigurationProperties("google.developer.api")
@Configuration
@Getter
@Setter
public class GoogleApiConfiguration {
    public String key;
    public String geocode;
    public String placePhoto;
    public String placeDetails;
}