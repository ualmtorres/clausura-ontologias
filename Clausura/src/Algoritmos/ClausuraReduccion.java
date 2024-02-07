package Algoritmos;
import com.poet.odmg.util.*;
import Repositorio.*;
import com.poet.odmg.*;
import org.odmg.QueryException;
import com.poet.odmg.imp.*;
import java.util.*;
import java.io.*;
import org.odmg.ObjectNameNotUniqueException;
import org.odmg.ObjectNameNotFoundException;
import org.odmg.ODMGRuntimeException;
import org.odmg.ODMGException;
/**
 * Título: algoritmo de clausura con reduccion
 * Descripcion: Contiene todas las funciones necesarias para implementar la clausura de un esquema por reducción.
 * Copyright:    Copyright (c) 2002
 * Empresa: Y
 * @author Carlos Alvarez Bermejo
 * @version 1.0
 */

public class ClausuraReduccion
{
    //Método Constructor de la clase.
    public ClausuraReduccion(SetOfObject S,SetOfObject SetToAnalyze, Module M)
    {
        System.out.println(M.getName());
        this.ReductionClosure(S,SetToAnalyze,M);
    }

    //MÉTODO EMPLEADO PARA IMPRIMIR UNA COLECCION YA SEA SET O BAG....Pag 69 del manual.
    public  void imprime(CollectionOfObject a)
    {
        Iterator k = a.iterator();
        while(k.hasNext())
        {
            System.out.println(((GenericInterface)k.next()).getName());
        }
    }//Fin imprime.


    //Obtiene todos los objetos a los q x hace referncia.
    public BagOfObject Uses(GenericInterface x, Module md)
    {
        BagOfObject herencia= new BagOfObject();
        BagOfObject isa=new BagOfObject();//almacenara las interfaces a las que se hace referencia.
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
        //Falta Obtener los que se obtienen por ISA... ya sean clases o interfaces
        try
        {
            Transaction tx3= new Transaction();
            Properties props = new Properties();
            props.put( "readLockMode", "NONE" );
            tx3.setProperties(props);
            tx3.begin();
            OQLQuery Query = new OQLQuery(tx3);
            Query.create("SELECT  a.CorrespondsToSupertypes FROM ModuleInterfacesExtent AS a WHERE a.CorrespondsToSupertypes IN InterfazExtent AND a.CorrespondsToSubtypes.name =$1 AND a.InModule.name = $2");
            Query.bind(x.getName());
            Query.bind(md.getName());
            String kk=Query.toString();
            resultado = Query.execute();
            isa=(BagOfObject) resultado;
            tx3.leave();
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
            props.put( "readLockMode", "NONE" );
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
            {
                Relationship aux= new Relationship();
                aux=(Relationship)it.next();
                Iterator it2 = aux.usedIn.iterator();
                //Se comprueba si la relación esta en el modulo o no.
                while(it2.hasNext())
                {
                    Module au = new Module("kk");
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
        herencia.addAll(isa);
        return herencia;
    }

    //devuelve si existe o no una clase en una coleccion.
    public  boolean Existe(CollectionOfObject a,GenericInterface c)
    {
        boolean encontrado=false;
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
    //Devuelve si existe una clase en la lista de NodosLista pasada por parametro
    public  boolean ExisteEnNodoLista(CollectionOfObject a,GenericInterface c)
    {
        boolean encontrado=false;
        Iterator k = a.iterator();
        while(k.hasNext())
        {
            if(c.getName().equals(((NodoLista)k.next()).getOldName()))
            {
                encontrado=true;
            }
        }
        return encontrado;
    }



    public Object GetAndRemoveNext(ListOfObject x)




    {        Object aux;
        aux=x.getFirst();
        x.removeFirst();
        return aux;
    }
    //Metodo que añade a ReferencesTo de Cj la referencia a Ci.
    public void UpdateUsesOfNodes(GenericInterface Cj,GenericInterface Ci,ListOfObject T)
    {
        GenericInterface auxCj=Cj;
        GenericInterface auxCi=Ci;
        NodoLista auxN=null;
        Iterator I_T = T.iterator();
        //Lo busca y lo actualiza en Temp
        while(I_T.hasNext())
        {
            int indice=0;
            auxN=(NodoLista)I_T.next();
            if(auxN.getOldName().equalsIgnoreCase(Cj.getName()))
            {
                //Ya tendria en auxN el valor de Nodolista correspondiente a Cj
                indice=T.indexOf(auxN);
                T.remove(indice);
                auxN.addReferencesTo(Ci);
                T.add(indice,auxN);
                break;//????furrulará?
            }
        }

    }//Fin updateusesofnodes
    /************************************GetReferneces****************************
     * Método que realiza la obtención de las clases a las que hace referencia los atributos y/o las operaciones
     * Tendrá mas sentido a la hora de realizar la creación de las clases o interfaces derivadas
     */
    public SetOfObject GetReferences(GenericInterface Ci,SetOfObject extRef, SetOfObject S)
    {
        //Recorrer todos los atributos y operaciones de la clase interfaz Ci... si encuentro uno
        //nuevo lo meto en intRef.
        SetOfObject intRef = new SetOfObject();//almacenara las clases a las que hace referncia los atributos y las operaciones y q no se encuentran en el set de referencias esternas!.
        SetOfObject auxTOP = new SetOfObject();//Almacenará los tipos de las operaciones de la clase Ci
        //Obtener los atributos de la clase/interface Ci.
        intRef=Ci.getTypeAttributes(Ci.getName());
        Iterator IR = intRef.iterator();
        GenericInterface auxGI;
        while(IR.hasNext())
        {
            auxGI=(GenericInterface)IR.next();
            if((extRef.contains(auxGI))||(S.contains(auxGI)))//Si está en las referencias externas o en el conjunto S se eliminan.
            {
                intRef.remove(auxGI);
            }
        }
        //Obtener los tipos (types) de las operaciones de la clase Ci.El método se crea en el GenericInterface.
        auxTOP=Ci.getResultOperation(Ci.getName());
        IR=auxTOP.iterator();
        while(IR.hasNext())
        {
            auxGI=(GenericInterface)IR.next();
            if((extRef.contains(auxGI))||(S.contains(auxGI)))
            {
                auxTOP.remove(auxGI);
            }
        }
        //Unir las dos clases en el intRef
        IR=auxTOP.iterator();
        while(IR.hasNext())
        {
            auxGI=(GenericInterface)IR.next();
            if(!intRef.contains(auxGI))
            {
                intRef.add(auxGI);
            }
        }

        //Retorno las clases a las que hacen referencia los atributos y las operaciones!
        return intRef;
    }

    //************************************Fin GetReferences*****************************
    //Método que encuentra las referencias externas de las clases y de las interfaces pasadas como parametro.
    public ListOfObject FindClasesWithExternalReferences(SetOfObject S,SetOfObject SetToAnalyze,Module M)
    //La funcion GetClasses es innecesaria pues ya pasamos los objetos como clases por parametro
    {
        SetOfObject ExternalReferences;
        SetOfObject InternalReferences;
        SetOfObject ReferencesTo;
        SetOfObject retorna= new SetOfObject();
        SetOfObject C = new SetOfObject();
        ListOfObject Temp = new ListOfObject();
        ListOfObject List = new ListOfObject();//Lista que almacenará la lista de NodosLista.

        //Asignamos a Temp y a C las clases de S
        Iterator Ic=S.iterator();
        while(Ic.hasNext())

        {            GenericInterface au=(GenericInterface)Ic.next();
            //    Temp.add(au);
            C.add(au);
        }

        //Cambio Introducido por el algoritmo Hibrido
        Iterator ISetToAnalyze = SetToAnalyze.iterator();
        while(ISetToAnalyze.hasNext())
        {
            GenericInterface au=(GenericInterface)ISetToAnalyze.next();
            Temp.add(au);
        }

        GenericInterface Ci;
        while(!Temp.isEmpty())
        {

            //Comprobar si es una clase o una interfaz y actuar en consecuencia al sacarlos de temp.

            Ci=(GenericInterface)this.GetAndRemoveNext(Temp);
            ExternalReferences = new SetOfObject();
            InternalReferences = new SetOfObject();
            ReferencesTo = new SetOfObject();

            //Deberia hacer lo mismo pero para las interfaces, para detectar los uses de las interfaces.

            //Aqui busco las referencias a clases.
            Iterator Iu = this.Uses(Ci,M).iterator();
            while (Iu.hasNext())
            {
                GenericInterface cAux= new GenericInterface();
                cAux=(GenericInterface)Iu.next();
                if((!this.Existe(C,cAux))&&(!this.Existe(ExternalReferences,cAux)))
                {

                    ExternalReferences.add(cAux);

                }
                /*else
                 { ESTO YA LO REALIZA EL PROPAGATE---PUES AÑADE LOS REFERENCES A TODAS LAS CLASES Q ESTAN EN LA LISTA DE MODIFICACION.
                                  //Las que no vayan a external references irán a ReferencesTo, con lo cual debo colocar otro SetOfObjects
                 ReferencesTo.add(cAux);
                                  }*/

            }//Fin del while

            //PARTE CON DUDAS DE SI HACERLA, INTERNAL REFERENCES,creare la clase por si las moscas.
            //Tendria que usar tambien la parte de atributos en relationcship.
            if(!ExternalReferences.isEmpty())
            {
                InternalReferences= GetReferences(Ci,ExternalReferences,S); // Solo devuelve referencias internas a objetos exterbnos....
                ExternalReferences.union(InternalReferences);
                System.out.println("Hay referencias externas!!!");
                //Añado a la lista de nodos el nuevo nodo creado.
                NodoLista auxi = new NodoLista();
                //Hacer metodo para generar nuevos nombres metiendo como parametro la lista y buscando si existe nombre+1, en old o new name
                auxi.setNodoLista(Ci,ExternalReferences);
                //Establezco el nuevo nombre de la clase/interfaz, hace la busqueda del nuevo nombre con el método setSearchNewName
                auxi.setNewName(auxi.setSearchNewName(Ci.getName(),List));
                //Establezco la lista de ReferencesTo si no esta vacía.
                if(!ReferencesTo.isEmpty())
                {
                    auxi.setReferencesTo(ReferencesTo);
                }
                List.add(auxi);
            }

        }//Fin del segundo while.


        return List;
    }//Fin de FindExternalReferences

    //METODO QUE IMPLEMENTA EL PROPAGATECHANGES(LA PROPAGACION DE LOS CAMBIOS)
    //S son las clases seleccionadas, List la lista de clases con referencias externas, y M el modulo.
    public ListOfObject PropagateChanges(SetOfObject S, ListOfObject List,Module M)
    {
        ListOfObject ListOfNodes=new ListOfObject();//Almacena la nueva lista de nodos que será devuelta.
        ListOfObject Temp = new ListOfObject();//Contiene el temporal de las clases que hay en la lista de reemplazables.
        SetOfObject C =new SetOfObject();//Obtiene las clases de S.
        //Paso lo que hay en List a temp y a ListOfNodes
        Iterator Ic=S.iterator();
        while(Ic.hasNext())
        {
            C.add(Ic.next());
        }

        //Sacamos a Temp y ListOfNodes lo que hay en List
        Iterator Ib= List.iterator();
        NodoLista auxN;
        while(Ib.hasNext())
        {
            auxN=(NodoLista)Ib.next();
            Temp.add(auxN);
            ListOfNodes.add(auxN);
        }

        //Consultar y obtener las "clases o interfaces" correspondientes a los nodos de la lista.
        //Volcado a los respectivos Sets
        //Se ha cambiado todo a GenericInterfaces para ver si shuta!
        GenericInterface Ci=new GenericInterface();
        GenericInterface Ck=new GenericInterface();
        NodoLista aux2=null;
        NodoLista aux3=null;
        while (!Temp.isEmpty())
        {
            aux2=(NodoLista)this.GetAndRemoveNext(Temp);//podria ser una interfaz
            Ci=Ck.getGeneric(aux2.getOldName());//Obtiene la clase de la bbdd a partir del nombre.
            Iterator Ik=C.iterator();
            //Equivale al For each.
            GenericInterface auxCj=null;
            GenericInterface Cj=new GenericInterface();
            while(Ik.hasNext())
            {
                Cj=(GenericInterface)Ik.next();
                //Hacemos la comprobacion de si pertenece al USES, EXTENT O ISA del Otro.
                //*Hay que comparar tres posibles casos:
                //*ci(interfaz)-cj(clase),ci(clase)-cj(clase), ci(interfaz)-cj(interfaz)

                if(this.Existe(this.Uses(Cj,M),Ci)||Ci.isExtenderOf(Cj,M)|| Cj.isSubtypeOf(Ci,M))
                {

                    if(!this.ExisteEnNodoLista(ListOfNodes,Cj))
                    {
                        //Ojo Cj hay que crear un NodoLista
                        aux3=new NodoLista(Cj.getName());
                        aux3.setNewName(aux3.setSearchNewName(Cj.getName(),ListOfNodes));;
                        ListOfNodes.add(aux3);
                        List.add(aux3);
                        Temp.addLast(aux3);

                    }
                    //El metodo UpdateUsesOfNodes que mete las referencias dentro de la clase.
                    //Si existe dentro de la lista de nodos tengo que actualizar su referencesTo.
                    //Por eso lo pone aqui para que recupere en ambos casos de la lista de nodos el Cj
                    //Actualiza los ReferencesTo en todas las listas.
                    this.UpdateUsesOfNodes(Cj,Ci,Temp);//Actualiza  en Temp
                    this.UpdateUsesOfNodes(Cj,Ci,ListOfNodes);//Actualiza en ListOfNodes
                    this.UpdateUsesOfNodes(Cj,Ci,List);//Actualiza List
                }//Fin del if
            }//Fin del while del For each
        }

        return ListOfNodes;
    }

    //Metodo AddReferences:comprueba si a la clase a la que hace referencia esta en
    //la lista de clases a reemplzar, y si lo esta crea los nodos tobeupdated.....
    public void addReference(NodoLista current,GenericInterface C,ListOfObject L)
    {

        //sacar los references del NodoLista.
        GenericInterface auxC=null;
        NodoLista auxN=null;
        Iterator It_R = current.getReferencesTo().iterator();

        while(It_R.hasNext())
        {
            auxC=(GenericInterface)It_R.next();
            //Busco el referencesTo en la lista de reemplazados.
            Iterator It_L = L.iterator();
            while(It_L.hasNext())
            {
                int indice=0;
                auxN=(NodoLista)It_L.next();
                if(auxN.getOldName().equalsIgnoreCase(auxC.getName()))
                {

                    indice=L.indexOf(current);
                    L.remove(indice);
                    nodoToBeUpdate auxNTU = new nodoToBeUpdate(auxN.getOldName(),auxN.getNewName());
                    current.addToBeUpdate(auxNTU);//Cambiar por addToBeUpdate
                    L.addAfter(indice-1,current);//Uso el addAfter pq el indice si es el ultimo puende no existir...pero el penultimo si

                    break;
                }
            }
        }//primer while
    }

    /**Metodo que hace la actualizacion del esquema de la base de datos.
     * Habra que empezar a implementar las operaciones de borrado en lugar de hacerlo
     * como en el primero, este será como una actualización.
     */
    public void tratamientoCurrentClase(ListOfObject List,NodoLista current,ListOfObject Relations,ListOfObject ClasesDerivadas,ListOfObject InterfDerivada,ListOfObject ExtendsClases,ListOfObject IsaClases, ListOfObject Atributos,ListOfObject Operaciones,Module Morigen,Module Mnuevo,SetOfObject S)
    {

        String nombreOrigen="";//Tenmos el origen de la relacion.
        String nombreDestino="";//Tenemos el destino de la relacion.
        String nombreO_old="";//Nombre origen antiguo---
        String nombreD_old="";//Nombre destino antiguo--
        DerivedClass dcorigen=new DerivedClass("origen");
        DerivedClass dcdestino=new DerivedClass("destino");
        DerivedInterface didestino = new DerivedInterface("destino");
        int indiceOrigen=0;//Contienen los indices del origen y el destino dentro de las
        int indiceDestino=0;//respectivas listas.

        //DEFINO LAS RELACIONES ENTRE CLASES QUE HAN CAMBIADO(RELATIONSHIPS,EXTEND,ISA(CON LAS INTERFACES) USO EL ToBeUpdate DEL CURRENT)
        Iterator TBU=current.getToBeUpdate().iterator();
        nombreOrigen=current.getNewName();
        nombreO_old=current.getOldName();

        while(TBU.hasNext())
        {
            //Indices que me dice donde esta el origen y el destino en la lista de clases derivadas.
            indiceOrigen=0;
            indiceDestino=0;
            //BUSCAR EL CURRENT.NEWname EN LA LISTA DE CLASES DERIVADAS DEFINIDAS PARA AÑADIR LA RELACION.
            nodoToBeUpdate  auxTBU=(nodoToBeUpdate)TBU.next();
            nombreDestino=auxTBU.getNuevo();
            nombreD_old=auxTBU.getAntiguo();
            Clase aux= new Clase();

            //Busco el indice del origen (current) y el destino en la lista de clases derivadas.
            Iterator I_CD = ClasesDerivadas.iterator();
            while(I_CD.hasNext())
            {

                DerivedClass aux2=(DerivedClass)I_CD.next();
                if(aux2.getName().equals(nombreOrigen))
                {
                    indiceOrigen=ClasesDerivadas.indexOf(aux2);
                }
                if(aux2.getName().equals(nombreDestino))
                {
                    indiceDestino=ClasesDerivadas.indexOf(aux2);
                }

            }//while de busqueda

            //HACER LA DIFERENCIACION DE SI ES O NO UNA CLASE EL DESTINO
            //Si es el destino una clase
            if(aux.isClase(nombreD_old))//Sacarlo y ponerlo unas linea mas abajo
            {
                //Ya tengo los indices del origen y el destino.
                System.out.println("indice origen "+indiceOrigen);
                System.out.println("indice Destino "+indiceDestino);
                dcorigen=(DerivedClass)ClasesDerivadas.get(indiceOrigen);
                dcdestino=(DerivedClass)ClasesDerivadas.get(indiceDestino);
                if(indiceOrigen<indiceDestino)
                {
                    ClasesDerivadas.remove(indiceDestino);
                    ClasesDerivadas.remove(indiceOrigen);//Lo elimino de la lista para volver a guardarlos luego
                }else
                {
                    ClasesDerivadas.remove(indiceOrigen);//Lo elimino de la lista para volver a guardarlos luego
                    ClasesDerivadas.remove(indiceDestino);
                }
                //VER SI ES un isa o extent, o un relationship. haciendo una consulta del tipo is...

                if(dcorigen.isExtenderOf(nombreO_old,nombreD_old,Morigen))
                {
                    //Entre las dos clases exite una relacion de herencia, creamos la relación.
                    ModuleClasses nuevaExtent = new ModuleClasses(dcdestino,dcorigen,Mnuevo);
                    ExtendsClases.add(nuevaExtent);
                }
                else//Si no es extend es pq unicamente ya solo puede ser Relationship
                {

                    //Obtenemos las clases que se vayan a meter en Type.

                    Relationship a= new Relationship(dcorigen,Mnuevo);
                    Relationship b= new Relationship(dcdestino,Mnuevo);
                    a.relacionar(b,0);
                    Relations.add(a);
                    Relations.add(b);
                }
                //Guardo en la lista de  clases derivadas otra vez las dos clases derivadas
                ClasesDerivadas.add(indiceOrigen,dcorigen);
                ClasesDerivadas.add(indiceDestino,dcdestino);

            }//Fin del if de si el destino es una clase.

            //Si el destino es una INTERFAZ
            if(dcorigen.isClaseIsaInterfaz(nombreO_old,nombreD_old,Morigen))//no estoy seguro... mejor definir una interfaz, y usar su metido isInterfaz
            //Seria una relacion ISA
            {
                //Obtenemos el destino que se encontrara en la lista de interfaces derivadas, puues no pudo ser calculado antes.

                Iterator I_ID = InterfDerivada.iterator();
                while(I_ID.hasNext())
                {
                    DerivedInterface auxinter=(DerivedInterface)I_ID.next();
                    if(auxinter.getName().equals(nombreDestino))
                    {
                        indiceDestino=InterfDerivada.indexOf(auxinter);
                    }

                }//while de busqueda del detino interfaz

                dcorigen=(DerivedClass)ClasesDerivadas.get(indiceOrigen);
                ClasesDerivadas.remove(indiceOrigen);
                didestino=(DerivedInterface)InterfDerivada.get(indiceDestino);
                InterfDerivada.remove(indiceDestino);

                ModuleInterfaces nuevaIsa = new ModuleInterfaces(dcdestino,dcorigen,Mnuevo);
                IsaClases.add(nuevaIsa);
                //Vuelvo a guardar la clase y la interfaz derivada en la lista
                ClasesDerivadas.add(indiceOrigen,dcorigen);
                InterfDerivada.add(indiceDestino,InterfDerivada);
            }//Fin del if de que el destino sea una interfaz.

        }//Fin while trata los TobeUpdate.

        //Relación de las clases derivadas con las clases que estan en el REFERENCESTO pero no en el ToBeUpdate
        //Definiria las relaciones con aquellas clases e interfaces que no son derivadas, es decir que no estan en las Listas de clases e interfaces derivadas.
        Iterator Idc=current.getReferencesTo().iterator();
        GenericInterface auxC = new GenericInterface();
        //Para cada uno de los referencesTo
        while(Idc.hasNext())
        {
            Clase aux= new Clase();
            boolean encontrado=false;
            auxC=(GenericInterface)Idc.next();
            //Saco la lista de ReferencesTo
            TBU = current.getToBeUpdate().iterator();
            while(TBU.hasNext())
            {
                if(auxC.getName().equalsIgnoreCase(((nodoToBeUpdate)TBU.next()).getAntiguo()))
                {
                    //Si está en la lista de ToBeUpdate, no se hace nada
                    System.out.print("HA SIDO ENCONTRADO EN LA LISTA TOBEUPDATE");
                    encontrado=true;
                    break;//Sale del bucle.. es tonteria seguir buscando en la lista de TOBEUPDATE si ya lo ha encontrado.
                }

            }//While TBU

            if(!encontrado)
            {
                //Entonces defino una nueva relacion entre la clase derivada actual y la clase/interfaz que se esté tratando del ReferencesTo
                //Aqui es donde hago la diferencia y creo la pertienete relación.
                //Si es el destino una clase
                Clase I_CSaux=new Clase();
                if(aux.isClase(auxC.getName()))
                {
                    //Tengo solo el indice origen---calculado lineas arriba.
                    dcorigen=(DerivedClass)ClasesDerivadas.get(indiceOrigen);
                    ClasesDerivadas.remove(indiceOrigen);//Lo elimino de la lista para volver a guardarlos luego
                    //Buscamos la clase de destino. Lo busco en el conjunto de clases-interfaces seleccionados es decir "S". Al ser un SetOfObjects no se puede coger en indice.
                    Iterator I_CS = S.iterator();
                    while(I_CS.hasNext())
                    {

                        I_CSaux=(Clase)I_CS.next();
                        if(I_CSaux.getName().equals(auxC.getName()))
                        {
                            S.remove(I_CSaux);//Lo elimino del set pq lo voy a guardar otra vez despues de realizar modificaciones.
                            break;//En I_CSaux tengo el destino
                        }

                    }

                    //VER SI ES un isa o extent, o un relationship. haciendo una consulta del tipo is...
                    if(dcorigen.isExtenderOf(nombreO_old,I_CSaux.getName(),Morigen))
                    {
                        //Entre las dos clases exite una relacion de herencia, creamos la relación.
                        ModuleClasses nuevaExtent = new ModuleClasses(I_CSaux,dcorigen,Mnuevo);
                        ExtendsClases.add(nuevaExtent);
                    }
                    else//Si no es extend es pq unicamente ya solo puede ser Relationship
                    {

                        //Obtenemos las clases que se vayan a meter en Type.
                        Relationship a= new Relationship(dcorigen,Mnuevo);
                        Relationship b= new Relationship(I_CSaux,Mnuevo);
                        a.relacionar(b,0);
                        Relations.add(a);
                        Relations.add(b);
                    }
                    //Guardo en la lista de  clases derivadas y en el Set S(original) guardo
                    ClasesDerivadas.add(indiceOrigen,dcorigen);
                    S.add(I_CSaux);//Guardo el objeto modificado en el SET
                }//Fin del if de si el destino es una clase.

                //Si el destino es una interfaz
                if(dcorigen.isClaseIsaInterfaz(nombreO_old,auxC.getName(),Morigen))//no estoy seguro... mejor definir una interfaz, y usar su metido isInterfaz
                //Existe otra manera que es averiguandolo con el metodo isInterface.
                {
                    //Buscamos la interface de destino
                    Iterator I_ID = S.iterator();
                    GenericInterface I_Iaux= new GenericInterface();
                    while(I_ID.hasNext())
                    {
                        I_Iaux=(GenericInterface)I_ID.next();
                        if(I_Iaux.getName().equals(auxC.getName()))
                        {
                            indiceDestino=InterfDerivada.indexOf(I_Iaux);
                        }

                    }//while de busqueda del detino interfaz

                    dcorigen=(DerivedClass)ClasesDerivadas.get(indiceOrigen);
                    ClasesDerivadas.remove(indiceOrigen);
                    S.remove(I_Iaux) ;//El destino ya lo tengo en el la variable I_Iaux

                    ModuleInterfaces nuevaIsa = new ModuleInterfaces(I_Iaux,dcorigen,Mnuevo);
                    IsaClases.add(nuevaIsa);
                    //Vuelvo a guardar la clase en las clases derivadas y la interfaz  en el conjunto S
                    ClasesDerivadas.add(indiceOrigen,dcorigen);
                    S.add(I_Iaux);
                }//Fin del bucle que identifica a una interface.
            }
        }//while de la lista de ReferencesTo

        //LOS ATRIBUTOS Y DE LAS OPERACIONES.
        //Obtener todos los atributos que tendria esta clase u/o interfaz
        SetOfObject ListaAtributos=GenericInterface.getAttributes(current.getOldName());
        //Obtener todas las operaciones que tendria esta clase
        SetOfObject ListaOperaciones=GenericInterface.getOperations(current.getOldName());
        //Eliminar aquellos que tienen el tipo o el resultado con clases incluidas en tobedropped
        System.out.println("Esta mierda esta vacia? "+current.getNewName());
        SetOfObject Dropped=current.getToBeDropped();
        Iterator I_d=Dropped.iterator();
        GenericInterface auxgi;
        Attribute auxAt;
        Operation auxOp;
        while(I_d.hasNext())//While para eliminar los atributos con referencias externas.
        {
            auxgi=(GenericInterface)I_d.next();
            Iterator I_A=ListaAtributos.iterator();
            Iterator I_O=ListaOperaciones.iterator();
            while(I_A.hasNext())
            {
                auxAt=(Attribute)I_A.next();
                if(auxgi.getName().equalsIgnoreCase(((GenericInterface)auxAt.getType()).getName()))
                {
                    ListaAtributos.remove(auxAt);
                }
            }
            while(I_O.hasNext())
            {
                auxOp=(Operation)I_O.next();
                if(auxgi.getName().equalsIgnoreCase(((GenericInterface)auxOp.getResult()).getName()))
                {
                    ListaOperaciones.remove(auxOp);
                }
            }//fin while de la lista de dropped


        }
        //While para eliminar las operaciones o atributos que tengan el tipo de una clase o interfaz q haya sido creada como derivada. Buscarlos en la lista de clases nodo.
        Iterator I_List=List.iterator();//Contiene la lista de nosdos que han cambiado y que por tanto serán objetos derivados
        NodoLista nodoaux;
        while(I_List.hasNext())
        {
            nodoaux=(NodoLista)I_List.next();
            Iterator I_A=ListaAtributos.iterator();
            Iterator I_O=ListaOperaciones.iterator();
            //Atributos
            while(I_A.hasNext())
            {
                auxAt=(Attribute)I_A.next();
                if(nodoaux.getOldName().equalsIgnoreCase(((GenericInterface)auxAt.getType()).getName()))
                {
                    ListaAtributos.remove(auxAt);
                }
            }
            //Operaciones
            while(I_O.hasNext())
            {
                auxOp=(Operation)I_O.next();
                if(nodoaux.getOldName().equalsIgnoreCase(((GenericInterface)auxOp.getResult()).getName()))
                {
                    ListaOperaciones.remove(auxOp);
                }
            }

        }//Fin  while de la lista de clases/interfaces que se convierten en derivadas.
        //Solo nos quedan dentro de ListaAtributos y ListaOperaciones los atributos y las operaciones que hacen referencia
        //a clases dentro del conjunto S(selecionado por el usuario) y que no han tenido que ser creadas como derivadas.
        //Buscar la clase derivada..... y meterle  los atributos y las  operaciones.
        Iterator I_Clase=ClasesDerivadas.iterator();
        DerivedClass auxdc;
        int deleteIndice;
        while(I_Clase.hasNext())
        {
            auxdc=(DerivedClass)I_Clase.next();
            if(auxdc.getName().equalsIgnoreCase(current.getNewName()))
            {
                deleteIndice=ClasesDerivadas.indexOf(auxdc);
                //Guardamos tanto los atributos como las operciones en la clase derivada.
                Iterator I_A=ListaAtributos.iterator();
                Iterator I_O=ListaOperaciones.iterator();
                //Atributos
                while(I_A.hasNext())
                {
                    auxAt=(Attribute)I_A.next();
                    auxdc.addProperties(auxAt);//Añado los atributos
                }
                //Añado todas las operaciones
                while(I_O.hasNext())
                {
                    auxOp=(Operation)I_O.next();
                    auxdc.addOperation(auxOp);//Añado las operaciones
                }

                //Eliminamos la clase derivada de la lista y la volvemos a introducir.
                ClasesDerivadas.remove(deleteIndice);
                ClasesDerivadas.add(auxdc);
            }//Fin del if... que da lugar a guardar las operaciones y los atributos

        }//Fin while I_Clase

    }//Fin tratamiento clases

    public void tratamientoCurrentInterfaz(ListOfObject List,NodoLista current,ListOfObject InterfDerivadas,ListOfObject IsaInterfaz,ListOfObject Atributos,ListOfObject Operaciones,Module Morigen, Module Mnuevo,SetOfObject S)
    {
        //Esta funcion actualiza en las interfaces derivadas sus posibles relaciones con otroas interfaces que tb se hayan convertido en derivadas.
        //Nos pasan el current, en este caso es una interfaz, obtenemos sus ToBeUpdate, como los ToBeUpdate de una interfaz solo puede ser otra interfaz
        //pq son los únicos elementos a los que puede apuntar una interfaz son a otra interfaz.
        //y obtenemos las interfaces origen y destino y creamos la clase que define las relaciones ISA, es decir un ModuleInterfaces
        ////////
        String nombreOrigen="";//Tenmos el origen de la relacion ISA.
        String nombreDestino="";//Tenemos el destino de la relacion ISA.
        String nombreO_old="";//Nombre origen antiguo---
        String nombreD_old="";//Nombre destino antiguo--
        DerivedInterface diorigen=new DerivedInterface("origen");
        DerivedInterface didestino=new DerivedInterface("destino");
        int indiceOrigen=0;
        int indiceDestino=0;

        //DEFINO LAS RELACIONES ENTRE LAS INTERFACES QUE HAN CAMBIADO(Solo existiran relaciones Isa) DEL ToBeUpdate)
        Iterator TBU=current.getToBeUpdate().iterator();
        nombreOrigen=current.getNewName();//Obtenemos los nombres del origen.
        nombreO_old=current.getOldName();

        while(TBU.hasNext())
        {
            //Indices que me dice donde esta el origen y el destino en la lista de interfaces derivadas.
            indiceOrigen=0;
            indiceDestino=0;
            //BUSCAR EL CURRENT.NEWname EN LA LISTA DE INTERFACES DERIVADAS DEFINIDAS PARA AÑADIR LA RELACION ISA.
            nodoToBeUpdate auxTBU=(nodoToBeUpdate)TBU.next();
            nombreDestino=auxTBU.getNuevo();
            nombreD_old=auxTBU.getAntiguo();
            Interfaz aux= new Interfaz();
            //El destino solo puede ser una interfaz, pero por si acaso lo comprobamos
            if(aux.isInterfaz(nombreD_old))
            {
                //Busco el indice del origen(current) y el destino en la lista de interfaces derivadas.
                Iterator I_ID = InterfDerivadas.iterator();
                while(I_ID.hasNext())
                {

                    DerivedInterface aux2=(DerivedInterface)I_ID.next();
                    if(aux2.getName().equals(nombreOrigen))
                    {
                        indiceOrigen=InterfDerivadas.indexOf(aux2);
                    }
                    if(aux2.getName().equals(nombreDestino))
                    {
                        indiceDestino=InterfDerivadas.indexOf(aux2);
                    }

                }
                //Ya tengo los indices del origen y el destino.

                diorigen=(DerivedInterface)InterfDerivadas.get(indiceOrigen);
                InterfDerivadas.remove(indiceOrigen);
                didestino=(DerivedInterface)InterfDerivadas.get(indiceDestino);
                InterfDerivadas.remove(indiceDestino);

                //La relacion solo puede ser de un tipo, es decir ISA.
                //Sería una relacion ISA

                ModuleInterfaces nuevaIsa = new ModuleInterfaces(didestino,diorigen,Mnuevo);
                IsaInterfaz.add(nuevaIsa);

                //Volvemos a meter las dos interfaces derivadas en la lista.

                InterfDerivadas.add(indiceOrigen,diorigen);
                InterfDerivadas.add(indiceDestino,didestino);
            }//Fin del if de si el destino es una interfaz.

        }//Fin while trata los TobeUpdate.

        //( REFERENCESTO ) DEFINO LAS RELACIONES CON OTRAS INTERFACES QUE SE ENCUENTRAN EN EL SET Y NO ESTAN EN LA LISTA DE INTERFACES A CAMBIAR (ReferencesTo)
        //Este cubriria el caso en que una interfaz derivada hace referncia a una interfaz que no es derivada, es decir el que está en la lista referencesTo
        Iterator Idc=current.getReferencesTo().iterator();
        GenericInterface auxC = new GenericInterface();
        while(Idc.hasNext())
        {
            Interfaz aux= new Interfaz();
            boolean encontrado=false;
            auxC=(GenericInterface)Idc.next();
            //Saco la lista de ReferencesTo
            TBU = current.getToBeUpdate().iterator();
            while(TBU.hasNext())
            {
                if(auxC.getName().equalsIgnoreCase(((nodoToBeUpdate)TBU.next()).getAntiguo()))
                {
                    //Si está en la lista de ToBeUpdate, no se hace nada
                    System.out.println("LA INTERFACE DE DESTINO HA SIDO ENCONTRADA EN LA LISTA TOBEUPDATE");
                    encontrado=true;
                    break;//Sale del bucle.. es tonteria seguir buscando en la lista de TOBEUPDATE si ya lo ha encontrado.
                }

            }//While TBU

            if(!encontrado)
            //Si no fue encontrado en la lista de ToBEUpdate... toces tengo buscar en destino en S y
            {
                //añadir la relacion que es una relacion ISA.
                //////////////////////////////////////////
                //Si es el destino una interface que es lo único que puede ser
                Interfaz I_ISaux=new Interfaz();
                if(aux.isInterfaz(auxC.getName()))
                {
                    //Tengo solo el indice origen---calculado lineas arriba.
                    diorigen=(DerivedInterface)InterfDerivadas.get(indiceOrigen);
                    InterfDerivadas.remove(indiceOrigen);//Lo elimino de la lista para volver a guardarlos luego
                    //Buscamos la interfaz de destino. Lo busco en el conjunto de clases-interfaces seleccionados es decir "S". Al ser un SetOfObjects no se puede coger en indice.
                    Iterator I_IS = S.iterator();
                    while(I_IS.hasNext())
                    {

                        I_ISaux=(Interfaz)I_IS.next();
                        if(I_ISaux.getName().equals(auxC.getName()))
                        {
                            S.remove(I_ISaux);//Lo elimino del set pq lo voy a guardar otra vez despues de realizar modificaciones.
                            break;//En I_ISaux tengo el destino
                        }

                    }

                    //ENTRE DOS INTERFACES SOLO PUEDE SER ISA LA RELACION
                    ModuleInterfaces nuevaIsa = new ModuleInterfaces(I_ISaux,diorigen,Mnuevo);
                    IsaInterfaz.add(nuevaIsa);
                    //Vuelvo a guardar la clase en las clases derivadas y la interfaz  en el conjunto S
                    InterfDerivadas.set(indiceOrigen,diorigen);
                    S.add(I_ISaux);
                }//Fin del bucle que identifica a una interface.
            }
        }
        //WHILE DE LOS ATRIBUTOS Y DE LAS OPERACIONES
        //Obtener todas las operaciones que tendria esta clase
        //Eliminar aquellos que tienen el tipo o el resultado con clases incluidas en tobedropped
        //Ver si Existen atributos u operaciones q tengan el tipo como alguno de los que estan en el tobeupdate y actualizar sus tipos

        //LOS ATRIBUTOS Y DE LAS OPERACIONES EN LA INTERFAZ DERIVADA.
        //Obtener todos los atributos que tendria esta interfaz
        SetOfObject ListaAtributos=GenericInterface.getAttributes(current.getOldName());
        //Obtener todas las operaciones que tendria esta Interfaz
        SetOfObject ListaOperaciones=GenericInterface.getOperations(current.getOldName());
        //Eliminar aquellos que tienen el tipo o el resultado con clases incluidas en tobedropped
        SetOfObject Dropped=current.getToBeDropped();
        Iterator I_d=Dropped.iterator();
        GenericInterface auxgi;
        Attribute auxAt;
        Operation auxOp;
        while(I_d.hasNext())//While para eliminar los atributos con referencias externas.
        {
            auxgi=(GenericInterface)I_d.next();
            Iterator I_A=ListaAtributos.iterator();
            Iterator I_O=ListaOperaciones.iterator();
            while(I_A.hasNext())
            {
                auxAt=(Attribute)I_A.next();
                if(auxgi.getName().equalsIgnoreCase(((GenericInterface)auxAt.getType()).getName()))
                {
                    ListaAtributos.remove(auxAt);
                }
            }
            while(I_O.hasNext())
            {
                auxOp=(Operation)I_O.next();
                if(auxgi.getName().equalsIgnoreCase(((GenericInterface)auxOp.getResult()).getName()))
                {
                    ListaOperaciones.remove(auxOp);
                }
            }//fin while de la lista de dropped


        }
        //While para eliminar las operaciones o atributos que tengan el tipo de una clase o interfaz q haya sido creada como derivada. Buscarlos en la lista de clases nodo.
        Iterator I_List=List.iterator();//Contiene la lista de nosdos que han cambiado y que por tanto serán objetos derivados
        NodoLista nodoaux;
        while(I_List.hasNext())
        {
            nodoaux=(NodoLista)I_List.next();
            Iterator I_A=ListaAtributos.iterator();
            Iterator I_O=ListaOperaciones.iterator();
            //Atributos
            while(I_A.hasNext())
            {
                auxAt=(Attribute)I_A.next();
                if(nodoaux.getOldName().equalsIgnoreCase(((GenericInterface)auxAt.getType()).getName()))
                {
                    ListaAtributos.remove(auxAt);
                }
            }
            //Operaciones
            while(I_O.hasNext())
            {
                auxOp=(Operation)I_O.next();
                if(nodoaux.getOldName().equalsIgnoreCase(((GenericInterface)auxOp.getResult()).getName()))
                {
                    ListaOperaciones.remove(auxOp);
                }
            }

        }//Fin  while de la lista de interfaces que se convierten en derivadas.
        //Solo nos quedan dentro de ListaAtributos y ListaOperaciones los atributos y las operaciones que hacen referencia
        //a clases dentro del conjunto S(selecionado por el usuario) y que no han tenido que ser creadas como derivadas.
        //Buscar la clase derivada..... y meterle  los atributos y las  operaciones.
        Iterator I_Interfaz=InterfDerivadas.iterator();
        DerivedInterface auxdi;
        int deleteIndice;
        while(I_Interfaz.hasNext())
        {
            auxdi=(DerivedInterface)I_Interfaz.next();
            if(auxdi.getName().equalsIgnoreCase(current.getNewName()))
            {
                deleteIndice=InterfDerivadas.indexOf(auxdi);
                //Guardamos tanto los atributos como las operciones en la clase derivada.
                Iterator I_A=ListaAtributos.iterator();
                Iterator I_O=ListaOperaciones.iterator();
                //Atributos
                while(I_A.hasNext())
                {
                    auxAt=(Attribute)I_A.next();
                    auxdi.addProperties(auxAt);//Añado los atributos
                }
                //Añado todas las operaciones
                while(I_O.hasNext())
                {
                    auxOp=(Operation)I_O.next();
                    auxdi.addOperation(auxOp);//Añado las operaciones
                }

                //Eliminamos la interfaz derivada de la lista y la volvemos a introducir.
                InterfDerivadas.remove(deleteIndice);
                InterfDerivadas.add(auxdi);
            }//Fin del if... que da lugar a guardar las operaciones y los atributos

        }


    }//Fin de tratarInterfaz
    //------------------------------REPLACE----------------------------------------------
    public void Replace(SetOfObject S, ListOfObject Tmp,ListOfObject DC,ListOfObject DI)
    {
        NodoLista current=new NodoLista();
        boolean encontrado=false;
        while(!Tmp.isEmpty())
        {
            GenericInterface c = new GenericInterface();
            DerivedClass auxDC = new DerivedClass("auxc");
            DerivedInterface auxDI = new DerivedInterface("auxi");
            current=(NodoLista)this.GetAndRemoveNext(Tmp);
            Iterator I_S=S.iterator();
            while(I_S.hasNext())
            {
                encontrado=false;
                c=(GenericInterface)I_S.next();
                if(c.getName().equals(current.getOldName()))
                {
                    //Si son iguales eliminamos el objeto de S para substituirlo por el derivado.
                    S.remove(c);
                    Iterator I_CD=DC.iterator();
                    Iterator I_CI=DI.iterator();
                    while(I_CD.hasNext())
                    {
                        auxDC=(DerivedClass)I_CD.next();
                        if(auxDC.getName().equals(current.getNewName()))
                        {
                            //Si son iguales lo hemos encotrado, lo metemos en S.
                            S.add(auxDC);
                            encontrado=true;
                            break;
                        }
                    }
                    while((I_CI.hasNext())&&(!encontrado))
                    {
                        auxDI=(DerivedInterface)I_CI.next();
                        if(auxDI.getName().equals(current.getNewName()))
                        {
                            //Si son iguales lo hemos encotrado en la lista de clases derivadas, y lo insertamos en S
                            S.add(auxDI);
                            encontrado=true;
                            break;
                        }
                    }


                }
            }

        }

        //Imprimo los objetos que hay en S para ver si queda fino o no
        Iterator II=S.iterator();
        GenericInterface auxImprimir=new GenericInterface();
        System.out.println("LISTADO DE LOS OBJETOS QUE SE INTRODUCIRAN EN EL NUEVO ESQUEMA");
        while(II.hasNext())
        {
            auxImprimir=(GenericInterface)II.next();
            System.out.println("Objeto: "+auxImprimir.getName());
        }
    }//Fin del método replace
    //--------------------------------Guardar en la Base de datos-------------------
    public void GuardarBBDD(SetOfObject S,ListOfObject ClasesDerivadas,ListOfObject InterfDerivada,ListOfObject ExtendsClases,ListOfObject IsaClases,ListOfObject Relations,ListOfObject IsaInterfaz,ListOfObject Atributos,ListOfObject Operaciones,Module Mnuevo )
    {
        //Base de datos.
        Database db2=Database.current();//Obtine el identificador de la base de datos actual, la que se encuentra abierta.

        //Primero me guardaré el módulo dentro de la base de datos.
        /*   try{
         Transaction tx= new Transaction();
         tx.begin();
         //Creamos el primer módulo o esquema que servirá de ejemplo.
         db2.bind(Mnuevo,Mnuevo.getName());
         tx.commit();
         }catch (ObjectNameNotUniqueException ex){
         ex.printStackTrace();
         }*/
        //Guardamos todos los objetos en la base de datos asociandola al modulo creado
        //Probamos a guardar las ClasesDerivadas a ver si salta el error de tener referencias a objetos no incluidos
        //Al guardar las clases me esta guardando tb todas las relaciones de extend, relationship e Isa

        Database db3=new Database();
        try
        {
            db2=Database.current();
            db2.close();
            db3.open(basedeDatos.BASEDEDATOS, Database.OPEN_READ_WRITE);
        }
        catch ( ODMGException x )
        {
            x.printStackTrace();
        }

        //Guardo todas las clases derivadas.
        String nombre="";
        int i=0;
        try
        {
            Transaction tx= new Transaction();
            Properties props = new Properties();
            props.put("readLockMode","NONE");
            props.put("readAfterTerminate", "true");
            tx.setProperties(props);
            tx.setName(new Integer(i).toString());
            tx.begin();
            System.out.println(tx.getId());
            db3.bind(ClasesDerivadas,null);
            tx.commit();
        }catch (ObjectNameNotUniqueException ax)
        {
            ax.printStackTrace();
        }


        //Falta guardar las interfaces derivadas.
        //Guardo todas las interfaces derivadas.
        try
        {
            Transaction tx= new Transaction();
            Properties props = new Properties();
            props.put("readLockMode","NONE");
            props.put("readAfterTerminate", "true");
            tx.setProperties(props);
            tx.begin();
            db3.bind(InterfDerivada,null);
            tx.commit();
        }catch (ObjectNameNotUniqueException ex)
        {
            ex.printStackTrace();
        }

        //Falta guardar las clases e interfaces que quedan en S y q no son ni clases ni interfaces derivadas.
        //-Para estas clases e interfaces solo tengo que meterlas en el modulo nuevo, es decir modificar su usedin, y para eso ya lo tenia hecho.


        Iterator I_S= S.iterator();
        GenericInterface auxGEIN= new GenericInterface();
        Module Modnuevo=new Module("auxiliar");
        while(I_S.hasNext())
        {
            auxGEIN=(GenericInterface)I_S.next();
            Clase auxCL=new Clase();
            Interfaz auxIN=new Interfaz();
            //Obtenemos el modulo, obtenemos la clase y guardamos todo.

            if((!this.Existe(ClasesDerivadas,auxGEIN))&&(!this.Existe(InterfDerivada,auxGEIN)))
//Si no existe en ninguno de los dos es pq es una clase o una interfaz.
            {
                if(auxCL.isClase(auxGEIN.getName()))
                {
                    auxCL.getClaseAddUsedIn(auxGEIN.getName(),Mnuevo);

                }//Fin del if

                //Si es una interfaz.... haremos lo mismo pero... con ella
                if(auxIN.isInterfaz(auxGEIN.getName()))
                {
                    auxIN.getInterfazAddUsedIn(auxGEIN.getName(),Mnuevo);
                }//fin if de la interfaz

            }

        }
        Database db4=new Database();
        try
        {
            db3=Database.current();
            db3.close();
            db4.open(basedeDatos.BASEDEDATOS, Database.OPEN_READ_WRITE);
        }
        catch ( ODMGException x )
        {
            x.printStackTrace();
        }

    }
    //Guarda en el nuevo módulo el esquema cuando este es cerrado.
    public void GuardarBBDDCerrado(SetOfObject S,Module Mnuevo)
    {
        //Guardo el módulo nuevo que no fue guardado.
        Database db2=Database.current();

        Database db3=new Database();
        try
        {
            db2.close();
            db3.open(basedeDatos.BASEDEDATOS, Database.OPEN_READ_WRITE);
        }
        catch ( ODMGException x )
        {
            x.printStackTrace();
        }

        try
        {
            Transaction tx= new Transaction();
            tx.begin();
            //Creamos el primer módulo o esquema que servirá de ejemplo.
            db3.bind(Mnuevo,Mnuevo.getName());
            tx.commit();
        }catch (ObjectNameNotUniqueException ex)
        {
            ex.printStackTrace();
        }
        //Falta guardar las clases e interfaces que quedan en S y q no son ni clases ni interfaces derivadas.
        //-Para estas clases e interfaces solo tengo que meterlas en el modulo nuevo, es decir modificar su usedin, y para eso ya lo tenia hecho.

        Iterator I_S= S.iterator();
        GenericInterface auxGEIN= new GenericInterface();
        Module Modnuevo=new Module("auxiliar");
        while(I_S.hasNext())
        {
            auxGEIN=(GenericInterface)I_S.next();
            Clase auxCL=new Clase();
            Interfaz auxIN=new Interfaz();
            //Obtenemos el modulo, obtenemos la clase y guardamos todo.

            //Si no existe en ninguno de los dos es pq es una clase o una interfaz.
            if(auxCL.isClase(auxGEIN.getName()))
            {
                auxCL.getClaseAddUsedIn(auxGEIN.getName(),Mnuevo);

            }//Fin del if

            //Si es una interfaz.... haremos lo mismo pero... con ella
            if(auxIN.isInterfaz(auxGEIN.getName()))
            {
                auxIN.getInterfazAddUsedIn(auxGEIN.getName(),Mnuevo);
            }//fin if de la interfaz

        }


    }

    //-----------------------------------------------------------------------------------
    public void UpdateSchema(SetOfObject S, ListOfObject List, Module Morigen, Module Mnuevo)
    {

        ListOfObject Temp = new ListOfObject();
        ListOfObject Temp2 = new ListOfObject();//Servira para el segundo bucle.
        ListOfObject Temp3 = new ListOfObject();//Servirá para pasarle los valores del current a la funcion replace.
        Iterator I_L = List.iterator();
        ListOfObject ClasesDerivadas= new ListOfObject();//Contiene las clases derivadas definidas en el penultimo bucle.
        ListOfObject InterfDerivada = new ListOfObject();//Contiene las interfaces derivadas del segundo bucle.
        ListOfObject ExtendsClases = new ListOfObject();//Contiene los ModuleClases que se crean.
        ListOfObject IsaClases = new ListOfObject();//Contiene las relaciones isa que se crean entre ClaseDerivada-->interfaz.
        ListOfObject Relations = new ListOfObject();//Contiene las relaciones Relationship entre las clases derivadas.
        ListOfObject IsaInterfaz = new ListOfObject();//Contiene las relaciones isa que se crean entre InterfazDerivada--->Interfaz, en el supuesto de que una de ellas esté fuera del esquema cogido..
        ListOfObject Atributos = new ListOfObject();//Contiene los atributos que se han creado por haber cambiado el tipo.
        ListOfObject Operaciones = new ListOfObject();//Contiene las operaciones que se han creado

        while(I_L.hasNext())
        {
            Temp.add((NodoLista)I_L.next());
        }

        Iterator I_T = Temp.iterator();
        NodoLista current=null;
        GenericInterface Cj=null;
        //While que contiene al for each.
        while(I_T.hasNext())
        {
            Iterator I_R;//Iterator para el ReferencesTo
            current=(NodoLista)I_T.next();
            //For each
            I_R = current.getReferencesTo().iterator();
            while(I_R.hasNext())
            {

                Cj=(GenericInterface)I_R.next();
                //Aqui va el if para ver si Cj esta dentro de Temp. Lo compruebo por el nombre.
                Iterator I_T2= Temp.iterator();
                while(I_T2.hasNext())
                {
                    if(((NodoLista)I_T2.next()).getOldName().equals(Cj.getName()))
                    {

                        this.addReference(current,Cj,List);
                    }

                }


            }

        }//fin while current----------------------------

        //EL PRIMER BUCLE SOLO LAS DEFINE, Y LAS METE EN UN LIST PARA PODER RECORRERLAS DESPUES
        //EN EL SEGUNDO BUCLE.

        Iterator I_tmp=Temp.iterator();
        NodoLista auxNoLis;
        while (I_tmp.hasNext())
        {
            auxNoLis=(NodoLista) I_tmp.next();
            Temp3.add(auxNoLis);
            Temp2.add(auxNoLis);
        }

        while(!Temp.isEmpty())
        {
            Clase auxClase = new Clase("c1");
            Interfaz auxInter = new Interfaz("i1");
            current=(NodoLista)this.GetAndRemoveNext(Temp);

            if(auxClase.isClase(current.getOldName()))
            //Le añado el parametro de donde debe ser definida, S es el esquema que se ha seleccionado, Current es el nodo actualmente analizado y Mnuevo es el modulo nuevo.
            {
                DerivedClass auxDC = DefineDerivedClass(current,S,Mnuevo);
                ClasesDerivadas.add(auxDC);
            }
            if(auxInter.isInterfaz(current.getOldName()))
            {
                DerivedInterface auxDI= DefineDerivedInterface(current,S,Mnuevo);
                InterfDerivada.add(auxDI);
            }

        }//fin while

        //EN ESTE BUCLE GENERO LOS RELATIONSHIP(TOBEUPDATE Y REFERENCESTO), LOS ATRIBUTOS Y LAS OPERACIONES!
        while(!Temp2.isEmpty())
        {

            Clase auxClase = new Clase("c2");
            Interfaz auxInter = new Interfaz("I2");
            current=(NodoLista)this.GetAndRemoveNext(Temp2);

            if(auxClase.isClase(current.getOldName()))
            {
                System.out.println("Se esta tratando la clase "+current.getNewName());
                this.tratamientoCurrentClase(List,current,Relations,ClasesDerivadas,InterfDerivada,ExtendsClases,IsaClases,Atributos,Operaciones,Morigen,Mnuevo,S);

            }

            if(auxInter.isInterfaz(current.getOldName()))
            {
                //DEFINO LAS RELACIONES CON LAS CLASES QUE CAMBIAN (ESTAN DENTRO DE LA LISTA)--Pero estas ya las he detectado en el caso anterior de Clase
                //DEFINO LAS RELACIONES (ISA) CON OTRAS POSIBLES INTERFACES QUE ESTAN DENTRO DEL ES DECIR QUE VAN A CAMBIAR.
                this.tratamientoCurrentInterfaz(List,current,InterfDerivada,IsaClases,Atributos,Operaciones,Morigen,Mnuevo,S);//Falta añadirle todas los parámetros necesarios, aunq creo que ya estan.

            }

        }//fin while temp2
        //Se realiza en reemplazamiento de las claser por las interfaces y las clases derivadas dentro del conjunto de clases.
        Replace(S,Temp3,ClasesDerivadas,InterfDerivada);
        //Guardo en BBDD lo que hay en S y en elresto
        GuardarBBDD(S,ClasesDerivadas,InterfDerivada,ExtendsClases,IsaClases,Relations,IsaInterfaz,Atributos,Operaciones,Mnuevo);
    }//Fin del updateSchema


    //Metodo que implementa la clase derivada a partir de los datos de la misma.
    //Emplea la información de NewClassname el tobedropped y el tobeupdate para la definicion.

    public DerivedClass DefineDerivedClass(NodoLista curr,SetOfObject S,Module M)
    {
        //Establecemos el nombre de la clase derivada.
        DerivedClass dclass = new DerivedClass(curr.getNewName(),M);
        return dclass;
    }

    //Método empleado par definir las interfaces derivadas.
    public DerivedInterface DefineDerivedInterface(NodoLista curr,SetOfObject S,Module M)
    {
        //Establecemos el nombre de la clase derivada.
        DerivedInterface dinter = new DerivedInterface(curr.getNewName(),M);
        return dinter;
    }

    /**Función Principal de la Clausura por reduccion.
     *Paso como parametro tanto la lista de clases seleccionadas como
     *el modulo del que se selecciona.
     *
     */
    public void ReductionClosure(SetOfObject S,SetOfObject SetToAnalyze, Module C)
    {
        // Module Modulo_nuevo = new Module(Calendar.getInstance().getTime().toString());
        Module Modulo_nuevo = new Module(basedeDatos.getNombreEsquemaNuevo());
        ListOfObject List= new ListOfObject();//Almacenara nodos del tipo nodoLista

        List=FindClasesWithExternalReferences(S,SetToAnalyze,C);
        Iterator IL=List.iterator();

        while(IL.hasNext())
        {
            NodoLista ax;
            System.out.println("DESPUES FINDCLASSES");
            ax=(NodoLista)IL.next();
            System.out.println("Nombre viejo");
            System.out.println(ax.getOldName());
            System.out.println("Nombre Nuevo");
            System.out.println(ax.getNewName());

        }
        if (!List.isEmpty())
        {

            List=PropagateChanges(S,List,C);
            IL=List.iterator();
            while(IL.hasNext())
            {
                System.out.println("DESPUES PROPAGATE");
                NodoLista ax;
                ax=(NodoLista)IL.next();
                System.out.println("Nombre viejo");
                System.out.println(ax.getOldName());
                System.out.println("Nombre Nuevo");
                System.out.println(ax.getNewName());

            }
            UpdateSchema(S,List,C,Modulo_nuevo);
        }else
        {
            //Si no hay referencias externas, entonces
            //Guardar en la base de datos cuando no hay referencias externas, tendría que añadir estas clases al nuevo modulo.
            this.GuardarBBDDCerrado(S,Modulo_nuevo);
        }
    }

}//Fin de la clase reducctionclousure

