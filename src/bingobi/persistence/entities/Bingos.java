/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bingobi.persistence.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author kbra
 */
@Entity
@Table(name = "bingos")
@NamedQueries({
    @NamedQuery(name = "Bingos.findAll", query = "SELECT b FROM Bingos b")
    , @NamedQuery(name = "Bingos.findById", query = "SELECT b FROM Bingos b WHERE b.id = :id")
    , @NamedQuery(name = "Bingos.findByEstado", query = "SELECT b FROM Bingos b WHERE b.estado = :estado")})
public class Bingos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "ESTADO")
    private int estado;
    @JoinColumn(name = "ID_PERSONA", referencedColumnName = "ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Personas idPersona;
    @JoinColumn(name = "ID_PROGRAMACION", referencedColumnName = "ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Programaciones idProgramacion;
    @JoinColumn(name = "ID_TABLA", referencedColumnName = "ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Tablas idTabla;

    public Bingos() {
    }

    public Bingos(Integer id) {
        this.id = id;
    }

    public Bingos(Integer id, int estado) {
        this.id = id;
        this.estado = estado;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public Personas getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(Personas idPersona) {
        this.idPersona = idPersona;
    }

    public Programaciones getIdProgramacion() {
        return idProgramacion;
    }

    public void setIdProgramacion(Programaciones idProgramacion) {
        this.idProgramacion = idProgramacion;
    }

    public Tablas getIdTabla() {
        return idTabla;
    }

    public void setIdTabla(Tablas idTabla) {
        this.idTabla = idTabla;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Bingos)) {
            return false;
        }
        Bingos other = (Bingos) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bingobi.persistence.entities.Bingos[ id=" + id + " ]";
    }
    
}
