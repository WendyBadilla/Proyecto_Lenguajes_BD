
package com.EatAway.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface FirebaseStorageService {

    public String cargaImagen(MultipartFile archivoLocalCliente, String carpeta, Long id);

    //El BuketName es el <id_del_proyecto> + ".appspot.com"
    final String BucketName = "eataway-ee782.appspot.com";

    //Esta es la ruta básica de este proyecto
    final String rutaSuperiorStorage = "EatAway";

    //Ubicación donde se encuentra el archivo de configuración Json
    final String rutaJsonFile = "firebase";
    
    //El nombre del archivo Json
    final String archivoJsonFile = "eataway-ee782-firebase-adminsdk-70ya5-316135d241.json";
}
