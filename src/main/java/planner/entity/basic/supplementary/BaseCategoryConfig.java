package planner.entity.basic.supplementary;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Class represents java object which correspond to json string with
 * user configurations with custom subCategories.
 */
@Getter
@Setter
@NoArgsConstructor
public class BaseCategoryConfig<T> {
    /**
     * Holds the list with category groups.
     */
    private List<T> config;

    @JsonIgnore
    public String getJsonString() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(this);
    }
}
