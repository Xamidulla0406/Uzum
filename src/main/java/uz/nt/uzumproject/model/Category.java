package uz.nt.uzumproject.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter

public class Category {

    @Id
    @GeneratedValue(generator = "categorySeq1")
    @SequenceGenerator(name = "categorySeq1",sequenceName = "categorySequence1", initialValue = 1,allocationSize = 1)
    private Integer id;
    private String name;
    private Integer parentId;

}