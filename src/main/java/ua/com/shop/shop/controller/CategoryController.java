package ua.com.shop.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ua.com.shop.shop.dto.request.CategoryRequest;
import ua.com.shop.shop.dto.response.CategoryResponse;
import ua.com.shop.shop.dto.response.DataResponse;
import ua.com.shop.shop.entity.Category;
import ua.com.shop.shop.exeption.WrongInputException;
import ua.com.shop.shop.service.CategoryService;

import javax.validation.Valid;
import java.util.List;


@CrossOrigin
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/public")
    public DataResponse<CategoryResponse> getCategories(@RequestParam Integer page, @RequestParam Integer size){
        return categoryService.findAll(page,size);
    }

    @GetMapping("/public/{id}")
    public CategoryResponse findOne(@PathVariable Long id) throws WrongInputException {
        return categoryService.findOneById(id);
    }

    @GetMapping("public/selector")
    public List<CategoryResponse> findAll() {
        return categoryService.findAllSelector();
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public CategoryResponse save(@RequestBody @Valid  CategoryRequest categoryRequest) throws WrongInputException {
        return categoryService.save(categoryRequest);
    }

    @PutMapping
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public CategoryResponse update(@RequestBody CategoryRequest categoryRequest, @RequestParam Long id) throws WrongInputException {
        return categoryService.update(categoryRequest, id);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public void delete(@PathVariable Long id) throws WrongInputException {
        categoryService.delete(id);
    }
}
