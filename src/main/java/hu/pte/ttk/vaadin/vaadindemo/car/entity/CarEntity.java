package hu.pte.ttk.vaadin.vaadindemo.car.entity;

import hu.pte.ttk.vaadin.vaadindemo.brand.entity.BrandEntity;
import hu.pte.ttk.vaadin.vaadindemo.core.entity.CoreEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Table(name = "car")
@Data
@NoArgsConstructor
@Entity
public class CarEntity extends CoreEntity {

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "car_id")
//    private Long carId;

    @Column(name = "car_name")
    private String carName;

    @ManyToOne
    @JoinColumn(name = "brand_id")
    private BrandEntity brand;

    @Column(name = "car_type")
    private String carType;

    @Column(name = "car_doors")
    private Double carDoors;

    @Column(name = "car_manufactured")
    private Double carManufactured;

}
