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
@Table(name = "cartones")
@NamedQueries({
    @NamedQuery(name = "Cartones.findAll", query = "SELECT c FROM Cartones c")
    , @NamedQuery(name = "Cartones.findById", query = "SELECT c FROM Cartones c WHERE c.id = :id")
    , @NamedQuery(name = "Cartones.findByB", query = "SELECT c FROM Cartones c WHERE c.b = :b")
    , @NamedQuery(name = "Cartones.findByI", query = "SELECT c FROM Cartones c WHERE c.i = :i")
    , @NamedQuery(name = "Cartones.findByN", query = "SELECT c FROM Cartones c WHERE c.n = :n")
    , @NamedQuery(name = "Cartones.findByG", query = "SELECT c FROM Cartones c WHERE c.g = :g")
    , @NamedQuery(name = "Cartones.findByO", query = "SELECT c FROM Cartones c WHERE c.o = :o")})
public class Cartones implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "B")
    private String b;
    @Basic(optional = false)
    @Column(name = "I")
    private String i;
    @Basic(optional = false)
    @Column(name = "N")
    private String n;
    @Basic(optional = false)
    @Column(name = "G")
    private String g;
    @Basic(optional = false)
    @Column(name = "O")
    private String o;
    @JoinColumn(name = "ID_TABLA", referencedColumnName = "ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Tablas idTabla;

    public Cartones() {
    }

    public Cartones(Integer id) {
        this.id = id;
    }

    public Cartones(Integer id, String b, String i, String n, String g, String o) {
        this.id = id;
        this.b = b;
        this.i = i;
        this.n = n;
        this.g = g;
        this.o = o;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getB() {
        return b;
    }

    public void setB(String b) {
        this.b = b;
    }

    public String getI() {
        return i;
    }

    public void setI(String i) {
        this.i = i;
    }

    public String getN() {
        return n;
    }

    public void setN(String n) {
        this.n = n;
    }

    public String getG() {
        return g;
    }

    public void setG(String g) {
        this.g = g;
    }

    public String getO() {
        return o;
    }

    public void setO(String o) {
        this.o = o;
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
        if (!(object instanceof Cartones)) {
            return false;
        }
        Cartones other = (Cartones) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bingobi.persistence.entities.Cartones[ id=" + id + " ]";
    }
    
}
