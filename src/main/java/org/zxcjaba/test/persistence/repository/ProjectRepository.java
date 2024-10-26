package org.zxcjaba.test.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.zxcjaba.test.persistence.entity.ProjectEntity;

import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<ProjectEntity,Long> {

    Optional<ProjectEntity> findByName(String name);

    Optional<ProjectEntity> findAllByUserId(Long id);

    Optional<ProjectEntity> findAllByUserIdAndName(Long id,String name);
}
