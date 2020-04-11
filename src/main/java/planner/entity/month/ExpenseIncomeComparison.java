package planner.entity.month;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
public class ExpenseIncomeComparison {
    private String categoryName;
    private List<Item> comparisons = new ArrayList<>();

    public void addItem(Item item) {
        if (item != null) {
            comparisons.add(item);
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Item {
        private String subcategoryName;
        private Integer plannedValue;
        private Integer actualValue;

        public static Item createItem(String subcatName, Integer planned, Integer actual) {
            Item item = new Item();
            item.setSubcategoryName(subcatName);
            item.setPlannedValue(Objects.requireNonNullElse(planned, 0));
            item.setActualValue(Objects.requireNonNullElse(actual, 0));
            return item;
        }
    }
}
