
import java.util.*;
package PokerSI;

/**
 * Created by Javi on 09/12/2016.
 */
public class Torneo {
    protected Jugador[] finalistas;
    private ArrayList<Jugador> participantes;

    public Torneo(ArrayList<Jugador> participantes) {
        finalistas= new Jugador[8];
        this.participantes.addAll(participantes);
    }


    public void colocarJugadores(){
        for(int i=7;i>=0;i--) {
            /**
             * Por cada  mesa habrá que:
             * 1. Como las primeras 8 posiciones de "participantes" son los participantes de la mesa final de la generación anterior, cada uno irá a una mesa diferente(posicion 0 de "participantes")
             * 2. A partir de la posicion i, se incluirán 7 jugadores más a cada mesa
             * 3. Al incluir 7 jugadores más en la mesa, se iniciará la partida hasta que termine y la mesa devuelva al ganador. Ese ganador se incluirá en "finalistas"
             * 4. Se avanza a la siguiente mesa y se repite la operación
             */
        }

            /**
             * 5. Al terminar las 8 mesas, se colocarán a los 8 finalistas en una nueva mesa y se realizará una última partida.
             * (Se recalculan los fitness aunque todos ellos van a salvarse para la próxima generación)
             *
             *
             *
             *
            * */
    }
}
