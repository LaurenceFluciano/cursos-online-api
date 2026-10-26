package api.mapper;

import java.util.function.Function;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import api.dto.PageResponse;

@Component 
public class PageMapper {

    public <T, R> PageResponse<R> toResponse(Page<T> page, Function<T, R> mapperFunction) {
        
        Page<R> transformedPage = page.map(mapperFunction);

        return new PageResponse<R>(
            transformedPage.getContent(),
            transformedPage.getTotalElements(),
            transformedPage.getTotalPages(),
            transformedPage.getSize(),
            transformedPage.getNumber() + 1
        );
    }
}
