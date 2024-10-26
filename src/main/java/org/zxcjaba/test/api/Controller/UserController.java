package org.zxcjaba.test.api.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.zxcjaba.test.api.Dto.ProjectDto;
import org.zxcjaba.test.api.Dto.UserDto;
import org.zxcjaba.test.api.Exceptions.NotFoundException;
import org.zxcjaba.test.persistence.entity.ProjectEntity;
import org.zxcjaba.test.persistence.entity.UserEntity;
import org.zxcjaba.test.persistence.repository.ProjectRepository;
import org.zxcjaba.test.persistence.repository.UserRepository;

@RequestMapping("/user")
@RestController
public class UserController {

    private final ProjectRepository projectRepository;

    private static final String me="/me";

    private static final String updateTime="/updatetime";
    private final UserRepository userRepository;

    public UserController(ProjectRepository projectRepository, UserRepository userRepository) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }


    @GetMapping(me)
    public ResponseEntity<UserDto> authenticatedUser() {


        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();


        UserEntity entity = (UserEntity) authentication.getPrincipal();



        UserDto dto=UserDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .password(entity.getPassword())
                .role(entity.getRole())
                .build();

        return ResponseEntity.ok(dto);

    }

    @PutMapping(updateTime)
    public ResponseEntity<ProjectDto> updateTime(@RequestParam(name="project_name")String projectName
            , @RequestParam(name="time")String time) {

        if(projectName.isEmpty()|| time.isEmpty()) {
            throw new NotFoundException("Project name or time are empty");
        }

        Long Time=0l;
        try{
            Time=Long.parseLong(time);

        }catch (NumberFormatException e){
            throw new NotFoundException("Time must be formatted in hours");
        }



        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();

        UserEntity entity = (UserEntity) authentication.getPrincipal();

        ProjectEntity project=projectRepository.findAllByUserIdAndName(entity.getId(),projectName).orElse(null);

        if(project==null) {
            throw new NotFoundException("Project not found");
        }
        else{
            project.setTrackedTime(project.getTrackedTime()-Time);
        }

        projectRepository.saveAndFlush(project);

        return ResponseEntity.ok(ProjectDto.builder()
                .id(project.getID())
                .name(project.getName())
                .description(project.getDescription())
                .userId(entity.getId())
                .timeRemaining(project.getTrackedTime())
                .build());

    }







}
