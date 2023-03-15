/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MrHouse.controladores;

import MrHouse.entidades.Cliente;
import MrHouse.entidades.Inmobiliaria;
import MrHouse.enumeraciones.ProvinciaEnum;
import MrHouse.excepciones.MyException;
import MrHouse.servicios.InmobiliariaServicios;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Ramiro
 */
@Controller
@RequestMapping("/inmobiliaria")
public class InmobiliariaControlador {

    @Autowired
    private InmobiliariaServicios inmobiliariaServicios;

    @GetMapping("/registrar")
    public String registrar(ModelMap modelo) {
        modelo.addAttribute("provincias", ProvinciaEnum.values());
        return "registro.html";
    }

    @PostMapping("/registro")
    public String registro(@ModelAttribute("provincias")ProvinciaEnum provincias, @RequestParam String nombre, @RequestParam String email, @RequestParam String password,
            @RequestParam String password2, ModelMap modelo, MultipartFile archivo) {
        try {
            inmobiliariaServicios.registrar(archivo, nombre, email, password, password2, provincias);
            modelo.put("exito", "Inmobiliaria registrada correctamente");
            return "index.html";
        } catch (MyException ex) {
            modelo.put("error", ex.getMessage());
            modelo.put("nombre", nombre);
            modelo.put("email", email);
            return "registro.html";
        }
    }

    @GetMapping("/ingresar")
    public String login(@RequestParam(required = false) String error, ModelMap modelo) {
        if (error != null) {
            modelo.put("error", "Usuario o contraseña invalidas");
        }
        return "ingreso.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_INQUILINO','ROLE_ADMIN','ROLE_PROPIETARIO','ROLE_INMOBILIARIA')")
    @GetMapping("/inicio")
    public String inicio(HttpSession session) {
        Inmobiliaria logueadoI = (Inmobiliaria) session.getAttribute("inmobiliariasession");

        return "index.html";
    }

}
