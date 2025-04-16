package com.codewithjay.ServicesImpl;

import com.codewithjay.Config.AppConstant;
import com.codewithjay.Dto.UserDto;
import com.codewithjay.Entities.Roles;
import com.codewithjay.Entities.User;
import com.codewithjay.Exceptions.ResourceNotFoundException;
import com.codewithjay.Reposetories.RoleRepo;
import com.codewithjay.Reposetories.UserRepo;
import com.codewithjay.Services.UserServices;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserImpl implements UserServices {


    @Autowired
    public ModelMapper modelMapper;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepo roleRepo;



    @Override
    public UserDto addUser(UserDto userDto) {

//        Roles role1 = new Roles();
//        role1.setRoleId(UUID.randomUUID().toString());
//        role1.setRoleName(AppConstant.ROLE_ADMIN);
//
//        Roles role2 = new Roles();
//        role2.setRoleId(UUID.randomUUID().toString());
//        role2.setRoleName(AppConstant.ROLE_USER);

        User map = modelMapper.map(userDto, User.class);

        map.setUserId(UUID.randomUUID().toString());
        map.setCreateAt(new Date());
        map.setPassword(passwordEncoder.encode(map.getPassword()));
        Roles roleAssign = roleRepo.findByRoleName(AppConstant.ROLE_USER).orElseThrow(() -> new ResourceNotFoundException("Role not assign"));
        map.addRole(roleAssign);

        User save = userRepo.save(map);

        return modelMapper.map(save, UserDto.class);

    }

    @Override
    public UserDto updateUser(String id, UserDto userDto) {

        User user = new User();
        user.setUserId(id);
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setPhoneNumber(userDto.getPhoneNumber());
        user.setAbout(userDto.getAbout());
        user.setActive(userDto.isActive());


        User save = userRepo.save(user);
        User map = this.modelMapper.map(save, User.class);


        return modelMapper.map(map, UserDto.class);
    }

    @Override
    public void deleteUser(String id) {

        User user = userRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not find by this id " + id));
        userRepo.delete(user);


    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> all = userRepo.findAll();
        List<UserDto> collect = all.stream().map(content -> modelMapper.map(content, UserDto.class)).collect(Collectors.toList());


        return collect;

    }

    @Override
    public UserDto getUser(String id) {

        User user = userRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not find by this id " + id));
        user.setRoles(user.getRoles());

        return modelMapper.map(user, UserDto.class);
    }
}
