package com.tfg.apirest.security.jwt;

import java.util.Date;

import com.tfg.apirest.security.dto.UserDetailsImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.*;

/**
 * Clase que permite:
 *  1) Generar un token JWT a partir de: un username (email), la fecha actual, el tiempo de expiración y el secret)
 *  2) Obtener un username (email) a partir del token JWT
 *  3) Validar un token JWT
 */
@Component
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    /** Secret con el que se firmarán los tokens JWT generados */
    @Value("${cgmedical.app.jwtSecret}")
    private String jwtSecret;
    /** Tiempo de expitación del token JWT */
    @Value("${cgmedical.app.jwtExpirationMs}")
    private long jwtExpirationMs;

    /**
     * Permite generar un token JWT
     * @param authentication
     * @return
     */
    public String generateJwtToken(Authentication authentication) {
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
        return Jwts.builder()
                .setSubject((userPrincipal.getUsername()))
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }
    public String getUsernameFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }
    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (MalformedJwtException e) {
            logger.error("[Error] Token JWT inválido: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("[Error] Token JWT expirado: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("[Error] Token JWT n soportado: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("[Error] Sin JWT claims: {}", e.getMessage());
        }
        return false;
    }
}