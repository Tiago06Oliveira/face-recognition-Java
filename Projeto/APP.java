package Projeto;

import org.apache.commons.math3.linear.EigenDecomposition;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;

import static Projeto.TESTES.isMatrixNull;

public class APP {

    public static final int MIN_VALUE_MATRIX_LENGTH = 0;
    public static final int MAX_VALUE_MATRIX_LENGTH = 255;

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        int option = 0;
        int k;
        String filePath = "";
        String folderPath = "";
        String outputFile = "";

        if (args.length == 0) {
            // Loop do menu para permitir repetir ou sair
            while (option != 5) {
                // Exibe o menu
                System.out.println("\nMenu:");
                System.out.println("1. Decomposição Própria");
                System.out.println("2. Reconstrução da Imagem Utilizando Eigenfaces");
                System.out.println("3. Comparar Imagens");
                System.out.println("4. Criar Imagem");
                System.out.println("5. Sair");
                System.out.print("Escolha uma opção: ");

                // Lê a opção do utilizador
                option = scanner.nextInt();
                scanner.nextLine();

                // Verifica a opção selecionada
                if (option == 1) {
                    // Decomposição própria
                    System.out.print("Introduza o número de eigenvalues a usar: ");
                    k = scanner.nextInt();
                    scanner.nextLine(); // Consome a linha

                    System.out.print("Introduza o caminho para o ficheiro CSV que contém a matriz: ");
                    filePath = scanner.nextLine();

                    functionality1(k, filePath);

                } else if (option == 2) {
                    // Reconstrução da Imagem Utilizando Eigenfaces
                    System.out.print("Digite o número de eigenfaces a usar na reconstrução: ");
                    k = scanner.nextInt();
                    scanner.nextLine(); // Consome a linha

                    System.out.print("Digite o caminho para a pasta com as imagens a reconstruir: ");
                    folderPath = scanner.nextLine();

                    functionality2(k, folderPath);

                } else if (option == 3) {
                    // Compare Images
                    System.out.print("Introduza o número de eigenfaces a usar para a comparação: ");
                    k = scanner.nextInt();
                    scanner.nextLine(); // Consume the newline character

                    System.out.print("Introduza o caminho para a pasta com a base de imagens: ");
                    folderPath = scanner.nextLine();

                    System.out.print("Introduza o caminho para a nova imagem a comparar: ");
                    String newImagePath = scanner.nextLine();

                    // Call the functionality to compare images
                    functionality3(k, newImagePath, folderPath);

                } else if (option == 4) {
                    // Reconstrução da Imagem Utilizando Eigenfaces
                    System.out.print("Digite o número de eigenfaces a usar na reconstrução: ");
                    k = scanner.nextInt();
                    scanner.nextLine(); // Consome a linha

                    System.out.print("Digite o caminho para a pasta com as imagens a reconstruir: ");
                    folderPath = scanner.nextLine();
                    functionality4(k, folderPath);

                } else if (option == 5) {
                    // Sai do programa
                    System.out.println("A terminar o programa.");
                } else {
                    // Caso o utilizador escolha uma opção inválida
                    System.out.println("Opção inválida. Tente novamente.");
                }
            }
            scanner.close();
        } else {
            int X = -1;
            k = -2;
            String z = null;
            String W = null;
            String nomeSaida = null;
            try {
                for (int i = 0; i < args.length; i++) {
                    switch (args[i]) {
                        case "-f":
                            X = Integer.parseInt(args[++i]);
                            break;
                        case "-k":
                            k = Integer.parseInt(args[++i]);
                            break;
                        case "-d":
                            W = args[++i];
                            break;
                        case "-i":
                            z = args[++i];
                            break;
                        default:
                            if (args[i].startsWith("\"") && args[i].endsWith("\"")) {
                                nomeSaida = args[i].replace("\"", "") + "/";  // Remove as aspas do nome da saída
                            } else if (nomeSaida == null) {
                                nomeSaida = (args[i] + "/").trim();
                            }
                            break;
                    }
                }
            } catch (Exception e) {
                System.out.println("Erro ao processar argumentos. Certifique-se de usar o formato correto:");
                System.out.println("-f X -k Y -i Z -d W \"nome da saída\"");
                return;
            }

            if (X == -2 || k == -2 || nomeSaida == null) {
                System.out.println("Erro: Argumentos obrigatórios ausentes.");
                System.out.println("Formato esperado: -f X -k Y -i Z -d W \"nome da saída\"");
                return;
            }
            nomeSaida=nomeSaida.trim();
            PrintStream fileOut = new PrintStream(new File(nomeSaida));
            System.setOut(fileOut);

            switch (X) {
                case 1: {
                    if (z == null) {
                        System.out.println("Erro: O argumento -i (imagem de entrada) é obrigatório para o caso 1.");
                        return;
                    }
                    functionality1(k,z);
                    break;
                }
                case 2: {
                    if (W == null) {
                        System.out.println("Erro: O argumento -d (base de imagens) é obrigatório para o caso 2.");
                        return;
                    }
                    functionality2(k, W);
                    break;
                }
                case 3: {
                    if (z == null || W == null) {
                        System.out.println("Erro: Os argumentos -i (imagem de entrada) e -d (base de imagens) são obrigatórios para o caso 3.");
                        return;
                    }
                   functionality3(k, z, W);
                    break;
                }
                case 4: {
                    if (W == null) {
                        System.out.println("Erro: O argumento -d (base de imagens) é obrigatório para o caso 2.");
                        return;
                    }
                    functionality4(k, W);
                    break;
                }
                default: {
                    System.out.println("Erro: O valor de -f deve ser 1, 2 ou 3.");
                    break;
                }
            }
        }
    }

    //functionality 1
    public static void functionality1(int k, String filePath) throws IOException {

        // Read the matrix
        double[][] inputMatrix = readMatrixFromFile(filePath);
        if (inputMatrix == null) {
            // Não faz mais nada, já mostrou o erro
            return;
        }
        if (isSymmetric(inputMatrix)) {
            // Assume the matrix has 'n' columns as the number of eigenvectors available
            int numEigenVectors = inputMatrix[0].length; // Assuming the number of eigenvectors is the number of columns in the matrix

            // Verifique se k é maior que o número de vetores próprios disponíveis
            if (k > numEigenVectors || k == -1) k = numEigenVectors;

            // Calculate eigenvalues and eigenvectors
            double[] eigenValues = calculateEigenValues(inputMatrix);
            double[][] eigenVectors = calculateEigenVectors(inputMatrix);

            // Display results of eigenvalues and eigenvectors
            displayEigenValues(eigenValues, k);
            displayEigenVectors(eigenVectors, k);
            // Reconstruct the matrix using eigenvectors and eigenvalues
            double[][] reconstructedMatrix = reconstructMatrix(eigenVectors, eigenValues, k);
            // Display the reconstructed matrix with 2 decimal places
            displayReconstructedMatrix(reconstructedMatrix);
            // Calculate approximation error
            double error = calculateMeanAbsoluteError(inputMatrix, reconstructedMatrix);
            System.out.printf("\nErro Absoluto Médio entre a matriz original e a reconstruída: %.2f\n", error);
            // Save the reconstructed matrix to a file
            saveReconstructedMatrix(reconstructedMatrix);
        } else System.out.println("Matriz não simétrica, por favor introduza uma matriz simétrica.");
    }

    //method to open the file and read
    public static double[][] readMatrixFromFile(String absolutePath) throws FileNotFoundException {
        Scanner in = new Scanner(new File(absolutePath));
        String[] firstLine = null;
        while (in.hasNextLine()) {
            String line = in.nextLine().trim();
            if (!line.isEmpty()) {
                firstLine = line.split(",");
            }
        }
        if (firstLine == null) {
            System.out.println("Arquivo vazio ou sem dados válidos.");
            return null;
        }
        int columns = firstLine.length;
        int rows = 0;
        in = new Scanner(new File(absolutePath));
        while (in.hasNextLine()) {
            String line = in.nextLine().trim();
            if (!line.isEmpty()) {
                rows++;
            }
        }
        double[][] matrix = new double[rows][columns];
        in = new Scanner(new File(absolutePath));
        int i = 0;
        while (in.hasNextLine()) {
            String line = in.nextLine().trim();
            if (!line.isEmpty()) {
                String[] values = line.split(",");
                boolean allNumbers = true;
                for (int j = 0; j < values.length; j++) {
                    String value = values[j].trim();
                    if (!value.matches("[-+]?\\d*\\.?\\d+")) {
                        allNumbers = false;
                        break;
                    }
                    double number = Double.parseDouble(value);
                    if (number < MIN_VALUE_MATRIX_LENGTH || number > MAX_VALUE_MATRIX_LENGTH) {
                        System.out.println("Erro: O valor " + number + " está fora do intervalo permitido (0 a 255).");
                        return null;
                    }
                }
                if (!allNumbers) {
                    System.out.println("Erro: A matriz contém caracteres inválidos. Certifique-se de que todos os valores são números reais.");
                    return null;
                }
                for (int j = 0; j < values.length; j++) {
                    matrix[i][j] = Double.parseDouble(values[j].trim());
                }
                i++;
            }
        }
        if (matrix.length <= MAX_VALUE_MATRIX_LENGTH + 1) return matrix;
        return null;
    }
    // Method to check if the matrix is symmetric
    public static boolean isSymmetric(double[][] matrix) {
        int rows = matrix.length;
        for (int i = 0; i < rows; i++) {
            if (matrix[i].length != rows) {
                return false;
            }
        }
        for (int i = 0; i < rows; i++) {
            for (int j = i + 1; j < rows; j++) {
                if (matrix[i][j] != matrix[j][i]) {
                    return false;
                }
            }
        }
        return true;
    }
    // Method to calculate eigenvalues of a symmetric matrix
    public static double[] calculateEigenValues(double[][] matrix) {
        RealMatrix realMatrix = MatrixUtils.createRealMatrix(matrix);
        EigenDecomposition eigenDecomposition = new EigenDecomposition(realMatrix);
        return eigenDecomposition.getRealEigenvalues();
    }
    // Method to calculate eigenvectors of a symmetric matrix
    public static double[][] calculateEigenVectors(double[][] matrix) {
        RealMatrix realMatrix = MatrixUtils.createRealMatrix(matrix);
        EigenDecomposition eigenDecomposition = new EigenDecomposition(realMatrix);
        return eigenDecomposition.getV().getData();
    }
    // Method to display eigenvalues
    public static void displayEigenValues(double[] eigenValues, int k) {
        System.out.println("Eigenvalues:");
        int i = 0;
        while (i < k) {
            System.out.printf("EigenValue %d: ", i + 1);
            System.out.printf("%.2f", eigenValues[i]);
            System.out.println();
            i++;
        }
    }
    // Method to display eigenvectors
    public static void displayEigenVectors(double[][] eigenVectors, int k) {
        System.out.println("\nEigenvectors:");
        for (int i = 0; i < k; i++) {
            System.out.printf("Vector %d: {", i + 1);
            for (int j = 0; j < eigenVectors[i].length; j++) {
                double val = eigenVectors[i][j];
                System.out.printf("%.2f", val);
                if (j < eigenVectors[i].length - 1) {
                    System.out.print(",");
                }
            }
            System.out.println("}");
        }
        System.out.println();
    }
    // Method to reconstruct the matrix using eigenvectors and eigenvalues
    public static double[][] reconstructMatrix(double[][] eigenVectors, double[] eigenValues, int k) {
        int n = eigenVectors.length;
        double[][] reconstructedMatrix = new double[n][n];
        k = Math.min(k, eigenValues.length);
        for (int i = 0; i < k; i++) {
            for (int row = 0; row < n; row++) {
                for (int col = 0; col < n; col++) {
                    reconstructedMatrix[row][col] += eigenValues[i] * eigenVectors[row][i] * eigenVectors[col][i];
                }
            }
        }
        return reconstructedMatrix;
    }
    // Method to display the reconstructed matrix
    public static void displayReconstructedMatrix(double[][] reconstructedMatrix) {
        System.out.println("Matriz reconstruida:");
        for (int i = 0; i < reconstructedMatrix.length; i++) {
            for (int j = 0; j < reconstructedMatrix[i].length; j++) {
                System.out.printf("%6.2f ", reconstructedMatrix[i][j]);
            }
            System.out.println();
        }
    }
    // Method to calculate the mean absolute error between the original and reconstructed matrices
    public static double calculateMeanAbsoluteError(double[][] original, double[][] approximation) {
        int rows = original.length;
        int columns = original[0].length;
        double errorSum = 0.0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                errorSum += Math.abs(original[i][j] - approximation[i][j]);
            }
        }
        return errorSum / (rows * columns);
    }
    // Method to save the reconstructed matrix to a CSV file
    public static void saveReconstructedMatrix(double[][] reconstructedMatrix) throws IOException {
        FileWriter writer = new FileWriter("C:\\Users\\Tiago Oliveira\\IdeaProjects\\lapr1-24-25_DCD_09\\Output\\reconstructed_matrix.csv");
        PrintWriter pw = new PrintWriter(writer);
        for (int i = 0; i < reconstructedMatrix.length; i++) {
            for (int j = 0; j < reconstructedMatrix[i].length; j++) {
                pw.printf("%6.2f ", reconstructedMatrix[i][j]);
            }
            pw.println();
        }
        pw.close();
        System.out.println("Matriz reconstruída guardada em Output/reconstructed_matrix.csv");
    }


    //functionality2
    public static void functionality2(int k, String folderPath) throws IOException {
        double[][] matrizM = obtainMatrixM(folderPath);
        if (!isMatrixNull(matrizM)) {
            int numEigenVectors = matrizM[0].length; // Assuming the number of eigenvectors is the number of columns in the matrix

            if (k > numEigenVectors || k == -1) k = numEigenVectors;

            double[][] matrizMTranspose = calculateTranspose(matrizM);
            double[] averageVector = calculateAverageVector(matrizM);
            System.out.println();
            System.out.println("Vetor Médio:");
            System.out.print("["); 

            for (int i = 0; i < averageVector.length; i++) {
                System.out.printf("%.2f", averageVector[i]);
                if (i < averageVector.length - 1) {
                    System.out.print("; ");               
                }
            }

            System.out.println("]");

            double[][] A = constructMatrixA(matrizM, averageVector);

            double[][] matrixCovariance = calculateCovariance(A);
            System.out.println();
            System.out.println("Matriz de Covariancia - A(T)*A:");
            System.out.println();
            displayMatrix(matrixCovariance);
            System.out.println();

            double[][] vetoresProprios = calculateEigenVectors(matrixCovariance);
            double[][] vetoresPropriosA = multiplyMatrix(A, vetoresProprios);

            double[][] eigenFaces = normalizeVectorsToEigenFaces(vetoresPropriosA);
            int[][] intEigenFaces = convertDoubleToInt(eigenFaces);

            for (int col = 0; col < intEigenFaces[0].length; col++) {
                // selected specific col as vector
                int[] columns = new int[intEigenFaces.length];
                for (int row = 0; row < intEigenFaces.length; row++) {
                    columns[row] = intEigenFaces[row][col];
                }

                int[][] image = convertVectorToMatrix(columns, false);

                String nomeCsv = "eigenface_reconstruida_" + col + ".csv";

                saveFilesInFolder("C:\\Users\\Tiago Oliveira\\IdeaProjects\\lapr1-24-25_DCD_09\\Eigenfaces",nomeCsv,image);
            }

            double[][] matrizImagesReconstructs = reconstructImage(averageVector, eigenFaces, A, k);
            double[][] matrizImagesReconstructsTranspose = calculateTranspose(matrizImagesReconstructs);

            for (int i = 0; i < matrizM[0].length; i++) {
                int[][] finalImage = matrixImage(matrizImagesReconstructs, i);
                int[][] transposeImageFinal = calculateTransposeInt(finalImage);
                String nome = String.valueOf(i);
                saveFilesInFolder("C:\\Users\\Tiago Oliveira\\IdeaProjects\\lapr1-24-25_DCD_09\\ImagensReconstruidas", "imagem_reconstruida_" + nome + ".csv", finalImage);
                writeArrayAsImage(transposeImageFinal, "C:\\Users\\Tiago Oliveira\\IdeaProjects\\lapr1-24-25_DCD_09\\ImagensReconstruidas\\imagem_reconstruida_" + i + ".jpg");
                calculateEAM(matrizMTranspose, matrizImagesReconstructsTranspose, i);
            }
        } else System.out.println("Erro ao ler a matriz M!");
    }

    // Method to open folder and read CSV files
    public static File[] obtainCSVFiles(String folderPath) {
        File folder = new File(folderPath);
        return folder.listFiles((dir, nome) -> nome.toLowerCase().endsWith(".csv"));
    }
    // Method to transform de CSV in matrix
    public static double[][] readCSVtoMatrix(File file) throws IOException {
        Scanner scanner = new Scanner(file);
        int numRows = 0;
        int numCols = 0;

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            if (!line.isEmpty()) {
                String[] dads = line.split(",");
                numCols = dads.length;
                numRows++;
            }
        }

        scanner.close();

        double[][] matriz = new double[numRows][numCols];
        scanner = new Scanner(file);
        int lineIndex = 0;

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            if (!line.isEmpty()) {
                String[] dads = line.split(",");
                for (int i = 0; i < dads.length; i++) {
                    double valor = Double.parseDouble(dads[i].trim());

                    // verify if the values are in [0, 255]
                    if (valor < MIN_VALUE_MATRIX_LENGTH || valor > MAX_VALUE_MATRIX_LENGTH) {
                        throw new IllegalArgumentException("Valor fora dos limites (0 a 255): " + valor +
                                " na linha " + (lineIndex + 1) + ", coluna " + (i + 1));
                    }

                    matriz[lineIndex][i] = valor;
                }
                lineIndex++;
            }
        }

        scanner.close();
        return matriz;
    }
    // Method to transform matrix in col vector
    public static double[] matrixToColumnVector(double[][] matrix) {
        int numRows = matrix.length;
        int numCols = matrix[0].length;
        double[] colunaVector = new double[numRows * numCols];
        int index = 0;

        for (int j = 0; j < numCols; j++) {
            for (int i = 0; i < numRows; i++) {
                colunaVector[index] = matrix[i][j];
                index++;
            }
        }

        return colunaVector;
    }
    //Method to return Matrix with all images 40x4096
    public static double[][] obtainMatrixM(String folderPath) throws IOException {
        File[] ficheirosCSV = obtainCSVFiles(folderPath);

        if (ficheirosCSV.length == 0) {
            System.out.println("Nenhum ficheiro CSV encontrado.");
            return null;
        }

        double[][] primeiraMatriz = readCSVtoMatrix(ficheirosCSV[0]);
        int numLinhas = primeiraMatriz.length;
        int numColunas = primeiraMatriz[0].length;

        double[][] M = new double[numLinhas * numColunas][ficheirosCSV.length];
        double[] vetorColuna;

        // Process file by file
        for (int i = 0; i < ficheirosCSV.length; i++) {
            File ficheiro = ficheirosCSV[i];

            double[][] matriz = readCSVtoMatrix(ficheiro);

            vetorColuna = matrixToColumnVector(matriz);
            //stores col by col
            for (int j = 0; j < vetorColuna.length; j++) {
                M[j][i] = vetorColuna[j];
            }
        }

        return M;
    }
    //Method to calculate average vector
    public static double[] calculateAverageVector(double[][] M) {
        int numLinhas = M.length;
        int numColunas = M[0].length;
        double[] vetorMedio = new double[numLinhas];

        for (int i = 0; i < numLinhas; i++) {
            double soma = 0;

            for (int j = 0; j < numColunas; j++) {
                soma += M[i][j];
            }
            vetorMedio[i] = soma / numColunas;
        }

        return vetorMedio;
    }
    //Method to calculate deviation
    public static double[] calculateDeviationVector(double[] image, double[] averageVector) {
        int comprimento = image.length;
        double[] desvio = new double[comprimento];

        for (int i = 0; i < comprimento; i++) {
            desvio[i] = image[i] - averageVector[i];
        }
        return desvio;
    }
    //stores deviation from all images 40x4096
    public static double[][] constructMatrixA(double[][] M, double[] averageVector) {
        int numLinhas = M.length;        // Número de linhas (dimensão de cada vetor)
        int numColunas = M[0].length;    // Número de colunas (número de imagens)

        // Matriz A onde serão armazenados os desvios
        double[][] A = new double[numLinhas][numColunas];

        // Para cada coluna de M (cada imagem), calcular o desvio e armazená-lo na matriz A
        for (int j = 0; j < numColunas; j++) {
            double[] imagem = new double[numLinhas];

            // Extraímos a imagem da coluna j de M
            for (int i = 0; i < numLinhas; i++) {
                imagem[i] = M[i][j];
            }

            // Calcula o desvio da imagem e armazena na coluna j da matriz A
            double[] desvio = calculateDeviationVector(imagem, averageVector);

            // Armazena o desvio na matriz A
            for (int i = 0; i < numLinhas; i++) {
                A[i][j] = desvio[i];
            }
        }

        return A;  // Retorna a matriz de desvios
    }
    //Show in Output a matrix
    public static void displayMatrix(double[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.printf("%12.2f ", matrix[i][j]);
            }
            System.out.println();
        }
    }
    //Method to invert cols in rows
    public static double[][] calculateTranspose(double[][] deviationMatrix) {
        int linhas = deviationMatrix.length;
        int colunas = deviationMatrix[0].length;
        double[][] transposta = new double[colunas][linhas];

        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                transposta[j][i] = deviationMatrix[i][j];
            }
        }
        return transposta;
    }
    public static int[][] calculateTransposeInt(int[][] deviationMatrix) {
        int linhas = deviationMatrix.length;
        int colunas = deviationMatrix[0].length;
        int[][] transposta = new int[colunas][linhas];

        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                transposta[j][i] = deviationMatrix[i][j];
            }
        }
        return transposta;
    }
    //Method to multiply matrix
    public static double[][] multiplyMatrix(double[][] array1, double[][] array2) {
        double[][] resultado = new double[array1.length][array2[0].length];

        for (int a = 0; a < array1.length; a++) {
            for (int b = 0; b < array2[0].length; b++) {
                for (int c = 0; c < array1[0].length; c++) {
                    resultado[a][b] = resultado[a][b] + (array1[a][c] * array2[c][b]);
                }
            }
        }
        return resultado;
    }
    // Method to calculate A(T)*A
    public static double[][] calculateCovariance(double[][] A) {
        // A matriz de covariância é dada por (A^T * A)
        double[][] transpostaA = calculateTranspose(A);

        // Multiplica A^T * A
        double[][] covariancia = multiplyMatrix(transpostaA, A);

        return covariancia;
    }
    //Method to obtain EigenFaces
    public static double[][] normalizeVectorsToEigenFaces(double[][] eigenFaces) {
        for (int j = 0; j < eigenFaces[0].length; j++) {
            double magnitude = 0;

            for (int i = 0; i < eigenFaces.length; i++) {
                magnitude += Math.pow(eigenFaces[i][j], 2);
            }
            magnitude = Math.sqrt(magnitude)+ 1e-10; //avoid divisions by zero


            if (magnitude != 1) {
                for (int i = 0; i < eigenFaces.length; i++) {
                    eigenFaces[i][j] /= magnitude;
                }
            }
        }
        return eigenFaces;
    }
    //Method to reconstruct image with eigenFace
    public static double[][] reconstructImage(double[] averageVector, double[][] eigenFaces, double[][] matrizA, int k) {

        int n = averageVector.length;
        int numImage = matrizA[0].length;

        double[][] reconstructImage = new double[n][numImage];

        double[][] eigenFacesTranspose = calculateTranspose(eigenFaces);

        // For each image, calculate the w_j coefficients and reconstruct the image
        for (int col = 0; col < numImage; col++) {
            double[] w = new double[k];

            // Calculate the w_j coefficients (image projection in relation to the eigenFaces)
            for (int j = 0; j < k; j++) {
                double soma = 0.0;
                for (int i = 0; i < n; i++) {
                    soma += eigenFacesTranspose[j][i] * matrizA[i][col];
                }
                w[j] = soma;
            }

            // Show vector W
            System.out.println("Pesos w para imagem " + col + ":");
            for (int j = 0; j < k; j++) {
                System.out.printf("%.2f ", w[j]);
            }
            System.out.println();

            // Reconstruct the image using the coefficients w_j and the first k eigenFaces
            for (int i = 0; i < n; i++) {
                double somaReconstruct = 0.0;
                for (int j = 0; j < k; j++) {
                    somaReconstruct += eigenFaces[i][j] * w[j];
                }
                reconstructImage[i][col] = averageVector[i] + somaReconstruct;
            }

        }
        return reconstructImage;
    }

    public static int[][] matrixImage(double[][] image, int col) {
        int tamanho = (int) Math.sqrt(image.length);
        int[][] imagemMatriz = new int[tamanho][tamanho];
        for (int i = 0; i < tamanho; i++) {
            for (int j = 0; j < tamanho; j++) {
                imagemMatriz[i][j] = (int) image[(i * tamanho) + j][col];
            }
        }
        return imagemMatriz;
    }
    //Method to calculate Erro Absoluto Medio
    private static void calculateEAM(double[][] originalImage, double[][] reconstructedImage, int i) {
        int colunas = originalImage[i].length;
        double somaErroLinha = 0.0;

        for (int j = 0; j < colunas; j++) {
            somaErroLinha += Math.abs(originalImage[i][j] - reconstructedImage[i][j]);
        }
        double erroLinha = somaErroLinha / colunas;
        System.out.printf("Erro da reconstrução da imagem %d: %.2f%n", i, erroLinha);
    }

    public static void saveFilesInFolder(String path, String nome, int[][] arrayAK) throws FileNotFoundException {

        File arquivo = new File(path, nome);
        PrintWriter out = new PrintWriter(arquivo);

        for (int i = 0; i < arrayAK.length; i++) {
            for (int j = 0; j < arrayAK[0].length; j++) {
                out.printf("%-1d", arrayAK[i][j]);
                if (j < arrayAK[0].length - 1) {
                    out.print(",");
                }
            }
            out.println();
        }

        out.close();
    }

    public static void writeArrayAsImage(int[][] array, String outputFilePath) throws IOException {
        int height = array.length;
        int width = array[0].length;

        // Create a BufferedImage
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);

        // Set the pixel intensities
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int intensity = array[y][x];
                if (intensity < 0) {
                    intensity = 0;
                }
                if (intensity > 255) {
                    intensity = 255;
                }
                int rgb = (intensity << 16) | (intensity << 8) | intensity; // Set the same value for R, G, B
                image.setRGB(x, y, rgb);
            }
        }

        // Write the image to the file
        File outputFile = new File(outputFilePath);
        ImageIO.write(image, "jpg", outputFile);
    }
    //Method to convert all doubles
    public static int[][] convertDoubleToInt(double[][] array) {
        int[][] intArray = new int[array.length][array[0].length];
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[0].length; j++) {
                intArray[i][j] = (int) Math.round(array[i][j]);
            }
        }
        return intArray;
    }
    //Method to convert vector in 64x64
    public static int[][] convertVectorToMatrix(int[] vector, boolean asRow) {
        if (asRow) {
            int[][] matrix = new int[1][vector.length];
            for (int i = 0; i < vector.length; i++) {
                matrix[0][i] = vector[i];
            }
            return matrix;
        } else {
            int[][] matrix = new int[vector.length][1];
            for (int i = 0; i < vector.length; i++) {
                matrix[i][0] = vector[i];
            }
            return matrix;
        }
    }

    //functionality3

    public static void functionality3(int k, String newImageName, String filePath) throws IOException {

        double[][] imageDataBase = obtainMatrixM(filePath);
        if(!isMatrixNull(imageDataBase)) {
            double[] vetorMedio = calculateAverageVector(imageDataBase);
            double[][] A = constructMatrixA(imageDataBase, vetorMedio);
            int numEigenVectors = imageDataBase[0].length;

            if (k > numEigenVectors || k == -1) k = numEigenVectors;

            double[][] matrixCovariance = calculateCovariance(A);
            double[] valores = calculateEigenValues(matrixCovariance);
            double[] indices = indiceProK(valores, k);
            double[][] vetoresPropriosTodos = calculateEigenVectors(matrixCovariance);
            double[][] vetoresProprios = vectorsPK(vetoresPropriosTodos, indices, k);
            double[][] vetoresPropriosA = multiplyMatrix(A, vetoresProprios);
            double[][] eigenFaces = normalizeVectorsToEigenFaces(vetoresPropriosA);
            double[][] eigenFacesTranspose = calculateTranspose(eigenFaces);

            double[][] newImage = readMatrixFromFile(newImageName);
            if(!isMatrixNull(newImage)) {
            double[] newImageVector = matrixToColumnVector(newImage);
            double[] phiNova = calculatePhi(newImageVector, vetorMedio);
            double[] omegaNew = calculatePesos(eigenFacesTranspose, phiNova, k);
            System.out.println();
            System.out.println("Número de eigenfaces utilizados: " + k);
            System.out.println();
            System.out.print("Imagem nova: [");
                for (int i = 0; i < omegaNew.length; i++) {
                    System.out.printf("%.2f", omegaNew[i]);
                    if (i < omegaNew.length - 1) {
                        System.out.print("; ");
                    }
                }
            System.out.println("]");
            System.out.println();
            double[][] matrizPesosImages = calculateWj(eigenFaces, A, k);

            double[] image = calculateDistance(omegaNew, matrizPesosImages, k);
            double[] menor = minorDistance(image);
            int[][] imagemProxima = convertColumnInMatrix(imageDataBase, (int) menor[0]);

            System.out.println();
            System.out.printf("Imagem mais próxima (índice: %d)%n", (int) menor[0]);

            saveClosestImage(imagemProxima, "C:\\Users\\Tiago Oliveira\\IdeaProjects\\lapr1-24-25_DCD_09\\Identificacao", (int) menor[0]);
            }
        }
    }
    //Method to calculate deviation new image
    public static double[] calculatePhi(double[] newImage, double[] media) {
        int tamanhoImagem = newImage.length;
        double[] phiNova = new double[tamanhoImagem];

        for (int i = 0; i < tamanhoImagem; i++) {
            phiNova[i] = newImage[i] - media[i];
        }

        return phiNova;
    }
    //Method to calculate Pesos das images base
    public static double[] calculatePesos(double[][] eigenFaces, double[] phiNova, int k) {
        double[] pesos = new double[k];

        for (int j = 0; j < k; j++) {
            pesos[j] = calculatePesoNewImage(eigenFaces[j], phiNova);
        }
        return pesos;
    }
    //Method to calculate euclidean distances
    public static double[] calculateDistance(double[] Nova, double[][] Base, int k) {
        int n = Base[0].length; // Número de colunas (imagens na base)
        double[] distancias = new double[n];

        for (int i = 0; i < n; i++) {
            double soma = 0.0;

            // Print the relevant values of the current image in the base considering only k eigenFaces
            System.out.print("Vetor (com " + k + " eigenfaces) da imagem " + i + ": [");
            for (int j = 0; j < k; j++) {
                System.out.print(Base[j][i]);
                if (j < k - 1) {
                    System.out.print(", ");
                }
            }
            System.out.println("]");

            // Calculate the Euclidean distance considering only k eigenFaces
            for (int j = 0; j < k; j++) {
                double difference = Nova[j] - Base[j][i];
                soma += difference * difference;
            }
            distancias[i] = Math.sqrt(soma);
            System.out.printf("Distância Euclidiana para imagem %d: %.2f%n", i, distancias[i]);
        }

        return distancias;
    }

    //Method to calculate pesos new image
    public static double calculatePesoNewImage(double[] uj, double[] phiNova) {
        double wNovaJ = 0.0;
        for (int i = 0; i < uj.length; i++) {
            wNovaJ += uj[i] * phiNova[i];
        }
        return wNovaJ;
    }
    //Method to calculate all pesos
    public static double[][] calculateWj(double[][] eigenFaces, double[][] phi, int k) {
        int n = eigenFaces.length;
        int numImages = phi[0].length;

        double[][] wj = new double[k][numImages];
        double[][] UTranspose = calculateTranspose(eigenFaces);

        for (int col = 0; col < numImages; col++) {
            for (int j = 0; j < k; j++) {
                double soma = 0.0;
                for (int i = 0; i < n; i++) {
                    soma += UTranspose[j][i] * phi[i][col];
                }
                wj[j][col] = soma;
            }
        }
        return wj;
    }

    public static int[][] convertColumnInMatrix(double[][] matriz, int columnIndex) {
        int numLinhas = matriz.length;
        int dimensao = (int) Math.sqrt(numLinhas);
        int[][] imagem = new int[dimensao][dimensao];
        for (int j = 0; j < dimensao; j++) {
            for (int k = 0; k < dimensao; k++) {
                imagem[j][k] = (int) matriz[(j * dimensao)+k][columnIndex];
            }
        }
        imagem = calculateTransposeInt(imagem);
        return imagem;
    }
    //Method to verify and select K´s
    public static double[] indiceProK(double[] valores, int k) {
        double[][] indiceValores = new double[valores.length][2];

        for (int i = 0; i < valores.length; i++) {
            indiceValores[i][0] = i;
            indiceValores[i][1] = Math.abs(valores[i]);
        }

        for (int i = 0; i < valores.length - 1; i++) {
            for (int j = i + 1; j < valores.length; j++) {
                // Se o valor absoluto de indiceValores[j] for maior que o de indiceValores[i], troca-os
                if (indiceValores[j][1] > indiceValores[i][1]) {
                    double[] temp = indiceValores[i];
                    indiceValores[i] = indiceValores[j];
                    indiceValores[j] = temp;
                }
            }
        }
        double[] kMaioresIndices = new double[k];
        for (int i = 0; i < k; i++) {
            kMaioresIndices[i] = indiceValores[i][0];
        }

        return kMaioresIndices;
    }
    public static double[][] vectorsPK(double[][] vectors, double[] KMaioresIndices, int k) {
        double[][] vetoPK = new double[vectors.length][k];
        for (int i = 0; i < k; i++) {
            int b = (int) KMaioresIndices[i];
            for (int t = 0; t < vectors.length; t++) {
                vetoPK[t][i] = vectors[t][b];
            }
        }
        return vetoPK;
    }
    //Method to compare and obtain minor distance
    public static double[] minorDistance(double[] distance) {
        double[][] copia = new double[distance.length][2];

        for (int i = 0; i < distance.length; i++) {
            copia[i][0] = i;
            copia[i][1] = distance[i];
        }
        // Ordering the elements of the 'copy' matrix by distance (column 1)
        for (int i = 0; i < distance.length - 1; i++) {
            for (int j = i + 1; j < distance.length; j++) {
                if (copia[j][1] < copia[i][1]) {
                    // Swap lines to sort in ascending order
                    double[] temp = copia[i];
                    copia[i] = copia[j];
                    copia[j] = temp;
                }
            }
        }
        // Extraction of indices corresponding to the smallest distances
        int k = distance.length;
        double[] menoresIndices = new double[k];
        for (int i = 0; i < k; i++) {
            menoresIndices[i] = copia[i][0];
        }
        return menoresIndices;
    }

    public static void saveClosestImage(int[][] image, String destinationFolder, int index) throws IOException {
        String outputFilePath = destinationFolder + File.separator + "imagem_" + index + ".jpg";
        writeArrayAsImage(image, outputFilePath);
        System.out.println("Imagem guardada: " + outputFilePath);
    }

    //functionality 4
    public static void functionality4(int k, String folderPath) throws IOException {
        int index = 1;
        double[][] matrizM = obtainMatrixM(folderPath);

        if (!isMatrixNull(matrizM)) {
            int numEigenVectors = matrizM[0].length;
            if (k > numEigenVectors || k == -1) k = numEigenVectors;
            double[] averageVector = calculateAverageVector(matrizM);
            double[][] A = constructMatrixA(matrizM, averageVector);
            double[][] matrixCovariance = calculateCovariance(A);
            double[][] vetoresProprios = calculateEigenVectors(matrixCovariance);
            double[][] vetoresPropriosA = multiplyMatrix(A, vetoresProprios);
            double[][] eigenFaces = normalizeVectorsToEigenFaces(vetoresPropriosA);
            double[] valoresProprios = calculateEigenValues(matrixCovariance);

            createImage(averageVector, eigenFaces, valoresProprios, k, "C:\\Users\\Tiago Oliveira\\IdeaProjects\\lapr1-24-25_DCD_09\\ImagemNova", index);
        } else System.out.println("Erro ao ler matriz inicial!");

    }

    public static void createImage(double[] media, double[][] eigenFaces, double[] valoresProprios, int k, String outputFolder, int index) throws IOException {
        int tamanhoImagem = media.length;
        double[] novaImagem = new double[tamanhoImagem];
        Random random = new Random();

        // Initialize new image with the average (\(\mu\))
        for (int i = 0; i < tamanhoImagem; i++) {
            novaImagem[i] = media[i];
        }
        double [][] eigenFacesTranspose = calculateTranspose(eigenFaces);
        // Create the random weights (\(w_i\)) and combine them with the EigenFaces (\(E_i\))
        for (int i = 0; i < k; i++) {
            double wi = random.nextDouble() * (2 * Math.sqrt(valoresProprios[i])) - Math.sqrt(valoresProprios[i]);
            for (int j = 0; j < tamanhoImagem; j++) {
                novaImagem[j] += wi * eigenFacesTranspose[i][j];
            }
        }

        int[][] imagem = vectorToMatrix(novaImagem);
        int [][] imagemFinal = calculateTransposeInt(imagem);
        saveClosestImage(imagemFinal, outputFolder, index);
    }

    public static int[][] vectorToMatrix(double[] vector) {
        int tamanho = (int) Math.sqrt(vector.length);
        int[][] matriz = new int[tamanho][tamanho];

        // Encontra o mínimo e o máximo para normalização
        double min = MIN_VALUE_MATRIX_LENGTH;
        double max = MAX_VALUE_MATRIX_LENGTH;
        for (double valor : vector) {
            if (valor < min) min = valor;
            if (valor > max) max = valor;
        }

        // Normaliza os valores para o intervalo [0, 255]
        for (int i = 0; i < tamanho; i++) {
            for (int j = 0; j < tamanho; j++) {
                double valorNormalizado = (vector[i * tamanho + j] - min) / (max - min) * 255.0;
                matriz[i][j] = (int) Math.round(valorNormalizado);
            }
        }

        return matriz;
    }

}

