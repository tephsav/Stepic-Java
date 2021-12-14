import java.io.*;

class Human implements Externalizable {
    private String name;
    private int[] salary;

    public Human() {
        this(null, null);
    }

    public Human(String name, int[] salary) {
        this.name = name;
        this.salary = salary;
    }

    public String getPyramidSalaryString() {
        StringBuilder sb = new StringBuilder();
        int indexSalary = 0;

        for (int indexRow = 0; indexSalary < salary.length; indexRow++) {
            for (int indexColumn = 0; indexColumn <= indexRow && indexSalary < salary.length; indexColumn++) {
                if (indexColumn == indexRow || indexSalary == salary.length - 1) {
                    sb.append(salary[indexSalary]);
                } else {
                    sb.append(salary[indexSalary]).append(" ");
                }
                indexSalary++;
            }
            sb.append("\n");
        }

        return sb.toString();
    }

    public int[] getSalaryList(String salaryString) {
        String[] salasyStringList = salaryString.split("\\s");
        int[] salaryList = new int[salasyStringList.length];

        for (int index = 0; index < salasyStringList.length; index++) {
            salaryList[index] = Integer.parseInt(salasyStringList[index]);
        }

        return salaryList;
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeUTF(this.name);
        out.writeUTF(this.getPyramidSalaryString());
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException {
        this.name = in.readUTF();
        this.salary = getSalaryList(in.readUTF());
    }

    @Override
    public String toString() {
        return String.format("%s\n%s", this.name, this.getPyramidSalaryString());
    }
}

public class ExternalizableHuman {
    public static void main(String[] args) {
        String fileName = "testFile.ser";
        String testName = "Саша";
        int[] testSalary = new int[]{150000, 1900000, 200000, 300000, 500000, 430000, 250000};
        Human testHuman = new Human(testName, testSalary);
        Human clonedHuman;
        System.out.printf("%s\n", testHuman);

        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(fileName))) {
            objectOutputStream.writeObject(testHuman);
        } catch (IOException ioException) {
            System.out.println(ioException.getMessage());
        }

        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(fileName))) {
            clonedHuman = (Human) objectInputStream.readObject();
            System.out.println(clonedHuman);
        } catch (IOException | ClassNotFoundException exception) {
            System.out.println(exception.getMessage());
        }
    }
}