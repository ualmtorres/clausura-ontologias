package Algoritmos;
import com.poet.odmg.util.*;
import Repositorio.*;
import com.poet.odmg.*;
import org.odmg.QueryException;
import com.poet.odmg.imp.*;
import java.util.*;
/**
 * Título: Algoritmo de clausura por ampliación.
 * Descripcion:Contiene todas las funciones necesarias para implementar la clausura de un esquema por amplicacion.
 * Copyright: carlos    Copyright (c) 2002
 * Empresa:Yo mismo
 * @author
 * @version 1.0
 */

public class ClausuraAmpliacion
{
    public SetOfObject Temp;
    public SetOfObject NeededClasses;

    //Se le pasa un conjunto de clases que el usuario define.
    public ClausuraAmpliacion()
    {
    }
    //Imprime pa depurar, lo que tiene una colleccion
    public  void imprime(CollectionOfObject a)
    {
        Iterator k = a.iterator();
        while(k.hasNext())
        {
            System.out.println(((GenericInterface)k.next()).getName());
        }
    }
    //devuelve si existe o no una clase en una coleccion.
    public  boolean Existe(CollectionOfObject a,GenericInterface c)


    {        boolean encontrado=false;
        Iterator k = a.iterator();
        while(k.hasNext())
        {
            if(c.getName().equals(((GenericInterface)k.next()).getName()))
            {
                encontrado=true;
            }
        }
        return encontrado;
    }


    //Obtiene todos los objetos a los q x hace referncia.
    public BagOfObject Uses(GenericClass x, Module md)
    {
        BagOfObject herencia= new BagOfObject();
        BagOfObject relacion= new BagOfObject();
        ListOfObject devolver= new  ListOfObject();
        Object resultado=null;

        //Devuelve una lista con todos los objetos que hacen referencia al objeto tratado.
        /**Obtener las relaciones de herecia.
         * Solo Interesan las clases que son superclases de la clase tratada (Ci)
         */
        try
        {
            Transaction tx= new Transaction();
            Properties props = new Properties();
            props.put( "readLockMode", "NONE" );
            tx.setProperties(props);
            tx.begin();
            OQLQuery Query = new OQLQuery(tx);
            Query.create("SELECT  a.CorrespondsToSuperclasses FROM ModuleClassesExtent AS a WHERE a.CorrespondsToSuperclasses IN ClaseExtent AND a.CorrespondsToSubclasses.name = $1 AND a.InModule.name = $2");
            Query.bind(x.getName());
            Query.bind(md.getName());
            String kk=Query.toString();
            resultado = Query.execute();
            herencia=(BagOfObject) resultado;

            tx.leave();
        }catch (QueryException ex)
        {
            ex.printStackTrace();
        }
        //Obtener los que se obtienen por relaciones. y devolverlos.
        try
        {
            Transaction tx2= new Transaction();
            resultado=null;
            Properties props = new Properties();
            // props.put( "readLockMode", "NONE" );
            tx2.setProperties(props);
            tx2.begin();
            OQLQuery Query2 = new OQLQuery(tx2);
            Query2.create("SELECT a FROM RelationshipExtent AS a WHERE ((GenericInterface)a.types).name = $1");
            Query2.bind(x.getName());
            String kk=Query2.toString();
            resultado = Query2.execute();
            BagOfObject relaux=(BagOfObject) resultado;
            /**Ver cual de ellos pertenece al modulo que se ha pedido mediante,
             * RelationShip.usedIn.name, los que no pertenezcan se eliminan del set.
             */
            Iterator it= relaux.iterator();
            while(it.hasNext())


            {                Relationship aux= new Relationship();
                aux=(Relationship)it.next();
                Iterator it2 = aux.usedIn.iterator();
                //Se comprueba si la relación esta en el modulo o no.
                while(it2.hasNext())


                {                    Module au = new Module("kk");
                    au =(Module) it2.next();
                    //Se comprueba que pertenezca al Modulo indicado.
                    if(au.getName().equals(md.getName()))
                    {
                        if(aux.getTraversal()!=null)
                        //Si fuera nulo implicaria que es solo en la direccion opuesta la navegabilidad
                        {
                            relacion.add(aux.getTraversal().getType());
                        }
                    }
                    System.out.println(au.getName());
                }

            }
            tx2.leave();
        }catch (QueryException ex)
        {
            ex.printStackTrace();
        }
        //Unir los dos, por ejemplo en la herencia y devolver la union en una lista.
        herencia.addAll(relacion);
        return herencia;
    }

    //Realiza la clausura con ampliacion, se le pasa el conjunto de clases y .
    public SetOfObject EnlargementClousore(SetOfObject c,SetOfObject SetToAnalyze, Module S)
    {
        ListOfObject Temp = new ListOfObject();//Usamos una lista, debido a q es como un array pero que puede crecer,permite ir añadiendo al final para tratar las demas clases.
        SetOfObject AuxiliarNeededClasses=new SetOfObject();
        SetOfObject NeededClasses=new SetOfObject();
        SetOfObject Interfa= new SetOfObject();
        Iterator Ic=SetToAnalyze.iterator();
        //Asignamos a Temp las clases que hay en C
        while(Ic.hasNext())
        {
            Temp.add((GenericInterface)Ic.next());
        }

        while (!Temp.isEmpty())
        {
            Clase Ci=new  Clase();
            GenericInterface auxGI;
            //Si es una interfaz... ignorarla... su tratamiento será posterior.
            auxGI=(GenericInterface)this.GetAndRemoveNext(Temp);
            if(Ci.isClase(auxGI.getName()))
            {
                //Devolver el siguiente de la lista de que esten en Temp.
                Ci=(Clase)auxGI;
                System.out.print("Esto es donde falla"+Ci.getName());
                this.imprime(Temp);
                //Aqui con el get class podriamos ver si estamos hablando de una clase o una interfaz
                if((!this.Existe(c,Ci)) &&(!this.Existe(NeededClasses,Ci)))
                {
                    NeededClasses.add(Ci);//Meterlo ya en la clase generada.
                }
                //Llamada a uses!!
                Iterator i=this.Uses(Ci,S).iterator();
                while(i.hasNext())
                {
                    Clase Ck;
                    Ck=(Clase)i.next();
                    System.out.println("CK: "+Ck.getName());
                    if((!this.Existe(c,Ck)) &&(!this.Existe(Temp,Ck))&&(!this.Existe(NeededClasses,Ck)))
                    {
                        Temp.addLast(Ck);//Posiblemente valdría con add--->COmprobarlo.
                        //Despues del uses
                        this.imprime(Temp);
                    }//Fin del If

                }//Fin del While que simula el For all
            }//fin del if de si es clase o interface
        }//Fin del While Global

        /**Buscar tambien las interfaces que intervienen.
         * A) Devolver y cambiar<-- Esta pq es un setofObject, es decir para cada una de las clases buscar si tienen interfaces.
         * B)Lo busco despues
         */
        System.out.println(NeededClasses.size());
        /**Añado a las classes necesarias la/s clases seleccionadas como entrada para el algoritmo
         * debido a que si no no me encontraria las interfaces Client-->Worker, por ello uso un auxiliar.
         */
        AuxiliarNeededClasses=(SetOfObject)AuxiliarNeededClasses.union(SetToAnalyze);//Antes usabamos C ahora SetToAnalyze
        AuxiliarNeededClasses=(SetOfObject)AuxiliarNeededClasses.union(NeededClasses);
        //En setToAnalyze estarian las interfaces que hemos suprimido en el primer paso
        Interfa=this.useInterfaces(AuxiliarNeededClasses,S);
        Iterator Volcado =Interfa.iterator();
        System.out.println(NeededClasses.size());
        System.out.println(AuxiliarNeededClasses.size());
        while(Volcado.hasNext())
        {
            NeededClasses.add((Interfaz)Volcado.next());
        }

        return NeededClasses;//Retorno las clases necesarias.
    }

    public Object GetAndRemoveNext(ListOfObject x)


    {        Object aux;
        aux=x.getFirst();
        x.removeFirst();
        return aux;
    }
    //RETORNA las interfaces para una clase q se le pasa, recorre toda la jerarquía
    public SetOfObject useInterfaces(SetOfObject NC, Module md)
    //Actual contiene las interfaces que ya se detectaron, para no volver a añadirlas.
    {
        SetOfObject resultado= new SetOfObject();
        ListOfObject temp= new ListOfObject();
        //Consulta sobre las interfaces de la clase actual, pero hay que recorrerlas para ver si hay ISA entre ellas..
        Iterator itNed = NC.iterator();
        while(itNed.hasNext())
   //Ver si es una interfaz en tal caso, ver si apunta a otra interfaz y guardarla si apunta a otra en el temp.. tratarla como se tratan a las clases.
        {
            Iterator findinterf=null;
            Clase uxis=new Clase();
            Interfaz uxisI=new Interfaz();
            GenericInterface auxGI=(GenericInterface)itNed.next();
            if(uxis.isClase(auxGI.getName()))
            {
                uxis=(Clase)auxGI;
                findinterf= uxis.getIsaClase(uxis,md).iterator();
            }
            if(uxisI.isInterfaz(auxGI.getName()))
            {
                uxisI=(Interfaz)auxGI;
                findinterf= uxisI.getIsaInterfaz(uxisI,md).iterator();
            }
            while(findinterf.hasNext())
            {
                Interfaz auxIn = new Interfaz();
                auxIn=(Interfaz)findinterf.next();
                if(!this.Existe(temp,auxIn))
                {
                    temp.add(auxIn);
                }
            }
        }
        //Buscar para cada interfaz encontrada hasta q se agote, es decir que el resultado sea null.
        while(!temp.isEmpty())


        {            Interfaz auxIn= new Interfaz();

            auxIn=(Interfaz)this.GetAndRemoveNext(temp);
            resultado.add(auxIn);
            Iterator auxIT=auxIn.getIsaInterfaz(auxIn,md).iterator();
            while (auxIT.hasNext())
            {
                auxIn=(Interfaz)auxIT.next();
                if((!this.Existe(temp,auxIn)&&(!this.Existe(resultado,auxIn))))
                {
                    temp.addLast(auxIn);
                }

            }

        }
        //Volver a ejecutar la consulta que existe en Interfaz sobre Subtype y supertype

        return resultado;
    }
}