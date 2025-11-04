package org.lasalle.mega.loja.application.security.services;

import org.lasalle.mega.loja.domain.request.LoginAuthRequest;
import org.lasalle.mega.loja.domain.request.RegisterAuthRequest;
import org.lasalle.mega.loja.domain.response.UserAuthResponse;

public interface AuthLoginService {

    UserAuthResponse executeUserLogin(LoginAuthRequest authRequest);

}
