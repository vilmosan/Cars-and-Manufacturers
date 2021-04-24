package hu.pte.ttk.vaadin.vaadindemo.brand.entity;

import hu.pte.ttk.vaadin.vaadindemo.core.entity.CoreEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@EqualsAndHashCode(callSuper = true)
@Table(name = "brand")
@Data
@NoArgsConstructor
@Entity
public class BrandEntity extends CoreEntity {

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "brand_id")
//    private Long brandId;

	@Column(name = "brand_name")
	private String brandName;

}
