package org.lasalle.mega.loja.application.security.services;

import org.lasalle.mega.loja.domain.request.AuthRequest;
import org.lasalle.mega.loja.domain.response.UserAuthResponse;

public interface AuthRegisterService {

    UserAuthResponse executeUserRegister(AuthRequest authRequest);

}
