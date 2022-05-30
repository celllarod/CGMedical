package com.tfg.apirest.security.controller;

import java.time.Instant;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.validation.Valid;

import com.tfg.apirest.entity.Rol;
import com.tfg.apirest.entity.Usuario;
import com.tfg.apirest.repository.RolRepository;
import com.tfg.apirest.repository.UsuarioRepository;
import com.tfg.apirest.security.dto.*;
import com.tfg.apirest.security.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
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
     * Permite el registro de un usuario
     *
     * @param loginRequest
     * @return
     */
    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        String rol = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList()).get(0);
        return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getId(),
                userDetails.getUsername(),
                rol));
    }
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        if (usuarioRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: El email introducido ya existe en el sistema!"));
        }
        // Create new user's account
        Usuario user = Usuario.builder()//
                        .id(UUID.randomUUID()) //
                        .nombre(signUpRequest.getNombre())//
                        .apellido1(signUpRequest.getApellido1())//
                        .apellido2(signUpRequest.getApellido2())//
                        .email(signUpRequest.getEmail())//
                        .hashPassword(encoder.encode(signUpRequest.getPassword()))//
                        .fechaAlta(Instant.now())//
                        .build();


        String strRol = signUpRequest.getRol();
        Rol rol = new Rol();
        if (strRol == null) {
            rol = rolRepository.findByCodigo("USER")
                    .orElseThrow(() -> new RuntimeException("Error: Rol no encontrado."));
        } else {
            switch (strRol) {
                case "ADMIN":
                    Rol adminRole = rolRepository.findByCodigo("ADMIN")
                            .orElseThrow(() -> new RuntimeException("Error: Rol no encontrado."));
                    break;
                default:
                    Rol userRole = rolRepository.findByCodigo("USER")
                            .orElseThrow(() -> new RuntimeException("Error: Rol no encontrado."));
            }
        }
        user.setRol(rol);
        usuarioRepository.save(user);
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
}