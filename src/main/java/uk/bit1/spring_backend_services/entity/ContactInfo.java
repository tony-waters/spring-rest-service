package uk.bit1.spring_backend_services.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class ContactInfo {

    @Id
    @GeneratedValue( strategy = GenerationType.AUTO)
    private Long id;

    private String email;
    private String phone;
}
