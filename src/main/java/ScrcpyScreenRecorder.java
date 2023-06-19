import java.io.IOException;
public class ScrcpyScreenRecorder {
    private Process scrcpyProcess;

    public void startRecording() throws IOException {
        ProcessBuilder processBuilder = new ProcessBuilder("scrcpy", "--record", "outputScrCpy.mp4");
        scrcpyProcess = processBuilder.start();
    }

    public void stopRecording() {
        scrcpyProcess.destroy();
    }
}
