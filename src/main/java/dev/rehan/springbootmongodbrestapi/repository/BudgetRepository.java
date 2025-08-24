package dev.rehan.springbootmongodbrestapi.repository;

import dev.rehan.springbootmongodbrestapi.model.Budget;
import dev.rehan.springbootmongodbrestapi.model.ExpenseCategory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BudgetRepository extends MongoRepository<Budget, String> {

    // Find budget for specific category and time period
    @Query("{'expenseCategory': ?0, 'month': ?1, 'year': ?2}")
    Optional<Budget> findByExpenseCategoryAndMonthAndYear(ExpenseCategory category, int month, int year);

    // Find all budgets for a specific month/year
    @Query("{'month': ?0, 'year': ?1}")
    List<Budget> findByMonthAndYear(int month, int year);

    // Find all budgets for a category
    List<Budget> findByExpenseCategory(ExpenseCategory category);

    // Check if budget exists for category and period
    boolean existsByExpenseCategoryAndMonthAndYear(ExpenseCategory category, int month, int year);
}