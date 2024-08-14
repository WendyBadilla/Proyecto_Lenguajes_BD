
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
@Table(name = "Fotos")
public class Fotos implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_foto")
    private Long idFoto;

    @JoinColumn(name = "id_local")
    private Long idLocal;  

    @Column(name = "ruta_foto")
    private String rutaFoto;

    public Fotos() {
    }

    public Fotos(Long idFoto, Long idLocal, String rutaFoto) {
        this.idFoto = idFoto;
        this.idLocal = idLocal;
        this.rutaFoto = rutaFoto;
    }
    
    
}

