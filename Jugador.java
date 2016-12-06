package Jugador;
import java.util.* ;
public class Jugador {

    public Jugador(){       // Constructor del Jugador
        int fichas = 0, fichasGanadas = 0, fichasApostadas = 0, manosGanadas = 0, manosJugadas = 0;
        float fitness = 0;
        int []identificacion = new int[3];              // Posicion 0: nºgeneracion // Posicion 1: nº mesa // Posicion 3: nº jugador
        Carta []cartasEnMano ;                          // Las dos cartas en mano que en principio los tomamos como strings
        Carta []cartasComunes ;                         // Las 5 cartas comunes de la mesa para ver nuestra mejor mano
    }
    public Jugador(int fichas, int fichasGanadas, int fichasApostadas, int manosGanadas,
                   int manosJugadas, float fitness, int [] identificacion, String []cartasEnMano,
                   String []cartasComunes){

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
    }
    public float calcularFitness(int fichasGanadas, int fichasApostadas, int manosGanadas, int manosJugadas, float fitness){
        // Como se calcula al final de cada mano tengo que ver como da por finalizada la partida Florin
        if(manosGanadas != 0 || fichasGanadas != 0)
            fitness = fitness + calcularFitnessRecursivo();
        else
            fitness = 0;

        return fitness;
    }
    public float calcularFitnessRecursivo(){
        float fitnessProvisional;


        return fitnessProvisional;
    }
    public String[] enseñarCartas(String []cartasEnMano, String []cartasComunes){
        String [] mejorManoEnseñarCartas = new String[5];
        for(int i=0; i<5; i++)
            mejorManoEnseñarCartas[i] =  verMejorMano(cartasEnMano,cartasComunes).mejorMano[i];
        return mejorManoEnseñarCartas;
    }

    public ArrayList<Carta>[] verMejorMano(String [] cartasEnMano, String [] cartasComunes){
        // Suponiendo que Florin lo haya implementado asi
        ArrayList<Carta>[] mejorMano;
        ArrayList<Carta>[] manoProvisional;
        manoProvisional = // array creado para añadir las 7 cartas al borroso y que devuelva la mejor mano
        for(int i=0; i<5; i++)
            mejorMano[i] = new Carta();
        return mejorMano;
    }
    public void tomarDecision(){ // Tomare cada decision como un entero, 0 no ir, 1 pasar, 2 apostar
        // Una buena opcion es crear un arraylist y añadir las cartas actuales y mandarlo al borroso para
        // ver que podemos hacer con la mejor mano que tenemos
    }

}
