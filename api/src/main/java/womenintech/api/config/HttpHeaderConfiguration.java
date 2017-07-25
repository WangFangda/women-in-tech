package womenintech.api.config;

import lombok.Getter;
import lombok.Setter;
import lombok.val;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

/**
 * @author fangda.wang
 */
@Configuration
public class HttpHeaderConfiguration {

    private static final String COLON = ":";
    private static final String AUTHORIZATION = "Authorization";

    @Bean
    public HttpHeaders getHttpHeaders(BookingApiConfiguration aConfiguration) {
        val plainCreds = aConfiguration.username+":"+aConfiguration.password;
        val plainCredsByte = plainCreds.getBytes();
        val base64CredsByte = Base64.encodeBase64(plainCredsByte);
        val base64Creds = new String(base64CredsByte);
        HttpHeaders result = new HttpHeaders();
        result.add(AUTHORIZATION, "Basic " + base64Creds);
        return result;
    }
}
