package ua.com.shop.shop.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ua.com.shop.shop.dto.request.ProductFilterRequest;
import ua.com.shop.shop.dto.request.ProductRequest;
import ua.com.shop.shop.dto.response.DataResponse;
import ua.com.shop.shop.dto.response.ProductResponse;
import ua.com.shop.shop.exeption.WrongInputException;
import ua.com.shop.shop.service.ProductService;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/public")
    public DataResponse<ProductResponse> getProduct(@RequestParam(required = false) String value,
                                                   @RequestParam Integer page,
                                                   @RequestParam Integer size,
                                                   @RequestParam String sortName,
                                                   @RequestParam Sort.Direction direction){
        return productService.findAll(page,size,sortName,direction);
    }


    @GetMapping("/public/{id}")
    public ProductResponse findOne(@PathVariable Long id) throws WrongInputException {
        return productService.findOneById(id);
    }

    @GetMapping("/public/selector")
    public List<ProductResponse> findAll() {
        return productService.findAllSelector();
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ProductResponse save(@RequestBody ProductRequest productRequest) throws WrongInputException {
        return productService.save(productRequest);
    }

    @PostMapping("/public/filter")
    public DataResponse<ProductResponse> findAllByFilter(@RequestBody ProductFilterRequest productFilterRequest) {
        return productService.findByFilter(productFilterRequest);
    }

    @PutMapping
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ProductResponse update(@RequestBody ProductRequest productRequest, @RequestParam Long id) throws WrongInputException {
        return productService.update(id, productRequest);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public void delete(@PathVariable Long id) throws WrongInputException {
        productService.delete(id);
    }
}
