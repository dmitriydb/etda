package com.github.dmitriydb.etda.security;

import org.mindrot.jbcrypt.BCrypt;

/**
 * Класс, который занимается хэшированием и проверкой паролей
 * Для шифрования используется алгоритм bcrypt
 *
 * @version 0.1.1
 * @since 0.1.1
 */
public class SecurityManager {

    public static String hashPassword(String plainTextPassword){
        return BCrypt.hashpw(plainTextPassword, BCrypt.gensalt());
    }

    public static boolean checkPass(String plainPassword, String hashedPassword) {
        return  (BCrypt.checkpw(plainPassword, hashedPassword));
    }
}