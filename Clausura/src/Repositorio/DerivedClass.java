//Source file: C:\\DOCPROYECTOFINDECARRERA\\PRUEBAKK\\sintitulo2\\src\\Repositorio\\DerivedClass.java

package Repositorio;
import com.poet.odmg.*;
import com.poet.odmg.util.*;
import com.poet.odmg.imp.*;
import org.odmg.QueryException;
import java.util.*;


public class DerivedClass extends GenericClass
{
    public SetOfObject derivedFrom;//tipo GenericClass, almacena de q clases es esta derivada

    /**
     *
     */
    //Constructor que genera una clase derivada a partir solo del nombre
    public DerivedClass(String nombre)
    {
        super.setName(nombre);
        this.metaobjop= new TypeI();
    }
    //Constructor que genera una clase derivada y la incluye dentro del Modulo indicado.
    public DerivedClass(String nombre, Module c)
    {
        super.setName(nombre);
        //Añadimos el usedIn y el inverso es decir el includes..
        this.metaobjop= new TypeI();
        this.addUsedIn(c);
    }
    //Añade de quien deriva una clase derivada
    public void addDerivedFrom(GenericClass c)
    {
        if ( this.derivedFrom == null )
        {
            this.derivedFrom = new SetOfObject();
        }
        this.derivedFrom.add( c );
        c.addBaseOf(c);//Al añadirle una clase derivada relleno al de arriba y le indico q es base de este.
    }

    //Devuelve true si existe una clase derivada con el nombre introducido como parametro
    public boolean isDerivedClass(String nombre)
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
            Query2.create("SELECT a FROM DerivedClassExtent AS a WHERE a.name = $1");
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

    //METODO QUE A DICE SI HAY DOS CLASES QUE TENGAN UNA RELACION DE EXTENT CON ESOS DOS NOMBRES.
    //A ES LA SUBLCLASE Y C LA SUPERCLASE.. COMPRUEBA QUE HAYA UN EXTENT A--->C
    public boolean isExtenderOf(String A,String C, Module M)
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
            Query.bind(A);//Blinda la subclase que sera la que este en el TobeUpdate.
            Query.bind(C);//Blinda la superclase
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
    //METODO QUE VE SI UNA RELACION ENTRE UNA CLASE Y UNA INTERFAZ ES ISA.
    public boolean isClaseIsaInterfaz(String h,String k, Module md)
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
            Query.bind(h);
            Query.bind(k);
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

        return devolver;//retorna true o false.
    }

    //Metodo que transforma las lo que haya en el tobeupdate en las relaciones de la clase.
    public void FromToBeUpdateToRelations()
    {


    }

    //Método que devuelve todas la clases derivadas de un modulo dado
    public BagOfObject getAllDerivedClass(String md)
    {
        DerivedClass c = new DerivedClass("aux");
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
            Query.create("SELECT K FROM DerivedClassExtent AS K");
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
            c=(DerivedClass)obj;
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
    //Obtiene en formato cadena las clases derivadas de las que extiende otra clase derivada o clase derivada.
    public String getDerivedExtend(String c, String modulo)
    {
        BagOfObject herencia= new BagOfObject();
        BagOfObject relacion= new BagOfObject();
        ListOfObject devolver= new  ListOfObject();
        Object resultado=null;
        String resultados="";

        //Devuelve una lista con todos los objetos que hacen referencia al objeto tratado.
        /**Obtener las relaciones de herecia.
         * Solo Interesan las clases que son superclases de la clase tratada (Ci)
         */
        try
        {
            Transaction tx= new Transaction();
            Properties props = new Properties();
            props.put( "readLockMode", "NONE" );
            tx.setProperties(props);
            tx.begin();
            OQLQuery Query = new OQLQuery(tx);
            Query.create("SELECT  a.CorrespondsToSuperclasses FROM ModuleClassesExtent AS a WHERE a.CorrespondsToSuperclasses IN DerivedClassExtent  AND a.CorrespondsToSubclasses.name = $1 AND a.InModule.name = $2");
            Query.bind(c);
            Query.bind(modulo);
            String kk=Query.toString();
            resultado = Query.execute();
            herencia=(BagOfObject) resultado;
            Iterator ite_c=herencia.iterator();
            while(ite_c.hasNext())
            {
                resultados=resultados+" extends "+((GenericInterface)ite_c.next()).getName();
            }
            tx.abort();
        }catch (QueryException ex)
        {
            ex.printStackTrace();
        }
        return resultados;
    }


}
