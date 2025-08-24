package dev.rehan.springbootmongodbrestapi.service;

import dev.rehan.springbootmongodbrestapi.model.Budget;
import dev.rehan.springbootmongodbrestapi.model.Expense;
import dev.rehan.springbootmongodbrestapi.model.ExpenseCategory;
import dev.rehan.springbootmongodbrestapi.repository.BudgetRepository;
import dev.rehan.springbootmongodbrestapi.repository.ExpenseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BudgetService {

    private final BudgetRepository budgetRepository;
    private final ExpenseRepository expenseRepository;

    // Create new budget
    public Budget createBudget(Budget budget) {
        if (budget.getMonth() < 1 || budget.getMonth() > 12) {
            throw new IllegalArgumentException("Month must be between 1 and 12");
        }
        // Check if budget already exists for this category and period
        if (budgetRepository.existsByExpenseCategoryAndMonthAndYear(
                budget.getExpenseCategory(), budget.getMonth(), budget.getYear())) {
            throw new RuntimeException("Budget already exists for " +
                    budget.getExpenseCategory() + " in " + budget.getMonth() + "/" + budget.getYear());
        }

        budget.setCreatedDate(LocalDate.now());
        return budgetRepository.save(budget);
    }

    // Update existing budget
    public Budget updateBudget(Budget budget) {
        if (budget.getMonth() < 1 || budget.getMonth() > 12) {
            throw new IllegalArgumentException("Month must be between 1 and 12");
        }
        Budget existingBudget = budgetRepository.findById(budget.getId())
                .orElseThrow(() -> new RuntimeException("Budget not found with ID: " + budget.getId()));

        // Update all relevant fields
        existingBudget.setExpenseCategory(budget.getExpenseCategory());
        existingBudget.setBudgetLimit(budget.getBudgetLimit());
        existingBudget.setMonth(budget.getMonth());
        existingBudget.setYear(budget.getYear());

        // Don't update createdDate as it should remain unchanged

        return budgetRepository.save(existingBudget);
    }

    // Get all budgets
    public List<Budget> getAllBudgets() {
        return budgetRepository.findAll();
    }

    // Get budgets by month and year
    public List<Budget> getBudgetsByMonthAndYear(int month, int year) {
        return budgetRepository.findByMonthAndYear(month, year);
    }

    // Get budget by category, month, and year
    public Budget getBudgetByCategory(ExpenseCategory category, int month, int year) {
        return budgetRepository.findByExpenseCategoryAndMonthAndYear(category, month, year)
                .orElseThrow(() -> new RuntimeException("No budget found for " + category +
                        " in " + month + "/" + year));
    }

    // Delete budget
    public void deleteBudget(String id) {
        if (!budgetRepository.existsById(id)) {
            throw new RuntimeException("Budget not found with ID: " + id);
        }
        budgetRepository.deleteById(id);
    }

    // Get budget status with spending information
    public BudgetStatus getBudgetStatus(String budgetId) {
        Budget budget = budgetRepository.findById(budgetId)
                .orElseThrow(() -> new RuntimeException("Budget not found with ID: " + budgetId));

        BigDecimal totalSpent = calculateTotalSpentForCategory(budget.getExpenseCategory());
        BigDecimal remaining = budget.getBudgetLimit().subtract(totalSpent);

        return new BudgetStatus(budget, totalSpent, remaining);
    }

    // Helper method to calculate total spent for a category
    private BigDecimal calculateTotalSpentForCategory(ExpenseCategory category) {
        List<Expense> categoryExpenses = expenseRepository.findAll().stream()
                .filter(expense -> expense.getExpenseCategory().equals(category))
                .collect(Collectors.toList());

        return categoryExpenses.stream()
                .map(Expense::getExpenseAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    // Inner class for budget status (simple response)
    public static class BudgetStatus {
        private Budget budget;
        private BigDecimal totalSpent;
        private BigDecimal remaining;

        public BudgetStatus(Budget budget, BigDecimal totalSpent, BigDecimal remaining) {
            this.budget = budget;
            this.totalSpent = totalSpent;
            this.remaining = remaining;
        }

        public Budget getBudget() { return budget; }
        public BigDecimal getTotalSpent() { return totalSpent; }
        public BigDecimal getRemaining() { return remaining; }

        public boolean isOverBudget() {
            return totalSpent.compareTo(budget.getBudgetLimit()) > 0;
        }

        public double getPercentageUsed() {
            if (budget.getBudgetLimit().compareTo(BigDecimal.ZERO) == 0) {
                return 0;
            }
            return totalSpent.divide(budget.getBudgetLimit(), 4, java.math.RoundingMode.HALF_UP)
                    .multiply(BigDecimal.valueOf(100)).doubleValue();
        }
    }
}