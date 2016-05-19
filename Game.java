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
    private Player player;
    /**
     * Crea el juego y lo inicializa
     */
    public Game() 
    {
        parser = new Parser();
        player = new Player ();
        createRooms();
    }

    /**
     * Crea las habitaciones y lo que contienen
     */
    private void createRooms()
    {
        Room sala, calabozo, armas, comedor, foso, habitacionFlechas, torreon;
        NPC esqueleto, princesa, rey;
        
        

        // create the rooms
        sala = new Room("has entrado al castillo, y te encuentras en la sala de espera");
        sala.addItem("pocion", 0.02F);
        calabozo = new Room("estas en el calabozo y quedas encerrado");
        calabozo.addItem("llave", 0.05F);
        armas = new Room("te encuentras en la sala de armas");
        armas.addItem("espada", 2F);
        comedor = new Room("te encuentras en el comedor");
        comedor.addItem("cadaver", 120F);
        foso = new Room("caes en un foso");
        foso.addItem("cuerda", 0.7F);
        habitacionFlechas = new Room("entras en la habitacion, " +  
            "y al pisar una baldosa algo suelta te disparan flechas");
        habitacionFlechas.addItem("arco", 0.65F);
        torreon = new Room("salvaste a la princesa");
        torreon.addItem("flechas", 0.5F);
        
        
        //Creamos los personajes
        esqueleto = new NPC(true, "guerrero", "!Intruso!, VAS A MORIR", "Un no-muerto con armadura y espada", 15, 75);
        princesa = new NPC(false, "princesa", "!OOOO¡ Mi heroes, ¿vienes a salvarme?", "Una princesa encerrada en el castillo", 0, 1000);
        rey = new NPC(true, "rey del castillo", "¡¿Que haces en mi morada!?, ¡a las armas!", "Un no-muerto con vestimenta de reyes y arma con escudo", 30, 120);
        
        //Añade los personajes en las localizaciones
        calabozo.addPNJ(esqueleto);
        torreon.addPNJ(princesa);
        
        // initialise room exits
        sala.setExit("este",armas);
        sala.setExit("oeste",calabozo);
        calabozo.setExit("este",sala);

        armas.setExit("norte",comedor);

        comedor.setExit("norte",torreon);
        comedor.setExit("este",habitacionFlechas);
        comedor.setExit("oeste",foso);

        foso.setExit("este",comedor);

        habitacionFlechas.setExit("oeste",comedor);
        habitacionFlechas.setExit("noroeste",torreon);

        torreon.setExit("suroeste",habitacionFlechas);

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
        System.out.println("Si tu necesitas ayuda escribe: " + Option.HELP.getComando() );
        System.out.println();

        player.printLocationInfo();
        System.out.println("Que quieres hacer: ");

    }

    /**
     * Se pasan los comandos que el jugador nos da por parametro y dependiendo de lo que el jugador
     * haya puesto realizara la funcion que le corresponda
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;
        Option commandWord = command.getCommandWord();
        if(command.isUnknown()) {
            System.out.println("No entiendo que es lo que quieres hacer");
            System.out.println("¿Que quieres hacer?");
            return false;
        }
        switch(command.getCommandWord()){
            case HELP:
            printHelp();
            break;

            case GO:
            player.goRoom(command);
            break;
            case QUIT:
            wantToQuit = quit(command);
            break;

            case LOOK:
            player.look();
            break;
            case EAT:
            player.eat();
            break;
            case BACK:
            if(player.emptyVisitedRooms() == true){
                player.removeVisitedRoom();
            }
            else{
                player.back();
            }
            break;

            case TAKE:
            player.take(command);
            break;
            case DROP:
            player.drop(command);

            break;
            case ITEMS:
            System.out.println(player.getItemsInfo());
            break;
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
        System.out.println("Estos son los comandos:");
        parser.printCommandWord();
        System.out.println("\nNo hay mas ayuda");
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

