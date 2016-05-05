import java.util.HashMap;   
/**
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 * 
 * This class holds an enumeration of all command words known to the game.
 * It is used to recognise commands as they are typed in.
 *
 * @author  Michael Kölling and David J. Barnes
 * @version 2011.07.31
 */

public class CommandWords
{
    // a constant array that holds all valid command words
    private static HashMap<String,Option> validCommands;
    /**
     * Constructor - initialise the command words.
     */
    public CommandWords()
    {
        // nothing to do at the mvalidCommands = new HashMap<>();
        validCommands = new HashMap<String,Option>();
         Option[] comands = Option.values();
        for (Option option : comands) {
             validCommands.put(option.getComando(), option);
        }
    }

    /**
     * Check whether a given String is a valid command word. 
     * @return true if a given string is a valid command,
     * false if it isn't.
     */
    public boolean isCommand(String aString)
    {
        boolean booleanToReturn = validCommands.containsKey(aString);
        return booleanToReturn;
    }

    /**
     * Muestra todos los comandos validos
     */
    public void showAll()
    {
        for (String key : validCommands.keySet()){
            System.out.println(key);
        }
    }

    /**
     * Return the object Option associated with a word.
     * @param commandWord The word to look up (as a string).
     * @return the object Option correspondng to the paramater commandWord, or the object Option.UNKNOWN
     *         if it is not a valid command word
     */
    public Option getCommandWord(String commandWord)
    {
        Option option = Option.UNKNOWN;  
        if(isCommand(commandWord)){          
            option = validCommands.get(commandWord);
        }
        return option;
    }
}
