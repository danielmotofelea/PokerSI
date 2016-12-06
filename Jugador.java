package Jugador;
import java.util.* ;
public class Jugador {

    public Jugador(){       // Constructor del Jugador
        int fichas = 0, fichasGanadas = 0, fichasApostadas = 0, manosGanadas = 0, manosJugadas = 0;
        float fitness = 0;
        int []identificacion = new int[3];              // Posicion 0: nºgeneracion // Posicion 1: nº mesa // Posicion 3: nº jugador
        Carta []cartasEnMano ;                          // Las dos cartas en mano que en principio los tomamos como strings
        Carta []cartasComunes ;                         // Las 5 cartas comunes de la mesa para ver nuestra mejor mano
        ArrayList<Carta> mejorMano;                     // La mejor combinacion de cartas sobre la mesa
    }
    public Jugador(int fichas, int fichasGanadas, int fichasApostadas, int manosGanadas,
                   int manosJugadas, float fitness, int [] identificacion, String []cartasEnMano,
                   String []cartasComunes, ArrayList<Carta> mejorMano){

        fichas = fichas;
        fichasGanadas = fichasGanadas;
        fichasApostadas = fichasApostadas;
        manosGanadas = manosGanadas;
        manosJugadas = manosJugadas;
        fitness = fitness;
        for(int i=0; i<3; i++)
            identificacion[i] = 0;
        for(int j=0; j<2; j++)
            cartasEnMano[j] = new Carta();
        for(int k=0; k<5; k++)
            cartasComunes[k] = new Carta();
        mejorMano = new ArrayList<Carta>();
    }
    public float calcularFitness(int fichasGanadas, int fichasApostadas, int manosGanadas, int manosJugadas, float fitness){
        // Como se calcula al final de cada mano tengo que ver como da por finalizada la partida Florin
        if((manosJugadas != 0) && (fichasApostadas != 0))
            fitness = ((manosGanadas/manosJugadas)*(fichasGanadas/fichasApostadas))/100;
        else
            fitness = 0;
        return fitness;
    }

    public ArrayList<Carta> enseñarCartas(ArrayList<Carta> mejorMano){
        return mejorMano;
    }

    public ArrayList<Carta>[] verMejorMano(Carta [] cartasEnMano, Carta [] cartasComunes){
        // Suponiendo que Florin lo haya implementado asi

        ArrayList<Carta> manoProvisional = new ArrayList<Carta>();    // array creado para añadir las 7 cartas al borroso y que devuelva la mejor mano
        for(int i=0; i<2; i++)
            manoProvisional.add(cartasEnMano[i]);        // se añaden las cartas que se tenga actualmente en un arraylist unificado
        for(int j=0; j<5; j++)                          // para pasarselo a otro metodo y que evalue la mejor mano
            manoProvisional.add(cartasComunes[j]);
        // Ahora habria que hacer una llamada a la logica borrosa o a las reglas para devolver la mejor mano.
        //mejorMano = calcularMejorMano(manoProvisional);
        return mejorMano;
    }
    public void tomarDecision(){ // Tomare cada decision como un entero, 0 no ir, 1 pasar, 2 apostar
        // Una buena opcion es crear un arraylist y añadir las cartas actuales y mandarlo al borroso para
        // ver que podemos hacer con la mejor mano que tenemos
    }

}
