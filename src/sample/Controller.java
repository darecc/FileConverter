package sample;

import com.sun.org.apache.xml.internal.security.Init;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.stage.FileChooser;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

import static java.lang.System.exit;

public class Controller implements Initializable {
    @FXML
    File file;
    @FXML
    Button konwertowanie = new Button();
    @FXML
    Button wyborPliku = new Button();
    @FXML
    TextField text = new TextField();
    @FXML
    public void initialize(URL u, ResourceBundle rb) {
        loadImage();
        konwertowanie.setDisable(true);
        String styl = "-fx-background-color: \n" +
                "        #3c7fb1,\n" +
                "        linear-gradient(#fafdfe, #e8f5fc),\n" +
                "        linear-gradient(#eaf6fd 0%, #d9f0fc 49%, #bee6fd 50%, #a7d9f5 100%);\n" +
                "    -fx-background-insets: 0,1,2;\n" +
                "    -fx-background-radius: 3,2,1;\n" +
                "    -fx-padding: 3 30 3 30;\n" +
                "    -fx-text-fill: black;\n" +
                "    -fx-font-size: 14px;";
        String styl1 = "-fx-background-color: #D7BCC9; -fx-color: #EE7C00;";
        String styl2 = "-fx-background-color: #D78CA9; -fx-color: #33ACBB;";
        konwertowanie.setStyle(styl);
        wyborPliku.setStyle(styl);
        wyborPliku.setTooltip(new Tooltip("Pamiętaj, że wybrany plik musi być kodowany UTF-8"));
    }

    @FXML
    public void wybierzPlik() {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter1 = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        FileChooser.ExtensionFilter extFilter2 = new FileChooser.ExtensionFilter("SRT files (*.srt)", "*.srt");
        fileChooser.getExtensionFilters().add(extFilter1);
        fileChooser.getExtensionFilters().add(extFilter2);
        Scene scene = konwertowanie.getParent().getScene();
        file = fileChooser.showOpenDialog(scene.getWindow());
        if (file != null) {
            konwertowanie.setDisable(false);
            System.out.println(file.getAbsolutePath());
        }
    }
    private void loadImage() {

    }
    @FXML void konwertuj() {
        try {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file + ".new"), "UTF8"));
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    new FileInputStream(file), "UTF8"));
            String linia = "";
            int count = 0;
            while((linia = br.readLine()) != null) {
                if (linia.startsWith("00:") || linia.startsWith("01:")) {
                    count++;
                    linia = linia.replace(": ", ":");
                    linia = linia.replace(" ->", " -->");
                    String zm = linia.substring(8,9);
                    if (zm.equals(" ") || zm.equals(".")) {
                        //00:33:18,050 --> 00:33:19,303
                        //012345678901234567890123456
                        count++;
                        linia = linia.substring(0, 8) + "," + linia.substring(9);
                    }
                    zm = linia.substring(25,26);
                    if (zm.equals(" ") || zm.equals(".")) {
                        //00:33:18,050 --> 00:33:19,303
                        //012345678901234567890123456
                        linia = linia.substring(0, 25) + "," + linia.substring(26);
                        count++;
                    }
                }
                bw.write(linia);
                bw.newLine();
            }
            br.close();
            bw.close();
            text.setText("Liczba zmian: " + count);
        }
        catch(IOException ie) {
            System.out.println(ie.getMessage());
        }
    }
}