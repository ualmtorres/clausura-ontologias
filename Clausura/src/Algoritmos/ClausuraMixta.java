package Algoritmos;
import Repositorio.*;
import com.poet.odmg.util.*;
import com.poet.odmg.*;
import com.poet.odmg.imp.*;
import org.odmg.QueryException;
import org.odmg.ObjectNameNotUniqueException;
import org.odmg.ObjectNameNotFoundException;
import org.odmg.ODMGRuntimeException;
import org.odmg.ODMGException;
import java.util.*;
import java.io.*;

/**
 * T�tulo:Clausuara Mixta (Clausura con ampliaci�n + clausura con Reducci�n)
 * Descripcion:
 * Copyright:  Carlos �lvarez Bermejo  Copyright (c) 2001
 * Empresa:
 * @author Carlos �lvarez Bermejo
 * @version 1.0
 */

public class ClausuraMixta
{
    /**
     * M�todos propios para la clausura mixta.
     */

    /**
     * M�TODOS MODIFICADOS DEL ALGORITMO DE CLAUSURA CON AMPLIACI�N
     */

    /**
     * M�TODOS MODIFICADOS DEL ALGORITMO DE CLAUSURA CON REDUCCI�N
     */
    public ClausuraMixta()
    {
    }

    public void  HybridClousure(SetOfObject S,SetOfObject NT,SetOfObject T,Module M)
    {
        SetOfObject Needed=new SetOfObject();//Son los componentes necesarios que devuelve la clausura con ampliacion.
        //Devuelve las clases necesarias que tendremos que incluir a S para el siguiente paso.
        //Bien construida pq no guardo nada en la base de datos.... todod lo guarda el algoritmo de clausura por reduccion,
        //Hacer el peque�o ajuste de que cuando sea cerrado tb lo guarde todo.... que hasta ahora no lo hace.
        ClausuraAmpliacion primero= new ClausuraAmpliacion();
        Needed=primero.EnlargementClousore(S,NT,M);
        //A�adimos a S las clases necesarias devueltas por el enlargementclousure..... para el siguiente paso del algoritmo...
        Iterator I_Need=Needed.iterator();
        while(I_Need.hasNext())
        {
            GenericInterface aux=(GenericInterface)I_Need.next();
            S.add(aux);
        }

        System.out.println("finalizo la clausura con ampliacion");
        //Guarda todo en la base de datos
        //Ver como guarda los valores..... si me entorpece en algun aspecto.
        ClausuraReduccion ReductionClousure= new ClausuraReduccion(S,T,M);

    }
}