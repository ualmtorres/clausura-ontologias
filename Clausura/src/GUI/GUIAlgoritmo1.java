package GUI;
import Repositorio.*;
import Algoritmos.basedeDatos;
import java.awt.*;
import java.*;
import org.odmg.ODMGException;
import java.util.Vector;
import org.odmg.ObjectNameNotUniqueException;
import com.poet.odmg.imp.*;
import javax.swing.*;
import com.borland.jbcl.layout.*;
import javax.swing.border.*;
import Algoritmos.ClausuraAmpliacion;
import java.awt.event.*;
import java.util.*;
import javax.swing.event.*;
import com.poet.odmg.*;
import com.poet.odmg.util.*;
import org.odmg.QueryException;

/**
 * Título:
 * Descripcion:
 * Copyright:    Copyright (c) 2001
 * Empresa:
 * @author
 * @version 1.0
 *
 */


public class GUIAlgoritmo1 extends JPanel
{
    XYLayout xYLayout1 = new XYLayout();
    JPanel jPanel1 = new JPanel();
    Border border1;
    TitledBorder titledBorder1;
    JPanel jPanel2 = new JPanel();
    Border border2;
    TitledBorder titledBorder2;
    XYLayout xYLayout3 = new XYLayout();
    JButton jBCopiar = new JButton();
    JScrollPane jSPListaClases = new JScrollPane();
    JList jLClases = new JList(new DefaultListModel());
    JScrollPane jScrollPane1 = new JScrollPane();
    JList jLClasesSeleccionadas = new JList(new DefaultListModel());
    JComboBox jCModulos = new JComboBox();
    TitledBorder titledBorder3;
    JButton jButton1 = new JButton();
    TitledBorder titledBorder4;
    JPanel jPanel3 = new JPanel();
    XYLayout xYLayout2 = new XYLayout();
    TitledBorder titledBorder5;
    Object [] seleccionados;
    JTextPane jTPResultados = new JTextPane();
    XYLayout xYLayout4 = new XYLayout();
    JScrollPane jScrollPane2 = new JScrollPane();
    JScrollPane jScrollPane3 = new JScrollPane();
    JTextPane jTPInterfaces = new JTextPane();
    Label label1 = new Label();
    Label label2 = new Label();
    JPanel jPEsquemaNuevo = new JPanel();
    XYLayout xYLayout5 = new XYLayout();
    Border border3;
    JTextField jTEsquemaNuevo = new JTextField();
    Border border4;
    DefaultListModel modelClases=(DefaultListModel)jLClases.getModel();
    DefaultListModel modelCSeleccionadas=(DefaultListModel)jLClasesSeleccionadas.getModel();
    JButton jBBorrar = new JButton();
    JButton jBGrafo = new JButton();
    Border border5;
    JButton jButton2 = new JButton();
    Border border6;

    public GUIAlgoritmo1()
    {
        try
        {
            jbInit();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    private void jbInit() throws Exception
    {
        border1 = BorderFactory.createEtchedBorder(Color.white,new Color(148, 145, 140));
        titledBorder1 = new TitledBorder(BorderFactory.createEtchedBorder(Color.white,new Color(148, 145, 140)),"Clases del Esquema");
        border2 = BorderFactory.createEtchedBorder(Color.white,new Color(148, 145, 140));
        titledBorder2 = new TitledBorder(BorderFactory.createEtchedBorder(Color.white,new Color(148, 145, 140)),"Esquemas");
        titledBorder3 = new TitledBorder(BorderFactory.createEtchedBorder(Color.white,new Color(148, 145, 140)),"Resultados");
        titledBorder4 = new TitledBorder("");
        titledBorder5 = new TitledBorder("");
        border3 = new TitledBorder(BorderFactory.createEtchedBorder(Color.white,new Color(148, 145, 140)),"Nombre del Nuevo Esquema");
        border4 = BorderFactory.createEtchedBorder(Color.white,new Color(178, 178, 178));
        border5 = BorderFactory.createEtchedBorder(Color.white,new Color(148, 145, 140));
        border6 = BorderFactory.createEtchedBorder(Color.white,new Color(148, 145, 140));
        this.setLayout(xYLayout1);
        xYLayout1.setWidth(583);
        xYLayout1.setHeight(300);
        jPanel1.setBorder(titledBorder1);
        jPanel1.setLayout(xYLayout2);
        jPanel2.setBorder(titledBorder2);
        jPanel2.setLayout(xYLayout3);
        jBCopiar.setBorder(BorderFactory.createEtchedBorder());
        jBCopiar.setText("Copiar >>");
        jBCopiar.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                jBCopiar_actionPerformed(e);
            }
        });
        jLClases.setBorder(BorderFactory.createEtchedBorder());
        jLClases.setToolTipText("Clases del esquema seleccionado");
        jLClasesSeleccionadas.setBackground(SystemColor.window);
        jLClasesSeleccionadas.setBorder(BorderFactory.createEtchedBorder());
        jLClasesSeleccionadas.setToolTipText("Clases Seleccionadas");
        this.addAncestorListener(new javax.swing.event.AncestorListener()
        {
            public void ancestorAdded(AncestorEvent e)
            {
                this_ancestorAdded(e);
            }
            public void ancestorRemoved(AncestorEvent e)
            {
            }
            public void ancestorMoved(AncestorEvent e)
            {
            }
        });
        jCModulos.setBackground(SystemColor.inactiveCaptionText);
        jCModulos.setAutoscrolls(true);
        jCModulos.setBorder(BorderFactory.createEtchedBorder());
        jCModulos.setToolTipText("Esquemas en la Base de datos");
        jCModulos.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                jCModulos_actionPerformed(e);
            }
        });
        jCModulos.addFocusListener(new java.awt.event.FocusAdapter()
        {
            public void focusGained(FocusEvent e)
            {
                jCModulos_focusGained(e);
            }
        });
        this.addComponentListener(new java.awt.event.ComponentAdapter()
        {
            public void componentShown(ComponentEvent e)
            {
                this_componentShown(e);
            }
        });
        jButton1.setBorder(titledBorder4);
        jButton1.setToolTipText("Ejecutar algoritmo");
        jButton1.setText("Ejecutar Algoritmo");
        jButton1.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                jButton1_actionPerformed(e);
            }
        });
        jPanel3.setBorder(titledBorder3);
        jPanel3.setLayout(xYLayout4);
        jTPResultados.setToolTipText("Clases Necesarias");
        jTPResultados.setBorder(BorderFactory.createEtchedBorder());
        jTPResultados.setEditable(false);
        jTPInterfaces.setToolTipText("Interfaces Necesarias");
        jTPInterfaces.setBorder(BorderFactory.createEtchedBorder());
        jTPInterfaces.setEditable(false);
        label1.setText("Clases:");
        label2.setText("Interfaces:");
        jPEsquemaNuevo.setLayout(xYLayout5);
        jPEsquemaNuevo.setBorder(border3);
        jTEsquemaNuevo.setBorder(border4);
        jTEsquemaNuevo.setToolTipText("Introduce el nombre para el nuevo esquema que se generará");
        jBBorrar.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                jBBorrar_actionPerformed(e);
            }
        });
        jBBorrar.setText("<<Copiar");
        jBBorrar.setBorder(BorderFactory.createEtchedBorder());
        jBGrafo.setBorder(border5);
        jBGrafo.setToolTipText("Muestra el resultado Gráficamente");
        jBGrafo.setText("Mostrar Resultado");
        jBGrafo.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                jBGrafo_actionPerformed(e);
            }
        });
        jButton2.setBorder(border6);
        jButton2.setText("Generar Informe");
        jButton2.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                jButton2_actionPerformed(e);
            }
        });
        this.add(jPanel1, new XYConstraints(223, 26, 355, 143));
        jPanel1.add(jSPListaClases, new XYConstraints(0, 0, 114, 115));
        jPanel1.add(jScrollPane1, new XYConstraints(228, 0, 114, 115));
        jPanel1.add(jBCopiar, new XYConstraints(139, 31, 61, 25));
        jPanel1.add(jBBorrar, new XYConstraints(138, 66, 61, 25));
        jScrollPane1.getViewport().add(jLClasesSeleccionadas, null);
        this.add(jPanel3, new XYConstraints(3, 173, 576, 121));
        jPanel3.add(jScrollPane3, new XYConstraints(376, 0, 168, 86));
        jPanel3.add(jScrollPane2, new XYConstraints(69, 0, 162, 89));
        jPanel3.add(label1, new XYConstraints(18, 1, 45, -1));
        jPanel3.add(label2, new XYConstraints(307, 0, 67, 22));
        jPanel3.add(jBGrafo, new XYConstraints(245, 56, 109, 28));
        jPanel3.add(jButton2, new XYConstraints(241, 24, 118, 28));
        this.add(jPanel2, new XYConstraints(8, 75, 211, 51));
        jPanel2.add(jCModulos, new XYConstraints(8, 1, 181, 16));
        this.add(jPEsquemaNuevo, new XYConstraints(11, 22, 204, 46));
        jPEsquemaNuevo.add(jTEsquemaNuevo, new XYConstraints(3, 0, 180, 17));
        this.add(jButton1, new XYConstraints(56, 135, 118, -1));
        jScrollPane2.getViewport().add(jTPResultados, null);
        jScrollPane3.getViewport().add(jTPInterfaces, null);
        jSPListaClases.getViewport().add(jLClases, null);


    }

    void jBCopiar_actionPerformed(ActionEvent e)
    {
        seleccionados=jLClases.getSelectedValues();
        //  jLClasesSeleccionadas.setListData(seleccionados);
        for (int i=0;i<seleccionados.length;i++)
        {
            modelCSeleccionadas.addElement(seleccionados[i]);
            modelClases.removeElement(seleccionados[i]);
        }

    }

    void this_ancestorAdded(AncestorEvent e)
    {


    }

    void this_componentShown(ComponentEvent e)
    {


    }

    void jCModulos_focusGained(FocusEvent e)
    {
        Module m=new Module("kk");
        BagOfObject s = new BagOfObject();
        s=m.getAllModules();
        jCModulos.removeAllItems();
        Iterator ite=s.iterator();
        while (ite.hasNext())
        {
            Object obj=ite.next();
            m=(Module)obj;
            jCModulos.addItem(m.getName());
        }

    }

    //***********************************************************//
    //******Llama al Algoritmo con las clases seleccionadas.*****//
    //***********************************************************//
    void jButton1_actionPerformed(ActionEvent e)
    {
        Clase auxC= new Clase();
        SetOfObject relacion= new SetOfObject();
        SetOfObject resultados=new SetOfObject();
        String Texto= new String();
        String TextoInterfaz=new String();
        basedeDatos.setNombreEsquemaNuevo(jTEsquemaNuevo.getText());
        String nombremod= basedeDatos.getNombreEsquemaNuevo();
        Object Volcados;

        //En Seleccionados tengo todos los valores de las clases a buscar.
        for (int i=0;i<modelCSeleccionadas.getSize();i++)
        {
            //String nombre=(String)seleccionados[i];
            String nombre=(String)modelCSeleccionadas.getElementAt(i);
            //Llamamos a la funcion que obtiene la clase de la bbdd.
            relacion.add(auxC.getClase(nombre));

        }

        //Pruebas para la clausura con ampliacion.
        Module pepe= new Module("modulin");

        //Obtengo el nombre del esquema que necesito ahora, del combobox.

        String NombreModulo=(String)jCModulos.getSelectedItem();
        ///************LLAMADA A LA CLAUSURA CON AMPLIACION*************////
        ClausuraAmpliacion Algoritmo = new ClausuraAmpliacion();
        resultados=Algoritmo.EnlargementClousore(relacion,relacion,pepe.getModule(NombreModulo));
        Database db = new Database();
        Database db2 =new Database();
        Vector kusk= new Vector();//Vector para almacenar los nombres de los objetos clase.
        Vector inter=new Vector();//Vector para almacenar los nombres de los objetos interfaz.
        Clase prueba= new Clase();
        Interfaz pruebaI= new Interfaz();
        Module nuevoModulo= new Module(nombremod);

        Iterator it=resultados.iterator();

        while(it.hasNext())

        {            Clase kaki=new Clase();
            Interfaz Iaux=new Interfaz();
            Volcados=it.next();
            String kk =kaki.getClass().toString();
            if(Volcados.getClass().toString().equalsIgnoreCase(kk))

            {                Clase Cx=new Clase();
                Cx=(Clase)Volcados;
                kusk.add(Cx.getName());
                Texto+=((Clase)Volcados).getName();
                Texto+="\n";
            }
            else
            {
                Interfaz Cx=new Interfaz();
                Cx=(Interfaz)Volcados;
                inter.add(Cx.getName());
                TextoInterfaz+=((Interfaz)Volcados).getName();
                TextoInterfaz+="\n";
            }

        }
        jTPResultados.setText(Texto);
        jTPInterfaces.setText(TextoInterfaz);
        //Cerrar abrir otra vez la base de datos y guardar el modulo....

        try
        {
            db=Database.current();
            db.close();
            db2.open(basedeDatos.BASEDEDATOS, Database.OPEN_READ_WRITE);

        }
        catch ( ODMGException x )
        {
            x.printStackTrace();
        }

        try
        {
            Transaction tx= new Transaction();
            tx.begin();
            //Creamos el primer módulo o esquema que servirá de ejemplo.
            db2.bind(nuevoModulo,nombremod);
            tx.commit();
        }catch (ObjectNameNotUniqueException ex)
        {
            ex.printStackTrace();
        }

        for (int i=0;i<modelCSeleccionadas.getSize();i++)
        {
            kusk.addElement((String)modelCSeleccionadas.getElementAt(i));

        }

        //Asocio las Clases al nuevo modulo.
        for(int i=0;i<kusk.size();i++)
        {

            prueba.getClaseAddUsedIn((String)kusk.elementAt(i),nuevoModulo);

        }
        //Asocio las interfaces al nuevo módulo.
        for(int j=0;j<inter.size();j++)
        {

            pruebaI.getInterfazAddUsedIn((String)inter.elementAt(j),nuevoModulo);

        }
        //Meter las relaciones en el nuevo esquema.

        /**Relaciones de Herencia  Extent.....(Para cada par de clases)
         * Añado las clases seleccionadas en el esquema si no no encontraria la relacion extent Client-->Worker
         *
         */
        //    for (int i=0;i<seleccionados.length;i++)
        //    {
        //       kusk.addElement((String)seleccionados[i]);
        //    }


        ModuleClasses anadir=new ModuleClasses();
        for(int i=0;i<kusk.size();i++)
        {
            for(int j=0;j<kusk.size();j++)
            {
                //Coger cada clase con todas las demas averiguar si existe relacion en el modulo origen y añadir la relacion al nuevo.
                if(i!=j)
                {
                    anadir.getModuleCLAddToModule((String)kusk.elementAt(i),(String)kusk.elementAt(j),NombreModulo,nombremod);//Origen destino
                }
            }
        }
        ModuleInterfaces anadirInter=new ModuleInterfaces();
        //Relaciones de Herencia ISA entre clase....Interfaz
        for(int j=0;j<inter.size();j++)
        {
            for(int i=0;i<kusk.size();i++)
            {

                anadirInter.getModuleINAddToModule((String)inter.elementAt(j),(String)kusk.elementAt(i),NombreModulo,nombremod);
            }
        }
        //Relaciones de Herencia ISA entre Interfaz....Interfaz
        for(int i=0;i<inter.size();i++)
        {
            for(int j=0;j<inter.size();j++)
            {
                //Coger cada clase con todas las demas averiguar si existe relacion en el modulo origen y añadir la relacion al nuevo.
                if(i!=j)
                {
                    anadirInter.getModuleINAddToModule((String)inter.elementAt(j),(String)kusk.elementAt(i),NombreModulo,nombremod);
                }

            }
        }


        //Relaciones Relationships.... Incluirlas en el modulo.
        Relationship auxRelat=new Relationship();
        for(int i=0;i<kusk.size();i++)
        {
            for(int j=0;j<kusk.size();j++)
            {
                //y ver si existe una relación en el orden establecido.
                if(i!=j)
                {
                    auxRelat.getRelationshipAddToModule((String)kusk.elementAt(i),(String)kusk.elementAt(j),NombreModulo,nombremod);
                }

            }
        }

        jTEsquemaNuevo.setText("");
    }

    //***************************************************************//
    //Rellenará el cuadro de clases del modulo con las clases del módulo.
    void jCModulos_actionPerformed(ActionEvent e)
    {
        Vector lista= new Vector();
        Clase aux = new Clase();
        Module auxM= new Module("kk");
        Iterator auxIT;
        //Coger el modulo del desplegable de los módulos.
        String md=(String)jCModulos.getSelectedItem();

        //jLClases.removeAll();
        modelClases.removeAllElements();
        modelCSeleccionadas.removeAllElements();

        auxIT=aux.getAllClases(md).iterator();
        while(auxIT.hasNext())
        {
            modelClases.addElement(((Clase)auxIT.next()).getName());

        }
        jLClases.enable();
        //    jLClases.setListData(lista);
    }
    void jBBorrar_actionPerformed(ActionEvent e)
    {
        seleccionados=jLClasesSeleccionadas.getSelectedValues();
        //  jLClasesSeleccionadas.setListData(seleccionados);
        for (int i=0;i<seleccionados.length;i++)
        {
            modelClases.addElement(seleccionados[i]);
            modelCSeleccionadas.removeElement(seleccionados[i]);
        }
    }

    //Crea el grafo de representacion
    public void crearGrafo(String nombreModulo)
    {
        //Variables para obtener cada uno de los elementos.
        BagOfObject clasesModulo;
        BagOfObject interfacesModulo;
        BagOfObject clasesDModulo;
        BagOfObject interfacesDModulo;
        String resultados="";
        String modulo=nombreModulo;
        //Método que obtiene todas las clases, interfaces y derivadas con relaciones del tipo a-b.
        System.out.println("El NOMBRE DEL MODULO: "+modulo);
        clasesModulo=(new Clase()).getAllClases(modulo);
        interfacesModulo=(new Interfaz()).getAllInterfaces(modulo);
        clasesDModulo=(new DerivedClass("aux")).getAllDerivedClass(modulo);
        interfacesDModulo=(new DerivedInterface("auxI")).getAllDerivedInterface(modulo);
        //Solucionar el problema de cuando no existan relaciones se sigan pintando las clases.
        Iterator Mismo=clasesModulo.iterator();
        while(Mismo.hasNext())
        {
            String nombreClase= ((Clase)Mismo.next()).getName();
            resultados=resultados+","+nombreClase+"-"+nombreClase+"/0";
        }
        //Interfaces
        Mismo=interfacesModulo.iterator();
        while(Mismo.hasNext())
        {
            String nombre= ((Interfaz)Mismo.next()).getName();
            resultados=resultados+","+nombre+"-"+nombre+"/10";
        }
        //Clases derivadas
        Mismo=clasesDModulo.iterator();
        while(Mismo.hasNext())
        {
            String nombre= ((DerivedClass)Mismo.next()).getName();
            resultados=resultados+","+nombre+"-"+nombre+"/8";
        }
        //Interfaces Derivadas
        Mismo=interfacesDModulo.iterator();
        while(Mismo.hasNext())
        {
            String nombre= ((DerivedInterface)Mismo.next()).getName();
            resultados=resultados+","+nombre+"-"+nombre+"/10";
        }


        //Obtener quienes tiene relacion con quien.  e introducirlas en la cadena REsultado
        //Caso 1º clase extent o Relation clase y viceversa.
        Iterator I_Clases=clasesModulo.iterator();
        Iterator I_Clases2=null;
        while(I_Clases.hasNext())
        {
            String nombreClase= ((Clase)I_Clases.next()).getName();
            I_Clases2=clasesModulo.iterator();
            while(I_Clases2.hasNext())
            {
                String nombreClase2= ((Clase)I_Clases2.next()).getName();
                //Ver si existe relacion extent
                System.out.println("CLASE - CLASE: "+nombreClase+" - "+nombreClase2);
                if((new Clase()).isExtenderOf(nombreClase2,nombreClase,modulo))
                {
                    //nombreClase2--->nombreClase.
                    resultados=resultados+","+nombreClase2+"-"+nombreClase+"/0";
                }
                //Ver si existe una relación entre las dos clases.
                if((new Clase()).isRelationship(nombreClase,nombreClase2,modulo))
                {
                    resultados=resultados+","+nombreClase+"-"+nombreClase2+"/1";
                }

            }

        }

        //CASO 1º B Extent DerivedClass-DerivedClass
        Iterator I_DClass=clasesDModulo.iterator();;
        Iterator I_DClass2=null;
        while(I_DClass.hasNext())
        {
            String nombreDClase= ((DerivedClass)I_DClass.next()).getName();
            I_DClass2=clasesDModulo.iterator();
            while(I_DClass2.hasNext())
            {
                String nombreDClase2= ((DerivedClass)I_DClass2.next()).getName();
                //Ver si existe relacion extent
                if((new Clase()).isExtenderOf(nombreDClase2,nombreDClase,modulo))
                {
                    //nombreClase2--->nombreClase.
                    resultados=resultados+","+nombreDClase2+"-"+nombreDClase+"/8";
                }
                //Ver si existe una relación entre las dos clases.
                if((new Clase()).isRelationship(nombreDClase,nombreDClase2,modulo))
                {
                    resultados=resultados+","+nombreDClase+"-"+nombreDClase2+"/9";
                }

            }

        }



        //Caso 2º Clase extent o Relation CLASE-DerivedClass y viceversa.
        I_Clases=clasesModulo.iterator();
        while(I_Clases.hasNext())
        {
            String nombreClase=((Clase)I_Clases.next()).getName();
            I_DClass=clasesDModulo.iterator();
            while(I_DClass.hasNext())
            {
                String nombreDClases=((DerivedClass)I_DClass.next()).getName();
                //Comprobar si CLASE EXTENT DCLASE Y VICE
                if((new Clase()).isExtenderOf(nombreClase,nombreDClases,modulo))
                {
                    //nombreClase--->nombreDClases.
                    resultados=resultados+","+nombreClase+"-"+nombreDClases+"/2";
                }
                if((new Clase()).isExtenderOf(nombreDClases,nombreClase,modulo))
                {
                    //nombreDClases--->nombreClase.
                    resultados=resultados+","+nombreDClases+"-"+nombreClase+"/4";
                }

                //COMPROBAR SI CLASE RELATIONSHIP DCLASE y vice
                if((new Clase()).isRelationship(nombreDClases,nombreClase,modulo))
                {
                    resultados=resultados+","+nombreDClases+"-"+nombreClase+"/5";
                }
                if((new Clase()).isRelationship(nombreClase,nombreDClases,modulo))
                {
                    resultados=resultados+","+nombreClase+"-"+nombreDClases+"/3";
                }

            }
        }//Fin caso 2
        //Caso 6º Clase Isa Interfaz.

        I_Clases=clasesModulo.iterator();
        Iterator I_Interfaz=null;
        while(I_Clases.hasNext())
        {
            String nombreClase=((Clase)I_Clases.next()).getName();
            I_Interfaz=interfacesModulo.iterator();
            while(I_Interfaz.hasNext())
            {
                String nombreInterfaz=((Interfaz)I_Interfaz.next()).getName();
                if((new Clase()).isSubtypeOf(nombreInterfaz,nombreClase,modulo))
                {
                    resultados=resultados+","+nombreClase+"-"+nombreInterfaz+"/06";
                }

            }


        }

        //caso 7º DClase Isa Interfaz.
        I_DClass=clasesDModulo.iterator();
        while(I_DClass.hasNext())
        {
            String nombreDClase=((DerivedClass)I_DClass.next()).getName();
            I_Interfaz=interfacesModulo.iterator();
            while(I_Interfaz.hasNext())
            {
                String nombreInterfaz=((Interfaz)I_Interfaz.next()).getName();
                if((new Clase()).isSubtypeOf(nombreInterfaz,nombreDClase,modulo))
                {
                    resultados=resultados+","+nombreDClase+"-"+nombreInterfaz+"/07";
                }
            }
        }


        // Construccion de la cadena que texto.
        //Pasarselo al grafo como cadena.
        System.out.println("TIENE ESTAS RELACIONES: "+resultados);
        GUIGrafo dlg = new GUIGrafo(resultados,modulo);
        Dimension dlgSize = dlg.getPreferredSize();
        Dimension frmSize = Toolkit.getDefaultToolkit().getScreenSize();;
        Point loc = getLocation();
        dlg.setLocation((frmSize.width - dlgSize.width) / 2 + loc.x, (frmSize.height - dlgSize.height) / 2 + loc.y);
        dlg.setModal(true);
        dlg.show();
    }


    void jBGrafo_actionPerformed(ActionEvent e)
    {
        this.crearGrafo((String)jCModulos.getSelectedItem());
    }

    void jButton2_actionPerformed(ActionEvent e)
    {
        basedeDatos.generarInforme("Informe.pdf",(String)jCModulos.getSelectedItem());

        GUIVisorAdobe a= new GUIVisorAdobe("Informe.pdf");
    }

}