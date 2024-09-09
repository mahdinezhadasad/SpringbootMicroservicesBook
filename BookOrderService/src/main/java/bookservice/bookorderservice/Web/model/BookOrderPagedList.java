package bookservice.bookorderservice.Web.model;
import java.util.List;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public class BookOrderPagedList extends PageImpl<BookOrderDto> {
    
    public BookOrderPagedList(List<BookOrderDto> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }
    
    public BookOrderPagedList(List<BookOrderDto> content) {
        super(content);
    }
}