/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bingobi.persistence.entities;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author kbra
 */
@Entity
@Table(name = "programaciones")
@NamedQueries({
    @NamedQuery(name = "Programaciones.findAll", query = "SELECT p FROM Programaciones p")
    , @NamedQuery(name = "Programaciones.findById", query = "SELECT p FROM Programaciones p WHERE p.id = :id")
    , @NamedQuery(name = "Programaciones.findByFecha", query = "SELECT p FROM Programaciones p WHERE p.fecha = :fecha")
    , @NamedQuery(name = "Programaciones.findByCantidadGanadores", query = "SELECT p FROM Programaciones p WHERE p.cantidadGanadores = :cantidadGanadores")
    , @NamedQuery(name = "Programaciones.findByCantidadPremio", query = "SELECT p FROM Programaciones p WHERE p.cantidadPremio = :cantidadPremio")
    , @NamedQuery(name = "Programaciones.findByCantidadTablas", query = "SELECT p FROM Programaciones p WHERE p.cantidadTablas = :cantidadTablas")
    , @NamedQuery(name = "Programaciones.findByEstado", query = "SELECT p FROM Programaciones p WHERE p.estado = :estado")})
public class Programaciones implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "FECHA")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Basic(optional = false)
    @Column(name = "CANTIDAD_GANADORES")
    private String cantidadGanadores;
    @Basic(optional = false)
    @Column(name = "CANTIDAD_PREMIO")
    private int cantidadPremio;
    @Basic(optional = false)
    @Column(name = "CANTIDAD_TABLAS")
    private int cantidadTablas;
    @Basic(optional = false)
    @Column(name = "ESTADO")
    private int estado;
    @OneToMany(mappedBy = "idProgramacion", fetch = FetchType.LAZY)
    private List<Bingos> bingosList;
    @JoinColumn(name = "ID_HORARIO", referencedColumnName = "ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Horarios idHorario;
    @JoinColumn(name = "ID_SEDE", referencedColumnName = "ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Sedes idSede;
    @JoinColumn(name = "ID_TIPO_BINGO", referencedColumnName = "ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private TipoBingos idTipoBingo;
    @OneToMany(mappedBy = "idProgramacion", fetch = FetchType.LAZY)
    private List<ControlProgramación> controlProgramaciónList;

    public Programaciones() {
    }

    public Programaciones(Integer id) {
        this.id = id;
    }

    public Programaciones(Integer id, Date fecha, String cantidadGanadores, int cantidadPremio, int cantidadTablas, int estado) {
        this.id = id;
        this.fecha = fecha;
        this.cantidadGanadores = cantidadGanadores;
        this.cantidadPremio = cantidadPremio;
        this.cantidadTablas = cantidadTablas;
        this.estado = estado;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getCantidadGanadores() {
        return cantidadGanadores;
    }

    public void setCantidadGanadores(String cantidadGanadores) {
        this.cantidadGanadores = cantidadGanadores;
    }

    public int getCantidadPremio() {
        return cantidadPremio;
    }

    public void setCantidadPremio(int cantidadPremio) {
        this.cantidadPremio = cantidadPremio;
    }

    public int getCantidadTablas() {
        return cantidadTablas;
    }

    public void setCantidadTablas(int cantidadTablas) {
        this.cantidadTablas = cantidadTablas;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public List<Bingos> getBingosList() {
        return bingosList;
    }

    public void setBingosList(List<Bingos> bingosList) {
        this.bingosList = bingosList;
    }

    public Horarios getIdHorario() {
        return idHorario;
    }

    public void setIdHorario(Horarios idHorario) {
        this.idHorario = idHorario;
    }

    public Sedes getIdSede() {
        return idSede;
    }

    public void setIdSede(Sedes idSede) {
        this.idSede = idSede;
    }

    public TipoBingos getIdTipoBingo() {
        return idTipoBingo;
    }

    public void setIdTipoBingo(TipoBingos idTipoBingo) {
        this.idTipoBingo = idTipoBingo;
    }

    public List<ControlProgramación> getControlProgramaciónList() {
        return controlProgramaciónList;
    }

    public void setControlProgramaciónList(List<ControlProgramación> controlProgramaciónList) {
        this.controlProgramaciónList = controlProgramaciónList;
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
        if (!(object instanceof Programaciones)) {
            return false;
        }
        Programaciones other = (Programaciones) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bingobi.persistence.entities.Programaciones[ id=" + id + " ]";
    }
    
}
