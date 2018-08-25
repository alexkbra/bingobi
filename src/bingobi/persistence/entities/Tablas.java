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
@Table(name = "tablas")
@NamedQueries({
    @NamedQuery(name = "Tablas.findAll", query = "SELECT t FROM Tablas t")
    , @NamedQuery(name = "Tablas.findById", query = "SELECT t FROM Tablas t WHERE t.id = :id")
    , @NamedQuery(name = "Tablas.findByNumero", query = "SELECT t FROM Tablas t WHERE t.numero = :numero")})
public class Tablas implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "NUMERO")
    private String numero;
    @OneToMany(mappedBy = "idTabla", fetch = FetchType.LAZY)
    private List<Bingos> bingosList;
    @OneToMany(mappedBy = "idTabla", fetch = FetchType.LAZY)
    private List<Cartones> cartonesList;

    public Tablas() {
    }

    public Tablas(Integer id) {
        this.id = id;
    }

    public Tablas(Integer id, String numero) {
        this.id = id;
        this.numero = numero;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public List<Bingos> getBingosList() {
        return bingosList;
    }

    public void setBingosList(List<Bingos> bingosList) {
        this.bingosList = bingosList;
    }

    public List<Cartones> getCartonesList() {
        return cartonesList;
    }

    public void setCartonesList(List<Cartones> cartonesList) {
        this.cartonesList = cartonesList;
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
        if (!(object instanceof Tablas)) {
            return false;
        }
        Tablas other = (Tablas) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bingobi.persistence.entities.Tablas[ id=" + id + " ]";
    }
    
}
