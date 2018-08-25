/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bingobi.persistence.entities;

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
    , @NamedQuery(name = "TipoBingos.findByValor", query = "SELECT t FROM TipoBingos t WHERE t.valor = :valor")
    , @NamedQuery(name = "TipoBingos.findByValorPremio", query = "SELECT t FROM TipoBingos t WHERE t.valorPremio = :valorPremio")})
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
    @Column(name = "VALOR")
    private int valor;
    @Basic(optional = false)
    @Column(name = "VALOR_PREMIO")
    private int valorPremio;
    @OneToMany(mappedBy = "idTipoBingo", fetch = FetchType.LAZY)
    private List<Programaciones> programacionesList;

    public TipoBingos() {
    }

    public TipoBingos(Integer id) {
        this.id = id;
    }

    public TipoBingos(Integer id, String nombre, int valor, int valorPremio) {
        this.id = id;
        this.nombre = nombre;
        this.valor = valor;
        this.valorPremio = valorPremio;
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

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public int getValorPremio() {
        return valorPremio;
    }

    public void setValorPremio(int valorPremio) {
        this.valorPremio = valorPremio;
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
        return "bingobi.persistence.entities.TipoBingos[ id=" + id + " ]";
    }
    
}
