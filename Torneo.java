
package pokerSI;
import java.util.*;


/**
 * Created by Javi on 09/12/2016.
 */
public class Torneo {
    private ArrayList<Jugador> finalistas;
    private ArrayList<Jugador> participantes;
    private int idGen;

    /**
     *
     * @param participantes ArrayList que contiene a todos los participantes de un torneo.
     * @param idGen identificador de la generación actual de los jugadores.
     *
     * El constructor solamente crea los arrayList necesarios y se guarda los valores que se le pasa por parámetro.
     */
    public Torneo(ArrayList<Jugador> participantes, int idGen) {
        finalistas =new ArrayList<Jugador>();
        this.participantes=new ArrayList<Jugador>();
        this.participantes.addAll(participantes);
        this.idGen=idGen;
    }

    /**
     * El método realizarTorneo se encarga de colocar en las mesas a los jugadores correspondientes, iniciar la partida y guardar a los ganadores.
     * Para ello se crea un ArrayList de jugadores que, por cada mesa, 8 jugadores que han sido generados en esta generación.
     * Luego crea un objeto mesa para que esos jugadores jueguen la partida.
     * El ganador de la mesa se guarda en el array de finalistas.
     * Esto se repite 8 veces en total
     * Finalmente se jugaría la mesa final para actualizar los fitness
     */
    public ArrayList<Jugador> realizarTorneo() {
        Mesa m;
        int njugador=0;

        for(int j=0;j<8/*Numero de mesas*/;j++){
            ArrayList<Jugador> jugadoresMesa = new ArrayList<Jugador>();
	        for (int i = 0; i < 8/*Numero de jugadores por mesa*/; i++) {
	            jugadoresMesa.add(participantes.get(njugador++));
			}

	        m = new Mesa(jugadoresMesa, (j + 1), idGen);//Constructor mesa: Mesa(Jugadores, idMesa, idGen); El id de la mesa va de 1 a 8
	        finalistas.add(m.jugar());//El método jugar devuelve el ganador de la mesa.
    	}
        
        for(int i=0; i<finalistas.size();i++){
            finalistas.get(i).calcularFitnessMesa();//Calculo el fitness de los ganadores de las mesas clasificatorias.
        }
        m=new Mesa(finalistas,idGen);//Constructor mesa: Mesa(Jugadores, idGen);
        for(int i=0; i<finalistas.size();i++){
            finalistas.get(i).gradoDeSoporte(i,idGen);//Escribo en fichero los grados de soporte de las reglas del borroso de los finalistas de la generacion
        }
        m.jugar();//Juega la mesa final

        return finalistas;

    }

}

