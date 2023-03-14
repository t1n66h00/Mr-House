/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MrHouse.servicios;

import MrHouse.entidades.Foto;
import MrHouse.entidades.Propiedad;
import MrHouse.excepciones.MyException;
import MrHouse.repositorios.PropiedadRepositorio;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author facuq
 */
@Service
public class PropiedadServicios {

    @Autowired
    PropiedadRepositorio propiedadRepositorio;

    @Autowired
    private FotoServicios fotoServicios;

    @Transactional
    public void registrar(MultipartFile archivo, Propiedad propiedadV) throws MyException {
        //P=PERSISTIR V=VALIDAR
        Propiedad propiedadP = validar(propiedadV);

        Foto foto = fotoServicios.save(archivo);
        propiedadP.setFoto(foto);
        propiedadRepositorio.save(propiedadP);
    }

    //PropiedadP = propiedad persistida  PropiedadC = nuevos cambios
    @Transactional
    public void modificar(MultipartFile archivo, Propiedad propiedadC) throws MyException {
        try {
            Propiedad propiedadP = validarCambios(propiedadC, buscarPorId(propiedadC.getId()));

            if (archivo != null && !archivo.isEmpty()) {
                propiedadP.setFoto(fotoServicios.save(archivo));
            }

            propiedadRepositorio.save(propiedadP);
        } catch (MyException e) {
            throw new MyException("No se editó la propiedad");
        }
    }

    @Transactional
    public Propiedad buscarPorId(String id) throws MyException {
        Optional<Propiedad> op = propiedadRepositorio.findById(id);
        if (op.isPresent()) {
            return op.get();
        } else {
            throw new MyException("No se encontró la propiedad solicitada.");

        }
    }

    @Transactional
    public void bajaPropiedad(Propiedad propiedad) throws MyException {

        Optional<Propiedad> op = propiedadRepositorio.findById(propiedad.getId());
        if (op.isPresent()) {
            Propiedad aux = op.get();
            aux.setAlta(Optional.of(false));
            propiedadRepositorio.save(aux);
        }

    }

    @Transactional
    public void altaPropiedad(Propiedad propiedad) throws MyException {

        Optional<Propiedad> op = propiedadRepositorio.findById(propiedad.getId());
        if (op.isPresent()) {
            Propiedad aux = op.get();
            aux.setAlta(Optional.of(true));
            propiedadRepositorio.save(aux);
        }
    }

    @Transactional
    public void eliminar(Propiedad propiedad) throws MyException {
        if (propiedad.getId() == null || propiedad.getId().isEmpty()) {
            throw new MyException("El id esta vacio o es nulo");
        }
        Optional<Propiedad> op = propiedadRepositorio.findById(propiedad.getId());
        if (op.isPresent()) {
            Propiedad aux = op.get();
            propiedadRepositorio.delete(aux);
        }
    }

    private Propiedad validar(Propiedad propiedad) throws MyException {
        propiedad.setAlta(Optional.of(true));
        if (propiedad.getPrecio() == null) {
            throw new MyException("El precio no puede ser nulo");

        }
        if (propiedad.getM2().isEmpty() || propiedad.getM2() == null) {
            throw new MyException("La cantidad de metros cuadrados no puede ser nula");
        }
        if (propiedad.getHabitaciones().isEmpty() || propiedad.getHabitaciones() == null) {
            throw new MyException("La cantidad de habitaciones no puede ser nula");
        }
        if (propiedad.getBanos().isEmpty() || propiedad.getBanos() == null) {
            throw new MyException("La cantidad de baños no puede ser nula");
        }
        if (propiedad.getDescripcion().isEmpty() || propiedad.getDescripcion() == null) {
            throw new MyException("La descripción no puede ser nula o estar vacía");
        }
        if (propiedad.getDireccion().isEmpty() || propiedad.getDireccion() == null) {
            throw new MyException("La dirección no puede ser nula o estar vacía");
        }
        if (propiedad.getInmobiliaria().getId().isEmpty() || propiedad.getInmobiliaria().getId() == null) {
            throw new MyException("La inmobiliaria no puede ser nula o estar vacía");
        }
        return propiedad;
    }

    //PropiedadP = propiedad persistida  PropiedadC = nuevos cambios
    private Propiedad validarCambios(Propiedad propiedadP, Propiedad propiedadC) throws MyException {

        if (propiedadC.getM2().equals(propiedadP.getM2())
                && propiedadC.getPrecio().equals(propiedadP.getPrecio())
                && propiedadC.getPropiedadTipo().equals(propiedadP.getPropiedadTipo())
                && propiedadC.getHabitaciones().equals(propiedadP.getHabitaciones())
                && propiedadC.getBanos().equals(propiedadP.getBanos())
                && propiedadC.getCochera().equals(propiedadP.getCochera())
                && propiedadC.getDireccion().equals(propiedadP.getDireccion())
                && propiedadC.getDescripcion().equals(propiedadP.getDescripcion())
                && propiedadC.getFoto().equals(propiedadP.getFoto())
                && propiedadC.getInmobiliaria().equals(propiedadP.getInmobiliaria())
                && propiedadC.getTransaccionPropiedad().equals(propiedadP.getTransaccionPropiedad())
                && propiedadC.getProvincias().equals(propiedadP.getProvincias())) {
            throw new MyException("No existen cambios para editar");
        }
        if (!propiedadC.getPrecio().equals(propiedadP.getPrecio())) {
            propiedadP.setPrecio(propiedadC.getPrecio());
        }
        if (!propiedadC.getPropiedadTipo().equals(propiedadP.getPropiedadTipo())) {
            propiedadP.setPropiedadTipo(propiedadC.getPropiedadTipo());
        }
        if (!propiedadC.getM2().equals(propiedadP.getM2())) {
            propiedadP.setHabitaciones(propiedadC.getHabitaciones());
        }
        if (!propiedadC.getHabitaciones().equals(propiedadP.getHabitaciones())) {
            propiedadP.setHabitaciones(propiedadC.getHabitaciones());
        }
        if (!propiedadC.getBanos().equals(propiedadP.getBanos())) {
            propiedadP.setBanos(propiedadC.getBanos());
        }
        if (!propiedadC.getCochera().equals(propiedadP.getCochera())) {
            propiedadP.setCochera(propiedadC.getCochera());
        }
        if (!propiedadC.getDireccion().equals(propiedadP.getDireccion())) {
            propiedadP.setDireccion(propiedadC.getDireccion());
        }

        if (!propiedadC.getDescripcion()
                .equals(propiedadP.getDescripcion())) {
            propiedadP.setDescripcion(propiedadC.getDescripcion());
        }

        if (!propiedadC.getFoto()
                .equals(propiedadP.getFoto())) {
            propiedadP.setFoto(propiedadC.getFoto());
        }

        if (!propiedadC.getInmobiliaria()
                .equals(propiedadP.getInmobiliaria())) {
            propiedadP.setInmobiliaria(propiedadC.getInmobiliaria());
        }
        return propiedadP;
    }
}
