package cdm-main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

public class ConsolaGUI extends JFrame {
    private Comandos cmd;
    private JTextArea outputTextArea;
    private JTextField inputTextField;
    private JLabel directoryLabel;

    public ConsolaGUI() {
        
        cmd = new Comandos(System.getProperty("user.dir"));
        setTitle("Consola de Comandos");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.BLACK); 

        JPanel topPanel = new JPanel(new BorderLayout());
        directoryLabel = new JLabel("Directorio actual: " + cmd.getCurrentPath());
        directoryLabel.setForeground(Color.WHITE); 
        topPanel.setBackground(Color.BLACK); 
        topPanel.add(directoryLabel, BorderLayout.NORTH);

        outputTextArea = new JTextArea();
        outputTextArea.setEditable(false);
        outputTextArea.setBackground(Color.BLACK); 
        outputTextArea.setForeground(Color.WHITE); 
        JScrollPane scrollPane = new JScrollPane(outputTextArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        inputTextField = new JTextField();
        inputTextField.setBackground(Color.BLACK); 
        inputTextField.setForeground(Color.WHITE); 
        inputTextField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String command = inputTextField.getText().trim();
                ejecutarComando(command);
            }
        });

        getContentPane().setLayout(new BorderLayout()); 
        getContentPane().add(topPanel, BorderLayout.NORTH);
        getContentPane().add(scrollPane, BorderLayout.CENTER);
        getContentPane().add(inputTextField, BorderLayout.SOUTH);

        inputTextField.requestFocusInWindow();
    }

    private void ejecutarComando(String command) {
        String[] parts = command.split("\\s+", 2);
        String cmdName = parts[0];
        String args = parts.length > 1 ? parts[1] : "";

        String result = "";
        switch (cmdName.toLowerCase()) {
            case "mkdir":
                result = cmd.Mkdir(args);
                break;
            case "mfile":
                result = cmd.Mfile(args);
                break;
            case "rm":
                if (args.length()==1){
                    result="Error: Debe ingresar la carpeta o archivo que desea eliminar.";
                    break;
                }
                File destino=new File(cmd.getCurrentPath()+"/"+args);
                result=cmd.Rm(destino);
                break;
            case "cd":
                result = cmd.Cd(args);
                break;
            case "dir":
                result = cmd.Dir(cmd.getCurrentPath());
                break;
            case "date":
                result = cmd.date();
                break;
            case "time":
                result = cmd.time();
                break;
            case "...":
                cmd.cambioDir("..");
                break;
            case "wr":
                cmd.escribir(args);
                result = "Texto escrito en archivo.";
                break;
            case "rd":
                result = cmd.leer();
                break;
            default:
                result = "Error: Comando no reconocido.";
        }

        outputTextArea.append("> " + command + "\n" + result + "\n");
        inputTextField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                ConsolaGUI consola = new ConsolaGUI();
                consola.setVisible(true);
            }
        });
    }
}

