package GUI;
import Algoritmos.ClausuraReduccion;
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

public class GUIAlgoritmo2 extends JPanel
{
    XYLayout xYLayout1 = new XYLayout();
    JPanel jPModulos = new JPanel();
    XYLayout xYLayout2 = new XYLayout();
    Border border1;
    TitledBorder titledBorder1;
    JComboBox jCModulos = new JComboBox();
    Border border2;
    Border border3;
    JButton jBEjecutar = new JButton();
    Border border4;
    JScrollPane jSPListaClases = new JScrollPane();
    JPanel jPClases = new JPanel();
    JList jLClases = new JList(new DefaultListModel());
    JButton jBCopiar = new JButton();
    JScrollPane jScrollPane1 = new JScrollPane();
    JList jLClasesSeleccionadas = new JList(new DefaultListModel());
    XYLayout xYLayout3 = new XYLayout();
    JPanel jPanel3 = new JPanel();
    JScrollPane jSPResultadosInterfaces = new JScrollPane();
    JScrollPane jSPResultadosClases = new JScrollPane();
    XYLayout xYLayout4 = new XYLayout();
    JTextPane jTPResultadosInterfaces = new JTextPane();
    JTextPane jTPResultadosClases = new JTextPane();
    Border border5;
    TitledBorder titledBorder2;
    Border border6;
    TitledBorder titledBorder3;
    JScrollPane jSPClasesD = new JScrollPane();
    JTextPane jTPReultadosClasesD = new JTextPane();
    Border border7;
    TitledBorder titledBorder4;
    Border border8;
    TitledBorder titledBorder5;
    JScrollPane jSPResultadosInterfacesD = new JScrollPane();
    JTextPane jTPResultadosInterfacesD = new JTextPane();
    JLabel jLabel1 = new JLabel();
    JLabel jLabel2 = new JLabel();
    JLabel jLabel3 = new JLabel();
    JLabel jLabel4 = new JLabel();
    JButton jBInforme = new JButton();
    Border border9;
    Border border10;
    TitledBorder titledBorder6;
    JScrollPane jSPListaInterfaces = new JScrollPane();
    JList jLInterfaces = new JList(new DefaultListModel());
    JScrollPane jScrollPane2 = new JScrollPane();
    JList jLInterfacesSeleccionadas = new JList(new DefaultListModel());
    Object [] seleccionadosClases; //En el Estaran los objetos seleccionados de los dos jLists
    Object [] seleccionadosInterfaces;
    JPanel jPanel1 = new JPanel();
    Border border11;
    TitledBorder titledBorder7;
    JTextField jTNombreNuevoEsquema = new JTextField();
    XYLayout xYLayout5 = new XYLayout();
    Border border12;
    DefaultListModel modelInterfaces=(DefaultListModel)jLInterfaces.getModel();
    DefaultListModel modelCSeleccionadas =(DefaultListModel) jLClasesSeleccionadas.getModel();
    DefaultListModel modelISeleccionadas = (DefaultListModel)jLInterfacesSeleccionadas.getModel();
    DefaultListModel modelClases =(DefaultListModel)jLClases.getModel();
    JButton jBCopiarReves = new JButton();
    JButton jBGrafo = new JButton();
    Border border13;
    public GUIAlgoritmo2()
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
        border1 = new EtchedBorder(EtchedBorder.RAISED,Color.white,new Color(148, 145, 140));
        titledBorder1 = new TitledBorder(BorderFactory.createEtchedBorder(Color.white,new Color(148, 145, 140)),"Clases e Interfaces del Esquema");
        border2 = BorderFactory.createEtchedBorder(Color.white,new Color(178, 178, 178));
        border3 = BorderFactory.createEtchedBorder(Color.white,new Color(148, 145, 140));
        border4 = BorderFactory.createEtchedBorder(Color.white,Color.gray);
        border5 = BorderFactory.createEtchedBorder(Color.white,new Color(148, 145, 140));
        titledBorder2 = new TitledBorder(border5,"Resultados");
        border6 = BorderFactory.createEtchedBorder(Color.white,new Color(178, 178, 178));
        titledBorder3 = new TitledBorder(border6,"Clases");
        border7 = BorderFactory.createEtchedBorder(Color.white,new Color(148, 145, 140));
        titledBorder4 = new TitledBorder(border7,"Resultados");
        border8 = BorderFactory.createEtchedBorder(Color.white,new Color(148, 145, 140));
        titledBorder5 = new TitledBorder(border8,"Resultados");
        border9 = BorderFactory.createEtchedBorder(Color.white,new Color(148, 145, 140));
        border10 = BorderFactory.createEtchedBorder(Color.white,new Color(148, 145, 140));
        titledBorder6 = new TitledBorder(border10,"Esquemas");
        border11 = BorderFactory.createEtchedBorder(Color.white,new Color(148, 145, 140));
        titledBorder7 = new TitledBorder(border11,"Nombre del nuevo Esquema:");
        border12 = BorderFactory.createEtchedBorder(Color.white,new Color(178, 178, 178));
        border13 = BorderFactory.createEtchedBorder(Color.white,new Color(148, 145, 140));
        this.setLayout(xYLayout1);
        jPModulos.setBorder(titledBorder6);
        jPModulos.setToolTipText("Esquemas actualmente  definidos en la base de datos.");
        jPModulos.setLayout(xYLayout2);
        xYLayout1.setWidth(583);
        xYLayout1.setHeight(300);
        jCModulos.setBorder(border2);
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
        jBEjecutar.setBorder(border4);
        jBEjecutar.setText("Ejecutar Algoritmo");
        jBEjecutar.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                jBEjecutar_actionPerformed(e);
            }
        });
        jPClases.setBorder(titledBorder1);
        jPClases.setLayout(xYLayout3);
        jLClases.setBorder(BorderFactory.createEtchedBorder());
        jLClases.setToolTipText("Clases del esquema seleccionado");
        jBCopiar.setBorder(BorderFactory.createEtchedBorder());
        jBCopiar.setText("Copiar >>");
        jBCopiar.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                jBCopiar_actionPerformed(e);
            }
        });
        jLClasesSeleccionadas.setBackground(SystemColor.window);
        jLClasesSeleccionadas.setBorder(BorderFactory.createEtchedBorder());
        jLClasesSeleccionadas.setToolTipText("Clases Seleccionadas");
        jPanel3.setLayout(xYLayout4);
        jTPResultadosInterfaces.setEnabled(false);
        jTPResultadosInterfaces.setToolTipText("Interfaces Necesarias");
        jTPResultadosInterfaces.setBorder(BorderFactory.createEtchedBorder());
        jTPResultadosInterfaces.setEditable(false);
        jTPResultadosClases.setToolTipText("Clases Necesarias");
        jTPResultadosClases.setBorder(BorderFactory.createEtchedBorder());
        jTPResultadosClases.setEditable(false);
        jPanel3.setFont(new java.awt.Font("Dialog", 1, 12));
        jPanel3.setBorder(titledBorder5);
        jTPReultadosClasesD.setToolTipText("Clases derivadas que han sido necesarias crear");
        jTPReultadosClasesD.setBorder(BorderFactory.createEtchedBorder());
        jTPReultadosClasesD.setEditable(false);
        jTPResultadosInterfacesD.setToolTipText("Interfaces Derivadas que han sido necesarias crear.");
        jTPResultadosInterfacesD.setBorder(BorderFactory.createEtchedBorder());
        jTPResultadosInterfacesD.setEditable(false);
        jLabel1.setText("Clases Derivadas");
        jLabel2.setText("Interfaces Derivadas");
        jLabel3.setText("Interfaces");
        jLabel4.setText("Clases");
        jBInforme.setBorder(border9);
        jBInforme.setText("Generar Informe");
        jBInforme.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                jBInforme_actionPerformed(e);
            }
        });
        jLInterfaces.setBorder(BorderFactory.createEtchedBorder());
        jLInterfaces.setToolTipText("Interfaces  del esquema seleccionado");
        jLInterfacesSeleccionadas.setBackground(SystemColor.window);
        jLInterfacesSeleccionadas.setBorder(BorderFactory.createEtchedBorder());
        jLInterfacesSeleccionadas.setToolTipText("Interfaces Seleccionadas");
        jPanel1.setBorder(titledBorder7);
        jPanel1.setLayout(xYLayout5);
        jTNombreNuevoEsquema.setBorder(border12);
        jBCopiarReves.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                jBCopiarReves_actionPerformed(e);
            }
        });
        jBCopiarReves.setText("<<Copiar");
        jBCopiarReves.setBorder(BorderFactory.createEtchedBorder());
        jBGrafo.setBorder(border13);
        jBGrafo.setToolTipText("Muestra gráficamente el esquema seleccionado en el desplegable.");
        jBGrafo.setText("Mostrar Resultado");
        jBGrafo.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                jBGrafo_actionPerformed(e);
            }
        });
        this.add(jPClases, new XYConstraints(217, 0, 355, 139));
        jPClases.add(jScrollPane1, new XYConstraints(221, 0, 114, 48));
        jPClases.add(jSPListaClases, new XYConstraints(4, 0, 114, 49));
        jPClases.add(jSPListaInterfaces, new XYConstraints(4, 53, 114, 52));
        jPClases.add(jScrollPane2, new XYConstraints(222, 58, 114, 48));
        jPClases.add(jBCopiar, new XYConstraints(139, 23, 61, 25));
        jPClases.add(jBCopiarReves, new XYConstraints(140, 58, 61, 25));
        jScrollPane2.getViewport().add(jLInterfacesSeleccionadas, null);
        jSPListaInterfaces.getViewport().add(jLInterfaces, null);
        jSPListaClases.getViewport().add(jLClases, null);
        jScrollPane1.getViewport().add(jLClasesSeleccionadas, null);
        this.add(jPanel3, new XYConstraints(3, 131, 569, -1));
        jPanel3.add(jSPResultadosInterfaces, new XYConstraints(355, 19, 163, 51));
        jPanel3.add(jSPResultadosInterfacesD, new XYConstraints(357, 86, 165, 53));
        jPanel3.add(jLabel2, new XYConstraints(356, 71, 119, 16));
        jPanel3.add(jSPResultadosClases, new XYConstraints(48, 16, 174, 53));
        jPanel3.add(jLabel1, new XYConstraints(49, 70, 107, -1));
        jPanel3.add(jSPClasesD, new XYConstraints(48, 86, 174, 54));
        jPanel3.add(jLabel3, new XYConstraints(355, 2, 69, -1));
        jPanel3.add(jLabel4, new XYConstraints(49, 0, 93, -1));
        jPanel3.add(jBInforme, new XYConstraints(237, 28, 103, 23));
        jPanel3.add(jBGrafo, new XYConstraints(233, 55, 114, 25));
        this.add(jPModulos, new XYConstraints(6, 47, 207, 49));
        jPModulos.add(jCModulos, new XYConstraints(5, 0, 183, 17));
        this.add(jPanel1, new XYConstraints(6, 1, 204, 45));
        jPanel1.add(jTNombreNuevoEsquema, new XYConstraints(5, 0, 183, 16));
        this.add(jBEjecutar, new XYConstraints(50, 102, 120, 29));
        jSPClasesD.getViewport().add(jTPReultadosClasesD, null);
        jSPResultadosClases.getViewport().add(jTPResultadosClases, null);
        jSPResultadosInterfacesD.getViewport().add(jTPResultadosInterfacesD, null);
        jSPResultadosInterfaces.getViewport().add(jTPResultadosInterfaces, null);
    }

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
    void jBEjecutar_actionPerformed(ActionEvent e)
    {
        //Obtener las clases y las interfaces seleccionadas y sacarlas de la base de datos.
        Clase auxC= new Clase();
        Module auxM= new Module("aux");
        Interfaz auxI= new Interfaz();
        SetOfObject relacion= new SetOfObject();
        SetOfObject resultados=new SetOfObject();
        //Nombre del modulo que le voy a dar.--->cuidao pq tb lo estoy poniendo en el propio ReductionClousure.
        basedeDatos.setNombreEsquemaNuevo(jTNombreNuevoEsquema.getText());
        //Limpio los cuadros de resultado.
        jTPResultadosClases.setText("");
        jTPResultadosInterfaces.setText("");
        jTPReultadosClasesD.setText("");
        jTPResultadosInterfacesD.setText("");


        //En SeleccionadosClases tengo todos los valores de las clases a buscar.
        for (int i=0;i<modelCSeleccionadas.getSize();i++)
        {
            //        String nombre=(String)seleccionadosClases[i];
            String nombre=(String)modelCSeleccionadas.elementAt(i);
            //Llamamos a la funcion que obtiene la clase de la bbdd.
            relacion.add(auxC.getClase(nombre));
        }

        //En SeleccionadosInterfaces tengo todos los valores de las interfaces a buscar.
        for (int i=0;i<modelISeleccionadas.getSize();i++)
        {
            // String nombre=(String)seleccionadosInterfaces[i];
            String nombre=(String)modelISeleccionadas.getElementAt(i);
            //Llamamos a la funcion que obtiene la clase de la bbdd.
            relacion.add(auxI.getInterfaz(nombre));
        }

        //Obtengo el nombre del esquema que necesito ahora, del combobox.
        String NombreModuloViejo=(String)jCModulos.getSelectedItem();

        //LLAMADA A LA CLAUSURA CON REDUCCIÓN

        System.out.print("IMPRIMEOBJETOS");
        ClausuraReduccion  Algoritmo= new ClausuraReduccion(relacion,relacion,auxM.getModule(NombreModuloViejo));
        System.out.println("EJECUCION DEL SEGUNDO ALGORITMO");
        mostrarResultados(relacion);
        jTNombreNuevoEsquema.setText("");
    }
    void jBCopiar_actionPerformed(ActionEvent e)
    {
        seleccionadosClases=jLClases.getSelectedValues();
        //     jLClasesSeleccionadas.setListData(seleccionadosClases);
        for (int i=0;i<seleccionadosClases.length;i++)
        {
            modelCSeleccionadas.addElement(seleccionadosClases[i]);
            modelClases.removeElement(seleccionadosClases[i]);
        }
        seleccionadosInterfaces=jLInterfaces.getSelectedValues();
        //     jLInterfacesSeleccionadas.setListData(seleccionadosInterfaces);
        for (int i=0;i<seleccionadosInterfaces.length;i++)
        {
            modelISeleccionadas.addElement(seleccionadosInterfaces[i]);
            modelInterfaces.removeElement(seleccionadosInterfaces[i]);
        }

    }

    void jCModulos_actionPerformed(ActionEvent e)
    {
        Vector lista= new Vector();
        Vector listaInterfaces = new Vector();
        Clase aux = new Clase();
        Interfaz auxI=new Interfaz();
        Module auxM= new Module("kk");
        Iterator auxIT;//Iterador para coger todas las clases de ese modulo
        Iterator auxIN;//Iterador para coger todas las interfaces del modulo seleccionado.

        //Coger el modulo del desplegable de los módulos.
        String md=(String)jCModulos.getSelectedItem();

        //   jLClases.removeAll();
        //   jLInterfaces.removeAll();
        modelClases.removeAllElements();
        modelInterfaces.removeAllElements();
        modelISeleccionadas.removeAllElements();
        modelCSeleccionadas.removeAllElements();

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
        // jLInterfaces.setListData(listaInterfaces);

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

    void BorrarModulo_actionPerformed(ActionEvent e)
    {
        basedeDatos.clear();
    }
    void jBCopiarReves_actionPerformed(ActionEvent e)
    {
        seleccionadosClases=jLClasesSeleccionadas.getSelectedValues();
        //   jLClasesSeleccionadas.setListData(seleccionadosClases);
        for (int i=0;i<seleccionadosClases.length;i++)
        {
            modelClases.addElement(seleccionadosClases[i]);
            modelCSeleccionadas.removeElement(seleccionadosClases[i]);
        }
        seleccionadosInterfaces=jLInterfacesSeleccionadas.getSelectedValues();
        //     jLInterfacesSeleccionadas.setListData(seleccionadosInterfaces);
        for (int i=0;i<seleccionadosInterfaces.length;i++)
        {
            modelInterfaces.addElement(seleccionadosInterfaces[i]);
            modelISeleccionadas.removeElement(seleccionadosInterfaces[i]);
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

    void jBInforme_actionPerformed(ActionEvent e)
    {
        basedeDatos.generarInforme("Informe.pdf",(String)jCModulos.getSelectedItem());

        GUIVisorAdobe a= new GUIVisorAdobe("Informe.pdf");
    }
}