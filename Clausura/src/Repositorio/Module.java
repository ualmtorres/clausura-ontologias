//Source file: C:\\DOCPROYECTOFINDECARRERA\\PRUEBAKK\\sintitulo2\\src\\Repositorio\\Module.java

package Repositorio;
import GUI.GUIPrincipal;
import Algoritmos.basedeDatos;
import com.poet.odmg.*;
import org.odmg.QueryException;
import org.odmg.ODMGException;
import org.odmg.ODMGRuntimeException;
import com.poet.odmg.util.*;
import com.poet.odmg.imp.*;
import java.util.*;

public class Module extends DefiningScope implements Metaobject
{

    public SetOfObject SubClasses;//Lo mismo que la de abajo.Tipo ModuleClasses
    public SetOfObject SubTypes;//cabe la posibilidad de que lo convirtamos a SetOfObjects.Tipo ModuleInterfaces
    public transient MetaobjectI MetaobjectOp;// Atraves de este hago lo del fordwarding, por ello lo ponemos a transient (no se almacena en la bbdd)

    public Module(String nom)
    {
        this.setName(nom);

    }

    public void removeModule(String param)
    {
        Database db3=new Database();
        Database db2= Database.current();
        Module k= new Module("aux");

        try
        {
            db2.close();
            db3.open(basedeDatos.BASEDEDATOS, Database.OPEN_READ_WRITE);
        }
        catch ( ODMGException x )
        {
            x.printStackTrace();
        }
        try
        {
            Object resultado;
            //Realizar esta función para cada uno de los objetos seleccionados
            Transaction tx2= new Transaction();
            tx2.begin();
            OQLQuery Query2 = new OQLQuery(tx2);
            Query2.create("SELECT a FROM ModuleExtent AS a WHERE a.name = $1");
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
                k=(Module)it.next();

            }
            tx2.abort();
        }catch (QueryException ex)
        {
            ex.printStackTrace();
        }


        Transaction []a=db3.getTransactions();
        for(int i=1;i<=a.length;i++)
        {
            a[i].commit();
        }

        try
        {
            Transaction tx= new Transaction();
            tx.begin();
            ObjectServices.current().awake(k);
            ObjectServices.current().delete( k );
            tx.commit();
        }catch ( ODMGRuntimeException x )
        {
            x.printStackTrace();
        }

    }

    public void removeInterface()
    {

    }

    public void removeClass()
    {

    }

    public String getName()
    {
        return this.name;
    }

    public void setName(String nom)
    {
        this.name=nom;
    }

    /**
     * @roseuid 3AB7EA5501DC
     */
    public String getCommnet()
    {
        return this.Commets;
    }

    public void setComment(String com)
    {
        this.Commets=com;
    }
    //Inserta un objeto en el Set del module, para indicar que objetos son usados en el modulo.
    public void addIncludes(MetaobjectI c)
    {
        if ( this.includes == null )
        {
            this.includes = new SetOfObject();
        }
        this.includes.add( c );
    }
    //Mismo método para clases e interfaces
    public void addIncludes(GenericInterface c)
    {
        if ( this.includes == null )
        {
            this.includes = new SetOfObject();
        }
        this.includes.add( c );
    }
    //Lo mismo que método para operaciones..
    public void addIncludes(Operation c)
    {
        if ( this.includes == null )
        {
            this.includes = new SetOfObject();
        }
        this.includes.add( c );
    }
    //Añade ModuleInterfaces a la relacion Subtypes, para indicar cuales son las Relaciones ISA q hay en el modulo.
    public void addSubtypes(ModuleInterfaces c)
    {
        if ( this.SubTypes == null )
        {
            this.SubTypes = new SetOfObject();
        }
        this.SubTypes.add( c );
    }
    //Añade ModuleClasses a la relacion SubClasses, para indicar cuales son las Relaciones Extens q hay en el modulo.
    public void addSubClasses(ModuleClasses c)
    {
        if ( this.SubClasses == null )
        {
            this.SubClasses = new SetOfObject();
        }
        this.SubClasses.add( c );
    }
    /**   METODOS DE CONSULTA SOBRE LOS MODULOS.
     *
     */
    //Obtener todos los módulos existentes en la base de datos.
    public BagOfObject getAllModules()


    {        Module m = new Module("kk");
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
            Query.create("SELECT * FROM ModuleExtent");
            resultado = Query.execute();
            devolver=(BagOfObject)resultado;
            tx.leave();
        }catch (QueryException ex)
        {
            ex.printStackTrace();

        }

        Iterator ite=((BagOfObject) resultado).iterator();
        while (ite.hasNext())
        {
            Object obj=ite.next();
            m=(Module)obj;
            System.out.println(m.getName());
        }

        return devolver;
    }

    //DEVUELVE EL MODULO AL QUE SE HACE REFERENCIA.
    public Module getModule(String param)


    {        Module devolver = new Module("kaki");
        try
        {
            Object resultado;
            //Realizar esta función para cada uno de los objetos seleccionados
            Transaction tx2= new Transaction();
            tx2.begin();
            OQLQuery Query2 = new OQLQuery(tx2);
            Query2.create("SELECT a FROM ModuleExtent AS a WHERE a.name = $1");
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
                devolver=(Module)it.next();

            }
            tx2.leave();
        }catch (QueryException ex)
        {
            ex.printStackTrace();
        }
        return devolver;
    }

    //MÉTODO PARA VER QUE HAY EN CADA MÓDULO.
}