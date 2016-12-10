package Version1;

/**
 * Este es el objeto carta, tiene dos campos tipo string que se corresponden al valor y al palo
 */

public class Cartas {
    private String Valor;
    private String Palo;

    // Constructor sin parametros para facilitar las inicializaciones en la clase Jugador
    public Cartas(){
        this.Palo = "";
        this.Valor = "";
    }
    /**
     *Constructor de la carta, le pasamos el valor y el palo.
     */
    public Cartas(String Valor,String Palo){
        this.Palo=Palo;
        this.Valor=Valor;
    }

    /**
     *Metodo toString para comprobar el correcto funcionamiento
     */
    public String toString() {
        return "Cartas{" +
                "Valor='" + Valor + '\'' +
                ", Palo='" + Palo + '\'' +
                '}';
    }

    /**
     * Ire aniadiendo mas metodos segun se vayan necesitando asi como los basicos set and get.
     */
}
