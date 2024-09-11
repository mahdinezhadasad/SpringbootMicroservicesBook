package bookservice.bookservice.Web.model;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class BookOrderPagedList extends PageImpl<BookOrderDto> {
    public BookOrderPagedList(List<BookOrderDto> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }
    
    public BookOrderPagedList(List<BookOrderDto> content) {
        super(content);
    }
}