package pokerSI;

import net.sourceforge.jFuzzyLogic.FIS;
import net.sourceforge.jFuzzyLogic.FunctionBlock;
import net.sourceforge.jFuzzyLogic.membership.MembershipFunction;
import net.sourceforge.jFuzzyLogic.membership.MembershipFunctionGaussian;
import net.sourceforge.jFuzzyLogic.membership.MembershipFunctionSingleton;
import net.sourceforge.jFuzzyLogic.membership.Value;
import net.sourceforge.jFuzzyLogic.rule.LinguisticTerm;
import net.sourceforge.jFuzzyLogic.rule.RuleBlock;
import net.sourceforge.jFuzzyLogic.rule.Variable;
import net.sourceforge.jFuzzyLogic.ruleAccumulationMethod.RuleAccumulationMethodMax;
import net.sourceforge.jFuzzyLogic.ruleActivationMethod.RuleActivationMethodMin;

import java.util.* ;
public class Jugador {

    /** Habría que añadir un atributo que indique que tipo de mano tiene el jugador y pasarsela a la mesa*/

    private int fichas;
    private int fichasGanadas;
    private int fichasApostadas;
    private int manosGanadas;
    private int manosJugadas;
    private int []gen; /* debera incluir en alguna de sus posiciones el valor de "agresividad"
                         que indicara el porcentaje de subida respecto a la apuesta minima, cuando corresponda */
    private float fitness;
    private int []identificacion;
    private Carta []cartasEnMano;
    private Carta []cartasComunes;
    private ArrayList<Carta> mejorMano;

    public Jugador(){       /** Constructor del pokerSI.Jugador sin parametros */
        this.fichas = 1000;
        this.fichasGanadas = 0;
        this.fichasApostadas = 0;
        this.manosGanadas = 0;
        this.manosJugadas = 0;
        this.fitness = 0;
        this.gen = new int[8];
        this.identificacion = new int[3];              /** Posicion 0: nºgeneracion // Posicion 1: nº mesa // Posicion 3: nº jugador*/
        this.cartasEnMano = new Carta[2] ;             /** Las dos cartas en mano las tomamos como enteros*/
        this.cartasComunes = new Carta[5];             /** Las 5 cartas comunes de la mesa para ver nuestra mejor mano*/
        this.mejorMano = new ArrayList<Carta>();       /** La mejor combinacion de cartas sobre la mesa*/
    }

    public Jugador(int []gen){ /** Contructor del pokerSI.Jugador con parametros */

        this.fichas = fichas;
        this.fichasGanadas = fichasGanadas;
        this.fichasApostadas = fichasApostadas;
        this.manosGanadas = manosGanadas;
        this.manosJugadas = manosJugadas;
        this.fitness = fitness;

        for(int i=0; i<3; i++)
            this.identificacion[i] = 0;
        for(int j=0; j<2; j++)
            this.cartasEnMano[j] = new Carta();
        for(int k=0; k<5; k++)
            this.cartasComunes[k] = new Carta();
        for(int v=0; v<8; v++)
            this.gen[v] = 0;
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

    public int[] getGen() {
        return gen;
    }

    public void setGen(int[] gen) {
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
        this.fichasApostadas = 0;   // guargamos el fitness de los jugadores de una generacion a otra
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

        ArrayList<Carta> manoProvisional = new ArrayList<Carta>();    /** Array creado para añadir las 7 cartas al borroso y que devuelva la mejor mano*/
        for(int i=0; i<2; i++)
            manoProvisional.add(cartasEnMano[i]);        /** Se añaden las cartas que se tenga actualmente en un arraylist unificado*/
        for(int j=0; j<5; j++)                           /** Para pasarselo a otro metodo y que evalue la mejor mano*/
            manoProvisional.add(cartasComunes[j]);

        int valorMejorMano = calcularMejorMano(manoProvisional);

        /** Ya tenemos el valor de nuestra mejor mano pero para apostar debemos hacer una consulta al sistema borroso
         *  y tomar una decision, de si apostar, pasar o irse, todo esto dependiendo de los jugadores que haya aun en la mesa
         *  cuantas fichas tengamos y sobre todo nuestra mano */

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

        /** Para ver si tenemos escalera de color */

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
                                        resul = 1;
                                    }else
                                        resul = 2; // Tenemos Pareja
                                }else
                                    resul = 3; // Tenemos Doble Pareja
                            }else
                                resul = 4; // Tenemos trio
                        }else
                            resul = 5; // Tenemos Escalera
                    }else
                        resul = 6; // Tenemos Color
                }else
                    resul = 7; // Tenemos Full
            }else
                resul = 8; // Tenemos Poker
        }else
            resul = 9; // Tenemos Escalera de color

        /** RESUL = 1 ---> CARTA ALTA
         *          2 ---> PAREJA
         *          3 ---> DOBLE PAREJA
         *          4 ---> TRIO
         *          5 ---> ESCALERA
         *          6 ---> COLOR
         *          7 ---> FULL
         *          8 ---> POKER
         *          9 ---> ESCALERA DE COLOR                   */

        return resul;
    }
    public void tomarDecision(){    /** Tomare cada decision como un entero, 0 no ir, 1 pasar, 2 apostar*/

        /** Una buena opcion es crear un arraylist y añadir las cartas actuales y mandarlo al borroso para
         ver que podemos hacer con la mejor mano que tenemos*/

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
            de ver la implementacion asignar los nombres de parametros correctos
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
        LinguisticTerm ltIgualar = new LinguisticTerm("mala", mIgualar);
        LinguisticTerm ltSubir = new LinguisticTerm("buena", mSubir);

        decision.add(ltpasarNI);
        decision.add(ltIgualar);
        decision.add(ltSubir);

        /**
         * Bloque de reglas
         * Como metodo de acumulacion se usara el MAX, y como activacion el MIN,
         * ya que están en los ejemplos tanto de clase como de los tutoriales oficiales,
         * habrá que investigar si usar otro método podría funcionarnos mejor.
         */

        RuleBlock ruleBlock = new RuleBlock(fb);
        ruleBlock.setName("Bloque de reglas");
        ruleBlock.setRuleAccumulationMethod(new RuleAccumulationMethodMax());
        ruleBlock.setRuleActivationMethod(new RuleActivationMethodMin());

        // RULE 1 : IF mano IS muyMala THEN decision IS pasar/noIr
        // RULE 2 : IF mano IS muyBuena THEN decision IS subir
        // RULE 3 : IF fase IS preflop AND (mano IS mala AND fichas IS pocas) THEN decision IS pasar/noIr
        // RULE 4 : IF fase IS preflop AND (mano IS mala AND (fichas IS medias OR fichas IS muchas)) THEN decision IS igualar
        // RULE 5 : IF fase IS preflop AND (mano IS buena AND fichas IS pocas) THEN decision IS igualar
        // RULE 6 : IF fase IS preflop AND (mano IS buena AND (fichas IS medias OR fichas IS muchas)) THEN decision IS igualar
        // RULE 7 : IF fase IS flop AND (mano IS mala AND (fichas IS pocas OR fichas IS medias)) THEN decision IS pasar/noIr
        // RULE 8 : IF fase IS flop AND (mano IS mala AND fichas IS muchas) THEN decision IS igualar
        // RULE 9 : IF fase IS turn/river AND mano IS mala THEN pasar/noIr
        /** RULE 10: IF fase IS turn/river AND (mano IS buena AND fichas IS pocas) THEN Igualar || SUBIR
        * Inicialmente, se habia contemplado que los posibles valores de decision eran No ir, Pasar y Apostar (igualar y subir).
         * Realmente, los que se comportan de la misma forma son No ir y Pasar, ya que contemplamos que intentara pasar y,
         * si no puede, no irá, lo que se controlara de forma externa al controlador.
         *
         * Por ello, esta útlima regla no se podra implementar, deberá aprender el jugador por sí solo lo que debe hacer
         */

    }
}
