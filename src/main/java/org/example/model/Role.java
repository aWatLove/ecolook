package org.example.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@Entity
@NoArgsConstructor
@Table(name = "roles")
public class Role {
    @Id
    private Long id;

    @Enumerated(EnumType.STRING)
    private ERole name;

    public Role(ERole role) {
        this.name = role;
    }
}
