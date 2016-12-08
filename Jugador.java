import java.util.* ;
package PokerSI;

public class Jugador {

    protected int fichas;
    protected int fichasGanadas;
    protected int fichasApostadas;
    protected int manosGanadas;
    protected int manosJugadas;
    //private float fitness;            En principio sobra, ya se verá si al final hace falta
    private int []identificacion;
    private Carta []cartasEnMano;
    private Carta []cartasComunes;
    private ArrayList<Carta> mejorMano;

    public Jugador(){       // Constructor del Jugador
        this.fichas = 0;
        this.fichasGanadas = 0;
        this.fichasApostadas = 0;
        this.manosGanadas = 0;
        this.manosJugadas = 0;
        //this.fitness = 0;
        this.identificacion = new int[3];              // Posicion 0: nºgeneracion // Posicion 1: nº mesa // Posicion 3: nº jugador
        this.cartasEnMano = new Carta[2];             // Las dos cartas en mano que en principio los tomamos como strings
        this.cartasComunes = new Carta[5];            // Las 5 cartas comunes de la mesa para ver nuestra mejor mano
        this.mejorMano = new ArrayList<Carta>();                    // La mejor combinacion de cartas sobre la mesa
    }

    public Jugador(int fichas, int fichasGanadas, int fichasApostadas, int manosGanadas,
                   int manosJugadas, int [] identificacion, Carta []cartasEnMano,
                   Carta []cartasComunes, ArrayList<Carta> mejorMano)
    {
        this.fichas = fichas;
        this.fichasGanadas = fichasGanadas;
        this.fichasApostadas = fichasApostadas;
        this.manosGanadas = manosGanadas;
        this.manosJugadas = manosJugadas;
        //this.fitness = fitness;

        for(int i=0; i<3; i++)
            this.identificacion[i] = 0;
        for(int j=0; j<2; j++)
            this.cartasEnMano[j] = new Carta();
        for(int k=0; k<5; k++)
            this.cartasComunes[k] = new Carta();

        this.mejorMano = new ArrayList<Carta>();
    }

    /*public void setFitness(float valor){
        fitness = valor;
    }*/

    /*public float getFitness(){
        return fitness;
    }
    En principio sobra, ya veremos si al final hace falta o no
    */

    public float calcularFitness(){
        float fitness; // En caso de que al final sea atributo de la clase, esta variable sobrará
        // Como se calcula al final de cada mano tengo que ver como da por finalizada la partida Florin
        if((manosJugadas != 0) && (fichasApostadas != 0))
            fitness = ((manosGanadas/manosJugadas)*(fichasGanadas/fichasApostadas))/100;
        else
            fitness = 0;
        return fitness;
    }

    public ArrayList<Carta> enseñarCartas(){
        return mejorMano;
    }

    public ArrayList<Carta>[] verMejorMano(){
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
