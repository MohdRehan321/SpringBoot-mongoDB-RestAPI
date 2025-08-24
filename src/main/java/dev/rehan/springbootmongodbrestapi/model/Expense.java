package dev.rehan.springbootmongodbrestapi.model;

import com.mongodb.lang.NonNull;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "expenses")
public class Expense {
    @Id
    private String id;

    @Field("name")
    @Indexed(unique = true)
    @NonNull
    private String expenseName;

    @Field("category")
    private ExpenseCategory expenseCategory;

    @Field("amount")
    private BigDecimal expenseAmount;

    @Field("expenseDate")
    private LocalDateTime expenseDate = LocalDateTime.now();
}