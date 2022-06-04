package com.tfg.apirest.security.dto;

import java.util.*;

import com.tfg.apirest.entity.Usuario;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.fasterxml.jackson.annotation.JsonIgnore;
/**
 *  Clase que permite obtener detalles del usuario necesarios para realizar la autenticación
 *
 * */
@AllArgsConstructor
public class UserDetailsImpl implements UserDetails {
    private static final long serialVersionUID = 1L;
    /** UUID */
    private UUID id;
    /** Email del usuario */
    private String userName;
    /** Contraseña del usuario */
    @JsonIgnore
    private String password;
    /** Rol del usuario */
    private Collection<? extends GrantedAuthority> authorities;

    /** Builder que permite crear una instancia de UserDetailsImp
     *
     * @param usuario Usuario a partir del cual obtener instancia
     * @return userDatails
     */
    public static UserDetailsImpl build(Usuario usuario) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(usuario.getRol().getCodigo()));

        return new UserDetailsImpl(
                usuario.getId(),
                usuario.getEmail(),
                usuario.getHashPassword(),
                authorities);
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    public UUID getId() { return this.id;}
    @Override
    public String getUsername() {
        return this.userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        UserDetailsImpl user = (UserDetailsImpl) o;
        return Objects.equals(id, user.id);
    }
}