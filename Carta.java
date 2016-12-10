package pokerSI;

/**
 * Este es el objeto carta, tiene dos campos tipo string que se corresponden al valor y al palo
 */

public class Carta {
    private String valor;
    private String palo;

    // Constructor sin parametros para facilitar las inicializaciones en la clase Jugador
    public Carta(){
        this.palo = "";
        this.valor = "";
    }
    /**
     *Constructor de la carta, le pasamos el valor y el palo.
     */
    public Carta(String valor,String palo){
        this.palo=palo;
        this.valor=valor;
    }

    /**
     *Metodo toString para comprobar el correcto funcionamiento
     */
    public String toString() {
        return "Carta{" +
                "Valor='" + valor + '\'' +
                ", Palo='" + palo + '\'' +
                '}';
    }

    public String getValor() {
        return valor;
    }

    public String getPalo() {
        return palo;
    }
    /**
     * Ire aniadiendo mas metodos segun se vayan necesitando asi como los basicos set and get.
     */
}
