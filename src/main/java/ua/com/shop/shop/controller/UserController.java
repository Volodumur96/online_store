package ua.com.shop.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ua.com.shop.shop.dto.request.UserRequest;
import ua.com.shop.shop.dto.response.DataResponse;
import ua.com.shop.shop.dto.response.UserResponse;
import ua.com.shop.shop.exeption.WrongInputException;
import ua.com.shop.shop.service.UserService;

@CrossOrigin
@RestController
@RequestMapping("/user")

public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public DataResponse<UserResponse> findAll (@RequestParam Integer page, @RequestParam Integer size){
        return userService.findAll(page,size);
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public UserResponse findOne(@PathVariable Long id) throws WrongInputException {
        return userService.findOneById(id);
    }

    @PostMapping("/public/register")
    public UserResponse saveUser(@RequestBody UserRequest userRequest) throws Exception {

        return userService.save(userRequest);
    }

    @PostMapping("/public/login")
    public UserResponse login(@RequestBody UserRequest userRequest) throws Exception {
        return userService.findOneByRequest(userRequest);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public void delete(@PathVariable Long id) throws WrongInputException {
        userService.delete(id);
    }
}
