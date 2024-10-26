package org.zxcjaba.test.api.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.zxcjaba.test.api.Dto.AnswerDtoForProject;
import org.zxcjaba.test.api.Dto.ProjectDto;
import org.zxcjaba.test.api.Dto.RegistrationDto;
import org.zxcjaba.test.api.Dto.UserDto;
import org.zxcjaba.test.api.Exceptions.AccessExeption;
import org.zxcjaba.test.api.Exceptions.NotFoundException;
import org.zxcjaba.test.api.Sevices.AuthenticationService;
import org.zxcjaba.test.persistence.entity.ProjectEntity;
import org.zxcjaba.test.persistence.entity.Role;
import org.zxcjaba.test.persistence.entity.UserEntity;
import org.zxcjaba.test.persistence.repository.ProjectRepository;
import org.zxcjaba.test.persistence.repository.UserRepository;

@RestController
public class AdminController {

    private final ProjectRepository projectRepository;

    private final UserRepository userRepository;

    private final AuthenticationService authenticationService;



    private static final String me="/admin/me";

    private static final String addProject="admin/addProject";

    private static final String addUser="admin/addUser";


    public AdminController(ProjectRepository projectRepository, UserRepository userRepository, AuthenticationService authenticationService) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
        this.authenticationService = authenticationService;
    }


    @GetMapping(me)
    public ResponseEntity<UserDto> authenticatedAdmin(){

        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();


        UserEntity entity = (UserEntity) authentication.getPrincipal();
        UserDto dto=UserDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .password(entity.getPassword())
                .role(entity.getRole())
                .build();



        if(dto.getRole().equals(Role.USER)){
            throw new AccessExeption("You are not allowed to access this resource");
        }else {

            return ResponseEntity.ok(dto);
        }



    }



    @PutMapping(addProject)
    public ResponseEntity<AnswerDtoForProject> addProject(@RequestParam(name = "user_name",required = true)String userName
            ,@RequestParam(name = "project_name",required = true) String projectName
    ,@RequestParam(name = "project_desc",required = true)String projectDesc
    ,@RequestParam(name="time",required = true)String time) {

        if(userName.isEmpty()||projectName.isEmpty() || projectDesc.isEmpty() || time.isEmpty()){
            throw new NotFoundException("all fields are mandatory");
        }

        Long Time=0l;
        try{
            Time=Long.parseLong(time);

        }catch (NumberFormatException e){
            throw new NotFoundException("time must be formatted in hours");
        }


        if(projectRepository.findByName(projectName).isPresent()){
            throw new AccessExeption("Project already exists");
        }

        ProjectEntity project=new ProjectEntity();
                project.setName(projectName);
                project.setDescription(projectDesc);
               project.setTrackedTime(Time);






        UserEntity entity=userRepository.findByName(userName).orElseThrow(()-> new NotFoundException("user not found"));

        project.setUserId(entity.getId());
        projectRepository.saveAndFlush(project);


        ProjectDto dto=ProjectDto.builder()
                .id(project.getID())
                .name(projectName)
                .description(projectDesc)
                .userId(entity.getId())
                .timeRemaining(project.getTrackedTime())
                .build();


        AnswerDtoForProject ans=AnswerDtoForProject.builder()
                .projectName(dto.getName())
                .id(dto.getUserId())
                .timeRemaining(dto.getTimeRemaining())
                .build();

        return  ResponseEntity.ok(ans);

    }


    @PostMapping(addUser)
    public ResponseEntity<UserDto> register(@RequestBody RegistrationDto registrationDto) {
        UserDto user=authenticationService.signUp(registrationDto);
        return ResponseEntity.ok(user);
    }






}
