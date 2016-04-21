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
    
    /**
     * Contructor de la clase Item
     */
    public Item(String itemName, float itemWeight)
    {
        this.itemName = itemName;
        this.itemWeight = itemWeight;
    }
    
    /**
     * Devuelve los atributos del objeto
     */
    public String getDescription()
    {
        String description = "\n" + "Objeto: " + itemName + "   Tamaño del objeto: " + itemWeight + " Kg.";
        return description;
    }
    
    /**
     * Devuelve el nombre del objeto 
     */
    public String getItemName()
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
}