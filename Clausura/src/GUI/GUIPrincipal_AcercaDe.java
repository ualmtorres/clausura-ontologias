package GUI;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

/**
 * Título:
 * Descripcion:
 * Copyright:    Copyright (c) 2001
 * Empresa:
 * @author
 * @version 1.0
 */

public class GUIPrincipal_AcercaDe extends JDialog implements ActionListener
{

    JPanel panel1 = new JPanel();
    JPanel panel2 = new JPanel();
    JPanel insetsPanel1 = new JPanel();
    JPanel insetsPanel2 = new JPanel();
    JPanel insetsPanel3 = new JPanel();
    JButton button1 = new JButton();
    JLabel imageLabel = new JLabel();
    JLabel label1 = new JLabel();
    JLabel label3 = new JLabel();
    BorderLayout borderLayout1 = new BorderLayout();
    BorderLayout borderLayout2 = new BorderLayout();
    FlowLayout flowLayout1 = new FlowLayout();
    GridLayout gridLayout1 = new GridLayout();
    String product = "Clausura de Esquemas externos en BBDDOO";
    String version = "2.0";
    String copyright = "Copyright (c) 2002";
    String comments = "Esperemos acabarlo para este verano";
    public GUIPrincipal_AcercaDe(Frame parent)
    {
        super(parent);
        enableEvents(AWTEvent.WINDOW_EVENT_MASK);
        try
        {
            jbInit();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        pack();
    }
    /**Inicialización de componentes*/
    private void jbInit() throws Exception
    {
        //imageLabel.setIcon(new ImageIcon(GUIPrincipal_AcercaDe.class.getResource("[Imagen]")));
        this.setTitle("Acerca de");
        setResizable(false);
        panel1.setLayout(borderLayout1);
        panel2.setLayout(borderLayout2);
        insetsPanel1.setLayout(flowLayout1);
        insetsPanel2.setLayout(flowLayout1);
        insetsPanel2.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        gridLayout1.setRows(4);
        gridLayout1.setColumns(1);
        label1.setText("Clausura de Esquemas externos para bases de datos ODMG");
        label3.setText("Autor: Carlos Alvarez Bermejo");
        insetsPanel3.setLayout(gridLayout1);
        insetsPanel3.setBorder(BorderFactory.createEmptyBorder(10, 60, 10, 10));
        button1.setText("Aceptar");
        button1.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                button1_actionPerformed(e);
            }
        });
        button1.addActionListener(this);
        insetsPanel2.add(imageLabel, null);
        panel2.add(insetsPanel2, BorderLayout.WEST);
        this.getContentPane().add(panel1, null);
        insetsPanel3.add(label1, null);
        insetsPanel3.add(label3, null);
        panel2.add(insetsPanel3, BorderLayout.CENTER);
        insetsPanel1.add(button1, null);
        panel1.add(insetsPanel1, BorderLayout.SOUTH);
        panel1.add(panel2, BorderLayout.NORTH);
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
        if (e.getSource() == button1)
        {
            cancel();
        }
    }

    void button1_actionPerformed(ActionEvent e)
    {

    }
}