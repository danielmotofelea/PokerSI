package Version1;
import java.util.*;

/**
 * Objeto baraja. En este momento solo se puede crear una baraja, cuyas cartas estan mezcladas y sacar el primer objeto de esa baraja.
 */
public class Baraja {

    /** Esta es la estrucutra de datos que se ha utilizado para almacenar las cartas que forman la baraja **/
    ArrayList<Cartas> b =new ArrayList<Cartas>();

    /** Constructor. Llena la baraja de cartas y luego las mezcla con el metodo shuffle.
     * Tengo pensado hacer el constructor mas limpio, para no crear los objetos uno a un
     */

    public Baraja() {
        Cartas carta1=new Cartas("2","Corazones");
        Cartas carta2=new Cartas("3","Corazones");
        Cartas carta3=new Cartas("4","Corazones");
        Cartas carta4=new Cartas("5","Corazones");
        Cartas carta5=new Cartas("6","Corazones");
        Cartas carta6=new Cartas("7","Corazones");
        Cartas carta7=new Cartas("8","Corazones");
        Cartas carta8=new Cartas("9","Corazones");
        Cartas carta9=new Cartas("10","Corazones");
        Cartas carta10=new Cartas("J","Corazones");
        Cartas carta11=new Cartas("Q","Corazones");
        Cartas carta12=new Cartas("K","Corazones");
        Cartas carta13=new Cartas("A","Corazones");

        Cartas carta14=new Cartas("2","Treboles");
        Cartas carta15=new Cartas("3","Treboles");
        Cartas carta16=new Cartas("4","Treboles");
        Cartas carta17=new Cartas("5","Treboles");
        Cartas carta18=new Cartas("6","Treboles");
        Cartas carta19=new Cartas("7","Treboles");
        Cartas carta20=new Cartas("8","Treboles");
        Cartas carta21=new Cartas("9","Treboles");
        Cartas carta22=new Cartas("10","Treboles");
        Cartas carta23=new Cartas("J","Treboles");
        Cartas carta24=new Cartas("Q","Treboles");
        Cartas carta25=new Cartas("K","Treboles");
        Cartas carta26=new Cartas("A","Treboles");

        Cartas carta27=new Cartas("2","Diamantes");
        Cartas carta28=new Cartas("3","Diamantes");
        Cartas carta29=new Cartas("4","Diamantes");
        Cartas carta30=new Cartas("5","Diamantes");
        Cartas carta31=new Cartas("6","Diamantes");
        Cartas carta32=new Cartas("7","Diamantes");
        Cartas carta33=new Cartas("8","Diamantes");
        Cartas carta34=new Cartas("9","Diamantes");
        Cartas carta35=new Cartas("10","Diamantes");
        Cartas carta36=new Cartas("J","Diamantes");
        Cartas carta37=new Cartas("Q","Diamantes");
        Cartas carta38=new Cartas("K","Diamantes");
        Cartas carta39=new Cartas("A","Diamantes");

        Cartas carta40=new Cartas("2","Diamantes");
        Cartas carta41=new Cartas("3","Diamantes");
        Cartas carta42=new Cartas("4","Diamantes");
        Cartas carta43=new Cartas("5","Diamantes");
        Cartas carta44=new Cartas("6","Diamantes");
        Cartas carta45=new Cartas("7","Diamantes");
        Cartas carta46=new Cartas("8","Diamantes");
        Cartas carta47=new Cartas("9","Diamantes");
        Cartas carta48=new Cartas("10","Diamantes");
        Cartas carta49=new Cartas("J","Diamantes");
        Cartas carta50=new Cartas("Q","Diamantes");
        Cartas carta51=new Cartas("K","Diamantes");
        Cartas carta52=new Cartas("A","Diamantes");

        b.add(carta1);
        b.add(carta2);
        b.add(carta3);
        b.add(carta4);
        b.add(carta5);
        b.add(carta6);
        b.add(carta7);
        b.add(carta8);
        b.add(carta9);
        b.add(carta10);
        b.add(carta11);
        b.add(carta12);
        b.add(carta13);
        b.add(carta14);
        b.add(carta15);
        b.add(carta16);
        b.add(carta17);
        b.add(carta18);
        b.add(carta19);
        b.add(carta20);
        b.add(carta21);
        b.add(carta22);
        b.add(carta23);
        b.add(carta24);
        b.add(carta25);
        b.add(carta26);
        b.add(carta27);
        b.add(carta28);
        b.add(carta29);
        b.add(carta30);
        b.add(carta31);
        b.add(carta32);
        b.add(carta33);
        b.add(carta34);
        b.add(carta35);
        b.add(carta36);
        b.add(carta37);
        b.add(carta38);
        b.add(carta39);
        b.add(carta40);
        b.add(carta41);
        b.add(carta42);
        b.add(carta43);
        b.add(carta44);
        b.add(carta45);
        b.add(carta46);
        b.add(carta47);
        b.add(carta48);
        b.add(carta49);
        b.add(carta50);
        b.add(carta51);
        b.add(carta52);

        Collections.shuffle(b);

    }

    /**
     * Este metodo impreme el contenido de la baraja por pantalla. Solo sirve para comprobar el funcionamiento de la baraja.
     * No tiene otro uso en la aplicacion final.
     */
    public void MostrarBaraja(){
        Iterator<Cartas> nombreIterator = b.iterator();
        while(nombreIterator.hasNext()){
            Cartas carta = nombreIterator.next();
            System.out.print(carta.toString()+" / ");
        }

    }

    /**
     * Este metodo devuelve un objeto de tipo cartsd que se corresponde a la primera que hay en la baraja. Antes de devolverla la borra
     * Todavia hay que considerar casos de erros, asi que hay que tener cuidado con el contenido de la baraja antes de usar este metodo
     */

    public Cartas SacarPrimeraCarta(){
        Cartas cartaRet = b.get(0);
        b.remove(0);
        return cartaRet;
    }
    /**
     *Ire aniadiendo mas metodos segun se vayan necesitando
     */



}
