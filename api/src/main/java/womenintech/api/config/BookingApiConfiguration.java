package womenintech.api.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author fangda.wang
 */
@Configuration
@ConfigurationProperties("booking.api")
@Getter
@Setter
public class BookingApiConfiguration {
    public String username;
    public String password;
    public String getHotelUrl;
    public String getDistrictHotelUrl;
    public String autocompleteUrl;
}
