package persistence.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import persistence.entity.Personas;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-09-11T22:42:59")
@StaticMetamodel(TipoDocumento.class)
public class TipoDocumento_ { 

    public static volatile SingularAttribute<TipoDocumento, String> descripcion;
    public static volatile SingularAttribute<TipoDocumento, String> tipoDocumento;
    public static volatile ListAttribute<TipoDocumento, Personas> personasList;
    public static volatile SingularAttribute<TipoDocumento, Integer> id;

}