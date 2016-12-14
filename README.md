# Parte de Juan
Modificaciones realizadas en la V.3:

- Se incluye al final el atributo fitness y la funcion calcularFitness, aunque la llamada se realice desde main.
- Modo de los atributos puestos en private para controlar el acceso a las variables.
- Consecuencia de la modificacion anterior, se ha implementado setters y getters para modificar los valores de los atributos.
- El valor de las manos ordenadas en orden creciente (carta alta = 0 ... escalera de color = 8).
- Fichas iniciales del jugador inicializadas a 1000.
- Añadido un nuevo atributo, un nuevo array llamado gen.
- Añadida una funcion reset que reinicia el jugador a excepcion del gen (hay que ver si el fitness también se mantiene)
- Constructor con parametros, dado que solo tiene que recibir el array gen, para los demas atributos se utiliza la funcion reset
