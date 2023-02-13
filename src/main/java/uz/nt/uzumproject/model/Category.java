package uz.nt.uzumproject.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private Integer ParentId;
}
