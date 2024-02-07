package Algoritmos;
import com.poet.odmg.util.*;
import Repositorio.*;
import com.poet.odmg.*;
import org.odmg.QueryException;
import com.poet.odmg.imp.*;
import java.util.*;
/**
 * Título: Nodos de la Lista del algoritmo segundo.
 * Descripcion:
 * Copyright:    Copyright (c) 2001
 * Empresa:
 * @author Carlos Alvarez Bermejo
 * @version 1.0
 */

// Se define la clase que dara origen a los nodos.

class NodoLista
{
    String OldClassName;
    String NewClassName;
    SetOfObject ReferencesTo;//(Almacena por ahora las clases------>lo ideal que solo almacene strings)contiene las referencias a clases dentro del esquema
    SetOfObject ToBeDropped;
    SetOfObject ToBeUpdated;//Seran del tipo nodoToBeUpdate.


    public NodoLista()
    {
        this.ToBeDropped = new SetOfObject();
        this.ToBeUpdated=new SetOfObject();
        this.ReferencesTo = new SetOfObject();
    }

    public NodoLista(String nombre)
    {
        this.setOldName(nombre);
        this.ToBeDropped = new SetOfObject();
        this.ToBeUpdated=new SetOfObject();
        this.ReferencesTo = new SetOfObject();
    }
    //MÉTODO ESTABLECE EL OLDCLASSNAME

    public void setOldName(String nombre)
    {
        this.OldClassName=nombre;
    }

    public String getOldName()
    {
        return this.OldClassName;
    }

    public String getNewName()
    {
        return this.NewClassName;
    }



    //MÉTODO ESTABLECE EL NEWCLASSNAME

    public void setNewName(String nombre)
    {
        this.NewClassName=nombre;
    }

    //Metodo que busca entre la lista si existe el nombre que se le pasa como parametro + 1 o 2 o 3
    //Para generar un nuevo newName. Lo que hace es buscar que ese nombre no exista ya en la lista .

    public String setSearchNewName(String nombre, ListOfObject L)
    {
        int contador=1;
        boolean encontrado=false;
        String nombreaux=nombre+contador;
        NodoLista aux;

        while(!encontrado)
        {
            nombreaux=nombre+contador;
            encontrado=true;
            Iterator Ic = L.iterator();
            while(Ic.hasNext())
            {
                aux=(NodoLista)Ic.next();
                //compruebao si es igual que el nombre que he introducido
                if((nombreaux.equals(aux.getNewName()))||(nombreaux.equals(aux.getOldName())))
                {
                    encontrado=false;
                    contador++;
                    break;
                }

            }//while del medio

        }
        return nombreaux;

    }//Fin del metodo que busca el nombre.


    //Metodo que añade las referencias ToBeDropped.

    public void setToBeDropped(CollectionOfObject a)
    {
        Iterator Ic;
        Ic= a.iterator();
        if(this.ToBeDropped==null)
        {
            this.ToBeDropped=new SetOfObject();
        }

        while (Ic.hasNext())
        {

            this.ToBeDropped.add(Ic.next());

        }

    }
    //Metodo que añade las referencias a ReferencesTo

    public void setReferencesTo(CollectionOfObject a)
    {
        Iterator Ic;
        Ic= a.iterator();

        if(this.ReferencesTo==null)
        {
            this.ReferencesTo=new SetOfObject();
        }

        while (Ic.hasNext())
        {

            this.ReferencesTo.add(Ic.next());

        }

    }//Fin de addReferencesTo

    //Metodo que añade una referencia a ReferencesTo
    public void addReferencesTo(GenericInterface C)
    {

        if(this.ReferencesTo==null)
        {
            this.ReferencesTo=new SetOfObject();
        }

        this.ReferencesTo.add(C);

    }

    //Añadir ToBeUpdate
    public void addToBeUpdate(nodoToBeUpdate C)
    {
        if(this.ToBeUpdated==null)
        {
            this.ToBeUpdated=new SetOfObject();
        }
        this.ToBeUpdated.add(C);

    }

    //METODO QUE DEVUELVE TODOS LOS nodoToBeUpdate del nodoLista.
    public SetOfObject getToBeUpdate()
    {
        return this.ToBeUpdated;//Son del tipo nodoToBeUpdate.
    }


    //Metodo que devuelve el ReferencesTo de un nodo.

    public SetOfObject getReferencesTo()
    {

        return this.ReferencesTo;
    }

    /**METODO ADDTOLIST que viene en el pseudocódigo. Le paso
     * La clase a guardar y las referencias a las clases que va a guardar en ToBeDropped
     */

    public void setNodoLista(GenericInterface g,CollectionOfObject c)
    {
        this.setOldName(g.getName());
        this.setToBeDropped(c);
    }
    //Nodo que devuelve los que serán eliminados.
    //METODO QUE DEVUELVE TODOS LOS nodoToBeUpdate del nodoLista.
    public SetOfObject getToBeDropped()
    {
        return this.ToBeDropped;
    }

}//Fin de la clase NodosLista.
