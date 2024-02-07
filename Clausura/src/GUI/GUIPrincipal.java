package GUI;
import Algoritmos.basedeDatos;
import org.odmg.ODMGException;
import org.odmg.ODMGRuntimeException;
import org.odmg.ObjectNameNotUniqueException;
import org.odmg.DatabaseNotFoundException;
import com.poet.odmg.Database;
import com.poet.odmg.*;
import com.poet.odmg.util.*;
import com.poet.odmg.imp.*;
import com.borland.jbcl.layout.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;
import java.io.File;
import java.util.Properties;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.UIManager;
import javax.swing.filechooser.FileFilter;
import GUI.*;
import GUI.GenericFileFilter;
import GUI.GUIVisorAdobe;
/**
 * Título:
 * Descripcion:
 * Copyright:    Copyright (c) 2001
 * Empresa:
 * @author
 * @version 1.0
 */

public class GUIPrincipal extends JFrame {
  public GUIAlgoritmo1 GUIAlgoritmo1= new GUIAlgoritmo1();
  public GUIAlgoritmo2 GUIAlgoritmo2= new GUIAlgoritmo2();
  public GUIAlgoritmo3 GUIAlgoritmo3= new GUIAlgoritmo3();
  JPanel contentPane;
  JMenuBar jbarraMenu = new JMenuBar();
  JMenu jMenuArchivo = new JMenu();
  JMenu jMenuAyuda = new JMenu();
  JMenuItem jMenuAcercaDe = new JMenuItem();
  JToolBar barraIconos = new JToolBar();
  JButton jbAbrir = new JButton();
  JButton jbGuardar = new JButton();
  ImageIcon image1;
  ImageIcon image2;
  ImageIcon image3;
  JLabel barraEstado = new JLabel();
  TitledBorder titledBorder1;
  JMenuItem jMenuISalir = new JMenuItem();
  JMenu jMenuAlgoritmos = new JMenu();
  JMenuItem jMenuItem2 = new JMenuItem();
  JMenuItem jMenuItem4 = new JMenuItem();
  JMenuItem jMenuItem5 = new JMenuItem();
  JMenuItem jMenuItem6 = new JMenuItem();
  JMenuItem jMCerrar = new JMenuItem();
  JPanel jPanelPrincipal = new JPanel();
  BorderLayout borderLayout1 = new BorderLayout();
  XYLayout xYLayout1 = new XYLayout();
  TitledBorder titledBorder2;
  Database db;
  TitledBorder titledBorder3;

  String BaseDatos=null;
  Border border1;
  TitledBorder titledBorder4;
  JMenu jMBDatos = new JMenu();
  JMenuItem jMBDatosCargar = new JMenuItem();
  JMenuItem jMBDatosBorrar = new JMenuItem();//Seran donde se almacene el nombre de la ruta de la BBDD


  /**Construir el marco*/
  public GUIPrincipal() {
     //Imagen antes de la carga
   SplashWindow sw;
   Image splashIm;

     MediaTracker mt = new MediaTracker(this);
     splashIm = Toolkit.getDefaultToolkit().getImage(GUI.GUIPrincipal.class.getResource("logoPrincipal1.gif"));
     mt.addImage(splashIm,0);
     //PONEMOS EL LOGO EN LAS VENTANAS.
     this.setIconImage(splashIm);
     try {
       mt.waitForID(0);
     } catch(InterruptedException ie){}
     sw = new SplashWindow(this,splashIm);

     try {
        Thread.sleep(2000);
     } catch(InterruptedException ie){}
     sw.dispose();

     enableEvents(AWTEvent.WINDOW_EVENT_MASK);
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }
  /**Inicialización de componentes*/
  private void jbInit() throws Exception  {
    image1 = new ImageIcon(GUI.GUIPrincipal.class.getResource("openFile.gif"));
    image2 = new ImageIcon(GUI.GUIPrincipal.class.getResource("closeFile.gif"));
    image3 = new ImageIcon(GUI.GUIPrincipal.class.getResource("help.gif"));
    contentPane = (JPanel) this.getContentPane();
    titledBorder1 = new TitledBorder("");
    titledBorder2 = new TitledBorder("");
    titledBorder3 = new TitledBorder("");
    border1 = BorderFactory.createEtchedBorder(Color.white,new Color(148, 145, 140));
    titledBorder4 = new TitledBorder(border1,"Selecciones una base de datos");
    contentPane.setLayout(borderLayout1);
    this.setSize(new Dimension(600, 406));
    this.setTitle("Clausura de esquemas externos");
    barraEstado.setBorder(BorderFactory.createEtchedBorder());
    barraEstado.setToolTipText("Barra de estado");
    barraEstado.setText("Clausura de esquemas externos.");
    barraEstado.setVerticalAlignment(SwingConstants.BOTTOM);
    barraEstado.setVerticalTextPosition(SwingConstants.BOTTOM);
    jMenuArchivo.setText("Aplicación");
    jMenuAyuda.setText("Acerca de");
    jMenuAcercaDe.setText("Acerca de");
    jMenuAcercaDe.addActionListener(new ActionListener()  {
      public void actionPerformed(ActionEvent e) {
        jMenuAcercaDe_actionPerformed(e);
      }
    });
    jbAbrir.setIcon(image1);
    jbAbrir.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jbAbrir_actionPerformed(e);
      }
    });
    jbAbrir.setBorder(BorderFactory.createEtchedBorder());
    jbAbrir.setToolTipText("Abrir archivo");
    jbGuardar.setIcon(image2);
    jbGuardar.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jbGuardar_actionPerformed(e);
      }
    });
    jbGuardar.setBorder(BorderFactory.createEtchedBorder());
    jbGuardar.setToolTipText("Cerrar archivo");
    jMenuISalir.setText("Salir");
    jMenuISalir.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuISalir_actionPerformed(e);
      }
    });
    jMenuAlgoritmos.setEnabled(false);
    jMenuAlgoritmos.setText("Algoritmos");
    jMenuItem2.setToolTipText("");
    jMenuItem2.setActionCommand("Clausura por Ampliación");
    jMenuItem2.setText("Clausura por Ampliación");
    jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem2_actionPerformed(e);
      }
    });
    jMenuItem4.setText("Clausura por Reducción");
    jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem4_actionPerformed(e);
      }
    });
    jMenuItem5.setText("Clausura Mixta");
    jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem5_actionPerformed(e);
      }
    });
    jMenuItem6.setFocusPainted(true);
    jMenuItem6.setText("Abrir");
    jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem6_actionPerformed(e);
      }
    });
    jMCerrar.setEnabled(false);
    jMCerrar.setText("Cerrar");
    jMCerrar.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMCerrar_actionPerformed(e);
      }
    });
    jPanelPrincipal.setLayout(xYLayout1);
    jPanelPrincipal.setBorder(BorderFactory.createEtchedBorder());

    jMBDatos.setText("Base de Datos");
    jMBDatosCargar.setEnabled(false);
    jMBDatosCargar.setToolTipText("Carga el ejemplo en la base de datos.");
    jMBDatosCargar.setText("Cargar ejemplo");
    jMBDatosCargar.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMBDatosCargar_actionPerformed(e);
      }
    });
    jMBDatosBorrar.setEnabled(false);
    jMBDatosBorrar.setToolTipText("Elimina el contenido de la base de datos.");
    jMBDatosBorrar.setText("Borrar contenido");
    jMBDatosBorrar.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMBDatosBorrar_actionPerformed(e);
      }
    });
    jMenuAyuda.add(jMenuAcercaDe);
    jbarraMenu.add(jMenuArchivo);
    jbarraMenu.add(jMenuAlgoritmos);
    jbarraMenu.add(jMenuAyuda);
    this.setJMenuBar(jbarraMenu);
    contentPane.add(barraEstado, BorderLayout.SOUTH);
    contentPane.add(barraIconos, BorderLayout.NORTH);
    barraIconos.add(jbAbrir);
    barraIconos.add(jbGuardar);
    contentPane.add(jPanelPrincipal, BorderLayout.CENTER);

    jMenuArchivo.add(jMBDatos);
    jMenuArchivo.addSeparator();
    jMenuArchivo.add(jMenuISalir);
    jMenuAlgoritmos.add(jMenuItem2);
    jMenuAlgoritmos.add(jMenuItem4);
    jMenuAlgoritmos.add(jMenuItem5);
    jMBDatos.add(jMenuItem6);
    jMBDatos.add(jMBDatosCargar);
    jMBDatos.add(jMBDatosBorrar);
    jMBDatos.add(jMCerrar);
  }
  /**Realizar Archivo | Salir*/
  public void jMenuFileExit_actionPerformed(ActionEvent e) {
    System.exit(0);

  }
  /**Realizar Ayuda | Acerca de*/
  public void jMenuAcercaDe_actionPerformed(ActionEvent e) {
    GUIPrincipal_AcercaDe dlg = new GUIPrincipal_AcercaDe(this);
    Dimension dlgSize = dlg.getPreferredSize();
    Dimension frmSize = getSize();
    Point loc = getLocation();
    dlg.setLocation((frmSize.width - dlgSize.width) / 2 + loc.x, (frmSize.height - dlgSize.height) / 2 + loc.y);
    dlg.setModal(true);
    dlg.show();
  }
  /**Modificado para poder salir cuando se cierra la ventana*/
  protected void processWindowEvent(WindowEvent e) {
    super.processWindowEvent(e);
    if (e.getID() == WindowEvent.WINDOW_CLOSING) {
      jMenuFileExit_actionPerformed(null);
    }
  }

  void jbGuardar_actionPerformed(ActionEvent e) {
    if(db!=null){
      try{
      db.close();
      barraEstado.setText("Base de datos cerrada....");
      } catch ( ODMGException x )
        {
          barraEstado.setText("Error al cerrar la base de datos!.");
          x.printStackTrace();
          }
     }
    else{
          JOptionPane.showMessageDialog(null,"¡La Base de datos no está abierta!", "¡ATENCIÓN!",JOptionPane.ERROR_MESSAGE);
    }
  }

  void jbAbrir_actionPerformed(ActionEvent e) {
    try{
        db = new Database();
        db.open(basedeDatos.BASEDEDATOS, Database.OPEN_READ_WRITE);
        barraEstado.setText("¡¡Base de datos abierta!!");
    }
    catch ( ODMGException x )
    {
        barraEstado.setText("Base de datos NO Abierta");
        JOptionPane.showMessageDialog(null,"No se ha podido abrir la Base de datos", "¡Error!",JOptionPane.ERROR_MESSAGE);
        x.printStackTrace();
    }


  }

  void jMenuISalir_actionPerformed(ActionEvent e) {
    System.exit(0);
  }

  void jbAyuda_actionPerformed(ActionEvent e) {
//Permite mostrar un panel dentro de otro panel
        jPanelPrincipal.add(GUIAlgoritmo1, BorderLayout.CENTER);
        jPanelPrincipal.getComponent(0).setVisible(true);
        GUIAlgoritmo1.revalidate();
  }

  void jMenuItem6_actionPerformed(ActionEvent e)
  {
  //Preparamos los parámetros del Dialogo para seleccionar la BBDD
    JFileChooser jFCBDatos = new JFileChooser("c:\\");//Es con lo que se selecciona la bbdd
    jPanelPrincipal.add(jFCBDatos, new XYConstraints(67, 63, 5, 6));
    jFCBDatos.setDialogTitle("Seleccionar Base de Datos");

    jFCBDatos.setOpaque(true);
    jFCBDatos.setBorder(titledBorder4);
    jFCBDatos.setToolTipText("");
    jFCBDatos.setBackground(new Color(212, 208, 200));
    jFCBDatos.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jFCBDatos_actionPerformed(e);
      }
    });

      String TipoDato[] = {"dat"};
      jFCBDatos.setApproveButtonToolTipText("Pulse para seleccionar la base de datos a abrir");
      jFCBDatos.setToolTipText("Permite seleccionar la base de datos a abrir");
      GenericFileFilter filtro = new GenericFileFilter(TipoDato,"Base de Datos");
     // System.out.println(jFCBDatos.getFileFilter().toString());
      if (!jFCBDatos.getFileFilter().equals((FileFilter)filtro)){
        jFCBDatos.setFileFilter((FileFilter)filtro);
      }

      int opcion = jFCBDatos.showDialog(jPanelPrincipal,"Seleccionar Base de Datos");

      if (opcion == jFCBDatos.APPROVE_OPTION)
      {
        //System.out.println("FastObjects://LOCAL/"+jFCBDatos.getSelectedFile().toString());
        String aux=new String("FastObjects://LOCAL/"+jFCBDatos.getCurrentDirectory().toString());
        BaseDatos=aux.replace('\\','/');
        basedeDatos.setBASEDEDATOS(BaseDatos);
            try{
                db = new Database();
                db.open(basedeDatos.BASEDEDATOS, basedeDatos.MODO_BASEDEDATOS);
                barraEstado.setText("Base de datos abierta: "+basedeDatos.BASEDEDATOS);
                jMenuItem6.setEnabled(false);
                jMCerrar.setEnabled(true);
            }
            catch ( ODMGException x )
            {
                barraEstado.setText("Base de datos NO Abierta");
                JOptionPane.showMessageDialog(null,"No se ha podido abrir la Base de datos", "¡Error!",JOptionPane.ERROR_MESSAGE);
                x.printStackTrace();
            }
         System.out.println("Se ha abierto la base de datos "+BaseDatos);
         jMenuAlgoritmos.setEnabled(true);
         jMBDatosCargar.setEnabled(true);
         jMBDatosBorrar.setEnabled(true);
         jMCerrar.setEnabled(true);
        }//Fin del if cuando se acepta.
  }

  void jMCerrar_actionPerformed(ActionEvent e) {
    db=db.current();
    if(db!=null){
      try{
        db.close();
        barraEstado.setText("Base de datos cerrada....");
        jMCerrar.setEnabled(false);
        jMenuItem6.setEnabled(true);
      } catch ( ODMGException x )
        {
          barraEstado.setText("Error al cerrar la base de datos!.");
          x.printStackTrace();
          }
     }
    else{
          JOptionPane.showMessageDialog(null,"¡La Base de datos no está abierta!", "¡ATENCIÓN!",JOptionPane.ERROR_MESSAGE);
    }
  }

  void jMenuItem2_actionPerformed(ActionEvent e) {
        this.desactivar();
        this.setTitle("Clausura de esquemas externos - Algoritmo de Clausura con Ampliación.");
        jPanelPrincipal.add(GUIAlgoritmo1, BorderLayout.CENTER);
        jPanelPrincipal.getComponent(0).setVisible(true);
        GUIAlgoritmo1.revalidate();
        jMenuItem2.setEnabled(false);
        jMenuItem4.setEnabled(true);
        jMenuItem5.setEnabled(true);
  }

  void jMenuItem4_actionPerformed(ActionEvent e) {
        this.desactivar();
        this.setTitle("Clausura de esquemas externos - Algoritmo de Clausura con Reducción.");
        jPanelPrincipal.add(GUIAlgoritmo2, BorderLayout.CENTER);
        jPanelPrincipal.getComponent(0).setVisible(true);
        GUIAlgoritmo2.revalidate();
        jMenuItem2.setEnabled(true);
        jMenuItem4.setEnabled(false);
        jMenuItem5.setEnabled(true);
  }

  void jMenuItem5_actionPerformed(ActionEvent e) {
        this.desactivar();
        this.setTitle("Clausura de esquemas externos - Algoritmo de Clausura Mixta.");
        jPanelPrincipal.add(GUIAlgoritmo3, BorderLayout.CENTER);
        jPanelPrincipal.getComponent(0).setVisible(true);
        GUIAlgoritmo3.revalidate();
        jMenuItem2.setEnabled(true);
        jMenuItem4.setEnabled(true);
        jMenuItem5.setEnabled(false);
  }
//////FUNCIONES PROPIAS PARA LA EJECUCION DE LOS PANELES///////////
  //Funcion que desactiva el panel que se está viendo en el instante actual.

  public void desactivar(){

    if ( jPanelPrincipal.getComponentCount() > 0)
    {
       jPanelPrincipal.getComponent(0).setVisible(false);
       jPanelPrincipal.removeAll();
    }

  }

  //Retorna el nombre de la base de datos.
  public String getNombreBaseDatos(){
    return(BaseDatos);

  }

  void jFCBDatos_actionPerformed(ActionEvent e) {

  }

  void jMBDatosCargar_actionPerformed(ActionEvent e) {
    basedeDatos.clear();
    basedeDatos.loadData();
  }

  void jMBDatosBorrar_actionPerformed(ActionEvent e) {
    basedeDatos.clear();
  }
}//Fin del archivo.

class SplashWindow extends Window {
    Image splashIm;

    SplashWindow(Frame parent, Image splashIm) {
        super(parent);
        this.splashIm = splashIm;
        setSize(300,300);

        /* Center the window */
        Dimension screenDim =
             Toolkit.getDefaultToolkit().getScreenSize();
        Rectangle winDim = getBounds();
        setLocation((screenDim.width - winDim.width) / 2,(screenDim.height - winDim.height) / 2);
        setVisible(true);
    }

    public void paint(Graphics g) {
       if (splashIm != null) {
           g.drawImage(splashIm,0,0,this);
       }
 }


}