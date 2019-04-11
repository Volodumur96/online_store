package ua.com.shop.shop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ua.com.shop.shop.dto.request.OrderRequest;
import ua.com.shop.shop.dto.response.DataResponse;
import ua.com.shop.shop.dto.response.OrderResponse;
import ua.com.shop.shop.entity.Order;
import ua.com.shop.shop.entity.OrderForProduct;
import ua.com.shop.shop.exeption.WrongInputException;
import ua.com.shop.shop.repository.OrderRepository;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderForProductService orderForProductService;

    @Autowired
    private UserService userService;


    public OrderResponse save(OrderRequest request) throws WrongInputException {
        return new OrderResponse(orderRequestToOrder(request, null));
    }

    public OrderResponse update(OrderRequest request, Long id) throws WrongInputException {
        return new OrderResponse(orderRequestToOrder(request, findOne(id)));
    }


    public void delete(Long id) throws WrongInputException {
        orderRepository.delete(findOne(id));
    }

    @Transactional
    public Order findOne(Long id) throws WrongInputException {
        return orderRepository.findById(id)
                .orElseThrow(() -> new WrongInputException("Category with id " + id + " not exists"));
    }

    public OrderResponse findOneById(Long id) throws WrongInputException {
        return new OrderResponse(findOne(id));
    }

    public DataResponse<OrderResponse> findAll(Integer page, Integer size){
        PageRequest pageRequest = PageRequest.of(page,size);
        Page<Order> pageOrder = orderRepository.findAll(pageRequest);
        return new DataResponse<OrderResponse>(pageOrder.stream().map(OrderResponse::new).collect(Collectors.toList()), pageOrder);
    }

    public List<OrderResponse> findAllSelector() {
        return orderRepository.findAll().stream()
                .map(OrderResponse::new)
                .collect(Collectors.toList());
    }

    private Order orderRequestToOrder(OrderRequest request, Order order) throws WrongInputException {
        if (order == null) {
            order = new Order();
        }
        order.setDate(LocalDate.now());
        order.setUser(userService.findOne(request.getUserId()));

        for (Long orderForProduct : request.getOrderForProductId()) {
            OrderForProduct orderForProduct1 = orderForProductService.findOne(orderForProduct);
            orderForProduct1.setAddToOrder(true);
            order.getOrderForProducts().add(orderForProduct1);
        }

        return orderRepository.save(order);
    }
}
