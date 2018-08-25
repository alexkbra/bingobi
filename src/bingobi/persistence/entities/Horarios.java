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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author kbra
 */
@Entity
@Table(name = "horarios")
@NamedQueries({
    @NamedQuery(name = "Horarios.findAll", query = "SELECT h FROM Horarios h")
    , @NamedQuery(name = "Horarios.findById", query = "SELECT h FROM Horarios h WHERE h.id = :id")
    , @NamedQuery(name = "Horarios.findByHoraInicio", query = "SELECT h FROM Horarios h WHERE h.horaInicio = :horaInicio")
    , @NamedQuery(name = "Horarios.findByHoraFin", query = "SELECT h FROM Horarios h WHERE h.horaFin = :horaFin")})
public class Horarios implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "HORA_INICIO")
    private String horaInicio;
    @Basic(optional = false)
    @Column(name = "HORA_FIN")
    private String horaFin;
    @JoinColumn(name = "ID_DIA", referencedColumnName = "ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Dias idDia;
    @OneToMany(mappedBy = "idHorario", fetch = FetchType.LAZY)
    private List<Programaciones> programacionesList;

    public Horarios() {
    }

    public Horarios(Integer id) {
        this.id = id;
    }

    public Horarios(Integer id, String horaInicio, String horaFin) {
        this.id = id;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    public String getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(String horaFin) {
        this.horaFin = horaFin;
    }

    public Dias getIdDia() {
        return idDia;
    }

    public void setIdDia(Dias idDia) {
        this.idDia = idDia;
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
        if (!(object instanceof Horarios)) {
            return false;
        }
        Horarios other = (Horarios) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bingobi.persistence.entities.Horarios[ id=" + id + " ]";
    }
    
}
