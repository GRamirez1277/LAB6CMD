package CmdLab6;
/**
 *
 * @author crist
 */
import java.io.*;
import java.util.Date;
import javax.swing.JOptionPane;

public class manejoArchivos {
    private File comandos = null;

    void setFile(String direccion) {
        comandos = new File(direccion);
    }



    boolean Mfile() throws IOException {
        return comandos.createNewFile();
    }

    boolean Mkdir() {
        return comandos.mkdirs();
    }

    boolean rm(File f) {
        if (f.isDirectory()) {
            for (File child : f.listFiles()) {
                rm(child);
            }
        }
        return f.delete();
    }

    boolean Rm() {
        return rm(comandos);
    }

    void dir() {
        if (comandos.isDirectory()) {
            System.out.println("Directorio de: " + comandos.getAbsolutePath());
            System.out.println(" ");
            int cfiles = 0, cdirs = 0, tbytes = 0;

            for (File child : comandos.listFiles()) {
                if (!child.isHidden()) {
                    //Ultima modificacion
                    Date ultima = new Date(child.lastModified());
                    System.out.println(ultima + "\t");
                    //Si es File o Dir
                    if (child.isDirectory()) {
                        cdirs++;
                        System.out.println("<DIR>\t\t");
                    } else {
                        cfiles++;
                        tbytes += child.length();
                    }
                    System.out.println(child.getName());
                }
            }
            System.out.println(cfiles + " archivos\t" + tbytes + " bytes"
                    + "\n" + cdirs + " dirs");
        }
    }

    void date() {
        Date fechaActual = new Date();
        System.out.println("Fecha actual: " + fechaActual);
    }

    void time() {
        Date horaActual = new Date();
        System.out.println("Hora actual: " + horaActual);
    }

    void escribir(String contenido) {
        try {
            if (comandos == null) {
           JOptionPane.showMessageDialog(null,"Error, archivo inexistente.");
                return;
            }
            FileWriter escritor = new FileWriter(comandos, true);
            BufferedWriter bw = new BufferedWriter(escritor);
            bw.write(contenido);
            bw.newLine();
            bw.close();
            JOptionPane.showMessageDialog(null,"Se ha escrito en el archivo.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void leer() {
        try {
            if (comandos == null) {
                   JOptionPane.showMessageDialog(null,"Error, archivo inexistente.");

                return;
            }
            FileReader lector = new FileReader(comandos);
            BufferedReader br = new BufferedReader(lector);
            String linea;
            while ((linea = br.readLine()) != null) {
                System.out.println(linea);
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}