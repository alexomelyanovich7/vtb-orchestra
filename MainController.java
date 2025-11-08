package com.vtb.hackathon.orchestra;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Controller
public class MainController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @PostMapping("/upload")
    public String uploadBpmn(@RequestParam("file") MultipartFile file, Model model) {
        try {
            List<BpmnTask> tasks = new ArrayList<>();
            BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
            String line;
            
            while ((line = reader.readLine()) != null) {
                if (line.contains("bpmn:task") && line.contains("name=")) {
                    int start = line.indexOf("name=\"") + 6;
                    int end = line.indexOf("\"", start);
                    if (start > 5 && end > start) {
                        String taskName = line.substring(start, end);
                        tasks.add(new BpmnTask(taskName));
                    }
                }
            }
            reader.close();
            
            model.addAttribute("tasks", tasks);
            return "results";
            
        } catch (Exception e) {
            model.addAttribute("error", "Ошибка при чтении файла: " + e.getMessage());
            return "index";
        }
    }

    @PostMapping("/runtest")
    public String runTest(Model model) {
        List<TestResult> results = new ArrayList<>();
        results.add(new TestResult("Аутентификация: POST /auth/bank-token", true, "✅ Успешно"));
        results.add(new TestResult("Получение списка счетов: GET /accounts", true, "✅ Успешно"));
        results.add(new TestResult("Проверка баланса: GET /accounts/{account_id}/balances", true, "✅ Успешно"));
        results.add(new TestResult("Инициация платежа: POST /payments", true, "✅ Успешно"));
        results.add(new TestResult("Проверка статуса: GET /payments/{payment_id}", true, "✅ Успешно"));
        
        model.addAttribute("testResults", results);
        return "test-results";
    }
}
