
package com.EatAway.serviceImpl;

import com.EatAway.domain.Evento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.EatAway.dao.EventoDao;
import com.EatAway.service.EventoService;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import oracle.jdbc.OracleTypes;

@Service
public class EventoServiceImpl implements EventoService{
    
    private final EventoDao eventoDao;

    @Autowired
    public EventoServiceImpl(EventoDao eventoDao) {
        this.eventoDao = eventoDao;
    }

    @Override
    public List<Evento> getEventos() {
        String jdbcUrl = "jdbc:oracle:thin:@localhost:1521:orcl";
        String user = "C##eataway";
        String password = "sws2024";
        List<Evento> lista = new ArrayList<>();

        try (Connection conexion = DriverManager.getConnection(jdbcUrl, user, password)) {
            System.out.println("Conectado a la base de datos");

            String callStatement = "{call C##eataway.PCK_EATAWAY_EVENTO_OBTENER.ObtenerEventosEspecialesSP(?)}";
            System.out.println("Obtención de eventos");
            try (CallableStatement llamadaExecute = conexion.prepareCall(callStatement)) {
                llamadaExecute.registerOutParameter(1, OracleTypes.CURSOR);

                llamadaExecute.execute();
                ResultSet resultSet = (ResultSet) llamadaExecute.getObject(1);

                while (resultSet.next()) {
                    Evento evento = new Evento();
                    evento.setIdEvento(resultSet.getLong("id_evento"));
                    evento.setNombreEvento(resultSet.getString("nombre_evento"));
                    evento.setDescripcion(resultSet.getString("descripcion"));
                    evento.setFechaEvento(resultSet.getDate("fecha_evento").toLocalDate());
                    evento.setHoraEvento(resultSet.getTimestamp("hora_evento").toLocalDateTime());
                    evento.setNombreLocal(resultSet.getString("nombre_local"));

                    lista.add(evento);
                }

                resultSet.close();
            }
        } catch (SQLException e) {
            System.out.println("Error");
            e.printStackTrace();
        }

        return lista;
    }
}
