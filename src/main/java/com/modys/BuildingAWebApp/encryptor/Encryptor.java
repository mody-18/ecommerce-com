package com.modys.BuildingAWebApp.encryptor;


import org.mindrot.jbcrypt.BCrypt;

public class Encryptor {

    public static String encrypt(String password) {

        String pepper = "securityIsImportant";

        return BCrypt.hashpw(password, BCrypt.gensalt() + pepper);
    }

    public static boolean decrypt(String customerPassword, String hashedPassword) {

        return BCrypt.checkpw(customerPassword, hashedPassword);
    }
}
