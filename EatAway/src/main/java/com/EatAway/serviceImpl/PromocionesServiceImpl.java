
package com.EatAway.serviceImpl;

import com.EatAway.domain.Promociones;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.EatAway.service.PromocionesService;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import oracle.jdbc.OracleTypes;

@Service
public class PromocionesServiceImpl implements PromocionesService {
    
    @Override
    public List<Promociones> getPromociones() {
        String jdbcUrl = "jdbc:oracle:thin:@localhost:1521:orcl";
        String user = "C##eataway";
        String password = "sws2024";
        List<Promociones> lista = new ArrayList<>();

        try (Connection conexion = DriverManager.getConnection(jdbcUrl, user, password)) {
            System.out.println("Conectado a la base de datos");

            String callStatement = "{call ObtenerPromocionesActivasSP(?)}";
            System.out.println("Obtención de promociones");
            try (CallableStatement llamadaExecute = conexion.prepareCall(callStatement)) {
                llamadaExecute.registerOutParameter(1, OracleTypes.CURSOR);

                llamadaExecute.execute();
                ResultSet resultSet = (ResultSet) llamadaExecute.getObject(1);

                while (resultSet.next()) {
                    Promociones promociones = new Promociones();
                    promociones.setIdPromocion(resultSet.getLong("id_promocion"));
                    promociones.setIdlocal(resultSet.getInt("id_local"));
                    promociones.setNombrePromocion(resultSet.getString("nombre_promocion"));
                    promociones.setDescripcion(resultSet.getString("descripcion"));
                    promociones.setFechaInio(resultSet.getDate("fecha_inicio"));
                    promociones.setFechaFin(resultSet.getDate("fecha_fin"));
                    promociones.setEstado(resultSet.getString("estado"));
                    promociones.setNombreLocal(resultSet.getString("nombre_local"));
                    promociones.setFotoLocal(resultSet.getString("foto_local"));
                    
                    lista.add(promociones);
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
