package com.EatAway.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.io.Serializable;
import lombok.Data;

@Data
@Entity
@Table(name = "Resenas")
public class Resena implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_resena")
    private Long idResena;

    @Column(name = "id_usuario")
    private Integer idUsuario;
    
    @Column(name = "id_local")
    private Integer idLocal;
    
    @Column(name = "calificacion")
    private Integer calificacion;

    @Column(name = "comentario")
    private String comentario;
    
    @Column(name = "nombre_completo")
    private String nombreCompleto;
    
    @Column(name = "nombre_usuario")
    private String nombreUsuario;
    
    @Column(name = "foto_usuario")
    private String fotoUsuario;

    @Column(name = "nombre_local")
    private String nombreLocal;

    public Resena() {
    }

    public Resena(Long idResena, Integer idUsuario, Integer idLocal, Integer calificacion, String comentario, String nombreCompleto, String nombreUsuario, String fotoUsuario, String nombreLocal) {
        this.idResena = idResena;
        this.idUsuario = idUsuario;
        this.idLocal = idLocal;
        this.calificacion = calificacion;
        this.comentario = comentario;
        this.nombreCompleto = nombreCompleto;
        this.nombreUsuario = nombreUsuario;
        this.fotoUsuario = fotoUsuario;
        this.nombreLocal = nombreLocal;
    }
    

    
    
}
