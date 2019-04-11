package ua.com.shop.shop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ua.com.shop.shop.dto.request.OrderForProductRequest;
import ua.com.shop.shop.dto.response.DataResponse;
import ua.com.shop.shop.dto.response.OrderForProductResponse;
import ua.com.shop.shop.entity.OrderForProduct;
import ua.com.shop.shop.entity.User;
import ua.com.shop.shop.exeption.WrongInputException;
import ua.com.shop.shop.repository.OrderForProductRepository;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderForProductService {

    @Autowired
    private OrderForProductRepository orderForProductRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;


    public OrderForProductResponse save(OrderForProductRequest request) throws WrongInputException {
        return new OrderForProductResponse(categoryRequestToCategory(request, null));
    }

    public OrderForProductResponse update(OrderForProductRequest request, Long id) throws WrongInputException {
        return new OrderForProductResponse(categoryRequestToCategory(request, findOne(id)));
    }


    public void delete(Long id) throws WrongInputException {
        orderForProductRepository.delete(findOne(id));
    }

    @Transactional
    public OrderForProduct findOne(Long id) throws WrongInputException {
        return orderForProductRepository.findById(id)
                .orElseThrow(() -> new WrongInputException("Category with id " + id + " not exists"));
    }

    public OrderForProductResponse findOneById(Long id) throws WrongInputException {
        return new OrderForProductResponse(findOne(id));
    }

    public DataResponse<OrderForProductResponse> findAll(Integer page, Integer size){
        PageRequest pageRequest = PageRequest.of(page,size);
        Page<OrderForProduct> pageCategory = orderForProductRepository.findAll(pageRequest);
        return new DataResponse<OrderForProductResponse>(pageCategory.stream().map(OrderForProductResponse::new).collect(Collectors.toList()), pageCategory);
    }

    public List<OrderForProductResponse> findAllSelector() {
        return orderForProductRepository.findAll().stream()
                .map(OrderForProductResponse::new)
                .collect(Collectors.toList());
    }

    private OrderForProduct categoryRequestToCategory(OrderForProductRequest request, OrderForProduct orderForProduct) throws WrongInputException {
        if (orderForProduct == null) {
            orderForProduct = new OrderForProduct();
        }
        orderForProduct.setProduct(productService.findOne(request.getProductId()));
        orderForProduct.setAmount(request.getAmount());
        orderForProduct.setUser(userService.findOne(request.getUserId()));
        orderForProduct.setAddToOrder(request.getAddToOrder());
        orderForProduct.setAddToOrder(false);
        return orderForProductRepository.save(orderForProduct);

    }
}
