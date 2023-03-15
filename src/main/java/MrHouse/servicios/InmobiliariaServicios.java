/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MrHouse.servicios;


import MrHouse.entidades.Foto;
import MrHouse.entidades.Inmobiliaria;
import MrHouse.enumeraciones.ProvinciaEnum;
import MrHouse.enumeraciones.Roles;
import MrHouse.excepciones.MyException;
import MrHouse.repositorios.InmobiliariaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author facuq
 */
@Service
public class InmobiliariaServicios implements UserDetailsService {

    @Autowired
    private InmobiliariaRepositorio inmobiliariaRepositorio;

    @Autowired
    private FotoServicios fotoServicios;

    @Transactional
    public void registrar(MultipartFile archivo,String nombre, String email, String password, String password2, ProvinciaEnum provincias) throws MyException {

        validar(nombre, email, password, provincias);

        Inmobiliaria inmobiliaria = new Inmobiliaria();
        inmobiliaria.setNombre(nombre);
        inmobiliaria.setEmail(email);
        inmobiliaria.setPassword(new BCryptPasswordEncoder().encode(password));
        inmobiliaria.setRol(Roles.INMOBILIARIA);
        inmobiliaria.setProvincias(provincias);
        Foto foto = fotoServicios.save(archivo);
        inmobiliaria.setFoto(foto);

        inmobiliariaRepositorio.save(inmobiliaria);

    }

    @Transactional
    public void modificar(MultipartFile archivo, String id, String nombre, String email, String password, String password2, ProvinciaEnum provincias) throws MyException {

        validar(nombre, email, password, provincias);

        Optional<Inmobiliaria> respuesta = inmobiliariaRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Inmobiliaria inmobiliaria = respuesta.get();
            inmobiliaria.setNombre(nombre);
            inmobiliaria.setEmail(email);
            String encriptada = new BCryptPasswordEncoder().encode(password);
            inmobiliaria.setPassword(encriptada);
            String idImagen = null;
            if(inmobiliaria.getFoto()!= null){
                idImagen = inmobiliaria.getFoto().getId();
            }
            Foto foto = fotoServicios.update(archivo, idImagen);
            inmobiliaria.setFoto(foto);

            inmobiliariaRepositorio.save(inmobiliaria);
        } else {

            throw new MyException("No se encontró el usuario solicitado");
        }
    }

    @Transactional
    public void eliminar(String id) throws MyException {
        if (id == null || id.isEmpty()) {
            throw new MyException("El id esta vacio o es nulo");
        }
        Optional<Inmobiliaria> respuesta = inmobiliariaRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Inmobiliaria inmobiliaria = respuesta.get();
            inmobiliariaRepositorio.delete(inmobiliaria);
        }
    }

    private void validar(String title, String body, String photo, ProvinciaEnum provincias) throws MyException {
        if (null == title || title.isEmpty()) {
            throw new MyException("El título ingresado no es válido.");
        }
        if (null == body || body.isEmpty()) {
            throw new MyException("La descripción no es válida.");
        }
        if (null == photo || photo.isEmpty()) {
            throw new MyException("La foto no es válida.");
        }
        if ( null == provincias) {
            throw new MyException("La provincia no puede ser nula o estar vacía");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Inmobiliaria inmobiliaria = inmobiliariaRepositorio.buscarPorEmail(email);

        if (inmobiliaria != null) {

            List<GrantedAuthority> permisos = new ArrayList();

            GrantedAuthority p = new SimpleGrantedAuthority("ROLE_" + inmobiliaria.getRol().toString());

            permisos.add(p);
            
            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();

            HttpSession session = attr.getRequest().getSession(true);
            
            session.setAttribute("inmobiliariasession",inmobiliaria);

            return new User(inmobiliaria.getEmail(), inmobiliaria.getPassword(), permisos);

        } else {
            return null;
        }
    }
}
