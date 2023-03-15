/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MrHouse.repositorios;


import MrHouse.entidades.Inmobiliaria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author facuq
 */
@Repository
public interface InmobiliariaRepositorio extends JpaRepository<Inmobiliaria, String> {

    @Query("SELECT u FROM Inmobiliaria u WHERE u.id = :id")

    public Inmobiliaria buscarPorID(@Param("id") String id);
    
    @Query("SELECT u FROM Inmobiliaria u WHERE u.email = :email")
    
    public Inmobiliaria buscarPorEmail(@Param("email") String email);

}
