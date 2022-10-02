package tlk.jorva.glorycounter;

import lombok.extern.slf4j.Slf4j;
import net.runelite.client.RuneLite;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.*;

@Slf4j
@Singleton
public final class LocalStorageHandler {

    private static final File DATA_FOLDER = new File(RuneLite.RUNELITE_DIR, "glory-counter");

    @Inject
    public LocalStorageHandler() {
        DATA_FOLDER.mkdir();
    }


    public synchronized boolean writeToFile(long hash, int amount) {
        try {
            File file = new File(DATA_FOLDER, hash + "");
            if (!file.exists()) {
                file.createNewFile();
            }

            final BufferedWriter writer = new BufferedWriter(new FileWriter(String.valueOf(file), false));
            writer.write("" + amount);
            writer.flush();
            writer.close();

            return true;
        } catch (Exception e) {
            log.warn("Error writing glory data for {}: {}", hash, e.getMessage());
            return false;
        }
    }

    public synchronized int readFromFile(long hash) {
        try {
            File file = new File(DATA_FOLDER, hash + "");
            final BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = reader.readLine();
            reader.close();
            return Integer.parseInt(line);
        } catch (Exception e) {
            log.warn("Error reading glory data for {}: {}", hash, e.getMessage());
        }
        return -1;
    }

}
