package bookservice.bookservice.mappers;

import bookservice.bookservice.domain.BookDto;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;
import java.util.List;

/**
 * Created by [your name] on [current date].
 */
public class BookPagedList extends PageImpl<BookDto> implements Serializable {
    
    static final long serialVersionUID = -606358419839558194L;
    
    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public BookPagedList(@JsonProperty("content") List<BookDto> content,
                         @JsonProperty("number") int number,
                         @JsonProperty("size") int size,
                         @JsonProperty("totalElements") Long totalElements,
                         @JsonProperty("pageable") JsonNode pageable,
                         @JsonProperty("last") boolean last,
                         @JsonProperty("totalPages") int totalPages,
                         @JsonProperty("sort") JsonNode sort,
                         @JsonProperty("first") boolean first,
                         @JsonProperty("numberOfElements") int numberOfElements) {
        
        super(content, PageRequest.of(number, size), totalElements);
    }
    
    public BookPagedList(List<BookDto> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }
    
    public BookPagedList(List<BookDto> content) {
        super(content);
    }
}