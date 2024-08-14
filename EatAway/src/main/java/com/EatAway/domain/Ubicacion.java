package com.EatAway.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import java.io.Serializable;
import lombok.Data;

@Data
@Entity
@Table(name = "Ubicacion")
public class Ubicacion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ubicacion")
    private Long idUbicacion;

    @JoinColumn(name = "id_local")
    private Long idLocal;  

    @Column(name = "provincia")
    private String provincia;

    @Column(name = "direccion")
    private String direccion;

    public Ubicacion() {
    }

    public Ubicacion(Long idUbicacion, Long idLocal, String provincia, String direccion) {
        this.idUbicacion = idUbicacion;
        this.idLocal = idLocal;
        this.provincia = provincia;
        this.direccion = direccion;
    }
}
