package persistence.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import persistence.entity.Dias;
import persistence.entity.Programaciones;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-09-11T22:42:59")
@StaticMetamodel(Horarios.class)
public class Horarios_ { 

    public static volatile SingularAttribute<Horarios, String> horaFin;
    public static volatile ListAttribute<Horarios, Programaciones> programacionesList;
    public static volatile SingularAttribute<Horarios, Dias> idDia;
    public static volatile SingularAttribute<Horarios, Integer> id;
    public static volatile SingularAttribute<Horarios, String> horaInicio;

}