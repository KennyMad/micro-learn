package org.pet.storage.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class ItemCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String name;
}
