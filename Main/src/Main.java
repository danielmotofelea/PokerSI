import java.util.ArrayList;

/**
 * Created by juanmanuelvc on 06/12/2016.
 */
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
    public Jugador generarJugador()
    {
        /**
         *  Se harán las mutaciones de los valores que tengamos y se realizará una llamada
         *  al constructor de Jugador con parametros, inicializando su variable @gen
         */
    }


    /*
    * main realiza la funcion principal de la aplicación
    * */
    public static void main()
    {
        ArrayList<Jugador> generacion = new ArrayList<Jugador>(64);         //ArrayList de jugadores, se pasa a clase Torneo
        ArrayList<Jugador> finalistas = new ArrayList<Jugador>(8);          //ArrayList con los finalistas del anterior torneo
        int numGeneracion = 1;                                              //Numero de la generación en la que nos encontramos

        //Los siguientes comentarios se utilizaran como guia para las funciones que se deben realizar
        /** Se crean los 8 jugadores predeterminados y se añaden a @generacion **/

        /** Se crean, de forma aleatoria, los 56 jugadores restantes de la primera generacion y se añaden a @generacion **/

        do{
        /** Llamada al constructor Torneo(@generacion), para que distribuya a los jugadores por las mesas **/

        /** Una vez finaliza el torneo, deben devolverse los 8 finalistas para añadirlos a @finalistas
         * Esto se hará accediendo a finalistas(suponiendolo protected), en clase Torneo, y copiandolo mediante un for each
         *
         * A continuación, se calcula el fitness de cada uno de los finalistas (jugador.calcularFitness()) y se guardan las estadísticas como corresponda.
         *
         * Despues, se procede a reiniciar las variables que influyen en el fitness en los finalistas, y a
         * la generacion de los jugadores restantes mediante mutación;
         * **/

        //Tras calcular el fitness
        numGeneracion++;
        //se reinician variables de finalistas y se añaden a generacion, sobreescribiendolo
        for (int i = 8; i < 64; i++)
        {
            generacion.set(i, generarJugador()); //Sobreescribimos las posiciones con jugadores mutados
        }
        while(numGeneracion < 100); //Se debe decidir esta condicion de terminacion, si se hace por tecla o por numGeneraciones
    }
}
