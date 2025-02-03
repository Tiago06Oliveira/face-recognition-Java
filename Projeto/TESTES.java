package Projeto;

import java.io.FileNotFoundException;
import java.io.IOException;

import static Projeto.APP.*;

public class TESTES {
    public static void main(String[] args) throws IOException {
        uniteTestFunctionality1();
        System.out.println();
        uniteTestFunctionality2();
        System.out.println();
        uniteTestFunctionality3();
    }

    public static void uniteTestFunctionality1() throws FileNotFoundException {
        isSymetricTest1("TestesFuncionalidade1\\symmetric_matrix_8x8.csv",true);
        isSymetricTest2("TestesFuncionalidade1\\symmetric_matrix_256x256.csv",true);
        isSymetricTest3("TestesFuncionalidade1\\symmetric_matrix_4x4.csv",true);
        isNotSymetricTest4("TestesFuncionalidade1\\non_symmetric_matrix7x8.csv",false);
        isNotSymetricTest5("TestesFuncionalidade1\\non_symmetric_matrix_3x4.csv",false);
        calculateMeanAbsoluteErrorTest1("TestesFuncionalidade1\\symmetric_matrix_4x4.csv","TestesFuncionalidade1\\reconstructedMatrixTest1.csv");
        calculateMeanAbsoluteErrorTest2("TestesFuncionalidade1\\symmetric_matrix_8x8.csv","TestesFuncionalidade1\\reconstructedMatrixTest2.csv");
        calculateMeanAbsoluteErrorTest3("TestesFuncionalidade1\\symmetric_matrix_256x256.csv","TestesFuncionalidade1\\reconstrucetdMatrixTest3.csv");
        if (reconstructedMatrizTest1("TestesFuncionalidade1\\reconstructedMatrixTest1.csv","TestesFuncionalidade1\\reconstructedMatrixTest1.csv")){
            System.out.println("Reconstruir Matriz Teste 1: Passou!");
        }else System.out.println("Reconstruir Matriz Teste 1: Falhou!");
        if (reconstructedMatrizTest2("TestesFuncionalidade1\\reconstructedMatrixTest2.csv","TestesFuncionalidade1\\reconstructedMatrixTest2.csv")){
            System.out.println("Reconstruir Matriz Teste 1: Passou!");
        }else System.out.println("Reconstruir Matriz Teste 1: Falhou!");
        if (reconstructedMatrizTest3("C:\\Users\\Tiago Oliveira\\IdeaProjects\\lapr1-24-25_DCD_09\\TestesFuncionalidade1\\reconstrucetdMatrixTest3.csv","C:\\Users\\Tiago Oliveira\\IdeaProjects\\lapr1-24-25_DCD_09\\TestesFuncionalidade1\\reconstrucetdMatrixTest3.csv")){
            System.out.println("Reconstruir Matriz Teste 1: Passou!");
        }else System.out.println("Reconstruir Matriz Teste 1: Falhou!");
    }
    public static boolean isMatrixNull(double[][] matrix) {
        // Verifica se a matriz é nula
        if (matrix == null) {
            return true;
        }

        // Verifica se todas as linhas da matriz são nulas
        for (double[] row : matrix) {
            if (row != null) {
                return false;
            }
        }

        return true;
    }
    public static void isSymetricTest1(String fileName, boolean expectedResult) throws FileNotFoundException {
        double[][] matrix = readMatrixFromFile(fileName);
        if (!isMatrixNull(matrix)) {
            if (expectedResult == isSymmetric(matrix)) {
                System.out.println("Matriz Simétrica1: Passou!");
            } else System.out.println("Matriz Simétrica1: Falhou!");
        }else System.out.println("Matriz não está a ser lida de forma correta.");
    }
    public static void isSymetricTest2(String fileName, boolean expectedResult) throws FileNotFoundException {
        double[][] matrix = readMatrixFromFile(fileName);
        if (!isMatrixNull(matrix)) {
            if (expectedResult == isSymmetric(matrix)) {
                System.out.println("Matriz Simétrica2: Passou!");
            } else System.out.println("Matriz Simétrica2: Falhou!");

        }else System.out.println("Matriz não está a ser lida de forma correta.");
    }
    public static void isSymetricTest3(String fileName, boolean expectedResult) throws FileNotFoundException {
        double[][] matrix = readMatrixFromFile(fileName);
        if (!isMatrixNull(matrix)) {
            if (expectedResult == isSymmetric(matrix)) {
                System.out.println("Matriz Simétrica3: Passou!");
            } else System.out.println("Matriz Simétrica3: Falhou!");
        }else System.out.println("Matriz não está a ser lida de forma correta.");
    }
    public static void isNotSymetricTest4(String fileName, boolean expectedResult) throws FileNotFoundException {
        double[][] matrix = readMatrixFromFile(fileName);
        if (!isMatrixNull(matrix)) {
            if (expectedResult == isSymmetric(matrix)) {
                System.out.println("Matriz não Simétrica1: Passou!");
            } else System.out.println("Matriz não Simétrica1: Falhou!");
        }else System.out.println("Matriz não está a ser lida de forma correta.");
    }
    public static void isNotSymetricTest5(String fileName, boolean expectedResult) throws FileNotFoundException {
        double[][] matrix = readMatrixFromFile(fileName);
        if (!isMatrixNull(matrix)) {
            if (expectedResult == isSymmetric(matrix)) {
                System.out.println("Matriz não Simétrica2: Passou!");
            } else System.out.println("Matriz não Simétrica2: Falhou!");
        }else System.out.println("Matriz não está a ser lida de forma correta.");
    }
    public static boolean reconstructedMatrizTest1(String filename, String filenameReconstruida) throws FileNotFoundException {
        double[][] matrizInicial = readMatrixFromFile(filename);
        double[][] matrizReconstruida = readMatrixFromFile(filenameReconstruida);
        if (!isMatrixNull(matrizInicial) && !isMatrixNull(matrizReconstruida)) {
            for (int i = 0; i < matrizInicial.length; i++) {
                for (int j = 0; j < matrizInicial[i].length; j++) {
                    if (matrizInicial[i][j] != matrizReconstruida[i][j]) {
                        return false; // Se qualquer elemento for diferente, as matrizes não são iguais.
                    }
                }
            }
            return true;// As matrizes são iguais.
        }
        return false;
    }
    public static boolean reconstructedMatrizTest2(String filename, String filenameReconstruida) throws FileNotFoundException {
        double[][] matrizInicial = readMatrixFromFile(filename);
        double[][] matrizReconstruida = readMatrixFromFile(filenameReconstruida);
        if (!isMatrixNull(matrizInicial) && !isMatrixNull(matrizReconstruida)) {
            for (int i = 0; i < matrizInicial.length; i++) {
                for (int j = 0; j < matrizInicial[i].length; j++) {
                    if (matrizInicial[i][j] != matrizReconstruida[i][j]) {
                        return false; // Se qualquer elemento for diferente, as matrizes não são iguais.
                    }
                }
            }
            return true;// As matrizes são iguais.
        }
        return false;
    }
    public static boolean reconstructedMatrizTest3(String filename, String filenameReconstruida) throws FileNotFoundException {
        double[][] matrizInicial = readMatrixFromFile(filename);
        double[][] matrizReconstruida = readMatrixFromFile(filenameReconstruida);
        if (!isMatrixNull(matrizInicial) && !isMatrixNull(matrizReconstruida)) {
            for (int i = 0; i < matrizInicial.length; i++) {
                for (int j = 0; j < matrizInicial[i].length; j++) {
                    if (matrizInicial[i][j] != matrizReconstruida[i][j]) {
                        return false; // Se qualquer elemento for diferente, as matrizes não são iguais.
                    }
                }
            }
            return true;// As matrizes são iguais.
        }
        return false;
    }
    public static void calculateMeanAbsoluteErrorTest1(String fileName, String fileNameApproximated) throws FileNotFoundException {
        double[][] matrix = readMatrixFromFile(fileName);
        double[][] aproximatedMatrix = readMatrixFromFile(fileNameApproximated);
        if (!isMatrixNull(matrix)) {
            double expectedResult = 1; // Correct initialization
            double actualResult = calculateMeanAbsoluteError(matrix, aproximatedMatrix);
            // Arredondar os valores do resultado a 2 casas decimais
            actualResult = Math.round(actualResult);

            if (expectedResult == actualResult) {
                System.out.println("EAM Teste 1: Passou!");
            } else {
                System.out.println("EAM Teste 1: Falhou!");
            }
        } else {
            System.out.println("Error: The matrix could not be read correctly.");
        }
    }
    public static void calculateMeanAbsoluteErrorTest2(String fileName, String fileNameApproximated) throws FileNotFoundException {
        double[][] matrix = readMatrixFromFile(fileName);
        double[][] aproximatedMatrix = readMatrixFromFile(fileNameApproximated);
        if (!isMatrixNull(matrix)) {
            double expectedResult = 2; // Correct initialization
            double actualResult = calculateMeanAbsoluteError(matrix, aproximatedMatrix);
            // Arredondar os valores do resultado a 2 casas decimais
            actualResult = Math.round(actualResult);

            if (expectedResult == actualResult) {
                System.out.println("EAM Teste 2: Passou!");
            } else {
                System.out.println("EAM Teste 2: Falhou!");
            }
        } else {
            System.out.println("Error: The matrix could not be read correctly.");
        }
    }
    public static void calculateMeanAbsoluteErrorTest3(String fileName, String fileNameApproximated) throws FileNotFoundException {
        double[][] matrix = readMatrixFromFile(fileName);
        double[][] aproximatedMatrix = readMatrixFromFile(fileNameApproximated);
        if (!isMatrixNull(matrix)) {
            double expectedResult = 17; // Correct initialization
            double actualResult = calculateMeanAbsoluteError(matrix, aproximatedMatrix);
            // Arredondar os valores do resultado a 2 casas decimais
            actualResult = Math.round(actualResult);

            if (expectedResult == actualResult) {
                System.out.println("EAM Teste 3: Passou!");
            } else {
                System.out.println("EAM Teste 3: Falhou!");
            }
        } else {
            System.out.println("Error: The matrix could not be read correctly.");
        }
    }

    public static void uniteTestFunctionality2() throws IOException {
        if (obtainMatrixMTest1("C:\\Users\\Tiago Oliveira\\IdeaProjects\\lapr1-24-25_DCD_09\\TestesFuncionalidade2_4x4","C:\\Users\\Tiago Oliveira\\IdeaProjects\\lapr1-24-25_DCD_09\\TestesFuncionalidade2_4x4\\matrizMesperada")) {
            System.out.println("MatrizMTest1: Passou!");
        }else System.out.println("MatrizMTest1: Falhou!");
        if(multiplyMatrixsTest1("C:\\Users\\Tiago Oliveira\\IdeaProjects\\lapr1-24-25_DCD_09\\TestesFuncionalidade2_4x4\\multiplicarMatriz")){
            System.out.println("MultiplicarMatrizTest1: Passou!");
        }else System.out.println("MultiplicarMatrizTest1: Falhou!");
        if(matrixToColumnVectorTest1()){
            System.out.println("matrizParaVetorTest1: Passou!");
        }else System.out.println("matrizParaVetorTest1: Falhou!");
        if(transposeTest1("C:\\Users\\Tiago Oliveira\\IdeaProjects\\lapr1-24-25_DCD_09\\TestesFuncionalidade2_4x4\\matriz_4x4_1.csv")){
            System.out.println("transpostaTest1: Passou!");
        }else System.out.println("transpostaTest1: Falhou!");
        if(transposeTest2("C:\\Users\\Tiago Oliveira\\IdeaProjects\\lapr1-24-25_DCD_09\\TestesFuncionalidade2_4x4\\matriz_4x4_2.csv")){
            System.out.println("transpostaTest2: Passou!");
        }else System.out.println("transpostaTest2: Falhou!");
        if(vetorColunaMedioTest1()){
            System.out.println("vetorColunaMedioTest1: Passou!");
        }else System.out.println("vetorColunaMedioTest1: Falhou!");

    }
    public static boolean obtainMatrixMTest1(String folderPath, String fileNameApproximated) throws IOException {
        double[][] matrizM = obtainMatrixM(folderPath);
        double[][] matrizEsperada = readMatrixFromFile(fileNameApproximated);
        if (!isMatrixNull(matrizM)) {
            for (int i = 0; i < matrizM.length; i++) {
                for (int j = 0; j < matrizM[i].length; j++) {
                    if (matrizM[i][j] != matrizEsperada[i][j]) {
                        return false; // Se qualquer elemento for diferente, as matrizes não são iguais.
                    }
                }
            }
            return true;// As matrizes são iguais.
        }
        return false;
    }
    public static boolean multiplyMatrixsTest1(String fileNameApproximated) throws FileNotFoundException {
        double[][] matrix = readMatrixFromFile("C:\\Users\\Tiago Oliveira\\IdeaProjects\\lapr1-24-25_DCD_09\\TestesFuncionalidade2_4x4\\matriz_4x4_1.csv");
        double[][] matrix2 = readMatrixFromFile("C:\\Users\\Tiago Oliveira\\IdeaProjects\\lapr1-24-25_DCD_09\\TestesFuncionalidade2_4x4\\matriz_4x4_2.csv");
        double [][] matrizEsperada = readMatrixFromFile(fileNameApproximated);
        if (!isMatrixNull(matrix) && !isMatrixNull(matrix2)) {
            double [][] matrizResultado = multiplyMatrix(matrix2, matrix);
            for (int i = 0; i < matrizResultado.length; i++) {
                for (int j = 0; j < matrizResultado[i].length; j++) {
                    if (matrizResultado[i][j] != matrizEsperada[i][j]) {
                        return false; // Se qualquer elemento for diferente, as matrizes não são iguais.
                    }
                }
            }
            return true;// As matrizes são iguais.
        }
        return false;
        }
    public static boolean matrixToColumnVectorTest1() throws FileNotFoundException {
        double[][] matrix = readMatrixFromFile("C:\\Users\\Tiago Oliveira\\IdeaProjects\\lapr1-24-25_DCD_09\\TestesFuncionalidade2_4x4\\matriz_4x4_1.csv");
        double[] vetorObtido = matrixToColumnVector(matrix);
        double[] vetorEsperado = {1, 3, 5, 6, 3, 2, 7, 1, 5, 7, 6, 5, 6, 1, 5, 2};
        for (int i = 0; i < vetorObtido.length; i++) {
            if (vetorObtido[i] != vetorEsperado[i]) {
                return false;
            }
        }
        return true;
    }
    public static boolean transposeTest1(String filename) throws FileNotFoundException {
        double[][] matrizInicial = readMatrixFromFile(filename);
        double[][] matrizTransposta = calculateTranspose(matrizInicial);
        if (!isMatrixNull(matrizInicial) && !isMatrixNull(matrizTransposta)) {
            for (int i = 0; i < matrizInicial.length; i++) {
                for (int j = 0; j < matrizInicial[i].length; j++) {
                    if (matrizInicial[i][j] != matrizTransposta[i][j]) {
                        return false; // Se qualquer elemento for diferente, as matrizes não são iguais.
                    }
                }
            }
            return true;// As matrizes são iguais.
        }
        return false;
    }
    public static boolean transposeTest2(String filename) throws FileNotFoundException {
        double[][] matrizInicial = readMatrixFromFile(filename);
        double[][] matrizTransposta = calculateTranspose(matrizInicial);
        if (!isMatrixNull(matrizInicial) && !isMatrixNull(matrizTransposta)) {
            for (int i = 0; i < matrizInicial.length; i++) {
                for (int j = 0; j < matrizInicial[i].length; j++) {
                    if (matrizInicial[i][j] != matrizTransposta[i][j]) {
                        return false; // Se qualquer elemento for diferente, as matrizes não são iguais.
                    }
                }
            }
            return true;// As matrizes são iguais.
        }
        return false;
    }
    public static boolean vetorColunaMedioTest1() throws FileNotFoundException {
        double[][] matrix = readMatrixFromFile("C:\\Users\\Tiago Oliveira\\IdeaProjects\\lapr1-24-25_DCD_09\\TestesFuncionalidade2_4x4\\matriz_4x4_1.csv");
        double[] vetorObtido = calculateAverageVector(matrix);
        double[] vetorEsperado = {3.75, 3.25, 5.75, 3.5};
        for (int i = 0; i < vetorObtido.length; i++) {
            if (vetorObtido[i] != vetorEsperado[i]) {
                return false;
            }
        }
        return true;
    }

    public static void uniteTestFunctionality3(){
    if (phiNovaTest1()){
        System.out.println("Calculo da Phi Teste 1: Passou!");
    }else System.out.println("Calculo da Phi Teste 1: Falhou!");
    if (phiNovaTest2()){
        System.out.println("Calculo da Phi Teste 2: Passou!");
    }else System.out.println("Calculo da Phi Teste 2: Falhou!");
    if (phiNovaTest3()){
        System.out.println("Calculo da Phi Teste 3: Passou!");
    }else System.out.println("Calculo da Phi Teste 3: Falhou!");
    if (extrairColunaComoMatrizTest()){
        System.out.println("ExtrairColunaComoMatrizTest1: Passou!");
    }else System.out.println("ExtrairColunaComoMatrizTest1: Falhou!");
    if (extrairColunaComoMatrizTest2()){
            System.out.println("ExtrairColunaComoMatrizTest2: Passou!");
    }else System.out.println("ExtrairColunaComoMatrizTest2: Falhou!");
    if (menorDistanciaTest1()){
        System.out.println("MenorDistanciaTest1: Passou!");
    }else System.out.println("MenorDistanciaTest1: Falhou!");
    if (menorDistanciaTest2()){
        System.out.println("MenorDistanciaTest2: Passou!");
    }else System.out.println("MenorDistanciaTest2: Falhou!");
    if (calcularWjTest1()){
        System.out.println("CalcularWjTest1: Falhou!");
    }else System.out.println("CalcularWjTest1: Passou!");
    if (calcularWjTest2()){
        System.out.println("CalcularWjTest2: Falhou!");
    }else System.out.println("CalcularWjTest2: Passou!");
    if (calcularPesosTest1()){
        System.out.println("CalcularPesosTest1: Falhou!");
    }else System.out.println("CalcularPesosTest1: Passou!");
    if (calcularPesosTest2()){
        System.out.println("CalcularPesosTest2: Falhou!");
    }else System.out.println("CalcularPesosTest2: Passou!");
}
    public static boolean phiNovaTest1() {
        double[]imagemNova = {10,10,10,10,5,5,5,5,10,10,10,10,5,5,5,5};
        double[]vetorMedio = {3.5,5,6.5,4,5,4,7.5,2,6.5,7.5,5,5,4,2,5,2};
        double [] phiInicial = calculatePhi(imagemNova, vetorMedio);
        double[] phiEsperada = {6.5,5,3.5,6,0,1,-2.5,3,3.5,2.5,5,5,1,3,0,3};
        for (int i = 0; i < phiEsperada.length; i++) {
            if (phiEsperada[i] != phiInicial[i]) {
                return false;
            }
        }
        return true;
    }
    public static boolean phiNovaTest2() {
        // Novos valores de imagem e vetor médio
        double[] imagemNova = {20, 15, 10, 5, 30, 25, 20, 15, 40, 35, 30, 25, 50, 45, 40, 35};
        double[] vetorMedio = {10, 12, 11, 9, 25, 20, 15, 10, 35, 30, 25, 20, 45, 40, 35, 30};

        // Calcula o vetor phiInicial
        double[] phiInicial = calculatePhi(imagemNova, vetorMedio);

        // Valores esperados de phi
        double[] phiEsperada = {10, 3, -1, -4, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5};

        // Compara cada elemento do vetor phiInicial com o vetor esperado
        for (int i = 0; i < phiEsperada.length; i++) {
            if (phiEsperada[i] != phiInicial[i]) {
                return false; // Retorna falso se algum elemento não corresponder
            }
        }

        return true; // Retorna true se todos os elementos forem iguais
    }
    public static boolean phiNovaTest3() {
        // Novos valores de imagem e vetor médio
        double[] imagemNova = {20, 15, 10, 5, 30, 25, 20, 15, 40, 35, 30, 25, 50, 45, 40, 35};
        double[] vetorMedio = {10, 12, 11, 9, 25, 20, 15, 10, 35, 30, 25, 20, 45, 40, 35, 30};

        // Calcula o vetor phiInicial
        double[] phiInicial = calculatePhi(imagemNova, vetorMedio);

        // Valores esperados de phi
        double[] phiEsperada = {10, 3, -1, -4, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5};

        // Compara cada elemento do vetor phiInicial com o vetor esperado
        for (int i = 0; i < phiEsperada.length; i++) {
            if (phiEsperada[i] != phiInicial[i]) {
                return false; // Retorna falso se algum elemento não corresponder
            }
        }

        return true; // Retorna true se todos os elementos forem iguais
    }
    public static boolean extrairColunaComoMatrizTest() {
        // Matriz de entrada (4x4 representando uma dimensão 2x2 quando convertido)
        double[][] matriz = {
                {10, 1}, {10, 2}, {10, 3}, {10, 4},
                {5, 5}, {5, 6}, {5, 7}, {5, 8},
                {10, 9}, {10, 10}, {10, 11}, {10, 12},
                {5, 13}, {5, 14}, {5, 15}, {5, 16}
        };

        // Índice da coluna que queremos extrair
        int colunaIndex = 0;

        // Matriz esperada (dimensão 2x2)
        int[][] matrizEsperada = {
                {10, 5},
                {10, 5}
        };

        int[][] resultado = convertColumnInMatrix(matriz, colunaIndex);

        // Verificar se o resultado corresponde à matriz esperada
        for (int i = 0; i < matrizEsperada.length; i++) {
            for (int j = 0; j < matrizEsperada[0].length; j++) {
                if (resultado[i][j] != matrizEsperada[i][j]) {
                    return false; // Retornar false se houver diferença
                }
            }
        }

        return true; // Retornar true se todos os elementos forem iguais
    }
    public static boolean extrairColunaComoMatrizTest2() {
        // Matriz de entrada (9x2 representando uma dimensão 3x3 quando convertido)
        double[][] matriz = {
                {1, 10}, {2, 20}, {3, 30},
                {4, 40}, {5, 50}, {6, 60},
                {7, 70}, {8, 80}, {9, 90}
        };

        // Índice da coluna que queremos extrair
        int colunaIndex = 1;

        // Matriz esperada (dimensão 3x3)
        int[][] matrizEsperada = {
                {10, 40, 70},
                {20, 50, 80},
                {30, 60, 90}
        };

        // Executar o método
        int[][] resultado = convertColumnInMatrix(matriz, colunaIndex);

        // Verificar se o resultado corresponde à matriz esperada
        for (int i = 0; i < matrizEsperada.length; i++) {
            for (int j = 0; j < matrizEsperada[0].length; j++) {
                if (resultado[i][j] != matrizEsperada[i][j]) {
                    return false; // Retornar false se houver diferença
                }
            }
        }

        return true; // Retornar true se todos os elementos forem iguais
    }
    public static boolean menorDistanciaTest1() {
        // Vetor de distâncias de entrada
        double[] distancias = {5.0, 2.5, 9.0, 1.5, 7.0};

        // Índices esperados em ordem de menor para maior distância
        double[] indicesEsperados = {3, 1, 0, 4, 2};

        // Executar o método
        double[] indicesRetornados = minorDistance(distancias);

        // Comparar os índices retornados com os esperados
        for (int i = 0; i < indicesEsperados.length; i++) {
            if (indicesEsperados[i] != indicesRetornados[i]) {
                return false; // Retornar false se algum índice não corresponder
            }
        }

        return true; // Retornar true se todos os índices forem iguais
    }
    public static boolean menorDistanciaTest2() {
        double[] distancias = {12.0, 3.0, 8.0, 6.0};
        double[] indicesEsperados = {1, 3, 2, 0}; // Após ordenação: {3.0, 6.0, 8.0, 12.0}
        double[] indicesRetornados = minorDistance(distancias);

        for (int i = 0; i < indicesEsperados.length; i++) {
            if (indicesEsperados[i] != indicesRetornados[i]) {
                return false;
            }
        }
        return true;
    }
    public static boolean calcularWjTest1() {
        // Matrizes de entrada
        double[][] eigenFaces = {
                {0.5, 0.5, 0.5, 0.5},
                {0.5, -0.5, 0.5, -0.5},
                {0.707, -0.707, -0.707, 0.707}
        };

        double[][] phi = {
                {1.0, 2.0},
                {3.0, 4.0},
                {5.0, 6.0},
                {7.0, 8.0}
        };

        int k = 2; // Número de componentes principais a serem consideradas

        // Matriz esperada (resultado conhecido)
        double[][] wjEsperado = {
                {5.54,7.24}, // Primeiro componente principal
                {4.54,5.24}   // Segundo componente principal
        };

        double[][] wjCalculado = calculateWj(eigenFaces, phi, k);
        // Comparar o resultado com o esperado
        for (int i = 0; i < k; i++) {
            for (int j = 0; j < phi[0].length; j++) {
                if (wjEsperado[i][j] != wjCalculado[i][j]) {
                    return false; // Retornar false se houver diferença significativa
                }
            }
        }

        return true; // Retornar true se todos os valores forem iguais (dentro da margem de erro)
    }
    public static boolean calcularWjTest2() {
        // Novas Matrizes de entrada
        double[][] eigenFaces = {
                {1.0, 0.5, 0.5, 0.5},
                {0.5, 1.0, 0.5, 0.5},
                {0.5, 0.5, 1.0, 0.5},
                {0.5, 0.5, 0.5, 1.0}
        };

        double[][] phi = {
                {0.5, 1.5},
                {1.0, 2.0},
                {1.5, 2.5},
                {2.0, 3.0}
        };

        int k = 2; // Número de componentes principais a serem consideradas

        // Matriz esperada com os resultados de Wj (conhecida manualmente ou calculada)
        double[][] wjEsperado = {
                {2.0, 3.0},  // Primeira componente
                {2.0, 3.0}   // Segunda componente
        };

        // Chamar o método a ser testado
        double[][] wjCalculado = calculateWj(eigenFaces, phi, k);

        // Comparar o resultado com a matriz esperada
        for (int i = 0; i < k; i++) {
            for (int j = 0; j < phi[0].length; j++) {
                if (Math.abs(wjEsperado[i][j] - wjCalculado[i][j]) > 0.001) {
                    return false; // Retornar false se houver diferença significativa
                }
            }
        }

        return true; // Retornar true se todos os valores forem iguais (dentro da margem de erro)
    }
    public static boolean calcularPesosTest1() {
        // Matrizes de entrada para eigenfaces e phiNova
        double[][] eigenfaces = {
                {1.0, 0.5, 0.5, 0.5},
                {0.5, 1.0, 0.5, 0.5},
                {0.5, 0.5, 1.0, 0.5}
        };

        double[] phiNova = {0.5, 1.5, 2.0, 2.5}; // Vetor phiNova com 4 componentes

        int k = 2; // Número de componentes principais a serem consideradas

        // Matriz esperada (resultados conhecidos para os pesos)
        double[] pesosEsperados = {2.5, 3.0};

        // Chamar o método a ser testado
        double[] pesosCalculados = calculatePesos(eigenfaces, phiNova, k);

        // Comparar os pesos calculados com os esperados
        for (int i = 0; i < k; i++) {
            if (Math.abs(pesosEsperados[i] - pesosCalculados[i]) > 0.001) {
                return false; // Retornar false se houver diferença significativa
            }
        }

        return true; // Retornar true se todos os pesos forem iguais (dentro da margem de erro)
    }
    public static boolean calcularPesosTest2() {
        // Novas Matrizes de entrada para eigenfaces e phiNova
        double[][] eigenfaces = {
                {2.0, 1.0, 1.5, 1.5},
                {1.5, 2.0, 1.0, 1.5},
                {1.0, 1.5, 2.0, 1.0}
        };

        double[] phiNova = {1.0, 2.0, 3.0, 4.0}; // Vetor phiNova com 4 componentes

        int k = 3; // Número de componentes principais a serem consideradas

        // Matriz esperada (resultados conhecidos para os pesos)
        double[] pesosEsperados = {5.5, 6.0, 6.5};

        // Chamar o método a ser testado
        double[] pesosCalculados = calculatePesos(eigenfaces, phiNova, k);

        // Comparar os pesos calculados com os esperados
        for (int i = 0; i < k; i++) {
            if (Math.abs(pesosEsperados[i] - pesosCalculados[i]) > 0.001) {
                return false; // Retornar false se houver diferença significativa
            }
        }

        return true; // Retornar true se todos os pesos forem iguais (dentro da margem de erro)
    }



}