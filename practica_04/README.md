# Teoría


1. Explica porqué tu solución si funciona, para esto puedes demostrar las propiedades de
   deadlock-free, libre de hambruna, lock-free, wait-free.

   Funciona por varias razones:
    - **Deadlock-free**: Básicamente, no hay bloqueos en el sistema. Gracias a cómo diseñamos `WFSnapshot`, los hilos pueden hacer sus cosas sin estorbarse entre sí.
    - **Libre de hambruna**: Cada cliente que llega sabe que será atendido. Todo lo que hace es registrar su tiempo de llegada y luego puede saber cuándo le tocará.
    - **Lock-free**: Aquí la magia es que no usamos bloqueos de la manera tradicional. Usamos algo llamado operaciones atómicas y sellos temporales para que todo sea fluido. ((;
    - **Wait-free**: En resumen, el diseño asegura que si un hilo llama al método `scan()`, obtendrá una respuesta. No estará ahí colgado esperando para siempre.


2. ¿Qué modificacion tendrías que hacer para que tuvieramos m consumidores?
    
    Si queremos tener más consumidores, digamos m, es fácil. Solo cambiamos el tamaño del arreglo `snapshot` a m  y lanzamos  m  hilos en lugar de 10. Así tendremos  m  clientes listos para ser atendidos.
 

3. Supón que posees más atributos, de tal manera que tienes un atributo que almacena los
   kg de masa disponibles, así tambien de otro que almacena la cantidad de masa requerida
   para hacaer 1 tortilla, ¿Qué modificación tendrías que hacer para que esto funcionara?
    
   Tendríamos que agregar esos dos valores en la clase y cada vez que vendas tortillas, restar de la masa disponible. Y claro, antes de vender, verificar que haya suficiente masa.


4. Para el problema de la tortilleria, como podríamos hacer para tener una fila dinámica, es
   decir que las personas llegaran sin tener que agruparlas o en bloques estaticos. Explica tu
   solución.

   Para hacer la fila de clientes más dinámica, podríamos usar una lista enlazada en lugar de un arreglo. Los clientes llegan, se agregan a la lista, y si se van, se quitan. Y para simular la llegada de nuevos clientes, podríamos usar algo más avanzado que simplemente lanzar un número fijo de hilos.
        

5. Da comentarios de lo que aprendiste en esta practica.

    Finalmente, en esta práctica, nos dimos cuenta de lo importante que es manejar muchos procesos a la vez y cómo podemos simular situaciones de la vida real, como una fila en una tortillería de Kassandra, También aprendí un montón sobre cómo asegurarme de que todos sean atendidos justamente y sin esperar demasiado.

