
import MrHouse.entidades.Propiedad;
import MrHouse.repositorios.PropiedadRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class SearchbarControlador {

    @Autowired
    private PropiedadRepositorio propiedadRepositorio;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @PostMapping("/buscar")
    public String buscar(@RequestParam("tipoPropiedad") String tipoPropiedad, @RequestParam("transaccionPropiedad") String transaccionPropiedad, @RequestParam("provincia") String provincia, @RequestParam("precioMin") Double precioMin, @RequestParam("precioMax") Double precioMax, Model modelo) {
        List<Propiedad> propiedades = propiedadRepositorio.buscarPropiedades(tipoPropiedad, transaccionPropiedad, provincia, precioMin, precioMax);
        modelo.addAttribute("propiedades", propiedades);
        return "propiedades";
    }
}
