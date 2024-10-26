package org.zxcjaba.test.api.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.zxcjaba.test.persistence.entity.Role;


/*
that's no need to create AdminDto like I thought cause if we create admin dto
we have to make 2 methods for user and admin with similar source code, so I decided
to use only UserDto and check the role, but we need the AdminEntity cause admin don't have a list of projects,
yeah we can just leave it empty but its just not pretty and not secure because anyone can call this field and then catch an exception
so that's why we need AdminEntity and don't need AdminDto

Never mind,admin dto is useless
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDto {

    @NonNull
    @JsonProperty("id")
    Long id;

    @NonNull
    @JsonProperty("name")
    String name;

    @NonNull
    String password;

    @NonNull
    @JsonProperty("role")
    Role role=Role.USER;


}
