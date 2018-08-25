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
@Table(name = "control_programaci\u00f3n")
@NamedQueries({
    @NamedQuery(name = "ControlProgramaci\u00f3n.findAll", query = "SELECT c FROM ControlProgramaci\u00f3n c")
    , @NamedQuery(name = "ControlProgramaci\u00f3n.findById", query = "SELECT c FROM ControlProgramaci\u00f3n c WHERE c.id = :id")
    , @NamedQuery(name = "ControlProgramaci\u00f3n.findByNumero", query = "SELECT c FROM ControlProgramaci\u00f3n c WHERE c.numero = :numero")
    , @NamedQuery(name = "ControlProgramaci\u00f3n.findByEstado", query = "SELECT c FROM ControlProgramaci\u00f3n c WHERE c.estado = :estado")})
public class ControlProgramación implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "NUMERO")
    private String numero;
    @Basic(optional = false)
    @Column(name = "ESTADO")
    private int estado;
    @JoinColumn(name = "ID_PROGRAMACION", referencedColumnName = "ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Programaciones idProgramacion;

    public ControlProgramación() {
    }

    public ControlProgramación(Integer id) {
        this.id = id;
    }

    public ControlProgramación(Integer id, String numero, int estado) {
        this.id = id;
        this.numero = numero;
        this.estado = estado;
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

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public Programaciones getIdProgramacion() {
        return idProgramacion;
    }

    public void setIdProgramacion(Programaciones idProgramacion) {
        this.idProgramacion = idProgramacion;
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
        if (!(object instanceof ControlProgramación)) {
            return false;
        }
        ControlProgramación other = (ControlProgramación) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bingobi.persistence.entities.ControlProgramaci\u00f3n[ id=" + id + " ]";
    }
    
}
