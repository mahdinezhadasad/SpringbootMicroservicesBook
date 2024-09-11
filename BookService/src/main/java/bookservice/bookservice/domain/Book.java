package bookservice.bookservice.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Version;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.UUID;
@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false, length = 36)
    private UUID id;
    
    @Version
    private Long version;
    
    @CreationTimestamp
    @Column(updatable = false)
    private Timestamp createdDate;
    
    @UpdateTimestamp
    private Timestamp lastModifiedDate;
    
    private String title;
    private String author;
    private String isbn;
    
    @Column(unique = true)
    private String upc; // Universal Product Code (if applicable)
    
    private BigDecimal price;
    private Integer stockAvailable;
}