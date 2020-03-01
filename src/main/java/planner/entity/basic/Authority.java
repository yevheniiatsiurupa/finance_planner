package planner.entity.basic;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import planner.entity.basic.supplementary.AuthorityType;

import javax.persistence.*;

/**
 * Class represents one authority object with specific authority type.
 */
@Entity
@Table(name = "authorities")
@Getter
@Setter
@NoArgsConstructor
public class Authority {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(name = "name")
    private AuthorityType name;

    public Authority(AuthorityType name) {
        this.name = name;
    }
}
