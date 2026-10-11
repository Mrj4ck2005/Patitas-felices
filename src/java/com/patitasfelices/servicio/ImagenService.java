package com.patitasfelices.servicio;

import com.patitasfelices.config.CloudinaryConfig;
import com.cloudinary.utils.ObjectUtils;

import javax.servlet.http.Part;
import java.io.InputStream;
import java.util.Map;

/**
 * Se encarga de subir la foto de la mascota a Cloudinary y devolver la URL
 * publica que se guardara en tb_mascotas.foto_url.
 * Requiere el SDK cloudinary-http44 en las Libraries del proyecto.
 */
public class ImagenService {

    @SuppressWarnings("unchecked")
    public String subirFoto(Part filePart) {
        try (InputStream input = filePart.getInputStream()) {
            byte[] bytes = input.readAllBytes();
            Map<?, ?> resultado = CloudinaryConfig.getInstancia().uploader()
                    .upload(bytes, ObjectUtils.asMap("folder", "patitas_felices/mascotas"));
            return (String) resultado.get("secure_url");
        } catch (Exception e) {
            throw new RuntimeException("Error al subir imagen a Cloudinary", e);
        }
    }
}
