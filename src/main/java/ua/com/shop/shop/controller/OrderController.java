package ua.com.shop.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ua.com.shop.shop.dto.request.OrderRequest;
import ua.com.shop.shop.dto.response.DataResponse;
import ua.com.shop.shop.dto.response.OrderResponse;
import ua.com.shop.shop.exeption.WrongInputException;
import ua.com.shop.shop.service.OrderService;
import java.util.List;


@CrossOrigin
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public DataResponse<OrderResponse> getAll(@RequestParam Integer page, @RequestParam Integer size){
        return orderService.findAll(page,size);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public OrderResponse findOne(@PathVariable Long id) throws WrongInputException {
        return orderService.findOneById(id);
    }

    @GetMapping("/selector")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public List<OrderResponse> findAll() {
        return orderService.findAllSelector();
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public OrderResponse save(@RequestBody OrderRequest orderRequest) throws WrongInputException {
        return orderService.save(orderRequest);
    }

    @PutMapping
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public OrderResponse update(@RequestBody OrderRequest orderRequest, @RequestParam Long id) throws WrongInputException {
        return orderService.update(orderRequest, id);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public void delete(@PathVariable Long id) throws WrongInputException {
        orderService.delete(id);
    }


}
