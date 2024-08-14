
package com.EatAway.serviceImpl;

import com.EatAway.domain.Fotos;
import com.EatAway.service.FotosService;
import java.util.List;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import oracle.jdbc.OracleTypes;
import org.springframework.stereotype.Service;

@Service
public class FotosServiceImpl implements FotosService{

    @Override
    public List<Fotos> getFotosPorLocal(long idLocal) {
        String jdbcUrl = "jdbc:oracle:thin:@localhost:1521:orcl";
        String user = "C##eataway";
        String password = "sws2024";

        List<Fotos> fotoses = new ArrayList<>();

        try (Connection conexion = DriverManager.getConnection(jdbcUrl, user, password)) {
            // La llamada al procedimiento almacenado
            String callStatement = "{call ObtenerFotosPorLocalSP(?, ?)}";

            try (CallableStatement llamadaExecute = conexion.prepareCall(callStatement)) {
                // Establecimiento del parámetro de entrada
                llamadaExecute.setLong(1, idLocal);
                // Registro del parámetro de salida
                llamadaExecute.registerOutParameter(2, OracleTypes.CURSOR);

                llamadaExecute.execute();

                // Recuperar el cursor de salida
                try (ResultSet resultSet = (ResultSet) llamadaExecute.getObject(2)) {
                    while (resultSet.next()) {
                        Fotos fotos = new Fotos();
                        fotos.setIdFoto(resultSet.getLong("id_foto"));
                        fotos.setIdLocal(resultSet.getLong("id_local"));
                        fotos.setRutaFoto(resultSet.getString("ruta_foto"));
                        fotoses.add(fotos);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return fotoses;
    }
}
