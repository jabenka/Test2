package org.zxcjaba.test.api.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level= AccessLevel.PRIVATE)
public class AnswerDtoForProject {

    @JsonProperty("project_name")
    String projectName;

    @JsonProperty("assigned_user")
    Long id;

    @JsonProperty("time_remaining")
    Long timeRemaining;

}
