package task3;


import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class Task3 {

    static void main(String[] args) {
        if (args.length < 3) {
            System.err.println("Ошибка: необходимо передать три аргумента - пути к файлам с значениями, тестовыми данными и файлом-отчетом.");
            return;
        }

        String valuesFile = "task3/" + args[0];
        String testsFile = "task3/" + args[1];
        String reportFile = "task3/" + args[2];

        ObjectMapper mapper = new ObjectMapper();

        try {
            // Чтение values.json
            ValuesRoot valuesRoot = mapper.readValue(new File(valuesFile), ValuesRoot.class);
            Map<Integer, String> valueMap = valuesRoot.getValues().stream()
                    .collect(Collectors.toMap(ValueItem::getId, ValueItem::getValue));

            // Чтение tests.json
            TestRoot testRoot = mapper.readValue(new File(testsFile), TestRoot.class);

            // Заполняем поля value
            fillValues(testRoot.getTests(), valueMap);

            // Запись результатов в report.json
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(reportFile), testRoot);
            System.out.println("Отчёт успешно создан: " + reportFile);
        } catch (IOException e) {
            System.err.println("Ошибка при обработке файлов: " + e.getMessage());
        }
    }

    private static void fillValues(List<TestItem> tests, Map<Integer, String> valueMap) {
        if (tests.isEmpty()) return;

        for (TestItem test : tests) {
            if (valueMap.containsKey(test.getId())) {
                test.setValue(valueMap.get(test.getId()));
            }

            // Рекурсивная обработка вложенных тестов
            if (test.getValues() != null && !test.getValues().isEmpty()) {
                fillValues(test.getValues(), valueMap);
            }
        }
    }

    // ---- Классы для десериализации JSON ----
    static class ValuesRoot {
        private List<ValueItem> values;

        public List<ValueItem> getValues() {
            return values;
        }

        public void setValues(List<ValueItem> values) {
            this.values = values;
        }
    }

    static class ValueItem {
        private int id;
        private String value;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    static class TestRoot {
        private List<TestItem> tests;

        public List<TestItem> getTests() {
            return tests;
        }

        public void setTests(List<TestItem> tests) {
            this.tests = tests;
        }
    }

    static class TestItem {
        private int id;
        private String title;
        private String value;

        private List<TestItem> values;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public List<TestItem> getValues() {
            return values;
        }

        public void setValues(List<TestItem> values) {
            this.values = values;
        }
    }
}
