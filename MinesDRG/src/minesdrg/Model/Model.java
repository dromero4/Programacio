package minesdrg.Model;
import minesdrg.Vista.Vista;

    /**
    * Classe per inicialitzar els arrays bidimiensionals i verificar posicions.
    */
public class Model {
    private static int files, columnes, bombes;
    private static boolean jocFinalitzat = false;
    private static char[][] minesOcultSolucio;
    private static char[][] campMinesVisible;

    private static int filesAleatories, columnesAleatories;
    private static int numeroBombas;


        /**
         * Funcio per inicialitzar el joc
         * @param f files de l'usuari
         * @param c columnes de l'usuari
         * @param b bombes que escull l'usuari
         */
    public static void inicialitzarJoc(int f, int c, int b){

        //Inicialitzar las variables
        files = f + 2;
        columnes = c + 2;
        bombes = b;

        //Crear i inicialitzar els arrays bidimensionals
        minesOcultSolucio = new char[files][columnes];
        campMinesVisible = new char[files][columnes];

        tauler(campMinesVisible, '·');
        tauler(minesOcultSolucio, ' ');

        System.out.println("----------------------------------------");

        //Repartir les bombes a llocs aleatoris
        bombasAleatorias();

        //Funcio per detectar les bombes properes
        numeroBombas();

        //Mostrar el tauler de joc
        Vista.mostrarCampMines(campMinesVisible);

        //verificar les supostes dades errònies de l'usuari
        boolean verificar;

        for (int i = 0; i < f; i++) {
            for (int j = 0; j < c; j++) {
               verificar  = verificarFC(i, j);
            }
        }

    }
// -----------------------------------------------------------------

    /**
     * Funció per ficar a la casella el numero de bombes que hi ha al voltant.
     * @param f la fila que ha marcat el jugador.
     * @param c la columna que ha marcat el jugador.
     * @return retorna el valor (int) de bombes que hi ha al voltant.
     */
    public static int comptador(int f, int c){
        int comptadorBombes = 0;

        if (minesOcultSolucio[f-1][c] == 'B'){comptadorBombes++;}
        if (minesOcultSolucio[f+1][c] == 'B'){comptadorBombes++;}
        if (minesOcultSolucio[f][c-1] == 'B'){comptadorBombes++;}
        if (minesOcultSolucio[f][c+1] == 'B'){comptadorBombes++;}
        if (minesOcultSolucio[f+1][c-1] == 'B'){comptadorBombes++;}
        if (minesOcultSolucio[f+1][c+1] == 'B'){comptadorBombes++;}
        if (minesOcultSolucio[f-1][c+1] == 'B'){comptadorBombes++;}
        if (minesOcultSolucio[f-1][c-1] == 'B'){comptadorBombes++;}

        return comptadorBombes;
    }

    /**
     * Funció per verificar si la casella que ha ficat el jugador es vàlida o no.
     * @param f la fila que indica el jugador.
     * @param c la columna que indica el jugador.
     * @return retorna el valor (boolea) per indicar si es vàlida o no.
     */
    public static boolean verificarFC(int f, int c){
        boolean verificar = true;

        if (f < 0 || c < 0 || f >= files || c >= columnes){
            verificar = false;
        }

        return verificar;
    }

    /**
     *
     * @param tauler
     * @param l
     */
    private static void tauler(char[][] tauler, char l){
        for (int i = 0; i < files; i++) {
            for (int j = 0; j < columnes; j++) {
                tauler[i][j] = l;
            }
        }
    }

    /**
     * Funció per ficar aleatoriament les bombes al tauler.
     */
    private static void bombasAleatorias(){
        for (int i = 0; i < bombes; i++) {
            filesAleatories = (int) (Math.random() * (files - 2) + 1);
            columnesAleatories = (int) (Math.random() * (columnes - 2) + 1);
            minesOcultSolucio[filesAleatories][columnesAleatories] = 'B';

        }
    }

    /**
     * Funció derivada de "comptador()" que fica a la casella el numero de bombes.
     */
    private static void numeroBombas(){
        for (int i = 1; i < files - 1; i++) {
            for (int j = 1; j < columnes - 1; j++) {
                if (minesOcultSolucio[i][j] != 'B') {
                    numeroBombas = comptador(i, j);
                    minesOcultSolucio[i][j] = (char) (numeroBombas + '0');
                }
            }
        }
    }

    /**
     * Funció per indicar quina casella vols destapar (de forma recursiva)
     * @param files la fila que l'usuari vol destapar
     * @param columnes la columna que l'usuari vol destapar
     */
    public static void trepitjar(int files, int columnes){
        if (!verificarFC(files,columnes)) return;
        if (campMinesVisible[files][columnes] != '·'){
            return;
        }
        campMinesVisible[files][columnes] = minesOcultSolucio[files][columnes];
        if (campMinesVisible[files][columnes] == '0'){
            trepitjar(files-1, columnes);
            trepitjar(files+1, columnes);
            trepitjar(files, columnes-1);
            trepitjar(files, columnes+1);
            trepitjar(files+1, columnes-1);
            trepitjar(files+1, columnes+1);
            trepitjar(files-1, columnes+1);
            trepitjar(files-1, columnes-1);
        }
    }

    /**
     * Funció per posar una bandera a on creus que hi ha una bomba.
     * @param files fila que l'usuari ha indicat
     * @param columnes columna que l'usuari ha indicat
     */
    public static void bandera(int files, int columnes){
        if (campMinesVisible[files][columnes] == '·'){
            campMinesVisible[files][columnes] = 'B';
            Vista.mostrarCampMines(campMinesVisible);
        } else if (campMinesVisible[files][columnes] == 'B'){
            campMinesVisible[files][columnes] = '·';
            Vista.mostrarCampMines(campMinesVisible);
        }
    }

    /**
     * Funció per detectar si hi ha una bomba.
     * @param files fila que l'usuari ha indicat
     * @param columnes columna que l'usuari ha indicat
     */
    public static void mirarSiHiHaBombes(int files, int columnes){
        if (campMinesVisible[files][columnes] == 'B'){
            Vista.missatge("S'ha acabat el joc");
            solucioFinal();
            System.exit(0);
        }
    }

    /**
     * Funció per detectar si el joc s'ha acabat
     * @param tauler1 tauler visible per l'usuari
     * @param tauler2 solucio del tauler
     */
    private static void isJocFinalitzat(char[][] tauler1, char[][] tauler2){

        for (int i = 1; i < files - 1; i++) {
            for (int j = 1; j < columnes - 1; j++) {
                if (tauler1[i][j] != tauler2[i][j]) {
                    return;
                }
            }
        }
        jocFinalitzat = true;
    }

    /**
     * Funció per mostrar la solució en cas de perdre.
     */
    private static void solucioFinal(){
        Vista.mostrarCampMines(minesOcultSolucio);
    }

    /**
     * Funcio per acabar el joc un cop finalitzat
     * @return true or false.
     */
    public static boolean finaljoc(){
        isJocFinalitzat(minesOcultSolucio, campMinesVisible);
        if (jocFinalitzat){
            Vista.mostrarCampMines(minesOcultSolucio);
        }
        return jocFinalitzat;
    }

        /**
         * Funció per poder trucar a la funcio de manera publica desde la classe controlador
         * @param files la fila que ha escollit l'usuari
         * @param columnes la columna que ha escollit l'usuari
         */
    public static void tr(int files, int columnes){
        Vista.mostrarCampMines(campMinesVisible);
        trepitjar(files, columnes);
    }
}
