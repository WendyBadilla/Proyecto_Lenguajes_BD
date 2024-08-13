package com.EatAway.serviceImpl;

import com.EatAway.service.ReservaService;
import org.springframework.stereotype.Service;
import com.EatAway.domain.Reserva;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Service
public class ReservaServiceImpl implements ReservaService {
    
    @Override
    public void saveReserva(Reserva reserva) {

        String jdbcUrl = "jdbc:oracle:thin:@localhost:1521:orcl";
        String user = "C##eataway";
        String password = "sws2024";

        try (Connection connection = DriverManager.getConnection(jdbcUrl, user, password)) {
            System.out.println("Connected to the Oracle database");

            String callStatement = "{ call C##eataway.PCK_EATAWAY_RESERVAS_MANEJAR.InsertarReservaSP(?, ?, ?, ?, ?, ?) }";
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
            System.out.println("Error detected");
            e.printStackTrace();
        }

    }

}