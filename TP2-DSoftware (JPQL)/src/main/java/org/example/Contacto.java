package org.example;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity @Table(name="contacto")
public class Contacto extends EntityId{
    private String email;
    private String telefono;
    private String celular;

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }
}
