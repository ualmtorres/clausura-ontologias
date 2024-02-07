//Source file: C:\\DOCPROYECTOFINDECARRERA\\PRUEBAKK\\sintitulo2\\src\\Repositorio\\ModuleInterfaces.java

package Repositorio;
import com.poet.odmg.util.*;
import java.util.*;
import com.poet.odmg.*;
import org.odmg.QueryException;
import org.odmg.ObjectNameNotUniqueException;
import com.poet.odmg.imp.*;

public class ModuleInterfaces
{
    public Module InModule;
    public GenericInterface CorrespondsToSubtypes;
    public GenericInterface CorrespondsToSupertypes;

    /**
     * Constructores, tenemos dos uno por cada modalidad de
     * herencia ISA que puede haber, Interfaz--->Interfaz
     * Clase--->Interfaz
     */
    public ModuleInterfaces()
    {
    }
    public ModuleInterfaces(GenericInterface i,GenericClass c,Module m )
    {
        this.setModule(m);
        this.setCTSupertypes(i);
        this.setCTSubtypes(c);//Una interfaz nunca puede heredar de una clase o una clase derivada, por ello la clase siempre es subtipo.

    }

    public ModuleInterfaces(GenericInterface i1,GenericInterface i2, Module m )
    {
        this.setModule(m);
        this.setCTSupertypes(i1);
        this.setCTSubtypes(i2);
    }

    /**********Métodos de la clase**************/

    //Establece el modulo
    public void setModule(Module m)
    {
        this.InModule=m;
        m.addSubtypes(this);//Relleno el inverso es decir le digo al modulo que estoy en su ámbito
    }
    //Establece la clase o la interfaz que hereda el comportamiento.
    public void setCTSubtypes(GenericInterface i)
    {
        this.CorrespondsToSubtypes=i;
        i.addInherits(this);//Relleno la clase o la interfaz indicando q ISA de Una interfaz.(Le guardo la relacion entera)
    }
    //Establece la interfaz desde la que se hereda el comportamiento.
    public void setCTSupertypes(GenericInterface i)
    {
        this.CorrespondsToSupertypes=i;
        i.addDerives(this);//Le indica a la Interfaz que de ella estan "heredando comportamiento"
    }

    //Funciones para obtener cada uno de los elementos del objeto
    //Obtiene el modulo del objeto
    public Module getModule()
    {
        return this.InModule;
    }
    //Obtiene el subtipo
    public GenericInterface getCTSubtypes()
    {
        return this.CorrespondsToSubtypes;
    }
    //Obtiene el supertipo.
    public GenericInterface getCTSupertypes()
    {
        return this.CorrespondsToSupertypes;
    }

    /**INTRODUCE LAS RELACIONES DE HERENCIA DE LAS ISA inter1, inter2 QUE ESTAN
     * EN EL MODULO ModuloOr EN EL MODULO DESTINO ModuloDest.
     */

    public void getModuleINAddToModule(String inter1, String inter2, String ModuloOr, String ModuloDest)
    {
        //Obtengo el Objeto Modulo.
        ModuleInterfaces devolver = new ModuleInterfaces();
        Module Destino= new Module("kaki");
        //CONSULTAMOS EL MODULO DESTINO
        try
        {
            Object resultado;
            Transaction tx3= new Transaction();
            Properties props = new Properties();
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

        //Consultamos la LOS MODULEInterfaces
        try
        {
            Object resultado;
            Transaction tx2= new Transaction();
            tx2.begin();
            ObjectServices.current().awake(Destino);
            OQLQuery Query2 = new OQLQuery(tx2);
            Query2.create("SELECT  * FROM ModuleInterfacesExtent AS a WHERE a.CorrespondsToSupertypes.name =$1 AND a.CorrespondsToSubtypes.name =$2  AND a.InModule.name = $3");
            Query2.bind(inter1);//$1
            Query2.bind(inter2);//$2
            Query2.bind(ModuloOr);//$3
            String kk=Query2.toString();
            resultado = Query2.execute();
            BagOfObject relacion2=(BagOfObject) resultado;
            Iterator it= relacion2.iterator();
            while(it.hasNext())
            {
                devolver=(ModuleInterfaces)it.next();
                ModuleInterfaces Nuevo = new ModuleInterfaces(devolver.getCTSupertypes(),devolver.getCTSubtypes(),Destino);
                try
                {
                    Database db = Database.current();
                    db.bind(Nuevo,null);
                } catch (ObjectNameNotUniqueException ex)
                {
                    ex.printStackTrace();
                }
            }


            tx2.commit();
        }catch (QueryException ex)
        {
            ex.printStackTrace();
        }
    }//Fin del metodo.


}
