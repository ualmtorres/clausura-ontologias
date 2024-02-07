package GUI;

/**
 * Título:
 * Descripcion:
 * Copyright:    Copyright (c) 2001
 * Empresa:
 * @author
 * @version 1.0
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import com.borland.jbcl.layout.*;

/**
 * Título:
 * Descripcion:
 * Copyright:    Copyright (c) 2001
 * Empresa:
 * @author
 * @version 1.0
 */

public class GUIGrafo extends JDialog implements ActionListener
{
    JPanel panel1 = new JPanel();
    JPanel panel2 = new JPanel();
    JPanel insetsPanel3 = new JPanel();
    XYLayout xYLayout1 = new XYLayout();
    XYLayout xYLayout2 = new XYLayout();
    XYLayout xYLayout3 = new XYLayout();
    XYLayout xYLayout4 = new XYLayout();
    XYLayout xYLayout5 = new XYLayout();
    Graph a=null;

    public GUIGrafo(String estructura, String nombre_esquema)
    {
        //    super(parent);
        enableEvents(AWTEvent.WINDOW_EVENT_MASK);
        try
        {
            jbInit(estructura,nombre_esquema);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        pack();
    }
    /**Inicialización de componentes*/
    private void jbInit(String estructura,String nombre_esquema) throws Exception
    {
        this.setTitle("Esquema "+ nombre_esquema);
        xYLayout1.setWidth(400);
        xYLayout1.setHeight(400);
        this.getContentPane().setLayout(xYLayout1);
        setResizable(false);
        panel1.setLayout(xYLayout4);
        panel2.setLayout(xYLayout3);
        insetsPanel3.setLayout(xYLayout2);
        insetsPanel3.setBorder(BorderFactory.createEtchedBorder());
        this.getContentPane().add(panel1, new XYConstraints(0, 0, -1, -1));
        a=new Graph(estructura);

        panel1.add(panel2, new XYConstraints(0, 0, -1, -1));
        panel2.add(insetsPanel3, new XYConstraints(4, 3, 392, 394));
        insetsPanel3.add(a, new XYConstraints(9, 6, 377, 382));
        ////




    }
    /**Modificado para poder salir cuando se cierra la ventana*/
    protected void processWindowEvent(WindowEvent e)
    {
        if (e.getID() == WindowEvent.WINDOW_CLOSING)
        {
            cancel();
        }
        super.processWindowEvent(e);
    }
    /**Cerrar el cuadro de diálogo*/
    void cancel()
    {
        dispose();
    }
    /**Cerrar el cuadro de diálogo tras un suceso de un botón*/
    public void actionPerformed(ActionEvent e)
    {
        //  if (e.getSource() == button1) {
        //  cancel();
        //a.stop();
        // }
    }

    public GUIGrafo()
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
    }



}