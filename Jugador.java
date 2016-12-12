package pokerSI;
import java.util.* ;

public class Jugador {

    protected int fichas;
    protected int fichasGanadas;
    protected int fichasApostadas;
    protected int manosGanadas;
    protected int manosJugadas;
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

        ArrayList<Carta> manoProvisional = new ArrayList<Carta>();    /** Array creado para añadir las 7 cartas al borroso y que devuelva la mejor mano*/
        for(int i=0; i<2; i++)
            manoProvisional.add(cartasEnMano[i]);        /** Se añaden las cartas que se tenga actualmente en un arraylist unificado*/
        for(int j=0; j<5; j++)                           /** Para pasarselo a otro metodo y que evalue la mejor mano*/
            manoProvisional.add(cartasComunes[j]);

        /** Ahora habría que hacer una llamada a la lógica borrosa o a las reglas para devolver la mejor mano.*/

        int mejorManoCalculada = calcularMejorMano(manoProvisional);

        return mejorMano;
    }
    public int  calcularMejorMano( ArrayList<Carta> manoProvisional){

        int numCartas = manoProvisional.size();

        int contEscalera = 0;         /** Para tener escalera ---> contEscalera > 5, este contador si es necesario para llevar la cuenta de cuantas cartas consecutivas llevamos */
        boolean color = false;        /** Para tener color ---> contPicas, contCorazones, contTreboles o contDiamantes tienen que valer al menos 5 */
        boolean poker = false;        /** Para tener poker ---> alguna posicion del array debe valer 4 */
        boolean full = false;         /** Para tener full ---> pareja1 = true o pareja2 = true y contTrio = true */
        boolean pareja1 = false;      /** Para tener pareja ---> debe haber alguna posicion del vector = 2 */
        boolean pareja2 = false;      /** Para tener pareja ---> tiene que haber un valor del vector que sea 2 y ademas no sea el mismo que pareja1 */
        boolean doblePareja = false;  /** Para tener doble pareja ---> pareja1 = true y pareja2 = true */
        boolean trio = false;         /** Para tener trio ---> alguna posicion del vector debe tener valor 3 */
        boolean escaleraColor = false;/** Para tener escalera de color ---> escalera = true y color = true */
        boolean escalera = false;     /** Para tener escalera tenemos que tener 5 cartas consecutivas*/

        int contCorazones = 0;        /** Son los contadores para tener el recuento de las veces que se repite un determinado palo*/
        int contPicas = 0;
        int contDiamantes = 0;
        int contTreboles = 0;
        int []numeroCarta = new int[13]; /** Array que cuenta las veces que se repiten las cartas por su valor pero, valor carta = posicion + 2*/
        int numeroDeLaCarta;
        int valorPareja1 = 0;            /** Lo utilizamos para guardar el valor de la primera pareja encontrada y en el caso de que tengamos dos parejas no sean la misma*/
        int valorActual;                 /** Estos contadores tienen la funcionalidad de guardar los valores para ayudar al recuento de la escalera*/
        int valorSiguiente;

        int resul = 0;                   /** Valor por defecto, dado que si no tenemos ninguna de las manos anteriores, tendremos carta alta*/

        /** OBSERVACION: En el caso del jugador nos da igual cual sea el valor de las cartas, es decir, en esta implementacion
         *               lo unico que nos interesa es que tipo de mano tenemos, trio, full, etc. Nos da igual el valor de dicha manos,
         *               ya que, dicha valoracion la tiene que tener en cuenta la logica borrosa y el objeto MESA cuando tenga que decidir
         *               cual es la mesa ganadora. */

        /** Para facilitar el recorrido ordenamos el arrayList por el valor de la carta*/

        Collections.sort(manoProvisional, new Comparator<Carta>() {
            public int compare(Carta c1, Carta c2) {
                return new Integer(c1.getValor()).compareTo(new Integer(c2.getValor()));
            }
        });

        for(int i=0; i < numCartas; i++){

            if (manoProvisional.get(i).getPalo() == 1)       /** Una vez ordenado, cogemos cada carta por separado y vemos de que palo es*/
                contCorazones++;                             /** Despues aumentamos el contador de dicho palo para llevar el recuento */
            if (manoProvisional.get(i).getPalo() == 2)
                contPicas++;
            if (manoProvisional.get(i).getPalo() == 3)
                contDiamantes++;
            if (manoProvisional.get(i).getPalo() == 4)
                contTreboles++;

            numeroDeLaCarta = manoProvisional.get(i).getValor();    /** Guardamos el valor de carta en la posicion valor - 2 (para ahorrar espacio) */
            numeroCarta[numeroDeLaCarta - 2] = numeroCarta[numeroDeLaCarta - 2] + 1;

            if((i+1) < numCartas) {              /** Se comprueba si hay mas cartas en la mano, en cuyo caso se pasa a comprobar si hay escalera o no*/

                valorActual = manoProvisional.get(i).getValor(); /** Para ver si tenemos escalera lo unico que tenemos que ver es si tenemos 5 cartas seguidas*/
                valorSiguiente = manoProvisional.get(i+1).getValor();

                if(valorActual+1 == valorSiguiente) {
                    contEscalera++;
                    if(contEscalera == 5)
                        escalera = true;
                }
                else if(!escalera)
                    contEscalera = 0;
            }

            /** VAMOS A TRATAR UN CASO ESPECIAL: Vemos si la ultima carta es un AS, si lo es buscamos la escalera A,2,3,4,5
             * Es mejor hacer la comparacion de manera secuencial para no tener que arrastrar un array circular durante todas las ejecuciones*/

            if((manoProvisional.get(i).getValor() == 14) && (manoProvisional.get(0).getValor() == 2) &&
                    (manoProvisional.get(1).getValor() == 3) && (manoProvisional.get(2).getValor() == 4) &&
                    (manoProvisional.get(3).getValor() == 5))
                escalera = true;
        }

        /** Ahora vamos a ver si tenemos color, trio, pareja, doble pareja, full o poker*/

        for(int j=0; j<13; j++) { /** El array es hasta 13 porque como hemos inicializado antes, valor de la carta = posicion(j)+2 */

            /** PARA PAREJAS O DOBLES PAREJAS */

            if ((numeroCarta[j] == 2) && (pareja1 == false)) { // Tenemos pareja
                pareja1 = true;
                valorPareja1 = j;
            }
            if ((numeroCarta[j] == 2) && (numeroCarta[j] != numeroCarta[valorPareja1]) && (pareja2 == false)) { // En este caso tenemos doble pareja
                pareja2 = true;
                doblePareja = true;
            }

            /**PARA TRIOS Y FULL*/

            if (numeroCarta[j] == 3) {
                if ((pareja1 == true) || (pareja2 == true)) // Si tenemos un contador con 3 y otro con 2, entonces tenemos Full
                    full = true;
                else
                    trio = true;
            }
            /** PARA POKER */

            if (numeroCarta[j] == 4) // Tenemos poker
                poker = true;
        }

        /** PARA COLOR */

        if ((contCorazones >4) || (contDiamantes >4) || (contPicas >4) || (contTreboles >4))
            color = true;

        /** OBSERVACION: Para escalera no hace falta valorar dado que si anteriormente escalera = true
         *               ya sabemos que tenemos escalera. */

        /** Ahora para ver si tenemos escalera de color */

        if (escalera && color)
            escaleraColor = true;

        /** Ahora habra que devolver la mejor mano */

        if (escaleraColor == false){
            if(poker == false){
                if(full == false){
                    if(color == false){
                        if (escalera == false){
                            if(trio == false){
                                if(doblePareja == false){
                                    if((pareja1 == false) &&(pareja2 == false)){
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

        /** RESUL = 0 ---> CARTA ALTA
         *          1 ---> ESCALERA DE COLOR
         *          2 ---> POKER
         *          3 ---> FULL
         *          4 ---> COLOR
         *          5 ---> ESCALERA
         *          6 ---> TRIO
         *          7 ---> DOBLE PAREJA
         *          8 ---> PAREJA                   */

        return resul;
    }

    public void tomarDecision(){    /** Tomare cada decision como un entero, 0 no ir, 1 pasar, 2 apostar*/

        /** Una buena opcion es crear un arraylist y añadir las cartas actuales y mandarlo al borroso para
         ver que podemos hacer con la mejor mano que tenemos*/


    }

}
