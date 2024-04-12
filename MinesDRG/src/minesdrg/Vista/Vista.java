package minesdrg.Vista;

public class Vista {
    /**
     * Classe per mostrar el tauler que vulguis per pantalla
     * @param tauler el tauler que vulguis mostrar per pantalla
     */
    public static void mostrarCampMines(char[][] tauler){
        int files = tauler.length;
        int columnes = tauler[0].length;

        System.out.print("  ");

        for (int i = 1; i <= files - 2; i++) {
            System.out.print(i + " ");
        }

        System.out.println();

        char l = 'A';
        for (int i = 1; i < files - 1; i++) {
            System.out.print(l + " ");
            for (int j = 1; j < columnes - 1; j++) {
                System.out.print(tauler[i][j] + " ");
            }
            System.out.println();
            ++l;
        }
    }

    /**
     * Funcio per mostrar el missatge que vulguis
     * @param mensaje el missatge que vols mostrar
     */
    public static void missatge(String mensaje){
        System.out.println(mensaje);
    }
}

