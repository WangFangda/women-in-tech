package womenintech.api.datamodel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * @author fangda.wang
 */
@Data
@Getter
@Setter
@Document(collection = "histories")
@AllArgsConstructor
public class History {
    @Id
    private int userId;
    private List<Preference> preferences;

}
