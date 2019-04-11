package ua.com.shop.shop.specification;

import org.springframework.data.jpa.domain.Specification;
import ua.com.shop.shop.dto.request.ProductFilterRequest;
import ua.com.shop.shop.entity.Category;
import ua.com.shop.shop.entity.Maker;
import ua.com.shop.shop.entity.Product;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

public class ProductSpecification implements Specification<Product> {

    private ProductFilterRequest filter;

    public ProductSpecification(ProductFilterRequest filter) {
        this.filter = filter;
    }


    @Override
    public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();

        Predicate byPrice = findByPrice(root, criteriaBuilder);
        if (byPrice != null) predicates.add(byPrice);

        Predicate byYear = findByYear(root, criteriaBuilder);
        if (byYear != null) predicates.add(byYear);

        Predicate byNameLike = findByNameLike(root, criteriaBuilder);
        if (byNameLike != null) predicates.add(byNameLike);

        Predicate byModelLike = findByModelLike(root, criteriaBuilder);
        if (byModelLike != null) predicates.add(byModelLike);

        Predicate byCategory = findByCategory(root, criteriaBuilder);
        if (byCategory != null) predicates.add(byCategory);

        Predicate byMaker = findByMaker(root, criteriaBuilder);
        if (byMaker != null) predicates.add(byMaker);

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

    private Predicate findByCategory(Root<Product> root, CriteriaBuilder criteriaBuilder) {
        Join<Product, Category> categoryJoin = root.join("category");
        if (filter.getCategoriesId() == null) {
            return null;
        } else if (filter.getCategoriesId().isEmpty()) {
            return null;
        } else return categoryJoin.get("id").in(filter.getCategoriesId().toArray());
    }

    private Predicate findByMaker(Root<Product> root, CriteriaBuilder criteriaBuilder) {
        Join<Product, Maker> makerJoin = root.join("maker");
        if (filter.getMakersId() == null) {
            return null;
        } else if (filter.getMakersId().isEmpty()) {
            return null;
        } else return makerJoin.get("id").in(filter.getMakersId().toArray());
    }

    private Predicate findByNameLike(Root<Product> root, CriteriaBuilder criteriaBuilder) {
        String name = filter.getName();
        if (name == null || name.trim().isEmpty()) {
            return null;
        }
        return criteriaBuilder.like(root.get("name"), '%' + name + '%');
    }

    private Predicate findByModelLike(Root<Product> root, CriteriaBuilder criteriaBuilder) {
        String model = filter.getModel();
        if (model == null || model.trim().isEmpty()) {
            return null;
        }
        return criteriaBuilder.like(root.get("model"), '%' + model + '%');
    }

    private Predicate findByPrice(Root<Product> root, CriteriaBuilder criteriaBuilder) {
        if (filter.getPriceFrom() == null && filter.getPriceTo() == null) {
            return null;
        }
        if (filter.getPriceFrom() == null) {
            filter.setPriceFrom(0);
        }
        if (filter.getPriceTo() == null) {
            filter.setPriceTo(Integer.MAX_VALUE);
        }
        return criteriaBuilder.between(root.get("price"), filter.getPriceFrom(), filter.getPriceTo());
    }

    private Predicate findByYear(Root<Product> root, CriteriaBuilder criteriaBuilder) {
        if (filter.getYearFrom() == null && filter.getYearTo() == null) {
            return null;
        }
        if (filter.getYearFrom() == null) {
            filter.setYearFrom(0);
        }
        if (filter.getYearTo() == null) {
            filter.setYearTo(Integer.MAX_VALUE);
        }
        return criteriaBuilder.between(root.get("year"), filter.getYearFrom(), filter.getYearTo());
    }

}
