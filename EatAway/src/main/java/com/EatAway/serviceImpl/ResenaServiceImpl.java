package com.EatAway.serviceImpl;

import com.EatAway.service.ResenaService;
import org.springframework.stereotype.Service;
import com.EatAway.domain.Resena;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import oracle.jdbc.OracleTypes;

@Service
public class ResenaServiceImpl implements ResenaService {

    @Override
    public List<Resena> getResenasPorLocal(long idLocal) {
        String jdbcUrl = "jdbc:oracle:thin:@localhost:1521:orcl";
        String user = "C##eataway";
        String password = "sws2024";

        List<Resena> resenas = new ArrayList<>();

        try (Connection conexion = DriverManager.getConnection(jdbcUrl, user, password)) {
            String callStatement = "{call C##eataway.PCK_EATAWAY_RESENAS_OBTENER.ObtenerResenasPorLocalSP(?, ?)}";

            try (CallableStatement llamadaExecute = conexion.prepareCall(callStatement)) {
                // Establecimiento del parámetro de entrada
                llamadaExecute.setLong(1, idLocal);
                // Registro del parámetro de salida
                llamadaExecute.registerOutParameter(2, OracleTypes.CURSOR);

                llamadaExecute.execute();
                try (ResultSet resultSet = (ResultSet) llamadaExecute.getObject(2)) {
                    while (resultSet.next()) {
                        Resena resena = new Resena();
                        resena.setIdResena(resultSet.getLong("id_resena"));
                        resena.setIdLocal(resultSet.getInt("id_local"));
                        resena.setCalificacion(resultSet.getInt("calificacion"));
                        resena.setComentario(resultSet.getString("comentario"));
                        resena.setNombreCompleto(resultSet.getString("nombre_completo"));
                        resena.setNombreUsuario(resultSet.getString("nombre_usuario"));
                        resena.setFotoUsuario(resultSet.getString("foto_usuario"));
                        resenas.add(resena);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resenas;
    }

}
