import java.util.ArrayList;


/**
 * Write a description of class NPC here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class NPC
{
    private boolean agresivo;
    private String nombre;
    private String descripcion;
    private int ataque;
    private int resistencia;
    private ArrayList<Item> inventario;

     public NPC(boolean agresivo, String nombre, String descripcion, int ataque, int resistencia)
    {
        this.agresivo = agresivo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.ataque = ataque;
        this.resistencia = resistencia;
        inventario = new ArrayList<Item>();
    }

    
    /**
     * Resta resistencia al PNJ
     * @param res La resistencia a restar
     */
    public void restaRes(int res)
    {
        resistencia -= res;
    }
    
    /**
     * Devuelve un objeto del inventario indicado por parametro
     * @param nombre El nombre del objeto a entregar
     * @return El objeto una vez encontrado
     */
    public Item search(String nombre)
    {
        boolean encontrado = false;
        Item objeto = null;
        int index = 0;
        // Busca el objeto en el inventario, si lo encuentra lo devuelve
        while((index < inventario.size()) && !(encontrado))
        {
            if(inventario.get(index).getItemName().equals(nombre))
            {
                objeto = inventario.get(index);
                encontrado = true;
            }
            index++;
        }
        return objeto;
    }
    
    /**
     * Elimina un objeto del inventario del PNJ pasado como parametro
     * @param obj El objeto a eliminar del inventario
     */
    public void remove(Item obj)
    {
        inventario.remove(obj);
    }
    
    /**
     * Devuelve la resistencia del PNJ
     * @return La resisencia del PNJ
     */
    public int getResistencia()
    {
        return resistencia;
    }
    
    /**
     * Devuelve el ataque del PNJ
     * @return El ataque del PNJ
     */
    public int getAtaque()
    {
        return ataque;
    }
    
    public void addItem(Item obj)
    {
        inventario.add(obj);
    }

    /**
     * Devuelve el nombre del PNJ
     * @return El nombre del PNJ
     */
    public String getNombre()
    {
        return nombre;
    }
    
    /**
     * Devuelve si el PNJ es agresivo o no
     * @return True si es agresivo, false sino
     */
    public boolean isAgresivo()
    {
        return agresivo;
    }
    
    /**
     * Devuelve un String con la información del PNJ
     * @return un String con la información del PNJ
     */
    public String description()
    {
        String desc = "";
        if(!agresivo)
        {
            desc = nombre + ", " + descripcion + " (amistoso)";
        }
        else
        {
            desc = nombre + ", " + descripcion + " (agresivo)";
        }
        return desc;
    }
}
