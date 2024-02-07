package GUI;
import Algoritmos.ClausuraMixta;
import Algoritmos.basedeDatos;
import Repositorio.*;
import java.*;
import java.awt.*;
import java.util.*;
import java.util.Vector;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import com.borland.jbcl.layout.*;
import com.poet.odmg.*;
import com.poet.odmg.util.*;
import com.poet.odmg.imp.*;
import com.lowagie.text.*;
import org.odmg.QueryException;
import org.odmg.ODMGException;
import org.odmg.ODMGRuntimeException;
import org.odmg.ObjectNameNotUniqueException;
import org.odmg.ObjectNameNotFoundException;

/**
 * Título:
 * Descripcion:
 * Copyright:    Copyright (c) 2001
 * Empresa:
 * @author
 * @version 1.0
 */

public class GUIAlgoritmo3 extends JPanel
{

    Object [] seleccionadosClases; //En el Estaran los objetos seleccionados de los dos jLists
    Object [] seleccionadosInterfaces;
    XYLayout xYLayout1 = new XYLayout();
    JButton jBTransformable = new JButton();
    JScrollPane jSPResultadosClases = new JScrollPane();
    JPanel jPanel3 = new JPanel();
    JList jLClases = new JList(new DefaultListModel());
    JPanel jPanel1 = new JPanel();
    JComboBox jCModulos = new JComboBox();
    JScrollPane jSPListaClases = new JScrollPane();
    JScrollPane jScrollPane2 = new JScrollPane();
    JScrollPane jScrollPane1 = new JScrollPane();
    JList jLInterfaces = new JList(new DefaultListModel());
    JTextPane jTPResultadosClases = new JTextPane();
    JTextField jTNombreNuevoEsquema = new JTextField();
    JScrollPane jSPResultadosInterfaces = new JScrollPane();
    JTextPane jTPResultadosInterfacesD = new JTextPane();
    JButton jBInforme = new JButton();
    JScrollPane jSPClasesD = new JScrollPane();
    JList jLNoTransformable = new JList(new DefaultListModel());
    JList jLTransformable = new JList(new DefaultListModel());
    JPanel jPModulos = new JPanel();
    JLabel jLabel4 = new JLabel();
    JTextPane jTPResultadosInterfaces = new JTextPane();
    JLabel jLabel3 = new JLabel();
    JScrollPane jSPResultadosInterfacesD = new JScrollPane();
    JButton BEjecutar = new JButton();
    JLabel jLabel2 = new JLabel();
    JLabel jLabel1 = new JLabel();
    XYLayout xYLayout5 = new XYLayout();
    XYLayout xYLayout4 = new XYLayout();
    JPanel jPClases = new JPanel();
    XYLayout xYLayout3 = new XYLayout();
    JScrollPane jSPListaInterfaces = new JScrollPane();
    XYLayout xYLayout2 = new XYLayout();
    JTextPane jTPReultadosClasesD = new JTextPane();
    Border border1;
    TitledBorder titledBorder1;
    Border border2;
    TitledBorder titledBorder2;
    Border border3;
    Border border4;
    TitledBorder titledBorder3;
    Border border5;
    TitledBorder titledBorder4;
    Border border6;
    Border border7;
    Border border8;
    Border border9;
    JButton jBNTransformable = new JButton();
    JButton jBBorraTransformable = new JButton();
    JButton jBBorrarNTransformable = new JButton();
    //SON LOS MODELOS QUE SE USAN PARA TRABAJAR CON LAS JLIST
    DefaultListModel modelClases =(DefaultListModel)jLClases.getModel();
    DefaultListModel modelInterfaces =(DefaultListModel)jLInterfaces.getModel();
    DefaultListModel modelTransformable = (DefaultListModel)jLTransformable.getModel();
    DefaultListModel modelNTransformable = (DefaultListModel)jLNoTransformable.getModel();
    JButton JBGrafo = new JButton();
    Border border10;
    public GUIAlgoritmo3()
    {
        try
        {
            jbInit();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    void jbInit() throws Exception
    {
        border1 = BorderFactory.createEtchedBorder(Color.white,new Color(148, 145, 140));
        titledBorder1 = new TitledBorder(border1,"Nombre del nuevo esquema");
        border2 = BorderFactory.createEtchedBorder(Color.white,new Color(148, 145, 140));
        titledBorder2 = new TitledBorder(border2,"Esquemas");
        border3 = BorderFactory.createEtchedBorder(Color.white,new Color(148, 145, 140));
        border4 = BorderFactory.createEtchedBorder(Color.white,new Color(148, 145, 140));
        titledBorder3 = new TitledBorder(border4,"Resultados");
        border5 = BorderFactory.createEtchedBorder(Color.white,new Color(148, 145, 140));
        titledBorder4 = new TitledBorder(border5,"Clases e Interfaces del esquema");
        border6 = BorderFactory.createEtchedBorder(Color.white,new Color(148, 145, 140));
        border7 = BorderFactory.createEtchedBorder(Color.white,new Color(148, 145, 140));
        border8 = BorderFactory.createEtchedBorder(Color.white,new Color(178, 178, 178));
        border9 = BorderFactory.createEtchedBorder(Color.white,new Color(178, 178, 178));
        border10 = BorderFactory.createEtchedBorder(Color.white,new Color(148, 145, 140));
        jBTransformable.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                jBTransformable_actionPerformed(e);
            }
        });
        jBTransformable.setText("Transformable>>");
        jBTransformable.setBorder(border6);
        this.setLayout(xYLayout1);
        jPanel3.setLayout(xYLayout4);
        jPanel3.setFont(new java.awt.Font("Dialog", 1, 12));
        jPanel3.setBorder(titledBorder3);
        jLClases.setBorder(BorderFactory.createEtchedBorder());
        jLClases.setToolTipText("Clases del esquema seleccionado");
        jLClases.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseReleased(MouseEvent e)
            {
                jLClases_mouseReleased(e);
            }
        });
        jPanel1.setLayout(xYLayout5);
        jCModulos.setBorder(border9);
        jCModulos.setToolTipText("Esquema Seleccionado");
        jCModulos.addFocusListener(new java.awt.event.FocusAdapter()
        {
            public void focusGained(FocusEvent e)
            {
                jCModulos_focusGained(e);
            }
        });
        jCModulos.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                jCModulos_actionPerformed(e);
            }
        });
        jLInterfaces.setBorder(BorderFactory.createEtchedBorder());
        jLInterfaces.setToolTipText("Interfaces  del esquema seleccionado");
        jTPResultadosClases.setToolTipText("Clases Necesarias");
        jTPResultadosClases.setBorder(BorderFactory.createEtchedBorder());
        jTPResultadosClases.setEditable(false);
        jTPResultadosInterfacesD.setToolTipText("Interfaces Derivadas que han sido necesarias crear.");
        jTPResultadosInterfacesD.setBorder(BorderFactory.createEtchedBorder());
        jTPResultadosInterfacesD.setEditable(false);
        jBInforme.setBorder(border7);
        jBInforme.setToolTipText("Genera Informe del modulo Seleccionado/Creado");
        jBInforme.setText("Generar Informe");
        jBInforme.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                jBInforme_actionPerformed(e);
            }
        });
        jLNoTransformable.setBackground(SystemColor.window);
        jLNoTransformable.setBorder(BorderFactory.createEtchedBorder());
        jLNoTransformable.setToolTipText("Componentes No Transformables");
        jLTransformable.setBackground(SystemColor.window);
        jLTransformable.setBorder(BorderFactory.createEtchedBorder());
        jLTransformable.setToolTipText("Componentes Transformables");
        jPModulos.setBorder(titledBorder2);
        jPModulos.setToolTipText("Esquemas actualmente  definidos en la base de datos.");
        jPModulos.setLayout(xYLayout2);
        jLabel4.setText("Clases");
        jTPResultadosInterfaces.setEnabled(false);
        jTPResultadosInterfaces.setToolTipText("Interfaces Necesarias");
        jTPResultadosInterfaces.setBorder(BorderFactory.createEtchedBorder());
        jTPResultadosInterfaces.setEditable(false);
        jLabel3.setText("Interfaces");
        BEjecutar.setBorder(border3);
        BEjecutar.setText("Ejecutar Algoritmo");
        BEjecutar.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                BEjecutar_actionPerformed(e);
            }
        });
        jLabel2.setText("Interfaces Derivadas");
        jLabel1.setText("Clases Derivadas");
        jPClases.setLayout(xYLayout3);
        jTPReultadosClasesD.setToolTipText("Clases derivadas que han sido necesarias crear");
        jTPReultadosClasesD.setBorder(BorderFactory.createEtchedBorder());
        jTPReultadosClasesD.setEditable(false);
        xYLayout1.setWidth(584);
        xYLayout1.setHeight(310);
        jPanel1.setBorder(titledBorder1);
        jPClases.setBorder(titledBorder4);
        jTNombreNuevoEsquema.setBorder(border8);
        jBNTransformable.setBorder(border6);
        jBNTransformable.setText("No Transformable>>");
        jBNTransformable.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                jBNTransformable_actionPerformed(e);
            }
        });
        jBBorraTransformable.setBorder(border6);
        jBBorraTransformable.setText("<<Transformable");
        jBBorraTransformable.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                jBBorraTransformable_actionPerformed(e);
            }
        });
        jBBorrarNTransformable.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                jBBorrarNTransformable_actionPerformed(e);
            }
        });
        jBBorrarNTransformable.setText("<<No Transformable");
        jBBorrarNTransformable.setBorder(border6);
        JBGrafo.setBorder(border10);
        JBGrafo.setText("Mostrar Resultado");
        JBGrafo.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                JBGrafo_actionPerformed(e);
            }
        });
        this.add(jPanel3, new XYConstraints(7, 137, 569, 168));
        jPanel3.add(jSPResultadosInterfaces, new XYConstraints(355, 19, 165, 51));
        jPanel3.add(jSPResultadosInterfacesD, new XYConstraints(357, 86, 165, 53));
        jPanel3.add(jLabel2, new XYConstraints(356, 71, 119, 16));
        jPanel3.add(jSPResultadosClases, new XYConstraints(48, 16, 174, 53));
        jPanel3.add(jLabel1, new XYConstraints(49, 70, 107, -1));
        jPanel3.add(jSPClasesD, new XYConstraints(48, 86, 174, 54));
        jPanel3.add(jLabel3, new XYConstraints(355, 2, 69, -1));
        jPanel3.add(jLabel4, new XYConstraints(49, 0, 93, -1));
        jPanel3.add(jBInforme, new XYConstraints(237, 28, 103, 23));
        jPanel3.add(JBGrafo, new XYConstraints(235, 57, 109, 25));
        this.add(jPanel1, new XYConstraints(10, 7, 180, 45));
        jPanel1.add(jTNombreNuevoEsquema, new XYConstraints(5, 0, 152, 16));
        this.add(jPModulos, new XYConstraints(10, 53, 179, 49));
        jPModulos.add(jCModulos, new XYConstraints(5, 0, 155, 17));
        this.add(jPClases, new XYConstraints(193, 6, 383, 133));
        jPClases.add(jSPListaClases, new XYConstraints(4, 0, 114, 49));
        jPClases.add(jSPListaInterfaces, new XYConstraints(4, 53, 114, 52));
        jPClases.add(jScrollPane1, new XYConstraints(248, 0, 114, 48));
        jPClases.add(jScrollPane2, new XYConstraints(250, 57, 114, 48));
        jPClases.add(jBTransformable, new XYConstraints(130, 2, 103, 25));
        jPClases.add(jBBorraTransformable, new XYConstraints(130, 27, 103, 25));
        jPClases.add(jBNTransformable, new XYConstraints(126, 55, -1, 25));
        jPClases.add(jBBorrarNTransformable, new XYConstraints(126, 81, -1, 25));
        this.add(BEjecutar, new XYConstraints(36, 105, 120, 29));
        jScrollPane2.getViewport().add(jLNoTransformable, null);
        jScrollPane1.getViewport().add(jLTransformable, null);
        jSPListaInterfaces.getViewport().add(jLInterfaces, null);
        jSPListaClases.getViewport().add(jLClases, null);
        jSPClasesD.getViewport().add(jTPReultadosClasesD, null);
        jSPResultadosClases.getViewport().add(jTPResultadosClases, null);
        jSPResultadosInterfacesD.getViewport().add(jTPResultadosInterfacesD, null);
        jSPResultadosInterfaces.getViewport().add(jTPResultadosInterfaces, null);
    }
    void jBTransformable_actionPerformed(ActionEvent e)
    {
        seleccionadosClases=jLClases.getSelectedValues();
        for (int i=0;i<seleccionadosClases.length;i++)
        {
            modelTransformable.addElement(seleccionadosClases[i]);
            modelClases.removeElement(seleccionadosClases[i]);
        }
        seleccionadosInterfaces=jLInterfaces.getSelectedValues();
        for (int i=0;i<seleccionadosInterfaces.length;i++)
        {
            modelTransformable.addElement(seleccionadosInterfaces[i]);
            modelInterfaces.removeElement(seleccionadosInterfaces[i]);
        }

    }
    void jCModulos_focusGained(FocusEvent e)
    {
        Module m=new Module("ModuloAuxliar");
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
    //MUESTRA LOS RESULTADOS QUE GUARDA UN SETOFOBJECT
    void mostrarResultados(SetOfObject S)
    {
        Iterator I_S=S.iterator();
        GenericInterface auxGEIN=new GenericInterface();
        boolean encontrado=false;
        Clase auxCL=new Clase("auxCL");
        DerivedInterface auxDI= new DerivedInterface("auxDI");
        DerivedClass auxDC= new DerivedClass("auxDC");
        Interfaz auxIN=new Interfaz("auxIN");
        while(I_S.hasNext())
        {
            encontrado=false;
            auxGEIN=(GenericInterface)I_S.next();
            if((auxCL.isClase(auxGEIN.getName()))&&(!encontrado))
            {
                jTPResultadosClases.setText(jTPResultadosClases.getText()+auxGEIN.getName()+"\n");
                encontrado=true;
            }
            if((auxIN.isInterfaz(auxGEIN.getName()))&&(!encontrado))
            {
                jTPResultadosInterfaces.setText(jTPResultadosInterfaces.getText()+auxGEIN.getName()+"\n");
                encontrado=true;
            }
            if((auxDC.isDerivedClass(auxGEIN.getName()))&&(!encontrado))
            {
                jTPReultadosClasesD.setText(jTPReultadosClasesD.getText()+auxGEIN.getName()+"\n");
                encontrado=true;
            }
            if((auxDI.isDerivedInterface(auxGEIN.getName()))&&(!encontrado))
            {
                jTPResultadosInterfacesD.setText(jTPResultadosInterfacesD.getText()+auxGEIN.getName()+"\n");
                encontrado=true;
            }


        }

    }

    void jCModulos_focusLost(FocusEvent e)
    {

    }
    void jCModulos_actionPerformed(ActionEvent e)
    {
        Vector lista= new Vector();
        Vector listaInterfaces = new Vector();
        Clase aux = new Clase();
        Interfaz auxI=new Interfaz();
        Module auxM= new Module("Auxiliar");
        Iterator auxIT;//Iterador para coger todas las clases de ese modulo
        Iterator auxIN;//Iterador para coger todas las interfaces del modulo seleccionado.

        //Coger el modulo del desplegable de los módulos.
        String md=(String)jCModulos.getSelectedItem();

        modelClases.removeAllElements();
        modelInterfaces.removeAllElements();
        modelTransformable.removeAllElements();
        modelNTransformable.removeAllElements();

        auxIT=aux.getAllClases(md).iterator();
        auxIN=auxI.getAllInterfaces(md).iterator();
        //Guardo las clases en un vector para ponerlas en el cuadro correspondiente en la pantalla
        while(auxIT.hasNext())
        {

            modelClases.addElement(((Clase)auxIT.next()).getName());

        }

        //Guardo las interfaces en un vector para ponerlas en el cuadro correspondiente en la pantalla
        while(auxIN.hasNext())
        {
            modelInterfaces.addElement(((Interfaz)auxIN.next()).getName());

        }
        //Cargo las clases en el jList
        jLClases.enable();
        //  jLClases.setListData(lista);
        //Cargo las interfaces en el jList
        jLInterfaces.enable();
        //  jLInterfaces.setListData(listaInterfaces);

    }
    void BEjecutar_actionPerformed(ActionEvent e)
    {
        //Recoger lo que teiene las listas de transformable y no transformable... meterlas en S
        SetOfObject NT= new SetOfObject();
        SetOfObject T= new SetOfObject();
        SetOfObject S= new SetOfObject();
        Clase auxC= new Clase();
        Module auxM= new Module("aux");
        Interfaz auxI= new Interfaz();
        //Nombre del modulo que le voy a dar.
        basedeDatos.setNombreEsquemaNuevo(jTNombreNuevoEsquema.getText());
        //Limpio los cuadros de resultado.
        jTPResultadosClases.setText("");
        jTPResultadosInterfaces.setText("");
        jTPReultadosClasesD.setText("");
        jTPResultadosInterfacesD.setText("");

        //En modelTransformable tengo todos los valores de las clases/interfaces a buscar.

        for (int i=0;i<modelTransformable.getSize();i++)
        {
            String nombre=(String)modelTransformable.getElementAt(i);
            //Llamamos a la funcion que obtiene la clase de la bbdd.
            //Comprobar si es una clase o una interfaz.
            if(auxC.isClase(nombre))
            {
                auxC=auxC.getClase(nombre);
                S.add(auxC);
                T.add(auxC);
            }
            if(auxI.isInterfaz(nombre))
            {
                auxI=auxI.getInterfaz(nombre);
                S.add(auxI);
                T.add(auxI);
            }
        }

        //En SeleccionadosNTransformable tengo todos los valores de las interfaces a buscar.
        for (int i=0;i<modelNTransformable.getSize();i++)
        {
            String nombre=(String)modelNTransformable.getElementAt(i);
            //Llamamos a la funcion que obtiene la clase de la bbdd.
            //Comprobar si es una clase o una interfaz.
            if(auxC.isClase(nombre))
            {
                auxC=auxC.getClase(nombre);
                S.add(auxC);
                NT.add(auxC);
            }
            if(auxI.isInterfaz(nombre))
            {
                auxI=auxI.getInterfaz(nombre);
                S.add(auxI);
                NT.add(auxI);
            }
        }

        //Obtengo el nombre del esquema que necesito ahora, del combobox.
        String NombreModuloViejo=(String)jCModulos.getSelectedItem();

        //LLAMADA A LA CLAUSURA MIXTA

        System.out.print("MIXTA");
        ClausuraMixta  Algoritmo= new ClausuraMixta();
        Algoritmo.HybridClousure(S,NT,T,auxM.getModule(NombreModuloViejo));
        System.out.println("EJECUCION DEL TERCE ALGORITMO");
        mostrarResultados(S);
        jTNombreNuevoEsquema.setText("");
        //Por separado tener los NT y los T que seran los que se les pase a las distintas clausuras.
    }
    void jBNTransformable_actionPerformed(ActionEvent e)
    {
        seleccionadosClases=jLClases.getSelectedValues();
        for (int i=0;i<seleccionadosClases.length;i++)
        {
            modelNTransformable.addElement(seleccionadosClases[i]);
            modelClases.removeElement(seleccionadosClases[i]);
        }
        seleccionadosInterfaces=jLInterfaces.getSelectedValues();
        for (int i=0;i<seleccionadosInterfaces.length;i++)
        {
            modelNTransformable.addElement(seleccionadosInterfaces[i]);
            modelInterfaces.removeElement(seleccionadosInterfaces[i]);
        }

    }
    void jBBorraTransformable_actionPerformed(ActionEvent e)
    {
        //Hace la operacion inversa, es decir pasa del list de los transformables  al de las clases o interfaces segun sea uno un otro
        seleccionadosClases=jLTransformable.getSelectedValues();
        Interfaz auxIN = new Interfaz("inaux");
        for (int i=0;i<seleccionadosClases.length;i++)
        {

            if(auxIN.isInterfaz((String)seleccionadosClases[i]))
            {
                //si es una interfaz pues lo guarda en el modelInterfaz
                modelInterfaces.addElement(seleccionadosClases[i]);
            }else
            {
                modelClases.addElement(seleccionadosClases[i]);
            }
            modelTransformable.removeElement(seleccionadosClases[i]);
        }

    }
    void jBBorrarNTransformable_actionPerformed(ActionEvent e)
    {
        seleccionadosClases=jLNoTransformable.getSelectedValues();
        Interfaz auxIN = new Interfaz("inaux");
        for (int i=0;i<seleccionadosClases.length;i++)
        {

            if(auxIN.isInterfaz((String)seleccionadosClases[i]))
            {
                //si es una interfaz pues lo guarda en el modelInterfaz
                modelInterfaces.addElement(seleccionadosClases[i]);

            }else
            {
                //Si no se trata de una clase
                modelClases.addElement(seleccionadosClases[i]);
            }
            modelNTransformable.removeElement(seleccionadosClases[i]);
        }
    }

    void jLClases_mouseReleased(MouseEvent e)
    {

    }

    void jBInforme_actionPerformed(ActionEvent e)
    {
        basedeDatos.generarInforme("Informe.pdf",(String)jCModulos.getSelectedItem());
        GUIVisorAdobe a= new GUIVisorAdobe("Informe.pdf");
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
    void JBGrafo_actionPerformed(ActionEvent e)
    {
        //Obtener el nombre del modulo del desplegable.
        this.crearGrafo((String)jCModulos.getSelectedItem());
    }
}