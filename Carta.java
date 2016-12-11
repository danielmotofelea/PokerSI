package pokerSI;

/**
 * Este es el objeto carta, tiene dos campos tipo string que se corresponden al valor y al palo
 */

public class Carta {
    private int valor;  // Valor en orden ascendente, siendo 2 la carta más baja y 14 la más alta (A)
    private int palo;   // 1: Corazones     2: Picas     3: Diamantes       4:Treboles

    // Constructor sin parametros para facilitar las inicializaciones en la clase Jugador
    public Carta(){
        this.palo = 0;
        this.valor = 0;
    }
    /**
     *Constructor de la carta, le pasamos el valor y el palo.
     */
    public Carta(int valor,int palo){
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

    public int getValor() {
        return valor;
    }

    public int getPalo() {
        return palo;
    }
    /**
     * Ire aniadiendo mas metodos segun se vayan necesitando asi como los basicos set and get.
     */
}
