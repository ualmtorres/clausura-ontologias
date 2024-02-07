package GUI;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import com.adobe.acrobat.Viewer;
import com.adobe.acrobat.*;
/**
 * Título:
 * Descripcion:
 * Copyright:    Copyright (c) 2001
 * Empresa:
 * @author
 * @version 1.0
 */

public class GUIVisorAdobe
{

    public  GUIVisorAdobe(String nombreArchivo)
    {
        Frame f = new Frame("Visor de documentos pdf");
        Image splashIm;
        f.setLayout(new BorderLayout());
        try
        {


            final Viewer acrobat = new Viewer();
            final Frame ff=f;

            f.addWindowListener(new WindowAdapter()
            {
                public void windowClosing(WindowEvent e)
                {

                    if (acrobat != null)
                    {

                        // The deactivate method will ensure that the
                        // acrobat.properties file is saved
                        // upon exit.

                        acrobat.deactivate();
                    }

                    //  System.exit(0);
                    ff.hide();
                    ff.removeAll();
                }
            });

            if (nombreArchivo!="")
            {
                try
                {

                    // assumes that args[0] is the name of a file

                    FileInputStream in = new FileInputStream(nombreArchivo);
                    acrobat.setDocumentInputStream(in);

                } catch (FileNotFoundException x)
                {
                    System.out.println("Archivo no encontrado");
                    // The viewer will display a blank screen.
                    // You can then use the Viewer's pop-up menu
                    // to open a local or remote PDF file.
                }
            }

            f.add(acrobat, BorderLayout.CENTER);

            // you must call activate to enable the Viewer object
            // to layout its sub-components and the further initialization
            // needed for it to be displayed.

            acrobat.activate(); //WithoutBars();

        } catch (Exception x)
        {
            f.add(new Label("No se pudo crear un visor adobe"), "Center");
        }
        f.setSize(500, 400);
        //Establecemos el icono
        splashIm = Toolkit.getDefaultToolkit().getImage(GUI.GUIPrincipal.class.getResource("logoPrincipal1.gif"));
        f.setIconImage(splashIm);
        //Establezco la pantalla en el medio.
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = f.getSize();
        if (frameSize.height > screenSize.height)
        {
            frameSize.height = screenSize.height;
        }
        if (frameSize.width > screenSize.width)
        {
            frameSize.width = screenSize.width;
        }
        f.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);

        f.show();

    }


}