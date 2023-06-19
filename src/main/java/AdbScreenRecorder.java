import org.bytedeco.javacv.FFmpegFrameRecorder;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

public class AdbScreenRecorder {
    private FFmpegFrameRecorder recorder;
    private Thread captureThread;
    private AtomicBoolean isRecording;

    public AdbScreenRecorder(String outputPath, int width, int height, double frameRate) {
        this.recorder = new FFmpegFrameRecorder(outputPath, width, height);
        this.recorder.setFrameRate(frameRate);
        this.recorder.setVideoBitrate(9000);
        this.isRecording = new AtomicBoolean(false);
    }

    public void startRecording() throws IOException {
        this.isRecording.set(true);
        this.captureThread = new Thread(() -> {
            int counter = 0;
            Java2DFrameConverter converter = new Java2DFrameConverter();
            while (isRecording.get()) {
                try {
                    String cmdCapture = "adb shell screencap -p /sdcard/screen" + counter + ".png";
                    Process p = Runtime.getRuntime().exec(cmdCapture);
                    p.waitFor();

                    String cmdPull = "adb pull /sdcard/screen" + counter + ".png ./";
                    Process p2 = Runtime.getRuntime().exec(cmdPull);
                    p2.waitFor();

                    BufferedImage image = ImageIO.read(new File("screen" + counter + ".png"));
                    Frame frame = converter.convert(image);
                    recorder.record(frame);

                    counter++;
                    Thread.sleep(33); // sleep for ~33 ms to achieve ~30 fps

                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        try {
            this.recorder.start();
            this.captureThread.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stopRecording() {
        this.isRecording.set(false);
        try {
            this.captureThread.join();
            this.recorder.stop();
            this.recorder.release();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

