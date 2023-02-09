package uz.nt.uzumproject.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Image {
    @Id
    @GeneratedValue(generator = "imageIdSeq")
    @SequenceGenerator(name = "imageIdSeq", sequenceName = "image_id_seq", allocationSize = 1)
    private Integer id;
    private String name;
    private String url;
    private String extension;
    @ManyToOne
    private Product product;
    private LocalDateTime createdAt;
}
