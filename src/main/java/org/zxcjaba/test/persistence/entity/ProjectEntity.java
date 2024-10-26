package org.zxcjaba.test.persistence.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name="projects")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProjectEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NonNull
    Long ID;

    @Column(unique = true)
    @NonNull
    String name;

    @NonNull
    String description;

    @NonNull
    Long trackedTime;

    @NonNull
    Long userId;

}
