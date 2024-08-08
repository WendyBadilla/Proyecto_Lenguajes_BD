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
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Data;

@Data
@Entity
@Table(name = "EventosEspeciales")
public class Evento implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_evento")
    private Long idEvento;

    @ManyToOne
    @JoinColumn(name = "id_local")
    private Local local;

    @Column(name = "nombre_evento")
    private String nombreEvento;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "fecha_evento")
    private LocalDate fechaEvento;

    @Column(name = "hora_evento")
    private LocalDateTime horaEvento;
    
    @Column(name = "nombre_local")
    private String nombreLocal;

    public Evento() {
    }

    public Evento(Long idEvento, Local local, String nombreEvento, String descripcion, LocalDate fechaEvento, LocalDateTime horaEvento, String nombreLocal) {
        this.idEvento = idEvento;
        this.local = local;
        this.nombreEvento = nombreEvento;
        this.descripcion = descripcion;
        this.fechaEvento = fechaEvento;
        this.horaEvento = horaEvento;
        this.nombreLocal = nombreLocal;
    }

    
}
