/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MrHouse.repositorios;

import MrHouse.entidades.Propiedad;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author facuq
 */
public interface PropiedadRepositorio extends JpaRepository<Propiedad, String> {

    @Query("SELECT u FROM Propiedad u WHERE u.id = :id")

    public Propiedad buscarPorID(@Param("id") String id);

    @Query("SELECT u FROM Propiedad u WHERE u.provincia = :provincia")

    List<Propiedad> buscarPorCiudad(@Param("provincia") String provincia);

    @Query("SELECT u FROM Propiedad u WHERE u.provincia = :provincia")

    public Propiedad buscarPorAlta(@Param("provincia") String provincia);

    @Query("SELECT p from Propiedad p WHERE p.alta = true")
    List<Propiedad> buscaActivos(@Param("activos") String activos);

    @Query("SELECT p FROM Propiedad p WHERE p.propiedadTipo = :tipoPropiedad AND p.transaccionPropiedad = :transaccionPropiedad AND p.provincia = :provincia AND p.precio BETWEEN :precioMin AND :precioMax")
    List<Propiedad> buscarPropiedades(@Param("tipoPropiedad") String tipoPropiedad, @Param("transaccionPropiedad") String transaccionPropiedad, @Param("provincia") String provincia, @Param("precioMin") Double precioMin, @Param("precioMax") Double precioMax);
}
