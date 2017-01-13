package Version1;

import com.sun.org.apache.xpath.internal.SourceTree;

import java.util.ArrayList;

/**
 * La mesa tiene 8 jugadores. Su funcion es repartir las cartas a cada jugador, junto a otros parametros.
 * Se encarga de informar a los jugadores si han ganado una determinada mano de la mesa, actualizar sus fichas...
 * Cuando un jugdor se queda sin fichas la mesa lo debe eliminar, o lo que viene a ser no repartirle cartas.
 * En el momento que solo haya un jugador en la mesa, este se devuelve como ganador al objeto torneo.
 */


public class Mesa {

    /**
     * Atributos del objeto mesa
     */
    private int numJugadores = 0;
    /**
     * Esto sera un array list de jugadores
     */
    private int numJugadoresEliminados = 0;
    private Carta[] cartasEnMano = new Carta[2];
    private ArrayList<Carta> cartasComunes = new ArrayList<Carta>();
    private ArrayList<Jugador> jugadoresMesa = new ArrayList<Jugador>();

    private ArrayList<Jugador> jugadoresManoActual;
    private int maxManos = 0;
    private int idCiega = 0;
    private int ciegaP = 25;
    private int ciegaG = 50;
    private int maxApuestaActual = 0;
    private int bote;
    /**
     * int o float
     */
    private int numSubidas = 0;
    private int idMesa = 0;
    /**
     * Se controla desde torneo
     */
    private int idGenracion = 0;
    /**
     * Se controla desde torneo
     */
    private Baraja b;


    public Mesa() {

    }

    /**
     * Contrucotor de la mesa final, al que no se le pasa id mesa
     */
    public Mesa(ArrayList<Jugador> JugadoresMesa, int idGeneracion) {
        this.jugadoresMesa.addAll(JugadoresMesa);
        this.numJugadores = 8;
        this.numJugadoresEliminados = 0;
        this.idCiega = 0;
        this.idGenracion = idGeneracion;
    }

    /**
     * El construcotr de mesa. Se le pasan 8 jugadores, idMesa e idGeneracion.
     * Id generacion se controla desde el main.
     */
    public Mesa(ArrayList<Jugador> JugadoresMesa, int idMesa, int idGeneracion) {
        this.jugadoresMesa.addAll(JugadoresMesa);
        this.numJugadores = 8;
        this.numJugadoresEliminados = 0;
        this.idCiega = 0;
        this.idMesa = idMesa;
        this.idGenracion = idGeneracion;
        this.bote = 0;
    }

    public void AumentarBote(int cantidad) {
        bote = bote + cantidad;
    }

    public void setMaxApuestaActual(int maxApuestaActual) {
        this.maxApuestaActual = maxApuestaActual;
    }

    @Override
    public String toString() {
        return "Mesa{" +
                "cartasComunes=" + cartasComunes +
                '}';
    }


    /**
     * Metodo principal de mesa. Este metodo enfrenta a los jugadores de la mesa hasta que solo quede 1 o se hayan disputdo 300 manos.
     * Si solo queda un jugador,este es devuelto. Si se llega al final del metodo por superar 300 manos se devuelve al jugador con mayor bote.
     * @return
     */


    public Jugador jugar() {

        b = new Baraja();
        ActualizarIdentificacion();
        for(int i=0; i<jugadoresMesa.size();i++){
            jugadoresMesa.get(i).resetAtributosJugador2();
        }
        System.out.println("MESA " + idMesa+ " Generacion "+ idGenracion);
        System.out.println("PRINCIPIo MESA");
        for(int i=0;i<jugadoresMesa.size();i++){
            System.out.println(jugadoresMesa.get(i).toString());
        }
        System.out.println("FINAL MESA");
        while(jugadoresMesa.size()!=1 && maxManos<300){  /** Mientras haya mas de un jugador en la mesa se van a jugar manos */
        b=new Baraja();

            /** Quitar ciegas a los jugadores que toca
             * Dicho indice se va actualizar en cada mano.
             */


            QuitarCiega();
            setMaxApuestaActual(ciegaG);


            RepartirCartas();
            DecEtapa();
           // System.out.println("PREFLOP");
           // MuestraContenido();
            setMaxApuestaActual(0);

            for (int i = 0; i < jugadoresMesa.size(); i++) {
                jugadoresMesa.get(i).resetFichasApostadas();
            }
            if (NumActivos() > 1) {
                ActualizarManosJugadas();
                ObtenerCartasComunes(3);
                RepartirCartasComunes();
                DecEtapa();
              // System.out.println("FLOP");
              // MuestraContenido();
                setMaxApuestaActual(0);

                for (int i = 0; i < jugadoresMesa.size(); i++) {
                    jugadoresMesa.get(i).resetFichasApostadas();
                }

                if (NumActivos() > 1) {
                    ObtenerCartasComunes(1);
                    RepartirCartasComunes();
                    DecEtapa();
                   // System.out.println("TURN");
                  /// MuestraContenido();
                    setMaxApuestaActual(0);

                    for (int i = 0; i < jugadoresMesa.size(); i++) {
                        jugadoresMesa.get(i).resetFichasApostadas();
                    }
                    if (NumActivos() > 1) {
                        ObtenerCartasComunes(1);
                        RepartirCartasComunes();
                        DecEtapa();
                        //System.out.println("RIVER");
                       // MuestraContenido();
                        if (NumActivos() > 1) {
                            DecidirGanador();
                            //System.out.println("Contenido jugadores despues dedecidir Ganador");
                            //MuestraContenido();
                        } else {
                            Ganador();
                           // System.out.println("Contenido jugadores despues dedecidir Ganador");
                           //System.out.println(" Fichas totales: "+sumaFichas());
                           // MuestraContenido();
                        }

                    } else {
                        Ganador();
                        //System.out.println("Contenido jugadores despues dedecidir Ganador");
                       // System.out.println(" Fichas totales: "+sumaFichas());
                       // MuestraContenido();
                    }
                } else {
                    Ganador();
                    //System.out.println("Contenido jugadores despues dedecidir Ganador");
                    //System.out.println(" Fichas totales: "+sumaFichas());
                    //MuestraContenido();
                }
            } else {
                Ganador();
                ActualizarManosJugadas();
                //System.out.println("Contenido jugadores despues dedecidir Ganador");
                //System.out.println(" Fichas totales: "+sumaFichas());
                //MuestraContenido();
            }

            //Dejar los atributos de mesa y los atributos de jugadores que corresponda listos para jugar otra mano desde 0.

            for (int i = 0; i < jugadoresMesa.size(); i++) {
                jugadoresMesa.get(i).resetFichasApostadas();
            }

            for (int i = 0; i < jugadoresMesa.size(); i++) {
                jugadoresMesa.get(i).setActivo(true);
            }

            ActualizarMesa();
            //System.out.println(" Fichas totales: "+sumaFichas());
            //System.out.println("MESA DESPUES DE EMIMINAR JUGADORES");
            //MuestraContenido();
            ActualizaridCiega();

            bote=0;
            maxManos++;
            cartasComunes = new ArrayList<Carta>();
            ResetCartasComunes();
            //ActualizarFitness();

            //System.out.println(maxManos);
        }
        System.out.println("FINAL DE LA MESA. Fichas totales: "+sumaFichas());
        MuestraContenido();


        /**
         * Se devuelve el ganador de la mesa, que esta en la posicion 0 ya que se han eliminado los demas.
         */
        if(maxManos!=300){
            return jugadoresMesa.get(0);
        }else{
            return jugadoresMesa.get(MayorBote());
        }

    }

    public int sumaFichas(){
        int fichas=0;
        for(int i=0;i<jugadoresMesa.size();i++){
            fichas=fichas+jugadoresMesa.get(i).getFichas();
        }
        return fichas;
    }


    public void QuitarCiega() {

        if (idCiega == jugadoresMesa.size() - 1) {

            if(jugadoresMesa.get(jugadoresMesa.size()-1).getFichas()>=ciegaP){
                AumentarBote(ciegaP);
                jugadoresMesa.get(jugadoresMesa.size()-1).settotalFichasApostadas(ciegaP);
                jugadoresMesa.get(jugadoresMesa.size()-1).setFichasApostadas(ciegaP);
                jugadoresMesa.get(jugadoresMesa.size()-1).setFichas(jugadoresMesa.get(jugadoresMesa.size() - 1).getFichas() - ciegaP);

            }else{
                AumentarBote(jugadoresMesa.get(jugadoresMesa.size()-1).getFichas());
                jugadoresMesa.get(jugadoresMesa.size()-1).settotalFichasApostadas(jugadoresMesa.get(jugadoresMesa.size()-1).getFichas());
                jugadoresMesa.get(jugadoresMesa.size()-1).setFichasApostadas(jugadoresMesa.get(jugadoresMesa.size()-1).getFichas());
               // jugadoresMesa.get(jugadoresMesa.size()-1).setFichas(jugadoresMesa.get(jugadoresMesa.size()-1).getFichas() - jugadoresMesa.get(jugadoresMesa.size() - 1).getFichas());
                jugadoresMesa.get(jugadoresMesa.size()-1).setFichas(0);

            }

            if(jugadoresMesa.get(0).getFichas()>=ciegaG){
                AumentarBote(ciegaG);
                jugadoresMesa.get(0).setFichasApostadas(ciegaG);
                jugadoresMesa.get(0).settotalFichasApostadas(ciegaG);
                jugadoresMesa.get(0).setFichas(jugadoresMesa.get(0).getFichas()-ciegaG);


            }else{
                AumentarBote(jugadoresMesa.get(0).getFichas());
                jugadoresMesa.get(0).setFichasApostadas(jugadoresMesa.get(0).getFichas());
                jugadoresMesa.get(0).settotalFichasApostadas(jugadoresMesa.get(0).getFichas());
                //jugadoresMesa.get(0).setFichas(jugadoresMesa.get(0).getFichas() - jugadoresMesa.get(0).getFichas());
                jugadoresMesa.get(0).setFichas(0);
            }

            idCiega = 0;
        } else {

            if(jugadoresMesa.get(idCiega).getFichas()>=ciegaP){
                AumentarBote(ciegaP);
                jugadoresMesa.get(idCiega).setFichasApostadas(ciegaP);
                jugadoresMesa.get(idCiega).settotalFichasApostadas(ciegaP);
                jugadoresMesa.get(idCiega).setFichas(jugadoresMesa.get(idCiega).getFichas() - ciegaP);


            }else{
                AumentarBote(jugadoresMesa.get(idCiega).getFichas());
                jugadoresMesa.get(idCiega).setFichasApostadas(jugadoresMesa.get(idCiega).getFichas());
                jugadoresMesa.get(idCiega).settotalFichasApostadas(jugadoresMesa.get(idCiega).getFichas());
                //jugadoresMesa.get(idCiega).setFichas(jugadoresMesa.get(idCiega).getFichas() - jugadoresMesa.get(idCiega).getFichas());
                jugadoresMesa.get(idCiega).setFichas(0);


            }

            if( jugadoresMesa.get(idCiega + 1).getFichas()>=ciegaG){
                AumentarBote(ciegaG);
                jugadoresMesa.get(idCiega + 1).setFichasApostadas(ciegaG);
                jugadoresMesa.get(idCiega + 1).settotalFichasApostadas(ciegaG);
                jugadoresMesa.get(idCiega + 1).setFichas(jugadoresMesa.get(idCiega + 1).getFichas() - ciegaG);


            }else{
                AumentarBote(jugadoresMesa.get(idCiega + 1).getFichas());
                jugadoresMesa.get(idCiega + 1).setFichasApostadas( jugadoresMesa.get(idCiega + 1).getFichas());
                jugadoresMesa.get(idCiega + 1).settotalFichasApostadas(jugadoresMesa.get(idCiega + 1).getFichas());
                //jugadoresMesa.get(idCiega + 1).setFichas(jugadoresMesa.get(idCiega + 1).getFichas() -  jugadoresMesa.get(idCiega + 1).getFichas());
                jugadoresMesa.get(idCiega + 1).setFichas(0);

            }

            idCiega++;
        }


    }

    public void ResetCartasComunes(){
        for (int i = 0; i < jugadoresMesa.size(); i++) {
            jugadoresMesa.get(i).setCartasComunes(cartasComunes);
        }
    }

    public void RepartirCartas() {


        for (int i = 0; i < jugadoresMesa.size(); i++) {
            cartasEnMano[0] = b.sacarPrimeraCarta();
            cartasEnMano[1] = b.sacarPrimeraCarta();

            jugadoresMesa.get(i).setCartasEnMano(cartasEnMano);
            cartasEnMano = new Carta[2];
        }
    }

    public void RepartirCartasComunes() {
        for (int i = 0; i < jugadoresMesa.size(); i++) {

            jugadoresMesa.get(i).setCartasComunes(cartasComunes);
        }

    }

    public void ObtenerCartasComunes(int cantidad) {
        for (int i = 0; i < cantidad; i++) {
            cartasComunes.add(b.sacarPrimeraCarta());
        }
    }

    public int NumActivos() {
        int cantidad = 0;
        for (int i = 0; i < jugadoresMesa.size(); i++) {
            if (jugadoresMesa.get(i).isActivo())
                cantidad++;
        }
        return cantidad;
    }

    public void DecidirGanador() {
        int mano = 0;
        int ganador = 0;
        double ponderacion =0.0;
        boolean aux=false;
        boolean aux2=false;

        for (int i = 0; i < jugadoresMesa.size(); i++) {
            if (jugadoresMesa.get(i).isActivo() && (jugadoresMesa.get(i).getValorMano() >= mano)) {
                mano = jugadoresMesa.get(i).getValorMano();
            }
        }

        for (int i = 0; i < jugadoresMesa.size(); i++) {
            if (jugadoresMesa.get(i).isActivo() && (jugadoresMesa.get(i).getValorMano() == mano) && (jugadoresMesa.get(i).getPonderacion())>=ponderacion){
                ponderacion=jugadoresMesa.get(i).getPonderacion();
            }
        }

        for (int i = 0; i < jugadoresMesa.size(); i++) {
            if (jugadoresMesa.get(i).isActivo() && (jugadoresMesa.get(i).getValorMano() == mano) && (jugadoresMesa.get(i).getPonderacion())==ponderacion){
                ganador++;
            }
        }


        for (int i = 0; i < jugadoresMesa.size(); i++) {
            if (jugadoresMesa.get(i).isActivo() && (jugadoresMesa.get(i).getValorMano() == mano)&& (jugadoresMesa.get(i).getPonderacion())==ponderacion) {
                aux=true;
                if(bote%ganador!=0 && !aux2){
                    aux2=true;
                    //System.out.println("Se pierden "+bote%ganador+" Fichas");
                    jugadoresMesa.get(i).setFichas(jugadoresMesa.get(i).getFichas()+(bote%ganador));
                    jugadoresMesa.get(i).setFichasGanadas(jugadoresMesa.get(i).getFichasGanadas()+(bote%ganador));
                }
                jugadoresMesa.get(i).setFichas(jugadoresMesa.get(i).getFichas()+bote/ganador);
                jugadoresMesa.get(i).setFichasGanadas(jugadoresMesa.get(i).getFichasGanadas()+(bote/ganador));
                if(ganador==1)
                jugadoresMesa.get(i).setManosGanadas(jugadoresMesa.get(i).getManosGanadas()+1);
            }
        }

        if(!aux){
            //el bote se lo lleva la ciega grande.
            jugadoresMesa.get(idCiega).setFichas(jugadoresMesa.get(idCiega).getFichas()+bote);
            jugadoresMesa.get(idCiega).setFichasGanadas(jugadoresMesa.get(idCiega).getFichasGanadas()+bote);
            if(ganador==1)
            jugadoresMesa.get(idCiega).setManosGanadas(jugadoresMesa.get(idCiega).getManosGanadas()+1);
        }


    }

    public void Ganador() {
        boolean aux=false;
        for (int i = 0; i < jugadoresMesa.size(); i++) {
            if (jugadoresMesa.get(i).isActivo()) {
                aux=true;
                jugadoresMesa.get(i).setFichas(jugadoresMesa.get(i).getFichas()+bote);
                jugadoresMesa.get(i).setFichasGanadas(jugadoresMesa.get(i).getFichasGanadas()+bote);
                jugadoresMesa.get(i).setManosGanadas(jugadoresMesa.get(i).getManosGanadas()+1);
            }
        }
        if(!aux){
            //el bote se lo lleva la ciega grande.
            jugadoresMesa.get(idCiega).setFichas(jugadoresMesa.get(idCiega).getFichas()+bote);
            jugadoresMesa.get(idCiega).setFichasGanadas(jugadoresMesa.get(idCiega).getFichasGanadas()+bote);
            jugadoresMesa.get(idCiega).setManosGanadas(jugadoresMesa.get(idCiega).getManosGanadas()+1);
        }

    }

    public void CalcularManos(){
        for (int i = 0; i < jugadoresMesa.size(); i++) {
            if(jugadoresMesa.get(i).isActivo()) {
                jugadoresMesa.get(i).calcularMejorMano();
            }
        }
    }

    public void MuestraContenido(){
        for(int i=0; i<jugadoresMesa.size();i++){
            System.out.println(jugadoresMesa.get(i).toString());
            //System.out.println("Maxima Apuesta Actual "+maxApuestaActual);
            // System.out.println("Bote " +bote);

        }

    }

    public void ActualizarMesa(){
        for(int i=0;i<jugadoresMesa.size();i++){
            if(jugadoresMesa.get(i).getFichas()<1){
                jugadoresMesa.remove(i);
                i=-1;
            }
        }
    }

    public int MayorBote(){
        int fichas=0;
        int aux=0;
        for (int i=0; i<jugadoresMesa.size();i++){
            if(jugadoresMesa.get(i).getFichas()>fichas){
                fichas=jugadoresMesa.get(i).getFichas();
                aux=i;
            }
        }
        return aux;
    }

    public void ActualizarManosJugadas(){

        for (int i=0; i<jugadoresMesa.size();i++){
            if(jugadoresMesa.get(i).isActivo()){
                jugadoresMesa.get(i).setManosJugadas(jugadoresMesa.get(i).getManosJugadas()+1);

            }
        }
    }

 /**   public void ActualizarFitness(){
        for (int i=0; i<jugadoresMesa.size();i++){
            jugadoresMesa.get(i).calcularFitness();


        }
    }*/

    public void ActualizarIdentificacion(){
        int []identificacion= new int[3];

        identificacion[1]=idMesa;
        for (int i=0; i<jugadoresMesa.size();i++){
            identificacion[2]=i;
            identificacion[0]=jugadoresMesa.get(i).getIdentificacion()[0];
            jugadoresMesa.get(i).setIdentificacion(identificacion);
        }

    }

    public void ActualizaridCiega(){
        if(idCiega>jugadoresMesa.size()-1){
            idCiega=0;
        }
    }

    public double getMP(int i){
        double aux=0;

        switch(i){
            case 8:
                aux=350;
                break;
            case 7:
                aux=492;
                break;
            case 6:
                aux=500;
                break;
            case 5:
                aux=600;
                break;
            case 4:
                aux=700;
                break;
            case 3:
                aux=900;
                break;
            case 2:
                aux=1300;
                break;
            default:
                aux=0;
                break;
        }
        return aux;
    }

    public double getMM(int i){
        double aux=0;

        switch(i){
            case 8:
                aux=1000;
                break;
            case 7:
                aux=1142.85;
                break;
            case 6:
                aux=1333.33;
                break;
            case 5:
                aux=1600;
                break;
            case 4:
                aux=2000;
                break;
            case 3:
                aux=2666.6;
                break;
            case 2:
                aux=4000;
                break;
            default:
                aux=0;
                break;
        }
        return aux;

    }

    public double getMMU(int i){
        double aux=0;

        switch(i){
            case 8:
                aux=4650;
                break;
            case 7:
                aux=4700;
                break;
            case 6:
                aux=4800;
                break;
            case 5:
                aux=5000;
                break;
            case 4:
                aux=5300;
                break;
            case 3:
                aux=5750;
                break;
            case 2:
                aux=6700;
                break;
            default:
                aux=0;
                break;
        }
        return aux;

    }


    public double getDM(int i){
        double aux=0;

        switch(i){
            case 8:
                aux=400;
                break;
            case 7:
                aux=457.14;
                break;
            case 6:
                aux=533.33;
                break;
            case 5:
                aux=640;
                break;
            case 4:
                aux=800;
                break;
            case 3:
                aux=1066.6;
                break;
            case 2:
                aux=1600;
                break;
            default:
                aux=0;
                break;
        }
        return aux;

    }

    public double getDMU(int i){
        double aux=0;

        switch(i){
            case 8:
                aux=3350;
                break;
            case 7:
                aux=3300;
                break;
            case 6:
                aux=3200;
                break;
            case 5:
                aux=3000;
                break;
            case 4:
                aux=2700;
                break;
            case 3:
                aux=2250;
                break;
            case 2:
                aux=1300;
                break;
            default:
                aux=0;
                break;
        }
        return aux;

    }
    public int getApuestaMaxima(int i){
        int aux=0;
        if(jugadoresMesa.get(i).getFichas()>=bote){
            aux=bote;
        }else{
            aux=jugadoresMesa.get(i).getFichas();
        }
        return aux;
    }

    public int getApuestaMinima(int i){
        int aux=0;
        if(maxApuestaActual==0){
            aux=ciegaG;
        }else{
            aux=maxApuestaActual-jugadoresMesa.get(i).getFichasApostadas();
        }
        return aux;
    }

    public void DecEtapa() {
        int subidas = 0;
        int decision = 0;
        int sigLibre = idCiega + 1;

        CalcularManos();
        for (int i = 0; i < jugadoresMesa.size(); i++) {

            if (jugadoresMesa.get(sigLibre % jugadoresMesa.size()).isActivo()) {
                decision = jugadoresMesa.get(sigLibre % jugadoresMesa.size()).tomarDecision(getApuestaMinima(sigLibre % jugadoresMesa.size()),getApuestaMaxima(sigLibre % jugadoresMesa.size()),ciegaG,getMP(jugadoresMesa.size()),getMM(jugadoresMesa.size()),getMMU(jugadoresMesa.size()),getMP(jugadoresMesa.size()),getDM(jugadoresMesa.size()),getDMU(jugadoresMesa.size()));

                if (decision > 1 && subidas < 3) {
                    if((decision+maxApuestaActual)<jugadoresMesa.get(sigLibre % jugadoresMesa.size()).getFichas())
                        decision = decision + maxApuestaActual;
                    else
                        decision=jugadoresMesa.get(sigLibre % jugadoresMesa.size()).getFichas();
                    subidas++;
                    i = 0;
                    //System.out.println("Jugador" + sigLibre % 8 + " Sube");
                    //quitar fichas
                    //Habria que controlar que el jugador tenga las fichas necesarias (No se si lo controla el borroso)
                    jugadoresMesa.get(sigLibre % jugadoresMesa.size()).setFichas(jugadoresMesa.get(sigLibre % jugadoresMesa.size()).getFichas()-decision);
                    //Acrualizar fichas apostadas del jugador
                    jugadoresMesa.get(sigLibre % jugadoresMesa.size()).setFichasApostadas(decision);
                    jugadoresMesa.get(sigLibre % jugadoresMesa.size()).settotalFichasApostadas(decision);
                    //Actualizo la apuets maxima de la mesa, si procede
                    //compurebo si lo que leva el jugador apostando hasta ahora supera a la maxApuestaActual de la mesa.
                    if (jugadoresMesa.get(sigLibre % jugadoresMesa.size()).getFichasApostadas() > maxApuestaActual) {
                        maxApuestaActual = jugadoresMesa.get(sigLibre % jugadoresMesa.size()).getFichasApostadas();
                    }
                    //aumentar bote
                    AumentarBote(decision);
                    //System.out.println("boteS " + bote);
                    //actualizar valores mesa
                } else {
                    if (decision == 0) {
                        if (jugadoresMesa.get(sigLibre % jugadoresMesa.size()).getFichasApostadas() == maxApuestaActual) {
                            //El jugador puede pasar
                            //Si pasa no deberia hacer nada aqui, o eso es lo que creo de momento
                           // System.out.println("Jugador " + sigLibre % 8 + " Pasa");
                        } else {
                            //El jugador no va
                            //System.out.println("Jugador " + sigLibre % 8 + " no va");
                            jugadoresMesa.get(sigLibre % jugadoresMesa.size()).setActivo(false);
                        }

                    } else {
                        int aux = maxApuestaActual - jugadoresMesa.get(sigLibre % jugadoresMesa.size()).getFichasApostadas();
                        if(aux>jugadoresMesa.get(sigLibre % jugadoresMesa.size()).getFichas()){
                            aux=jugadoresMesa.get(sigLibre % jugadoresMesa.size()).getFichas();
                            jugadoresMesa.get(sigLibre % jugadoresMesa.size()).setFichas(0);
                        }
                            //int aux = maxApuestaActual - jugadoresMesa.get(sigLibre % jugadoresMesa.size()).getFichasApostadas();
                            //System.out.println("Jugador " + sigLibre % 8 + " Iguala la apuesta actual ");
                            //Aumentar bote
                            AumentarBote(aux);
                            //System.out.println("bote " + bote);
                            //igualar la apuesta minima
                            //primero actualizo las fichas que ha apostado el jugador
                            jugadoresMesa.get(sigLibre % jugadoresMesa.size()).setFichasApostadas(aux);
                            jugadoresMesa.get(sigLibre % jugadoresMesa.size()).settotalFichasApostadas(aux);
                            //Ahora quito las fichas necesarias a jugador
                            //Fichas que tiene el jugador -(diferencia entre la maxima apuesta de la mano y la apuesta actual de jugador)
                        if(jugadoresMesa.get(sigLibre % jugadoresMesa.size()).getFichas()!=0) {
                            jugadoresMesa.get(sigLibre % jugadoresMesa.size()).setFichas(jugadoresMesa.get(sigLibre % jugadoresMesa.size()).getFichas() - aux);
                        }
                    }

                }

            }
            sigLibre++;


        }
    }


}

