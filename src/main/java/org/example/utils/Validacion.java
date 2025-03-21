package org.example.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Validacion {

    /**
     * Cifra una clave utilizando el algoritmo de hash SHA3-256.
     * Este método convierte la clave en un hash hexadecimal seguro.
     *
     * @param clave La clave que se desea cifrar.
     * @return El hash hexadecimal de la clave o {@code null} si ocurre un error durante el cifrado.
     * @throws NoSuchAlgorithmException Si el algoritmo SHA3-256 no está disponible en el entorno.
     */
    public static String encryptClave(String clave) {
        String hexString = null;

        try {

            MessageDigest digest = MessageDigest.getInstance("SHA3-256");

            byte[] hash = digest.digest(clave.getBytes());

            StringBuilder hexStringBuilder = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexStringBuilder.append('0');
                }
                hexStringBuilder.append(hex);
            }

            hexString = hexStringBuilder.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return hexString;
    }

    /**
     * Valida el formato de un correo electrónico asegurándose de que siga
     * un patrón específico (dominios permitidos: gmail.com, gmail.es, hotmail.com, hotmail.es).
     *
     * @param email El correo electrónico que se desea validar.
     * @return {@code true} si el correo cumple con el formato, {@code false} de lo contrario.
     */
    public static boolean validacionEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@(gmail|hotmail)\\.(com|es)$";
        return email.matches(emailRegex);
    }

}



