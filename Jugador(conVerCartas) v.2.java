package pokerSI;
import java.util.* ;
public class Jugador {

    protected int fichas, fichasGanadas, fichasApostadas, manosGanadas, manosJugadas;
   // protected float fitness;
    private int []identificacion;
    private Carta []cartasEnMano;
    private Carta []cartasComunes;
    private ArrayList<Carta> mejorMano;
    public Jugador(){       /** Constructor del Jugador*/
        this.fichas = 0;
        this.fichasGanadas = 0;
        this.fichasApostadas = 0;
        this.manosGanadas = 0;
        this.manosJugadas = 0;
        // this.fitness = 0; DE MOMENTO COMENTADO HASTA VER COMO UTILIZARLO
        this.identificacion = new int[3];              /** Posicion 0: nºgeneracion // Posicion 1: nº mesa // Posicion 3: nº jugador*/
        this.cartasEnMano = new Carta[2] ;             /** Las dos cartas en mano que en principio los tomamos como strings*/
        this.cartasComunes = new Carta[5];             /** Las 5 cartas comunes de la mesa para ver nuestra mejor mano*/
        this.mejorMano = new ArrayList<Carta>();       /** La mejor combinacion de cartas sobre la mesa*/
    }
    public Jugador(int fichas, int fichasGanadas, int fichasApostadas, int manosGanadas,
                   int manosJugadas,/* float fitness,*/ int [] identificacion, String []cartasEnMano,
                   String []cartasComunes, ArrayList<Carta> mejorMano){

        this.fichas = fichas;
        this.fichasGanadas = fichasGanadas;
        this.fichasApostadas = fichasApostadas;
        this.manosGanadas = manosGanadas;
        this.manosJugadas = manosJugadas;
        // this.fitness = fitness;

        for(int i=0; i<3; i++)
            this.identificacion[i] = 0;
        for(int j=0; j<2; j++)
            this.cartasEnMano[j] = new Carta();
        for(int k=0; k<5; k++)
            this.cartasComunes[k] = new Carta();
        this.mejorMano = new ArrayList<Carta>();
    }
    /* public void setFitness(float valor){
        fitness = valor;
    }
    public float getFitness(){
        return fitness;
    }
    public float calcularFitness(){
        // Como se calcula al final de cada mano tengo que ver como da por finalizada la partida Florin
        if((manosJugadas != 0) && (fichasApostadas != 0))
            fitness = ((manosGanadas/manosJugadas)*(fichasGanadas/fichasApostadas))/100;
        else
            fitness = 0;
        return fitness;
    }*/

    public ArrayList<Carta> verMejorMano(){
        /** Suponiendo que Florin lo haya implementado asi*/

        ArrayList<Carta> manoProvisional = new ArrayList<Carta>();    /** array creado para añadir las 7 cartas al borroso y que devuelva la mejor mano*/
        for(int i=0; i<2; i++)
            manoProvisional.add(cartasEnMano[i]);        /** se añaden las cartas que se tenga actualmente en un arraylist unificado*/
        for(int j=0; j<5; j++)                          /** para pasarselo a otro metodo y que evalue la mejor mano*/
            manoProvisional.add(cartasComunes[j]);

        /** Ahora habría que hacer una llamada a la lógica borrosa o a las reglas para devolver la mejor mano.*/
        int mejorManoCalculada = calcularMejorMano(manoProvisional);

        return mejorMano;
    }
    public int  calcularMejorMano( ArrayList<Carta> manoProvisional){

        int numCartas = manoProvisional.size();

        int contColor = 0;        /** Para tener color ---> contColor = 5*/
        int contEscalera = 0;     /** Para tener escalera ---> contEscalera > 5*/
        int contPoker = 0;        /** Para tener poker ---> contPoker = 1*/
        int contFull = 0;         /** Para tener full ---> contPareja1 = 1 o contaPareja2 = 1 y contTrio = 1*/
        int contPareja1 = 0;      /** Para tener pareja ---> contPareja = 1*/
        int contPareja2 = 0;      /** Para tener pareja ---> contPareja = 1*/
        int contDoblePareja = 0;  /** Para tener doble pareja ---> contPareja1 = 1 y contPareja2 = 1*/
        int contTrio = 0;         /** Para tener trio ---> contTrio = 3 o contPareja1 = 3 o contPareja2 = 3*/
        int contCartaAlta = 0;    /** Por defecto*/
        int contEscaleraColor = 0;

        int contPaloCorazones = 0;
        int contPaloPicas = 0;
        int contPaloDiamantes = 0;
        int contPaloTreboles = 0;
        int []numeroCarta = new int[15];
        int numeroDeLaCarta = 0;
        int valorPareja1 = 0;
        int valorPareja2 = 0;
        int valorActual = 0;
        int valorSiguiente = 0;
        Boolean encontrado = false;
        int resul = 0;


        /** Para ver si tenemos escalera lo unico que tenemos que ver es si tenemos 5 cartas seguidas*/
        Collections.sort(manoProvisional, new Comparator<Carta>() {
            @Override
            public int compare(Carta c1, Carta c2) {
                return new Integer(c1.getValor()).compareTo(new Integer(c2.getValor()));
            }
        });
        for(int i=0; i< numCartas; i++){
            if (manoProvisional.get(i).getPalo() == 1)       /** Esta forma lo que quiere expresar es que cojo todas las cartas que tenga a la vez*/
                contPaloCorazones++;                         /** y los almacena para despues valorar que tipo de mano tenemos */
            if (manoProvisional.get(i).getPalo() == 2)
                contPaloPicas++;
            if (manoProvisional.get(i).getPalo() == 3)
                contPaloDiamantes++;
            if (manoProvisional.get(i).getPalo() == 4)
                contPaloTreboles++;
            numeroDeLaCarta = manoProvisional.get(i).getValor();
            numeroCarta[numeroDeLaCarta] = numeroCarta[numeroDeLaCarta] + 1;
            if(numCartas <=(i+1)) {
                valorActual = manoProvisional.get(i).getValor();
                valorSiguiente = manoProvisional.get(i+1).getValor();
                if((valorActual+1 == valorSiguiente)) {
                    contEscalera++;
                    if(contEscalera == 5)
                        encontrado = true;
                }
                else if(!encontrado)
                    contEscalera = 0;

            }
        }
        /** Ahora vamos a ver si tenemos color, trio, pareja, doble pareja, full o poker*/

        // Para ver si tenemos pareja

        for(int j=2;j<=14;j++) {

            /** PARA PAREJAS, DOBLES PAREJAS O TRIPLES PAREJAS*/

            if ((numeroCarta[j] == 2) && (contPareja1 == 0)) { // Tenemos pareja
                contPareja1 = 1;
                valorPareja1 = j;
            }
            if ((numeroCarta[j] == 2) && (numeroCarta[j] != numeroCarta[valorPareja1]) && (contPareja2 == 0)) { // En este caso tenemos doble pareja
                contPareja2 = 1;
                contDoblePareja = 1;
                valorPareja2 = j;
            }

            /**PARA TRIOS Y FULL*/

            if (numeroCarta[j] == 3) {
                if ((contPareja1 == 1) || (contPareja2 == 1)) // Si tenemos un contador con 3 y otro con 2, entonces tenemos Full
                    contFull = 1;
                else
                    contTrio = 1;
            }
            /** PARA POKER */

            if (numeroCarta[j] == 4) // Tenemos poker
                contPoker = 1;
        }


        /** PARA COLOR */

        if ((contPaloCorazones >=4) || (contPaloDiamantes >=4) || (contPaloPicas >=4) || (contPaloTreboles >=4))
            contColor = 1;

        /** PARA ESCALERA */
        if(encontrado) // Tenemos escalera
            contEscalera = 1;

        /** Ahora para ver si tenemos escalera de color*/

        if (encontrado && (contColor == 1))
            contEscaleraColor = 1;

        /** Ahora habra que devolver la mejor mano */
        if (contEscaleraColor == 0){
            if(contPoker == 0){
                if(contFull == 0){
                    if(contColor == 0){
                        if (contEscalera == 0){
                            if(contTrio == 0){
                                if(contDoblePareja == 0){
                                    if((contPareja1 == 0) &&(contPareja2 == 0)){
                                        resul = 0;
                                    }else
                                        resul = 8; // Tenemos Pareja
                                }else
                                    resul = 7; // Tenemos Doble Pareja
                            }else
                                resul = 6; // Tenemos trio
                        }else
                            resul = 5; // Tenemos Escalera
                    }else
                        resul = 4; // Tenemos Color
                }else
                    resul = 3; // Tenemos Full
            }else
                resul = 2; // Tenemos Poker
        }else
            resul = 1; // Tenemos Escalera de color

        // si no hay mejor mano que carta alta (resul = 0 ---> mejor mano carta alta)

        return resul;
    }

    public void tomarDecision(){    /** Tomare cada decision como un entero, 0 no ir, 1 pasar, 2 apostar*/

        /** Una buena opcion es crear un arraylist y añadir las cartas actuales y mandarlo al borroso para
         ver que podemos hacer con la mejor mano que tenemos*/


    }

}
