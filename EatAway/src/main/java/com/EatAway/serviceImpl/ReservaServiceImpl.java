package com.EatAway.serviceImpl;

import com.EatAway.service.ReservaService;
import org.springframework.stereotype.Service;
import com.EatAway.domain.Reserva;
import jakarta.servlet.http.HttpSession;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import oracle.jdbc.OracleTypes;

@Service
public class ReservaServiceImpl implements ReservaService {

    @Override
    public void agregarReserva(Reserva reserva){

        String jdbcUrl = "jdbc:oracle:thin:@localhost:1521:orcl";
        String user = "C##eataway";
        String password = "sws2024";

        try (Connection connection = DriverManager.getConnection(jdbcUrl, user, password)) {
            System.out.println("Connected to the Oracle database");

            String callStatement = "{ call C##eataway.PCK_EATAWAY_RESERVA_INSERTAR.InsertarReservaSP(?, ?, ?, ?, ?, ?) }";
            System.out.println("Agregar mediante paquete");
            try (CallableStatement callableStatement = connection.prepareCall(callStatement)) {

                callableStatement.setInt(1, reserva.getIdUsuario());
                callableStatement.setInt(2, reserva.getIdLocal());
                callableStatement.setDate(3, reserva.getFecha());
                callableStatement.setString(4, reserva.getHora());
                callableStatement.setInt(5, reserva.getNumeroPersonas());
                callableStatement.setString(6, reserva.getDescripcion());

                callableStatement.execute();
                System.out.println("Stored procedure de creacion ejecutado exitosamente.");
            }
        } catch (SQLException e) {
            if (e.getErrorCode() == 20001) { 
                System.out.println("Error de reserva: La fecha no puede ser pasada.");
            } else {
                System.out.println("Error detectado");
                e.printStackTrace();
            }
        }

    }

    public List<Reserva> obtenerReservasPorUsuario(HttpSession session) {
        String jdbcUrl = "jdbc:oracle:thin:@localhost:1521:orcl";
        String user = "C##eataway";
        String password = "sws2024";
        List<Reserva> reservas = new ArrayList<>();

        // Obtener el ID del usuario desde la sesión
        Long userId = (Long) session.getAttribute("id_usuario");

        if (userId == null) {
            throw new IllegalArgumentException("No se encontró el ID del usuario en la sesión.");
        }

        try (Connection connection = DriverManager.getConnection(jdbcUrl, user, password)) {
            System.out.println("Connected to the Oracle database");

            String callStatement = "{ call C##eataway.PCK_EATAWAY_RESERVA_OBTENER.ObtenerReservasPorUsuarioSP(?, ?) }";
            System.out.println("Consultar mediante paquete");

            try (CallableStatement callableStatement = connection.prepareCall(callStatement)) {
                callableStatement.setLong(1, userId);
                callableStatement.registerOutParameter(2, OracleTypes.CURSOR);

                callableStatement.execute();
                
                try (ResultSet resultSet = (ResultSet) callableStatement.getObject(2)) {
                    while (resultSet.next()) {
                        Reserva reserva = new Reserva();
                        reserva.setIdReserva(resultSet.getLong("id_reserva"));
                        reserva.setIdUsuario(resultSet.getInt("id_usuario"));
                        reserva.setIdLocal(resultSet.getInt("id_local"));
                        reserva.setFecha(resultSet.getDate("fecha"));
                        reserva.setHora(resultSet.getString("hora"));
                        reserva.setNumeroPersonas(resultSet.getInt("numero_personas"));
                        reserva.setDescripcion(resultSet.getString("descripcion"));
                        reserva.setNombreLocal(resultSet.getString("nombre_local"));
                        reservas.add(reserva);
                    }
                }
                
                System.out.println("Stored procedure de consulta ejecutado exitosamente.");
            }
        } catch (SQLException e) {
            System.out.println("Error detected");
            e.printStackTrace();
        }
        
        return reservas;
    }

    @Override
    public void eliminarReserva(Reserva reserva) {
        
        Long id = reserva.getIdReserva();
        
        String jdbcUrl = "jdbc:oracle:thin:@localhost:1521:orcl";
        String callStatement = "{ call C##eataway.PCK_EATAWAY_RESERVA_ELIMINAR.EliminarReservaSP(?) }";
        System.out.println("Eliminacion preparada con paquete");

        try (Connection conexion = DriverManager.getConnection(jdbcUrl, "C##eataway", "sws2024")) {
            CallableStatement callableStatement = conexion.prepareCall(callStatement);
            int pid = reserva.getIdReserva().intValue();
            callableStatement.setInt(1, pid);
            callableStatement.execute();

            System.out.println("Stored procedure executed successfully.");
        } catch (SQLException e) {
            System.out.println("Error detected");
            e.printStackTrace();
        }
    }
    
    @Override
    public Reserva getReservaID(long idReserva) {
        String jdbcUrl = "jdbc:oracle:thin:@localhost:1521:orcl";
        String user = "C##eataway";
        String password = "sws2024";

        Reserva reserva = new Reserva(); // Crear un objeto Reserva para devolver

        try (Connection conexion = DriverManager.getConnection(jdbcUrl, user, password)) {
            String callStatement = "{call ObtenerReservaPorId(?, ?)}";

            try (CallableStatement llamadaExecute = conexion.prepareCall(callStatement)) {
                // Establecimiento del parámetro de entrada
                llamadaExecute.setLong(1, idReserva);
                // Registro del parámetro de salida
                llamadaExecute.registerOutParameter(2, OracleTypes.CURSOR);

                llamadaExecute.execute();
                try (ResultSet resultSet = (ResultSet) llamadaExecute.getObject(2)) {
                    if (resultSet.next()) {
                        reserva.setIdReserva(resultSet.getLong("id_reserva"));
                        reserva.setIdUsuario(resultSet.getInt("id_usuario"));
                        reserva.setIdLocal(resultSet.getInt("id_local"));
                        reserva.setFecha(resultSet.getDate("fecha"));
                        reserva.setHora(resultSet.getString("hora"));
                        reserva.setNumeroPersonas(resultSet.getInt("numero_personas"));
                        reserva.setDescripcion(resultSet.getString("descripcion"));
                        reserva.setNombreLocal(resultSet.getString("nombre_local"));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reserva;
    }
    
    @Override
    public void editarReserva(Reserva reserva) {
        
        
        String jdbcUrl = "jdbc:oracle:thin:@localhost:1521:orcl";

  
        try (Connection conexion = DriverManager.getConnection(jdbcUrl, "C##eataway", "sws2024")) {
            
            String callStatement = "{ call ActualizarReservaSP(?, ?, ?, ?, ?) }";
            
            System.out.println("Edicion mediante SP");

            try (CallableStatement callableStatement = conexion.prepareCall(callStatement)) {
                callableStatement.setInt(1, reserva.getIdReserva().intValue());
                callableStatement.setDate(2, reserva.getFecha());
                callableStatement.setString(3, reserva.getHora());
                callableStatement.setInt(4, reserva.getNumeroPersonas());
                callableStatement.setString(5, reserva.getDescripcion());

                callableStatement.execute();
                System.out.println("Stored procedure executed successfully.");
            }
        } catch (SQLException e) {
            if (e.getErrorCode() == 20008) {
                System.out.println("No se puede modificar la hora de una reserva para una fecha que ya ha pasado.");               
            } else {
                System.out.println("Error detectado");
                e.printStackTrace();
            }
        }   
    }
}
