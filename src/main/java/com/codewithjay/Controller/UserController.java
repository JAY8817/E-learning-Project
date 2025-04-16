package com.codewithjay.Controller;

import com.codewithjay.Dto.CustomMessage;
import com.codewithjay.Dto.UserDto;
import com.codewithjay.Services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/users")
public class UserController {

    @Autowired
    public UserServices userServices;

    @PostMapping("/")
    public ResponseEntity<UserDto> addUser(@RequestBody UserDto userDto ) {
        userServices.addUser(userDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(userDto);


    }

    @PutMapping("{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable String id, @RequestBody UserDto userDto ) {
        UserDto userDto1 = userServices.updateUser(id, userDto);
        return ResponseEntity.status(HttpStatus.OK).body(userDto1);

    }

    @DeleteMapping("{id}")
    public ResponseEntity<CustomMessage> deleteUser(@PathVariable String id) {
        userServices.deleteUser(id);

        CustomMessage customMessage = new CustomMessage();
        customMessage.setMessage("User deleted successfully by this id:- " + id);
        customMessage.setSuccess(true);
        return ResponseEntity.status(HttpStatus.OK).body(customMessage);
    }

    @GetMapping("/")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> allUsers = userServices.getAllUsers();
        return ResponseEntity.status(HttpStatus.OK).body(allUsers);
    }

    @GetMapping("{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable String id) {
        UserDto userDto = userServices.getUser(id);
        return ResponseEntity.status(HttpStatus.OK).body(userDto);
    }
}
