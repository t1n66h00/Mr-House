/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MrHouse.entidades;

import MrHouse.enumeraciones.Cochera;
import MrHouse.enumeraciones.PropiedadTipo;
import MrHouse.enumeraciones.ProvinciaEnum;
import MrHouse.enumeraciones.TransaccionPropiedad;
import com.sun.istack.NotNull;
import java.util.Optional;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author facuq
 */
@Entity
@Table(name = "propiedad")
public class Propiedad {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @Column(unique = true)


    private Double precio;
    @NotNull
    @Enumerated(EnumType.STRING)
    private PropiedadTipo propiedadTipo; //1=CASA 2=DEPARTAMENTO   
    private String m2;
    private String habitaciones;
    private String banos;
    @NotNull
    @Enumerated(EnumType.STRING)
    private Cochera cochera;
    private String direccion;
    private String descripcion;
    @NotNull
    @Enumerated(EnumType.STRING)
    private TransaccionPropiedad transaccionPropiedad; //Tipo de transacción: Venta o Alquiler
    
    @NotNull
    @Enumerated(EnumType.STRING)
    private ProvinciaEnum provincias;

    private boolean alta;

    @OneToOne
    private Foto foto;

    public Propiedad() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getM2() {
        return m2;
    }

    public void setM2(String m2) {
        this.m2 = m2;
    }

    public String getBanos() {
        return banos;
    }

    public void setBanos(String baños) {
        this.banos = baños;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public String getHabitaciones() {
        return habitaciones;
    }

    public void setHabitaciones(String habitaciones) {
        this.habitaciones = habitaciones;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Foto getFoto() {
        return foto;
    }

    public void setFoto(Foto foto) {
        this.foto = foto;
    }

    public boolean isAlta() {
        return alta;
    }

    public void setAlta(boolean alta) {
        this.alta = alta;
    }

    public void setAlta(Optional<Boolean> alta) {
        this.alta = alta.orElse(false);
    }
    
    public ProvinciaEnum getProvincias() {
        return provincias;
    }

    public void setProvincias(ProvinciaEnum provincias) {
        this.provincias = provincias;
    }

    public PropiedadTipo getPropiedadTipo() {
        return propiedadTipo;
    }

    public void setPropiedadTipo(PropiedadTipo propiedadTipo) {
        this.propiedadTipo = propiedadTipo;
    }

    public Cochera getCochera() {
        return cochera;
    }

    public void setCochera(Cochera cochera) {
        this.cochera = cochera;
    }

    public TransaccionPropiedad getTransaccionPropiedad() {
        return transaccionPropiedad;
    }

    public void setTransaccionPropiedad(TransaccionPropiedad transaccionPropiedad) {
        this.transaccionPropiedad = transaccionPropiedad;
    }
    
}
