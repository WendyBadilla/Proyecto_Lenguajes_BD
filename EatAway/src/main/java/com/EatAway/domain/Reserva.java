package com.EatAway.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.sql.Date;
import lombok.Data;

@Data
@Entity
@Table(name = "Reservas")
public class Reserva implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_reserva")
    private Long idReserva;

    @Column(name = "id_usuario")
    private Integer idUsuario;

    @Column(name = "id_local")
    private Integer idLocal;

    @Column(name = "fecha")
    private Date fecha;

    @Column(name = "hora")
    private String hora;

    @Column(name = "numero_personas")
    private Integer numeroPersonas;

    @Column(name = "descripcion")
    private String descripcion;
    
    @Column(name = "nombre_local")
    private String nombreLocal;

    public Reserva() {
    }

    public Reserva(Long idReserva, Integer idUsuario, Integer idLocal, Date fecha, String hora, Integer numeroPersonas, String descripcion, String nombreLocal) {
        this.idReserva = idReserva;
        this.idUsuario = idUsuario;
        this.idLocal = idLocal;
        this.fecha = fecha;
        this.hora = hora;
        this.numeroPersonas = numeroPersonas;
        this.descripcion = descripcion;
        this.nombreLocal = nombreLocal;
    }

    

    
}