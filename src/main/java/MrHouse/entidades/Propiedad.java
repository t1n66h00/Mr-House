/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MrHouse.entidades;

import java.util.Optional;
import javax.persistence.Column;
import javax.persistence.Entity;
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
    private String propiedadTipo; //1=CASA 2=DEPARTAMENTO   
    private String m2;
    private String habitaciones;
    private String banos;
    private boolean cochera;
    private String direccion;
    private String descripcion;
    private String transaccionPropiedad; //Tipo de transacción: Venta o Alquiler
    private String provincia;

    private boolean alta;

    @OneToOne
    private Foto foto;
    @OneToOne
    private Inmobiliaria inmobiliaria;
    @OneToOne
    private Propietario propietario;

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

    public String getPropiedadTipo() {
        return propiedadTipo;
    }

    public void setPropiedadTipo(String propiedadTipo) {
        this.propiedadTipo = propiedadTipo;
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

    public Inmobiliaria getInmobiliaria() {
        return inmobiliaria;
    }

    public void setInmobiliaria(Inmobiliaria inmobiliaria) {
        this.inmobiliaria = inmobiliaria;
    }

    public Propietario getPropietario() {
        return propietario;
    }

    public void setPropietario(Propietario propietario) {
        this.propietario = propietario;
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
    
    public boolean isCochera() {
        return cochera;
    }

    public void setCochera(boolean cochera) {
        this.cochera = cochera;
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

    public String getTransaccionPropiedad() {
        return transaccionPropiedad;
    }

    public void setTransaccionPropiedad(String transaccionPropiedad) {
        this.transaccionPropiedad = transaccionPropiedad;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }
}
