/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistence.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author kbra
 */
@Entity
@Table(name = "tipo_bingos")
@NamedQueries({
    @NamedQuery(name = "TipoBingos.findAll", query = "SELECT t FROM TipoBingos t")
    , @NamedQuery(name = "TipoBingos.findById", query = "SELECT t FROM TipoBingos t WHERE t.id = :id")
    , @NamedQuery(name = "TipoBingos.findByNombre", query = "SELECT t FROM TipoBingos t WHERE t.nombre = :nombre")
    , @NamedQuery(name = "TipoBingos.findByDescripcion", query = "SELECT t FROM TipoBingos t WHERE t.descripcion = :descripcion")
    , @NamedQuery(name = "TipoBingos.findByEstado", query = "SELECT t FROM TipoBingos t WHERE t.estado = :estado")})
public class TipoBingos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "NOMBRE")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Basic(optional = false)
    @Column(name = "ESTADO")
    private int estado;
    @OneToMany(mappedBy = "idTipoBingo", fetch = FetchType.LAZY)
    private List<ValorBingos> valorBingosList;
    @OneToMany(mappedBy = "idTipoBingo", fetch = FetchType.LAZY)
    private List<Programaciones> programacionesList;

    public TipoBingos() {
    }

    public TipoBingos(Integer id) {
        this.id = id;
    }

    public TipoBingos(Integer id, String nombre, String descripcion, int estado) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.estado = estado;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public List<ValorBingos> getValorBingosList() {
        return valorBingosList;
    }

    public void setValorBingosList(List<ValorBingos> valorBingosList) {
        this.valorBingosList = valorBingosList;
    }

    public List<Programaciones> getProgramacionesList() {
        return programacionesList;
    }

    public void setProgramacionesList(List<Programaciones> programacionesList) {
        this.programacionesList = programacionesList;
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
        if (!(object instanceof TipoBingos)) {
            return false;
        }
        TipoBingos other = (TipoBingos) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "persistence.entity.TipoBingos[ id=" + id + " ]";
    }
    
}
