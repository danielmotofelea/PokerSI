package Version1;

/**
 * Primeras pruebas realizadas a carta y baraja.
 * Se crea una baraja y despues se muestra su contenido.
 * se hace uso del metodo SacarPrimeraCarta.
 */
public class Main {
    public static void main(String arg[]) {
           Baraja bar = new Baraja();
           bar.MostrarBaraja();

           Cartas cartaPrueba1,cartaPrueba2;
           cartaPrueba1=bar.SacarPrimeraCarta();
           cartaPrueba2=bar.SacarPrimeraCarta();
           System.out.print("\n"+cartaPrueba1.toString());
           System.out.print("\n"+cartaPrueba2.toString());

        }





}
