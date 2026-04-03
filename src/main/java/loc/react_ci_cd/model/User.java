package loc.react_ci_cd.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("users")
public record User(@Id String id, String name, int age, boolean active) {
}
