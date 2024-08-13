
package com.EatAway.service;

import com.EatAway.domain.Usuario;
import java.util.List;

public interface UsuarioService {

    public List<Usuario> getUsuarios();

    public Usuario getUsuario(Usuario usuario);

    public Usuario getUsuarioPorUsername(String username);

    public Usuario getUsuarioPorUsernameYPassword(String username, String password);

    public Usuario getUsuarioPorUsernameOCorreo(String username, String correo);

    public boolean existeUsuarioPorUsernameOCorreo(String username, String correo);

    public void save(Usuario usuario);

    public void delete(Usuario usuario);

}
