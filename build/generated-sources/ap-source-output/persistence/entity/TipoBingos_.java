package persistence.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import persistence.entity.Programaciones;
import persistence.entity.ValorBingos;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-09-14T22:03:12")
@StaticMetamodel(TipoBingos.class)
public class TipoBingos_ { 

    public static volatile SingularAttribute<TipoBingos, String> descripcion;
    public static volatile ListAttribute<TipoBingos, ValorBingos> valorBingosList;
    public static volatile SingularAttribute<TipoBingos, Integer> estado;
    public static volatile ListAttribute<TipoBingos, Programaciones> programacionesList;
    public static volatile SingularAttribute<TipoBingos, Integer> id;
    public static volatile SingularAttribute<TipoBingos, String> nombre;

}