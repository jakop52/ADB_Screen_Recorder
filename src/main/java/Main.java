import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException, IOException {
        AdbScreenRecorder recorder = new AdbScreenRecorder("output.mp4", 720, 1280, 30);
        recorder.startRecording();

        Thread.sleep(10000); // na przykład, nagrywa przez 10 sekund

        recorder.stopRecording();
    }
}