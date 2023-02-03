package lk.ijse.studentsmanagement.qr;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import javafx.embed.swing.SwingNode;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import javax.swing.*;
import javax.swing.text.Element;
import javax.swing.text.html.ImageView;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class QRGenerator{
    public static void getGenerator(String id) throws IOException, WriterException {
        String path = "/home/shan/Downloads/1st-Semester-Final-Project-master/src/lk/ijse/registrationQRCodes"+id+".png";
        BitMatrix encode = new MultiFormatWriter().encode(id, BarcodeFormat.QR_CODE, 200, 200);
        Path path1 = Paths.get(path);
        ///home/shan/Downloads/1st-Semester-Final-Project-master/src/lk
        MatrixToImageWriter.writeToPath(encode,path.substring(path.lastIndexOf('.')+1), path1);
//        Stage stage = new Stage();
//        ImageView imageView = new ImageView(path);
//        //Setting image to the image view
//        imageView.setImage(new Image(Files.newInputStream(path1)));
//        imageView.setFitWidth(575);
//        imageView.setPreserveRatio(true);
//        //Setting the Scene object
//        Group root = new Group(imageView);
//
//        StackPane stackPane = new StackPane();
//        ImageView image = new ImageView();
//        image.setParent(new ImageView((Element) Files.newInputStream(path1));
//        stackPane.getChildren().add();
//       // Stage stage = new Stage();
//        stage.setScene(new Scene(stackPane, 800, 600));
//        stage.show();
    }
}
