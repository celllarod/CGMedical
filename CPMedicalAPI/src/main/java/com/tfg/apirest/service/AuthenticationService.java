package com.tfg.apirest.service;

import com.tfg.apirest.entity.Hospital;
import com.tfg.apirest.entity.TipoRol;
import com.tfg.apirest.entity.Usuario;
import com.tfg.apirest.security.dto.JwtResponse;
import com.tfg.apirest.security.dto.SignUpRequest;
import com.tfg.apirest.security.dto.UserDetailsImpl;
import com.tfg.apirest.security.jwt.JwtUtils;
import com.tfg.apirest.validation.group.annotation.SignUpRequestValidationAnnotation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.time.Instant;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Validated
public class AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    /** Clase para codificar la contraseña */
    private final PasswordEncoder encoder;
    /** Servicio de Rol */
    private final TipoRolesService tipoRolesService;
    /** Servicio de Hospital */
    private final HospitalesService hospitalesService;
    /** Repositorio de Usuario */
    private final UsuariosService usuariosService;

    public JwtResponse autenticar(Authentication authentication) {
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        String rol = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).toList().get(0);
        return new JwtResponse(jwt, userDetails.getId(),
                userDetails.getUsername(),
                rol);
    }


    @Transactional
    @Validated
    public JwtResponse registrar(@SignUpRequestValidationAnnotation SignUpRequest signUpRequest) {
        // Se crea una nueva cuenta para el usuario
        Usuario usuario = Usuario.builder()//
                .id(UUID.randomUUID()) //
                .nombre(signUpRequest.getNombre())//
                .apellido1(signUpRequest.getApellido1())//
                .apellido2(signUpRequest.getApellido2())//
                .email(signUpRequest.getEmail().toLowerCase())//
                .hashPassword(encoder.encode(signUpRequest.getPassword()))//
                .fechaAlta(Instant.now())//
                .build();

        // Asociar hospital al usuario y crerar hospital si no existe
        var nombreHospital = signUpRequest.getHospital();
        var hospital = hospitalesService.findHospitalByNombre(nombreHospital);
        var hospitalUsuario = new Hospital();
        if(hospital.isEmpty()){
            var hospitalNuevo = new Hospital();
            hospitalNuevo.setNombre(nombreHospital.toLowerCase());
            hospitalUsuario = hospitalesService.registrar(hospitalNuevo);
        } else{
            hospitalUsuario = hospital.get();
        }
        usuario.setHospital(hospitalUsuario);

        // Se asocia un rol al usuario
        var strRol = signUpRequest.getRol();
        var rol = new TipoRol();
        // Si se ha creado un hospital porque no existía, el usuario será GESTOR
        if (hospital.isEmpty() || "GESTOR".equals(strRol)) {
            rol = tipoRolesService.findRolByCodigo("GESTOR");
        } else {
            rol = tipoRolesService.findRolByCodigo("USER");
        }
        usuario.setRol(rol);

        // Se registra el usuario
        usuariosService.registrar(usuario);

        // Se autentica al nuevo usuario para obtener el token
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signUpRequest.getEmail(), signUpRequest.getPassword()));

        return this.autenticar(authentication);
    }





}
