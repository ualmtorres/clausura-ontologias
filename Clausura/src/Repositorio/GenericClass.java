//Source file: C:\\DOCPROYECTOFINDECARRERA\\PRUEBAKK\\sintitulo2\\src\\Repositorio\\GenericClass.java

package Repositorio;
import com.poet.odmg.*;
import com.poet.odmg.util.*;
import com.poet.odmg.imp.*;
import java.util.*;
import org.odmg.QueryException;


public class GenericClass extends GenericInterface
{

    public SetOfObject extender;//tipo ModuleClasses
    public SetOfObject extensions;//tipo ModuleClasses


    /**
     * @roseuid 3AB7EA58024E
     */
    public GenericClass()
    {

    }
    public void addExtender(ModuleClasses c)
    {
        if ( this.extender == null )
        {
            this.extender = new SetOfObject();
        }
        this.extender.add( c );
    }

    public void addExtensions(ModuleClasses c)
    {
        if ( this.extensions == null )
        {
            this.extensions = new SetOfObject();
        }
        this.extensions.add( c );
    }

    /**
     *  METODO QUE GUARDA LAS RELACIONES EN EL NUEVO ESQUEMA TRAS UNA CONSULTA.
     *  Dada las clases clas1 y clas2 busca relacion en la base de datos y
     *  lo introducen en el modulo nuevo.
     */
    public boolean  isRelationship(String clas1, String clas2, String Modulo)
    {
        Relationship devolver=new Relationship();
        boolean resultad=false;
        try
        {
            Object resultado;
            Transaction tx2= new Transaction();
            Properties props = new Properties();
            props.put( "readLockMode", "NONE" );
            tx2.setProperties(props);
            tx2.begin();
            OQLQuery Query2 = new OQLQuery(tx2);
            Query2.create("SELECT  * FROM RelationshipExtent");
            String kk=Query2.toString();
            resultado = Query2.execute();
            BagOfObject relacion2=(BagOfObject) resultado;
            Iterator it= relacion2.iterator();
            while(it.hasNext())

            {                boolean pertenece=false;
                //Comprobar que la relacion pertenece al modulo.
                devolver=(Relationship)it.next();
                Iterator pertene=devolver.getUsedIn().iterator();
                /*      while (pertene.hasNext()){
                 Module auxM=new Module("auxM");
                 auxM=(Module)pertene.next();
                 if(auxM.getName().equals((String)Modulo)){
                 pertenece=true;
                 }//Fin if
                                  }//Fin while de busqueda en e modulo.*/

                if(true)//Si pertenece al modulo origen
                {
                    //busco para cada par de clases si <-->
                    if(devolver.getTraversal()!=null)
                    {
                        if((((GenericInterface)devolver.getType()).getName().equals((String)clas1))&&(((GenericInterface)devolver.getTraversal().getType()).getName().equals((String)clas2)))
                        {
                            resultad=true;
                        }


                        if((((GenericInterface)devolver.getType()).getName().equals((String)clas1))&&(((GenericInterface)devolver.getTraversal().getType()).getName().equals((String)clas2))&&(devolver.getTraversal().getTraversal()==null))
                        {
                            /**Si la relacion es sólo en uno de los sentidos clas1-->clas2, la relacion cals2 no la va a detectar nunca aunque entre
                             *  en un paso posterior como clas1, pues no tendrá traversal.
                             */
                            resultad=true;
                        }
                    }
                }//fin de pertenece.
            }
            tx2.abort();
        }catch (QueryException ex)
        {
            ex.printStackTrace();
        }
        return resultad;
    }
    //Metodo que devuelve verdadero o falso en funcion de si una GenericClass es isa de una Interfaz
    public boolean isSubtypeOf(String I,String B,String md)
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
            Query.create("SELECT  a.CorrespondsToSupertypes FROM ModuleInterfacesExtent AS a  WHERE a.CorrespondsToSubtypes.name = $1 AND a.CorrespondsToSupertypes.name = $2");
            Query.bind(B);//Clase o Dclase
            Query.bind(I);//Interfaz
            //          Query.bind(md);
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
    //Obtiene en formato cadena las clases de las que extiende una clase o clase derivada.
    public String getExtend(String c, String modulo)
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
            Query.create("SELECT  a.CorrespondsToSuperclasses FROM ModuleClassesExtent AS a WHERE a.CorrespondsToSuperclasses IN ClaseExtent AND a.CorrespondsToSubclasses.name = $1 AND a.InModule.name = $2");
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
    //Metodo que obtiene las relationships y los concatena para obtener el odl. Se le pasa el nombre de la clase o clase derivada.
    public String getRelationshipODL(String x, String md)
    {
        String resultados="";
        Object resultado=null;
        try
        {
            Transaction tx2= new Transaction();
            Properties props = new Properties();
            props.put( "readLockMode", "NONE" );
            tx2.setProperties(props);
            tx2.begin();
            OQLQuery Query2 = new OQLQuery(tx2);
            Query2.create("SELECT a FROM RelationshipExtent AS a WHERE ((GenericInterface)a.types).name = $1");
            Query2.bind(x);
            String kk=Query2.toString();
            resultado = Query2.execute();
            BagOfObject relaux=(BagOfObject) resultado;
            /**Ver cual de ellos pertenece al modulo que se ha pedido mediante,
             * RelationShip.usedIn.name, los que no pertenezcan se eliminan del set.
             */
            Iterator it= relaux.iterator();
            while(it.hasNext())
            {
                Relationship aux= new Relationship();
                aux=(Relationship)it.next();
                Iterator it2 = aux.usedIn.iterator();
                //Se comprueba si la relación esta en el modulo o no.
                while(it2.hasNext())
                {
                    Module au = new Module("kk");
                    au =(Module) it2.next();
                    //Se comprueba que pertenezca al Modulo indicado.
                    if(au.getName().equals(md))
                    {
                        if(aux.getTraversal()!=null)
                        //Si fuera nulo implicaria que es solo en la direccion opuesta la navegabilidad
                        {
                            resultados="\n"+resultados+" relationship "+((GenericClass)aux.getTraversal().getType()).getName();//Cojo el directo
                            if(aux.getTraversal().getTraversal()!=null)
                            //Tendria inverso
                            {
                                resultados=resultados+" inverse "+((GenericClass)aux.getTraversal().getTraversal().getType()).getName()+":"+" ;";
                            }else
                            {
                                resultados=";\n";
                            }
                        }


                    }
                    System.out.println(au.getName());
                }

            }
            tx2.leave();
        }catch (QueryException ex)
        {
            ex.printStackTrace();
        }
        return resultados;

    }//Fin del método.

}