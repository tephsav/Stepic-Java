import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Report {
    private final Long id;
    private final String studentUserName;
    private final Integer hours;
    private final LocalDate date;
    private final String title;

    public Report(Long id, String studentUserName, Integer hours, LocalDate date, String title) {
        this.id = id;
        this.studentUserName = studentUserName;
        this.hours = hours;
        this.date = date;
        this.title = title;
    }

    public String getStudentUserName() {
        return studentUserName;
    }

    public Integer getHours() {
        return hours;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getTitle() {
        return title;
    }

    public static String reportHistory(List<Report> reports, String studentUserName, int count) {
        String separator = "-----------------";
        StringBuilder sb = new StringBuilder();

        reports.stream()
                .filter(report -> report.getStudentUserName().equals(studentUserName))
                .sorted(Comparator.comparing(Report::getDate).reversed())
                .limit(count)
                .sorted(Comparator.comparing(Report::getDate))
                .forEach(report -> sb.append(String.format("%s\n%s\n%d\n%s\n%s\n",
                        report.getStudentUserName(),
                        report.getDate(),
                        report.getHours(),
                        report.getTitle(),
                        separator)));

        try {
            sb.delete(sb.length() - separator.length() - 1, sb.length());
        } catch (StringIndexOutOfBoundsException sob) {
            System.out.printf("StringIndexOutOfBoundsException: %s", sob.getMessage());
        }

        return sb.toString();
    }

    public static void main(String[] args) {
        Report report1 = new Report(1L, "no_punkz", 1, LocalDate.of(2021, 9, 25), "Из-за внешних факторов все никак не могу сделать " +
                "задачу" +
                " со стримами, не хватило времени");
        Report report2 = new Report(2L, "no_punkz", 5, LocalDate.of(2021, 9, 27), "Продолжаю мучать предпоследнюю задачу (теперь и " +
                "Толяна), собес на котором узнал много нового");
        Report report3 = new Report(3L, "no_punkz", 2, LocalDate.of(2021, 9, 26), "Бился над компаратором, пытался накостылить через " +
                "видимую внешнюю переменную, начал читать о решениях из чата");
        Report report4 = new Report(4L, "no_punkz", 6, LocalDate.of(2021, 9, 23), "тестовый");
        Report report5 = new Report(5L, "no_punkz", 3, LocalDate.of(2021, 9, 24), "тестовый2");
        Report report6 = new Report(6L, "no_punkz", 4, LocalDate.of(2021, 9, 19), "тестовый2");
        Report report7 = new Report(7L, "alekshas", 2, LocalDate.of(2021, 9, 25), "тестовый леха");
        Report report8 = new Report(8L, "alekshas", 1, LocalDate.of(2021, 9, 24), "тестовый лех2");
        List<Report> list = new ArrayList<>();
        list.add(report1);
        list.add(report2);
        list.add(report3);
        list.add(report4);
        list.add(report5);
        list.add(report6);
        list.add(report7);
        list.add(report8);
        System.out.println(reportHistory(list, "no_punkz", 3));
    }
}
