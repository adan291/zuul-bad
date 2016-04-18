/**
 * Write a description of class Item here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Item
{
    private String itemDescription;
    private float itemWeight;
    
    /**
     * Constructor of class Item.
     */
    public Item(String itemDescription, float itemWeight)
    {
        this.itemDescription = itemDescription;
        this.itemWeight = itemWeight;
    }
    
    /**
     * Return the values of atributes.
     */
    public String getDescription()
    {
        String description = "\n" + "Objeto: " + itemDescription + "   Tamaño del objeto: " + itemWeight + " Kg.";
        return description;
    }
}