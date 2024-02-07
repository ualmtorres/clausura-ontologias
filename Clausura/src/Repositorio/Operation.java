package Repositorio;
import com.poet.odmg.*;
import com.poet.odmg.util.*;
import com.poet.odmg.imp.*;
import java.util.*;
import org.odmg.QueryException;
import org.odmg.ObjectNameNotUniqueException;
import org.odmg.ObjectNameNotUniqueException;
/**
 * Título:
 * Descripcion:
 * Copyright:    Copyright (c) 2001
 * Empresa:
 * @author
 * @version 1.0
 */

public class Operation extends Scope implements Metaobject
{
    public SetOfObject excepciones;//Relacion con la clase excepciones.
    public MetaobjectI metaobjop=new MetaobjectI();//Forwarding de la herencia desde metaobject.Este es en operation.
    public Object result;  //Es de tipo Object pero en realidad es de tipo TypeI.Relacion con result, las operaciones solo pueden tener un tipo de resultado.
    public String Parameters [];

    //Constructores....
    public Operation(String nom)
    {
        this.setName(nom);
        this.metaobjop = new MetaobjectI();
    }

    public Operation(String nom,GenericInterface almacenado_en,GenericInterface resu,Module m)
    {
        this.setName(nom);
        this.addUsedIn(m);//Modulo en el q se encuentra el GenericInterface que contiene a la operacion
        this.setResult(resu);//Tipo de resultado q proporciona la operacion
        this.setGIOperation(almacenado_en);//Establece en que Objeto esta almacenada esta operacion.

    }



    public void setName(String nom)
    {
        this.metaobjop.setName(nom);
    }

    public String getName()
    {
        return this.metaobjop.getName();
    }


    public void setComment(String com)
    {
        this.metaobjop.setComment(com);
    }

    public String getCommnet()
    {
        return this.metaobjop.getCommnet();
    }
    //Las operaciones van a pertenecer a una clase o a una interface y van a devolver un tipo
    //Y sus parametros seran de otro tripo.
    public void setResult(GenericInterface f)
    {

        this.result=f;
    }

    public Object getResult()
    {
        return this.result;
    }

    //Añade la operacion a un GenericInterface que es al que pertenece
    public void setGIOperation(GenericInterface g)
    {

        g.addOperation(this);

    }

    //Añadir al modulo al que pertenece o a otros modulos <<esta tb el inverso>>.
    public void addUsedIn(Module c)
    {
        if ( this.metaobjop.usedIn == null )
        {
            this.metaobjop.usedIn = new SetOfObject();
        }
        this.metaobjop.usedIn.add( c );
        c.addIncludes(this);
    }

}