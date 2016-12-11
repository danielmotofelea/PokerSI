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
        mejorMano = calcularMejorMano(manoProvisional);

        return mejorMano;
    }
    public ArrayList<Carta>  calcularMejorMano( ArrayList<Carta> manoProvisional){

        int numCartas = manoProvisional.size();

        /** Con estos contadores lo que hacemos es ver que tipo de mano tenemos*/
        /** y dependiendo de que contador este activo tenemos una mano u otra*/

        int contColor = 0;        /** Para tener color ---> contColor = 5*/
        int contEscalera = 0;     /** Para tener escalera ---> contEscalera > 5*/
        int contPoker = 0;        /** Para tener poker ---> contPoker = 1*/
        int contFull = 0;         /** Para tener full ---> contPareja1 = 1 o contaPareja2 = 1 y contTrio = 1*/
        int contPareja1 = 0;      /** Para tener pareja ---> contPareja = 1*/
        int contPareja2 = 0;      /** Para tener pareja ---> contPareja = 1*/
        int contDoblePareja = 0;  /** Para tener doble pareja ---> contPareja1 = 1 y contPareja2 = 1*/
        int contTrio = 0;         /** Para tener trio ---> contTrio = 3 o contPareja1 = 3 o contPareja2 = 3*/
        int contCartaAlta = 0;    /** Por defecto*/
        int contCartasIguales = 0;/** Para contar numero de cartas iguales ya se en valor o palo*/

        int tipoMano = 0;          /** Este entero lo que hace es que recibe el tipo de mano que tenemos, es decir,
                                   /** tipoMano = 1 (Pareja), 2 (Trio), 3 (DoblePareja), 4 (Full), 5 (Poker), 6 (Escalera), 7 (Color)*/
        int []numParejas = new int[5]; /** Este array de enteros es para guardar el numero de cartas iguales hay al comparar*/
        for (int i=0;i<5;i++)
            numParejas[i] = 0;
        int []numColor = new int[4];    /** Este array es para guardar el numero de colores iguales que hay por cada palo*/
        for(int j=0; j<4; j++)          /** 1ª posicion(0): nº cartas de corazones*/
            numColor[j] = 0;            /** 2ª posicion(1): nº cartas de picas*/
                                        /** 3ª posicion(2): nº cartas de diamantes*/
                                        /** 3ª posicion(3): nº cartas de treboles*/

        /** SI NO SE CUMPLE NINGUNA DE LAS SIGUIENTES CONDICIONES DEFAULT ---> CARTA ALTA ---> jugador debe pasar o irse */

        if(numCartas == 2) {        /** Estamos en el Preflop por lo tanto solo hay dos cartas*/

            if (manoProvisional.get(0).getValor() == manoProvisional.get(1).getValor())
                contPareja1 = 1;
            else contCartaAlta = 1;
        }
        if(numCartas == 5){        /** Estamos en el Flop por lo tanto hay 5 cartas*/

            if(contPareja1 == 1){   /** Si en el Preflop teniamos ya una pareja, miramos si tenemos doble pareja, trio o poker*/
                for(int i=2; i< numCartas; i++) {
                    if (manoProvisional.get(0).getValor() == manoProvisional.get(i).getValor())
                        contPareja1++;        /** Si encontramos carta del mismo valor sumamos y vemos despues cuantas tenemos*/
                }
                if(contPareja1 > 1)          /** Significa que hemos encontrado una carta mas en la pareja de la mano*/

                    if( contPareja1 == 2) {   /** Hemos encontrado una carta mas del mismo valor, por lo tanto trio*/
                        contTrio = 1;
                        contPareja1 = 0;     /** Tenemos que poner el contador a 0 porque si no nos saltaria que tenemos Full y es falso*/
                    }
                    else {
                        contPoker = 1;      /** Hemos encontrado dos cartas mas del mismo valor, por lo tanto tenemos poker*/
                        contPareja1 = 0;    /** Por lo mismo que antes, tenemos que reiniciar el contador de pareja1*/
                    }
                else if(contPareja1 == 1){   /** Tenemos la pareja en mano, pero no tenemos trio o poker, por ello buscamos doble pareja*/
                                             /** Buscamos combinaciones de las demas cartas*/
                      /** AQUI SEGURO QUE DA ALGUN ERROR INTENTAR IMPLEMENTAR DE OTRA FORMA PORQUE NO SE REALIZA BIEN EL RECORRIDO*/
                    for(int j=2; j< numCartas;j++){
                        for(int k=3; k< numCartas;k++) {
                            if (manoProvisional.get(j).getValor() == manoProvisional.get(k).getValor())
                                contPareja2++;
                        }
                    }
                    if (contPareja2 == 1 && contPareja1 == 1) /** Tenemos otra pareja, por lo tanto doble pareja*/
                        contDoblePareja = 1;  /** Asignamos el valor*/

                    else if(contPareja2 > 1) {   /** En este caso ya sea trio o poker, seria comun a todos los jugadores*/
                        if (contPareja2 == 2) {  /** ya que se trata de las cartas de la mesa*/
                            contTrio = 1;
                            contPareja2 = 0;
                        } else {
                            contPoker = 1;
                            contPareja2 = 0;
                        }
                    }
                }
            }
            else if(contPareja1 != 1){
                /** Con cada conclusion habría que cambiar el valor de carta alta dado que en este caso al principio de esta condicion
                 * vale 1 porque en el preflop no tenemos  pareja
                 * Ademas tenemos que tener en cuenta que si en el preflop no teniamos pareja ahora si podemos tenerla
                 * porque podemos tener combinaciones de cartas en mano con cartas comunes*/

                /** Comenzamos viendo si tenemos pareja*/
                for(int i=0;i <numCartas;i++){
                    for(int j = 0; j<numCartas; j++) {
                        /** Para guardar el numero de parejas podriamos hacerlo en un array de 5 posiciones en este caso para
                         * que con cada iterraccion ver cuantas cartas iguales tenemos con cada interraccion*/
                        if (manoProvisional.get(i).getValor() == manoProvisional.get(j).getValor())
                            numParejas[i] = numParejas[i] + 1;
                        if (manoProvisional.get(i).getPalo() == manoProvisional.get(j).getPalo()) {
                            if (manoProvisional.get(i).getPalo() == 1)
                                numColor[0] = numColor[0] + 1;
                            if (manoProvisional.get(i).getPalo() == 2)
                                numColor[1] = numColor[1] + 1;
                            if(manoProvisional.get(i).getPalo() == 3)
                                numColor[2] = numColor[2] + 1;
                            if(manoProvisional.get(i).getPalo() == 4)
                                numColor[3] = numColor[3] + 1;
                        }
                    }
                }
                /** Ahora tenemos que tomar una decision de los resultados obtenidos del recorrido anterior
                 * por ello si alguna de las posiciones del array de numColor = 5 tendremos color*/
                for(int k=0;k<4;k++)
                    if(numColor[k] == 5)
                        contColor = 1;
            }
            if ((contPareja1 == 1 && contTrio == 1) || (contPareja2 == 1 && contTrio == 1)) /** Si esto sucede, tendremos full*/
                contFull = 1;
            /** A continuacion partimos de que tenemos carta alta (tambien pareja, pero al ser una mano bastante baja nos da igual)*/
            /** y buscamos si tenemos color o escalera*/

        }
        if (numCartas == 6){        // Estamos en el Turn

        }
        if (numCartas == 7){        // Estamos en el River

        }


        return mejorMano;
    }

    public void tomarDecision(){    /** Tomare cada decision como un entero, 0 no ir, 1 pasar, 2 apostar*/

        /** Una buena opcion es crear un arraylist y añadir las cartas actuales y mandarlo al borroso para
         ver que podemos hacer con la mejor mano que tenemos*/


    }

}
