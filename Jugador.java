package pokerSI;

import net.sourceforge.jFuzzyLogic.FIS;
import net.sourceforge.jFuzzyLogic.FunctionBlock;
import net.sourceforge.jFuzzyLogic.membership.MembershipFunction;
import net.sourceforge.jFuzzyLogic.membership.MembershipFunctionGaussian;
import net.sourceforge.jFuzzyLogic.membership.MembershipFunctionSingleton;
import net.sourceforge.jFuzzyLogic.membership.Value;
import net.sourceforge.jFuzzyLogic.rule.*;
import net.sourceforge.jFuzzyLogic.ruleAccumulationMethod.RuleAccumulationMethodMax;
import net.sourceforge.jFuzzyLogic.ruleActivationMethod.RuleActivationMethodMin;
import net.sourceforge.jFuzzyLogic.ruleConnectionMethod.RuleConnectionMethodAndMin;
import net.sourceforge.jFuzzyLogic.ruleConnectionMethod.RuleConnectionMethodOrMax;

import java.util.* ;
public class Jugador {

    /** Habría que añadir un atributo que indique que tipo de mano tiene el jugador y pasarsela a la mesa*/

    private int fichas;
    private int fichasGanadas;
    private int fichasApostadas;
    private int manosGanadas;
    private int manosJugadas;
    private double []gen; /** Posiciones 0 a 8: pesos de reglas, posicion 9: agresividad */

    private int valorMano;
    private float fitness;
    private int []identificacion;
    private Carta []cartasEnMano;
    private ArrayList<Carta> cartasComunes;
    private ArrayList<Carta> mejorMano;

    public Jugador(){       /** Constructor del pokerSI.Jugador sin parametros */
        this.fichas = 1000;
        this.fichasGanadas = 0;
        this.fichasApostadas = 0;
        this.manosGanadas = 0;
        this.manosJugadas = 0;
        this.fitness = 0;
        this.valorMano = 1;
        this.gen = new double[10];                     /** Tamaño 10 por ser 9 pesos de reglas + valor de agresividad*/
        this.identificacion = new int[3];              /** Posicion 0: nºgeneracion // Posicion 1: nº mesa // Posicion 3: nº jugador*/
        this.cartasEnMano = new Carta[2] ;             /** Las dos cartas en mano las tomamos como enteros*/
        this.cartasComunes = new ArrayList<Carta>();   /** Las 5 cartas comunes de la mesa para ver nuestra mejor mano*/
        this.mejorMano = new ArrayList<Carta>();       /** La mejor combinacion de cartas sobre la mesa*/

        for(int i=0; i<3; i++)
            this.identificacion[i] = 0;
        for(int j=0; j<3; j++)
            this.cartasEnMano[j] = new Carta();
        for(int k=0; k<10; k++)
            this.gen[k] = 0.0;

        this.cartasComunes = new ArrayList<Carta>();
        this.mejorMano = new ArrayList<Carta>();
    }

    /** Al tratarse de atributos privados se necesitan metodos para acceder a las variables
     *  se ha decidido que sean private para asi tener un acceso controlado a los atributos
     *  que no pertenezcan a la misma clase */

    public int getFichas() {
        return fichas;
    }

    public void setFichas(int fichas) {
        this.fichas = fichas;
    }

    public int getFichasGanadas() {
        return fichasGanadas;
    }

    public void setFichasGanadas(int fichasGanadas) {
        this.fichasGanadas = fichasGanadas;
    }

    public int getFichasApostadas() {
        return fichasApostadas;
    }

    public void setFichasApostadas(int fichasApostadas) {
        this.fichasApostadas = fichasApostadas;
    }

    public int getManosGanadas() {
        return manosGanadas;
    }

    public void setManosGanadas(int manosGanadas) {
        this.manosGanadas = manosGanadas;
    }

    public int getManosJugadas() {
        return manosJugadas;
    }

    public void setManosJugadas(int manosJugadas) {
        this.manosJugadas = manosJugadas;
    }

    public double[] getGen() {
        return gen;
    }

    public void setGen(double[] gen) {
        this.gen = gen;
    }

    public int[] getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(int[] identificacion) {
        this.identificacion = identificacion;
    }

    public void setFitness(float valor){
        fitness = valor;
    }
    public float getFitness(){
        return fitness;
    }

    public ArrayList<Carta> getMejorMano() {
        return mejorMano;
    }

    public void resetAtributosJugador(){
        this.fichas = 1000;
        this.fichasGanadas = 0;
        this.fichasApostadas = 0;   // guardamos el fitness de los jugadores de una generacion a otra
        this.manosGanadas = 0;
        this.manosJugadas = 0;
        this.fitness = 0;
    }

    /** Esta funcion unicamente se invoca cuando haya finalizado la partida ya sea en la mesa inicial o en el mesa final
     *  por lo tanto se calculara unicamente 2 veces por partida en el caso de los jugadores de la mesa final
     *  y con el valor fitness ver la evolucion de la grafica de aprendizaje que elabora la clase MAIN */

    public float calcularFitness(){

        if((manosJugadas != 0) && (fichasApostadas != 0))
            fitness = ((manosGanadas/manosJugadas)*(fichasGanadas/fichasApostadas))/100;
        else
            fitness = 0;
        return fitness;
    }

    public ArrayList<Carta> verMejorMano(){

        int k;
        int posMejorCarta1 = 0;         /** Estos dos valores son para indicar la posicion de la mejor carta en cada caso*/
        int posMejorCarta2 = 0;         /** El segundo valor solo se utilizara en caso de doble pareja o full
                                        tambien podria usarse para indicar la carta mas alta y mas baja de una escalera*/
        int []numCarta = new int[13];

        ArrayList<Carta> manoProvisional = new ArrayList<Carta>();    /** Array creado para añadir las 7 cartas al borroso y que devuelva la mejor mano*/
        for(int i=0; i<2; i++)
            manoProvisional.add(cartasEnMano[i]);        /** Se añaden las cartas que se tenga actualmente en un arraylist unificado*/
        for(int j=0; j<cartasComunes.size(); j++)                           /** Para pasarselo a otro metodo y que evalue la mejor mano*/
            manoProvisional.add(cartasComunes.get(j));

        /** Ya tenemos el valor de nuestra mejor mano pero para apostar debemos hacer una consulta al sistema borroso
         *  y tomar una decision, de si apostar, pasar o irse, todo esto dependiendo de los jugadores que haya aun en la mesa
         *  cuantas fichas tengamos y sobre todo nuestra mano */

        return mejorMano;
    }

    /** Creo que seria bueno que devuelva dos valores:
     *
     *                   1 que sea el tipo de mano(sin especificar),
     *                   2 que sea la ponderacion(llamando al metodo de ponderacion)*/

    public double [] calcularMejorMano(ArrayList<Carta> manoProvisional){

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

        boolean cor = false;          /** Hemos creado estas variables para tener en cuenta que tipo de mano tenemos para asi facilitar la ponderacion */
        boolean pic = false;
        boolean treb = false;
        boolean diam = false;
        boolean escDia = false;
        boolean escCor = false;
        boolean escPic = false;
        boolean escTreb = false;

        int contCorazones = 0;        /** Son los contadores para tener el recuento de las veces que se repite un determinado palo*/
        int contPicas = 0;
        int contDiamantes = 0;
        int contTreboles = 0;
        int []numeroCarta = new int[13]; /** Array que cuenta las veces que se repiten las cartas por su valor pero, valor carta = posicion + 2*/
        int numeroDeLaCarta;

        int valorPareja1 = 0;
        int valorPareja2 = 0;            /** Lo utilizamos para guardar el valor de la primera pareja encontrada y en el caso de que tengamos dos parejas no sean la misma*/
        int valorActual;                 /** Estos contadores tienen la funcionalidad de guardar los valores para ayudar al recuento de la escalera*/
        int valorSiguiente;
        int []valorFull = new int[2];
        int valorTrio = 0;
        int valorEscaleraInicio = 0;
        int valorEscaleraFinal = 0;
        int valorColor = 0;
        int valorPoker = 0;

        double mejorMano = new double[2]; /** Se usará como retorno de la función*/
        // int resul = 1;            /** Valor por defecto, dado que si no tenemos ninguna de las manos anteriores, tendremos carta alta*/

        for(int i=0; i<13; i++)
            numeroCarta[i] = 0;
        for(int j=0; j<2; j++)
            valorFull[j] = 0;

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

        /** Valoramos de qué palo es cada carta, y también si hay COLOR*/
        for(int i=0; i < numCartas; i++){

            if (cartasComunes.get(i).getPalo() == 1) {    /** Una vez ordenado, cogemos cada carta por separado y vemos de que palo es*/
                contCorazones++;                          /** Despues aumentamos el contador de dicho palo para llevar el recuento */
                if(contCorazones > 4){
                    valorColor = cartasComunes.get(i).getValor();      /** Guarda el valor mas alto del color */
                    color = true;
                    cor = true;
                }

            }
            if (cartasComunes.get(i).getPalo() == 2){
                contPicas++;
                if(contPicas > 4){
                    valorColor = cartasComunes.get(i).getValor();
                    color = true;
                    pic = true;
                }
            }
            if (cartasComunes.get(i).getPalo() == 3) {
                contDiamantes++;
                if(contDiamantes > 4){
                    valorColor = cartasComunes.get(i).getValor();
                    color = true;
                    diam = true;
                }
            }
            if (cartasComunes.get(i).getPalo() == 4){
                contTreboles++;
                if(contTreboles > 4){
                    valorColor = cartasComunes.get(i).getValor();
                    color = true;
                    treb = true;
                }
            }
            numeroDeLaCarta = manoProvisional.get(i).getValor();    /** Guardamos el valor de carta en la posicion valor - 2 (para ahorrar espacio) */
            numeroCarta[numeroDeLaCarta - 2] = numeroCarta[numeroDeLaCarta - 2] + 1;

            if((i+1) < numCartas) {              /** Se comprueba si hay mas cartas en la mano, en cuyo caso se pasa a comprobar si hay escalera o no*/

                valorActual = manoProvisional.get(i).getValor(); /** Para ver si tenemos escalera lo unico que tenemos que ver es si tenemos 5 cartas seguidas*/
                valorSiguiente = manoProvisional.get(i+1).getValor();

                if(valorActual+1 == valorSiguiente) {
                    contEscalera++;
                    if(contEscalera == 5) {
                        escalera = true;
                        valorEscaleraFinal = valorSiguiente;
                        valorEscaleraInicio = valorSiguiente - 5;
                    }
                }
                else if(!escalera)
                    contEscalera = 0;
            }

            /** VAMOS A TRATAR UN CASO ESPECIAL: Vemos si la ultima carta es un AS, si lo es buscamos la escalera A,2,3,4,5
             * Es mejor hacer la comparacion de manera secuencial para no tener que arrastrar un array circular durante todas las ejecuciones*/

            if((manoProvisional.get(i).getValor() == 14) && (manoProvisional.get(0).getValor() == 2) &&
                    (manoProvisional.get(1).getValor() == 3) && (manoProvisional.get(2).getValor() == 4) &&
                    (manoProvisional.get(3).getValor() == 5)) {
                escalera = true;
                valorEscaleraInicio = 14;
                valorEscaleraFinal = 5;
            }
        }

        /** Ahora vamos a ver si tenemos color, trio, pareja, doble pareja, full o poker*/

        for(int j=0; j<13; j++) { /** El array es hasta 13 porque como hemos inicializado antes, valor de la carta = posicion(j)+2 */

            /** PARA PAREJAS O DOBLES PAREJAS */

            if ((numeroCarta[j] == 2) && (pareja1 == false)) { // Tenemos pareja
                pareja1 = true;
                valorPareja1 = j + 2;
            }
            if ((numeroCarta[j] == 2) && (numeroCarta[j] != numeroCarta[valorPareja1]) && (pareja2 == false)) { // En este caso tenemos doble pareja
                pareja2 = true;
                doblePareja = true;
                valorPareja2 = j + 2;
            }

            /**PARA TRIOS Y FULL*/

            if (numeroCarta[j] == 3) {
                if ((pareja1 == true) || (pareja2 == true)) // Si tenemos un contador con 3 y otro con 2, entonces tenemos Full
                {
                    full = true;
                    if (valorPareja1 > valorPareja2) {
                        valorFull[0] = valorPareja1;
                    }else valorFull[0] = valorPareja2;

                    valorFull[1] = j + 2;

                } else {
                    trio = true;
                    valorTrio = j + 2;
                }
            }
            /** PARA POKER */

            if (numeroCarta[j] == 4) { // Tenemos poker
                poker = true;
                valorPoker = j + 2;
            }
        }

        /** COLOR VALORADO ANTERIORMENTE */


        /** OBSERVACION: Para escalera no hace falta valorar dado que si anteriormente escalera = true
         *               ya sabemos que tenemos escalera. */

        /** Para ver si tenemos escalera de color */

        if (escalera && color){
            escaleraColor = true;
            if(cor)
                escCor = true;

                else if(treb)
                    escTreb = true;

                    else if(diam)
                        escDia = true;

                        else if(pic)
                            escPic = true;
        }


        /** Ahora habra que devolver la mejor mano */

        if (escaleraColor == false){
            if(poker == false){
                if(full == false){
                    if(color == false){
                        if (escalera == false){
                            if(trio == false){
                                if(doblePareja == false){
                                    if((pareja1 == false) &&(pareja2 == false)){
                                        this.valorMano = 1;
                                    }else
                                        this.valorMano = 2; // Tenemos Pareja
                                }else
                                    this.valorMano = 3; // Tenemos Doble Pareja
                            }else
                                this.valorMano = 4; // Tenemos trio
                        }else
                            this.valorMano = 5; // Tenemos Escalera
                    }else
                        this.valorMano = 6; // Tenemos Color
                }else
                    this.valorMano = 7; // Tenemos Full
            }else
                this.valorMano = 8; // Tenemos Poker
        }else
            this.valorMano = 9; // Tenemos Escalera de color

        /** valorMano = 1 ---> CARTA ALTA
         *              2 ---> PAREJA
         *              3 ---> DOBLE PAREJA
         *              4 ---> TRIO
         *              5 ---> ESCALERA
         *              6 ---> COLOR
         *              7 ---> FULL
         *              8 ---> POKER
         *              9 ---> ESCALERA DE COLOR                   */

        mejorMano[0] = (double) this.valorMano;

        mejorMano[1] = ponderarMano(escPic, escCor, escDia, escTreb, cor, pic, treb, diam, valorPareja1, valorPareja2,
                valorPoker, valorTrio, valorFull,valorColor, valorEscaleraInicio, valorEscaleraFinal);

        return mejorMano;
    }

    /** Metodo creado para ponderar las mano mano que tenemos nosotros con la mejor de la mesa, es decir,
     * comparamos el valor que nos devuelve el metodo anterior (calcularMejorMano)
     *
     * NOTA: No sería mejor tener un atributo que guarde el valor de la mejor mano para no tener que volver a llamar al metodo
     *       y asi evitar calcular de nuevo la mejor mano. (añadido, si no, resulta util buscar otra forma)*/


    public double ponderarMano(boolean escPic, boolean escCor, boolean escDia,boolean escTreb,
                            boolean cor, boolean pic, boolean treb, boolean diam, int valorPareja1, int valorPareja2,
                            int valorPoker, int valorTrio, int []valorFull,int valorColor, int valorEscaleraInicio, int valorEscaleraFinal){

        double ponderacion = 0;

        /** En el metodo recibimos la mejor combinacion que tenemos respecto de todas las cartas
         *
         * PONDERACION = ((valorCartasEnMano*tipoDeMano)*(valorCartasMejorMano*tipoMejorMano))/(valorMejorCartasMesa*tipoMejorManoMesa)
         *
         * NOTA: LA ECUACION ANTERIOR TIENE UN PROBLEMA, CUANDO NO HAY CARTAS EN MESA, POR LO TANTO HAY QUE TRATAR ESE CASO DE MANERA ESPECIAL*/

        /**Para cartas en mano*/

        int parMano = 0; // valor pareja en mano
        int carAlta = 0; // valor carta alta en mano

        /**Para cartas comunes*/

        int valorManoComun = 1;

        int contEscaleraComun = 0;
        boolean colorComun = false;
        boolean pokerComun = false;
        boolean fullComun = false;
        boolean pareja1Comun = false;
        boolean pareja2Comun = false;
        boolean dobleParejaComun = false;
        boolean trioComun = false;
        boolean escaleraColorComun = false;
        boolean escaleraComun = false;

        boolean corComun = false;
        boolean picComun = false;
        boolean trebComun = false;
        boolean diamComun = false;
        boolean escDiaComun = false;
        boolean escCorComun = false;
        boolean escPicComun = false;
        boolean escTrebComun = false;

        int contCorazonesComun = 0;        /** Son los contadores para tener el recuento de las veces que se repite un determinado palo*/
        int contPicasComun = 0;
        int contDiamantesComun = 0;
        int contTrebolesComun = 0;
        int []numeroCartaComun = new int[13]; /** Array que cuenta las veces que se repiten las cartas por su valor pero, valor carta = posicion + 2*/
        int numeroDeLaCartaComun;

        int valorPareja1Comun = 0;
        int valorPareja2Comun = 0;            /** Lo utilizamos para guardar el valor de la primera pareja encontrada y en el caso de que tengamos dos parejas no sean la misma*/
        int valorActualComun;                 /** Estos contadores tienen la funcionalidad de guardar los valores para ayudar al recuento de la escalera*/
        int valorSiguienteComun;
        int []valorFullComun = new int[2];
        int valorTrioComun = 0;
        int valorEscaleraInicioComun = 0;
        int valorEscaleraFinalComun = 0;
        int valorColorComun = 0;
        int valorPokerComun = 0;

        for(int i=0; i<13; i++)
            numeroCartaComun[i] = 0;
        for(int j=0; j<2; j++)
            valorFullComun[j] = 0;

        /** Valoramos que tenemos en nuestra mano*/

        if(cartasEnMano[0].getValor() == cartasEnMano[1].getValor())
            parMano = cartasEnMano[0].getValor();
        else{
            if(cartasEnMano[0].getValor() > cartasEnMano[1].getValor())
                carAlta = cartasEnMano[0].getValor();
            else
                carAlta = cartasEnMano[1].getValor();
        }

        /** Ahora procedemos a valorar que hay COMUN en la mesa*/

        Collections.sort(cartasComunes, new Comparator<Carta>() {
            public int compare(Carta c1, Carta c2) {
                return new Integer(c1.getValor()).compareTo(new Integer(c2.getValor()));
            }
        });

        /**
         * Contamos cuantas cartas hay de cada palo, y se valora si hay COLOR
         */
        for(int i=0; i< cartasComunes.size(); i++){

            if (cartasComunes.get(i).getPalo() == 1) {    /** Una vez ordenado, cogemos cada carta por separado y vemos de que palo es*/
                contCorazonesComun++;                     /** Despues aumentamos el contador de dicho palo para llevar el recuento */
                if(contCorazonesComun > 4) {
                    valorColorComun = cartasComunes.get(i).getValor();      /** Guarda el valor mas alto del color */
                    colorComun = true;
                    corComun = true;
                }
            }
            if (cartasComunes.get(i).getPalo() == 2){
                contPicasComun++;
                if(contPicasComun > 4){
                    valorColorComun = cartasComunes.get(i).getValor();
                    colorComun = true;
                    picComun = true;
                }

            }
            if (cartasComunes.get(i).getPalo() == 3) {
                contDiamantesComun++;
                if(contDiamantesComun > 4){
                    valorColorComun = cartasComunes.get(i).getValor();
                    colorComun = true;
                    diamComun = true;
                }

            }
            if (cartasComunes.get(i).getPalo() == 4){
                contTrebolesComun++;
                if(contTrebolesComun > 4){
                    valorColorComun = cartasComunes.get(i).getValor();
                    colorComun = true;
                    trebComun = true;
                }

            }

            numeroDeLaCartaComun = cartasComunes.get(i).getValor();    /** Guardamos el valor de carta en la posicion valor - 2 (para ahorrar espacio) */
            numeroCartaComun[numeroDeLaCartaComun - 2] = numeroCartaComun[numeroDeLaCartaComun - 2] + 1;

            if((i+1) < cartasComunes.size()) {              /** Se comprueba si hay mas cartas en la mano, en cuyo caso se pasa a comprobar si hay escalera o no*/

                valorActualComun = cartasComunes.get(i).getValor(); /** Para ver si tenemos escalera lo unico que tenemos que ver es si tenemos 5 cartas seguidas*/
                valorSiguienteComun = cartasComunes.get(i+1).getValor();

                if(valorActualComun+1 == valorSiguienteComun) {
                    contEscaleraComun++;
                    if(contEscaleraComun == 5) {
                        escaleraComun = true;
                        valorEscaleraFinalComun = valorSiguienteComun;
                        valorEscaleraInicioComun = valorEscaleraFinalComun - 4; //Escalera de ejemplo: 5-6-7-8-9, el valor inicial es el final - 4
                    }
                }
                else if(!escaleraComun)
                    contEscaleraComun = 0;
            }

            /**
                A continuación, comprobamos el caso especial de la escalera A-2-3-4-5
             */
            if((cartasComunes.get(i).getValor() == 14) && (cartasComunes.get(0).getValor() == 2) &&
                    (cartasComunes.get(1).getValor() == 3) && (cartasComunes.get(2).getValor() == 4) &&
                    (cartasComunes.get(3).getValor() == 5)) {
                escaleraComun = true;
                valorEscaleraInicioComun = 14;
                valorEscaleraFinalComun = 5;
            }
        }

        for(int j=0; j<13; j++) { /** El array es hasta 13 porque, como hemos inicializado antes, valor de la carta = posicion(j)+2 */

            /** PARA PAREJAS O DOBLES PAREJAS */

            if ((numeroCartaComun[j] == 2) && (pareja1Comun == false)) {
                pareja1Comun = true;
                valorPareja1Comun = j + 2;
            }
            if ((numeroCartaComun[j] == 2) && (numeroCartaComun[j] != numeroCartaComun[valorPareja1]) && (pareja2Comun == false)) {
                pareja2Comun = true;
                dobleParejaComun = true;
                valorPareja2Comun = j + 2;
            }

            /**PARA TRIOS Y FULL*/

            if (numeroCartaComun[j] == 3) {

                if ((pareja1Comun == true) || (pareja2Comun == true)){
                    fullComun = true;

                    if (valorPareja1Comun > valorPareja2Comun) {
                        valorFullComun[0] = valorPareja1Comun;
                    } else valorFullComun[0] = valorPareja2Comun;

                    valorFullComun[1] = j + 2; //Valor del trío que forma parte del full común
                } else {
                    trioComun = true;
                    valorTrioComun = j + 2;
                }
            }
            /** PARA POKER */

            if (numeroCartaComun[j] == 4) { // Tenemos poker
                pokerComun = true;
                valorPokerComun = j + 2;
            }
        }

        /** COLOR VALORADO ANTERIORMENTE */


        /** PARA ESCALERA DE COLOR */

        if (escaleraComun && colorComun){
            escaleraColorComun = true;
            if(corComun)
                escCorComun = true;

            else if(trebComun)
                escTrebComun = true;

            else if(diamComun)
                escDiaComun = true;

            else if(picComun)
                escPicComun = true;
        }


        /** Ahora habra que devolver la mejor mano */

        if (escaleraColorComun == false){
            if(pokerComun == false){
                if(fullComun == false){
                    if(colorComun == false){
                        if (escaleraComun == false){
                            if(trioComun == false){
                                if(dobleParejaComun == false){
                                    if((pareja1Comun == false) &&(pareja2Comun == false)){
                                        valorManoComun = 1; // Tenemos Carta Alta en mesa
                                    }else
                                        valorManoComun = 2; // Tenemos Pareja en mesa
                                }else
                                    valorManoComun = 3; // Tenemos Doble Pareja en mesa
                            }else
                                valorManoComun = 4; // Tenemos trio en mesa
                        }else
                            valorManoComun = 5; // Tenemos Escalera en mesa
                    }else
                        valorManoComun = 6; // Tenemos Color en mesa
                }else
                    valorManoComun = 7; // Tenemos Full en mesa
            }else
                valorManoComun = 8; // Tenemos Poker en mesa
        }else
            valorManoComun = 9; // Tenemos Escalera de color en mesa

        /**Procedemos a hacer la ponderacion de la mano*/

        if(cartasComunes.size() == 0) { /**Primer caso: no tenemos cartas en mesa y solo tenemos carta alta o pareja */
            if (parMano != 0)
                ponderacion = (parMano * valorMano) * (valorPareja1 * valorMano); /** Lo hacemos de esta forma para evitar que si tenemos */
            else ponderacion = carAlta;                                           /** pareja de 2, pondere mas que carta alta AS */
        }
        if(carAlta != 0){
            /** Ahora vamos a considerar todos los casos cuando tengamos CARTA ALTA en mano */
            /** Tenemos que valorar todas las combinaciones posibles(mano, mesa, mejorMano)*/

             /** 1er caso: tenemos carta alta en mano */
                /** A continuacion vamos a ver cual es nuestra mejor mano */

                if (valorMano == 1) { // mejorMano --> CA
                    if (valorManoComun == 1) { // manoMesa --> CA
                        ponderacion = carAlta; /** Carta alta ---> mesa, mano y mejor mano*/
                    }

                    else if (valorManoComun == 2) { /** Carta alta --> mano y mejor mano.
                                                        Pareja --> mesa */
                        ponderacion = (carAlta * valorMano * carAlta * valorMano) / (valorPareja1Comun * valorPareja1Comun * 2);
                        /** EJEMPLO: MANO --> CA = 7
                         *           MEJOR MANO --> CA = 7
                         *           MESA --> PAREJA = 2
                         *
                         *           PONDERACION = ((7*1)*(7*1))/(2*2*2) = 6
                         *           Conclusion: es logico este valor, ya que tener una pareja de 2 no es muy determinante*/
                    }

                    else if (valorManoComun == 3) { /** Doble Pareja --> mesa*/
                        ponderacion = (carAlta * valorMano * carAlta * valorMano) / (valorPareja1Comun * valorPareja1Comun * valorPareja2Comun * valorPareja2Comun * valorManoComun);
                    }

                    else if (valorManoComun == 4) { /** Trio --> mesa*/
                        ponderacion = (carAlta * valorMano * carAlta * valorMano) / (valorTrioComun * valorTrioComun * valorTrioComun * valorManoComun);
                    }

                    else if (valorManoComun == 5) { /** Escalera --> mesa*/
                        ponderacion = (carAlta * valorMano * carAlta * valorMano) /
                                ((valorEscaleraInicioComun + (valorEscaleraInicioComun + 1) + (valorEscaleraInicioComun + 2) + (valorEscaleraInicioComun + 3) + valorEscaleraFinalComun) * valorManoComun);
                    }

                    else if (valorManoComun == 6) { /** Color --> mesa*/
                        ponderacion = (carAlta * valorMano * carAlta * valorMano) / (valorColorComun * 5 + valorManoComun);
                        /** Al tratarse de color hay que dar mas ponderacion dependiendo del palo:
                         *
                         *                          CORAZONES
                         *                          PICAS
                         *                          DIAMANTES
                         *                          TREBOLES
                         *
                         *                   ORDENADOS POR IMPORTANCIA*/
                        if (corComun)
                            ponderacion = ponderacion + 4;
                        else if (picComun)
                            ponderacion = ponderacion + 3;
                        else if (diamComun)
                            ponderacion = ponderacion + 2;
                        else if (trebComun)
                            ponderacion = ponderacion + 1;
                    }

                    else if (valorManoComun == 7) { /** Full --> mesa*/
                        ponderacion = (carAlta * valorMano * carAlta * valorMano) / (valorFullComun[0] * valorFullComun[0] * valorFullComun[1] * valorFullComun[1] * valorManoComun);
                    }

                    else if (valorManoComun == 8) { /** Poker --> mesa*/
                        ponderacion = (carAlta * valorMano * carAlta * valorMano) / (valorPokerComun * valorPokerComun * valorPokerComun * valorPokerComun * valorManoComun);
                    }

                    else if (valorManoComun == 9) { /** Escalera color --> mesa*/
                        ponderacion = (carAlta * valorMano * carAlta * valorMano) /
                                ((valorEscaleraInicioComun + (valorEscaleraInicioComun + 1) + (valorEscaleraInicioComun + 2) + (valorEscaleraInicioComun + 3) + valorEscaleraFinalComun) * valorManoComun);

                        if (corComun)
                            ponderacion = ponderacion + 4;
                        else if (picComun)
                            ponderacion = ponderacion + 3;
                        else if (diamComun)
                            ponderacion = ponderacion + 2;
                        else if (trebComun)
                            ponderacion = ponderacion + 1;
                    }
                }
            /******************************************* HASTA AQUI CARTA ALTA COMO MEJOR MANO *******************************************************/

                /** Los siguientes casos son con: Carta alta en mano
                 *                                Pareja como mejor mano
                 *                                y todas las combinaciones posibles en mesa*/

                else if (valorMano == 2) {
                    if (valorManoComun == 1){
                      //  ponderacion = (carAlta * 1 * valorPareja1 * valorPareja1 * valorMano); OMITIDOS LOS 1'S PARA SIMPLIFICAR
                        ponderacion = (carAlta * valorPareja1 * valorPareja1 * valorMano);
                    }

                    else if (valorManoComun == 2){
                        ponderacion = (carAlta * valorPareja1 * valorPareja1 * valorMano) / (valorPareja1Comun * valorPareja1Comun * valorManoComun);
                    }

                    else if (valorManoComun == 3) { /** Doble Pareja --> mesa*/
                        ponderacion = (carAlta * valorMano * valorPareja1 * valorMano) / (valorPareja1Comun * valorPareja1Comun * valorPareja2Comun * valorPareja2Comun * valorManoComun);
                    }

                    else if (valorManoComun == 4){
                        ponderacion = (carAlta * valorPareja1 * valorPareja1 * valorMano) / (valorTrioComun * valorTrioComun * valorTrioComun * valorManoComun);
                    }

                    else if (valorManoComun == 5) { /** Escalera --> mesa*/
                        ponderacion = (carAlta * valorPareja1 * valorMano) /
                                ((valorEscaleraInicioComun + (valorEscaleraInicioComun + 1) + (valorEscaleraInicioComun + 2) + (valorEscaleraInicioComun + 3) + valorEscaleraFinalComun) * valorManoComun);
                    }

                    else if (valorManoComun == 6) { /** Color --> mesa*/
                        ponderacion = (carAlta * valorPareja1 * valorMano) / (valorColorComun * 5 + valorManoComun);

                        if (corComun)
                            ponderacion = ponderacion + 4;
                        else if (picComun)
                            ponderacion = ponderacion + 3;
                        else if (diamComun)
                            ponderacion = ponderacion + 2;
                        else if (trebComun)
                            ponderacion = ponderacion + 1;
                    }

                    else if (valorManoComun == 7) { /** Full --> mesa*/
                        ponderacion = (carAlta * valorPareja1 * valorMano) / (valorFullComun[0] * valorFullComun[0] * valorFullComun[1] * valorFullComun[1] * valorManoComun);
                    }

                    else if (valorManoComun == 8) { /** Poker --> mesa*/
                        ponderacion = (carAlta * valorPareja1 * valorMano) / (valorPokerComun * valorPokerComun * valorPokerComun * valorPokerComun * valorManoComun);
                    }

                    else if (valorManoComun == 9) { /** Escalera color --> mesa*/
                        ponderacion = (carAlta * valorPareja1 * valorMano) /
                                ((valorEscaleraInicioComun + (valorEscaleraInicioComun + 1) + (valorEscaleraInicioComun + 2) + (valorEscaleraInicioComun + 3) + valorEscaleraFinalComun) * valorManoComun);

                        if (corComun)
                            ponderacion = ponderacion + 4;
                        else if (picComun)
                            ponderacion = ponderacion + 3;
                        else if (diamComun)
                            ponderacion = ponderacion + 2;
                        else if (trebComun)
                            ponderacion = ponderacion + 1;
                    }
                }
                /******************************************* HASTA AQUI PAREJA MEJOR MANO *******************************************************/

                /** Ahora tenemos como MEJOR MANO una DOBLE PAREJA*/
                else if (valorMano == 3) {
                    if (valorManoComun == 1){
                        ponderacion = (carAlta * valorPareja1 * valorPareja1 * valorPareja2 * valorPareja2 * valorMano);
                    }

                    else if (valorManoComun == 2){
                        ponderacion = (carAlta * valorPareja1 * valorPareja1 * valorPareja2 * valorPareja2 * valorMano) / (valorPareja1Comun * valorPareja1Comun * valorManoComun);
                    }

                    else if (valorManoComun == 3) { /** Doble Pareja --> mesa*/
                        ponderacion = (carAlta * valorPareja1 * valorPareja1 * valorPareja2 * valorPareja2 * valorMano) / (valorPareja1Comun * valorPareja1Comun * valorPareja2Comun * valorPareja2Comun * valorManoComun);
                    }

                    else if (valorManoComun == 4){
                        ponderacion = (carAlta * valorPareja1 * valorPareja1 * valorPareja2 * valorPareja2 * valorMano) / (valorTrioComun * valorTrioComun * valorTrioComun * valorManoComun);
                    }

                    else if (valorManoComun == 5) { /** Escalera --> mesa*/
                        ponderacion = (carAlta * valorPareja1 * valorPareja1 * valorPareja2 * valorPareja2 * valorMano) /
                                ((valorEscaleraInicioComun + (valorEscaleraInicioComun + 1) + (valorEscaleraInicioComun + 2) + (valorEscaleraInicioComun + 3) + valorEscaleraFinalComun) * valorManoComun);
                    }

                    else if (valorManoComun == 6) { /** Color --> mesa*/
                        ponderacion = (carAlta * valorPareja1 * valorPareja1 * valorPareja2 * valorPareja2 * valorMano) / (valorColorComun * 5 + valorManoComun);

                        if (corComun)
                            ponderacion = ponderacion + 4;
                        else if (picComun)
                            ponderacion = ponderacion + 3;
                        else if (diamComun)
                            ponderacion = ponderacion + 2;
                        else if (trebComun)
                            ponderacion = ponderacion + 1;
                    }

                    else if (valorManoComun == 7) { /** Full --> mesa*/
                        ponderacion = (carAlta * valorPareja1 * valorPareja1 * valorPareja2 * valorPareja2 * valorMano) / (valorFullComun[0] * valorFullComun[0] * valorFullComun[1] * valorFullComun[1] * valorManoComun);
                    }

                    else if (valorManoComun == 8) { /** Poker --> mesa*/
                        ponderacion = (carAlta * valorPareja1 * valorPareja1 * valorPareja2 * valorPareja2 * valorMano) / (valorPokerComun * valorPokerComun * valorPokerComun * valorPokerComun * valorManoComun);
                    }

                    else if (valorManoComun == 9) { /** Escalera color --> mesa*/
                        ponderacion = (carAlta * valorPareja1 * valorPareja1 * valorPareja2 * valorPareja2 * valorMano) /
                                ((valorEscaleraInicioComun + (valorEscaleraInicioComun + 1) + (valorEscaleraInicioComun + 2) + (valorEscaleraInicioComun + 3) + valorEscaleraFinalComun) * valorManoComun);

                        if (corComun)
                            ponderacion = ponderacion + 4;
                        else if (picComun)
                            ponderacion = ponderacion + 3;
                        else if (diamComun)
                            ponderacion = ponderacion + 2;
                        else if (trebComun)
                            ponderacion = ponderacion + 1;
                    }
                }
                /******************************************* HASTA AQUI DOBLE PAREJA MEJOR MANO *******************************************************/

                else if (valorMano == 4) {
                    /** Los siguientes casos son con: Carta alta en mano
                     *                                Trio como mejor mano
                     *                                y todas las combinaciones posibles en mesa */
                    if (valorManoComun == 1){
                        ponderacion = (carAlta * valorTrio * valorTrio * valorTrio * valorMano);
                    }

                    else if (valorManoComun == 2){
                        ponderacion = (carAlta * valorTrio * valorTrio * valorTrio * valorMano) / (valorPareja1Comun * valorPareja1Comun * valorManoComun);
                    }

                    else if (valorManoComun == 3) { /** Doble Pareja --> mesa*/
                        ponderacion = (carAlta * valorTrio * valorTrio * valorTrio * valorMano) / (valorPareja1Comun * valorPareja1Comun * valorPareja2Comun * valorPareja2Comun * valorManoComun);
                    }

                    else if (valorManoComun == 4){
                        ponderacion = (carAlta * valorTrio * valorTrio * valorTrio * valorMano) / (valorTrioComun * valorTrioComun * valorTrioComun * valorManoComun);
                    }

                    else if (valorManoComun == 5) { /** Escalera --> mesa*/
                        ponderacion = (carAlta * valorTrio * valorTrio * valorTrio * valorMano) /
                                ((valorEscaleraInicioComun + (valorEscaleraInicioComun + 1) + (valorEscaleraInicioComun + 2) + (valorEscaleraInicioComun + 3) + valorEscaleraFinalComun) * valorManoComun);
                    }

                    else if (valorManoComun == 6) { /** Color --> mesa*/
                        ponderacion = (carAlta * valorTrio * valorTrio * valorTrio * valorMano) / (valorColorComun * 5 + valorManoComun);

                        if (corComun)
                            ponderacion = ponderacion + 4;
                        else if (picComun)
                            ponderacion = ponderacion + 3;
                        else if (diamComun)
                            ponderacion = ponderacion + 2;
                        else if (trebComun)
                            ponderacion = ponderacion + 1;
                    }

                    else if (valorManoComun == 7) { /** Full --> mesa*/
                        ponderacion = (carAlta * valorTrio * valorTrio * valorTrio * valorMano) / (valorFullComun[0] * valorFullComun[0] * valorFullComun[1] * valorFullComun[1] * valorManoComun);
                    }

                    else if (valorManoComun == 8) { /** Poker --> mesa*/
                        ponderacion = (carAlta * valorTrio * valorTrio * valorTrio * valorMano) / (valorPokerComun * valorPokerComun * valorPokerComun * valorPokerComun * valorManoComun);
                    }

                    else if (valorManoComun == 9) { /** Escalera color --> mesa*/
                        ponderacion = (carAlta * valorTrio * valorTrio * valorTrio * valorMano) /
                                ((valorEscaleraInicioComun + (valorEscaleraInicioComun + 1) + (valorEscaleraInicioComun + 2) + (valorEscaleraInicioComun + 3) + valorEscaleraFinalComun) * valorManoComun);

                        if (corComun)
                            ponderacion = ponderacion + 4;
                        else if (picComun)
                            ponderacion = ponderacion + 3;
                        else if (diamComun)
                            ponderacion = ponderacion + 2;
                        else if (trebComun)
                            ponderacion = ponderacion + 1;
                    }
                }
            /******************************************* HASTA AQUI TRIO MEJOR MANO *******************************************************/
                else if (valorMano == 5) {
                    /** Los siguientes casos son con: Carta alta en mano
                     *                                Escalera como mejor mano
                     *                                y todas las combinaciones posibles en mesa */
                    if (valorManoComun == 1){
                        ponderacion = (carAlta *(valorEscaleraInicio + (valorEscaleraInicio + 1) + (valorEscaleraInicio + 2) + (valorEscaleraInicio + 3) + valorEscaleraFinal) * valorMano);
                    }

                    else if (valorManoComun == 2){
                        ponderacion = (carAlta *(valorEscaleraInicio + (valorEscaleraInicio + 1) + (valorEscaleraInicio + 2) + (valorEscaleraInicio + 3) + valorEscaleraFinal) * valorMano) /
                                        (valorPareja1Comun * valorPareja1Comun * valorManoComun);
                    }

                    else if (valorManoComun == 3) { /** Doble Pareja --> mesa*/
                        ponderacion = (carAlta *(valorEscaleraInicio + (valorEscaleraInicio + 1) + (valorEscaleraInicio + 2) + (valorEscaleraInicio + 3) + valorEscaleraFinal) * valorMano) /
                                        (valorPareja1Comun * valorPareja1Comun * valorPareja2Comun * valorPareja2Comun * valorManoComun);
                    }

                    else if (valorManoComun == 4){
                        ponderacion = (carAlta *(valorEscaleraInicio + (valorEscaleraInicio + 1) + (valorEscaleraInicio + 2) + (valorEscaleraInicio + 3) + valorEscaleraFinal) * valorMano) /
                                        (valorTrioComun * valorTrioComun * valorTrioComun * valorManoComun);
                    }

                    else if (valorManoComun == 5) { /** Escalera --> mesa*/
                        ponderacion = (carAlta *(valorEscaleraInicio + (valorEscaleraInicio + 1) + (valorEscaleraInicio + 2) + (valorEscaleraInicio + 3) + valorEscaleraFinal) * valorMano) /
                                ((valorEscaleraInicioComun + (valorEscaleraInicioComun + 1) + (valorEscaleraInicioComun + 2) + (valorEscaleraInicioComun + 3) + valorEscaleraFinalComun) * valorManoComun);
                    }

                    else if (valorManoComun == 6) { /** Color --> mesa*/
                        ponderacion = (carAlta *(valorEscaleraInicio + (valorEscaleraInicio + 1) + (valorEscaleraInicio + 2) + (valorEscaleraInicio + 3) + valorEscaleraFinal) * valorMano) /
                                        (valorColorComun * 5 + valorManoComun);

                        if (corComun)
                            ponderacion = ponderacion + 4;
                        else if (picComun)
                            ponderacion = ponderacion + 3;
                        else if (diamComun)
                            ponderacion = ponderacion + 2;
                        else if (trebComun)
                            ponderacion = ponderacion + 1;
                    }

                    else if (valorManoComun == 7) { /** Full --> mesa*/
                        ponderacion = (carAlta *(valorEscaleraInicio + (valorEscaleraInicio + 1) + (valorEscaleraInicio + 2) + (valorEscaleraInicio + 3) + valorEscaleraFinal) * valorMano) /
                                         (valorFullComun[0] * valorFullComun[0] * valorFullComun[1] * valorFullComun[1] * valorManoComun);
                    }

                    else if (valorManoComun == 8) { /** Poker --> mesa*/
                        ponderacion = (carAlta *(valorEscaleraInicio + (valorEscaleraInicio + 1) + (valorEscaleraInicio + 2) + (valorEscaleraInicio + 3) + valorEscaleraFinal) * valorMano) /
                                        (valorPokerComun * valorPokerComun * valorPokerComun * valorPokerComun * valorManoComun);
                    }

                    else if (valorManoComun == 9) { /** Escalera color --> mesa*/
                        ponderacion = (carAlta *(valorEscaleraInicio + (valorEscaleraInicio + 1) + (valorEscaleraInicio + 2) + (valorEscaleraInicio + 3) + valorEscaleraFinal) * valorMano) /
                                ((valorEscaleraInicioComun + (valorEscaleraInicioComun + 1) + (valorEscaleraInicioComun + 2) + (valorEscaleraInicioComun + 3) + valorEscaleraFinalComun) * valorManoComun);

                        if (corComun)
                            ponderacion = ponderacion + 4;
                        else if (picComun)
                            ponderacion = ponderacion + 3;
                        else if (diamComun)
                            ponderacion = ponderacion + 2;
                        else if (trebComun)
                            ponderacion = ponderacion + 1;
                    }
                }
                /******************************************* HASTA AQUI ESCALERA MEJOR MANO *******************************************************/
                else if (valorMano == 6) {
                    /** Los siguientes casos son con: Carta alta en mano
                     *                                Color como mejor mano
                     *                                y todas las combinaciones posibles en mesa */
                    if (valorManoComun == 1){
                        ponderacion = (carAlta *(valorEscaleraInicio + (valorEscaleraInicio + 1) + (valorEscaleraInicio + 2) + (valorEscaleraInicio + 3) + valorEscaleraFinal) * valorMano);
                    }

                    else if (valorManoComun == 2){
                        ponderacion = (carAlta *(valorEscaleraInicio + (valorEscaleraInicio + 1) + (valorEscaleraInicio + 2) + (valorEscaleraInicio + 3) + valorEscaleraFinal) * valorMano) /
                                (valorPareja1Comun * valorPareja1Comun * valorManoComun);
                    }

                    else if (valorManoComun == 3) { /** Doble Pareja --> mesa*/
                        ponderacion = (carAlta *(valorEscaleraInicio + (valorEscaleraInicio + 1) + (valorEscaleraInicio + 2) + (valorEscaleraInicio + 3) + valorEscaleraFinal) * valorMano) /
                                (valorPareja1Comun * valorPareja1Comun * valorPareja2Comun * valorPareja2Comun * valorManoComun);
                    }

                    else if (valorManoComun == 4){
                        ponderacion = (carAlta *(valorEscaleraInicio + (valorEscaleraInicio + 1) + (valorEscaleraInicio + 2) + (valorEscaleraInicio + 3) + valorEscaleraFinal) * valorMano) /
                                (valorTrioComun * valorTrioComun * valorTrioComun * valorManoComun);
                    }

                    else if (valorManoComun == 5) { /** Escalera --> mesa*/
                        ponderacion = (carAlta *(valorEscaleraInicio + (valorEscaleraInicio + 1) + (valorEscaleraInicio + 2) + (valorEscaleraInicio + 3) + valorEscaleraFinal) * valorMano) /
                                ((valorEscaleraInicioComun + (valorEscaleraInicioComun + 1) + (valorEscaleraInicioComun + 2) + (valorEscaleraInicioComun + 3) + valorEscaleraFinalComun) * valorManoComun);
                    }

                    else if (valorManoComun == 6) { /** Color --> mesa*/
                        ponderacion = (carAlta *(valorEscaleraInicio + (valorEscaleraInicio + 1) + (valorEscaleraInicio + 2) + (valorEscaleraInicio + 3) + valorEscaleraFinal) * valorMano) /
                                (valorColorComun * 5 + valorManoComun);

                        if (corComun)
                            ponderacion = ponderacion + 4;
                        else if (picComun)
                            ponderacion = ponderacion + 3;
                        else if (diamComun)
                            ponderacion = ponderacion + 2;
                        else if (trebComun)
                            ponderacion = ponderacion + 1;
                    }

                    else if (valorManoComun == 7) { /** Full --> mesa*/
                        ponderacion = (carAlta *(valorEscaleraInicio + (valorEscaleraInicio + 1) + (valorEscaleraInicio + 2) + (valorEscaleraInicio + 3) + valorEscaleraFinal) * valorMano) /
                                (valorFullComun[0] * valorFullComun[0] * valorFullComun[1] * valorFullComun[1] * valorManoComun);
                    }

                    else if (valorManoComun == 8) { /** Poker --> mesa*/
                        ponderacion = (carAlta *(valorEscaleraInicio + (valorEscaleraInicio + 1) + (valorEscaleraInicio + 2) + (valorEscaleraInicio + 3) + valorEscaleraFinal) * valorMano) /
                                (valorPokerComun * valorPokerComun * valorPokerComun * valorPokerComun * valorManoComun);
                    }

                    else if (valorManoComun == 9) { /** Escalera color --> mesa*/
                        ponderacion = (carAlta *(valorEscaleraInicio + (valorEscaleraInicio + 1) + (valorEscaleraInicio + 2) + (valorEscaleraInicio + 3) + valorEscaleraFinal) * valorMano) /
                                ((valorEscaleraInicioComun + (valorEscaleraInicioComun + 1) + (valorEscaleraInicioComun + 2) + (valorEscaleraInicioComun + 3) + valorEscaleraFinalComun) * valorManoComun);

                        if (corComun)
                            ponderacion = ponderacion + 4;
                        else if (picComun)
                            ponderacion = ponderacion + 3;
                        else if (diamComun)
                            ponderacion = ponderacion + 2;
                        else if (trebComun)
                            ponderacion = ponderacion + 1;
                    }

                }
            /******************************************* HASTA AQUI COLOR MEJOR MANO *******************************************************/
                else if (valorMano == 7) {
                    /** Los siguientes casos son con: Carta alta en mano
                     *                                Full como mejor mano
                     *                                y todas las combinaciones posibles en mesa */
                    if (valorManoComun == 1){
                        ponderacion = (carAlta * valorFull[0] * valorFull[0] * valorFull[1] * valorFull[1] * valorMano);
                    }

                    else if (valorManoComun == 2){
                        ponderacion = (carAlta * valorFull[0] * valorFull[0] * valorFull[1] * valorFull[1] * valorMano) /
                                (valorPareja1Comun * valorPareja1Comun * valorManoComun);
                    }

                    else if (valorManoComun == 3) { /** Doble Pareja --> mesa*/
                        ponderacion = (carAlta * valorFull[0] * valorFull[0] * valorFull[1] * valorFull[1] * valorMano) /
                                (valorPareja1Comun * valorPareja1Comun * valorPareja2Comun * valorPareja2Comun * valorManoComun);
                    }

                    else if (valorManoComun == 4){
                        ponderacion = (carAlta * valorFull[0] * valorFull[0] * valorFull[1] * valorFull[1] * valorMano) /
                                (valorTrioComun * valorTrioComun * valorTrioComun * valorManoComun);
                    }

                    else if (valorManoComun == 5) { /** Escalera --> mesa*/
                        ponderacion = (carAlta * valorFull[0] * valorFull[0] * valorFull[1] * valorFull[1] * valorMano) /
                                ((valorEscaleraInicioComun + (valorEscaleraInicioComun + 1) + (valorEscaleraInicioComun + 2) + (valorEscaleraInicioComun + 3) + valorEscaleraFinalComun) * valorManoComun);
                    }

                    else if (valorManoComun == 6) { /** Color --> mesa*/
                        ponderacion = (carAlta * valorFull[0] * valorFull[0] * valorFull[1] * valorFull[1] * valorMano) /
                                (valorColorComun * 5 + valorManoComun);

                        if (corComun)
                            ponderacion = ponderacion + 4;
                        else if (picComun)
                            ponderacion = ponderacion + 3;
                        else if (diamComun)
                            ponderacion = ponderacion + 2;
                        else if (trebComun)
                            ponderacion = ponderacion + 1;
                    }

                    else if (valorManoComun == 7) { /** Full --> mesa*/
                        ponderacion = (carAlta * valorFull[0] * valorFull[0] * valorFull[1] * valorFull[1] * valorMano) /
                                (valorPareja1Comun * valorPareja1Comun * valorTrioComun * valorTrioComun * valorManoComun);
                    }

                    else if (valorManoComun == 8) { /** Poker --> mesa*/
                        ponderacion = (carAlta * valorFull[0] * valorFull[0] * valorFull[1] * valorFull[1] * valorMano) /
                                (valorPokerComun * valorPokerComun * valorPokerComun * valorPokerComun * valorManoComun);
                    }

                    else if (valorManoComun == 9) { /** Escalera color --> mesa*/
                        ponderacion = (carAlta * valorFull[0] * valorFull[0] * valorFull[1] * valorFull[1] * valorMano) /
                                ((valorEscaleraInicioComun + (valorEscaleraInicioComun + 1) + (valorEscaleraInicioComun + 2) + (valorEscaleraInicioComun + 3) + valorEscaleraFinalComun) * valorManoComun);

                        if (corComun)
                            ponderacion = ponderacion + 4;
                        else if (picComun)
                            ponderacion = ponderacion + 3;
                        else if (diamComun)
                            ponderacion = ponderacion + 2;
                        else if (trebComun)
                            ponderacion = ponderacion + 1;
                    }

                }
            /******************************************* HASTA AQUI FULL MEJOR MANO *******************************************************/
                else if (valorMano == 8) {
                    /** Los siguientes casos son con: Carta alta en mano
                     *                                Poker como mejor mano
                     *                                y todas las combinaciones posibles en mesa */
                    if (valorManoComun == 1){
                        ponderacion = (carAlta * valorPoker * valorPoker * valorPoker * valorPoker * valorMano);
                    }

                    else if (valorManoComun == 2){
                        ponderacion = (carAlta * valorPoker * valorPoker * valorPoker * valorPoker * valorMano) /
                                (valorPareja1Comun * valorPareja1Comun * valorManoComun);
                    }

                    else if (valorManoComun == 3) { /** Doble Pareja --> mesa*/
                        ponderacion = (carAlta * valorPoker * valorPoker * valorPoker * valorPoker * valorMano) /
                                (valorPareja1Comun * valorPareja1Comun * valorPareja2Comun * valorPareja2Comun * valorManoComun);
                    }

                    else if (valorManoComun == 4){
                        ponderacion = (carAlta * valorPoker * valorPoker * valorPoker * valorPoker * valorMano) /
                                (valorTrioComun * valorTrioComun * valorTrioComun * valorManoComun);
                    }

                    else if (valorManoComun == 5) { /** Escalera --> mesa*/
                        ponderacion = (carAlta * valorPoker * valorPoker * valorPoker * valorPoker * valorMano) /
                                ((valorEscaleraInicioComun + (valorEscaleraInicioComun + 1) + (valorEscaleraInicioComun + 2) + (valorEscaleraInicioComun + 3) + valorEscaleraFinalComun) * valorManoComun);
                    }

                    else if (valorManoComun == 6) { /** Color --> mesa*/
                        ponderacion = (carAlta * valorPoker * valorPoker * valorPoker * valorPoker * valorMano) /
                                (valorColorComun * 5 + valorManoComun);

                        if (corComun)
                            ponderacion = ponderacion + 4;
                        else if (picComun)
                            ponderacion = ponderacion + 3;
                        else if (diamComun)
                            ponderacion = ponderacion + 2;
                        else if (trebComun)
                            ponderacion = ponderacion + 1;
                    }

                    else if (valorManoComun == 7) { /** Full --> mesa*/
                        ponderacion = (carAlta * valorPoker * valorPoker * valorPoker * valorPoker * valorMano) /
                                (valorPareja1Comun * valorPareja1Comun * valorTrioComun * valorTrioComun * valorManoComun);
                    }

                    else if (valorManoComun == 8) { /** Poker --> mesa*/
                        ponderacion = (carAlta * valorPoker * valorPoker * valorPoker * valorPoker * valorMano) /
                                (valorPokerComun * valorPokerComun * valorPokerComun * valorPokerComun * valorManoComun);
                    }

                    else if (valorManoComun == 9) { /** Escalera color --> mesa*/
                        ponderacion = (carAlta * valorPoker * valorPoker * valorPoker * valorPoker * valorMano) /
                                ((valorEscaleraInicioComun + (valorEscaleraInicioComun + 1) + (valorEscaleraInicioComun + 2) + (valorEscaleraInicioComun + 3) + valorEscaleraFinalComun) * valorManoComun);

                        if (corComun)
                            ponderacion = ponderacion + 4;
                        else if (picComun)
                            ponderacion = ponderacion + 3;
                        else if (diamComun)
                            ponderacion = ponderacion + 2;
                        else if (trebComun)
                            ponderacion = ponderacion + 1;
                    }
                }
            /******************************************* HASTA AQUI POKER MEJOR MANO *******************************************************/
                else if (valorMano == 9) {
                    /** Los siguientes casos son con: Carta alta en mano
                     *                                Escalera color como mejor mano
                     *                                y todas las combinaciones posibles en mesa */
                    if (valorManoComun == 1){
                        ponderacion = (carAlta * (valorEscaleraInicio + (valorEscaleraInicio + 1) + (valorEscaleraInicio + 2) + (valorEscaleraInicio + 3) + valorEscaleraFinal) * valorMano);

                        if (cor)
                            ponderacion = ponderacion + 4;
                        else if (pic)
                            ponderacion = ponderacion + 3;
                        else if (diam)
                            ponderacion = ponderacion + 2;
                        else if (treb)
                            ponderacion = ponderacion + 1;
                    }

                    else if (valorManoComun == 2){
                        ponderacion = (carAlta * (valorEscaleraInicio + (valorEscaleraInicio + 1) + (valorEscaleraInicio + 2) + (valorEscaleraInicio + 3) + valorEscaleraFinal) * valorMano) /
                                (valorPareja1Comun * valorPareja1Comun * valorManoComun);
                        if (cor)
                            ponderacion = ponderacion + 4;
                        else if (pic)
                            ponderacion = ponderacion + 3;
                        else if (diam)
                            ponderacion = ponderacion + 2;
                        else if (treb)
                            ponderacion = ponderacion + 1;
                    }

                    else if (valorManoComun == 3) { /** Doble Pareja --> mesa*/
                        ponderacion = (carAlta * (valorEscaleraInicio + (valorEscaleraInicio + 1) + (valorEscaleraInicio + 2) + (valorEscaleraInicio + 3) + valorEscaleraFinal) * valorMano) /
                                (valorPareja1Comun * valorPareja1Comun * valorPareja2Comun * valorPareja2Comun * valorManoComun);
                        if (cor)
                            ponderacion = ponderacion + 4;
                        else if (pic)
                            ponderacion = ponderacion + 3;
                        else if (diam)
                            ponderacion = ponderacion + 2;
                        else if (treb)
                            ponderacion = ponderacion + 1;
                    }

                    else if (valorManoComun == 4){
                        ponderacion = (carAlta * (valorEscaleraInicio + (valorEscaleraInicio + 1) + (valorEscaleraInicio + 2) + (valorEscaleraInicio + 3) + valorEscaleraFinal) * valorMano) /
                                (valorTrioComun * valorTrioComun * valorTrioComun * valorManoComun);
                        if (cor)
                            ponderacion = ponderacion + 4;
                        else if (pic)
                            ponderacion = ponderacion + 3;
                        else if (diam)
                            ponderacion = ponderacion + 2;
                        else if (treb)
                            ponderacion = ponderacion + 1;
                    }

                    else if (valorManoComun == 5) { /** Escalera --> mesa*/
                        ponderacion = (carAlta * (valorEscaleraInicio + (valorEscaleraInicio + 1) + (valorEscaleraInicio + 2) + (valorEscaleraInicio + 3) + valorEscaleraFinal) * valorMano) /
                                ((valorEscaleraInicioComun + (valorEscaleraInicioComun + 1) + (valorEscaleraInicioComun + 2) + (valorEscaleraInicioComun + 3) + valorEscaleraFinalComun) * valorManoComun);
                        if (cor)
                            ponderacion = ponderacion + 4;
                        else if (pic)
                            ponderacion = ponderacion + 3;
                        else if (diam)
                            ponderacion = ponderacion + 2;
                        else if (treb)
                            ponderacion = ponderacion + 1;
                    }

                    else if (valorManoComun == 6) { /** Color --> mesa*/
                        ponderacion = (carAlta * (valorEscaleraInicio + (valorEscaleraInicio + 1) + (valorEscaleraInicio + 2) + (valorEscaleraInicio + 3) + valorEscaleraFinal) * valorMano) /
                                (valorColorComun * 5 + valorManoComun);

                        if (corComun)
                            ponderacion = ponderacion + 4;
                        else if (picComun)
                            ponderacion = ponderacion + 3;
                        else if (diamComun)
                            ponderacion = ponderacion + 2;
                        else if (trebComun)
                            ponderacion = ponderacion + 1;
                    }

                    else if (valorManoComun == 7) { /** Full --> mesa*/
                        ponderacion = (carAlta * (valorEscaleraInicio + (valorEscaleraInicio + 1) + (valorEscaleraInicio + 2) + (valorEscaleraInicio + 3) + valorEscaleraFinal) * valorMano) /
                                (valorPareja1Comun * valorPareja1Comun * valorTrioComun * valorTrioComun * valorManoComun);
                        if (cor)
                            ponderacion = ponderacion + 4;
                        else if (pic)
                            ponderacion = ponderacion + 3;
                        else if (diam)
                            ponderacion = ponderacion + 2;
                        else if (treb)
                            ponderacion = ponderacion + 1;
                    }

                    else if (valorManoComun == 8) { /** Poker --> mesa*/
                        ponderacion = (carAlta * (valorEscaleraInicio + (valorEscaleraInicio + 1) + (valorEscaleraInicio + 2) + (valorEscaleraInicio + 3) + valorEscaleraFinal) * valorMano) /
                                (valorPokerComun * valorPokerComun * valorPokerComun * valorPokerComun * valorManoComun);
                        if (cor)
                            ponderacion = ponderacion + 4;
                        else if (pic)
                            ponderacion = ponderacion + 3;
                        else if (diam)
                            ponderacion = ponderacion + 2;
                        else if (treb)
                            ponderacion = ponderacion + 1;
                    }

                    else if (valorManoComun == 9) { /** Escalera color --> mesa*/
                        ponderacion = (carAlta * (valorEscaleraInicio + (valorEscaleraInicio + 1) + (valorEscaleraInicio + 2) + (valorEscaleraInicio + 3) + valorEscaleraFinal) * valorMano) /
                                ((valorEscaleraInicioComun + (valorEscaleraInicioComun + 1) + (valorEscaleraInicioComun + 2) + (valorEscaleraInicioComun + 3) + valorEscaleraFinalComun) * valorManoComun);

                        if (corComun)
                            ponderacion = ponderacion + 4;
                        else if (picComun)
                            ponderacion = ponderacion + 3;
                        else if (diamComun)
                            ponderacion = ponderacion + 2;
                        else if (trebComun)
                            ponderacion = ponderacion + 1;
                    }
                }
            /******************************************* HASTA AQUI ESCALERA DE COLOR MEJOR MANO *********************************************/
        }/** Final carta alta en mano*/

            /** AHORA VAMOS A TRATAR LOS CASOS EN LOS QUE TENGAMOS PAREJA EN MANO*/

        else { // if(parMano !=0 )
                /** Seria el mismo codigo pero cambiando la primera parte del calculo de la ponderacion
                 * el unico cambio seria que en mano tendriamos pareja
                 * 1er caso: tenemos carta alta en mano
                 * A continuacion vamos a ver cual es nuestra mejor mano */

            /** NOTA: NO SE PUEDE DAR EL CASO DE QUE TENGAMOS EN MANO CARTA ALTA O NUESTRA MEJOR MANO SEA CARTA ALTA (MINIMO PAREJA COMO MEJOR MANO)*/

            /** Los siguientes casos son con: Pareja en mano
             *                                Pareja como mejor mano
             *                                y todas las combinaciones posibles en mesa*/

            if (valorMano == 2) {
                if (valorManoComun == 1){
                    ponderacion = (parMano * parMano * 2 * valorPareja1 * valorPareja1 * valorMano);
                }

                else if (valorManoComun == 2){
                    ponderacion = (parMano * parMano * 2 * valorPareja1 * valorPareja1 * valorMano) / (valorPareja1Comun * valorPareja1Comun * valorManoComun);
                }

                else if (valorManoComun == 3) { /** Doble Pareja --> mesa*/
                    ponderacion = (parMano * parMano * 2 * valorMano * valorPareja1 * valorMano) / (valorPareja1Comun * valorPareja1Comun * valorPareja2Comun * valorPareja2Comun * valorManoComun);
                }

                else if (valorManoComun == 4){
                    ponderacion = (parMano * parMano * 2 * valorPareja1 * valorPareja1 * valorMano) / (valorTrioComun * valorTrioComun * valorTrioComun * valorManoComun);
                }

                else if (valorManoComun == 5) { /** Escalera --> mesa*/
                    ponderacion = (parMano * parMano * 2 * valorPareja1 * valorMano) /
                            ((valorEscaleraInicioComun + (valorEscaleraInicioComun + 1) + (valorEscaleraInicioComun + 2) + (valorEscaleraInicioComun + 3) + valorEscaleraFinalComun) * valorManoComun);
                }

                else if (valorManoComun == 6) { /** Color --> mesa*/
                    ponderacion = (parMano * parMano * 2 * valorPareja1 * valorMano) / (valorColorComun * 5 + valorManoComun);

                    if (corComun)
                        ponderacion = ponderacion + 4;
                    else if (picComun)
                        ponderacion = ponderacion + 3;
                    else if (diamComun)
                        ponderacion = ponderacion + 2;
                    else if (trebComun)
                        ponderacion = ponderacion + 1;
                }

                else if (valorManoComun == 7) { /** Full --> mesa*/
                    ponderacion = (parMano * parMano * 2 * valorPareja1 * valorMano) / (valorFullComun[0] * valorFullComun[0] * valorFullComun[1] * valorFullComun[1] * valorManoComun);
                }

                else if (valorManoComun == 8) { /** Poker --> mesa*/
                    ponderacion = (parMano * parMano * 2 * valorPareja1 * valorMano) / (valorPokerComun * valorPokerComun * valorPokerComun * valorPokerComun * valorManoComun);
                }

                else if (valorManoComun == 9) { /** Escalera color --> mesa*/
                    ponderacion = (parMano * parMano * 2 * valorPareja1 * valorMano) /
                            ((valorEscaleraInicioComun + (valorEscaleraInicioComun + 1) + (valorEscaleraInicioComun + 2) + (valorEscaleraInicioComun + 3) + valorEscaleraFinalComun) * valorManoComun);

                    if (corComun)
                        ponderacion = ponderacion + 4;
                    else if (picComun)
                        ponderacion = ponderacion + 3;
                    else if (diamComun)
                        ponderacion = ponderacion + 2;
                    else if (trebComun)
                        ponderacion = ponderacion + 1;
                }
            }
            /******************************************* HASTA AQUI PAREJA MEJOR MANO *******************************************************/

            /** Ahora tenemos como MEJOR MANO una DOBLE PAREJA*/
            else if (valorMano == 3) {
                if (valorManoComun == 1){
                    ponderacion = (parMano * parMano * 2 * valorPareja1 * valorPareja1 * valorPareja2 * valorPareja2 * valorMano);
                }

                else if (valorManoComun == 2){
                    ponderacion = (parMano * parMano * 2 * valorPareja1 * valorPareja1 * valorPareja2 * valorPareja2 * valorMano) / (valorPareja1Comun * valorPareja1Comun * valorManoComun);
                }

                else if (valorManoComun == 3) { /** Doble Pareja --> mesa*/
                    ponderacion = (parMano * parMano * 2 * valorPareja1 * valorPareja1 * valorPareja2 * valorPareja2 * valorMano) / (valorPareja1Comun * valorPareja1Comun * valorPareja2Comun * valorPareja2Comun * valorManoComun);
                }

                else if (valorManoComun == 4){
                    ponderacion = (parMano * parMano * 2 * valorPareja1 * valorPareja1 * valorPareja2 * valorPareja2 * valorMano) / (valorTrioComun * valorTrioComun * valorTrioComun * valorManoComun);
                }

                else if (valorManoComun == 5) { /** Escalera --> mesa*/
                    ponderacion = (parMano * parMano * 2 * valorPareja1 * valorPareja1 * valorPareja2 * valorPareja2 * valorMano) /
                            ((valorEscaleraInicioComun + (valorEscaleraInicioComun + 1) + (valorEscaleraInicioComun + 2) + (valorEscaleraInicioComun + 3) + valorEscaleraFinalComun) * valorManoComun);
                }

                else if (valorManoComun == 6) { /** Color --> mesa*/
                    ponderacion = (parMano * parMano * 2 * valorPareja1 * valorPareja1 * valorPareja2 * valorPareja2 * valorMano) / (valorColorComun * 5 + valorManoComun);

                    if (corComun)
                        ponderacion = ponderacion + 4;
                    else if (picComun)
                        ponderacion = ponderacion + 3;
                    else if (diamComun)
                        ponderacion = ponderacion + 2;
                    else if (trebComun)
                        ponderacion = ponderacion + 1;
                }

                else if (valorManoComun == 7) { /** Full --> mesa*/
                    ponderacion = (parMano * parMano * 2 * valorPareja1 * valorPareja1 * valorPareja2 * valorPareja2 * valorMano) / (valorFullComun[0] * valorFullComun[0] * valorFullComun[1] * valorFullComun[1] * valorManoComun);
                }

                else if (valorManoComun == 8) { /** Poker --> mesa*/
                    ponderacion = (parMano * parMano * 2 * valorPareja1 * valorPareja1 * valorPareja2 * valorPareja2 * valorMano) / (valorPokerComun * valorPokerComun * valorPokerComun * valorPokerComun * valorManoComun);
                }

                else if (valorManoComun == 9) { /** Escalera color --> mesa*/
                    ponderacion = (parMano * parMano * 2 * valorPareja1 * valorPareja1 * valorPareja2 * valorPareja2 * valorMano) /
                            ((valorEscaleraInicioComun + (valorEscaleraInicioComun + 1) + (valorEscaleraInicioComun + 2) + (valorEscaleraInicioComun + 3) + valorEscaleraFinalComun) * valorManoComun);

                    if (corComun)
                        ponderacion = ponderacion + 4;
                    else if (picComun)
                        ponderacion = ponderacion + 3;
                    else if (diamComun)
                        ponderacion = ponderacion + 2;
                    else if (trebComun)
                        ponderacion = ponderacion + 1;
                }
            }
            /******************************************* HASTA AQUI DOBLE PAREJA MEJOR MANO *******************************************************/

            else if (valorMano == 4) {
                /** Los siguientes casos son con: Carta alta en mano
                 *                                Trio como mejor mano
                 *                                y todas las combinaciones posibles en mesa */
                if (valorManoComun == 1){
                    ponderacion = (parMano * parMano * 2 * valorTrio * valorTrio * valorTrio * valorMano);
                }

                else if (valorManoComun == 2){
                    ponderacion = (parMano * parMano * 2 * valorTrio * valorTrio * valorTrio * valorMano) / (valorPareja1Comun * valorPareja1Comun * valorManoComun);
                }

                else if (valorManoComun == 3) { /** Doble Pareja --> mesa*/
                    ponderacion = (parMano * parMano * 2 * valorTrio * valorTrio * valorTrio * valorMano) / (valorPareja1Comun * valorPareja1Comun * valorPareja2Comun * valorPareja2Comun * valorManoComun);
                }

                else if (valorManoComun == 4){
                    ponderacion = (parMano * parMano * 2 * valorTrio * valorTrio * valorTrio * valorMano) / (valorTrioComun * valorTrioComun * valorTrioComun * valorManoComun);
                }

                else if (valorManoComun == 5) { /** Escalera --> mesa*/
                    ponderacion = (parMano * parMano * 2 * valorTrio * valorTrio * valorTrio * valorMano) /
                            ((valorEscaleraInicioComun + (valorEscaleraInicioComun + 1) + (valorEscaleraInicioComun + 2) + (valorEscaleraInicioComun + 3) + valorEscaleraFinalComun) * valorManoComun);
                }

                else if (valorManoComun == 6) { /** Color --> mesa*/
                    ponderacion = (parMano * parMano * 2 * valorTrio * valorTrio * valorTrio * valorMano) / (valorColorComun * 5 + valorManoComun);

                    if (corComun)
                        ponderacion = ponderacion + 4;
                    else if (picComun)
                        ponderacion = ponderacion + 3;
                    else if (diamComun)
                        ponderacion = ponderacion + 2;
                    else if (trebComun)
                        ponderacion = ponderacion + 1;
                }

                else if (valorManoComun == 7) { /** Full --> mesa*/
                    ponderacion = (parMano * parMano * 2 * valorTrio * valorTrio * valorTrio * valorMano) / (valorFullComun[0] * valorFullComun[0] * valorFullComun[1] * valorFullComun[1] * valorManoComun);
                }

                else if (valorManoComun == 8) { /** Poker --> mesa*/
                    ponderacion = (parMano * parMano * 2 * valorTrio * valorTrio * valorTrio * valorMano) / (valorPokerComun * valorPokerComun * valorPokerComun * valorPokerComun * valorManoComun);
                }

                else if (valorManoComun == 9) { /** Escalera color --> mesa*/
                    ponderacion = (parMano * parMano * 2 * valorTrio * valorTrio * valorTrio * valorMano) /
                            ((valorEscaleraInicioComun + (valorEscaleraInicioComun + 1) + (valorEscaleraInicioComun + 2) + (valorEscaleraInicioComun + 3) + valorEscaleraFinalComun) * valorManoComun);

                    if (corComun)
                        ponderacion = ponderacion + 4;
                    else if (picComun)
                        ponderacion = ponderacion + 3;
                    else if (diamComun)
                        ponderacion = ponderacion + 2;
                    else if (trebComun)
                        ponderacion = ponderacion + 1;
                }
            }
            /******************************************* HASTA AQUI TRIO MEJOR MANO *******************************************************/
            else if (valorMano == 5) {
                /** Los siguientes casos son con: Carta alta en mano
                 *                                Escalera como mejor mano
                 *                                y todas las combinaciones posibles en mesa */
                if (valorManoComun == 1){
                    ponderacion = (parMano * parMano * 2 *(valorEscaleraInicio + (valorEscaleraInicio + 1) + (valorEscaleraInicio + 2) + (valorEscaleraInicio + 3) + valorEscaleraFinal) * valorMano);
                }

                else if (valorManoComun == 2){
                    ponderacion = (parMano * parMano * 2 *(valorEscaleraInicio + (valorEscaleraInicio + 1) + (valorEscaleraInicio + 2) + (valorEscaleraInicio + 3) + valorEscaleraFinal) * valorMano) /
                            (valorPareja1Comun * valorPareja1Comun * valorManoComun);
                }

                else if (valorManoComun == 3) { /** Doble Pareja --> mesa*/
                    ponderacion = (parMano * parMano * 2 *(valorEscaleraInicio + (valorEscaleraInicio + 1) + (valorEscaleraInicio + 2) + (valorEscaleraInicio + 3) + valorEscaleraFinal) * valorMano) /
                            (valorPareja1Comun * valorPareja1Comun * valorPareja2Comun * valorPareja2Comun * valorManoComun);
                }

                else if (valorManoComun == 4){
                    ponderacion = (parMano * parMano * 2 *(valorEscaleraInicio + (valorEscaleraInicio + 1) + (valorEscaleraInicio + 2) + (valorEscaleraInicio + 3) + valorEscaleraFinal) * valorMano) /
                            (valorTrioComun * valorTrioComun * valorTrioComun * valorManoComun);
                }

                else if (valorManoComun == 5) { /** Escalera --> mesa*/
                    ponderacion = (parMano * parMano * 2 *(valorEscaleraInicio + (valorEscaleraInicio + 1) + (valorEscaleraInicio + 2) + (valorEscaleraInicio + 3) + valorEscaleraFinal) * valorMano) /
                            ((valorEscaleraInicioComun + (valorEscaleraInicioComun + 1) + (valorEscaleraInicioComun + 2) + (valorEscaleraInicioComun + 3) + valorEscaleraFinalComun) * valorManoComun);
                }

                else if (valorManoComun == 6) { /** Color --> mesa*/
                    ponderacion = (parMano * parMano * 2 *(valorEscaleraInicio + (valorEscaleraInicio + 1) + (valorEscaleraInicio + 2) + (valorEscaleraInicio + 3) + valorEscaleraFinal) * valorMano) /
                            (valorColorComun * 5 + valorManoComun);

                    if (corComun)
                        ponderacion = ponderacion + 4;
                    else if (picComun)
                        ponderacion = ponderacion + 3;
                    else if (diamComun)
                        ponderacion = ponderacion + 2;
                    else if (trebComun)
                        ponderacion = ponderacion + 1;
                }

                else if (valorManoComun == 7) { /** Full --> mesa*/
                    ponderacion = (parMano * parMano * 2 *(valorEscaleraInicio + (valorEscaleraInicio + 1) + (valorEscaleraInicio + 2) + (valorEscaleraInicio + 3) + valorEscaleraFinal) * valorMano) /
                            (valorFullComun[0] * valorFullComun[0] * valorFullComun[1] * valorFullComun[1] * valorManoComun);
                }

                else if (valorManoComun == 8) { /** Poker --> mesa*/
                    ponderacion = (parMano * parMano * 2 *(valorEscaleraInicio + (valorEscaleraInicio + 1) + (valorEscaleraInicio + 2) + (valorEscaleraInicio + 3) + valorEscaleraFinal) * valorMano) /
                            (valorPokerComun * valorPokerComun * valorPokerComun * valorPokerComun * valorManoComun);
                }

                else if (valorManoComun == 9) { /** Escalera color --> mesa*/
                    ponderacion = (parMano * parMano * 2 *(valorEscaleraInicio + (valorEscaleraInicio + 1) + (valorEscaleraInicio + 2) + (valorEscaleraInicio + 3) + valorEscaleraFinal) * valorMano) /
                            ((valorEscaleraInicioComun + (valorEscaleraInicioComun + 1) + (valorEscaleraInicioComun + 2) + (valorEscaleraInicioComun + 3) + valorEscaleraFinalComun) * valorManoComun);

                    if (corComun)
                        ponderacion = ponderacion + 4;
                    else if (picComun)
                        ponderacion = ponderacion + 3;
                    else if (diamComun)
                        ponderacion = ponderacion + 2;
                    else if (trebComun)
                        ponderacion = ponderacion + 1;
                }
            }
            /******************************************* HASTA AQUI ESCALERA MEJOR MANO *******************************************************/
            else if (valorMano == 6) {
                /** Los siguientes casos son con: Carta alta en mano
                 *                                Color como mejor mano
                 *                                y todas las combinaciones posibles en mesa */
                if (valorManoComun == 1){
                    ponderacion = (parMano * parMano * 2 *(valorEscaleraInicio + (valorEscaleraInicio + 1) + (valorEscaleraInicio + 2) + (valorEscaleraInicio + 3) + valorEscaleraFinal) * valorMano);
                }

                else if (valorManoComun == 2){
                    ponderacion = (parMano * parMano * 2 *(valorEscaleraInicio + (valorEscaleraInicio + 1) + (valorEscaleraInicio + 2) + (valorEscaleraInicio + 3) + valorEscaleraFinal) * valorMano) /
                            (valorPareja1Comun * valorPareja1Comun * valorManoComun);
                }

                else if (valorManoComun == 3) { /** Doble Pareja --> mesa*/
                    ponderacion = (parMano * parMano * 2 *(valorEscaleraInicio + (valorEscaleraInicio + 1) + (valorEscaleraInicio + 2) + (valorEscaleraInicio + 3) + valorEscaleraFinal) * valorMano) /
                            (valorPareja1Comun * valorPareja1Comun * valorPareja2Comun * valorPareja2Comun * valorManoComun);
                }

                else if (valorManoComun == 4){
                    ponderacion = (parMano * parMano * 2 *(valorEscaleraInicio + (valorEscaleraInicio + 1) + (valorEscaleraInicio + 2) + (valorEscaleraInicio + 3) + valorEscaleraFinal) * valorMano) /
                            (valorTrioComun * valorTrioComun * valorTrioComun * valorManoComun);
                }

                else if (valorManoComun == 5) { /** Escalera --> mesa*/
                    ponderacion = (parMano * parMano * 2 *(valorEscaleraInicio + (valorEscaleraInicio + 1) + (valorEscaleraInicio + 2) + (valorEscaleraInicio + 3) + valorEscaleraFinal) * valorMano) /
                            ((valorEscaleraInicioComun + (valorEscaleraInicioComun + 1) + (valorEscaleraInicioComun + 2) + (valorEscaleraInicioComun + 3) + valorEscaleraFinalComun) * valorManoComun);
                }

                else if (valorManoComun == 6) { /** Color --> mesa*/
                    ponderacion = (parMano * parMano * 2 *(valorEscaleraInicio + (valorEscaleraInicio + 1) + (valorEscaleraInicio + 2) + (valorEscaleraInicio + 3) + valorEscaleraFinal) * valorMano) /
                            (valorColorComun * 5 + valorManoComun);

                    if (corComun)
                        ponderacion = ponderacion + 4;
                    else if (picComun)
                        ponderacion = ponderacion + 3;
                    else if (diamComun)
                        ponderacion = ponderacion + 2;
                    else if (trebComun)
                        ponderacion = ponderacion + 1;
                }

                else if (valorManoComun == 7) { /** Full --> mesa*/
                    ponderacion = (parMano * parMano * 2 *(valorEscaleraInicio + (valorEscaleraInicio + 1) + (valorEscaleraInicio + 2) + (valorEscaleraInicio + 3) + valorEscaleraFinal) * valorMano) /
                            (valorFullComun[0] * valorFullComun[0] * valorFullComun[1] * valorFullComun[1] * valorManoComun);
                }

                else if (valorManoComun == 8) { /** Poker --> mesa*/
                    ponderacion = (parMano * parMano * 2 *(valorEscaleraInicio + (valorEscaleraInicio + 1) + (valorEscaleraInicio + 2) + (valorEscaleraInicio + 3) + valorEscaleraFinal) * valorMano) /
                            (valorPokerComun * valorPokerComun * valorPokerComun * valorPokerComun * valorManoComun);
                }

                else if (valorManoComun == 9) { /** Escalera color --> mesa*/
                    ponderacion = (parMano * parMano * 2 *(valorEscaleraInicio + (valorEscaleraInicio + 1) + (valorEscaleraInicio + 2) + (valorEscaleraInicio + 3) + valorEscaleraFinal) * valorMano) /
                            ((valorEscaleraInicioComun + (valorEscaleraInicioComun + 1) + (valorEscaleraInicioComun + 2) + (valorEscaleraInicioComun + 3) + valorEscaleraFinalComun) * valorManoComun);

                    if (corComun)
                        ponderacion = ponderacion + 4;
                    else if (picComun)
                        ponderacion = ponderacion + 3;
                    else if (diamComun)
                        ponderacion = ponderacion + 2;
                    else if (trebComun)
                        ponderacion = ponderacion + 1;
                }

            }
            /******************************************* HASTA AQUI COLOR MEJOR MANO *******************************************************/
            else if (valorMano == 7) {
                /** Los siguientes casos son con: Carta alta en mano
                 *                                Full como mejor mano
                 *                                y todas las combinaciones posibles en mesa */
                if (valorManoComun == 1){
                    ponderacion = (parMano * parMano * 2 * valorFull[0] * valorFull[0] * valorFull[1] * valorFull[1] * valorMano);
                }

                else if (valorManoComun == 2){
                    ponderacion = (parMano * parMano * 2 * valorFull[0] * valorFull[0] * valorFull[1] * valorFull[1] * valorMano) /
                            (valorPareja1Comun * valorPareja1Comun * valorManoComun);
                }

                else if (valorManoComun == 3) { /** Doble Pareja --> mesa*/
                    ponderacion = (parMano * parMano * 2 * valorFull[0] * valorFull[0] * valorFull[1] * valorFull[1] * valorMano) /
                            (valorPareja1Comun * valorPareja1Comun * valorPareja2Comun * valorPareja2Comun * valorManoComun);
                }

                else if (valorManoComun == 4){
                    ponderacion = (parMano * parMano * 2 * valorFull[0] * valorFull[0] * valorFull[1] * valorFull[1] * valorMano) /
                            (valorTrioComun * valorTrioComun * valorTrioComun * valorManoComun);
                }

                else if (valorManoComun == 5) { /** Escalera --> mesa*/
                    ponderacion = (parMano * parMano * 2 * valorFull[0] * valorFull[0] * valorFull[1] * valorFull[1] * valorMano) /
                            ((valorEscaleraInicioComun + (valorEscaleraInicioComun + 1) + (valorEscaleraInicioComun + 2) + (valorEscaleraInicioComun + 3) + valorEscaleraFinalComun) * valorManoComun);
                }

                else if (valorManoComun == 6) { /** Color --> mesa*/
                    ponderacion = (parMano * parMano * 2 * valorFull[0] * valorFull[0] * valorFull[1] * valorFull[1] * valorMano) /
                            (valorColorComun * 5 + valorManoComun);

                    if (corComun)
                        ponderacion = ponderacion + 4;
                    else if (picComun)
                        ponderacion = ponderacion + 3;
                    else if (diamComun)
                        ponderacion = ponderacion + 2;
                    else if (trebComun)
                        ponderacion = ponderacion + 1;
                }

                else if (valorManoComun == 7) { /** Full --> mesa*/
                    ponderacion = (parMano * parMano * 2 * valorFull[0] * valorFull[0] * valorFull[1] * valorFull[1] * valorMano) /
                            (valorPareja1Comun * valorPareja1Comun * valorTrioComun * valorTrioComun * valorManoComun);
                }

                else if (valorManoComun == 8) { /** Poker --> mesa*/
                    ponderacion = (parMano * parMano * 2 * valorFull[0] * valorFull[0] * valorFull[1] * valorFull[1] * valorMano) /
                            (valorPokerComun * valorPokerComun * valorPokerComun * valorPokerComun * valorManoComun);
                }

                else if (valorManoComun == 9) { /** Escalera color --> mesa*/
                    ponderacion = (parMano * parMano * 2 * valorFull[0] * valorFull[0] * valorFull[1] * valorFull[1] * valorMano) /
                            ((valorEscaleraInicioComun + (valorEscaleraInicioComun + 1) + (valorEscaleraInicioComun + 2) + (valorEscaleraInicioComun + 3) + valorEscaleraFinalComun) * valorManoComun);

                    if (corComun)
                        ponderacion = ponderacion + 4;
                    else if (picComun)
                        ponderacion = ponderacion + 3;
                    else if (diamComun)
                        ponderacion = ponderacion + 2;
                    else if (trebComun)
                        ponderacion = ponderacion + 1;
                }

            }
            /******************************************* HASTA AQUI FULL MEJOR MANO *******************************************************/
            else if (valorMano == 8) {
                /** Los siguientes casos son con: Carta alta en mano
                 *                                Poker como mejor mano
                 *                                y todas las combinaciones posibles en mesa */
                if (valorManoComun == 1){
                    ponderacion = (parMano * parMano * 2 * valorPoker * valorPoker * valorPoker * valorPoker * valorMano);
                }

                else if (valorManoComun == 2){
                    ponderacion = (parMano * parMano * 2 * valorPoker * valorPoker * valorPoker * valorPoker * valorMano) /
                            (valorPareja1Comun * valorPareja1Comun * valorManoComun);
                }

                else if (valorManoComun == 3) { /** Doble Pareja --> mesa*/
                    ponderacion = (parMano * parMano * 2 * valorPoker * valorPoker * valorPoker * valorPoker * valorMano) /
                            (valorPareja1Comun * valorPareja1Comun * valorPareja2Comun * valorPareja2Comun * valorManoComun);
                }

                else if (valorManoComun == 4){
                    ponderacion = (parMano * parMano * 2 * valorPoker * valorPoker * valorPoker * valorPoker * valorMano) /
                            (valorTrioComun * valorTrioComun * valorTrioComun * valorManoComun);
                }

                else if (valorManoComun == 5) { /** Escalera --> mesa*/
                    ponderacion = (parMano * parMano * 2 * valorPoker * valorPoker * valorPoker * valorPoker * valorMano) /
                            ((valorEscaleraInicioComun + (valorEscaleraInicioComun + 1) + (valorEscaleraInicioComun + 2) + (valorEscaleraInicioComun + 3) + valorEscaleraFinalComun) * valorManoComun);
                }

                else if (valorManoComun == 6) { /** Color --> mesa*/
                    ponderacion = (parMano * parMano * 2 * valorPoker * valorPoker * valorPoker * valorPoker * valorMano) /
                            (valorColorComun * 5 + valorManoComun);

                    if (corComun)
                        ponderacion = ponderacion + 4;
                    else if (picComun)
                        ponderacion = ponderacion + 3;
                    else if (diamComun)
                        ponderacion = ponderacion + 2;
                    else if (trebComun)
                        ponderacion = ponderacion + 1;
                }

                else if (valorManoComun == 7) { /** Full --> mesa*/
                    ponderacion = (parMano * parMano * 2 * valorPoker * valorPoker * valorPoker * valorPoker * valorMano) /
                            (valorPareja1Comun * valorPareja1Comun * valorTrioComun * valorTrioComun * valorManoComun);
                }

                else if (valorManoComun == 8) { /** Poker --> mesa*/
                    ponderacion = (parMano * parMano * 2 * valorPoker * valorPoker * valorPoker * valorPoker * valorMano) /
                            (valorPokerComun * valorPokerComun * valorPokerComun * valorPokerComun * valorManoComun);
                }

                else if (valorManoComun == 9) { /** Escalera color --> mesa*/
                    ponderacion = (parMano * parMano * 2 * valorPoker * valorPoker * valorPoker * valorPoker * valorMano) /
                            ((valorEscaleraInicioComun + (valorEscaleraInicioComun + 1) + (valorEscaleraInicioComun + 2) + (valorEscaleraInicioComun + 3) + valorEscaleraFinalComun) * valorManoComun);

                    if (corComun)
                        ponderacion = ponderacion + 4;
                    else if (picComun)
                        ponderacion = ponderacion + 3;
                    else if (diamComun)
                        ponderacion = ponderacion + 2;
                    else if (trebComun)
                        ponderacion = ponderacion + 1;
                }
            }
            /******************************************* HASTA AQUI POKER MEJOR MANO *******************************************************/
            else if (valorMano == 9) {
                /** Los siguientes casos son con: Carta alta en mano
                 *                                Escalera color como mejor mano
                 *                                y todas las combinaciones posibles en mesa */
                if (valorManoComun == 1){
                    ponderacion = (parMano * parMano * 2 * (valorEscaleraInicio + (valorEscaleraInicio + 1) + (valorEscaleraInicio + 2) + (valorEscaleraInicio + 3) + valorEscaleraFinal) * valorMano);

                    if (cor)
                        ponderacion = ponderacion + 4;
                    else if (pic)
                        ponderacion = ponderacion + 3;
                    else if (diam)
                        ponderacion = ponderacion + 2;
                    else if (treb)
                        ponderacion = ponderacion + 1;
                }

                else if (valorManoComun == 2){
                    ponderacion = (parMano * parMano * 2 * (valorEscaleraInicio + (valorEscaleraInicio + 1) + (valorEscaleraInicio + 2) + (valorEscaleraInicio + 3) + valorEscaleraFinal) * valorMano) /
                            (valorPareja1Comun * valorPareja1Comun * valorManoComun);
                    if (cor)
                        ponderacion = ponderacion + 4;
                    else if (pic)
                        ponderacion = ponderacion + 3;
                    else if (diam)
                        ponderacion = ponderacion + 2;
                    else if (treb)
                        ponderacion = ponderacion + 1;
                }

                else if (valorManoComun == 3) { /** Doble Pareja --> mesa*/
                    ponderacion = (parMano * parMano * 2 * (valorEscaleraInicio + (valorEscaleraInicio + 1) + (valorEscaleraInicio + 2) + (valorEscaleraInicio + 3) + valorEscaleraFinal) * valorMano) /
                            (valorPareja1Comun * valorPareja1Comun * valorPareja2Comun * valorPareja2Comun * valorManoComun);
                    if (cor)
                        ponderacion = ponderacion + 4;
                    else if (pic)
                        ponderacion = ponderacion + 3;
                    else if (diam)
                        ponderacion = ponderacion + 2;
                    else if (treb)
                        ponderacion = ponderacion + 1;
                }

                else if (valorManoComun == 4){
                    ponderacion = (parMano * parMano * 2 * (valorEscaleraInicio + (valorEscaleraInicio + 1) + (valorEscaleraInicio + 2) + (valorEscaleraInicio + 3) + valorEscaleraFinal) * valorMano) /
                            (valorTrioComun * valorTrioComun * valorTrioComun * valorManoComun);
                    if (cor)
                        ponderacion = ponderacion + 4;
                    else if (pic)
                        ponderacion = ponderacion + 3;
                    else if (diam)
                        ponderacion = ponderacion + 2;
                    else if (treb)
                        ponderacion = ponderacion + 1;
                }

                else if (valorManoComun == 5) { /** Escalera --> mesa*/
                    ponderacion = (parMano * parMano * 2 * (valorEscaleraInicio + (valorEscaleraInicio + 1) + (valorEscaleraInicio + 2) + (valorEscaleraInicio + 3) + valorEscaleraFinal) * valorMano) /
                            ((valorEscaleraInicioComun + (valorEscaleraInicioComun + 1) + (valorEscaleraInicioComun + 2) + (valorEscaleraInicioComun + 3) + valorEscaleraFinalComun) * valorManoComun);
                    if (cor)
                        ponderacion = ponderacion + 4;
                    else if (pic)
                        ponderacion = ponderacion + 3;
                    else if (diam)
                        ponderacion = ponderacion + 2;
                    else if (treb)
                        ponderacion = ponderacion + 1;
                }

                else if (valorManoComun == 6) { /** Color --> mesa*/
                    ponderacion = (parMano * parMano * 2 * (valorEscaleraInicio + (valorEscaleraInicio + 1) + (valorEscaleraInicio + 2) + (valorEscaleraInicio + 3) + valorEscaleraFinal) * valorMano) /
                            (valorColorComun * 5 + valorManoComun);

                    if (corComun)
                        ponderacion = ponderacion + 4;
                    else if (picComun)
                        ponderacion = ponderacion + 3;
                    else if (diamComun)
                        ponderacion = ponderacion + 2;
                    else if (trebComun)
                        ponderacion = ponderacion + 1;
                }

                else if (valorManoComun == 7) { /** Full --> mesa*/
                    ponderacion = (parMano * parMano * 2 * (valorEscaleraInicio + (valorEscaleraInicio + 1) + (valorEscaleraInicio + 2) + (valorEscaleraInicio + 3) + valorEscaleraFinal) * valorMano) /
                            (valorPareja1Comun * valorPareja1Comun * valorTrioComun * valorTrioComun * valorManoComun);
                    if (cor)
                        ponderacion = ponderacion + 4;
                    else if (pic)
                        ponderacion = ponderacion + 3;
                    else if (diam)
                        ponderacion = ponderacion + 2;
                    else if (treb)
                        ponderacion = ponderacion + 1;
                }

                else if (valorManoComun == 8) { /** Poker --> mesa*/
                    ponderacion = (parMano * parMano * 2 * (valorEscaleraInicio + (valorEscaleraInicio + 1) + (valorEscaleraInicio + 2) + (valorEscaleraInicio + 3) + valorEscaleraFinal) * valorMano) /
                            (valorPokerComun * valorPokerComun * valorPokerComun * valorPokerComun * valorManoComun);
                    if (cor)
                        ponderacion = ponderacion + 4;
                    else if (pic)
                        ponderacion = ponderacion + 3;
                    else if (diam)
                        ponderacion = ponderacion + 2;
                    else if (treb)
                        ponderacion = ponderacion + 1;
                }

                else if (valorManoComun == 9) { /** Escalera color --> mesa*/
                    ponderacion = (parMano * parMano * 2 * (valorEscaleraInicio + (valorEscaleraInicio + 1) + (valorEscaleraInicio + 2) + (valorEscaleraInicio + 3) + valorEscaleraFinal) * valorMano) /
                            ((valorEscaleraInicioComun + (valorEscaleraInicioComun + 1) + (valorEscaleraInicioComun + 2) + (valorEscaleraInicioComun + 3) + valorEscaleraFinalComun) * valorManoComun);

                    if (corComun)
                        ponderacion = ponderacion + 4;
                    else if (picComun)
                        ponderacion = ponderacion + 3;
                    else if (diamComun)
                        ponderacion = ponderacion + 2;
                    else if (trebComun)
                        ponderacion = ponderacion + 1;
                }
            }
            /******************************************* HASTA AQUI ESCALERA DE COLOR MEJOR MANO *********************************************/
        }

        return ponderacion;
    }

    public int tomarDecision(){    /** Tomare cada decision como un entero: 0 pasar/noIr, la cantidad a apostar en caso de igualar o subir*/

        /** Una buena opcion es crear un arraylist y añadir las cartas actuales y mandarlo al borroso para
         ver que podemos hacer con la mejor mano que tenemos*/

            /***********************************************
             *          COMIENZO CONTROLADOR BORROSO       *
             ***********************************************/
        FIS fis = new FIS();
        FunctionBlock fb = new FunctionBlock(fis);
        fis.addFunctionBlock("Decision", fb);

        /**  VAR_INPUT
         *    fichas : REAL;
         *    fase : INTEGER; -> Singleton
         *    mano : REAL;
         *  END_VAR */

        Variable fichas = new Variable("fichas"); // Cantidad de fichas del jugador
        Variable fase = new Variable("fase");     // Fase de la mano (preflop, flop...)
        Variable mano = new Variable("mano");     // "Calidad" de la mano: muy mala, mala...

        /** VAR_OUTPUT
         *  decision : REAL;
         *  END_VAR */

        Variable decision = new Variable("decision");

        /**
         FUZZIFY fichas
         TERM pocas := gauss mediaPocas desvPocas;
         TERM medias := gauss mediaMedias desvMedias;
         TERM muchas := gauss mediaMuchas desvMuchas;
         END_FUZZIFY */

        /*
            Valores de medias y desviaciones tipicas se calculan en mesa cada vez que se elimina un jugador. A la espera
            de ver la implementacion para asignar los nombres de parametros correctos
         */
        MembershipFunction mPocas = new MembershipFunctionGaussian(new Value(mediaPocas), new Value(desvPocas));
        MembershipFunction mMedias = new MembershipFunctionGaussian(new Value(mediaMedias), new Value(desvMedias));
        MembershipFunction mMuchas = new MembershipFunctionGaussian(new Value(mediaMuchas), new Value(desvMuchas));
        LinguisticTerm ltPocas = new LinguisticTerm("pocas", mPocas);
        LinguisticTerm ltMedias = new LinguisticTerm("medias", mMedias);
        LinguisticTerm ltMuchas = new LinguisticTerm("muchas", mMuchas);

        fichas.add(ltPocas);
        fichas.add(ltMedias);
        fichas.add(ltMuchas);

        /**
         FUZZIFY fase
         TERM preflop := 1;
         TERM flop := 2;
         TERM TR := 3; //TR : Turn River
         END_FUZZIFY */

        MembershipFunction mPreflop = new MembershipFunctionSingleton(new Value(1));
        MembershipFunction mFlop = new MembershipFunctionSingleton(new Value(2));
        MembershipFunction mTR = new MembershipFunctionSingleton(new Value(3));

        LinguisticTerm ltPreflop = new LinguisticTerm("preflop", mPreflop);
        LinguisticTerm ltFlop = new LinguisticTerm("flop", mFlop);
        LinguisticTerm ltTR = new LinguisticTerm("turn/river", mTR);

        fase.add(ltPreflop);
        fase.add(ltFlop);
        fase.add(ltTR);

        /**
         FUZZIFY mano
         TERM muyMala := gauss
         TERM mala := gauss
         TERM buena := gauss
         TERM muyBuena := gauss
         END_FUZZIFY */

        /*
            TODO asignar valores definicion de funciones gaussianas, teniendo en cuenta ponderación cartas
         */
        MembershipFunction mMuyMala = new MembershipFunctionGaussian(new Value(), new Value());
        MembershipFunction mMala = new MembershipFunctionGaussian(new Value(), new Value());
        MembershipFunction mBuena = new MembershipFunctionGaussian(new Value(), new Value());
        MembershipFunction mMuyBuena = new MembershipFunctionGaussian(new Value(), new Value());

        LinguisticTerm ltMuyMala = new LinguisticTerm("muyMala", mMuyMala);
        LinguisticTerm ltMala = new LinguisticTerm("mala", mMala);
        LinguisticTerm ltBuena = new LinguisticTerm("buena", mBuena);
        LinguisticTerm ltMuyBuena = new LinguisticTerm("muyBuena", mMuyBuena);

        mano.add(ltMuyMala);
        mano.add(ltMala);
        mano.add(ltBuena);
        mano.add(ltMuyBuena);

        /**  DEFUZZIFY decision
         TERM pasarNI := ;
         TERM igualar := ;
         TERM subir := ;
         METHOD : COG;
         DEFAULT := 0;
         END_DEFUZZIFY */

        /*
            TODO : definir forma de los terminos de decision (gauss, trapezoidal..?). En principio, Gauss.
            En principio, el metodo de defuzzyficacion será el de Centro de Gravedad, pues parece ser el más usado
         */

        MembershipFunction mPasarNI = new MembershipFunctionGaussian(new Value(), new Value());
        MembershipFunction mIgualar = new MembershipFunctionGaussian(new Value(), new Value());
        MembershipFunction mSubir = new MembershipFunctionGaussian(new Value(), new Value());

        LinguisticTerm ltpasarNI = new LinguisticTerm("pasar/noIr", mPasarNI);
        LinguisticTerm ltIgualar = new LinguisticTerm("igualar", mIgualar);
        LinguisticTerm ltSubir = new LinguisticTerm("subir", mSubir);

        decision.add(ltpasarNI);
        decision.add(ltIgualar);
        decision.add(ltSubir);

        /**
         * Bloque de reglas
         *
         * Se han omitido declaraciones repetidas de términos, a pesar de que en los ejemplos
         * si se mantenían repetidas. En las pruebas se comprobará si la omisión es correcta o no, por si acaso
         * está la implementación literal comentada en cada regla.
         *
         * En caso de que lo anterior funcione, quizá se podría probar a omitir también algunas RuleExpression,
         * que también se repiten aunque con menor frecuencia. Esto también podría dificultar la lectura del código
         * por lo que queda descartado por el momento.
         *
         * También se podría probar a declarar cada tipo de decision como RuleTerm y no pasarlo "a pelo" cada vez que
         * se hace un addConsequent, pero igualmente podría dificultar la lectura del código.
         */

        RuleBlock ruleBlock = new RuleBlock(fb);
        ruleBlock.setName("Bloque de reglas");
        ruleBlock.setRuleAccumulationMethod(new RuleAccumulationMethodMax());
        ruleBlock.setRuleActivationMethod(new RuleActivationMethodMin());

        /*
         RULE 1 : IF mano IS muyMala THEN decision IS pasar/noIr
          */

        Rule rule1 = new Rule("Regla1", ruleBlock);
        RuleTerm t1r1 = new RuleTerm(mano, "muyMala", false);
        rule1.addConsequent(decision, "pasar/noIr", false);
        ruleBlock.add(rule1);

        /*
         RULE 2 : IF mano IS muyBuena THEN decision IS subir
          */

        Rule rule2 = new Rule("Regla2", ruleBlock);
        RuleTerm t1r2 = new RuleTerm(mano, "muyBuena", false);
        rule2.addConsequent(decision, "subir", false);
        ruleBlock.add(rule2);

        /*
         RULE 3 : IF (fase IS preflop AND mano IS mala) AND fichas IS pocas THEN decision IS pasar/noIr
          */

        Rule rule3 = new Rule("Regla3", ruleBlock);
        RuleTerm rtFasePreflop = new RuleTerm(fase, "preflop", false);
        RuleTerm rtManoMala = new RuleTerm(mano, "mala", false);
        RuleTerm rtFichasPocas = new RuleTerm(fichas, "pocas", false);
        RuleExpression r3AntecedenteAnd1 = new RuleExpression(rtFasePreflop, rtManoMala, RuleConnectionMethodAndMin.get());
        RuleExpression r3AntecedenteAnd2 = new RuleExpression(r3AntecedenteAnd1, rtFichasPocas, RuleConnectionMethodAndMin.get());
        rule3.setAntecedents(r3AntecedenteAnd2);
        rule3.addConsequent(decision, "pasar/noIr", false);
        ruleBlock.add(rule3);

        /*
         RULE 4 : IF (fase IS preflop AND mano IS mala) AND (fichas IS medias OR fichas IS muchas) THEN decision IS igualar
          */

        Rule rule4 = new Rule("Regla4", ruleBlock);
        //RuleTerm t1r4 = new RuleTerm(fase, "preflop", false); En el ejemplo se declaran repetidas, pero a lo mejor no hace falta
        //RuleTerm t2r4 = new RuleTerm(mano, "mala", false);
        RuleTerm rtFichasMedias = new RuleTerm(fichas, "medias", false);
        RuleTerm rtFichasMuchas = new RuleTerm(fichas, "muchas", false);
        RuleExpression r4AntecedenteAnd1 = new RuleExpression(rtFasePreflop, rtManoMala, RuleConnectionMethodAndMin.get());
        RuleExpression r4AntecedenteOr1 = new RuleExpression(rtFichasMedias, rtFichasMuchas, RuleConnectionMethodOrMax.get());
        RuleExpression r4AntecedenteAnd2 = new RuleExpression(r4AntecedenteAnd1, r4AntecedenteOr1, RuleConnectionMethodAndMin.get());
        rule4.setAntecedents(r4AntecedenteAnd2);
        rule4.addConsequent(decision, "igualar", false);
        ruleBlock.add(rule4);

        /*
         RULE 5 : IF (fase IS preflop AND mano IS buena) AND fichas IS pocas THEN decision IS igualar
          */

        Rule rule5 = new Rule("Regla5", ruleBlock);
        //RuleTerm t1r5 = new RuleTerm(fase, "preflop", false);  En el ejemplo se declaran repetidas, pero a lo mejor no hace falta
        RuleTerm rtManoBuena = new RuleTerm(mano, "buena", false);
        //RuleTerm t3r5 = new RuleTerm(fichas, "pocas", false);
        RuleExpression r5AntecedenteAnd1 = new RuleExpression(rtFasePreflop, rtManoBuena, RuleConnectionMethodAndMin.get());
        RuleExpression r5AntecedenteAnd2 = new RuleExpression(r5AntecedenteAnd1, rtFichasPocas, RuleConnectionMethodAndMin.get());
        rule5.setAntecedents(r5AntecedenteAnd2);
        rule5.addConsequent(decision, "igualar", false);
        ruleBlock.add(rule5);

        /*
         RULE 6 : IF (fase IS preflop AND mano IS buena) AND (fichas IS medias OR fichas IS muchas) THEN decision IS subir
          */

        Rule rule6 = new Rule("Regla6", ruleBlock);
        //RuleTerm t1r6 = new RuleTerm(fase, "preflop", false);  En el ejemplo se declaran repetidas, pero a lo mejor no hace falta
        //RuleTerm t2r6 = new RuleTerm(mano, "buena", false);
        //RuleTerm t3r6 = new RuleTerm(fichas, "medias", false);
        //RuleTerm t4r6 = new RuleTerm(fichas, "muchas", false);
        RuleExpression r6AntecedenteAnd1 = new RuleExpression(rtFasePreflop, rtManoBuena, RuleConnectionMethodAndMin.get());
        RuleExpression r6AntecedenteOr1 = new RuleExpression(rtFichasMedias, rtFichasMuchas, RuleConnectionMethodOrMax.get());
        RuleExpression r6AntecedenteAnd2 = new RuleExpression(r6AntecedenteAnd1, r6AntecedenteOr1, RuleConnectionMethodAndMin.get());
        rule6.setAntecedents(r6AntecedenteAnd2);
        rule6.addConsequent(decision, "subir", false);
        ruleBlock.add(rule6);

        /*
         RULE 7 : IF (fase IS flop AND mano IS mala) AND (fichas IS pocas OR fichas IS medias) THEN decision IS pasar/noIr
          */

        Rule rule7 = new Rule("Regla7", ruleBlock);
        RuleTerm rtFaseFlop = new RuleTerm(fase, "flop", false);
        //RuleTerm t2r7 = new RuleTerm(mano, "mala", false);
        //RuleTerm t3r7 = new RuleTerm(fichas, "pocas", false);
        //RuleTerm t4r7 = new RuleTerm(fichas, "medias", false);
        RuleExpression r7AntecedenteAnd1 = new RuleExpression(rtFaseFlop, rtManoMala, RuleConnectionMethodAndMin.get());
        RuleExpression r7AntecedenteOr1 = new RuleExpression(rtFichasPocas, rtFichasMedias, RuleConnectionMethodOrMax.get());
        RuleExpression r7AntecedenteAnd2 = new RuleExpression(r7AntecedenteAnd1, r7AntecedenteOr1, RuleConnectionMethodAndMin.get());
        rule7.setAntecedents(r7AntecedenteAnd2);
        rule7.addConsequent(decision, "pasar/noIr", false);
        ruleBlock.add(rule7);

        /*
         RULE 8 : IF (fase IS flop AND mano IS mala) AND fichas IS muchas THEN decision IS igualar
          */

        Rule rule8 = new Rule("Regla8", ruleBlock);
        RuleExpression r8AntecedenteAnd1 = new RuleExpression(rtFaseFlop, rtManoMala, RuleConnectionMethodAndMin.get());
        RuleExpression r8AntecedenteAnd2 = new RuleExpression(r8AntecedenteAnd1, rtFichasMuchas, RuleConnectionMethodAndMin.get());
        rule8.setAntecedents(r8AntecedenteAnd2);
        rule8.addConsequent(decision, "igualar", false);
        ruleBlock.add(rule8);

        /*
         RULE 9 : IF fase IS turn/river AND mano IS mala THEN pasar/noIr
          */
        Rule rule9 = new Rule("Regla9", ruleBlock);
        RuleTerm rtFaseTR = new RuleTerm(fase, "turn/river", false);
        RuleExpression r9AntecedenteAnd1 = new RuleExpression(rtFaseTR, rtManoMala, RuleConnectionMethodAndMin.get());
        rule9.setAntecedents(r9AntecedenteAnd1);
        rule9.addConsequent(decision, "pasar/noIr", false);
        ruleBlock.add(rule9);

        /** RULE 10: IF fase IS turn/river AND (mano IS buena AND fichas IS pocas) THEN Igualar || SUBIR
         * Inicialmente, se habia contemplado que los posibles valores de decision eran No ir, Pasar y Apostar (igualar y subir).
         * Realmente, los que se comportan de la misma forma son No ir y Pasar, ya que contemplamos que intentara pasar y,
         * si no puede, no irá, lo que se controlara de forma externa al controlador.
         *
         * Por ello, esta útlima regla no se podra implementar, deberá aprender el jugador por sí solo lo que debe hacer
         */


        /*
         * Los pesos se obtienen de las 9 primeras posiciones de gen
         */
        rule1.setWeight(gen[0]);
        rule2.setWeight(gen[1]);
        rule3.setWeight(gen[2]);
        rule4.setWeight(gen[3]);
        rule5.setWeight(gen[4]);
        rule6.setWeight(gen[5]);
        rule7.setWeight(gen[6]);
        rule8.setWeight(gen[7]);
        rule9.setWeight(gen[8]);
        /*
            END_RULEBLOCK
         */
        pruebaBorroso(fis); //TODO eliminar esta declaración cuando hayan finalizado las pruebas del controlador

        /**
         * TODO especificar donde se van a guardar los valores de grado de soporte y peso de las reglas, etc
         */
        /**
         * TODO especificar valores de entrada al borroso, previos al evaluate
         */

        fis.evaluate();

        /**
         * TODO especificar cada cuantas generaciones (atributo numGeneracion de Main) hay que guardar en fichero el output del fis
         */

        /**
         * TODO establecer return
         */
    }

    /**
     * La única finalidad de la función pruebaBorroso es comprobar que las reglas y las variables han quedado bien
     * definidas.
     * @param fis
     */
    public void pruebaBorroso(FIS fis)
    {
        /**
         * TODO definir prueba del borroso
         */
    }
}
