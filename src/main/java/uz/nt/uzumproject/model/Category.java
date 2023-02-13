package uz.nt.uzumproject.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Category {
    @GeneratedValue(generator = "category_id")
    @SequenceGenerator(name = "category_id",sequenceName = "category_id_seq",allocationSize = 1)
    @Id
    Integer id;

    @Column(nullable = false)
    String name;
    Integer parentId;
}
