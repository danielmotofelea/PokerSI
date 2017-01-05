package pokerSI;

import net.sourceforge.jFuzzyLogic.FIS;
import net.sourceforge.jFuzzyLogic.FunctionBlock;
import net.sourceforge.jFuzzyLogic.defuzzifier.DefuzzifierCenterOfGravity;
import net.sourceforge.jFuzzyLogic.membership.*;
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
    private int totalfichasApostadas;
    private int manosGanadas;
    private int manosJugadas;
    private double []gen; /** Posiciones 0 a 10: pesos de reglas, posicion 11: agresividad */
    private double [] mejorMano; /** Se usará como retorno de la función*/
    private boolean activo;
    //private boolean ganador;
    private int valorMano;
    private double fitness;
    private int []identificacion;
    private Carta []cartasEnMano;
    private ArrayList<Carta> cartasComunes;

    public Jugador(){       /** Constructor del pokerSI.Jugador sin parametros */
        this.fichas = 1000;
        this.fichasGanadas = 0;
        this.fichasApostadas = 0;
        this.totalfichasApostadas=0;
        this.manosGanadas = 0;
        this.manosJugadas = 0;
        this.fitness = 0;
        this.activo=true;
        //this.ganador=false;
        this.valorMano = 1;
        this.gen = new double[11];                     /** Tamaño 12 por ser 11 pesos de reglas + valor de agresividad*/
        this.identificacion = new int[3];              /** Posicion 0: nºgeneracion // Posicion 1: nº mesa // Posicion 3: nº jugador*/
        this.cartasEnMano = new Carta[2] ;             /** Las dos cartas en mano las tomamos como enteros*/
        this.cartasComunes = new ArrayList<Carta>();   /** Las 5 cartas comunes de la mesa para ver nuestra mejor mano*/
        this.mejorMano = new double[2];                /** Guarda en la primera posicion el valorMano y en la segunda la ponderacion*/
        for(int i=0; i<3; i++)
            this.identificacion[i] = 0;
        for(int j=0; j<2; j++)
            this.cartasEnMano[j] = new Carta();
        for(int k=0; k<gen.length; k++)
            this.gen[k] = 0.0;

        this.cartasComunes = new ArrayList<Carta>();
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
        this.fichasApostadas = this.fichasApostadas+fichasApostadas;
    }

    public void resetFichasApostadas(){
        this.fichasApostadas=0;
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
        System.arraycopy(gen, 0, this.gen, 0, gen.length-1);
    }

    public int[] getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(int[] identificacion) {
        System.arraycopy(identificacion, 0, this.identificacion, 0, identificacion.length-1);
    }

    public void setFitness(double valor){
        fitness = valor;
    }
    public double getFitness(){
        return fitness;
    }

    public double getMejorMano() {
        return mejorMano[1];
    }

    public Carta[] getCartasEnMano() {
        return cartasEnMano;
    }

    public void setCartasEnMano(Carta[] cartasEnMano) {
        this.cartasEnMano = cartasEnMano;
    }

    public ArrayList<Carta> getCartasComunes() {
        return cartasComunes;
    }

    public void setCartasComunes(ArrayList<Carta> cartasComunes) {
        this.cartasComunes = cartasComunes;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }


    public int getValorMano() {
        return valorMano;
    }

    public void setValorMano(int valorMano) {
        this.valorMano = valorMano;
    }

    public int getTotalfichasApostadas() {
        return totalfichasApostadas;
    }

    public void setTotalfichasApostadas(int totalfichasApostadas) {
        this.totalfichasApostadas = this.totalfichasApostadas+totalfichasApostadas;
    }

    @Override
    public String toString() {
        return "Jugador{" +
                "fichas=" + fichas +
                ", fichasGanadas=" + fichasGanadas +
                ", fichasApostadas=" + fichasApostadas +
                ", totalfichasApostadas=" + totalfichasApostadas +
                ", manosGanadas=" + manosGanadas +
                ", manosJugadas=" + manosJugadas +
                ", gen=" + Arrays.toString(gen) +
                ", mejorMano=" + Arrays.toString(mejorMano) +
                ", activo=" + activo +
                ", valorMano=" + valorMano +
                ", fitness=" + fitness +
                ", identificacion=" + Arrays.toString(identificacion) +
                ", cartasEnMano=" + Arrays.toString(cartasEnMano) +
                ", cartasComunes=" + cartasComunes +
                '}';
    }

    public void resetAtributosJugador(){
        this.fichas = 1000;
        this.fichasGanadas = 0;
        this.fichasApostadas = 0;   // guardamos el fitness de los jugadores de una generacion a otra
        this.manosGanadas = 0;
        this.manosJugadas = 0;
        this.fitness = 0;
        this.cartasEnMano = new Carta[2];
        this.cartasComunes.clear();
    }

    /** Esta funcion unicamente se invoca cuando haya finalizado la partida ya sea en la mesa inicial o en el mesa final
     *  por lo tanto se calculara unicamente 2 veces por partida en el caso de los jugadores de la mesa final
     *  y con el valor fitness ver la evolucion de la grafica de aprendizaje que elabora la clase MAIN */

    public double calcularFitness(){

        if((manosJugadas != 0) && (totalfichasApostadas != 0))
            fitness =  ((double)manosGanadas/(double)manosJugadas)*((double)fichasGanadas/(7.0*(double)fichasApostadas)) / 100.0 ;
            //fichasApostadas * 7 porque se considera que la máxima cantidad de fichas que puede ganar es 7000
        else
            fitness = 0;
        return fitness;
    }

    /** Devuelve dos valores:
     *
     *            1 que sea el tipo de mano(sin especificar),
     *            2 que sea la ponderacion(llamando al metodo de ponderacion)*/

    public double [] calcularMejorMano(){

        ArrayList<Carta> manoProvisional = new ArrayList<Carta>();    /** Array creado para añadir las 7 cartas al borroso y que devuelva la mejor mano*/
        for(int i=0; i<2; i++)
            manoProvisional.add(cartasEnMano[i]);        /** Se añaden las cartas que se tenga actualmente en un arraylist unificado*/
        for(int j=0; j<cartasComunes.size(); j++)                           /** Para pasarselo a otro metodo y que evalue la mejor mano*/
            manoProvisional.add(cartasComunes.get(j));

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
        boolean diam = false;

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
        int valorEscaleraFinal = 0;
        int valorColor = 0;
        int valorPoker = 0;
        int valorCartaAlta = 0;

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

            if (manoProvisional.get(i).getPalo() == 1) {    /** Una vez ordenado, cogemos cada carta por separado y vemos de que palo es*/
                contCorazones++;                          /** Despues aumentamos el contador de dicho palo para llevar el recuento */
                if(contCorazones > 4){
                    valorColor = manoProvisional.get(i).getValor();      /** Guarda el valor mas alto del color */
                    color = true;
                    cor = true;
                }

            }
            if (manoProvisional.get(i).getPalo() == 2){
                contPicas++;
                if(contPicas > 4){
                    valorColor = manoProvisional.get(i).getValor();
                    color = true;
                    pic = true;
                }
            }
            if (manoProvisional.get(i).getPalo() == 3) {
                contDiamantes++;
                if(contDiamantes > 4){
                    valorColor = manoProvisional.get(i).getValor();
                    color = true;
                    diam = true;
                }
            }
            if (manoProvisional.get(i).getPalo() == 4){
                contTreboles++;
                if(contTreboles > 4){
                    valorColor = manoProvisional.get(i).getValor();
                    color = true;
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
                valorEscaleraFinal = 5;
            }
            if(manoProvisional.get(i).getValor()> valorCartaAlta)
                valorCartaAlta = manoProvisional.get(i).getValor();
        }

        /** Ahora vamos a ver si tenemos color, trio, pareja, doble pareja, full o poker*/

        for(int j=0; j<13; j++) { /** El array es hasta 13 porque como hemos inicializado antes, valor de la carta = posicion(j)+2 */

            /** PARA PAREJAS O DOBLES PAREJAS */

            if ((numeroCarta[j] == 2) && (pareja1 == false)) { // Tenemos pareja
                pareja1 = true;
                valorPareja1 = j + 2;
            }
            if ((numeroCarta[j] == 2) && (j+2 != valorPareja1)) { // En este caso tenemos doble pareja
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

        mejorMano[1] = ponderarMano(valorCartaAlta, cor, pic, diam, valorPareja1, valorPareja2,
                valorPoker, valorTrio, valorFull,valorColor, valorEscaleraFinal);

        /** Ya tenemos el valor de nuestra mejor mano pero para apostar debemos hacer una consulta al sistema borroso
         *  y tomar una decision, de si apostar, pasar o irse, todo esto dependiendo de los jugadores que haya aun en la mesa
         *  cuantas fichas tengamos y sobre todo nuestra mano */

        return mejorMano;
    }

    /** Metodo creado para ponderar las mano mano que tenemos nosotros con la mejor de la mesa, es decir,
     * comparamos el valor que nos devuelve el metodo anterior (calcularMejorMano)
     *
     * NOTA: No sería mejor tener un atributo que guarde el valor de la mejor mano para no tener que volver a llamar al metodo
     *       y asi evitar calcular de nuevo la mejor mano. (añadido, si no resulta util, buscar otra forma)*/


    public double ponderarMano(int valorCartaAlta, boolean cor, boolean pic, boolean diam, int valorPareja1, int valorPareja2,
                               int valorPoker, int valorTrio, int []valorFull,int valorColor, int valorEscaleraFinal){

        double ponderacion = 0.0;

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
        boolean diamComun = false;

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
        int valorEscaleraFinalComun = 0;
        int valorColorComun = 0;
        int valorPokerComun = 0;
        int valorCartaAltaComun = 0;

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
                valorEscaleraFinalComun = 5;
            }
            if(cartasComunes.get(i).getValor()> valorCartaAltaComun)
                valorCartaAltaComun = cartasComunes.get(i).getValor();
        }

        for(int j=0; j<13; j++) { /** El array es hasta 13 porque, como hemos inicializado antes, valor de la carta = posicion(j)+2 */

            /** PARA PAREJAS O DOBLES PAREJAS */

            if ((numeroCartaComun[j] == 2) && (pareja1Comun == false)) {
                pareja1Comun = true;
                valorPareja1Comun = j + 2;
            }
            if ((numeroCartaComun[j] == 2) && (j+2 != valorPareja1Comun)) {
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


        int ponComun = 0;
        int ponEnMano = 0;
        int ponMejorMano = 0;

        if(parMano !=0 )
            ponEnMano = parMano;
        else
            ponEnMano = carAlta;

        switch(valorManoComun){
            case 1:
                ponComun = valorCartaAltaComun ; break;
            case 2:
                ponComun = 100 + valorPareja1Comun; break;
            case 3:
                ponComun = 200 + valorPareja1Comun + valorPareja2Comun; break;
            case 4:
                ponComun = 300 + valorTrioComun; break;
            case 5:
                ponComun = 400 + valorEscaleraFinalComun; break;
            case 6:
                ponComun = 500 + valorColorComun;
                if(corComun)
                    ponComun = ponComun + 40;
                else if(picComun)
                    ponComun = ponComun + 30;
                else if (diamComun)
                    ponComun = ponComun + 20;
                else
                    ponComun = ponComun + 10;
                break;
            case 7:
                ponComun = 600 + 2 * valorFullComun[0] + 3*valorFullComun[1]; break;
            case 8:
                ponComun = 700 + valorPokerComun; break;
            case 9:
                ponComun = 800 + valorEscaleraFinalComun;
                if(corComun)
                    ponComun = ponComun + 40;
                else if(picComun)
                    ponComun = ponComun + 30;
                else if (diamComun)
                    ponComun = ponComun + 20;
                else
                    ponComun = ponComun + 10;
        }
        switch(valorMano){
            case 1:
                ponMejorMano = valorCartaAlta ; break;
            case 2:
                ponMejorMano = 100 + valorPareja1; break;
            case 3:
                ponMejorMano = 200 + valorPareja1 + valorPareja2; break;
            case 4:
                ponMejorMano = 300 + valorTrio; break;
            case 5:
                ponMejorMano = 400 + valorEscaleraFinal; break;
            case 6:
                ponMejorMano = 500 + valorColor;
                if(cor)
                    ponMejorMano = ponMejorMano + 40;
                else if(pic)
                    ponMejorMano = ponMejorMano + 30;
                else if (diam)
                    ponMejorMano = ponMejorMano + 20;
                else
                    ponMejorMano = ponMejorMano + 10;
                break;
            case 7:
                ponMejorMano = 600 + 2 * valorFull[0] + 3*valorFull[1]; break;
            case 8:
                ponMejorMano = 700 + valorPoker; break;
            case 9:
                ponMejorMano = 800 + valorEscaleraFinal;
                if(cor)
                    ponMejorMano = ponMejorMano + 40;
                else if(pic)
                    ponMejorMano = ponMejorMano + 30;
                else if (diam)
                    ponMejorMano = ponMejorMano + 20;
                else
                    ponMejorMano = ponMejorMano + 10;
        }
        if(((double)ponComun/(double) ponMejorMano)> 0.6)
            ponderacion = ponComun + ponEnMano;
        else
            ponderacion = ponMejorMano;

        return ponderacion;
    }

    /**
     * Todos los parametros se pasan desde mesa, que es donde se hará la llamada a esta función.
     * @param apuestaMinima : valor de la apuesta mínima. No siempre será igual a la ciega grande.
     * @param apuestaMaxima : Valor del bote, para controlar que el valor devuelto no la supera
     * @param ciegaGrande : valor de la ciega grande, se utilizará cuando se decida subir la apuesta
     * @param mediaPocas : media muestral para definición de variable gaussiana
     * @param mediaMedias : "
     * @param mediaMuchas : "
     * @param desvPocas : desviación típica para definición de variable gaussiana
     * @param desvMedias
     * @param desvMuchas
     * @return (número entero): 0 pasar/noIr; 1 igualar; (cantidad a apostar) subir
     *
     * La mesa se encargará de controlar si el jugador pasa o no puede, y por tanto se va.
     */
    public int tomarDecision(int apuestaMinima, int apuestaMaxima, int ciegaGrande, double mediaPocas, double mediaMedias, double mediaMuchas, double desvPocas, double desvMedias, double desvMuchas){

        int subidaMaxima;
        int apuesta; //Se usará como return

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
            Decision devolvera la cantidad de ciegas a subir. Como la apuesta es un valor entero, tomaremos sólo
            la parte entera del valor que devuelva la defuzzyficacion:
            Pasar/NoIr: 0 a 1
            Igualar: 0.5 a 2 (iguala la apuesta mínima)
            Apostar: 1 a ¿10?.
            Ejemplos:
                        -si obtenemos un valor de 0'6, se considerará pasar/noIr o igualar en función de las reglas
                        -si obtenemos un valor de 1'9, se considerará Igualar o subir en función de las reglas
                        -si obtenemos un valor de 3'7, se tomará una subida de 3 veces la ciega grande
            Formula que determinará el retorno: apuestaMínima + valorDecisión(entero) + valorDecision*agresividad
            Habrá que determinar si la agresividad sería necesaria, viendo que sólo se tiene en cuenta en la subida
            y esta ya devuelve una cantidad de subida (que hay que limitar, en funcion del valor de las ciegas)
            Lo recomendable sería hacer pruebas cuando la aplicación esté operativa, para ver si se usa correctamente
            todo el rango de subidas que tendrá asignada la aplicación o, por el contrario, se confirma la necesidad
            de la agresividad.
         */

        Value pniX[] = { new Value(0), new Value(1) };
        Value pniY[] = { new Value(1), new Value(0) };
        MembershipFunction mPasarNI = new MembershipFunctionPieceWiseLinear(pniX, pniY);

        Value igX[] = { new Value(0.5), new Value(1), new Value(2) };
        Value igY[] = { new Value(0), new Value(1), new Value(0) };
        MembershipFunction mIgualar = new MembershipFunctionPieceWiseLinear(igX, igY);

        subidaMaxima = (apuestaMaxima - apuestaMinima) / ciegaGrande;
        Value subX[] = { new Value(1), new Value(2), new Value(subidaMaxima) };
        Value subY[] = { new Value(0), new Value(1), new Value(1) };
        MembershipFunction mSubir = new MembershipFunctionPieceWiseLinear(subX, subY);

        LinguisticTerm ltpasarNI = new LinguisticTerm("pasar/noIr", mPasarNI);
        LinguisticTerm ltIgualar = new LinguisticTerm("igualar", mIgualar);
        LinguisticTerm ltSubir = new LinguisticTerm("subir", mSubir);

        decision.add(ltpasarNI);
        decision.add(ltIgualar);
        decision.add(ltSubir);

        decision.setDefuzzifier(new DefuzzifierCenterOfGravity(decision));

        /**
         * Bloque de reglas
         *
         * Se han omitido declaraciones repetidas de términos, a pesar de que en los ejemplos
         * si se mantenían repetidas. Se han hecho pruebas y esta omisión funciona correctamente.
         *
         * Quizá se podría probar a omitir también algunas RuleExpression,
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
        rule1.addAntecedent(mano, "muyMala", false);
        rule1.addConsequent(decision, "pasar/noIr", false);
        ruleBlock.add(rule1);

        /*
         RULE 2 : IF mano IS muyBuena THEN decision IS subir
          */

        Rule rule2 = new Rule("Regla2", ruleBlock);
        rule2.addAntecedent(mano, "muyBuena", false);
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
        RuleTerm rtManoBuena = new RuleTerm(mano, "buena", false);
        RuleExpression r5AntecedenteAnd1 = new RuleExpression(rtFasePreflop, rtManoBuena, RuleConnectionMethodAndMin.get());
        RuleExpression r5AntecedenteAnd2 = new RuleExpression(r5AntecedenteAnd1, rtFichasPocas, RuleConnectionMethodAndMin.get());
        rule5.setAntecedents(r5AntecedenteAnd2);
        rule5.addConsequent(decision, "igualar", false);
        ruleBlock.add(rule5);

        /*
         RULE 6 : IF (fase IS preflop AND mano IS buena) AND (fichas IS medias OR fichas IS muchas) THEN decision IS subir
          */

        Rule rule6 = new Rule("Regla6", ruleBlock);
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

        /** RULE 10: IF (fase IS turn/river AND mano IS buena) AND fichas IS pocas THEN Igualar || SUBIR
         * Esta regla se dividirá en 2, una para igualar y otra para subir. Los pesos que tenga cada jugador
         * determinarán que decisión tomará si se da el caso
         */

        /*
            Igualar
         */
        Rule rule10 = new Rule("Regla10", ruleBlock);
        RuleExpression r10AntecedenteAnd1 = new RuleExpression(rtFaseTR, rtManoBuena, RuleConnectionMethodAndMin.get());
        RuleExpression r10AntecedenteAnd2 = new RuleExpression(r10AntecedenteAnd1, rtFichasPocas, RuleConnectionMethodAndMin.get());
        rule10.setAntecedents(r10AntecedenteAnd2);
        rule10.addConsequent(decision, "igualar", false);
        ruleBlock.add(rule10);

        /*
            Subir
         */

        Rule rule11 = new Rule("Regla11", ruleBlock);
        RuleExpression r11AntecedenteAnd1 = new RuleExpression(rtFaseTR, rtManoBuena, RuleConnectionMethodAndMin.get());
        RuleExpression r11AntecedenteAnd2 = new RuleExpression(r11AntecedenteAnd1, rtFichasPocas, RuleConnectionMethodAndMin.get());
        rule11.setAntecedents(r11AntecedenteAnd2);
        rule11.addConsequent(decision, "subir", false);
        ruleBlock.add(rule11);

        /*
         * Los pesos se obtienen del gen
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
        rule10.setWeight(gen[9]);
        rule11.setWeight(gen[10]);
        /*
            END_RULEBLOCK
         */
        /*
            END_FUNTIONBLOCK
         */

        /**
         * Utilizaremos un HashMap para guardar las reglas y poder mostrarlas por pantalla de una forma sencilla
         */
        HashMap<String, RuleBlock> ruleBlockHashMap = new HashMap<>();
        ruleBlockHashMap.put(ruleBlock.getName(), ruleBlock);
        fb.setRuleBlocks(ruleBlockHashMap);

        pruebaBorroso(ruleBlockHashMap); //TODO eliminar esta declaración cuando hayan finalizado las pruebas del controlador

        /**
         * TODO especificar donde se van a guardar los valores de grado de soporte y peso de las reglas, etc
         */
        /**
         * TODO especificar valores de entrada al borroso, previos al evaluate
         */

        fis.evaluate();

        /**
         * Comprobamos la decisión que se va a tomar.
         * En principio, no se tendrá en cuenta el punto de corte entre pasar/noIr e igualar ya que, aunque no hay dos reglas
         * que mediante sus pesos lo puedan limitar, es muy improbable que se de el caso del punto de corte, debido a que estamos
         * trabajando con bastante precision (double)
         */

        if(fis.getVariable("decision").getMembership("pasar/noIr") > fis.getVariable("decision").getMembership("igualar")) //Si es mayor que igualar, es mayor que subir
        {
            apuesta = 0; //Si pasa o se va se controla en mesa
        }
        else if (fis.getVariable("decision").getMembership("igualar") > fis.getVariable("decision").getMembership("subir"))
        {
            apuesta = 1; //Se iguala la apuesta mínima
        }
        else{
            apuesta = apuestaMinima + (int) fis.getVariable("decision").getValue()*ciegaGrande;
            /**
             *  En caso de subir, el valor entero obtenido en el borroso se entenderá como la cantidad de
             *  ciegas grandes que se añaden a la apuesta mínima. A ello se le añade además la cantidad de ciegas
             *  que se sube debido a la agresividad del jugador.
             */

        }

        /**
         * TODO especificar cada cuantas generaciones (this.identificacion[0]) hay que guardar en fichero el output del fis
         */

        return apuesta;

    }

    /**
     * La única finalidad de la función pruebaBorroso es comprobar que las reglas y las variables han quedado bien
     * definidas.
     * @param ruleBlockHashMap
     */
    public void pruebaBorroso(HashMap<String, RuleBlock> ruleBlockHashMap)
    {
        /**
         * TODO definir prueba del borroso
         */
        System.out.println(ruleBlockHashMap.toString());

    }


    public int tomarDecision(){
        int devolver=0;
        return devolver;
    }
}

