/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MrHouse.repositorios;

import MrHouse.entidades.Propiedad;
import MrHouse.enumeraciones.ProvinciaEnum;
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

    @Query("SELECT u FROM Propiedad u WHERE u.provincias = :provincias")
    List<Propiedad> buscarPorCiudad(@Param("provincias") String provincias);

    @Query("SELECT u FROM Propiedad u WHERE u.provincias = :provincias")

    public Propiedad buscarPorAlta(@Param("provincias") ProvinciaEnum provincias);

    @Query("SELECT p from Propiedad p WHERE p.alta = true")
    List<Propiedad> buscaActivos(@Param("activos") String activos);

    @Query("SELECT p FROM Propiedad p WHERE p.propiedadTipo = :tipoPropiedad AND p.transaccionPropiedad = :transaccionPropiedad AND p.provincias = :provincias AND p.precio BETWEEN :precioMin AND :precioMax")
    List<Propiedad> buscarPropiedades(@Param("tipoPropiedad") String tipoPropiedad, @Param("transaccionPropiedad") String transaccionPropiedad, @Param("provincias") String provincias, @Param("precioMin") Double precioMin, @Param("precioMax") Double precioMax);
}
