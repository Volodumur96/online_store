package ua.com.shop.shop.dto.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

@Getter @Setter
public class PaginationRequest {
    private Integer size;

    private Integer page;

    private SortRequest sortRequest;

    public PageRequest mapToPageRequest() {
        PageRequest pageRequest = null;
        if (sortRequest == null) {
            return PageRequest.of(page, size);
        } else {
            return PageRequest.of(page, size, Sort.by(sortRequest.getDirection(), sortRequest.getFieldName()));
        }
    }
}
