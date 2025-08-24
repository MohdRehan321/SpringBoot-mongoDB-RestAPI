package dev.rehan.springbootmongodbrestapi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.index.Indexed;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "budget")
public class Budget {
    @Id
    private String id;

    @Field("category")
    @Indexed
    private ExpenseCategory expenseCategory;

    @Field("budgetLimit")
    private BigDecimal budgetLimit;

    @Field("month")
    @Indexed
    private int month; // 1-12

    @Field("year")
    @Indexed
    private int year;

    @Field("createdDate")
    private LocalDate createdDate;

    // Helper method to get period as string
    public String getPeriod() {
        return year + "-" + String.format("%02d", month);
    }
}