package ua.com.shop.shop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.shop.shop.dto.request.CategoryRequest;
import ua.com.shop.shop.dto.response.CategoryResponse;
import ua.com.shop.shop.dto.response.DataResponse;
import ua.com.shop.shop.entity.Category;
import ua.com.shop.shop.exeption.WrongInputException;
import ua.com.shop.shop.repository.CategoryRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;


    public CategoryResponse save(CategoryRequest request) throws WrongInputException {
        return new CategoryResponse(categoryRequestToCategory(request, null));
    }

    public CategoryResponse update(CategoryRequest request, Long id) throws WrongInputException {
        return new CategoryResponse(categoryRequestToCategory(request, findOne(id)));
    }


    public void delete(Long id) throws WrongInputException {
        categoryRepository.delete(findOne(id));
    }

    @Transactional
    public Category findOne(Long id) throws WrongInputException {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new WrongInputException("Category with id " + id + " not exists"));
    }

    public CategoryResponse findOneById(Long id) throws WrongInputException {
        return new CategoryResponse(findOne(id));
    }

    public DataResponse<CategoryResponse> findAll(Integer page, Integer size){
        PageRequest pageRequest = PageRequest.of(page,size);
        Page<Category> pageCategory = categoryRepository.findAll(pageRequest);
        return new DataResponse<CategoryResponse>(pageCategory.stream().map(CategoryResponse::new).collect(Collectors.toList()), pageCategory);
    }

    public List<CategoryResponse> findAllSelector() {
        return categoryRepository.findAll().stream()
                .map(CategoryResponse::new)
                .collect(Collectors.toList());
    }

    private Category categoryRequestToCategory(CategoryRequest request, Category category) throws WrongInputException {
        if (category == null) {
            category = new Category();
        }
        category.setName(request.getName());
        return categoryRepository.save(category);
    }
}