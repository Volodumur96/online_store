package ua.com.shop.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ua.com.shop.shop.dto.request.OrderForProductRequest;
import ua.com.shop.shop.dto.response.DataResponse;
import ua.com.shop.shop.dto.response.OrderForProductResponse;
import ua.com.shop.shop.exeption.WrongInputException;
import ua.com.shop.shop.service.OrderForProductService;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/order_for")
public class OrderForProductController {

    @Autowired
    private OrderForProductService orderForProductService;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public DataResponse<OrderForProductResponse> getAll(@RequestParam Integer page, @RequestParam Integer size){
        return orderForProductService.findAll(page,size);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public OrderForProductResponse findOne(@PathVariable Long id) throws WrongInputException {
        return orderForProductService.findOneById(id);
    }

    @GetMapping("/selector")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public List<OrderForProductResponse> findAll() {
        return orderForProductService.findAllSelector();
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public OrderForProductResponse save(@RequestBody OrderForProductRequest orderForProductRequest) throws WrongInputException {
        return orderForProductService.save(orderForProductRequest);
    }

    @PutMapping
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public OrderForProductResponse update(@RequestBody OrderForProductRequest orderForProductRequest, @RequestParam Long id) throws WrongInputException {
        return orderForProductService.update(orderForProductRequest, id);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public void delete(@PathVariable Long id) throws WrongInputException {
        orderForProductService.delete(id);
    }


}
