package minesdrg.Controlador;
import minesdrg.Model.Model;
import minesdrg.Vista.Vista;

import java.util.Scanner;

/**
 * Classe controlador
 */
public class Controlador {

    private static Scanner scan = new Scanner(System.in);
    private static int numeroFila, TColumnes;

    /**
     * Funció per començar a jugar
     */
    public static void jugar(){
        int files, columnes, bombes;
        char opcio;
        char TFiles;

        System.out.println("Quantes files vols? ");
        files = scan.nextInt();

        System.out.println("Quantes columnes vols? ");
        columnes = scan.nextInt();

        System.out.println("Quantes bombes vols? ");
        bombes = scan.nextInt();

        if ((files * columnes) > bombes) {
            Model.inicialitzarJoc(files, columnes, bombes);
        } else {
            Vista.missatge("Hi ha masses bombes.");
            System.exit(0);
        }

        do {
            System.out.println("Vols (T)repitjar, posar (B)andera o (A)cabar ");
            opcio = scan.next().toUpperCase().charAt(0);
            scan.nextLine();

            if (opcio == 'T'){
                System.out.println("A on vols trepitjar? EX: C 4");
                TFiles = scan.next().toUpperCase().charAt(0);
                TColumnes = scan.nextInt();
                scan.nextLine();

                numeroFila = TFiles - 'A' + 1;

                Model.trepitjar(numeroFila, TColumnes);
                Model.tr(numeroFila, TColumnes);

                Model.mirarSiHiHaBombes(numeroFila, TColumnes);

            } else if (opcio == 'B'){
                System.out.println("A on vols trepitjar? EX: C 4");

                TFiles = scan.next().toUpperCase().charAt(0);
                TColumnes = scan.nextInt();
                scan.nextLine();

                numeroFila = TFiles - 'A' + 1;
                Model.bandera(numeroFila, TColumnes);
            }

        } while (opcio != 'A' && !Model.finaljoc());

        Vista.missatge("S'ha acabat el joc...");
    }
}




