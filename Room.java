import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
/**
 * Class Room - a room in an adventure game.
 *
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 *
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits.  The exits are labelled north, 
 * east, south, west.  For each direction, the room stores a reference
 * to the neighboring room, or null if there is no exit in that direction.
 * 
 * @author  Michael KÃ¶lling and David J. Barnes
 * @version 2011.07.31
 */
public class Room 
{
    public String description;
    private HashMap<String,Room> direcciones;
    private ArrayList <Item> items;

    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * @param description The room's description.
     */
    public Room(String description) 
    {
        this.description = description;
        direcciones = new HashMap<>();
        items = new ArrayList<>();
    }

    /**
     * Define an exit from this room.
     * @param direction The direction of the exit.
     * @param neighbor The room in the given direction.
     */
    public void setExit(String direccion, Room habitacionVecina)
    {
        direcciones.put(direccion,habitacionVecina);
    }

    public Room getExit(String direccion)
    {
        Room returnRoom = null;

        returnRoom = direcciones.get(direccion);

        return returnRoom;
    }

    /**
     * @return The description of the room.
     */
    public String getDescription()
    {
        return description;
    }

    /**
     * Return a description of the room's exits.
     * For example: "Exits: north east west"
     *
     * @ return A description of the available exits.
     */
    public String getExitString()
    {
        String stringToReturn = "Hacia donde quieres ir: ";

        stringToReturn += direcciones.keySet();

        return stringToReturn;
    }

    /**
     * Return a long description of this room, of the form:
     *     You are in the 'name of room'
     *     Exits: north west southwest
     * @return A description of the room, including exits.
     */
    public String getLongDescription()
    {
        String longDescription = "Tu estas en el " + getDescription() + "\n"  + getExitString();
        for(int i = 0; i < items.size(); i++)
        {
            longDescription += items.get(i).getDescription();
        }
        return longDescription;
    }

    /**
     * Añadie nuevos items al ArrayList<Item>
     */
    public void addItem(String itemName, float itemWeight)
    {
        items.add(new Item(itemName,itemWeight));
    }

    /**
     * Metodo que devuelve true si el arraylist items esta lleno de objetos y false si no lo esta
     */
    public boolean emptyItems()
    {
        boolean itemsEmpty = false;
        if(items.size() == 0)
        {
            itemsEmpty = true;
        }
        return itemsEmpty;
    }

    /**
     * Metodo que devuelve un objeto que coincida con la descripicion del objeto,
     * dentro del arraylist items
     */
    public Item searchItem(String itemName)
    {
        Item itemMach = null;
        for(int i = 0; i < items.size(); i++)
        {
            if(items.get(i).getItemName().equals(itemName))
            {
                itemMach = items.get(i);
            }
        }
        return itemMach;
    }

    /**
     * Metodo que borra un objeto con el mismo nombre
     */
    public void removeItem(String itemName)
    {
        Iterator<Item> it = items.iterator();
        while(it.hasNext())
        {
            Item item = it.next();
            if(item.getItemName().equals(itemName))
            {
                it.remove();
            }
        }
    }
}
