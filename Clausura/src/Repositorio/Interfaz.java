//Source file: C:\\DOCPROYECTOFINDECARRERA\\PRUEBAKK\\sintitulo2\\src\\Repositorio\\Interface.java

package Repositorio;
import com.poet.odmg.util.*;
import java.util.*;
import com.poet.odmg.*;
import org.odmg.QueryException;
import com.poet.odmg.imp.*;

public class Interfaz extends GenericInterface
{

    /**
     * @roseuid 3AB7EA570102
     */
    public Interfaz(String nom)
    {
        super.setName(nom);
        this.metaobjop= new TypeI();
    }
    public  Interfaz()
    {
    }

    //CONSULTAR SI UNA INTERFAZ TIENE UNA RELACION DE ISA CON OTRA INTERFAZ
    public SetOfObject getIsaInterfaz(Interfaz k, Module md)
    {
        BagOfObject isa= new BagOfObject();
        BagOfObject relacion= new BagOfObject();
        SetOfObject devolverIsa= new  SetOfObject();
        Object resultado=null;

        try
        {
            Transaction tx= new Transaction();
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

    //Guarda una interfaz en la base de datos y la enlaza con un modulo
    public void getInterfazAddUsedIn(String param, Module M)

    {        Interfaz devolver = new Interfaz();
        try
        {
            Object resultado;
            //Realizar esta función para cada uno de los objetos seleccionados
            Transaction tx2= new Transaction();
            tx2.begin();
            ObjectServices.current().awake(M);
            OQLQuery Query2 = new OQLQuery(tx2);
            Query2.create("SELECT a FROM InterfazExtent AS a WHERE a.name = $1");
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
                devolver=(Interfaz)it.next();

            }
            devolver.addUsedIn(M);
            tx2.commit();
        }catch (QueryException ex)
        {
            ex.printStackTrace();
        }
        // return devolver;
    }
    //METODO QUE RETORNA TODAS LAS INTERFAES QUE HAY EN UN MODULO DETERMINADO
    public BagOfObject getAllInterfaces(String md)
    {
        Interfaz c =new Interfaz();
        BagOfObject devolver=new BagOfObject();

        Transaction tx = new Transaction();
        Object resultado=null;
        try
        {
            Properties props = new Properties();
            props.put("readAfterTerminate", "true");
            tx.setProperties(props);
            tx.begin();
            OQLQuery Query = new OQLQuery(tx);
            Query.create("SELECT K FROM InterfazExtent as K");
            resultado = Query.execute();
            tx.leave();
        }catch (QueryException ex)
        {
            ex.printStackTrace();
        }

        Iterator ite=((BagOfObject) resultado).iterator();
        while (ite.hasNext())
        {
            SetOfObject aux = new SetOfObject();
            Iterator auxIT;
            Module auxM= new Module("k");

            Object obj=ite.next();
            c=(Interfaz)obj;
            //La interfaz puede estar usada en varios midulos, extraigo los modulos.
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
    //Devuelve true si entre dos interfaces existe relacion ISA
    public boolean isSubtypeOf(Interfaz k, Module md)
    {
        BagOfObject isa= new BagOfObject();
        boolean devolver=false;
        Object resultado=null;

        try
        {
            Transaction tx= new Transaction();
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

        return devolver;//retorna la interfaz/ces de
    }
    //Devuelve true si en la base de datos existe algun pbjeto interfaz con el nombre que se le pasa como string.
    public boolean isInterfaz(String nombre)
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
            Query2.create("SELECT a FROM InterfazExtent AS a WHERE a.name = $1");
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

    //MÉTODO PARA RECUPERAR UNA INTERFAZ ESPECÍFICA.
    public Interfaz getInterfaz(String param)
    {
        Interfaz devolver = new Interfaz();
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
            Query2.create("SELECT a FROM InterfazExtent AS a WHERE a.name = $1");
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
                devolver=(Interfaz)it.next();

            }
            tx2.leave();
        }catch (QueryException ex)
        {
            ex.printStackTrace();
        }
        return devolver;
    }

}//Fin de la clase interfaz.
