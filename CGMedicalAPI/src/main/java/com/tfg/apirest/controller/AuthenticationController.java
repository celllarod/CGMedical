package com.tfg.apirest.controller;

import java.time.Instant;
import java.util.UUID;
import javax.validation.Valid;

import com.tfg.apirest.entity.TipoRol;
import com.tfg.apirest.entity.Usuario;
import com.tfg.apirest.repository.RolRepository;
import com.tfg.apirest.repository.UsuarioRepository;
import com.tfg.apirest.security.dto.*;
import com.tfg.apirest.security.jwt.JwtUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    UsuarioRepository usuarioRepository;
    @Autowired
    RolRepository rolRepository;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    JwtUtils jwtUtils;


    /**
     * Permite autenticar un usuario y obtener su token JWT
     *
     * @param loginRequest Información de inicio de sesión del usuario
     * @return token si es autorizado, 413 si no
     */
    @PostMapping("/signin")
    @ResponseStatus(HttpStatus.OK)
    @CrossOrigin(origins = "*", maxAge = 3600)
    public JwtResponse authenticateUser(@Valid @RequestBody @NotNull LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        String rol = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).toList().get(0);
        return new JwtResponse(jwt, userDetails.getId(),
                userDetails.getUsername(),
                rol);
    }

    /**
     * Permite el registro de un usuario
     *
     * @param signUpRequest Información de registro del usuario
     * @return token si es autorizado, 413 si no
     */
    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        if (usuarioRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Email ya existe en el sistema: " + signUpRequest.getEmail()));
        }
        // Se crea una nueva cuenta para el usuario
        Usuario usuario = Usuario.builder()//
                        .id(UUID.randomUUID()) //
                        .nombre(signUpRequest.getNombre())//
                        .apellido1(signUpRequest.getApellido1())//
                        .apellido2(signUpRequest.getApellido2())//
                        .email(signUpRequest.getEmail())//
                        .hashPassword(encoder.encode(signUpRequest.getPassword()))//
                        .fechaAlta(Instant.now())//
                        .build();

        String strRol = signUpRequest.getRol();
        var rol = new TipoRol();
        if (strRol == null) {
            rol = rolRepository.findByCodigo("USER")
                    .orElseThrow(() -> new RuntimeException("Rol no encontrado"));
        } else {
            rol = switch (strRol) {
                case "GESTOR" -> rolRepository.findByCodigo("GESTOR")
                        .orElseThrow(() -> new RuntimeException("Rol no encontrado: " + strRol));
                default -> rolRepository.findByCodigo("USER")
                        .orElseThrow(() -> new RuntimeException("Rol no encontrado: " + strRol));
            };
        }
        usuario.setRol(rol);
        usuarioRepository.save(usuario);
        return ResponseEntity.ok(new MessageResponse("Usuario registrado con éxito"));
    }
}