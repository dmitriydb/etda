package com.github.dmitriydb.etda.webapp.security;

import com.github.dmitriydb.etda.security.SecurityManager;
import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;

public class CustomPasswordEncoder implements PasswordEncoder {

    private static final Logger logger = LoggerFactory.getLogger(CustomPasswordEncoder.class);

    @Override
    public String encode(CharSequence rawPassword) {
        String result = BCrypt.hashpw(rawPassword.toString(), BCrypt.gensalt());
        logger.debug("Encoding {} to {}", rawPassword.toString(), result);
        return result;
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        boolean result = BCrypt.checkpw(rawPassword.toString(), encodedPassword);
        logger.debug("Matching {} with {} = {}", rawPassword.toString(), encodedPassword, result);
        return result;
    }
}
