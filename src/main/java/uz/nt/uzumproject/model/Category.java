package uz.nt.uzumproject.model;

import jakarta.persistence.*;

@Entity
public class Category {
    @GeneratedValue(generator = "category_id")
    @SequenceGenerator(name = "category_id",sequenceName = "category_id_seq",allocationSize = 1)
    @Id
    Integer id;
    @Column(nullable = false)
    String name;
    Integer parentId;
}
