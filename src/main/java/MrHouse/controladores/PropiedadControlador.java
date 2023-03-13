/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MrHouse.controladores;

import MrHouse.entidades.Propiedad;
import MrHouse.excepciones.MyException;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestParam;
import MrHouse.servicios.PropiedadServicios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author facuq
 */
@Controller
@RequestMapping("/propiedades")
public class PropiedadControlador {

    @Autowired
    private PropiedadServicios propiedadServicios;
    
    @GetMapping("/")
    public String propiedades() {
        return "propiedades.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_INQUILINO','ROLE_INMOBILIARIA')")
    @GetMapping("/publicar")
    public String publicar() {
        return "publicar.html";
    }

    @PostMapping("/publicado")
    public String registro(@RequestParam String provincia, @RequestParam String transaccionPropiedad, @RequestParam Double precio, @RequestParam String propiedadTipo, @RequestParam String m2, @RequestParam String habitaciones, @RequestParam String banos, @RequestParam boolean cochera, @RequestParam String direccion, @RequestParam String descripcion, ModelMap modelo, MultipartFile archivo) {

        Propiedad propiedadV = new Propiedad();
        propiedadV.setPrecio(precio);
        propiedadV.setPropiedadTipo(propiedadTipo);
        propiedadV.setM2(m2);
        propiedadV.setHabitaciones(habitaciones);
        propiedadV.setBanos(banos);
        propiedadV.setCochera(cochera);
        propiedadV.setDireccion(direccion);
        propiedadV.setDescripcion(descripcion);
        propiedadV.setDescripcion(descripcion);
        propiedadV.setTransaccionPropiedad(transaccionPropiedad);
        propiedadV.setProvincia(provincia);
        try {
            propiedadServicios.registrar(archivo, propiedadV);
            modelo.put("exito", "Se publicó la propiedad correctamente");
            return "propiedades.html";
        } catch (MyException ex) {
            modelo.put("error", ex.getMessage());
            modelo.put("precio", precio);
            modelo.put("propiedadTipo", propiedadTipo);
            modelo.put("m2", m2);
            modelo.put("habitaciones", habitaciones);
            modelo.put("banos", banos);
            modelo.put("cochera", cochera);
            modelo.put("direccion", direccion);
            modelo.put("descripcion", descripcion);
            return "publicar.html";
        }
    }
}
