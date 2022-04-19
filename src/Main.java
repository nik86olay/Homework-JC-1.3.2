import java.io.*;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {

    public static void main(String[] args) {
        GameProgress save0 = new GameProgress(80, 3, 25, 854.23);
        GameProgress save1 = new GameProgress(100, 1, 1, 10.51);
        GameProgress save2 = new GameProgress(15, 9, 94, 1808.37);

        List<String> wayObj = new ArrayList<>();
        Collections.addAll(wayObj, "D://Games/savegames/save0.dat", "D://Games/savegames/save1.dat",
                "D://Games/savegames/save2.dat");

        saveGame(save0, wayObj.get(0));
        saveGame(save1, wayObj.get(1));
        saveGame(save2, wayObj.get(2));
        zipFiles("D://Games/savegames/zip_output.zip", wayObj);

        // Не понимаю почему не работает данный блок.
        // Информацию таким образом получить возможно, а удаление не удается.
        File dir = new File("D://Games/savegames");
        if (dir.isDirectory()) {
            for (File item : Objects.requireNonNull(dir.listFiles())) {

                if (!item.getName().equals("zip_output.zip")) {
                    if (item.delete()) System.out.println("файл удален");
                } else System.out.println("файл пропущен");
            }
        }
    }

    private static void saveGame(GameProgress save, String wayObj) {
        try (FileOutputStream file = new FileOutputStream(wayObj);
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(file)) {
            objectOutputStream.writeObject(save);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private static void zipFiles(String wayZip, List<String> wayObj) {

        try (ZipOutputStream zip = new ZipOutputStream(new FileOutputStream(wayZip))) {
            for (String s : wayObj) {
                FileInputStream inputStream = new FileInputStream(s);
                ZipEntry entry = new ZipEntry(s.substring(s.lastIndexOf("/") + 1));
                zip.putNextEntry(entry);
                byte[] buffer = new byte[inputStream.available()];
                inputStream.read(buffer);
                zip.write(buffer);
                zip.closeEntry();
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

    }
}
