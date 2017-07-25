package womenintech.api.datamodel;

import lombok.*;

/**
 * @author fangda.wang
 */
@Data
@Getter
@Setter
@NoArgsConstructor

public class AutocompleteUnit {

    private String city_ufi;
    private String dest_id;
    private int rtl;
    private String hotels;
    private String dest_type;
    private String name;
    private String city_name;
    private String label;
    private String url;
    private String longitude;
    private String latitude;
    private String lc;
    private String region;
    private String country;
    private String nr_hotels;
    private String cc1;
    private String type;
}
