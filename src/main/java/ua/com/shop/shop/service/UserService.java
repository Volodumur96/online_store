package ua.com.shop.shop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ua.com.shop.shop.dto.request.UserRequest;
import ua.com.shop.shop.dto.response.DataResponse;
import ua.com.shop.shop.dto.response.UserResponse;
import ua.com.shop.shop.entity.Role;
import ua.com.shop.shop.entity.User;
import ua.com.shop.shop.exeption.WrongInputException;
import ua.com.shop.shop.repository.UserRepository;
import ua.com.shop.shop.security.tokenUtils.TokenTool;

import javax.transaction.Transactional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenTool tokenTool;

    public DataResponse<UserResponse> findAll(Integer page, Integer size){
        PageRequest pageRequest = PageRequest.of(page,size);
        Page<User> pageUser = userRepository.findAll(pageRequest);
        return new DataResponse<UserResponse>(pageUser.stream().map(UserResponse::new).collect(Collectors.toList()), pageUser);
    }

    public UserResponse findOneById(Long id) throws WrongInputException {
        return new UserResponse(findOne(id));
    }

    @Transactional
    public User findOne(Long id) throws WrongInputException {
        return userRepository.findById(id).orElseThrow(() -> new WrongInputException("User with id " + id + " not exists"));
    }

    public void delete(Long id) throws WrongInputException {
        userRepository.delete(findOne(id));
    }

    public UserResponse save(UserRequest request) throws Exception {
        if (userRepository.findByLoginEquals(request.getLogin()).isPresent()) {
            throw new Exception("Credentials are busy. Please, try one more time " +
                    "with other login");
        }
        User user = new User();
        user.setLogin(request.getLogin());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setName(request.getName());

        user.setRole(Role.USER);

        user = userRepository.saveAndFlush(user);
        User saving = userRepository.save(user);
        Long id = saving.getId();
        Role role = saving.getRole();
        String password = tokenTool.createToken(user.getLogin(), user.getRole().name());


        return settingData(id,password, role);
    }

    private UserResponse settingData(Long id, String password, Role role){
        UserResponse userResponse = new UserResponse();
        userResponse.setId(id);
        userResponse.setRole(role);
        userResponse.setPassword(password);
        return userResponse;
    }
    public UserResponse findOneByRequest(UserRequest request) throws WrongInputException {
        User user = userRepository.findByLoginEquals(request.getLogin()).orElseThrow(() -> new WrongInputException("User with login " + request.getLogin() + " not exists"));

        if (passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            Long id = user.getId();
            Role role = user.getRole();
            String token = tokenTool.createToken(user.getLogin(), user.getRole().name());
            return settingData(id, token, role);
        }

        throw new IllegalArgumentException("Wrong login or password");
    }
}
