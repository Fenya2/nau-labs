package naumen.course;

import java.text.MessageFormat;
import java.util.List;

/**
 * 1. Необходимо реализовать java-класс сотрудник (код “Employee”).
 * Приватные поля класса: ФИО (“fullName” тип “String”), Возраст (“age”
 * тип “Integer”), Отдел (“department” тип “String”), З/П (“salary” тип
 * “Double”). Класс должен содержать геттеры и сеттеры для доступа к
 * полям.
 * 2. Необходимо реализовать предзаполненный список (тип
 * “ArrayList<Employee>”) с объектами класса “Employee”, по которым будем
 * выполняться задание. Необходимо создать не менее 5 элементов списка.
 * 3. Преобразовать список сотрудников в список строк вида "Имя -
 * Отдел".
 */
public class Task3V4 {
    /**
     * Форматер для решения задачи
     */
    private static final MessageFormat formatter = new MessageFormat("{0} - {1}");

    /**
     * Сотрудник
     */
    private static class Employee {
        /**
         * ФИО
         */
        private String fullName;

        /**
         * Возраст
         */
        private Integer age;

        /**
         * Отдел
         */
        private String department;

        /**
         * Зарплата
         */
        private Double salary;

        public Employee(String fullName, Integer age, String department, Double salary) {
            this.fullName = fullName;
            this.age = age;
            this.department = department;
            this.salary = salary;
        }

        /**
         * Возвращает имя сотрудника
         */
        public String getName() {
            return fullName.split(" ")[1];
        }

        public String getFullName() {
            return fullName;
        }

        public void setFullName(String fullName) {
            this.fullName = fullName;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }

        public String getDepartment() {
            return department;
        }

        public void setDepartment(String department) {
            this.department = department;
        }

        public Double getSalary() {
            return salary;
        }

        public void setSalary(Double salary) {
            this.salary = salary;
        }
    }

    /**
     * Решает задачу
     */
    public List<String> solve() {
        List<Employee> employees = List.of(
                new Employee("Иванов Александр Иванович", 10, "dev", 100.0),
                new Employee("Петрова Анна Александровна", 15, "qa", 200.0),
                new Employee("Сидорова Татьяна Николаевна", 20, "hr", 300.0),
                new Employee("Кузнецов Михаил Дмитриевич", 25, "dev", 400.0),
                new Employee("Смирнов Николай Денисович", 30, "support", 500.0));

        return mapEmployeesToNameDepartmentPairs(employees);
    }

    /**
     * Возвращает список строк в формате "Имя - Отдел" на основе переданного списка сотрудников
     */
    private List<String> mapEmployeesToNameDepartmentPairs(List<Employee> employees) {
        return employees.stream()
                .map(employee -> formatter.format(new Object[]{employee.getName(), employee.getDepartment()}))
                .toList();
    }

    public static void main(String[] args) {
        new Task3V4().solve().forEach(System.out::println);
    }
}
