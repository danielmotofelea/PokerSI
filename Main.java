package pokerSI;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

import static java.lang.Math.sqrt;


public class Main {

    /**
     * generarJugador servirá para los jugadores de la primera generacion
     * @return Jugador
     */
    private static Jugador generarJugador()
    {
        Jugador j = new Jugador();
        Random rand = new Random();
        double [] gen = new double[j.getGen().length];
        j.setIdentificacion(new int[]{1, 0, 0}); //Las posiciones 1 y 2 se inicializan en Torneo
        System.arraycopy(j.getGen(), 0, gen, 0, j.getGen().length);

        //Inicializamos gen
        for (int i = 0; i < gen.length; i++) {
            gen[i] = rand.nextDouble();
        }
        j.setGen(gen);
        return j;
    }


    /**
     * generarJugadores generará los jugadores de las generaciones 2 a n,
     * mutando y emparejando los valores del gen cuando proceda
     * @param numGeneracion
     * @param generacion
     * @param nuevaGeneracion
     */
    private static void generarJugadores(int numGeneracion, ArrayList<Jugador> generacion, ArrayList<Jugador> nuevaGeneracion)
    {
        Jugador nuevoJugador1 = new Jugador();
        Jugador nuevoJugador2 = new Jugador();
        double [] nuevoGen1 = new double[nuevoJugador1.getGen().length];
        double [] nuevoGen2 = new double[nuevoJugador1.getGen().length];
        Random rand = new Random();
        nuevoJugador1.setIdentificacion(new int[]{numGeneracion, 0, 0}); //Las posiciones 1 y 2 se inicializan en Torneo
        nuevoJugador2.setIdentificacion(new int[]{numGeneracion, 0, 0});

        /**
         * Para annadir la mayor diversidad posible recurriremos tanto a la mutación como a la recombinacion
         *
         * Debido a que usamos un unico gen y una población de entre 50 y 100 individuos (64), utilizaremos
         * una probabilidad de mutación muy baja (0.001) y una probabilidad de emparejamiento alta (0.6).
         *
         * Esta alta probabilidad de emparejamiento garantizará que no hay muchos individuos con el mismo comportamiento.
         *
         * Si no toca emparejar, se copiaran dos jugadores integramente y despues se valorara la mutacion
         */


        if(rand.nextDouble() < 0.6){ //Recombinamos

            /*
                En primer lugar, elegimos dos de los jugadores
             */

            int i1 = 8;
            int i2; //i1 e i2 serviran para controlar que no sacamos dos veces el mismo jugador
            Jugador f1 = new Jugador();
            Jugador f2 = new Jugador();

            for (int i = 0; i < 2; i++) { //se hace lo mismo para los dos jugadores

                i2 = rand.nextInt(generacion.size());

                /* Seleccion de finalistas con probabilidad en funcion del fitness, descartada en la version final
                if(prob < 0.045){ //4.5%
                    i2 = 0;
                }
                else if(prob < 0.1){ //5.5% (+ el anterior)
                    i2 = 1;
                }
                else if(prob < 0.165){ //6.5% (+ anteriores)
                    i2 = 2;
                }
                else if(prob < 0.25){ //8.5%
                    i2 = 3;
                }
                else if(prob < 0.35){ //10%
                    i2 = 4;
                }
                else if(prob < 0.5){ //15%
                    i2 = 5;
                }
                else if(prob < 0.7){ //20%
                    i2 = 6;
                }
                else{ //30%
                    i2 = 7;
                }*/

                if(i < 1) {//primera iteracion
                    i1 = i2;
                    f1 = generacion.get(i1);
                }
                else if(i1 == i2){
                    i--; //sera necesario volver a iterar, pues ha salido dos veces el mismo jugador
                }
                else{ //Segunda iteracion y hemos obtenido dos indices distintos
                    f2 = generacion.get(i2);
                }
            }

            /*
                Una vez seleccionados los jugadores, elegimos a partir de que índice del gen vamos a recombinar.
                Hay que recordar que el proceso de recombinación genera 2 individuos.

                En nuevoJugador1, de 0 a i2 se copia de f1, de i2+1 hasta el final se copia de f2.
                En nuevoJugador2 las posiciones restantes, primero de f2 y luego de f1.

                Ejemplo: i2 = 6. nuevoGen[0] a nuevoGen[5] serán de f1, 6 a 11 de f2.
             */

            i2 = rand.nextInt(nuevoGen1.length); //i2 ahora se usara para controlar el indice de corte del gen

            if(i2 == nuevoGen1.length - 1) // El corte es la ultima posición, se copia entero el gen del jugador f1
            {
                nuevoGen1 = f1.getGen();
                nuevoGen2 = f2.getGen();
            }
            else{
                //Para nuevoJugador1
                System.arraycopy(f1.getGen(), 0, nuevoGen1, 0, i2);
                System.arraycopy(f2.getGen(), i2, nuevoGen1, i2, nuevoGen1.length-i2);

                //Para nuevoJugador2
                System.arraycopy(f2.getGen(), 0, nuevoGen2, 0, i2);
                System.arraycopy(f1.getGen(), i2, nuevoGen2, i2, nuevoGen2.length-i2);

            }
        }
        else{ //No toca recombinar, se copia directamente el gen de un jugador aleatorio

            /*
                Como no vamos a recombinar, puede salir dos veces el mismo jugador
             */
            int i1;

            for (int i = 0; i < 2; i++) { //se hace lo mismo para los dos jugadores

                i1 = rand.nextInt(generacion.size());
                /* Seleccion de finalistas con probabilidad en funcion del fitness, descartada en la version final
                prob = rand.nextDouble();

                if(prob < 0.045){ //4.5%
                    i1 = 0;
                }
                else if(prob < 0.1){ //5.5% (+ el anterior)
                    i1 = 1;
                }
                else if(prob < 0.165){ //6.5% (+ anteriores)
                    i1 = 2;
                }
                else if(prob < 0.25){ //8.5%
                    i1 = 3;
                }
                else if(prob < 0.35){ //10%
                    i1 = 4;
                }
                else if(prob < 0.5){ //15%
                    i1 = 5;
                }
                else if(prob < 0.7){ //20%
                    i1 = 6;
                }
                else{ //30%
                    i1 = 7;
                }*/

                if(i < 1){//primera iteracion
                    nuevoGen1 = generacion.get(i1).getGen();
                }
                else nuevoGen2 = generacion.get(i1).getGen();
            }
        }

        /*
            Ahora pasamos a mirar, por cada posición del gen (ambos jugadores), si debe mutarse o no.
         */
        for (int j = 0; j < 2; j++){ //para cada jugador

            for (int i = 0; i < nuevoGen1.length; i++){ //para cada posicion del gen

                if(rand.nextDouble() < 0.001){ //Mutamos
                    //Mutación de un +-10% como máximo

                    /*
                        nextInt((max - min) + 1) + min, siendo max = 10 y min = - 10
                        Se divide entre 10 para dejarlo como digito decimal.
                        Ejemplo: se genera aleatoriamente el valor 0 --> (0-10)/100 = -0.1 (por el cast)
                                                                     --> 1.0 + (-0.1) = 0.9 (90%, reduccion del 10%)
                     */

                    if(j < 1){ //Toca mutar el nuevoGen1
                        nuevoGen1[i] = nuevoGen1[i] * (1.0 + ((double) rand.nextInt(21) - 10.0) / 100.0);
                    }
                    else nuevoGen2[i] = nuevoGen2[i] * (1.0 + ((double)rand.nextInt(21) - 10.0) / 100.0);
                }
            }
        }


        /*
            Tras haber mutado y/o recombinado como corresponda, se guardan los genes en sus respectivos jugadores
            y se añaden a la nueva generacion.
         */
        nuevoJugador1.setGen(nuevoGen1);
        nuevoJugador2.setGen(nuevoGen2);

        nuevaGeneracion.add(nuevoJugador1);
        nuevaGeneracion.add(nuevoJugador2);

    }


    /*
    * main realiza la funcion principal de la aplicacion
    * */
    public static void main(String [] args)
    {
        ArrayList<Jugador> generacion = new ArrayList<>(64);         //ArrayList de jugadores, se pasa a clase Torneo
        ArrayList<Jugador> finalistas = new ArrayList<>();          //ArrayList con los finalistas del anterior torneo
        int numGeneracion = 1;                                      //Numero de la generación en la que nos encontramos
        Torneo torneo;


        FileWriter salida = null;
        PrintWriter salida2=null;

        FileWriter fic = null;
        PrintWriter fic2=null;
        double linea=0.0;
        double fitnessGrafica=0.0;
        int aux2=0;



        /** Se crean, de forma aleatoria e individual, los 64 jugadores de la primera generacion y se añaden al ArrayList homonimo **/

        for (int i = 0; i < 64; i++) {
            generacion.add(generarJugador());
        }

        do {
            /** Se crea un torneo para que distribuya a los jugadores por las mesas **/

            torneo = new Torneo(generacion, numGeneracion);
            finalistas.addAll(torneo.realizarTorneo());

            /** Una vez finaliza el torneo, deben devolverse los 8 finalistas
             *
             * A continuación, se calcula el fitness de cada uno de los finalistas (jugador.calcularFitness()).
             *
             * Despues, se procede a reiniciar las variables que influyen en el fitness en los finalistas, y a
             * la generacion de los jugadores restantes mediante mutación y recombinación;
             * **/


            //Calculamos fitness y exportamos datos
            try {
                salida = new FileWriter("Fitness.txt", true);
                salida2 = new PrintWriter(salida);
                fic = new FileWriter("ValoresGrafica.txt", true);
                fic2 = new PrintWriter(fic);
                salida2.println("FITNESS DE LA GENERACION "+numGeneracion);
                double fitnessTotal=0.0;
                double varianza = 0.0;
                double fitnessMedia= 0.0;
                double mejorFitness1=0.0;
                double mejorFitness2=0.0;
                double desviacionGrafica=0.0;
                int aux=0;
                for (int i = 0; i < 8; i++) {
                    finalistas.get(i).calcularFitnessMesaFinal();
                    System.out.println("MESA AL CALCULAR EL FITNESS");
                    System.out.println(finalistas.get(i).toString());

                    linea = finalistas.get(i).getFitness();
                    fitnessTotal=fitnessTotal+linea;
                    salida2.println("JUGADOR "+i);
                    salida2.println("Fitness en la mesa final "+finalistas.get(i).getFitnessMesaFinal());
                    salida2.println("Fitness en la mesa clasificadora "+ finalistas.get(i).getFitnessMesaClasificatoria());
                    salida2.println("Media fitness de las dos mesas "+ finalistas.get(i).getFitness());
                    System.out.println(linea);


                }
                fitnessMedia=fitnessTotal/finalistas.size();

                for(int j=0; j<finalistas.size(); j++){
                    varianza=varianza+((finalistas.get(j).getFitness()-(fitnessMedia)) * (finalistas.get(j).getFitness()-(fitnessMedia)));
                }

                for(int i=0;i<finalistas.size();i++){
                    if(finalistas.get(i).getFitness()>mejorFitness1){
                        mejorFitness1=finalistas.get(i).getFitness();
                        aux=i;
                    }
                }

                for(int i=0;i<finalistas.size();i++){
                    if(finalistas.get(i).getFitness()>mejorFitness2&&i!=aux){
                        mejorFitness2=finalistas.get(i).getFitness();
                    }
                }

                varianza=varianza/finalistas.size();

                if(numGeneracion%10!=0){
                    fitnessGrafica+=(mejorFitness1+mejorFitness2)/2;
                    desviacionGrafica+=sqrt(varianza);
                }else{

                    if(numGeneracion==10) {
                        fic2.println("Generaciones " + (numGeneracion - 9) + " a " + numGeneracion);
                        fic2.println("Media de los fitness: " + fitnessGrafica / 9);
                        fic2.println("Desviacion media de los fitness: " + desviacionGrafica / 9);
                    }
                    else{
                        fic2.println("Generaciones " + (numGeneracion - 10) + " a " + numGeneracion);
                        fic2.println("Media de los fitness: " + fitnessGrafica / 10);
                        fic2.println("Desviacion media de los fitness: " + desviacionGrafica / 10);
                    }
                    fic2.println("/////////////////");
                    fitnessGrafica=(mejorFitness1+mejorFitness2)/2;
                    desviacionGrafica=sqrt(varianza);
                }
                
                salida2.println("La media de los dos mejores fitness de la generacion "+numGeneracion+" es "+(mejorFitness1+mejorFitness2)/2);
                salida2.println("La desviacion de los fitness de la generacion "+numGeneracion+" es "+ sqrt(varianza));
                salida2.println();
                salida2.println();
                salida.close();
                salida2.close();
                fic.close();
                fic2.close();
            }catch (Exception e) {
                System.out.println(e.getMessage());
            }

            numGeneracion++;

            /*
             *  Reordenamos los finalistas en función de su fitness, en orden ascendente
             */
            Collections.sort(finalistas, new Comparator<Jugador>() {
                public int compare(Jugador j1, Jugador j2) {
                    return new Double(j1.getFitness()).compareTo(new Double(j2.getFitness()));
                }
            });
            for (Jugador j: finalistas) {
                j.gradoDeSoporte(j.getIdentificacion()[2], j.getIdentificacion()[0]);
            }
           /*
                Como solo consevaremos los dos finalistas de mayor fitness, eliminamos al resto
                 y utilizamos el arraylist para guardar la nueva generacion
            */

            for (int i = 0; i < 6; i++) {
                finalistas.remove(0); //Borramos todos menos los dos mejores, que se conservarán para la siguiente generacion
            }

            for (int i = 2; i < 64; i+=2) {
                generarJugadores(numGeneracion, generacion, finalistas);//Generamos 2 jugadores cada vez, y los añadimos a generacion
            }

            //Ya se han generado todos los jugadores, luego se puede pasar los jugadores que hay en finalistas a generacion y volver a limpiar
            generacion.clear();
            generacion.addAll(finalistas);
            finalistas.clear();

            /*
                Limpiamos memoria antes de volver a iniciar el bucle
             */
            Runtime garbage = Runtime.getRuntime();
            garbage.gc();

        }while(numGeneracion < 1000);
    }
}
