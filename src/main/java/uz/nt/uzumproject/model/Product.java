package uz.nt.uzumproject.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(generator = "productIdSeq")
    @SequenceGenerator(name = "productIdSeq", sequenceName = "product_id_seq", allocationSize = 1)
    private Integer id;
    private String name;
    private Integer price;
    private Integer amount;
    private String description;
    @OneToMany(mappedBy = "product")
    private List<Image> images;
    private Boolean isAvailable;
    @ManyToOne
    private Category category;

//    mvn clean package -Dmaven .test.skip
}
//1. Product qo'shish
//2. Bor productni o'zgartirish
//3. Umumiy productlar ro'yxatini qaytarish
