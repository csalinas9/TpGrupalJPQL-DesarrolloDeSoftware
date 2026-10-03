package org.example;

import jakarta.persistence.*;

@Entity @Table(name = "cliente")
public class Cliente extends AuditoriaApp{
    @Column(nullable = false)
    private String cuitCuil;
    @Column(nullable = false)
    private String denominacion;
    @OneToOne @JoinColumn(nullable = false)
    private Contacto contacto;
    @OneToOne @JoinColumn(nullable = false)
    private Domicilio domicilio;

    public void setCuitCuil(String cuitCuil) {
        this.cuitCuil = cuitCuil;
    }

    public void setDenominacion(String denominacion) {
        this.denominacion = denominacion;
    }

    public void setContacto(Contacto contacto) {
        this.contacto = contacto;
    }

    public void setDomicilio(Domicilio domicilio) {
        this.domicilio = domicilio;
    }

    public String getCuitCuil() {
        return cuitCuil;
    }

    public String getDenominacion() {
        return denominacion;
    }
}
