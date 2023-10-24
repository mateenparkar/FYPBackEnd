package org.fyp.api;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.fyp.cli.User;
import org.fyp.db.AuthDao;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

public class TokenService {
    private static final long EXPIRATION_TIME = 86400000;
    private AuthDao authDao;
    public TokenService(AuthDao authDao){
        this.authDao = authDao;
    }

    private static final String SECRET = "afdkghjwkrejsdgewrkjvh,shvsgbkuweyiu5.kghjwesbjfg,kw3h4e";

    public String generateToken(User user){
        Key key = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));

        String subject = user.getUsername();
        Date expiration = new Date(System.currentTimeMillis() + EXPIRATION_TIME);

        String token = Jwts.builder().setSubject(subject).claim("username", user.getUsername()).setExpiration(expiration)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        return token;
    }
}
