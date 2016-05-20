import java.util.ArrayList;
import java.util.Random;

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
    private String conversacion;
    private String descripcion;
    private int ataque;
    private int resistencia;
    private ArrayList<Item> inventario;

    
    public NPC(boolean agresivo, String nombre, String conversacion, String descripcion, int ataque, int resistencia)
    {
        this.agresivo = agresivo;
        this.nombre = nombre;
        this.conversacion = conversacion;
        this.descripcion = descripcion;
        this.ataque = ataque;
        this.resistencia = resistencia;
        inventario = new ArrayList<Item>();
    }

     public Item hablar()
    {
        
        Item obj = null;
        if(!agresivo)
        {
            System.out.println(conversacion);

            if(inventario.size() > 0)
            {
                Random rand = new Random();
                String nombre = inventario.get(rand.nextInt(inventario.size())).getItemName();
                obj = search(nombre);
            }
        }
        else
        {
            System.out.println("No puedes hablar con personajes agresivos");
        }
        return obj;
    }
    
    /**
     * Resta resistencia al PNJ
     * @param res La resistencia a restar
     */
    public void restaRes(int res)
    {
        resistencia -= res;
    }
    
    public ArrayList<Item> saquear()
    {
        if(resistencia > 0)
        {
            return null;
        }
        else
        {
            return inventario;
        }
    }

   
    public Item search(String nombre)
    {
        boolean encontrado = false;
        Item objeto = null;
        int index = 0;
      
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


    public void remove(Item obj)
    {
        inventario.remove(obj);
    }

    public int getResistencia()
    {
        return resistencia;
    }

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
    
   
    public boolean isAgresivo()
    {
        return agresivo;
    }
    
     public String description()
    {
        String desc = "";
        if(!agresivo)
        {
            if (resistencia > 0)
            {
                desc = nombre + ", " + descripcion + " [amistoso]";
            }
            else
            {
                desc = nombre + ", " + descripcion;
            }
        }
        else
        {
            desc = nombre + ", " + descripcion + " [agresivo]";
        }
        return desc;
    }
    
      public void estaMuerto()
    {
        agresivo = false;
        descripcion = nombre + " muerto";
    }
}
