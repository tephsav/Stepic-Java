abstract class Employe {
    private int[] profits;

    Employe(int[] profits) {
        this.profits = profits;
    }

    public int getBonus() {
        // avg monthly salary
        int bonus = 0;

        for (int item : getProfits()) {
            bonus += item;
        }

        bonus = bonus / getProfits().length;

        return bonus;
    }

    public int[] getProfits() {
        return profits;
    }

    public void todo() {
        String[] stringArray = new String[getProfits().length];

        for (int i = 0; i < stringArray.length; i++) {
            stringArray[i] = String.valueOf(getProfits()[i]);
        }

        System.out.println("Salary: " + String.join(" ", stringArray));
        System.out.println("Bonus: " + getBonus());
    }
}

class SberEmploye extends Employe{
    private double ratingFactor;
    private static final double SUCCESS_RATE = 1.3;

    SberEmploye(int[] profits, double ratingFactor) {
        super(profits);
        this.ratingFactor = ratingFactor;
    }

    @Override
    public int getBonus() {
        return (int) (super.getBonus() * SUCCESS_RATE * ratingFactor);
    }
}

class EpamEmploye extends Employe {
    EpamEmploye(int[] profits) {
        super(profits);
    }
}

class QIWIEmploye extends Employe {
    private boolean isCompletedCriticalFeature;
    private static final int SALARY_NUMBERS = 8;

    QIWIEmploye(int[] profits, boolean isCompletedCriticalFeature) {
        super(profits);
        this.isCompletedCriticalFeature = isCompletedCriticalFeature;
    }

    @Override
    public int getBonus() {
        if (isCompletedCriticalFeature) {
            return super.getBonus() * SALARY_NUMBERS;
        }

        return 0;
    }
}

public class Bonus {
    public static void main(String[] args) {
        int[] salaries = {85, 42, 40, 76, 35, 68, 110, 63, 40, 54, 75, 91};
        double ratingFactor1 = 1.5;
        double ratingFactor2 = 0.7;
        SberEmploye se1 = new SberEmploye(salaries, ratingFactor1);
        SberEmploye se2 = new SberEmploye(salaries, ratingFactor2);
        EpamEmploye ee = new EpamEmploye(salaries);
        QIWIEmploye qe1 = new QIWIEmploye(salaries, true);
        QIWIEmploye qe2 = new QIWIEmploye(salaries, false);

        Employe[] e = {se1, se2, ee, qe1, qe2};
        int[] testSalaries = {124, 58, 64, 512, 0};

        for (int i = 0; i < e.length; i++) {
            e[i].todo();
            System.out.println("Test bonus: " + testSalaries[i]);
            System.out.println();
        }
    }
}
