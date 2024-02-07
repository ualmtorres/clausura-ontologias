//Source file: C:\\DOCPROYECTOFINDECARRERA\\PRUEBAKK\\sintitulo2\\src\\Repositorio\\MetaobjectI.java

package Repositorio;
import com.poet.odmg.*;
import com.poet.odmg.util.*;
import com.poet.odmg.imp.*;
import java.util.*;

public class MetaobjectI implements Metaobject
{
    protected String name;
    private String comment;
    public SetOfObject usedIn; //tipo DefiningScope

    /**
     * @roseuid 3AB7EA5E0257
     */
    public MetaobjectI()
    {

    }

    /**
     * @return java.lang.String
     * @roseuid 3AB7EA5E027F
     */
    public String getName()
    {
        return name;
    }

    /**
     * @roseuid 3AB7EA5E02A7
     */
    public void setName(String nom)
    {
        name=nom;
    }

    /**
     * @roseuid 3AB7EA5E02CF
     */
    public String getCommnet()
    {
        return  comment;
    }

    /**
     * @roseuid 3AB7EA5E02F7
     */
    public void setComment(String com)
    {
        comment=com;
    }
    //METODO QUE AÑADE

}
