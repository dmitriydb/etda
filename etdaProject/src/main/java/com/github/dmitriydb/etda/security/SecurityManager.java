package com.github.dmitriydb.etda.security;

import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Класс, который занимается хэшированием и проверкой паролей
 * Для шифрования используется алгоритм bcrypt
 *
 * @version 0.1.1
 * @since 0.1.1
 */
public class SecurityManager {

    private final static Logger logger = LoggerFactory.getLogger(SecurityManager.class);

    public static String hashPassword(String plainTextPassword){
        String result = BCrypt.hashpw(plainTextPassword, BCrypt.gensalt());
        logger.debug("Encoding {} to {}", plainTextPassword, result);
        return result;
    }

    public static boolean checkPass(String plainPassword, String hashedPassword) {
        boolean result = BCrypt.checkpw(plainPassword, hashedPassword);
        logger.debug("Matching {} with {} = {}", plainPassword, hashedPassword, result);
        return result;
    }
}