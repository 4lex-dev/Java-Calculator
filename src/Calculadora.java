import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;

public class Calculadora {

    JFrame frame = new JFrame("Calculadora");
    JLabel pantalla = new JLabel("0", SwingConstants.RIGHT); // el texto de la pantalla estará alineado a la derecha

    Color claro = new Color(212, 212, 210);
    Color oscuro = new Color(80, 80, 80);
    Color negro = new Color(28, 28, 28);
    Color naranja = new Color(255, 149, 0);

    // todos los botones de la calculadora (ordenados)
    String[] valores = {
            "AC", "+/-", "%", "÷",
            "7", "8", "9", "×",
            "4", "5", "6", "-",
            "1", "2", "3", "+",
            "0", ".", "√", "="
    };

    // botones que hacen operaciones matemáticas
    String[] operadores = {"÷", "×", "-", "+", "="};

    // botones de arriba: limpiar, signo y porcentaje
    String[] funciones = {"AC", "+/-", "%"};

    // variables para hacer las cuentas
    String A = "0"; // primer número
    String B = null; // segundo número
    String operador = null; // tipo de operación que elegimos

    public Calculadora() {
        // configuramos la ventana principal
        frame.setSize(360, 540);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // cuando le damos a la X se cierra
        frame.setLocationRelativeTo(null); // para que aparezca centrada
        frame.setResizable(false); // no se puede redimensionar
        frame.setLayout(new BorderLayout()); // layout para poner cosas arriba y al centro

        // configuramos la pantalla
        pantalla.setFont(new Font("Arial", Font.PLAIN, 80));
        pantalla.setOpaque(true);
        pantalla.setBackground(negro);
        pantalla.setForeground(Color.white);
        frame.add(pantalla, BorderLayout.NORTH); // la ponemos arriba

        // panel donde van los botones
        JPanel panelBotones = new JPanel(new GridLayout(5, 4)); // 5 filas y 4 columnas
        panelBotones.setBackground(negro);

        // creamos cada botón
        for (String textoBoton : valores) {
            JButton boton = new JButton(textoBoton);
            boton.setFocusable(false); // quita el borde azul al pulsar
            boton.setFont(new Font("Arial", Font.PLAIN, 30));
            boton.setBorder(new LineBorder(negro)); // borde del botón

            // le damos color dependiendo del tipo de botón
            if (Arrays.asList(funciones).contains(textoBoton)) {
                boton.setBackground(claro);
                boton.setForeground(negro);
            } else if (Arrays.asList(operadores).contains(textoBoton)) {
                boton.setBackground(naranja);
                boton.setForeground(Color.white);
            } else {
                boton.setBackground(oscuro);
                boton.setForeground(Color.white);
            }

            // qué pasa cuando se hace clic en un botón
            boton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String valor = boton.getText(); // obtenemos el texto del botón que pulsamos

                    // si es un operador tipo + - × ÷ =
                    if (Arrays.asList(operadores).contains(valor)) {
                        manejarOperador(valor);
                    }
                    // si es un botón especial tipo AC, +/- o %
                    else if (Arrays.asList(funciones).contains(valor)) {
                        manejarFuncion(valor);
                    }
                    // si es un número o un punto
                    else {
                        manejarNumero(valor);
                    }
                }
            });

            panelBotones.add(boton); // lo añadimos al panel
        }

        frame.add(panelBotones, BorderLayout.CENTER); // ponemos los botones en el centro
        frame.setVisible(true); // mostramos la ventana
    }

    // método para cuando pulsamos un operador
    void manejarOperador(String valor) {
        if (valor.equals("=")) {
            if (A != null && operador != null) {
                B = pantalla.getText(); // segundo número
                double numA = Double.parseDouble(A);
                double numB = Double.parseDouble(B);
                double resultado = 0;

                // hacemos la operación según el operador
                switch (operador) {
                    case "+":
                        resultado = numA + numB;
                        break;
                    case "-":
                        resultado = numA - numB;
                        break;
                    case "×":
                        resultado = numA * numB;
                        break;
                    case "÷":
                        if (numB != 0) {
                            resultado = numA / numB;
                        } else {
                            pantalla.setText("Error"); // no se puede dividir entre 0
                            limpiar(); // reiniciamos todo
                            return;
                        }
                        break;
                    default:
                        resultado = numB;
                }

                pantalla.setText(formatear(resultado)); // mostramos el resultado
                limpiar(); // reiniciamos variables
            }
        } else {
            // si pulsamos un operador pero aún no le dimos al igual
            if (operador == null) {
                A = pantalla.getText(); // guardamos el primer número
                pantalla.setText("0"); // limpiamos pantalla para el segundo número
                B = "0";
            }
            operador = valor; // guardamos el tipo de operación
        }
    }

    // método para botones tipo AC, +/- y %
    void manejarFuncion(String valor) {
        if (valor.equals("AC")) {
            limpiar();
            pantalla.setText("0");
        } else if (valor.equals("+/-")) {
            double numero = Double.parseDouble(pantalla.getText());
            numero *= -1; // cambiamos el signo
            pantalla.setText(formatear(numero));
        } else if (valor.equals("%")) {
            double numero = Double.parseDouble(pantalla.getText());
            numero /= 100; // sacamos el porcentaje
            pantalla.setText(formatear(numero));
        }
    }

    // método para cuando se pulsa un número o un punto
    void manejarNumero(String valor) {
        String actual = pantalla.getText();

        if (valor.equals(".")) {
            if (!actual.contains(".")) {
                pantalla.setText(actual + ".");
            }
        } else if ("0123456789".contains(valor)) {
            if (actual.equals("0")) {
                pantalla.setText(valor); // si hay un 0 al inicio, lo reemplazamos
            } else {
                pantalla.setText(actual + valor); // si ya hay algo, lo agregamos al final
            }
        }
    }

    // este método reinicia todas las variables (como si empezáramos de nuevo)
    void limpiar() {
        A = "0";
        B = null;
        operador = null;
    }

    // este método quita el .0 si el número es entero
    String formatear(double num) {
        if (num % 1 == 0) {
            return Integer.toString((int) num);
        } else {
            return Double.toString(num);
        }
    }

    public static void main(String[] args) {
        new Calculadora(); // iniciamos la app
    }
}
