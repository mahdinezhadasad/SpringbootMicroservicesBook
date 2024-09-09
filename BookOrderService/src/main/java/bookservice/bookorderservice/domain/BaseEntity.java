package bookservice.bookorderservice.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.GeneratedValue;
import org.hibernate.annotations.GenericGenerator;
import jakarta.persistence.Id;
import org.hibernate.annotations.Type;
import jakarta.persistence.Column;

import javax.persistence.MappedSuperclass;
import java.sql.Timestamp;
import java.util.UUID;
import org.hibernate.annotations.CreationTimestamp;
import jakarta.persistence.Version;
import org.hibernate.annotations.UpdateTimestamp;


@Setter
@Getter
@NoArgsConstructor
@MappedSuperclass
public class BaseEntity {
    
    public BaseEntity(UUID id, Long version, Timestamp createdDate, Timestamp lastModifiedDate) {
        this.id = id;
        this.version = version;
        this.createdDate = createdDate;
        this.lastModifiedDate = lastModifiedDate;
    }
    
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Type(type="org.hibernate.type.UUIDCharType")
    @Column(length = 36, columnDefinition = "varchar", updatable = false, nullable = false)
    private UUID id;
    
    @Version
    private Long version;
    
    @CreationTimestamp
    @Column(updatable = false)
    private Timestamp createdDate;
    
    @UpdateTimestamp
    private Timestamp lastModifiedDate;
    
    public boolean isNew() {
        return this.id == null;
    }
}