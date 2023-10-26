package org.fyp.api;

import org.fyp.cli.LoginRequest;
import org.fyp.cli.User;
import org.fyp.client.FailedLoginException;
import org.fyp.client.FailedToGenerateTokenException;
import org.fyp.db.AuthDao;
import org.mindrot.jbcrypt.BCrypt;

public class AuthService {
    TokenService tokenService;
    private AuthDao authDao;

    public AuthService(AuthDao authDao, TokenService tokenService) {
        this.tokenService = tokenService;
        this.authDao = authDao;
    }


    public String login(LoginRequest login) throws FailedLoginException {
        try{
            User user = authDao.getUserByEmail(login.getUsername(), login.getPassword());
            if(user==null || !isValidPassword(login.getPassword(), user.getHashedPassword())){
                throw new FailedLoginException();
            }
            String token = tokenService.generateToken(user);
            if(token!=null){
                return token;
            }
        }catch(Exception e){
            throw new FailedToGenerateTokenException();
        }
        throw new FailedLoginException();
    }

    public boolean isValidPassword(String candidatePassword, String hashedPassword){
        return BCrypt.checkpw(candidatePassword, hashedPassword);
    }
}
