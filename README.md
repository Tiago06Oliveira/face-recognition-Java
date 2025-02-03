# 🖼️ Eigenfaces - Facial Recognition with Linear Algebra

This project implements a **facial recognition system** based on the **Eigenfaces** method, using **Java** and libraries for matrix manipulation and image processing.

## 📂 Project Structure

```
📦 Eigenfaces
 ┣ 📂 Eigenfaces                 # In this folder, results are stored in ".csv" files
 ┣ 📂 Identification              # Identification of the closest image
 ┣ 📂 ReconstructedImages       # Stores images reconstructed from Eigenfaces
 ┣ 📂 Input                      # Contains input images for testing
 ┣ 📂 Output                     # Processing and testing results
 ┣ 📂 FunctionalityTests1      # Tests for validating functionality 1 - Linear Algebra
 ┣ 📂 FunctionalityTests2_4x4  # Tests for 4x4 matrix - Linear Algebra
 ┣ 📂 FunctionalityTests2_8x8  # Tests for 8x8 matrix - Linear Algebra
 ┣ 📂 FunctionalityTests3      # Tests for functionality 3 - Linear Algebra
 ┣ 📜 commons-math3-3.6.1.jar    # Linear algebra library (Apache Commons Math)
 ┣ 📜 lapr1-24-25_DCD_09apresentacao.jar # Compiled application file for easier execution
 ┣ 📜 README.md                  # Project documentation
```

## 🚀 Features

✅ **Eigenfaces Calculation**: Extraction of the main characteristics of images.  
✅ **Facial Reconstruction**: Compact representation of faces based on k eigenfaces.  
✅ **Facial Identification**: Comparison between images and recognition based on the number of k eigenfaces.  
✅ **Facial Identification**: Given an image database and k eigenfaces, creates a new face.  
✅ **Automated Tests**: A set of tests to validate each functionality without using JUnit.  
✅ **Interactive and Non-Interactive Mode**

## 🛠️ Technologies Used

- **Java** ☕
- **Apache Commons Math** (Matrix operations)
- **Grayscale image processing** 🎭

## 🔧 How to Run

1. **Clone the repository:**
   ```sh
   git clone https://github.com/your-user/your-repository.git
   cd your-repository
   ```
2. **Compile the project:**
   ```sh
   javac -cp lib/commons-math3-3.6.1.jar src/*.java  
   ```
3. **Run the application:**
   ```sh
   java -cp lib/commons-math3-3.6.1.jar:src Main
   ```

## 📥 Understanding the Inputs

The application can be executed in **interactive mode** or **non-interactive mode**.

### 🔹 Interactive Mode
The application should be run from the command line using:
```sh
java -jar program_name.jar
```
In this mode, all necessary parameters are requested from the user during execution.

### 🔹 Non-Interactive Mode
The user must specify all parameters in the command line, following the syntax:
```sh
java -jar program_name.jar -f X -k Y -i Z -d W output_file.txt
```

The parameters are:

- **`-f X`**: Defines the functionality to execute.
    - `X = 1` → Matrix eigen decomposition.
    - `X = 2` → Image reconstruction using eigenfaces.
    - `X = 3` → Image identification using eigenface weights.
- **`-k Y`**: Number of eigenfaces to use.
    - `Y > 0` → Defines a specific number.
    - `Y = -1` or greater than the number of available eigenvalues → Uses all real eigenvalues of the matrix.
- **`-i Z`**: Path to the CSV file containing the input matrix/image.
    - Required only for functionalities `1` and `3`.
- **`-d W`**: Path to the image database.
    - Required only for functionalities `2` and `3`.
- **`output_file.txt`**: Name of the file where results will be saved if the non-interactive mode is used.

---

## 📜 About the Project

This project was developed as part of the **LAPR1** course at ISEP and aims to explore **Linear Algebra techniques applied to facial recognition**, using matrix decomposition to identify visual patterns.

---