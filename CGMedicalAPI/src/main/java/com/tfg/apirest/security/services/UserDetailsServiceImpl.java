package com.tfg.apirest.security.services;
import com.tfg.apirest.entity.Usuario;
import com.tfg.apirest.repository.UsuarioRepository;
import com.tfg.apirest.security.dto.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Clase que permite obtener el objeto que implementa la interfaz UserDetails
 *
 * @author celllarod
 */

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UsuarioRepository usuarioRepository;
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Se comprueba si existe el usuario con el email (username) dado
        Usuario user = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("[FAIL] No se ha encontrado ningún usuario con este email: " + email));
        return UserDetailsImpl.build(user);
    }
}