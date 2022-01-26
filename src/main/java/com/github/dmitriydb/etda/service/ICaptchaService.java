package com.github.dmitriydb.etda.service;

public interface ICaptchaService {
    void processResponse(String response) throws Exception;
}
