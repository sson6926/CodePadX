package com.shawnix.codepadx.specification;

import com.shawnix.codepadx.entity.Code;
import com.shawnix.codepadx.entity.enums.Visibility;
import org.springframework.data.jpa.domain.Specification;

public final class CodeSpecification {
    private CodeSpecification() {}

    public static Specification<Code> titleOrSourceContains(String keyword) {
        return (root, query, cb) -> {
            if (keyword == null || keyword.isBlank()) {
                return cb.conjunction();
            }
            String pattern = "%" + keyword.trim().toLowerCase() + "%";
            var title = cb.like(cb.lower(root.get("title")), pattern);
            var source = cb.like(cb.lower(root.get("sourceCode")), pattern);
            return cb.or(title, source);
        };
    }

    public static Specification<Code> hasLanguageId(Integer languageId) {
        return (root, query, cb) ->
                languageId == null ? cb.conjunction() : cb.equal(root.get("language").get("id"), languageId);
    }

    public static Specification<Code> hasVisibility(Visibility visibility) {
        return (root, query, cb) ->
                visibility == null ? cb.conjunction() : cb.equal(root.get("visibility"), visibility);
    }

    public static Specification<Code> hasUserId(Long userId) {
        return (root, query, cb) ->
                userId == null ? cb.conjunction() : cb.equal(root.get("user").get("id"), userId);
    }

    public static Specification<Code> build(
            String keyword,
            Integer languageId,
            Visibility visibility,
            Long userId) {
        return Specification.where(titleOrSourceContains(keyword))
                .and(hasLanguageId(languageId))
                .and(hasVisibility(visibility))
                .and(hasUserId(userId));
    }
}