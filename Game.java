/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!

 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Michael KÃ¶lling and David J. Barnes
 * @version 2011.07.31
 */

public class Game 
{
    private Parser parser;
    private Room currentRoom;

    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        createRooms();
        parser = new Parser();
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        Room sala, calabozo, armas, comedor, foso, habitacionFlechas, princesa;

        // create the rooms
        sala = new Room("has entrado al castillo, y te encuentras en la sala de espera");
        calabozo = new Room("estas en el calabozo y quedas encerrado");
        armas = new Room("te encuentras en la sala de armas");
        comedor = new Room("te encuentras en el comedor");
        foso = new Room("caes en un foso");
        habitacionFlechas = new Room("entras en la habitacion, " +  
            "y al pisar una baldosa algo suelta te disparan flechas");
        princesa = new Room("salvaste a la princesa");

        // initialise room exits
        sala.setExit("este",armas);
        sala.setExit("oeste",calabozo);
        calabozo.setExit("este",sala);

        armas.setExit("norte",comedor);

        comedor.setExit("norte",princesa);
        comedor.setExit("este",habitacionFlechas);
        comedor.setExit("oeste",foso);

        foso.setExit("este",comedor);

        habitacionFlechas.setExit("oeste",comedor);
        habitacionFlechas.setExit("noroeste",princesa);

        princesa.setExit("suroeste",habitacionFlechas);

        currentRoom = sala;  // start game outside
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();
        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.

        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Gracias por jugar, nos vemos pronto");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Bienvenido al mundo LostPrincess");
        System.out.println("LostPrincess es un juego donde deberas usar tu intuicion y " + 
            "tu perspicacia para ir superando las pruebas y rescatar a la princesa");
        System.out.println("Escribe 'mago' si tu necesitas ayuda o 'mirar' para saber donde estas");
        System.out.println();
        printLocationInfo();
        System.out.print("Que direccion quieres tomar: ");
        System.out.println();
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        if(command.isUnknown()) {
            System.out.println("Que es lo que quieres hacer, por ahi no hay nada");
            return false;
        }

        String commandWord = command.getCommandWord();
        if (commandWord.equals("mago")) {
            printHelp();
        }
        else if (commandWord.equals("al")) {
            goRoom(command);
        }
        else if (commandWord.equals("salir")) {
            wantToQuit = quit(command);
        }
        else if (commandWord.equals("mirar")) {
             System.out.println(currentRoom.getLongDescription());
        }else if(commandWord.equals("comer")) {
            System.out.println("Umm?¡ La comida estaba deliciosa y ya no tienes hambre");
        }

        return wantToQuit;
    }


    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("Soccoro ayuda. -Grito una voz de mujer");
        System.out.println("Puede ser la princesa. Recorre el castillo");
        System.out.println();
        System.out.println("Hay trampas en las habitaciones recuerda");
        System.out.println("se acabo la ayuda de momento");
        parser.getCommandWord().showAll();
        parser.printCommandWord();
    }

    /** 
     * Try to go in one direction. If there is an exit, enter
     * the new room, otherwise print an error message.
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("¿Que direccion quieres tomar?");
            return;
        }

        String direccion = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = null;

        nextRoom = currentRoom.getExit(direccion);

        if (nextRoom == null) {
            System.out.println("Por esa direccion no hay puerta");
        }
        else {
            currentRoom = nextRoom;

            printLocationInfo();

            System.out.println();
        }
    }

    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }

    private void printLocationInfo()
    {
        System.out.println(currentRoom.getLongDescription());
    }

    private void mirar()
    {
        printLocationInfo();
    }
}
