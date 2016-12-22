package pokerSI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

/**
 * Objeto baraja. En este momento solo se puede crear una baraja, cuyas cartas estan mezcladas y sacar el primer objeto de esa baraja.
 */
public class Baraja {

    /** Esta es la estrucutra de datos que se ha utilizado para almacenar las cartas que forman la baraja **/
     private ArrayList<Carta> b =new ArrayList<Carta>();

    /** Constructor. Llena la baraja de cartas y luego las mezcla con el metodo shuffle.
     * Tengo pensado hacer el constructor mas limpio, para no crear los objetos uno a un
     */

    public Baraja() {

        for (int palo = 1; palo <= 4; palo++)
        {
            //1: Corazones,     2:  Picas,   3:  Diamantes,  4:  Treboles
            for (int valor = 2; valor <= 14; valor++)
            {
                //valores en orden ascendente, A = 14 por ser la carta más alta
                b.add(new Carta(valor, palo));
            }
        }

        Collections.shuffle(b);

    }

    /**
     * Este metodo impreme el contenido de la baraja por pantalla. Solo sirve para comprobar el funcionamiento de la baraja.
     * No tiene otro uso en la aplicacion final.
     */
    public void mostrarBaraja(){
        Iterator<Carta> nombreIterator = b.iterator();
        while(nombreIterator.hasNext()){
            Carta carta = nombreIterator.next();
            System.out.print(carta.toString()+" / ");
        }

    }

    /**
     * Este metodo devuelve un objeto de tipo carta que se corresponde a la primera que hay en la baraja. Antes de devolverla la borra
     */

    public Carta sacarPrimeraCarta(){
        Carta cartaRet = b.get(0);
        b.remove(0);
        return cartaRet;
    }
    /**
     * El siguiente método sirve en caso de necesitar el tamaño de la baraja. Usado inicialmente para pruebas
     */
    public int cartasRestantes()
    {
        return b.size();
    }



}
