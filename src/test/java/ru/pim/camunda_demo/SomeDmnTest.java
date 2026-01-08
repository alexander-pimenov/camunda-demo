package ru.pim.camunda_demo;

import org.camunda.bpm.dmn.engine.DmnDecision;
import org.camunda.bpm.dmn.engine.DmnDecisionTableResult;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

public class SomeDmnTest extends AbstractDecisionTest {

    @Test
    @DisplayName("diagram_sample_drink - пример, когда возвращается одно значение")
    public void someTest() {

        Map<String, Object> variables = Map.of(
                "gender", "Male"
        );

        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("dmn/diagram_sample_drink.dmn");
        //Decision_0f3gj97 - decision id from dmn file
        DmnDecision decision = dmnEngine.parseDecision("Decision_0f4gj97", inputStream);

        DmnDecisionTableResult ruleResults = dmnEngine.evaluateDecisionTable(decision, variables);

        String singleEntry = ruleResults.getSingleResult().getSingleEntry();
        System.out.println(singleEntry); //Tea


    }


    @Test
    @DisplayName("diagram_order_review_groups - пример, когда возвращается несколько значений")
    public void some2Test() {
        //прочитаем и сохраним dmn файл в переменную decision
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("dmn/diagram_order_review_groups.dmn");
        //Decision_1ds316f - decision id from dmn file
        DmnDecision decision = dmnEngine.parseDecision("Decision_1ds416f", inputStream);

        //создадим для передачи первый вариант параметров
        Map<String, Object> variables = Map.of(
                "orderCategory", "New car",
                "orderValue", 6000
        );

        DmnDecisionTableResult ruleResults = dmnEngine.evaluateDecisionTable(decision, variables);
        List<Map<String, Object>> resultList =ruleResults.getResultList();
        System.out.println(resultList); //[{reviewGroup=Sales}, {reviewGroup=Management}]


        //передадим другие параметры
        Map<String, Object> variables2 = Map.of(
                "orderCategory", "New car",
                "orderValue", 36000
        );

        DmnDecisionTableResult ruleResults2 = dmnEngine.evaluateDecisionTable(decision, variables2);
        List<Map<String, Object>> resultList2 =ruleResults2.getResultList();
        System.out.println(resultList2); //[{reviewGroup=Management}]

        //передадим другие параметры
        Map<String, Object> variables3 = Map.of(
                "orderCategory", "Spare parts",
                "orderValue", 30000
        );

        DmnDecisionTableResult ruleResults3 = dmnEngine.evaluateDecisionTable(decision, variables3);
        List<Map<String, Object>> resultList3 =ruleResults3.getResultList();
        System.out.println(resultList3); //[{reviewGroup=Management}]
    }

    @Test
    @DisplayName("diagram_сreditworthiness - пример, когда возвращается сумма значений")
    public void some3Test() {
        //прочитаем и сохраним dmn файл в переменную decision
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("dmn/diagram_сreditworthiness.dmn");
        //Decision_1ds317f - decision id from dmn file
        DmnDecision decision = dmnEngine.parseDecision("Decision_1ds417f", inputStream);

        //создадим для передачи первый вариант параметров
        Map<String, Object> variables = Map.of(
                "rating", "Good",
                "monthlyIncome", 600
        );

        DmnDecisionTableResult ruleResults = dmnEngine.evaluateDecisionTable(decision, variables);
        List<Map<String, Object>> resultList =ruleResults.getResultList();
        System.out.println(resultList); //+15 - [{creditworthiness=15}]


        //передадим другие параметры
        Map<String, Object> variables2 = Map.of(
                "rating", "Good",
                "monthlyIncome", 36000
        );

        DmnDecisionTableResult ruleResults2 = dmnEngine.evaluateDecisionTable(decision, variables2);
        List<Map<String, Object>> resultList2 =ruleResults2.getResultList();
        System.out.println(resultList2); //+15+5+10 - [{creditworthiness=30}]

        //передадим другие параметры
        Map<String, Object> variables3 = Map.of(
                "rating", "Bad",
                "monthlyIncome", 3000
        );

        DmnDecisionTableResult ruleResults3 = dmnEngine.evaluateDecisionTable(decision, variables3);
        List<Map<String, Object>> resultList3 =ruleResults3.getResultList();
        System.out.println(resultList3); //-15+5+10 - [{creditworthiness=0}]

        //передадим другие параметры
        Map<String, Object> variables4 = Map.of(
                "rating", "Bad",
                "monthlyIncome", 1500
        );

        DmnDecisionTableResult ruleResults4 = dmnEngine.evaluateDecisionTable(decision, variables4);
        List<Map<String, Object>> resultList4 =ruleResults4.getResultList();
        System.out.println(resultList4); //-15+5 - [{creditworthiness=-10}]
    }
}
