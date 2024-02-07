//Source file: C:\\DOCPROYECTOFINDECARRERA\\PRUEBAKK\\sintitulo2\\src\\Repositorio\\Attribute.java

package Repositorio;

public class Attribute extends Property
  //Puede ser de un tipo pero estar almacenado en otra clase.
{
    public boolean is_read_only;
    //Constructor........
    public Attribute(String nombre_atributo,GenericInterface tipo,GenericInterface almacen, Module f)
    {
        this.setName(nombre_atributo);
        this.setType(tipo,almacen);
        super.addUsedIn(f);
    }

    //Establece el tipo del atributo y en que objeto (clase o interface) se almacena.
    public void setType(GenericInterface g,GenericInterface k)
    {
        this.types=g;
        k.addProperties(this);//Relleno el inverso, es decir las propiedades de la clase.
    }

    //Obtiene el tipo del atributo
    public Object getType()
    {
        return this.types;
    }

    //Añadir el atributo a la lista de propiedades de otro/a clase/interfaz.
    public void addToGIProperties(GenericInterface g)
    {

        g.addProperties(this);
    }
    //Añade un atributo a otro module en el q se esté utilizando
    public void addAttributeUsedIn(Module M)
    {
        super.addUsedIn(M);
    }


}
