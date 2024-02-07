//Source file: C:\\DOCPROYECTOFINDECARRERA\\PRUEBAKK\\sintitulo2\\src\\Repositorio\\Type.java

package Repositorio;
import com.poet.odmg.*;
import com.poet.odmg.util.*;
import com.poet.odmg.imp.*;
import java.util.*;
import org.odmg.QueryException;
import org.odmg.ObjectNameNotUniqueException;
import org.odmg.ObjectNameNotUniqueException;


public class TypeI extends MetaobjectI implements Type
{
    public SetOfObject properties;//Tipo Property, es el que estaba antes en GenericInterface.
    public SetOfObject operations;//Relacion con operations
    TypeI()
    {

    }
}
