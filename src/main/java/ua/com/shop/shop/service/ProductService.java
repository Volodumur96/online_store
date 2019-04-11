package ua.com.shop.shop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ua.com.shop.shop.dto.request.ProductFilterRequest;
import ua.com.shop.shop.dto.request.ProductRequest;
import ua.com.shop.shop.dto.response.DataResponse;
import ua.com.shop.shop.dto.response.MakerResponse;
import ua.com.shop.shop.dto.response.ProductResponse;
import ua.com.shop.shop.entity.Product;
import ua.com.shop.shop.exeption.WrongInputException;
import ua.com.shop.shop.repository.ProductRepository;
import ua.com.shop.shop.specification.ProductSpecification;
//import ua.com.shop.shop.specification.ProductSpecification;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private MakerService makerService;

    @Autowired
    private CategoryService categoryService;

    public ProductResponse save(ProductRequest productRequest) throws WrongInputException {
        return new ProductResponse(productRequestToProduct(null, productRequest));
    }

    public ProductResponse update(Long id, ProductRequest productRequest) throws WrongInputException {
        return new ProductResponse(productRequestToProduct(findOne(id), productRequest));
    }

//    public List<ProductResponse> findAll() {
//        return productRepository.findAll().stream().map(ProductResponse::new).collect(Collectors.toList());
//    }

    public void delete(Long id) throws WrongInputException {
        productRepository.delete(findOne(id));
    }

    @Transactional
    public Product findOne(Long id) throws WrongInputException {
        return productRepository.findById(id).orElseThrow(() -> new WrongInputException("Product with id " + id + " not exists"));
    }

    public ProductResponse findOneById(Long id) throws WrongInputException {
        return new ProductResponse(findOne(id));
    }


    public DataResponse<ProductResponse> findAll(Integer page, Integer size, String name, Sort.Direction direction){
        Sort sort = Sort.by(direction, name);
        PageRequest pageRequest = PageRequest.of(page,size,sort);
        Page<Product> pageProduct = productRepository.findAll(pageRequest);
        return new DataResponse<ProductResponse>(pageProduct.stream().map(ProductResponse::new).collect(Collectors.toList()), pageProduct);
    }

    public List<ProductResponse> findAllSelector() {
        return productRepository.findAll().stream().map(ProductResponse::new).collect(Collectors.toList());
    }


    public DataResponse<ProductResponse> findByFilter(ProductFilterRequest filterRequest) {
        Page<Product> page = productRepository.findAll(
                new ProductSpecification(filterRequest),
                filterRequest.getPaginationRequest().mapToPageRequest());

        return new DataResponse<>(page.get().map(ProductResponse::new).collect(Collectors.toList()), page.getTotalPages(), page.getTotalElements());

    }



    private Product productRequestToProduct(Product product, ProductRequest request) throws WrongInputException {
        if (product == null) {
            product = new Product();
        }

        product.setMaker(makerService.findOne(request.getMakerId()));
        product.setCategory(categoryService.findOne(request.getCategoryId()));
        product.setName(request.getName());
        product.setModel(request.getModel());
        product.setYear(request.getYear());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setAmount(request.getAmount());
        product.setImagePath(request.getImagePath());
        return productRepository.save(product);
    }
}
