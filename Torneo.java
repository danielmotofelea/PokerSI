
import java.util.*;
package PokerSI;

/**
 * Created by Javi on 09/12/2016.
 */
public class Torneo {
    protected ArrayList<Jugador> finalistas;
    private ArrayList<Jugador> participantes;
    private int idGen;

    /**
     *
     * @param participantes ArrayList que contiene a todos los participantes de un torneo.
     * @param idGen identificador de la generación actual de los jugadores.
     *
     * El constructor solamente instancia un array de 8 finalistas y se guarda los valores que se le pasa por parámetro.
     */
    public Torneo(ArrayList<Jugador> participantes, int idGen) {
        finalistas =new ArrayList<Jugador>();
        participantes=new ArrayList<Jugador>();
        this.participantes.addAll(participantes);
        this.idGen=idGen;
    }

    /**
     * El método realizarTorneo se encarga de colocar en las mesas a los jugadores correspondientes, iniciar la partida y guardar a los ganadores.
     * Para ello se crea un ArrayList de jugadores que, por cada mesa, añade a un finalista de la generación anterior y a 7 jugadores que han sido generados en esta generación.
     * Luego crea un objeto mesa para que esos jugadores jueguen la partida.
     * El ganador de la mesa se guarda en el array de finalistas.
     * Esto se repite 8 veces en total
     * Finalmente se jugaría la mesa final para actualizar los fitness
     */
    public void realizarTorneo() {
        Mesa m;
        ArrayList<Jugador> jugadoresMesa = new ArrayList<Jugador>();
        for (int i = 0; i < 8; i++) {
            int posOtrosJug=8;
            jugadoresMesa.add(participantes.get(i));//Los 8 primeros jugadores del ArrayList son los finalistas de la generación anterior.
            for(int j=0;j<7;j++) {
                jugadoresMesa.add(participantes.get(posOtrosJug));
                posOtrosJug++;
            }
            m= new Mesa(jugadoresMesa, i, idGen);//Constructor mesa: Mesa(Jugadores, idMesa, idGen);
            finalistas.add(m.jugar());//El método jugar devuelve el ganador de la mesa.
        }
        m=new Mesa(finalistas,idGen);//Constructor mesa: Mesa(Jugadores, idGen);
        m.jugarMesaFinal();
    }

}
