package Algoritmos;
import Repositorio.*;
import java.*;
import java.util.*;
import java.util.Vector;
import java.awt.Color;
import java.io.FileOutputStream;
import java.io.IOException;
import com.poet.odmg.*;
import com.poet.odmg.util.*;
import com.poet.odmg.imp.*;
import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import com.lowagie.text.pdf.PdfWriter;
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

public class basedeDatos
{
    public static String BASEDEDATOS="FastObjects://LOCAL/C:/Clausura/BaseDeDatos/Repositorio_base";
    public static int MODO_BASEDEDATOS=Database.OPEN_READ_WRITE;
    public static String NombreNuevoEsquema="Modulo Nuevo";
    public basedeDatos()
    {
    }
    //Elimina todos los objetos que hay dentro de la base de datos.
    public static void clear()
    {
        Database db=new Database().current();
        Database db1=new Database();
        if(db.isOpen())
        {
            try
            {
                db.close();
                db1.open(basedeDatos.BASEDEDATOS, Database.OPEN_READ_WRITE);
            }catch(ODMGException ex)
            {
                ex.printStackTrace();
            }
        }else
        {
            try
            {
                db1.open(basedeDatos.BASEDEDATOS, Database.OPEN_READ_WRITE);
            }catch(ODMGException ex)
            {
                ex.printStackTrace();
            }
        }

        Transaction [] a = db1.getTransactions();
        for(int i=1;i<=a.length;i++)
        {
            a[i].commit();
        }

        try
        {
            Transaction tx= new Transaction();
            tx.begin();
            Extent ex = new Extent("java.lang.Object");
            ObjectServices.current().deleteAll(ex);
            tx.commit();
        }catch(ODMGRuntimeException ec)
        {
            ec.printStackTrace();
        }
    }

    //Método que carga los ejemplos en la base de datos.
    public static void loadData()
    {
        Database db;
        db = new Database().current();
        if(!db.isOpen())
        {

            try
            {
                db.open(basedeDatos.BASEDEDATOS, Database.OPEN_READ_WRITE);

            }
            catch ( ODMGException x )
            {
                x.printStackTrace();
            }
        }

        //Cargamos los ejemplos(El que aparece en el manual).
        try
        {
            Transaction tx= new Transaction();
            tx.begin();
            //Creamos el primer módulo o esquema que servirá de ejemplo.
            Module M1=new Module("Esquema Principal");
            db.bind(M1,"EsquemaPrincipal");
            //Creamos las clases y las interfaces que intervendran en el esquema.
            Clase C1=new Clase("Address");
            C1.addUsedIn(M1);//digo en el modulo que esta, y me rellena solo el inverso.
            db.bind(C1,"Address");
            Clase C2=new Clase("People");
            C2.addUsedIn(M1);
            db.bind(C2,"People");
            Clase C3=new Clase("Car");
            C3.addUsedIn(M1);
            db.bind(C3,"Car");
            Clase C4=new Clase("Manufacturer");
            C4.addUsedIn(M1);
            db.bind(C4,"Manufacturer");
            Clase C5=new Clase("Client");
            C5.addUsedIn(M1);
            db.bind(C5,"Client");
            Clase C6=new Clase("Employee");
            C6.addUsedIn(M1);
            db.bind(C6,"Employee");
            Clase C7=new Clase("Temporary");
            C7.addUsedIn(M1);
            db.bind(C7,"Temporary");
            Interfaz I1=new Interfaz("Worker");
            I1.addUsedIn(M1);
            db.bind(I1,"Worker");
            //Creamos las relaciones(habra q usar inversos)
            //Address<--->People
            Relationship R1 = new Relationship(C2,M1);
            Relationship R2 = new Relationship(C1,M1);
            R1.relacionar(R2,0);
            //People<---->Car
            Relationship R3 = new Relationship(C2,M1);
            Relationship R4 = new Relationship(C3,M1);
            R3.relacionar(R4,1);
            //Car<-->Manufacturer
            Relationship R5 = new Relationship(C3,M1);
            Relationship R6 = new Relationship(C4,M1);
            R5.relacionar(R6,1);


            //Creamos los las relaciones de herencia y las guardamos en la bbdd.
            //Relaciones ISA
            ModuleInterfaces MI1= new ModuleInterfaces(I1,C6,M1);
            db.bind(MI1,null);
            ModuleInterfaces MI2= new ModuleInterfaces(I1,C7,M1);
            db.bind(MI2,null);
            //Relaciones Extends
            ModuleClasses MC1= new ModuleClasses(C2,C5,M1);
            db.bind(MC1,null);
            ModuleClasses MC2= new ModuleClasses(C2,C6,M1);
            db.bind(MC2,null);
            //DECLARACION DE LOS ATRIBUTOS!
            Attribute A1=new Attribute("Conductor Ocasional",C2,C3,M1);
            db.bind(A1,null);
            //DECLARACION DE LAS OPERACIONES.
            Operation Q1= new Operation("Opera",C3,C2,M1);
            db.bind(Q1,null);
            tx.commit();
        }
        catch (ObjectNameNotUniqueException ex)
        {
            ex.printStackTrace();
        }
        //cerramos la base de datos.
        try
        {
            db.close();
        }
        catch ( ODMGException x )
        {
            x.printStackTrace();
        }

    }
    //Establece la URL de la base de datos a emplear en la ejecucion de la aplicación.
    public static void setBASEDEDATOS(String url)
    {
        basedeDatos.BASEDEDATOS=url;

    }
    //Establece el nombre que se le dará al nuevo módulo.
    public static void setNombreEsquemaNuevo(String nombre)
    {
        if(nombre.equalsIgnoreCase(""))
        {
            NombreNuevoEsquema=new String(new Date(System.currentTimeMillis()).toString());
        }else
        {
            NombreNuevoEsquema=nombre;
        }
    }
    //Obtiene el nombre del nuevo esquema que se va a crear.
    public static String getNombreEsquemaNuevo()
    {
        return NombreNuevoEsquema;
    }
    //Métodos que proporcionan reportes de la base de datos, en este caso la crea un documento.
    public static void generarInforme(String nombre,String modulo)
    {

        System.out.println("Generando Informe del Esquema");

        // step 1: creation of a document-object
        Document document = new Document();

        try
        {
            //Establecemos las fuentes para el título.

            // step 2:
            // we create a writer that listens to the document
            // and directs a PDF-stream to a file

            PdfWriter.getInstance(document, new FileOutputStream(nombre));


            // step 3: Abrimos el documento y escribimos los metadatos

            document.addTitle("Informe Del Esquema");
            document.addSubject("Genera un informe detallado del modulo seleccionado");
            document.addKeywords("ODMG, Clausura, Esquemas, Externo");
            document.addCreator("Proyecto final de carrera");
            document.addProducer();
            document.addAuthor("Carlos Alvarez Bermejo");
            document.addHeader("Informe Generado por la aplicación", "0");
            HeaderFooter footer = new HeaderFooter(new Phrase("Página "), true);
            footer.setBorder(Rectangle.NO_BORDER);
            document.setFooter(footer);
            HeaderFooter header = new HeaderFooter(new Phrase("                         Clausura de Esquemas Externos para bases de datos ODMG",FontFactory.getFont(FontFactory.HELVETICA_BOLDOBLIQUE, 12, Font.BOLDITALIC)), false);
            document.setHeader(header);

            document.open();

            //Creacion del documento..... aqui poner lo que quieras ooo massss!
            document=generarDocumento(modulo,document);


        }
        catch(DocumentException de)
        {
            System.err.println(de.getMessage());
        }
        catch(IOException ioe)
        {
            System.err.println(ioe.getMessage());
        }

        // step 5: we close the document
        document.close();
    }


    public static Document generarDocumento(String nombreModulo,Document document)
    {
        //Variables para obtener cada uno de los elementos.
        BagOfObject clasesModulo;
        BagOfObject interfacesModulo;
        BagOfObject clasesDModulo;
        BagOfObject interfacesDModulo;
        String resultados="";
        String modulo=nombreModulo;
        //Método que obtiene todas las clases, interfaces y derivadas con relaciones del tipo a-b.
        clasesModulo=(new Clase()).getAllClases(modulo);
        interfacesModulo=(new Interfaz()).getAllInterfaces(modulo);
        clasesDModulo=(new DerivedClass("aux")).getAllDerivedClass(modulo);
        interfacesDModulo=(new DerivedInterface("auxI")).getAllDerivedInterface(modulo);
        //Solucionar el problema de cuando no existan relaciones se sigan pintando las clases.
        Iterator Mismo=clasesModulo.iterator();
        try
        {
            Table datatable = new Table(10);
            datatable.setCellsFitPage(true);

            datatable.setPadding(4);
            datatable.setSpacing(0);
            //datatable.setBorder(Rectangle.NO_BORDER);
            int headerwidths[] =
            {                10, 24, 12, 12, 7, 7, 7, 7, 7, 7};
            datatable.setWidths(headerwidths);
            datatable.setWidth(100);

            // the first cell spans 10 columns
            Cell cell = new Cell(new Phrase("COMPONENTES DEL ESQUEMA", FontFactory.getFont(FontFactory.HELVETICA, 24, Font.BOLD)));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setLeading(30);
            cell.setColspan(10);
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
            datatable.addCell(cell);

            // These cells span 2 rows
            datatable.setDefaultCellBorderWidth(2);
            datatable.setDefaultHorizontalAlignment(1);
            datatable.setDefaultColspan(2);
            datatable.setDefaultRowspan(2);
            datatable.addCell("Esquema");
            datatable.addCell(new Phrase("Clases", FontFactory.getFont(FontFactory.HELVETICA, 14, Font.BOLD)));
            datatable.addCell("Interfaces");
            datatable.addCell("Clases derivadas");
            datatable.addCell("Interfaces Derivadas");



            // this is the end of the table header
            datatable.endHeaders();

            datatable.setDefaultCellBorderWidth(1);
            datatable.setDefaultRowspan(1);


            datatable.setDefaultHorizontalAlignment(Element.ALIGN_LEFT);

            datatable.addCell(modulo);
            resultados="";
            //Busco las clases
            while(Mismo.hasNext())
            {
                String nombreClase= ((Clase)Mismo.next()).getName();
                resultados=resultados+nombreClase+"\n";
            }
            datatable.addCell(resultados);
            resultados="";
            //Busco y escribo las interfaces.
            Mismo=interfacesModulo.iterator();
            while(Mismo.hasNext())
            {
                String nombre= ((Interfaz)Mismo.next()).getName();
                resultados=resultados+nombre+"\n";
            }
            datatable.addCell(resultados);
            resultados="";
            //Clases derivadas
            Mismo=clasesDModulo.iterator();
            while(Mismo.hasNext())
            {
                String nombre= ((DerivedClass)Mismo.next()).getName();
                resultados=resultados+nombre+"\n";
            }
            datatable.addCell(resultados);

            //Busco y escribo las interfaces derivadas.
            resultados="";
            Mismo=interfacesDModulo.iterator();
            while(Mismo.hasNext())
            {
                String nombre= ((DerivedInterface)Mismo.next()).getName();
                resultados=resultados+nombre+"\n";
            }
            datatable.addCell(resultados);
            datatable.setDefaultHorizontalAlignment(Element.ALIGN_CENTER);




            document.add(datatable);
            document.add(new Paragraph(""));



        }catch(BadElementException a)
        {            a.printStackTrace();}
        catch(DocumentException f)
        {            f.printStackTrace();}


        //Tabla que contiene las relaciones

        try
        {
            Table datatable = new Table(4);

            datatable.setCellsFitPage(true);

            datatable.setPadding(4);
            datatable.setSpacing(0);
            //datatable.setBorder(Rectangle.NO_BORDER);
            int headerwidths[] =
            {                15,12, 12, 12};
            datatable.setWidths(headerwidths);
            datatable.setWidth(100);

            // the first cell spans 10 columns
            Cell cell = new Cell(new Phrase("RELACIONES ENTRE LOS COMPONENTES", FontFactory.getFont(FontFactory.HELVETICA, 24, Font.BOLD)));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setLeading(30);
            cell.setColspan(4);
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
            datatable.addCell(cell);

            // These cells span 2 rows
            datatable.setDefaultCellBorderWidth(2);
            datatable.setDefaultHorizontalAlignment(1);
            datatable.setDefaultColspan(1);
            datatable.setDefaultRowspan(2);
            datatable.addCell("Esquema");
            datatable.addCell(new Phrase("Extent", FontFactory.getFont(FontFactory.HELVETICA, 14, Font.BOLD)));
            datatable.addCell("Agregación");
            datatable.addCell("Isa");

            String isa="";
            String exten="";
            String relat="";

            // this is the end of the table header
            datatable.endHeaders();

            datatable.setDefaultCellBorderWidth(1);
            datatable.setDefaultRowspan(1);


            datatable.setDefaultHorizontalAlignment(Element.ALIGN_LEFT);

            datatable.addCell(modulo);
            resultados="";
            //Busco las extent
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

                    if((new Clase()).isExtenderOf(nombreClase2,nombreClase,modulo))
                    {
                        //nombreClase2--->nombreClase.
                        exten=exten+nombreClase2+"->"+nombreClase+"\n";
                    }
                    //Ver si existe una relación entre las dos clases.
                    if((new Clase()).isRelationship(nombreClase,nombreClase2,modulo))
                    {
                        relat=relat+nombreClase+"->"+nombreClase2+"\n";
                    }

                }
            }


            //Caso 1º clase extent o Relation clase y viceversa.

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
                        exten=exten+nombreDClase2+"->"+nombreDClase+"\n";
                    }
                    //Ver si existe una relación entre las dos clases.
                    if((new Clase()).isRelationship(nombreDClase,nombreDClase2,modulo))
                    {
                        relat=relat+nombreDClase+"->"+nombreDClase2+"\n";
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
                        exten=exten+nombreClase+"->"+nombreDClases+"\n";
                    }
                    if((new Clase()).isExtenderOf(nombreDClases,nombreClase,modulo))
                    {
                        //nombreDClases--->nombreClase.
                        exten=exten+nombreDClases+"->"+nombreClase+"\n";
                    }

                    //COMPROBAR SI CLASE RELATIONSHIP DCLASE y vice
                    if((new Clase()).isRelationship(nombreDClases,nombreClase,modulo))
                    {
                        relat=relat+nombreDClases+"->"+nombreClase+"\n";
                    }
                    if((new Clase()).isRelationship(nombreClase,nombreDClases,modulo))
                    {
                        relat=relat+nombreClase+"->"+nombreDClases+"\n";
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
                        isa=isa+nombreClase+"->"+nombreInterfaz+"\n";
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
                        isa=isa+nombreDClase+"->"+nombreInterfaz+"\n";
                    }
                }
            }

            datatable.addCell(exten);
            datatable.addCell(relat);
            datatable.addCell(isa);
            datatable.setDefaultHorizontalAlignment(Element.ALIGN_CENTER);



            document.add(datatable);
        }catch(BadElementException a)
        {            a.printStackTrace();}
        catch(DocumentException f)
        {            f.printStackTrace();}
        //-----------------GENERACION DE ODL------------------

        try
        {
            Table datatable = new Table(1);

            datatable.setCellsFitPage(true);

            datatable.setPadding(4);
            datatable.setSpacing(0);
            //datatable.setBorder(Rectangle.NO_BORDER);
            int headerwidths[] =
            {                63};
            datatable.setWidths(headerwidths);
            datatable.setWidth(100);

            // the first cell spans 10 columns
            Cell cell = new Cell(new Phrase("ODL GENERADO DEL ESQUEMA", FontFactory.getFont(FontFactory.HELVETICA, 24, Font.BOLD)));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setLeading(30);
            cell.setColspan(1);
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
            datatable.addCell(cell);

            // These cells span 2 rows
            /* datatable.setDefaultCellBorderWidth(2);
             datatable.setDefaultHorizontalAlignment(1);
             datatable.setDefaultColspan(1);
             datatable.setDefaultRowspan(1);
             datatable.addCell("ODL");*/

            // this is the end of the table header
            /*   datatable.endHeaders();
                          datatable.setDefaultCellBorderWidth(1);
             datatable.setDefaultRowspan(1);*/


            /*    datatable.setDefaultHorizontalAlignment(Element.ALIGN_LEFT);*/

            resultados="";
            resultados="module "+modulo+"{\n";
            //Busco y escribo las interfaces.
            Mismo=interfacesModulo.iterator();
            while(Mismo.hasNext())
            {
                String nombre= ((Interfaz)Mismo.next()).getName();
                resultados=resultados+"interface "+nombre+"\n{\n}\n";
            }

            //Busco las clases
            Mismo=clasesModulo.iterator();
            while(Mismo.hasNext())
            {
                String nombreClase= ((Clase)Mismo.next()).getName();
                resultados=resultados+"class "+nombreClase;
                //Busco los extent
                resultados=resultados+(String)(new Clase()).getExtend(nombreClase,modulo);
                //Buscar ISA
                resultados=resultados+(String)(new Clase()).getIsaClase(nombreClase,modulo)+"{\n";
                //Obtener los atributos
                resultados=resultados+(new GenericInterface()).getAttributesODL(nombreClase);
                //Obtener las operaciones y/o relaciones
                resultados=resultados+(new GenericClass()).getRelationshipODL(nombreClase,modulo);
                resultados=resultados+"} \n";
            }
            //     resultados="";


            //Clases derivadas
            Mismo=clasesDModulo.iterator();
            while(Mismo.hasNext())
            {
                String nombre= ((DerivedClass)Mismo.next()).getName();
                resultados=resultados+" class "+nombre;
                //Busco los extent
                resultados=resultados+(String)(new DerivedClass("auxiliar")).getDerivedExtend(nombre,modulo);
                //Buscar ISA (Usamos la misma debido a que no necesitamos pa nunca se generan interfaces derivadas)
                resultados=resultados+(String)(new Clase()).getIsaClase(nombre,modulo)+"{";
                //Obtener los atributos
                resultados=resultados+(new GenericInterface()).getAttributesODL(nombre);
                //Obtener las operaciones y/o relaciones
                resultados=resultados+(new GenericClass()).getRelationshipODL(nombre,modulo);
                resultados=resultados+"}\n";
            }


            //Busco y escribo las interfaces derivadas.

            Mismo=interfacesDModulo.iterator();
            while(Mismo.hasNext())
            {
                String nombre= ((DerivedInterface)Mismo.next()).getName();
                resultados=resultados+nombre+"{\n}";
            }


            System.out.println(resultados);
            datatable.setDefaultHorizontalAlignment(Element.ALIGN_CENTER);

            //Cierro el modulo
            resultados=resultados+"}";
            document.add(datatable);
            document.add(new Paragraph(resultados));
        }catch(BadElementException a)
        {            a.printStackTrace();}
        catch(DocumentException f)
        {            f.printStackTrace();}


        //Obtener quienes tiene relacion con quien e introducirlas en la cadena REsultado

        return document;
    }


}