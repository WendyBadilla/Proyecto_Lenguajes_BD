
package com.EatAway.dao;

import com.EatAway.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioDao  extends JpaRepository<Usuario,Long> {
    
     //Asi buscamos el usuario de distintas formas
    
    Usuario findByUsername(String username);

    Usuario findByUsernameAndPassword(String username, String Password);

    Usuario findByUsernameOrCorreo(String username, String correo);

    boolean existsByUsernameOrCorreo(String username, String correo);
    
}