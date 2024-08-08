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
@Table(name = "Locales")
public class Local implements Serializable {

    private static final long serialVersionUID = 1L;
    /*asigna automaticamente el numero de id de los locales*/

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_local")
    private Long idLocal;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "id_categoria")
    private Long idCategoria;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "tipo")
    private String tipoCategoria;

    @Column(name = "foto")
    private String foto;
    
    @Column(name = "telefono")
    private String telefono;
    
    @Column(name = "email")
    private String email;
    
    @Column(name = "instagram")
    private String instagram;

    public Local() {
    }

    public Local(Long idLocal, String nombre, Long idCategoria, String descripcion, String tipoCategoria, String foto, String telefono, String email, String instagram) {
        this.idLocal = idLocal;
        this.nombre = nombre;
        this.idCategoria = idCategoria;
        this.descripcion = descripcion;
        this.tipoCategoria = tipoCategoria;
        this.foto = foto;
        this.telefono = telefono;
        this.email = email;
        this.instagram = instagram;
    }

    
    
    
}

