package persistence.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import persistence.entity.Bingos;
import persistence.entity.Cartones;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-09-11T22:42:59")
@StaticMetamodel(Tablas.class)
public class Tablas_ { 

    public static volatile SingularAttribute<Tablas, String> numero;
    public static volatile ListAttribute<Tablas, Bingos> bingosList;
    public static volatile ListAttribute<Tablas, Cartones> cartonesList;
    public static volatile SingularAttribute<Tablas, Integer> id;

}