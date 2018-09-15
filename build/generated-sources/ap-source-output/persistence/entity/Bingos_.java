package persistence.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import persistence.entity.Personas;
import persistence.entity.Programaciones;
import persistence.entity.Tablas;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-09-14T22:03:13")
@StaticMetamodel(Bingos.class)
public class Bingos_ { 

    public static volatile SingularAttribute<Bingos, Integer> estado;
    public static volatile SingularAttribute<Bingos, Programaciones> idProgramacion;
    public static volatile SingularAttribute<Bingos, Integer> id;
    public static volatile SingularAttribute<Bingos, Personas> idPersona;
    public static volatile SingularAttribute<Bingos, Tablas> idTabla;

}