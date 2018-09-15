package persistence.entity;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import persistence.entity.Bingos;
import persistence.entity.TipoDocumento;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-09-14T22:03:13")
@StaticMetamodel(Personas.class)
public class Personas_ { 

    public static volatile SingularAttribute<Personas, String> segundoNombre;
    public static volatile SingularAttribute<Personas, String> primerNombre;
    public static volatile SingularAttribute<Personas, String> primerApellido;
    public static volatile ListAttribute<Personas, Bingos> bingosList;
    public static volatile SingularAttribute<Personas, String> genero;
    public static volatile SingularAttribute<Personas, String> direccion;
    public static volatile SingularAttribute<Personas, String> documento;
    public static volatile SingularAttribute<Personas, String> segundoApellido;
    public static volatile SingularAttribute<Personas, TipoDocumento> idTipodocumento;
    public static volatile SingularAttribute<Personas, Integer> id;
    public static volatile SingularAttribute<Personas, String> telefono;
    public static volatile SingularAttribute<Personas, Date> fechaN;

}