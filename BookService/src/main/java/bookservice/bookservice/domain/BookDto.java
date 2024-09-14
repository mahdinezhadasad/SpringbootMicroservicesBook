package bookservice.bookservice.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * Created by [your name] on [current date].
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookDto  implements Serializable {
    
    static final long serialVersionUID = 1044078483111912018L;
    
    @Null
    private UUID id;
    
    @Null
    private Integer version;
    
    @Null
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ssZ", shape=JsonFormat.Shape.STRING)
    private OffsetDateTime createdDate;
    
    @Null
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ssZ", shape=JsonFormat.Shape.STRING)
    private OffsetDateTime lastModifiedDate;
    
    @NotBlank
    private String title;
    
    @NotBlank
    private String author;
    
    @NotBlank
    private String isbn;
    
    @NotNull
    private String upc;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Positive
    @NotNull
    private BigDecimal price;
    
    private Integer stockAvailable;
}