package com.patitasfelices.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

public class CloudinaryConfig {

    private static Cloudinary instancia;

    public static Cloudinary getInstancia() {
        if (instancia == null) {
            instancia = new Cloudinary(ObjectUtils.asMap(
                    "cloud_name", Config.get("cloudinary.name"),
                    "api_key", Config.get("cloudinary.key"),
                    "api_secret", Config.get("cloudinary.secret"),
                    "secure", true
            ));
        }
        return instancia;
    }
}