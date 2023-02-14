package uz.nt.uzumproject.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    @Id
    @GeneratedValue(generator = "categoryIdSeq")
    @SequenceGenerator(name="categoryIdSeq",sequenceName = "category_id_seq",allocationSize = 1)
    private Integer id;
    private String name;
    private Integer parentId;
    @OneToMany(mappedBy = "category")
    private List<Product> products;
}