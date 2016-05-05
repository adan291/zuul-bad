
/**
 * Write a description of class Option here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public enum Option
{
    GO("ir"), QUIT("salir"), HELP("mago"),LOOK("mirar"),EAT("comer"),BACK("volver"),
     TAKE("coger"),DROP("soltar"),ITEMS("objetos"),UNKNOWN("");
     
     private String comando;
     
     /**
      * Constructor de la clase Option
      */
     private Option (String comando)
     {
         this.comando = comando;
     }
     
     /**
      * Metodo que devuelve un String que es el comando
      */
     public String getComando()
     {
         return comando;
     }
}

