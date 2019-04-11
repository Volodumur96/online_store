package ua.com.shop.shop.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ua.com.shop.shop.entity.Role;
import ua.com.shop.shop.entity.User;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class UserResponse {
    private Long id;
    private String name;
    private String login;
    private String password;
    private Role role;
    private List<OrderForProductResponse> orderForProductResponses;

    public UserResponse(User user) {
        id = user.getId();
        name = user.getName();
        login = user.getLogin();
        password = user.getPassword();
        role = user.getRole();
        orderForProductResponses = user.getOrderForProducts().stream().map(OrderForProductResponse::new).collect(Collectors.toList());

    }
}
