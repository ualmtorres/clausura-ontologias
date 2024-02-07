//Source file: C:\\DOCPROYECTOFINDECARRERA\\PRUEBAKK\\sintitulo2\\src\\Repositorio\\Property.java

package Repositorio;
import Repositorio.*;
import com.poet.odmg.*;
import com.poet.odmg.util.*;
import com.poet.odmg.imp.*;
import java.util.*;
import org.odmg.QueryException;
import org.odmg.ObjectNameNotUniqueException;
import org.odmg.ObjectNameNotUniqueException;

public class Property extends MetaobjectI implements Metaobject
{
    public Object types;

    /**
     * @roseuid 3AB7EA5A023D
     */
    public Property()
    {

    }
    //Añade el el atributo o la relacion a un modulo.
    public void addUsedIn(Module c)
    {
        if ( this.usedIn == null )
        {
            this.usedIn = new SetOfObject();
        }
        this.usedIn.add( c );
        c.addIncludes(this);
    }
    //Identifica si una Property es atributo o relacion, no se le pasa parametrops coge el nombre directametne!!.
    public boolean isAttribute()
    {
        boolean result= false;
        String borrar=this.getName();
        try

        {            Object resultado=null;
            BagOfObject bag = new BagOfObject();
            Transaction tx2= new Transaction();
            Properties props = new Properties();
            props.put( "readLockMode", "NONE" );
            tx2.setProperties(props);
            tx2.begin();
            OQLQuery Query2 = new OQLQuery(tx2);
            Query2.create("SELECT a FROM AttributeExtent AS a WHERE a.name = $1");
            Query2.bind(borrar);
            String kk=Query2.toString();
            resultado = Query2.execute();
            bag=(BagOfObject)resultado;
            if(!bag.isEmpty())
            {
                result=true;
            }

            tx2.abort();
        }catch (QueryException ex)
        {
            ex.printStackTrace();
        }
        return result;
    }

}
