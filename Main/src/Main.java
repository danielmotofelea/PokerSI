package pokerSI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;


public class Main {

    /*
    *   generarJugador deberá recibir los jugadores de la mesa final
    *   del torneo anterior y utilizar los valores de sus "genes" para
    *   realizar mutaciones y generar jugadores nuevos.
    *
    *   Se tomaran los atributos de un jugador elegido aleatoriamente
    *   entre los que forman parte de la mesa final.
    *
    *   El jugador generado se devolverá a la funcion main para introducirlo
    *   en generacion, encargandose dicha funcion de gestionar todo el proceso
    *   de generacion de jugadores, llamando 56 veces a esta funcion.
    *   Los 8 jugadores restantes se obtienen de la mesa final (atributo en clase Torneo)
    *   y por tanto no es necesario generarlos
    *
    * */

    /**
     * generarJugador servirá para los jugadores de la primera generacion
     * @return Jugador
     */
    public static  Jugador generarJugador()
    {
        Jugador j = new Jugador();
        Random rand = new Random(); //rand.nextDouble() devuelve numero entre 0 y 1, para gen.
        double [] gen = new double[j.getGen().length];
        j.setIdentificacion(new int[]{1, 0, 0}); //Las posiciones 1 y 2 se inicializan en Torneo
        System.arraycopy(j.getGen(), 0, gen, 0, j.getGen().length);

        //Inicializamos gen
        for (int i = 0; i < gen.length; i++) {
            gen[i] = rand.nextDouble(); //TODO revisar agresividad, pues no se ha definido que valores puede tomar
        }
        return j;
    }


    /**
     * generarJugadores generará los jugadores de las generaciones 2 a n,
     * mutando y emparejando los valores del gen cuando proceda
     * @param numGeneracion
     * @param posicion : posicion de referencia para insertar los jugadores en generacion
     * @param generacion
     * @param finalistas
     */
    public static void generarJugadores(int numGeneracion, int posicion,ArrayList<Jugador> generacion, ArrayList<Jugador> finalistas)
    {
        Jugador nuevoJugador1 = new Jugador();
        Jugador nuevoJugador2 = new Jugador();
        double [] nuevoGen1 = new double[nuevoJugador1.getGen().length];
        double [] nuevoGen2 = new double[nuevoJugador1.getGen().length];
        Random rand = new Random();
        nuevoJugador1.setIdentificacion(new int[]{numGeneracion, 0, 0}); //Las posiciones 1 y 2 se inicializan en Torneo
        nuevoJugador2.setIdentificacion(new int[]{numGeneracion, 0, 0});

        /**
         * Para annadir la mayor diversidad posible recurriremos tanto a la mutación como al emparejamiento
         *
         * Debido a que usamos un único cromosoma y una población de entre 50 y 100 individuos (64), utilizaremos
         * una probabilidad de mutación muy baja (0.001) y una probabilidad de emparejamiento alta (0.6).
         *
         * Esta alta probabilidad de emparejamiento garantizará que no hay muchos individuos con el mismo comportamiento.
         */

        if(rand.nextDouble() < 0.6) //Emparejamos
        {
            //En primer lugar, elegimos dos de los finalistas
            /*
                TODO cambiar la seleccion para que sea más probable seleccionar a los de mejor fitness
             */
            int i1, i2;
            i1 = rand.nextInt(9); //Número aleatorio entre 0 y 8
            do{
                i2 = rand.nextInt(9);
            }while (i2 == i1); //Garantizamos que no se elige dos veces al mismo jugador

            Jugador f1 = finalistas.get(i1);
            Jugador f2 = finalistas.get(i2);

            /*
                Una vez elegidos los finalistas, elegimos a partir de que índice del gen vamos a emparejar.
                Hay que recordar que el proceso de emparejamiento genera 2 individuos.

                En nuevoJugador1, de 0 a i2 se copia de f1, de i2+1 hasta el final se copia de f2.
                En nuevoJugador2 las posiciones restantes, primero de f2 y luego de f1.

                Como f1 tendrá, probablemente, mayor fitness que f2 y, además, el gen es de longitud par (12),
                se contemplará que siempre habrá parte del gen de f1 formando al nuevo jugador, no así con f2 que no
                aportará nada en el caso de que i2 = 11 (ultima posición).

                Ejemplo: i2 = 6. nuevoGen[0] a nuevoGen[5] serán de f1, 6 a 11 de f2.
             */

            i2 = rand.nextInt(nuevoJugador1.getGen().length);
            if(i2 == nuevoJugador1.getGen().length - 1) // El corte es la ultima posición, se copia entero el gen del jugador f1
            {
                nuevoGen1 = f1.getGen();
                nuevoGen2 = f2.getGen();
            }
            else{
                //Para nuevoJugador1
                System.arraycopy(f1.getGen(), 0, nuevoGen1, 0, i2); //i2 expresa longitud, no posición
                System.arraycopy(f2.getGen(), i2, nuevoGen1, i2, nuevoGen1.length); //Aquí i2 si representa una posición
                nuevoJugador1.setGen(nuevoGen1);

                //Para nuevoJugador2
                System.arraycopy(f2.getGen(), 0, nuevoGen2, 0, i2);
                System.arraycopy(f1.getGen(), i2, nuevoGen2, i2, nuevoGen2.length);

            }
        }else { //No toca emparejar, se copia directamente el gen de un finalista aleatorio (mas probabilidad cuanto mas fitness)
                /*
                    TODO definir la selección en función del fitness
                 */
        }

        /*
            Ahora pasamos a mirar, por cada posición del gen (ambos jugadores), si debe mutarse o no.
         */
        for (int i = 0; i < nuevoJugador1.getGen().length; i++) {
            if(rand.nextDouble() < 0.03){ //Mutamos
                //Mutación de un +-10% como máximo

                /*
                    nextInt((max - min) + 1) + min, siendo max = 10 y min = - 10
                    Se divide entre 10 para dejarlo como digito decimal.
                    Ejemplo: se genera aleatoriamente el valor 0 --> (0-10)/10 = -0.1 (por el cast)
                                                                 --> 1.0 + (-0.1) = 0.9 (90%, reduccion del 10%)
                 */
                nuevoGen1[i] = nuevoGen1[i] * (1.0 + ((double)rand.nextInt(21) - 10)/10);
                nuevoGen2[i] = nuevoGen2[i] * (1.0 + ((double)rand.nextInt(21) - 10)/10);
            }
        }

        /*
            Tras haber mutado y/o emparejado como corresponda, se guardan los genes en sus respectivos jugadores
            y se añaden a generacion.
         */
        nuevoJugador1.setGen(nuevoGen1);
        nuevoJugador2.setGen(nuevoGen2);

        generacion.set(posicion, nuevoJugador1);
        generacion.set(posicion+1, nuevoJugador2);

    }


    /*
    * main realiza la funcion principal de la aplicacion
    * */
    public static void main(String [] args)
    {
        ArrayList<Jugador> generacion = new ArrayList<Jugador>(64);         //ArrayList de jugadores, se pasa a clase Torneo
        ArrayList<Jugador> finalistas = new ArrayList<Jugador>(8);          //ArrayList con los finalistas del anterior torneo
        int numGeneracion = 1;                                              //Numero de la generación en la que nos encontramos

        //Los siguientes comentarios se utilizaran como guia para las funciones que se deben realizar
        /** Se crean los 8 jugadores predeterminados y se añaden a @generacion **/

        /** Se crean, de forma aleatoria, los 56 jugadores restantes de la primera generacion y se añaden a @generacion **/

        for (int i = 8; i < 64; i++) {
            generacion.set(i, generarJugador());
        }

        do {
            /** Llamada al constructor Torneo(@generacion), para que distribuya a los jugadores por las mesas **/

            /** Una vez finaliza el torneo, deben devolverse los 8 finalistas para añadirlos a @finalistas
             *
             * A continuación, se calcula el fitness de cada uno de los finalistas (jugador.calcularFitness()) y se guardan las estadísticas como corresponda.
             *
             * Despues, se procede a reiniciar las variables que influyen en el fitness en los finalistas, y a
             * la generacion de los jugadores restantes mediante mutación;
             * **/

            /*
             * Reordenamos los finalistas en función de su fitness, cuanto mayor sea su indice en el arraylist, mayor sera su fitness
             */
            Collections.sort(finalistas, new Comparator<Jugador>() {
                public int compare(Jugador j1, Jugador j2) {
                    return new Double(j1.getFitness()).compareTo(new Double(j2.getFitness()));
                }
            });

            generacion.clear(); //Los finalistas se han guardado, el resto desaparecen
            //Calculamos fitness, exportamos datos, metemos los finalistas en la nueva generación y los reiniciamos.
            for (int i = 0; i < 8; i++) {
                finalistas.get(i).calcularFitness();
                /**
                 * Tras calcularlo, se pasan los datos a fichero cuando corresponda.
                 * Si es necesario, se puede separar este bucle en dos partes para facilitar la implementación.
                 */
                generacion.set(i, finalistas.get(i));
                generacion.get(i).resetAtributosJugador();
            }

            numGeneracion++; /** declarado aquí por si hay que exportar numGeneracion a fichero en el bucle anterior*/

            for (int i = 8; i < 64; i+=2) {
                generarJugadores(numGeneracion, i, generacion, finalistas);//Generamos 2 jugadores cada vez, y los añadimos a generacion
            }

            //Ya se han generado todos los jugadores, luego se puede limpiar finalistas
            finalistas.clear();

        }while(numGeneracion < 100); /** Se debe decidir esta condicion de terminacion, si se hace por tecla o por numGeneraciones*/
    }
}