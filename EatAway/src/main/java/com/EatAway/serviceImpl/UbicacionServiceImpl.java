package com.EatAway.serviceImpl;

import com.EatAway.domain.Ubicacion;
import com.EatAway.service.UbicacionService;
import org.springframework.stereotype.Service;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import oracle.jdbc.OracleTypes;

@Service
public class UbicacionServiceImpl implements UbicacionService {

    @Override
    public List<Ubicacion> getUbicacionesPorLocal(long idLocal) {
        String jdbcUrl = "jdbc:oracle:thin:@localhost:1521:orcl";
        String user = "C##eataway";
        String password = "sws2024";

        List<Ubicacion> ubicaciones = new ArrayList<>();

        try (Connection conexion = DriverManager.getConnection(jdbcUrl, user, password)) {
            // La llamada al procedimiento almacenado
            String callStatement = "{call ObtenerUbicacionesPorLocalSP(?, ?)}";

            try (CallableStatement llamadaExecute = conexion.prepareCall(callStatement)) {
                // Establecimiento del parámetro de entrada
                llamadaExecute.setLong(1, idLocal);
                // Registro del parámetro de salida
                llamadaExecute.registerOutParameter(2, OracleTypes.CURSOR);

                llamadaExecute.execute();

                // Recuperar el cursor de salida
                try (ResultSet resultSet = (ResultSet) llamadaExecute.getObject(2)) {
                    while (resultSet.next()) {
                        Ubicacion ubicacion = new Ubicacion();
                        ubicacion.setIdUbicacion(resultSet.getLong("id_ubicacion"));
                        ubicacion.setIdLocal(resultSet.getLong("id_local"));
                        ubicacion.setProvincia(resultSet.getString("provincia"));
                        ubicacion.setDireccion(resultSet.getString("direccion"));
                        ubicaciones.add(ubicacion);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ubicaciones;
    }

}
