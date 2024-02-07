package GUI;
import Algoritmos.basedeDatos;
import Repositorio.*;
import java.io.File;
import java.util.Properties;
import org.odmg.ODMGException;
import org.odmg.ODMGRuntimeException;
import org.odmg.ObjectNameNotUniqueException;
import org.odmg.DatabaseNotFoundException;
import com.poet.odmg.Database;
import javax.swing.UIManager;
import java.awt.*;
import com.poet.odmg.*;
import com.poet.odmg.util.*;
import com.poet.odmg.imp.*;
import java.util.*;

/**
 * Título:
 * Descripcion:
 * Copyright:    Copyright (c) 2001
 * Empresa:
 * @author
 * @version 1.0
 */

public class Principal
{
    boolean packFrame = false;
    //  public static String BASEDEDATOS="FastObjects://LOCAL/C:/DOCPROYECTOFINDECARRERA/PRUEBAKK/Repositorio_base";
    /**Construir la aplicación*/
    public Principal()
    {
        GUIPrincipal frame = new GUIPrincipal();
        //Validar marcos que tienen tamaños preestablecidos
        //Empaquetar marcos que cuentan con información de tamaño preferente útil. Ej. de su diseño.
        if (packFrame)
        {
            frame.pack();
        }
        else
        {
            frame.validate();
        }
        //Centrar la ventana
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = frame.getSize();
        if (frameSize.height > screenSize.height)
        {
            frameSize.height = screenSize.height;
        }
        if (frameSize.width > screenSize.width)
        {
            frameSize.width = screenSize.width;
        }
        frame.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
        frame.setVisible(true);
    }
    /**Método Main*/
    public static void main(String[] args)
    {
        //Abrimos la base de datos.
        Database db=new Database();
        try
        {
            db.open(basedeDatos.BASEDEDATOS,basedeDatos.MODO_BASEDEDATOS);
        }catch(ODMGException ex)
        {            ex.printStackTrace();}
        //Limpiamos base de datos.
        basedeDatos.clear();
        //Cargamos los datos en la base de datos.
        basedeDatos.loadData();
        //Funciones propias del diseño de la aplicacion.
        try
        {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        new Principal();
    }
}