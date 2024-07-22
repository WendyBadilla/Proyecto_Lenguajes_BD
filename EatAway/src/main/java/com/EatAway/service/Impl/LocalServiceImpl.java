package com.EatAway.serviceImpl;

import com.EatAway.domain.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.EatAway.dao.LocalDao;
import com.EatAway.service.LocalService;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import oracle.jdbc.OracleTypes;

@Service
public class LocalServiceImpl implements LocalService {

    private final LocalDao localDao;

    @Autowired
    public LocalServiceImpl(LocalDao localDao) {
        this.localDao = localDao;
    }

    @Override
    public List<Local> getLocales() {
        String jdbcUrl = "jdbc:oracle:thin:@localhost:1521:orcl";
        String user = "C##eataway";
        String password = "sws2024";
        List<Local> lista = new ArrayList<>();

        try (Connection conexion = DriverManager.getConnection(jdbcUrl, user, password)) {
            System.out.println("Conectado a la base de datos");

            String callStatement = "{call C##eataway.PCK_EATAWAY_LOCALES_OBTENER.ObtenerLocalesSP(?)}";
            System.out.println("Obtención de locales");
            try (CallableStatement llamadaExecute = conexion.prepareCall(callStatement)) {
                llamadaExecute.registerOutParameter(1, OracleTypes.CURSOR);

                llamadaExecute.execute();
                ResultSet resultSet = (ResultSet) llamadaExecute.getObject(1);

                while (resultSet.next()) {
                    Local local = new Local();
                    local.setIdLocal(resultSet.getLong("id_local"));
                    local.setNombre(resultSet.getString("nombre"));
                    local.setIdCategoria(resultSet.getLong("id_categoria"));
                    local.setDescripcion(resultSet.getString("descripcion"));
                    local.setTipoCategoria(resultSet.getString("tipo"));
                    local.setFoto(resultSet.getString("foto"));
                    
                    lista.add(local);
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
