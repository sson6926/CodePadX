package com.shawnix.codepadx.specification;

import com.shawnix.codepadx.entity.Course;
import com.shawnix.codepadx.entity.enums.CourseStatus;
import jakarta.persistence.criteria.Path;
import org.springframework.data.jpa.domain.Specification;

public final class CourseSpecification {
    private CourseSpecification() {}
    public static Specification<Course> hasStatus(CourseStatus status) {
        return (root, query, cb) ->
                status == null ? cb.conjunction() : cb.equal(root.get("status"), status);
    }
    public static Specification<Course> titleOrDescriptionContains(String keyword) {
        return (root, query, cb) -> {
            if (keyword == null || keyword.isBlank()) {
                return cb.conjunction();
            }
            String pattern = "%" + keyword.trim().toLowerCase() + "%";
            var title = cb.like(cb.lower(root.get("title")), pattern);
            var desc = cb.like(cb.lower(root.get("description")), pattern);
            return cb.or(title, desc);
        };
    }
    public static Specification<Course> priceBetween(Double min, Double max) {
        return (root, query, cb) -> {
            Path<Double> p = root.get("price");
            if (min != null && max != null) {
                return cb.between(p, min, max);
            }
            if (min != null) {
                return cb.greaterThanOrEqualTo(p, min);
            }
            if (max != null) {
                return cb.lessThanOrEqualTo(p, max);
            }
            return cb.conjunction();
        };
    }
    public static Specification<Course> build(
            String keyword,
            CourseStatus status,
            Double minPrice,
            Double maxPrice) {
        CourseStatus effective = status != null ? status : CourseStatus.PUBLISHED;
        return Specification.where(hasStatus(effective))
                .and(titleOrDescriptionContains(keyword))
                .and(priceBetween(minPrice, maxPrice));
    }
}
