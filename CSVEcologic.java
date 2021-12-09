import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

class User {
    private int id;
    private String name;
    private int waterCount;
    private int gasCount1;
    private int gasCount2;
    private int electroCount1;
    private int electroCount2;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getWaterCount() {
        return waterCount;
    }

    public int getGasCount1() {
        return gasCount1;
    }

    public int getGasCount2() {
        return gasCount2;
    }

    public int getElectroCount1() {
        return electroCount1;
    }

    public int getElectroCount2() {
        return electroCount2;
    }

    public User(int id, String name, int waterCount, int gasCount1, int gasCount2, int electroCount1, int electroCount2) {
        this.id = id;
        this.name = name;
        this.waterCount = waterCount;
        this.gasCount1 = gasCount1;
        this.gasCount2 = gasCount2;
        this.electroCount1 = electroCount1;
        this.electroCount2 = electroCount2;
    }
}

class UserService {
    public static ArrayList getUsers(String path) {
        ArrayList<User> users = new ArrayList<>();
        String line;

        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            reader.readLine();  // название полей

            while ((line=reader.readLine()) != null) {
                String[] words = line.split("\\|");
                int id = Integer.parseInt(words[0]);
                String name = words[1];
                int waterCount = Integer.parseInt(words[2]);
                int gasCount1 = Integer.parseInt(words[3]);
                int gasCount2 = Integer.parseInt(words[4]);
                int electroCount1 = Integer.parseInt(words[5]);
                int electroCount2 = Integer.parseInt(words[6]);
                User user = new User(id, name, waterCount, gasCount1, gasCount2, electroCount1, electroCount2);
                users.add(user);
            }
        } catch (IOException ioException) {
            System.out.println(ioException.getMessage());
        }

        return users;
    }

    public static ArrayList<User> getEcologicUsers(ArrayList<User> objects, int maxCount) {
        ArrayList<User> ecoUsers = new ArrayList<>();

        for (User user : objects) {
            if (user.getWaterCount() < maxCount
                    && user.getGasCount1()+user.getGasCount2() < maxCount
                    && user.getElectroCount1()+user.getElectroCount2() < maxCount) {
                ecoUsers.add(user);
            }
        }

        return ecoUsers;
    }

    public static void writeUsers(ArrayList<User> users, String path) {
        String line;
        
        try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(path)))){
            out.println("id|name|waterCount|gasCount1|gasCount2|electroCount1|electroCount2");

            for (User user : users) {
                line = String.join("|",
                        String.valueOf(user.getId()),
                        user.getName(), 
                        String.valueOf(user.getWaterCount()),
                        String.valueOf(user.getGasCount1()),
                        String.valueOf(user.getGasCount2()),
                        String.valueOf(user.getElectroCount1()),
                        String.valueOf(user.getElectroCount2()));
                out.println(line);
            }
        } catch (IOException ioException) {
            System.out.println(ioException.getMessage());
        }
    }
}

public class CSVEcologic {
    public static void main(String[] args) {
        String path;
        int maxUse;

        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите путь к файлу:");
        path = scanner.nextLine();
        System.out.println("Введите число максимального потребления:");
        maxUse = scanner.nextInt();
        scanner.close();

        ArrayList<User> users = UserService.getUsers(path);
        ArrayList<User> ecologicUsers = UserService.getEcologicUsers(users, maxUse);

        String newPath = path.substring(0, path.lastIndexOf("\\")+1) + "newdata.csv";
        UserService.writeUsers(ecologicUsers, newPath);
    }
}