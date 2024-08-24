
package com.EatAway.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;
import lombok.Data;

@Data
@Entity
@Table(name = "Promociones")
public class Promociones implements Serializable{
    
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_promocion")
    private Long idPromocion;
        
    @JoinColumn(name = "id_local")
    private Integer idlocal;

    @Column(name = "nombre_promocion")
    private String nombrePromocion;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "fecha_inicio")
    private Date fechaInio;

    @Column(name = "fecha_fin")
    private Date fechaFin;
    
    @Column(name = "estado")
    private String estado;
    
    @Column(name = "nombre_local")
    private String nombreLocal;
    
    @Column(name = "foto_local")
    private String fotoLocal;

    public Promociones() {
    }

    public Promociones(Long idPromocion, Integer idlocal, String nombrePromocion, String descripcion, Date fechaInio, Date fechaFin, String estado, String nombreLocal, String fotoLocal) {
        this.idPromocion = idPromocion;
        this.idlocal = idlocal;
        this.nombrePromocion = nombrePromocion;
        this.descripcion = descripcion;
        this.fechaInio = fechaInio;
        this.fechaFin = fechaFin;
        this.estado = estado;
        this.nombreLocal = nombreLocal;
        this.fotoLocal = fotoLocal;
    }

           
}
