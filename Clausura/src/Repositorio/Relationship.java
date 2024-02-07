//Source file: C:\\DOCPROYECTOFINDECARRERA\\PRUEBAKK\\sintitulo2\\src\\Repositorio\\Relationship.java

package Repositorio;
import Repositorio.*;
import com.poet.odmg.*;
import com.poet.odmg.util.*;
import com.poet.odmg.imp.*;
import java.util.*;
import org.odmg.QueryException;
import org.odmg.ObjectNameNotUniqueException;
import org.odmg.ObjectNameNotUniqueException;



public class Relationship extends Property
{
    public Relationship traversal;//almacena la referencia a otro Relationship que almacena el otro lado de la referencia.

    public Relationship()
    {
    }
    //Constructores

    public Relationship(GenericInterface g,Module f)
    {
        this.setType(g);
        //La relacion se define en un modulo.... con lo cual la establecemos
        this.addUsedIn(f);
    }

    // Metodo que establece la relacion entre los dos relationship
    /**Distinguir entre Unidireccional(0) o Bidireccional(1)
     * Si es unidireccional  R--->H la llamada es R.relacionar(H,0)
     * Si es bidireccional  R<--->H la llamada es R.relacionar(H,1),
     * entonces ya automaticamente rellenaría el inverso. es Traversal de H
     */

    public void relacionar(Relationship r, int tipo)
    {
        if(r!=null)//Se usa en las relaciones que son destino de una unidireccional.
        {
            if (tipo==0)
            {
                this.setTraversal(r);
            }
            else
            {
                r.setTraversal(this);
                this.setTraversal(r);
            }
        }
    }
    //Obtiene la clase Relationship que almacena el otro lado de la Relationship
    public Relationship getTraversal()
    {
        return this.traversal;
    }

    /**
     * @roseuid 3A8F1381018E
     */
    //Establece cual es el otro extremo de la relación
    public void setTraversal(Relationship r)
    {
        this.traversal=r;

    }
    //Establece el tipo de objeto que es uno de los extremos de la relacion y añade el inverso.
    public void setType(GenericInterface g)
    {
        this.types=g;
        g.addProperties(this);//Relleno el inverso, es decir las propiedades de la clase.
    }
    public Object getType()
    {
        return this.types;
    }
    //Método que inserta en que modulos es usada la relación

    public void addUsedIn(Module c)
    {
        if ( this.usedIn == null )
        {
            this.usedIn = new SetOfObject();
        }
        this.usedIn.add( c );
        c.addIncludes(this);
    }
    //MÉTODO QUE DEVUELVE EL USEDIN DE LAS RELACIONES, ES DECIR DONDE SON USADAS.
    public SetOfObject getUsedIn()
    {

        return this.usedIn;

    }
    /**
     *  METODO QUE GUARDA LAS RELACIONES EN EL NUEVO ESQUEMA TRAS UNA CONSULTA.
     *  Dada las clases clas1 y clas2 busca relacion en la base de datos y
     *  lo introducen en el modulo nuevo.
     */
    public void  getRelationshipAddToModule(String clas1, String clas2, String ModuloOr, String ModuloDest)
    {
        Module Destino=new Module("Devolver");
        Relationship devolver=new Relationship();
        try
        {
            Object resultado;
            Transaction tx3= new Transaction();
            Properties props = new Properties();
            props.put( "readLockMode", "NONE" );
            props.put("readAfterTerminate", "true");
            tx3.setProperties(props);
            tx3.begin();
            OQLQuery Query = new OQLQuery(tx3);
            Query.create("SELECT a FROM ModuleExtent AS a WHERE a.name=$1");
            Query.bind(ModuloDest);//$1
            String kk=Query.toString();
            resultado = Query.execute();
            BagOfObject relacion2=(BagOfObject) resultado;
            Iterator it= relacion2.iterator();
            while(it.hasNext())
            {
                Destino=(Module)it.next();
                System.out.println("esto es lo q hace"+Destino.name);
            }
            tx3.abort();

        }catch (QueryException ex)
        {
            ex.printStackTrace();
        }

        /**
         * Consultamos los relationships y nos quedamos con los que nos interesan
         * realmante. Que son los que pertenecen al modulo origen.
         */
        try
        {
            Object resultado;
            Transaction tx2= new Transaction();
            Properties props = new Properties();
            props.put( "readLockMode", "NONE" );
            tx2.setProperties(props);
            tx2.begin();
            ObjectServices.current().awake(Destino);
            OQLQuery Query2 = new OQLQuery(tx2);
            Query2.create("SELECT  * FROM RelationshipExtent");
            String kk=Query2.toString();
            resultado = Query2.execute();
            BagOfObject relacion2=(BagOfObject) resultado;
            Iterator it= relacion2.iterator();
            while(it.hasNext())

            {                boolean pertenece=false;
                //Comprobar que la relacion pertenece al modulo origen.
                devolver=(Relationship)it.next();
                Iterator pertene=devolver.getUsedIn().iterator();
                while (pertene.hasNext())
                {
                    Module auxM=new Module("auxM");
                    auxM=(Module)pertene.next();
                    if(auxM.getName().equals((String)ModuloOr))
                    {
                        pertenece=true;
                    }//Fin if

                }//Fin while de busqueda en e modulo.


                if(pertenece==true)//Si pertenece al modulo origen
                {

                    //busco para cada par de clases si <-->
                    if(devolver.getTraversal()!=null)
                    {
                        if((((GenericInterface)devolver.getType()).getName().equals((String)clas1))&&(((GenericInterface)devolver.getTraversal().getType()).getName().equals((String)clas2)))
                        {

                            devolver.addUsedIn(Destino);
                        }


                        if((((GenericInterface)devolver.getType()).getName().equals((String)clas1))&&(((GenericInterface)devolver.getTraversal().getType()).getName().equals((String)clas2))&&(devolver.getTraversal().getTraversal()==null))
                        {
                            /**Si la relacion es sólo en uno de los sentidos clas1-->clas2, la relacion cals2 no la va a detectar nunca aunque entre
                             *  en un paso posterior como clas1, pues no tendrá traversal.
                             */

                            devolver.getTraversal().addUsedIn(Destino);//No se si tira
                        }
                    }
                }//fin de pertenece.
            }

            tx2.commit();
        }catch (QueryException ex)
        {
            ex.printStackTrace();
        }
    }//FIN MÉTODO.

}
