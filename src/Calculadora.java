import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class Calculadora {
    JFrame frame=new JFrame("Calculadora");
    Color claro=new Color(212,212,210);
    Color oscuro=new Color(80,80,80);
    Color negro=new Color(28,28,28);
    Color naranja=new Color(255,149,0);

    JLabel texto =new JLabel();
    JPanel panel=new JPanel();
    JPanel botones = new JPanel();
    String[]valores={
            "AC", "+/-", "%", "÷",
            "7", "8", "9", "×",
            "4", "5", "6", "-",
            "1", "2", "3", "+",
            "0", ".", "√" , "="
    };
    String[] rightSymbols = {"÷", "×", "-", "+", "="};
    String[] topSymbols = {"AC", "+/-", "%"};
    String A="0";
    String B = null;
    String operator=null;

    Calculadora(){
        //Los atributos de la ventana se ponen en el constructor
        frame.setSize(360,540);
        frame.setLocationRelativeTo(null); //esto sirve para centrar a la ventana
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//Para que cuando le des a la x se cierre
        frame.setLayout(new BorderLayout());// Para poner propiedades sobre los elementos con norte sur , etc...
        texto.setBackground(negro);
        texto.setForeground(Color.white); //color del texto
        texto.setFont(new Font("Arial",Font.PLAIN,80));
        texto.setHorizontalTextPosition(JLabel.RIGHT);
        texto.setText("0");
        texto.setOpaque(true);
        panel.setLayout(new BorderLayout());//BorderLayout	Divide el espacio en cinco regiones: North, South, East, West, Center
        panel.add(texto);
        frame.add(panel, BorderLayout.NORTH);
        botones.setLayout(new GridLayout(5,4));
        botones.setBackground(negro);
        frame.add(botones);

        for (int i =0;i<valores.length;i++){
            JButton boton=new JButton();
            // Obtenemos el texto que llevará el botón (como "7", "+", "AC", etc.)
            String valorboton=valores[i];
            boton.setFocusable(false); //para que no aparezca el cuadrado alrededor al seleccionar
            boton.setBorder(new LineBorder(negro));
            boton.setFont(new Font("Arial",Font.PLAIN,30));
            boton.setText(valorboton);
            if (valorboton.equals("AC") || valorboton.equals("+/-") || valorboton.equals("%")) {
                boton.setBackground(claro);
                boton.setForeground(negro);
            }else if (valorboton.equals("÷") || valorboton.equals("×") ||
                    valorboton.equals("-") || valorboton.equals("+") || valorboton.equals("=")) {
                boton.setBackground(naranja);
                boton.setForeground(Color.white);
            }    else {
                boton.setBackground(oscuro);
                boton.setForeground(Color.white);
            }
            boton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
              JButton button= (JButton) e.getSource();
              String valordelboton= button.getText(); //arrayaslist lo pongo porque transforma el normal en un arraylist temporal, de esta forma teniendo mas metodos como constains
                    //porque si no tendré que hacer como arriba y señalizar uno por uno
              if (Arrays.asList(rightSymbols).contains(valordelboton)){
                  if (valordelboton== "="){
                      if (A !=null){
                         B=texto.getText();
                         double numA=Double.parseDouble(A); //transforma un string a un decimal
                          double numB=Double.parseDouble(B);
                          if (operator == "+"){
                              texto.setText(removeZeroDecimal(numA+numB));
                          }  else if (operator == "-") {
                              texto.setText(removeZeroDecimal(numA-numB));
                          }  else if (operator == "×") {
                              texto.setText(removeZeroDecimal(numA*numB));
                          }else if (operator == "÷") {
                             texto.setText(removeZeroDecimal(numA/numB));
                          }
                          clearAll();

                      }
                  } else if ("+-×÷".contains(valordelboton)) {
                      if (operator==null){
                          A=texto.getText();
                          texto.setText("0");
                          B="0";
                      }
                      operator=valordelboton;
                  }
              } else if (Arrays.asList(topSymbols).contains(valordelboton)) {
                  if (valordelboton=="AC"){
                   clearAll();
                      texto.setText("0");
                  } else if (valordelboton=="+/-") {
                      double numDisplay = Double.parseDouble(texto.getText());
                      numDisplay *= -1;
                      texto.setText(removeZeroDecimal(numDisplay));
                  } else if (valordelboton=="%") {
                      double numDisplay = Double.parseDouble(texto.getText());
                      numDisplay /= 100;
                     texto.setText(removeZeroDecimal(numDisplay));
                  }

              }else {
                  if (valordelboton == ".") {
                      if (!texto.getText().contains(valordelboton)) {
                          texto.setText(texto.getText() + valordelboton); //para que solo se ponga 1 vez y administrarlo
                      }
                  }
                  else if ("0123456789".contains(valordelboton)) {
                      if (texto.getText() == "0") {
                          texto.setText(valorboton); // atribuimos valores y hacemos que se pongan los numeros por pantalla
                      }
                      else {
                          texto.setText(texto.getText() + valordelboton); //para que no se reemplaze el numero y si pones mas que sigan adelante
                      }
              }
                }}
            });

            botones.add(boton);


        }

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new Calculadora();
    }
    void clearAll() {
        A = "0";
        operator = null;
        B = null;
    }
    String removeZeroDecimal(double numDisplay) {
        if (numDisplay % 1 == 0) {
            return Integer.toString((int) numDisplay);
        }
        return Double.toString(numDisplay);
    }
}
