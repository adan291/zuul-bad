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
    private boolean enCombate;
    private int resistencia;
    private int ataque;
    private Item equipo;
    private final static float MAX_WEIGHT = 7.5F;
    private final int MAX_RESISTANCE;
    

    /**
     * Contructor del jugador
     */
    public Player(int resistencia, int ataque)
    {
        visitedRooms = new Stack<>();
        items = new ArrayList<>();
        this.resistencia = resistencia;
        this.ataque = ataque;
        weight = 0.0F;
        this.enCombate = false;
        this.equipo = null;
        this.MAX_RESISTANCE = resistencia;
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
        if(!enCombate){
            System.out.println(currentRoom.getLongDescription());
        }
        else{
            System.out.println("No puedes hacer eso en combate");
        }
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
        if(!enCombate){
            System.out.println("Umm?¡ La comida estaba deliciosa y ya no tienes hambre");
        }
        else{
            System.out.println("No puedes hacer eso en combate");
        }
    }

    /**
     * Metodo que añade un objeto al arraylist items
     */
    public boolean addItem(Item item)
    {
        boolean itemAdd = false;
        if(item.getItemWeight() > MAX_WEIGHT)
        {
            System.out.println("Ese objeto que intentas coger pesa demasiado o ya llevas demasiado");
        }else{
            items.add(item);
            itemAdd = true;
            weight += item.getItemWeight();
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
     * Si no se puede añadir el artículo muestra un mensaje de error 
     * 
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
            System.out.println("Que quieres hacer: ");
        }else{
            Item item = currentRoom.searchItem(itemString);
            if(item == null)
            {
                System.out.println("No hay objetos con ese nombre");
                System.out.println("Que quieres hacer: ");
            }else{
                boolean added = addItem(item);

                if(added == true)
                {
                    currentRoom.removeItem(itemString);
                    System.out.println("Lo/La has cogido");
                    System.out.println("Que quieres hacer: ");
                }else{
                    System.out.println("No puedes coger ese objeto");
                    System.out.println("Que quieres hacer: ");
                }
            }
        }
    }

    /**
     * Intenta soltar un objeto que lleve
     * Si no tiene el objeto no puede soltarlo
     * Si no hay objetos con ese nombre no puede soltarlo
     * Si en esa habitacion no puede soltar ese objeto no lo suelta
     */
   public boolean dropItem(String objeto)
    {
        boolean soltar = false;
        if(!enCombate)
        {
            // busca el objeto en el inventario
            Item tempObj = search(objeto);
            if(tempObj != null)
            {
                items.remove(tempObj);
                currentRoom.addItem(tempObj);
                System.out.println("Sueltas " + tempObj.getLongDescription());
                MAX_WEIGHT-= tempObj.getPeso();
                soltar = true;
                if(tempObj == equipo)
                {
                    equipo = null;
                }
            }
            else
            {
                System.out.println("No tienes ese objeto en tu inventario");
            }
        }
        else
        {
            System.out.println("No puedes hacer eso en combate");
        }
        return soltar;
    }
    
    /**
     * Devuelve true si esta vacio y false si esta lleno
     */
    public boolean emptyVisitedRooms()
    {      return visitedRooms.empty();
    }

    /**
     * Coge el objeto ultimo del stack
     */
    public void removeVisitedRoom()
    {
        currentRoom = visitedRooms.pop();
    }

    public void modificaRes(int res)
    {
        resistencia += res; 
    }

    public void hablar()
    {
        if(currentRoom.getPNJ() != null)
        {
            Item obj = currentRoom.getPNJ().hablar();
            if (obj != null)
            {
                boolean exito = addItem(obj);
                if(exito)
                {
                    currentRoom.getPNJ().remove(obj);
                }
                else
                {
                    System.out.println("No puedes recibir el objeto que te intenta dar");
                }
            }
        }
    }

    public void entraEnCombate()
    {
        enCombate = true;
    }

    /**
     * Saca al jugador de combate
     */
    public void saleDeCombate()
    {
        enCombate = false;
    }

    public void atacar()
    {
        System.out.println("\nGolpeas a " + getPNJ().getNombre() + " y le haces " + getAtaque() + " puntos de daño");
        getPNJ().restaRes(getAtaque());
    }

    public NPC getPNJ()
    {
        return currentRoom.getPNJ();
    }

    public int getAtaque()
    {
        int ataqueTotal = ataque;
        if(equipo != null)
        {
            ataqueTotal += equipo.getAtaque();
        }
        return ataqueTotal;
    }

    public void sumaResistencia(int res)
    {
        resistencia += res; 
        // Comprueba que no supere el maximo
        if(resistencia > maxResistencia)
        {
            resistencia = maxResistencia;
        }
    }

    public int getResistencia()
    {
        return resistencia;
    }

    private Item search(String nombre)
    {
        boolean find = false;
        int index = 0;
        Item objeto = null;
        // Busca el objeto en el inventario
        while((index < items.size()) & (!find))
        {
            if(nombre.equals(items.get(index).getItemName()))
            {
                objeto = items.get(index);
                find = true;
            }
            index++;
        }
        return objeto;
    }

    public void equipar(String nombre)
    {
        Item objeto = search(nombre);
        if(objeto != null)
        {
            equipo = objeto;
            System.out.println("Equipas " + objeto.getItemName() + " y te proporciona " + objeto.getAtaque() + " ataque");
        }
        else
        {
            System.out.println("No tienes ese objeto en tu inventario para equiparlo");
        }
    }

    public boolean saquear()
    {
        boolean saqueado = false;
        // Toma el inventario del PNJ
        ArrayList<Item> loot = getPNJ().saquear();
        // Intenta añadir cada objeto al inventario del PNJ
        if(loot.size() > 0)
        {
            Iterator<Item> it = loot.iterator();
            while(it.hasNext())
            {
                Item item = it.next();
                saqueado = addItem(item);
                if(saqueado)
                {
                    it.remove();
                }
            }
        }
        else
        {
            System.out.println("Aqui no hay nada que saquear");
        }
        return saqueado;
    }
   

    /**
     * Devuelve si el jugador esta o no en combate
     * @return True si esta en combate, false sino
     */
    public boolean enCombate()
    {
        return enCombate;
    }
    
    public boolean usar(String nombre)
    {
        boolean usar = false;
        // Busca el objeto en el inventario
        Item obj = search(nombre);
        if(obj != null)
        {
            if(obj.getCuraRes() > 0)
            {
                sumaResistencia(obj.getCuraRes());
                System.out.println("¡Usas "+ obj.getItemName() + " y recuperas resistencia!");
                usar = true;
                items.remove(obj);
            }
            else
            {
                System.out.println("Ese objeto no puede usarse");
            }
        }
        else
        {
            System.out.println("No tienes ese objeto en el inventario");
        }
        return usar;
    }
    
    public void estado()
    {
        String estado = "Resistencia: " + resistencia + "/" + maxResistencia;
        estado += "\nAtaque base: " + ataque + ", ataque total: " + getAtaque();
        if(equipo != null)
        {
            estado += "\nEquipado: " + equipo.getItemName() + ", " + equipo.getAtaque() + " añadido al ataque";
        }
        else
        {
            estado += "\nNo tienes nada equipado";
        }
        estado += getItemsInfo();
        System.out.println(estado);
    }

    
}