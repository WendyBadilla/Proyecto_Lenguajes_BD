package com.EatAway.serviceImpl;

import com.EatAway.service.ResenaService;
import org.springframework.stereotype.Service;
import com.EatAway.domain.Resena;
import com.EatAway.domain.Usuario;
import com.EatAway.service.UsuarioService;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import oracle.jdbc.OracleTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

@Service
public class ResenaServiceImpl implements ResenaService {

    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private FirebaseStorageServiceImpl firebaseStorageService;
    
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
    
    @Override
    public void saveResena(Resena resena) {

        String jdbcUrl = "jdbc:oracle:thin:@localhost:1521:orcl";
        String user = "C##eataway";
        String password = "sws2024";

        try (Connection connection = DriverManager.getConnection(jdbcUrl, user, password)) {
            System.out.println("Connected to the Oracle database");

            String callStatement = "{ call C##eataway.PCK_EATAWAY_RESENAS_MANEJAR.InsertarResenaSP(?, ?, ?, ?) }";
            System.out.println("Agregar mediante paquete");
            try (CallableStatement callableStatement = connection.prepareCall(callStatement)) {

                callableStatement.setInt(1, resena.getIdUsuario());
                callableStatement.setInt(2, resena.getIdLocal());
                callableStatement.setInt(3, resena.getCalificacion());
                callableStatement.setString(4, resena.getComentario());

                callableStatement.execute();
                System.out.println("Stored procedure de creacion ejecutado exitosamente.");
            }
        } catch (SQLException e) {
            System.out.println("Error detected");
            e.printStackTrace();
        }

    }

}
