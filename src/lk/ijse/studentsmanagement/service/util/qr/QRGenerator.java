package lk.ijse.studentsmanagement.service.util.qr;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class QRGenerator implements Runnable{
    static QRGenerator qrGenerator;
    private String id;

    private QRGenerator() {
    }

    public static QRGenerator getInstance() {
        return qrGenerator == null ? (qrGenerator = new QRGenerator()) : qrGenerator;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void generate() throws IOException, WriterException, RuntimeException {
        if (id == null) throw new RuntimeException("Id is null");
        String path = "/home/shan/Downloads/1st-Semester-Final-Project-master/src/lk/ijse/studentsmanagement/service/util/qr/images/registrationQRCodes" + id + ".png";
        BitMatrix encode = new MultiFormatWriter().encode(id, BarcodeFormat.QR_CODE, 200, 200);
        Path path1 = Paths.get(path);
        MatrixToImageWriter.writeToPath(encode, path.substring(path.lastIndexOf('.') + 1), path1);
    }

    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        try {
            generate();
        } catch (IOException | WriterException e) {
            throw new RuntimeException(e);
        }
    }
}
