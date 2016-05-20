/**
 * Write a description of class Item here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Item
{
    private String itemName;
    private float itemWeight;
    private int ataque;
    private int curas;

    /**
     * Contructor de la clase Item
     */
    public Item(String itemName, float itemWeight, int ataque, int curas)
    {
        this.itemName = itemName;
        this.itemWeight = itemWeight;
        this.ataque = ataque;
        this.curas = curas;
    }

    /**
     * Devuelve los atributos del objeto
     */
    public String getDescriptions()
    {
        String description = "\n" + "Objeto: " + itemName + "   Tamaño del objeto: " + itemWeight + " Kg.";
        return description;
    }

    /**
     * Devuelve el nombre del objeto 
     */
    public String getDescription()
    {
        return itemName;
    }

    /**
     * Devuelve el peso del objeto
     */
    public float getItemWeight()
    {
        return itemWeight;
    }

    public int getCuraRes()
    {
        return curas;
    }

    public int getAtaque()
    {
        return ataque;
    }

    public String getLongDescription()
    {		      {
            String info = getDescription() + "[" + itemName + "]" + "(" + itemWeight + "kg) Ataque: " + ataque;
            if(curas > 0)
            {
                info += ". Usable";
            }
            else
            {
                info += ". No usable";
            }
            return info;		         
        }
    }
}
