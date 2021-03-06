/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistence.entity;

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
@Table(name = "personas")
@NamedQueries({
    @NamedQuery(name = "Personas.findAll", query = "SELECT p FROM Personas p")
    , @NamedQuery(name = "Personas.findById", query = "SELECT p FROM Personas p WHERE p.id = :id")
    , @NamedQuery(name = "Personas.findByDocumento", query = "SELECT p FROM Personas p WHERE p.documento = :documento")
    , @NamedQuery(name = "Personas.findByPrimerNombre", query = "SELECT p FROM Personas p WHERE p.primerNombre = :primerNombre")
    , @NamedQuery(name = "Personas.findBySegundoNombre", query = "SELECT p FROM Personas p WHERE p.segundoNombre = :segundoNombre")
    , @NamedQuery(name = "Personas.findByPrimerApellido", query = "SELECT p FROM Personas p WHERE p.primerApellido = :primerApellido")
    , @NamedQuery(name = "Personas.findBySegundoApellido", query = "SELECT p FROM Personas p WHERE p.segundoApellido = :segundoApellido")
    , @NamedQuery(name = "Personas.findByTelefono", query = "SELECT p FROM Personas p WHERE p.telefono = :telefono")
    , @NamedQuery(name = "Personas.findByDireccion", query = "SELECT p FROM Personas p WHERE p.direccion = :direccion")
    , @NamedQuery(name = "Personas.findByGenero", query = "SELECT p FROM Personas p WHERE p.genero = :genero")
    , @NamedQuery(name = "Personas.findByFechaN", query = "SELECT p FROM Personas p WHERE p.fechaN = :fechaN")})
public class Personas implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "DOCUMENTO")
    private String documento;
    @Basic(optional = false)
    @Column(name = "PRIMER_NOMBRE")
    private String primerNombre;
    @Basic(optional = false)
    @Column(name = "SEGUNDO_NOMBRE")
    private String segundoNombre;
    @Basic(optional = false)
    @Column(name = "PRIMER_APELLIDO")
    private String primerApellido;
    @Basic(optional = false)
    @Column(name = "SEGUNDO_APELLIDO")
    private String segundoApellido;
    @Basic(optional = false)
    @Column(name = "TELEFONO")
    private String telefono;
    @Basic(optional = false)
    @Column(name = "DIRECCION")
    private String direccion;
    @Basic(optional = false)
    @Column(name = "GENERO")
    private String genero;
    @Column(name = "FECHA_N")
    @Temporal(TemporalType.DATE)
    private Date fechaN;
    @OneToMany(mappedBy = "idPersona", fetch = FetchType.LAZY)
    private List<Bingos> bingosList;
    @JoinColumn(name = "ID_TIPODOCUMENTO", referencedColumnName = "ID")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private TipoDocumento idTipodocumento;

    public Personas() {
    }

    public Personas(Integer id) {
        this.id = id;
    }

    public Personas(Integer id, String documento, String primerNombre, String segundoNombre, String primerApellido, String segundoApellido, String telefono, String direccion, String genero) {
        this.id = id;
        this.documento = documento;
        this.primerNombre = primerNombre;
        this.segundoNombre = segundoNombre;
        this.primerApellido = primerApellido;
        this.segundoApellido = segundoApellido;
        this.telefono = telefono;
        this.direccion = direccion;
        this.genero = genero;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getPrimerNombre() {
        return primerNombre;
    }

    public void setPrimerNombre(String primerNombre) {
        this.primerNombre = primerNombre;
    }

    public String getSegundoNombre() {
        return segundoNombre;
    }

    public void setSegundoNombre(String segundoNombre) {
        this.segundoNombre = segundoNombre;
    }

    public String getPrimerApellido() {
        return primerApellido;
    }

    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    public String getSegundoApellido() {
        return segundoApellido;
    }

    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public Date getFechaN() {
        return fechaN;
    }

    public void setFechaN(Date fechaN) {
        this.fechaN = fechaN;
    }

    public List<Bingos> getBingosList() {
        return bingosList;
    }

    public void setBingosList(List<Bingos> bingosList) {
        this.bingosList = bingosList;
    }

    public TipoDocumento getIdTipodocumento() {
        return idTipodocumento;
    }

    public void setIdTipodocumento(TipoDocumento idTipodocumento) {
        this.idTipodocumento = idTipodocumento;
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
        if (!(object instanceof Personas)) {
            return false;
        }
        Personas other = (Personas) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "persistence.entity.Personas[ id=" + id + " ]";
    }
    
}
