//Source file: C:\\DOCPROYECTOFINDECARRERA\\PRUEBAKK\\sintitulo2\\src\\Repositorio\\ModuleClasses.java

package Repositorio;
import com.poet.odmg.util.*;
import java.util.*;
import com.poet.odmg.*;
import org.odmg.QueryException;
import org.odmg.ObjectNameNotUniqueException;
import com.poet.odmg.imp.*;

public class ModuleClasses
{
    public Module InModule;
    public GenericClass CorrespondsToSubclasses;
    public GenericClass CorrespondsToSuperclasses;

    /**
     * Constructor de la clase.Primero se pasa la superclase despues la
     * subclase y por último el módulo.
     */
    public ModuleClasses()
    {
    }
    public ModuleClasses(GenericClass c1,GenericClass c2, Module m)
    {
        this.setModule(m);
        this.setCTSuperclasses(c1);
        this.setCTSubclasses(c2);
    }

    /***************Métodos de la clase***********************/
    public void setModule(Module m)
    {
        this.InModule=m;
        m.addSubClasses(this);//relleno el inverso es decir Module.SubClasses
    }

    public void setCTSubclasses(GenericClass c)
    {
        this.CorrespondsToSubclasses=c;
        c.addExtender(this);//se le indica que hereda de alguna clase por eso se guarda en extender
    }

    public void setCTSuperclasses(GenericClass c)
    {
        this.CorrespondsToSuperclasses=c;
        c.addExtensions(this);//Se le indican que de el hereda alguna clase.
    }

    /*******Métodos para obtener las propiedades del objeto*********/
    public Module getModule()
    {
        return this.InModule;
    }

    public GenericClass getCTSubclasses()
    {
        return this.CorrespondsToSubclasses;
    }

    public GenericClass getCTSuperclasses()
    {
        return this.CorrespondsToSuperclasses;
    }

    /**INTRODUCE LAS RELACIONES DE HERENCIA DE LAS CLASES clas1, clas2 QUE ESTAN
     * EN EL MODULO ModuloOr EN EL MODULO DESTINO ModuloDest.
     */

    public void getModuleCLAddToModule(String clas1, String clas2, String ModuloOr, String ModuloDest)
    {
        //Obtengo el Objeto Modulo.
        ModuleClasses devolver = new ModuleClasses();
        Module Destino= new Module("kaki");
        //CONSULTAMOS EL MODULO DESTINO
        // Destino=Destino.getModule(ModuloDest);
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

        //Consultamos la LOS MODULECLASSES
        try
        {
            Object resultado;
            Transaction tx2= new Transaction();
            tx2.begin();
            ObjectServices.current().awake(Destino);
            OQLQuery Query2 = new OQLQuery(tx2);
            Query2.create("SELECT  * FROM ModuleClassesExtent AS a WHERE a.CorrespondsToSuperclasses.name =$1 AND a.CorrespondsToSubclasses.name =$2  AND a.InModule.name = $3");
            Query2.bind(clas1);//$1
            Query2.bind(clas2);//$2
            Query2.bind(ModuloOr);//$3
            String kk=Query2.toString();
            resultado = Query2.execute();
            BagOfObject relacion2=(BagOfObject) resultado;
            Iterator it= relacion2.iterator();
            while(it.hasNext())
            {
                devolver=(ModuleClasses)it.next();
                ModuleClasses Nuevo = new ModuleClasses(devolver.getCTSuperclasses(),devolver.getCTSubclasses(),Destino);
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
