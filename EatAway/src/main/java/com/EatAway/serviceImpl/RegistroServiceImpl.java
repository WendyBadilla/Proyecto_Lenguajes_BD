
package com.EatAway.serviceImpl;

import com.EatAway.domain.Usuario;
import com.EatAway.service.CorreoService;
import com.EatAway.service.RegistroService;
import com.EatAway.service.UsuarioService;
import jakarta.mail.MessagingException;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

@Service
public class RegistroServiceImpl implements RegistroService {
    
    @Autowired
    private CorreoService correoService;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private MessageSource messageSource;  
    @Autowired
    private FirebaseStorageServiceImpl firebaseStorageService;
    
    @Override
    public Model activar(Model model, String username, String clave) {
        Usuario usuario
                = usuarioService.getUsuarioPorUsernameYPassword(username,
                        clave);
        if (usuario != null) {
            model.addAttribute("usuario", usuario);
        } else {
            model.addAttribute(
                    "titulo",
                    messageSource.getMessage(
                            "registro.activar",
                            null, Locale.getDefault()));
            model.addAttribute(
                    "mensaje",
                    messageSource.getMessage(
                            "registro.activar.error",
                            null, Locale.getDefault()));
        }
        return model;
    }
    
    @Override
    public void activar(Usuario usuario, MultipartFile imagenFile) {
        var passwordEncoder = new BCryptPasswordEncoder();
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));

        // Obtener el usuario existente usando el método getUsuario
        Usuario usuarioExistente = usuarioService.getUsuario(usuario);

        if (usuarioExistente != null) {
            // Si se proporciona una nueva imagen, actualiza la URL de la foto
            if (imagenFile != null && !imagenFile.isEmpty()) {
                String imagenUrl = firebaseStorageService.cargaImagen(imagenFile, "usuarios", usuario.getIdUsuario());
                usuario.setFoto(imagenUrl);
            } else {
                // Conservar la foto existente si no se proporciona una nueva imagen
                usuario.setFoto(usuarioExistente.getFoto());
            }

            // Guarda el usuario con la información actualizada
            usuarioService.save(usuario);
        }
    }


    @Override
    public Model crearUsuario(Model model, Usuario usuario)
            throws MessagingException {
        String mensaje;
        if (!usuarioService.
                existeUsuarioPorUsernameOCorreo(
                        usuario.getUsername(),
                        usuario.getCorreo())) {
            String clave = demeClave();
            usuario.setPassword(clave);
            usuarioService.save(usuario);
            enviaCorreoActivar(usuario, clave);
            mensaje = String.format(
                    messageSource.getMessage(
                            "registro.mensaje.activacion.ok",
                            null,
                            Locale.getDefault()),
                    usuario.getCorreo());
        } else {
            mensaje = String.format(
                    messageSource.getMessage(
                            "registro.mensaje.usuario.o.correo",
                            null,
                            Locale.getDefault()),
                    usuario.getUsername(), usuario.getCorreo());
        }
        model.addAttribute(
                "titulo",
                messageSource.getMessage(
                        "registro.activar",
                        null,
                        Locale.getDefault()));
        model.addAttribute(
                "mensaje",
                mensaje);
        return model;
    }

    @Override
    public Model recordarUsuario(Model model, Usuario usuario)
            throws MessagingException {
        String mensaje;
        Usuario usuario2 = usuarioService.getUsuarioPorUsernameOCorreo(
                usuario.getUsername(),
                usuario.getCorreo());
        if (usuario2 != null) {
            String clave = demeClave();
            usuario2.setPassword(clave);
            usuario2.getFoto();
            usuarioService.save(usuario2);
            enviaCorreoRecordar(usuario2, clave);
            mensaje = String.format(
                    messageSource.getMessage(
                            "registro.mensaje.recordar.ok",
                            null,
                            Locale.getDefault()),
                    usuario.getCorreo());
        } else {
            mensaje = String.format(
                    messageSource.getMessage(
                            "registro.mensaje.usuario.o.correo",
                            null,
                            Locale.getDefault()),
                    usuario.getUsername(), usuario.getCorreo());
        }
        model.addAttribute(
                "titulo",
                messageSource.getMessage(
                        "registro.activar",
                        null,
                        Locale.getDefault()));
        model.addAttribute(
                "mensaje",
                mensaje);
        return model;
    }

    private String demeClave() {
        String tira = "ABCDEFGHIJKLMNOPQRSTUXYZabcdefghijklmnopqrstuvwxyz0123456789_*+-";
        String clave = "";
        for (int i = 0; i < 40; i++) {
            clave += tira.charAt((int) (Math.random() * tira.length()));
        }
        return clave;
    }

    //Ojo cómo le lee una informacion del application.properties
    @Value("${servidor.http}")
    private String servidor;

    private void enviaCorreoActivar(Usuario usuario, String clave) throws MessagingException {
        String mensaje = messageSource.getMessage(
                "registro.correo.activar",
                null, Locale.getDefault());
        mensaje = String.format(
                mensaje, usuario.getNombre(),
                usuario.getPrimerApellido(), servidor,
                usuario.getUsername(), clave);
        String asunto = messageSource.getMessage(
                "registro.mensaje.activacion",
                null, Locale.getDefault());
        correoService.enviarCorreoHtml(usuario.getCorreo(), asunto, mensaje);
    }

    private void enviaCorreoRecordar(Usuario usuario, String clave) throws MessagingException {
        String mensaje = messageSource.getMessage(""
                + "registro.correo.recordar",
                null,
                Locale.getDefault());
        mensaje = String.format(
                mensaje, usuario.getNombre(),
                usuario.getPrimerApellido(), servidor,
                usuario.getUsername(), clave);
        String asunto = messageSource.getMessage(
                "registro.mensaje.recordar",
                null, Locale.getDefault());
        correoService.enviarCorreoHtml(
                usuario.getCorreo(),
                asunto, mensaje);
    }
}
