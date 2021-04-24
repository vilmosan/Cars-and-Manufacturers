package hu.pte.ttk.vaadin.vaadindemo.brand.entity;

import hu.pte.ttk.vaadin.vaadindemo.core.entity.CoreEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Table(name = "brand")
@Data
@NoArgsConstructor
@Entity
public class BrandEntity extends CoreEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "brand_id")
    private Long id;

    @Column(name = "brand_name")
    private String brandName;

}
