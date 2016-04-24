import java.util.Stack;
import java.util.ArrayList;
import java.util.Iterator;
/**
 * Write a description of class Player here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Player
{
    private Room currentRoom;
    private Stack<Room> visitedRooms;
    private ArrayList<Item> items;
    private float weight;
    private final static float maxWeight = 7.5F;

    /**
     * Contructor del jugador
     */
    public Player()
    {
        visitedRooms = new Stack<>();
        items = new ArrayList<>();
        weight = 0.0F;
    }

    /**
     * Metodo que devuelve la habitacion 
     */
    public Room getCurrentRoom()
    {
        return currentRoom;
    }

    /**
     * Metodo para modificar la habitacion
     */
    public void setCurrentRoom(Room newCurrentRoom)
    {
        currentRoom = newCurrentRoom;
    }

    /** 
     * Prueba a entrar en una habitacion si no puede se muestra un mensaje 
     */
    public void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Hacia donde quieres ir?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = null;

        nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("Por ahi no hay ninguna puerta");
        }
        else {
            visitedRooms.push(currentRoom);
            currentRoom = nextRoom;

            printLocationInfo();

            System.out.println();
        }
    }

    /**
     * Muestra la informacion de donde esta
     */
    public void printLocationInfo()
    {
        System.out.println(currentRoom.getLongDescription());
    }

    /**
     * Mira lo que hay en la habitacion 
     */
    public void look()
    {
        System.out.println(currentRoom.getLongDescription());
    }

    /**
     * Vuelve a la habitacion anterior en la que estaba
     */
    public void back()
    {
        currentRoom = visitedRooms.pop();
        printLocationInfo();
    }

    /**
     * Come y muestra mensaje de que ha comido
     */
    public void eat()
    {
        System.out.println("Umm?¡ La comida estaba deliciosa y ya no tienes hambre");
    }

    /**
     * Metodo que añade un objeto al arraylist items
     */
    public boolean addItem(Item item)
    {
        boolean itemAdd = false;
        if(item.getItemWeight() > maxWeight)
        {
            System.out.println("Ese objeto que intentas coger pesa demasiado");
        }else{
            items.add(item);
            itemAdd = true;
        }
        return itemAdd;
    }

    /**
     * Metodo que borra un objeto del arraylist items
     */
    public void removeItem(String itemName)
    {
        Iterator<Item> it = items.iterator();
        while(it.hasNext())
        {
            Item item = it.next();
            if(item.getDescription().contains(itemName))
            {
                it.remove();
            }
        }
    }

    /**
     * Metodo que devuelve la informacion de los objetos item dentro del arraylist items
     */
    public String getItemsInfo()
    {
        String itemsInfo = null;
        if(items == null)
        {
            itemsInfo = "No tienes ningun item actualmente";
        }else{
            for(int i = 0; i < items.size(); i++)
            {
                itemsInfo = items.get(i).getDescription();
            }
        }
        return itemsInfo;
    }

    /**
     * Metodo que busca un objeto que pertenezca al arraylist items
     */
    public Item searchItem(String itemName)
    {
        Item itemFound = null;
        for(int i = 0; i < items.size(); i++)
        {
            if(items.get(i).getDescription().contains(itemName))
            {
                itemFound = items.get(i);
            }
        }
        return itemFound;
    }

    /**
     * Intenta coger el objeto de la habitación .
     * Si el peso maximo del obejto es mayor que el que puede coger el jugador o
     * si no se puede añadir el artículo muestra un mensaje de error 
     */
    public void take(Command command)
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("¿Que objeto quieres coger?");
            return;
        }

        String itemString = command.getSecondWord();

        if(currentRoom.emptyItems() == true)
        {
            System.out.println("No hay objetos en esa habitacion");
        }else{
            Item item = currentRoom.searchItem(itemString);
            if(item == null)
            {
                System.out.println("No hay objetos con ese nombre");
            }else{
                boolean added = addItem(item);
                if(added == true)
                {
                    currentRoom.removeItem(itemString);
                }else{
                    System.out.println("No puedes coger ese objeto");
                }
            }
        }
    }

    public void drop(Command command)
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("¿Que objeto quieres soltar?");
            return;
        }

        String itemString = command.getSecondWord();

        if(items == null)
        {
            System.out.println("No tienes ese objeto");
        }else{
            Item item = searchItem(itemString);
            String itemName = item.getItemName();
            float itemWeight = item.getItemWeight();
            if(item == null)
            {
                System.out.println("No hay objetos con esa descripción");
            }else{
                boolean added = currentRoom.addItem(itemName,itemWeight);
                if(added == true)
                {
                    removeItem(itemString);
                }else{
                    System.out.println("No puedes soltar el objeto es esta habitacion");
                }
            }
        }
    }

    public boolean emptyVisitedRooms()
    {      return visitedRooms.empty();
    }

    public void removeVisitedRoom()
    {
        currentRoom = visitedRooms.pop();
    }
}