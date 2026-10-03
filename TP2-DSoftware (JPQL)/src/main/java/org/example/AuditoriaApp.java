package org.example;

import jakarta.persistence.*;

import java.util.Date;

@MappedSuperclass
public abstract class AuditoriaApp extends EntityId {
    @Column(nullable = false) @Temporal(TemporalType.TIMESTAMP)
    protected Date fechaAlta;
    @Temporal(TemporalType.TIMESTAMP)
    protected Date fechaBaja;
    @Column(nullable = false) @Temporal(TemporalType.TIMESTAMP)
    protected Date fechaModificacion;
    @ManyToOne @JoinColumn(nullable = false)
    protected Usuario usuarioCarga;
    @ManyToOne
    protected Usuario usuarioBaja;
    @ManyToOne @JoinColumn(nullable = false)
    protected Usuario usuarioModificacion;

    public void setFechaAlta(Date fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public void setFechaBaja(Date fechaBaja) {
        this.fechaBaja = fechaBaja;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public void setUsuarioCarga(Usuario usuarioCarga) {
        this.usuarioCarga = usuarioCarga;
    }

    public void setUsuarioBaja(Usuario usuarioBaja) {
        this.usuarioBaja = usuarioBaja;
    }

    public void setUsuarioModificacion(Usuario usuarioModificacion) {
        this.usuarioModificacion = usuarioModificacion;
    }
}
