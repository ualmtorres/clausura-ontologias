//Source file: C:\\DOCPROYECTOFINDECARRERA\\PRUEBAKK\\sintitulo2\\src\\Repositorio\\DerivedInterface.java

package Repositorio;
import com.poet.odmg.*;
import com.poet.odmg.util.*;
import org.odmg.QueryException;
import com.poet.odmg.imp.*;
import java.util.*;

public class DerivedInterface extends GenericInterface
{
    public SetOfObject derivedFrom;//tipo GenericInterface almacena de quien deriva.

    /**
     *
     */
    //Constructor que genera una interfaz derivada solo con el nombre
    public DerivedInterface(String nombre)
    {
        super.setName(nombre);
        this.metaobjop= new TypeI();
    }

    //Interfaz derivada que genera una interfaz derivada a partir del nombre y la incluye el el modulo indicado.
    public DerivedInterface(String nombre,Module c)
    {
        super.setName(nombre);
        this.metaobjop= new TypeI();
        this.addUsedIn(c);
    }

    //Método añade a la lista derivedFrom de una interfaz derivada de quien deriva
    public void addDerivedFrom(GenericInterface c)
    {
        if ( this.derivedFrom == null )
        {
            this.derivedFrom = new SetOfObject();
        }
        this.derivedFrom.add( c );
        c.addBaseOf(this);//Al añadirle una interfaz derivada relleno al de arriba y le indico q es base de este.

    }
    //Devuelve true si es una interface derivada y esta en la bbdd.
    public boolean isDerivedInterface(String nombre)
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
            Query2.create("SELECT a FROM DerivedInterfaceExtent AS a WHERE a.name = $1");
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

    //Método que devuelve todas la clases derivadas de un modulo dado
    public BagOfObject getAllDerivedInterface(String md)
    {
        DerivedInterface c = new DerivedInterface("aux");
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
            Query.create("SELECT K FROM DerivedInterfaceExtent AS K");
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
            c=(DerivedInterface)obj;
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
        }

        return devolver;
    }

}
