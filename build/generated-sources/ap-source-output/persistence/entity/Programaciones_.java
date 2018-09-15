package persistence.entity;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import persistence.entity.Bingos;
import persistence.entity.ControlProgramación;
import persistence.entity.Horarios;
import persistence.entity.Sedes;
import persistence.entity.TipoBingos;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-09-14T22:03:12")
@StaticMetamodel(Programaciones.class)
public class Programaciones_ { 

    public static volatile SingularAttribute<Programaciones, Integer> estado;
    public static volatile SingularAttribute<Programaciones, Horarios> idHorario;
    public static volatile SingularAttribute<Programaciones, Integer> cantidadPremio;
    public static volatile SingularAttribute<Programaciones, TipoBingos> idTipoBingo;
    public static volatile SingularAttribute<Programaciones, String> cantidadGanadores;
    public static volatile SingularAttribute<Programaciones, String> horaIni;
    public static volatile SingularAttribute<Programaciones, String> horaFin;
    public static volatile SingularAttribute<Programaciones, Date> fecha;
    public static volatile SingularAttribute<Programaciones, Integer> cantidadTablas;
    public static volatile ListAttribute<Programaciones, Bingos> bingosList;
    public static volatile SingularAttribute<Programaciones, Integer> id;
    public static volatile SingularAttribute<Programaciones, Sedes> idSede;
    public static volatile ListAttribute<Programaciones, ControlProgramación> controlProgramaciónList;

}