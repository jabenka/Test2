package org.zxcjaba.test.api.Dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProjectDto {

    @NonNull
    @JsonProperty("id")
    Long id;

    @NonNull
    @JsonProperty("user_id")
    Long userId;

    @NonNull
    @JsonProperty("name")
    String name;


    @NonNull
    @JsonProperty("description")
    String description;

    @NonNull
    @JsonProperty("time_remaining")
    Long timeRemaining;

}
