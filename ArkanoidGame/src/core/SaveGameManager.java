package core;

import java.io.*;

public class SaveGameManager implements Serializable {
    private static final String FILE_PATH = "ArkanoidGame/data/savegame.dat";

    // Save Game
    public static void saveGame(SaveGame data) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            oos.writeObject(data);
            System.out.print("Game Save Successfully");
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Load Game
    public static SaveGame loadGame() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_PATH))) {
            return (SaveGame) ois.readObject();
            
        } catch (Exception e) {
            return null;
        }
    }
    // Delete File Save Game
    public static void deleteSave() {
        new File(FILE_PATH).delete();
    }

    public static boolean hasSaveGame() {
        File file = new File(FILE_PATH);
        return file.exists() && file.length() > 0;
    }
}
