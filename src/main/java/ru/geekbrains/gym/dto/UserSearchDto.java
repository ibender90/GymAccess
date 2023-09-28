package ru.geekbrains.gym.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import ru.geekbrains.gym.model.User;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserSearchDto {

    public static final UserSearchDto FIND_ALL = new UserSearchDto();

    private String firstName;

    private String lastName;

    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Integer limit= 50; //todo move to constants

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Integer page=0;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String sort="id";

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Sort.Direction dir=Sort.DEFAULT_DIRECTION;

    @JsonIgnore
    public Specification<User> getSpecification() {
        return (root, query, criteriaBuilder) -> {
            query.distinct(true);
            Predicate noFiltersApplied = criteriaBuilder.conjunction();
            List<Predicate> filters = new ArrayList<>();
            filters.add(noFiltersApplied);
            addFilters(root, query, criteriaBuilder,filters);
            return criteriaBuilder.and(filters.toArray(new Predicate[filters.size()]));
        };
    }

    @JsonIgnore
    public Pageable getPageable() {
        return PageRequest.of(
                (page != null) ? page : 0,
                (limit != null && limit >= 0) ? limit : 50, //todo remove magic numbers
                getSortSpec()
        );
    }

    @JsonIgnore
    public Sort getSortSpec() {
        if (sort == null) return Sort.unsorted();
        return (dir != null && dir == Sort.Direction.DESC) ?
                Sort.by(sort).descending() : Sort.by(sort).ascending();
    }

    protected void addFilters(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder, List<Predicate> filters) {

        if (StringUtils.isNotEmpty(lastName)){
            Predicate lastNameAsPredicate = criteriaBuilder.equal(root.get("lastName"), lastName);
            filters.add(lastNameAsPredicate);
        }

        if (StringUtils.isNotEmpty(firstName)){
            Predicate lastNameAsPredicate = criteriaBuilder.equal(root.get("lastName"), lastName);
            filters.add(lastNameAsPredicate);
        }
        if (StringUtils.isNotEmpty(email)){
            Predicate lastNameAsPredicate = criteriaBuilder.equal(root.get("lastName"), lastName);
            filters.add(lastNameAsPredicate);
        }
    }
}
