//Source file: C:\\DOCPROYECTOFINDECARRERA\\PRUEBAKK\\sintitulo2\\src\\Repositorio\\GenericInterface.java

package Repositorio;
import com.poet.odmg.*;
import com.poet.odmg.util.*;
import com.poet.odmg.imp.*;
import java.util.*;
import org.odmg.QueryException;


public class GenericInterface extends DefiningScope implements Type
{

    public SetOfObject inherits;//Tipo module interfaces
    public SetOfObject derives;//Tipo module interfaces
    public SetOfObject baseOf;//tipo derivedInterface
    public TypeI metaobjop;//Se emplea para hacer el fordwardiong de las funciones q se precisen


    public GenericInterface()
    {

    }

    public void getDerives()
    {

    }

    public void getInherits()
    {

    }

    public String getName()
    {
        return  this.name;
    }


    public void setName(String nom)
    {
        this.name=nom;
    }

    public String getCommnet()
    {
        return this.Commets;
    }

    /**
     * @roseuid 3AB7EA5601F1
     */
    public void setComment(String con )
    {
        this.Commets=con;
    }
    /*   protected MetaobjectI MetaobjopFuncional()
     {
     return metaobjop;
     }Borrar cuando te acuerdes.*/
    //Metodo para añadir una clase,clase derivada, interfaz o interfaz derivada a UsedIn

    public void addUsedIn(Module c)
    {
        if ( this.metaobjop.usedIn == null )
        {
            this.metaobjop.usedIn = new SetOfObject();
        }
        this.metaobjop.usedIn.add( c );
        c.addIncludes(this);//relleno el inverso es decir el del module
    }
    //Definiremos los metodos para agregar a la relación Inherits y derives
    public void addInherits(ModuleInterfaces c)
    {
        if ( this.inherits == null )
        {
            this.inherits = new SetOfObject();
        }
        this.inherits.add( c );
    }
    public void addDerives(ModuleInterfaces c)
    {
        if ( this.derives == null )
        {
            this.derives = new SetOfObject();
        }
        this.derives.add( c );
    }
    //Metodo para rellenar Properties (inverso de type)
    public void addProperties(Relationship c)
    {
        if ( this.metaobjop.properties == null )
        {
            this.metaobjop.properties = new SetOfObject();
        }
        this.metaobjop.properties.add( c );
    }
    //Rellena el inverso para los atributos, aunque se puede juntar con el anterior
    public void addProperties(Attribute c)
    {
        if ( this.metaobjop.properties == null )
        {
            this.metaobjop.properties = new SetOfObject();
        }
        this.metaobjop.properties.add( c );
    }

    //Método que añade un objeto derivado a la relacion BaseOf.Solo se almacenan Objetos DerivedClass y DerivedInterfaz
    public void addBaseOf(GenericInterface c)
    {
        if ( this.baseOf == null )
        {
            this.baseOf = new SetOfObject();
        }
        this.baseOf.add( c );
    }

    //Añadir una operacion a la clase o a la interfaz.
    //Rellena el inverso para los atributos, aunque se puede juntar con el anterior
    public void addOperation(Operation c)
    {
        if ( this.metaobjop.operations == null )
        {
            this.metaobjop.operations = new SetOfObject();
        }
        this.metaobjop.operations.add( c );
    }

    //MÉTODO PARA RECUPERAR UNA CLASE O INTERFAZ, POR EL NOMBRE.
    public GenericInterface getGeneric(String param)
    {
        GenericInterface devolver=null;
        try
        {
            Object resultado;
            //Realizar esta función para cada uno de los objetos seleccionados
            Transaction tx2= new Transaction();
            Properties props = new Properties();
            props.put( "readLockMode", "NONE" );
            tx2.setProperties(props);
            tx2.begin();
            OQLQuery Query2 = new OQLQuery(tx2);
            Query2.create("SELECT a FROM GenericInterfaceExtent AS a WHERE a.name = $1");
            Query2.bind(param);
            String kk=Query2.toString();
            resultado = Query2.execute();
            BagOfObject relacion2=(BagOfObject) resultado;
            Iterator it= relacion2.iterator();
            while(it.hasNext())
            {
                devolver=(GenericInterface)it.next();
                System.out.println("Este es en getGeneric"+devolver.getName());

            }
            tx2.leave();
        }catch (QueryException ex)
        {
            ex.printStackTrace();
        }
        return devolver;
    }
    //Clases que se han introducido de la Clase clase
    //Metodo que devuelve si una clase extiende a otra o no, es booleana la funcion.

    public boolean isExtenderOf(GenericInterface C, Module M)
 //Devuelve true si la clase que llama a la funcion es extender de la clase que se pasa por paramatro.
    {
        BagOfObject extent= new BagOfObject();
        boolean devolver=false;
        Object resultado=null;

        try
        {
            Transaction tx= new Transaction();
            Properties props = new Properties();
            props.put( "readLockMode", "NONE" );
            tx.setProperties(props);
            tx.begin();
            OQLQuery Query = new OQLQuery(tx);
            Query.create("SELECT  a.CorrespondsToSubclasses FROM ModuleClassesExtent AS a  WHERE  a.CorrespondsToSubclasses.name = $1 AND  a.CorrespondsToSuperclasses.name = $2 AND a.InModule.name = $3 ");
            Query.bind(C.getName());//Blinda la subclase
            Query.bind(this.getName());//Blinda la superclase
            Query.bind(M.getName());//blinda el modulo
            String kk=Query.toString();
            resultado = Query.execute();
            extent=(BagOfObject) resultado;
            if(extent.isEmpty())
            {
                devolver=false;
            }
            if(!extent.isEmpty())
            {
                devolver= true;
            }
            tx.abort();
        }catch (QueryException ex)
        {
            ex.printStackTrace();
        }

        return devolver;//retorna si existe entre los dos relacion extent.
    }


    // Devuelve si una clase hereda comportamiento (ISA) de una inerfaz
    //K es el q actua de supertype
    public boolean isSubtypeOf(GenericInterface k, Module md)
    {
        BagOfObject isa= new BagOfObject();
        boolean devolver=false;
        Object resultado=null;

        try
        {
            Transaction tx= new Transaction();
            Properties props = new Properties();
            props.put( "readLockMode", "NONE" );
            tx.setProperties(props);
            tx.begin();
            OQLQuery Query = new OQLQuery(tx);
            Query.create("SELECT  a.CorrespondsToSupertypes FROM ModuleInterfacesExtent AS a  WHERE a.CorrespondsToSubtypes.name = $1 AND a.CorrespondsToSupertypes.name = $2 AND a.InModule.name = $3 ");
            Query.bind(this.getName());
            Query.bind(k.getName());
            Query.bind(md.getName());
            String kk=Query.toString();
            resultado = Query.execute();
            isa=(BagOfObject) resultado;

            if(!isa.isEmpty())
            {
                devolver=true;
            }
            tx.abort();
        }catch (QueryException ex)
        {
            ex.printStackTrace();
        }

        return devolver;//retorna falso si no existe relacion isa con la interfaz y verdadero si lo es.
    }
    //Metodo que devuelve los atributos de una clase o de una interfaz
    public SetOfObject getTypeAttributes(String param)
    {
        SetOfObject devolver = new SetOfObject();
        try
        {
            Object resultado;
            //Realizar esta función para cada uno de los objetos seleccionados
            Transaction tx2= new Transaction();
            Properties props = new Properties();
            props.put( "readLockMode", "NONE" );
            props.put( "shareThread", "true" );
            tx2.setProperties(props);
            tx2.begin();
            OQLQuery Query2 = new OQLQuery(tx2);
            Query2.create("SELECT a.metaobjop.properties FROM GenericInterfaceExtent AS a WHERE a.name =$1");
            Query2.bind(param);
            String kk=Query2.toString();
            resultado = Query2.execute();
            BagOfObject relacion2=(BagOfObject) resultado;
            Iterator it= relacion2.iterator();
            Property aux;
            while(it.hasNext())
            {
                aux=(Property)it.next();
                //OJO!! si las propiedades devueltas en la consulta anterior son Relationships tienen el nombre a "null"---> fallaria el isAttribute!!
                if(aux.getName()!=null)
                {
                    if((aux.isAttribute()) && (!devolver.contains(((Attribute)aux).getType())))
                    {
                        devolver.add(((Attribute)aux).getType());//Si es un Atributometo el tipo del q es en el Set para devolverlo!!
                    }
                }

            }
            tx2.leave();
        }catch (QueryException ex)
        {
            ex.printStackTrace();
        }
        return devolver;
    }

    //Método que devuelve los tipos que devuelven las operaciones.
    public SetOfObject getResultOperation(String param)
    {
        SetOfObject devolver = new SetOfObject();
        try
        {
            Object resultado;
            //Realizar esta función para cada uno de los objetos seleccionados
            Transaction tx2= new Transaction();
            Properties props = new Properties();
            props.put( "readLockMode", "NONE" );
            tx2.setProperties(props);
            tx2.begin();
            OQLQuery Query2 = new OQLQuery(tx2);
            Query2.create("SELECT a.metaobjop.operations FROM GenericInterfaceExtent AS a WHERE a.name =$1");
            Query2.bind(param);
            String kk=Query2.toString();
            resultado = Query2.execute();
            BagOfObject relacion2=(BagOfObject) resultado;
            Iterator it= relacion2.iterator();
            Operation aux;
            while(it.hasNext())
            {
                aux=(Operation)it.next();
                if(!devolver.contains(((Operation)aux).getResult()))
                {
                    devolver.add(((Operation)aux).getResult());//Meto el tipo de la operacion dentro de devolver!!
                }
            }
            tx2.leave();
        }catch (QueryException ex)
        {
            ex.printStackTrace();
        }
        return devolver;
    }
    //MÉTODO QUE RETORNA LAS OPERACIONES QUE CONTIENE UN OBJETO.
    static public SetOfObject getOperations(String param)
    {
        SetOfObject devolver = new SetOfObject();
        try
        {
            Object resultado;
            //Realizar esta función para cada uno de los objetos seleccionados
            Transaction tx2= new Transaction();
            Properties props = new Properties();
            props.put( "readLockMode", "NONE" );
            tx2.setProperties(props);
            tx2.begin();
            OQLQuery Query2 = new OQLQuery(tx2);
            Query2.create("SELECT a.metaobjop.operations FROM GenericInterfaceExtent AS a WHERE a.name =$1");
            Query2.bind(param);
            String kk=Query2.toString();
            resultado = Query2.execute();
            BagOfObject relacion2=(BagOfObject) resultado;
            Iterator it= relacion2.iterator();
            Operation aux;
            while(it.hasNext())
            {
                aux=(Operation)it.next();
                devolver.add(aux);//Meto el tipo de la operacion dentro de devolver!!
            }
            tx2.leave();
        }catch (QueryException ex)
        {
            ex.printStackTrace();
        }
        return devolver;
    }
    //MÉTODO QUE RETORNA LOS ATRIBUTOS DE UN OBJETO.
    static public  SetOfObject getAttributes(String param)
    {
        SetOfObject devolver = new SetOfObject();
        try
        {
            Object resultado;
            //Realizar esta función para cada uno de los objetos seleccionados
            Transaction tx2= new Transaction();
            Properties props = new Properties();
            props.put( "readLockMode", "NONE" );
            props.put("shareThread","TRUE");//Pruebo si tira con esto si no tira lo descomento
            tx2.setProperties(props);
            tx2.begin();
            OQLQuery Query2 = new OQLQuery(tx2);
            Query2.create("SELECT a.metaobjop.properties FROM GenericInterfaceExtent AS a WHERE a.name =$1");
            Query2.bind(param);
            String kk=Query2.toString();
            resultado = Query2.execute();
            BagOfObject relacion2=(BagOfObject) resultado;
            Iterator it= relacion2.iterator();
            Property aux= new Property();
            while(it.hasNext())
            {
                aux=(Property)it.next();
                //Si la propiedad name de aux es = null entonces es una relacion,recuerdese que la propiedad de name de las relaciones no se rellena,en otro caso es un attributo.... puedo usar esto para la comparacion
                if(aux.getName()!=null)
                {
                    devolver.add((Attribute)aux);//Guardo el tipo de atributo dentro del set clase que lo devolverá.
                }
            }
            tx2.leave();
        }catch (QueryException ex)
        {
            ex.printStackTrace();
        }
        return devolver;
    }

    //MÉTODO QUE RETORNA LOS ATRIBUTOS DE UN OBJETO EN FORMATO CADENA.
    static public  String getAttributesODL(String param)
    {
        String devolver ="";
        try
        {
            Object resultado;
            //Realizar esta función para cada uno de los objetos seleccionados
            Transaction tx2= new Transaction();
            Properties props = new Properties();
            props.put( "readLockMode", "NONE" );
            props.put("shareThread","TRUE");//Pruebo si tira con esto si no tira lo descomento
            tx2.setProperties(props);
            tx2.begin();
            OQLQuery Query2 = new OQLQuery(tx2);
            Query2.create("SELECT a.metaobjop.properties FROM GenericInterfaceExtent AS a WHERE a.name =$1");
            Query2.bind(param);
            String kk=Query2.toString();
            resultado = Query2.execute();
            BagOfObject relacion2=(BagOfObject) resultado;
            Iterator it= relacion2.iterator();
            Property aux= new Property();
            while(it.hasNext())
            {
                aux=(Property)it.next();
                //Si la propiedad name de aux es = null entonces es una relacion,recuerdese que la propiedad de name de las relaciones no se rellena,en otro caso es un attributo.... puedo usar esto para la comparacion
                if(aux.getName()!=null)
                {

                    devolver=devolver+"\n"+" attribute "+((GenericInterface)((Attribute)aux).getType()).getName()+" "+aux.getName()+";";
                }
            }
            tx2.leave();
        }catch (QueryException ex)
        {
            ex.printStackTrace();
        }

        return devolver;
    }

}
