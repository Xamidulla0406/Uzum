package uz.nt.uzumproject.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Image {
    @Id
    @GeneratedValue(generator = "imageIdSeq")
    @SequenceGenerator(name = "imageIdSeq",sequenceName = "image_id_seq",allocationSize = 1)
    private Integer id;
    private String name;
    private String extension;
    private String url;
    @ManyToOne
    private Product product;
    private LocalDateTime createdAt;
}
