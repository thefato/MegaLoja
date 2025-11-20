package org.lasalle.mega.loja.application.services.security;

import org.lasalle.mega.loja.domain.request.LoginAuthRequest;
import org.lasalle.mega.loja.domain.response.UserAuthResponse;

public interface AuthLoginService {

    UserAuthResponse executeUserLogin(LoginAuthRequest authRequest);

}
