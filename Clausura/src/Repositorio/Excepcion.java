package Repositorio;
import com.poet.odmg.*;
import com.poet.odmg.util.*;
import com.poet.odmg.imp.*;
import java.util.*;
/**
 * Título:
 * Descripcion:
 * Copyright:    Copyright (c) 2001
 * Empresa:
 * @author
 * @version 1.0
 */

public class Excepcion extends MetaobjectI implements Metaobject
{
    public SetOfObject operations;

    public Excepcion(String nom)
    {
        this.setName(nom);
    }
}