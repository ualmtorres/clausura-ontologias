package Algoritmos;

/**
 * Título:
 * Descripcion:
 * Copyright:    Copyright (c) 2001
 * Empresa:
 * @author
 * @version 1.0
 */


//Esta clase es el tipo de dato (pares) a almacenar dentro de ToBeUpdate. Que se precisará mas adelante.
public class nodoToBeUpdate
{
    String antiguo;
    String nuevo;

    nodoToBeUpdate(String ant, String nue)
    {
        this.setAntiguo(ant);
        this.setNuevo(nue);
    }
    //METODOS NECESARIOS PARA TRABAJAR CON LOS ATRIBUTOS DE LA CLASE.
    public void setAntiguo(String nombre)
    {

        this.antiguo=nombre;

    }

    public void setNuevo(String nombre)
    {

        this.nuevo=nombre;

    }

    public String getNuevo()
    {

        return this.nuevo;

    }

    public String getAntiguo()
    {

        return this.antiguo;

    }



}//Fin clase nodo to be updatein clase nodo to be update