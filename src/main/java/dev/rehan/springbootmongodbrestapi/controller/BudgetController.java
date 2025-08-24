package dev.rehan.springbootmongodbrestapi.controller;

import dev.rehan.springbootmongodbrestapi.model.Budget;
import dev.rehan.springbootmongodbrestapi.model.ExpenseCategory;
import dev.rehan.springbootmongodbrestapi.service.BudgetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/budget")
@RequiredArgsConstructor
public class BudgetController {

    private final BudgetService budgetService;

    // Create new budget
    @PostMapping("/")
    public ResponseEntity<Budget> createBudget(@RequestBody Budget budget) {
        Budget createdBudget = budgetService.createBudget(budget);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // Update budget
    @PutMapping
    public ResponseEntity<Budget> updateBudget(@RequestBody Budget budget) {
        Budget updatedBudget = budgetService.updateBudget(budget);
        return ResponseEntity.ok(updatedBudget);
    }

    // Get all budgets
    @GetMapping
    public ResponseEntity<List<Budget>> getAllBudgets() {
        List<Budget> budgets = budgetService.getAllBudgets();
        return ResponseEntity.ok(budgets);
    }

    // Get budgets by month and year
    @GetMapping("/period")
    public ResponseEntity<List<Budget>> getBudgetsByPeriod(
            @RequestParam int month,
            @RequestParam int year) {
        List<Budget> budgets = budgetService.getBudgetsByMonthAndYear(month, year);
        return ResponseEntity.ok(budgets);
    }

    // Get budget by category
    @GetMapping("/category/{category}")
    public ResponseEntity<Budget> getBudgetByCategory(
            @PathVariable ExpenseCategory category,
            @RequestParam int month,
            @RequestParam int year) {
        Budget budget = budgetService.getBudgetByCategory(category, month, year);
        return ResponseEntity.ok(budget);
    }

    // Get budget status (with spending info)
    @GetMapping("/status/{budgetId}")
    public ResponseEntity<BudgetService.BudgetStatus> getBudgetStatus(@PathVariable String budgetId) {
        BudgetService.BudgetStatus status = budgetService.getBudgetStatus(budgetId);
        return ResponseEntity.ok(status);
    }

    // Delete budget
    @DeleteMapping("/id/{id}")
    public ResponseEntity<Void> deleteBudget(@PathVariable String id) {
        budgetService.deleteBudget(id);
        return ResponseEntity.noContent().build();
    }
}