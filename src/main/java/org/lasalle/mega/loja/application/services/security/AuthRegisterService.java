package org.lasalle.mega.loja.application.services.security;

import org.lasalle.mega.loja.domain.request.RegisterAuthRequest;
import org.lasalle.mega.loja.domain.response.UserAuthResponse;

public interface AuthRegisterService {

    UserAuthResponse executeUserRegister(RegisterAuthRequest authRequest);

}
