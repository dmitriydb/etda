package com.github.dmitriydb.etda.service;

import com.github.dmitriydb.etda.security.CaptchaSettings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.regex.Pattern;

@Service
public class CaptchaService implements ICaptchaService {

    private static final Logger logger = LoggerFactory.getLogger(CaptchaService.class);

    @Autowired
    private CaptchaSettings captchaSettings;

    private RestOperations restTemplate = new RestTemplate();

    private static Pattern RESPONSE_PATTERN = Pattern.compile("[A-Za-z0-9_-]+");

    @Override
    public void processResponse(String response) throws Exception {
        if(!responseSanityCheck(response)) {
            throw new Exception("Response contains invalid characters");
        }
        URI verifyUri = URI.create(String.format(
                "https://www.google.com/recaptcha/api/siteverify?secret=%s&response=%s",
                captchaSettings.getSecret(), response));

        GoogleResponse googleResponse = restTemplate.getForObject(verifyUri, GoogleResponse.class);

        if(!googleResponse.isSuccess()) {
            logger.debug("Error reCaptcha");
            throw new Exception("reCaptcha was not successfully validated");
        }
        logger.debug("Successful reCaptcha");


    }

    private boolean responseSanityCheck(String response) {
        return StringUtils.hasLength(response) && RESPONSE_PATTERN.matcher(response).matches();
    }
}