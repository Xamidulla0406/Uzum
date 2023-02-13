package uz.nt.uzumproject.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "category")
public class Category {

    @Id
    @GeneratedValue(generator = "category_seq")
    @SequenceGenerator(name = "category_seq", sequenceName = "category_id_seq", allocationSize = 1)
    private Integer id;
    private String name;
    private Integer parentId;
}
