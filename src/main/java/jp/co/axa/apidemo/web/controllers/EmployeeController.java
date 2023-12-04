package jp.co.axa.apidemo.web.controllers;

import jp.co.axa.apidemo.application.CommonValue;
import jp.co.axa.apidemo.application.dto.EmployeeDto;
import jp.co.axa.apidemo.application.services.EmployeeService;
import jp.co.axa.apidemo.domain.entities.Employee;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static jp.co.axa.apidemo.application.CommonValue.NUMBER_OF_COLUMNS;

@Controller
@Slf4j
@RequestMapping("/api/v1")
public class EmployeeController {
    private final EmployeeService employeeService;

    Map<String, List<Employee>> cache = new HashMap<>();

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    String cacheKey(String name, int page) {
        return name + CommonValue.CACHE_KEY + page;
    }

    // reset cache if database changes(create, update, delete)
    void resetCache() {
        cache = new HashMap<>();
        log.info("resetCache");
    }

    void initPage(ModelMap map, int pageNum) {
        Example<Employee> of = employeeService.generateQueryParam("");

        long total = employeeService.getTotal(of);
        // Every page will only show 5 item
        // todo: add pull down on frontend to change the amount showing on one page
        int totalPage = ((int) total + NUMBER_OF_COLUMNS - 1) / NUMBER_OF_COLUMNS;
        map.addAttribute("total", employeeService.getTotal(of) / NUMBER_OF_COLUMNS);
        map.addAttribute("totalPage", totalPage);
        if (totalPage == 1) {
            map.addAttribute("pre", 1);
            map.addAttribute("next", 1);
        } else {
            if (pageNum != 1) {
                map.addAttribute("pre", pageNum - 1);
            } else {
                map.addAttribute("pre", 1);
            }
            if (pageNum != totalPage) {
                map.addAttribute("next", totalPage);
            } else {
                map.addAttribute("next", pageNum + 1);
            }
        }
    }

    @GetMapping("/index")
    public String indexJumpPage(
            ModelMap map,
            @RequestParam(value = "name", defaultValue = "") String name,
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        if (pageNum == null || pageNum == 0) {
            pageNum = 1;
        }
        map.addAttribute("pageNum", pageNum);
        initPage(map, pageNum);
        map.addAttribute("beans", getEmployees(name, pageNum));
        return "index";
    }

    private List<Employee> getEmployees(
            @RequestParam(value = "name") String name,
            @RequestParam(value = "page") int page) {
        String k = cacheKey(name, page);
        if (cache.containsKey(k)) {
            log.info("start cache query");
            return cache.get(k);
        }

        Example<Employee> of = employeeService.generateQueryParam(name);

        List<Employee> employees = employeeService.retrieveEmployees(of, page).getContent();
        cache.put(k, employees);
        return employees;
    }


    @GetMapping("/add")
    public String add(ModelMap map) {
        resetCache();
        return "add";
    }


    @GetMapping("/getEmployee")
    public String getEmployee(ModelMap map, @RequestParam(value = "employeeId") Long employeeId) {
        map.put("employee", employeeService.getEmployee(employeeId));
        return "edit";
    }

    @PostMapping("/saveEmployee")
    public String saveEmployee(ModelMap map, Employee employee) {
        employeeService.saveEmployee(employee);
        log.info("Employee Saved Successfully");
        resetCache();
        return "redirect:/api/v1/index";

    }

    @GetMapping("/deleteEmployee")
    public String deleteEmployee(ModelMap map, @RequestParam(value = "employeeId") Long employeeId) {
        employeeService.deleteEmployee(employeeId);
        log.info("Employee Deleted Successfully");
        map.addAttribute("pageNum", 1);
        resetCache();
        return "redirect:/api/v1/index";
    }

    @RequestMapping("/updateEmployee")
    public String updateEmployee(ModelMap map, Employee employee) {
        EmployeeDto emp = employeeService.getEmployee(employee.getId());
        if (emp != null) {
            employeeService.updateEmployee(employee);
        }
        resetCache();
        return "redirect:/api/v1/index";
    }
}
