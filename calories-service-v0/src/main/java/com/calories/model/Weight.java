package com.calories.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.*;

@Entity
@Table(name = "weight", schema = "public")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "idweight")
public  class Weight  implements Serializable {

	private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue ( strategy = GenerationType.AUTO)
    private Long id;


    @Column(name = "date", nullable = false)
    private Date date;

    @Column( nullable = false)
    private Double poids;
    
    @ManyToOne( cascade = CascadeType.ALL)
    @JoinColumn(name = "fitness_id", nullable = false , referencedColumnName = "id")
    @JsonIgnoreProperties("weights")
    private FitnessUser fitness;

    
}
