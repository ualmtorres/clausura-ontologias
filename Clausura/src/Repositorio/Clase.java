//Source file: C:\\DOCPROYECTOFINDECARRERA\\PRUEBAKK\\sintitulo2\\src\\Repositorio\\Class.java

package Repositorio;
import com.poet.odmg.util.*;
import java.util.*;
import com.poet.odmg.*;
import org.odmg.QueryException;
import com.poet.odmg.imp.*;

public class Clase extends GenericClass
{

    /**
     * @roseuid 3AB7EA590119
     */
    //------CONSTRUCTORES...............
    public Clase(String nombre)
    {
        super.setName(nombre);
        // this.setName(nombre);
        this.metaobjop= new TypeI();//Cuando creo un nuevo objeto clase creo tb un nuevo objeto type que contiene el used in y todas las propiedades(atributos y relaciones).

    }
    //Método para ayudar,para hacer de métodos auxiliares y no tener que definirlos con nombre.
    public Clase()
    {

    }

    //----------MÉTODOS
    //MÉTODO PARA OBTENER TODAS LAS CLASES DE UN MODULO.
    public BagOfObject getAllClases(String md)


    {        Clase c = new Clase();
        BagOfObject devolver=new BagOfObject();

        Transaction tx = new Transaction();
        Object resultado=null;
        try
        {
            Properties props = new Properties();
            props.put( "readLockMode", "NONE" );
            props.put("readAfterTerminate", "true");
            tx.setProperties(props);
            tx.begin();
            OQLQuery Query = new OQLQuery(tx);
            Query.create("SELECT K FROM ClaseExtent AS K");
            resultado = Query.execute();
            tx.leave();
        }catch (QueryException ex)
        {
            ex.printStackTrace();
        }

        Iterator ite=((BagOfObject) resultado).iterator();
        while (ite.hasNext())


        {            SetOfObject aux = new SetOfObject();
            Iterator auxIT;
            Module auxM= new Module("k");

            Object obj=ite.next();
            c=(Clase)obj;
            aux=c.metaobjop.usedIn;
            auxIT=aux.iterator();
            while (auxIT.hasNext())
            {
                auxM=(Module)auxIT.next();
                if(auxM.getName().equals(md))
                {
                    devolver.add(c);
                }

            }
            //System.out.println(c.getName());
        }

        return devolver;
    }
    //MÉTODO PARA RECUPERAR UNA CLASE ESPECÍFICA.
    public Clase getClase(String param)
    {
        Clase devolver = new Clase();
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
            Query2.create("SELECT a FROM ClaseExtent AS a WHERE a.name = $1");
            Query2.bind(param);
            String kk=Query2.toString();
            resultado = Query2.execute();

            /**Ver cual de ellos pertenece al modulo que se ha pedido mediante,
             * RelationShip.usedIn.name, los que no pertenezcan se eliminan del set.
             */
            BagOfObject relacion2=(BagOfObject) resultado;
            Iterator it= relacion2.iterator();
            while(it.hasNext())
            {
                devolver=(Clase)it.next();

            }
            tx2.leave();
        }catch (QueryException ex)
        {
            ex.printStackTrace();
        }
        return devolver;
    }
    //DADA UNA CLASE VER SI TIENE UNA RELACION DE ISA CON UNA INTERFAZ.
    public SetOfObject getIsaClase(Clase k, Module md)
    {
        BagOfObject isa= new BagOfObject();
        BagOfObject relacion= new BagOfObject();
        SetOfObject devolverIsa= new  SetOfObject();
        Object resultado=null;

        try
        {
            Transaction tx= new Transaction();
            Properties props = new Properties();
            props.put( "readLockMode", "NONE" );
            tx.setProperties(props);
            tx.begin();
            OQLQuery Query = new OQLQuery(tx);
            Query.create("SELECT  a.CorrespondsToSupertypes FROM ModuleInterfacesExtent AS a  WHERE a.CorrespondsToSupertypes IN InterfazExtent AND a.CorrespondsToSubtypes.name = $1 AND a.InModule.name = $2 ");
            Query.bind(k.getName());
            Query.bind(md.getName());
            String kk=Query.toString();
            resultado = Query.execute();
            isa=(BagOfObject) resultado;
            tx.leave();
        }catch (QueryException ex)
        {
            ex.printStackTrace();
        }
        Iterator it =isa.iterator();
        while(it.hasNext())


        {            Interfaz aux = new Interfaz();
            aux=(Interfaz)it.next();
            devolverIsa.add(aux);
        }

        return devolverIsa;//retorna la interfaz/ces de
    }

    //DADA UNA CLASE VER SI TIENE UNA RELACION DE ISA CON UNA INTERFAZ.
    public String getIsaClase(String k, String md)
    {
        BagOfObject isa= new BagOfObject();
        BagOfObject relacion= new BagOfObject();
        Object resultado=null;
        String retorna="";


        try
        {
            Transaction tx= new Transaction();
            Properties props = new Properties();
            props.put( "readLockMode", "NONE" );
            tx.setProperties(props);
            tx.begin();
            OQLQuery Query = new OQLQuery(tx);
            Query.create("SELECT  a.CorrespondsToSupertypes FROM ModuleInterfacesExtent AS a  WHERE a.CorrespondsToSupertypes IN InterfazExtent AND a.CorrespondsToSubtypes.name = $1 AND a.InModule.name = $2 ");
            Query.bind(k);
            Query.bind(md);
            String kk=Query.toString();
            resultado = Query.execute();
            isa=(BagOfObject) resultado;
            tx.leave();
        }catch (QueryException ex)
        {
            ex.printStackTrace();
        }
        Iterator it =isa.iterator();
        while(it.hasNext())


        {            Interfaz aux = new Interfaz();
            aux=(Interfaz)it.next();
            retorna=retorna+" : "+aux.getName();
        }

        return retorna;//retorna la interfaz/ces de
    }

    /**Habrá que establecer los métodos para introducir las relaciones
     * en el directo y en el objeto inverso.. tb para la relacion Module y para
     * las module Interfaces o moduleclasses
     */

    public void getClaseAddUsedIn(String param, Module M)


    {        Clase devolver = new Clase();
        try
        {
            Object resultado;
            //Realizar esta función para cada uno de los objetos seleccionados
            Transaction tx2= new Transaction();
            Properties props = new Properties();
            props.put( "readLockMode", "NONE" );
            tx2.setProperties(props);
            tx2.begin();
            ObjectServices.current().awake(M);
            OQLQuery Query2 = new OQLQuery(tx2);
            Query2.create("SELECT a FROM ClaseExtent AS a WHERE a.name = $1");
            Query2.bind(param);
            String kk=Query2.toString();
            resultado = Query2.execute();

            /**Ver cual de ellos pertenece al modulo que se ha pedido mediante,
             * RelationShip.usedIn.name, los que no pertenezcan se eliminan del set.
             */
            BagOfObject relacion2=(BagOfObject) resultado;
            Iterator it= relacion2.iterator();
            while(it.hasNext())
            {
                devolver=(Clase)it.next();

            }
            devolver.addUsedIn(M);
            tx2.commit();
        }catch (QueryException ex)
        {
            ex.printStackTrace();
        }

    }

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

    //Devuelve si dadas dos clases una extiende de la otra. C es la subclase B la superclase
    public boolean isExtenderOf(String C,String B, String M)
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
            Query.create("SELECT  a.CorrespondsToSubclasses FROM ModuleClassesExtent AS a  WHERE  a.CorrespondsToSubclasses.name = $1 AND  a.CorrespondsToSuperclasses.name = $2 ");
            Query.bind(C);//Blinda la subclase
            Query.bind(B);//Blinda la superclase
            //            Query.bind(M);//blinda el modulo
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

    //Metodo que devuelve true si la cadena es el nombre de una clase, no me inteesa el modulo.
    public boolean isClase(String nombre)
    {


        boolean cadena=false;
        try

        {            Object resultado=null;
            BagOfObject bag = new BagOfObject();
            //Realizar esta función para cada uno de los objetos seleccionados
            Transaction tx2= new Transaction();
            Properties props = new Properties();
            props.put( "readLockMode", "NONE" );
            tx2.setProperties(props);
            tx2.begin();
            OQLQuery Query2 = new OQLQuery(tx2);
            Query2.create("SELECT a FROM ClaseExtent AS a WHERE a.name = $1");
            Query2.bind(nombre);
            String kk=Query2.toString();
            resultado = Query2.execute();
            bag=(BagOfObject)resultado;
            if(!bag.isEmpty())
            {
                cadena=true;
            }

            tx2.abort();
        }catch (QueryException ex)
        {
            ex.printStackTrace();
        }
        return cadena;
    }

}
