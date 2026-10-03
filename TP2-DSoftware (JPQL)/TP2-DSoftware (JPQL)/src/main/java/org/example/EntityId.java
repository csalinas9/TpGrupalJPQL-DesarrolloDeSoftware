package org.example;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class EntityId {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
}
