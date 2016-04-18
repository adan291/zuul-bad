import java.util.Stack;
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
   
   /**
    * Contructor del jugador
    */
   public Player()
   {
       visitedRooms = new Stack<>();
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
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = null;

        nextRoom = currentRoom.getExit(direction);
        
        if (nextRoom == null) {
            System.out.println("There is no door!");
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
}