
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
 * @author  Michael Kölling and David J. Barnes
 * @version 2011.07.31
 */

public class Game 
{
    private Parser parser;
    private Player player;
    /**
     * Crea el juego y lo inicializa
     */
    public Game() 
    {
        createRooms();
        parser = new Parser();
        player = new Player ();
    }

    /**
     * Crea las habitaciones y lo que contienen
     */
    private void createRooms()
    {
        Room sala, calabozo, armas, comedor, foso, habitacionFlechas, princesa;

        // create the rooms
        sala = new Room("has entrado al castillo, y te encuentras en la sala de espera");
        sala.addItem("Llave de las puertas del castillo", 0.02F);
        calabozo = new Room("estas en el calabozo y quedas encerrado");
        calabozo.addItem("Llave del calabozo", 0.03F);
        armas = new Room("te encuentras en la sala de armas");
        armas.addItem("Espada", 2F);
        comedor = new Room("te encuentras en el comedor");
        comedor.addItem("Cadaver de Rey envenenado", 120F);
        foso = new Room("caes en un foso");
        foso.addItem("Escalera", 4F);
        habitacionFlechas = new Room("entras en la habitacion, " +  
            "y al pisar una baldosa algo suelta te disparan flechas");
        habitacionFlechas.addItem("Arco y flechas", 0.75F);
        princesa = new Room("salvaste a la princesa");
        princesa.addItem("Cuerda", 0.5F);

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

        player.setCurrentRoom(sala);  // start game outside
    }

    /**
     *  Inicia el juego y hace que el juego no finalice o sea finalizado
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
     * Mensaje de informacion para el jugador y para empezar el juego
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Bienvenido al mundo LostPrincess");
        System.out.println("LostPrincess es un juego donde deberas usar tu intuicion y " + 
            "tu perspicacia para ir superando las pruebas y rescatar a la princesa");
        System.out.println("Escribe 'mago' si tu necesitas ayuda o 'mirar' para saber donde estas");
        System.out.println();
        player.printLocationInfo();
        System.out.print("Que direccion quieres tomar: ");
        System.out.println();
    }

    /**
     * Se pasan los comandos que el jugador nos da por parametro y dependiendo de lo que el jugador
     * haya puesto realizara la funcion que le corresponda
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
            player.goRoom(command);;
        }
        else if (commandWord.equals("salir")) {
            wantToQuit = quit(command);
        }
        else if (commandWord.equals("mirar")) {
            player.look();
        }
        else if(commandWord.equals("comer")) {
            player.eat();
        }
        else if(commandWord.equals("volver")) {
            player.back();
        }
        else if(commandWord.equals("coger")) {
            player.take(command);
        }
        else if(commandWord.equals("soltar")) {
            player.take(command);
        }

        return wantToQuit;
    }

    /**
     * Mensaje de ayuda para el juegador para dar pistas de lo que tiene que conseguir en el juego.
     */
    private void printHelp() 
    {
        System.out.println("Soccoro ayuda. -Grito una voz de mujer");
        System.out.println("Puede ser la princesa. Recorre el castillo");
        System.out.println();
        System.out.println("Hay trampas en las habitaciones recuerda");
        System.out.println("se acabo la ayuda de momento");
        parser.printCommandWord();
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

    /**
     * Muestra al jugador lo que hay en la habitacion
     */
    private void mirar()
    {
        player.printLocationInfo();
    }
}
