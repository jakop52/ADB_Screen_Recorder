import java.io.IOException;

public class Main {
    public static void main(String[] args) throws Exception {
        //System.setProperty("java.library.path", "/usr/local/lib");

        ScrcpyScreenRecorder scrcpyScreenRecorder = new ScrcpyScreenRecorder();

        scrcpyScreenRecorder.startRecording();

        Thread.sleep(1*1000*60);

        scrcpyScreenRecorder.stopRecording();
    }
}