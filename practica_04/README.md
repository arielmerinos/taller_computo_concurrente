Bueno, mira, la solución que hice funciona por varias razones:

- **Deadlock-free**: Básicamente, no hay bloqueos en el sistema. Gracias a cómo diseñamos `WFSnapshot`, los hilos pueden hacer sus cosas sin estorbarse entre sí.
- **Libre de hambruna**: Cada cliente que llega sabe que será atendido. Todo lo que hace es registrar su tiempo de llegada y luego puede saber cuándo le tocará.
- **Lock-free**: Aquí la magia es que no usamos bloqueos de la manera tradicional. Usamos algo llamado operaciones atómicas y sellos temporales para que todo sea fluido. ((;
- **Wait-free**: En resumen, el diseño asegura que si un hilo llama al método `scan()`, obtendrá una respuesta. No estará ahí colgado esperando para siempre.

Ahora, si queremos tener más consumidores, digamos \( m \), es fácil. Solo cambiamos el tamaño del arreglo `snapshot` a \( m \) y lanzamos \( m \) hilos en lugar de 10. Así tendremos \( m \) clientes listos para ser atendidos.

Si metemos más variables al juego, como la cantidad de masa disponible y cuánta se necesita para una tortilla, hay que hacer algunos ajustes. Tendríamos que agregar esos dos valores en la clase y cada vez que vendas tortillas, restar de la masa disponible. Y claro, antes de vender, verificar que haya suficiente masa.

Para hacer la fila de clientes más dinámica, podríamos usar una lista enlazada en lugar de un arreglo. Los clientes llegan, se agregan a la lista, y si se van, se quitan. Y para simular la llegada de nuevos clientes, podríamos usar algo más avanzado que simplemente lanzar un número fijo de hilos.

Finalmente, en esta práctica, nos dimos cuenta de lo importante que es manejar muchos procesos a la vez y cómo podemos simular situaciones de la vida real, como una fila en una tortillería de Kassandra, También aprendí un montón sobre cómo asegurarme de que todos sean atendidos justamente y sin esperar demasiado.
