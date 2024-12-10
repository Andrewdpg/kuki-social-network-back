package com.kuki.social_networking.util;

import com.kuki.social_networking.request.PageableRequest;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class EntitySpecs {

    public static final int AND = 0;
    public static final int OR = 1;

    /**
     * Composes a query from a list of specifications.
     * @param type the type of the composition (AND or OR)
     * @param specs the list of specifications
     * @param <Y> the type of the entity
     * @return the composed query
     */
    @SafeVarargs
    public static <Y> Specification<Y> composedQuery(int type, Specification<Y>... specs) {
        if(specs == null) return (root, query, cb) -> cb.conjunction();
        return (root, query, cb) -> {
            Specification<Y> result = Specification.where(specs[0]);
            for (int i = 1; i < specs.length; i++) {
                result = specs[i] == null ? result : type == AND? result.and(specs[i]) : result.or(specs[i]);
            }
            return result.toPredicate(root, query, cb);
        };
    }

    /**
     * Creates a specification that checks if an attribute is equal to a given element.
     * @param attribute the attribute to check
     * @return a specification that checks if the attribute is equal to the element
     * @param <T> the type of the entity
     */
    public static <T> Specification<T> notNull(String attribute) {
        return (root, query, cb) -> cb.isNotNull(root.get(attribute));
    }

    /**
     * Creates a specification that checks if an attribute is equal to a given element.
     * @param attribute the attribute to check
     * @return a specification that checks if the attribute is equal to the element
     * @param <T> the type of the entity
     */
    public static <T> Specification<T> isNull(String attribute) {
        return (root, query, cb) -> cb.isNull(root.get(attribute));
    }

    /**
     * Creates a specification that checks if an attribute is equal to a given element.
     * The element must not be null. if it is, the specification will return a conjunction (true).
     *
     * @param attribute the attribute to check
     * @param element   the element to compare the attribute to
     * @param <T>       the type of the element
     * @param <Y>       the type of the entity
     * @return a specification that checks if the attribute is equal to the element
     */
    public static <T, Y> Specification<Y> joinAttribute(String joinAttribute, String attribute, T element) {
        if (element == null) {
            return (root, query, cb) -> cb.conjunction();
        }
        return (root, query, cb) -> {
            Join<Y, T> join = root.join(joinAttribute, JoinType.INNER);
            return cb.equal(join.get(attribute), element);
        };
    }

    /**
     * Creates a specification that checks if an attribute is equal to a given element.
     * The element must not be null. if it is, the specification will return a conjunction (true).
     *
     * @param attribute the attribute to check
     * @param element   the element to compare the attribute to
     * @param <T>       the type of the element
     * @param <Y>       the type of the entity
     * @return a specification that checks if the attribute is equal to the element
     */
    public static <T, Y> Specification<Y> joinAttributeIgnoreCase(String joinAttribute, String attribute, T element) {
        if (element == null) {
            return (root, query, cb) -> cb.conjunction();
        }
        return (root, query, cb) -> {
            Join<Y, T> join = root.join(joinAttribute, JoinType.INNER);
            return cb.equal(cb.lower(join.get(attribute)), element.toString().toLowerCase());
        };
    }

    /**
     * Creates a specification that checks if an attribute is equal to a given element.
     * The element must not be null. if it is, the specification will return a conjunction (true).
     *
     * @param attribute the attribute to check
     * @param element   the element to compare the attribute to
     * @param <T>       the type of the element
     * @param <Y>       the type of the entity
     * @return a specification that checks if the attribute is equal to the element
     */
    public static <T, Y> Specification<Y> hasAttribute(String attribute, T element) {
        if (element == null) {
            return (root, query, cb) -> cb.conjunction();
        }
        return (root, query, cb) -> cb.equal(root.get(attribute), element);
    }

    /**
     * Creates a specification that checks if an attribute is equal to a given element.
     * The element must not be null. if it is, the specification will return a conjunction (true).
     *
     * @param attribute the attribute to check
     * @param element   the element to compare the attribute to
     * @param <T>       the type of the element
     * @param <Y>       the type of the entity
     * @return a specification that checks if the attribute is equal to the element
     */
    public static <T, Y> Specification<Y> hasAttributeIgnoreCase(String attribute, T element) {
        if (element == null) {
            return (root, query, cb) -> cb.conjunction();
        }
        return (root, query, cb) -> cb.equal(cb.lower(root.get(attribute)), element.toString().toLowerCase());
    }

    /**
     * Creates a specification that checks if an attribute is equal to a given element.
     * The element must not be null. if it is, the specification will return a conjunction (true).
     *
     * @param attribute the attribute to check
     * @param element   the element to compare the attribute to
     * @param <T>       the type of the element
     * @param <Y>       the type of the entity
     * @return a specification that checks if the attribute is equal to the element
     */
    public static <T, Y> Specification<Y> hasAttributeLike(String attribute, T element) {
        if (element == null) {
            return (root, query, cb) -> cb.conjunction();
        }
        return (root, query, cb) -> cb.like(root.get(attribute), "%" + element + "%");
    }

    /**
     * Creates a specification that checks if an attribute is equal to a given element.
     * The element must not be null. if it is, the specification will return a conjunction (true).
     *
     * @param attribute the attribute to check
     * @param element   the element to compare the attribute to
     * @param <T>       the type of the element
     * @param <Y>       the type of the entity
     * @return a specification that checks if the attribute is equal to the element
     */
    public static <T, Y> Specification<Y> hasAttributeLikeIgnoreCase(String attribute, T element) {
        if (element == null) {
            return (root, query, cb) -> cb.conjunction();
        }
        return (root, query, cb) -> cb.like(cb.lower(root.get(attribute)), "%" + element.toString().toLowerCase() + "%");
    }

    /**
     * Creates a specification that checks if an entity has any of the elements in a list (simple join).
     * The list must not be empty. if it is, the specification will return a conjunction (true).
     * The attribute must be a simple attribute (not a nested attribute).
     *
     * @param joinAttribute the attribute to join with
     * @param attribute the attribute to check
     * @param list      the list of elements to compare the attribute to
     * @param <T>       the type of the elements
     * @param <Y>       the type of the entity
     * @return a specification that checks if the attribute is equal to any of the elements in the list
     */
    public static <T, Y> Specification<Y> hasAnyElement(String joinAttribute, String attribute, List<T> list) {
        if (list == null || list.isEmpty()) {
            return (root, query, cb) -> cb.conjunction();
        }
        return (root, query, cb) -> root.join(joinAttribute, JoinType.INNER).get(attribute).in(list);
    }

    /**
     * Creates a specification that checks if an entity has any of the elements in a list (nested join).
     * The list must not be empty. if it is, the specification will return a conjunction (true).
     * The attribute must be a nested attribute.
     * Useful when the @ManyToMany relationship is not directly connected to the entity but through another entity (via @OneToMany).
     *
     * @param joinAttribute the attribute to join
     * @param middleAttribute the attribute to join in the middle
     * @param attribute the attribute to check
     * @param list      the list of elements to compare the attribute to
     * @param <T>       the type of the elements
     * @param <Y>       the type of the entity
     * @return a specification that checks if the attribute is equal to any of the elements in the list
     */
    public static <T, Y> Specification<Y> hasAnyElement(String joinAttribute, String middleAttribute, String attribute, List<T> list) {
        if (list == null || list.isEmpty()) {
            return (root, query, cb) -> cb.conjunction();
        }
        return (root, query, cb) -> root.join(joinAttribute, JoinType.INNER).join(middleAttribute).get(attribute).in(list);
    }

    /**
     * Creates a specification that checks if an entity has all the elements in a list (simple join).
     * The list must not be empty. if it is, the specification will return a conjunction (true).
     * The attribute must be a simple attribute (not a nested attribute).
     *
     * @param attribute the attribute to check
     * @param list      the list of elements to compare the attribute to
     * @param <T>       the type of the elements
     * @param <Y>       the type of the entity
     * @return a specification that checks if the attribute is equal to all the elements in the list
     */
    public static <T, Y, Z> Specification<Y> hasAllElements(String joinAttribute, String attribute, List<T> list) {
        if (list == null || list.isEmpty()) {
            return (root, query, cb) -> cb.conjunction();
        }
        return (root, query, cb) -> {
            assert query != null;
            Join<Y, Z> join = root.join(joinAttribute, JoinType.INNER);

            return query.getRestriction();
        };
    }

    /**
     * Creates a specification that checks if an entity has all the elements in a list (nested join).
     * The list must not be empty. if it is, the specification will return a conjunction (true).
     * The attribute must be a nested attribute.
     * Useful when the @ManyToMany relationship is not directly connected to the entity but through another entity (via @OneToMany).
     *
     * @param joinAttribute the attribute to join
     * @param middleAttribute the attribute to join in the middle
     * @param attribute the attribute to check
     * @param list      the list of elements to compare the attribute to
     * @param <T>       the type of the elements
     * @param <Y>       the type of the entity
     * @return a specification that checks if the attribute is equal to all the elements in the list
     */
    public static <T, Y, Z> Specification<Y> hasAllElements(String joinAttribute, String middleAttribute, String attribute, List<T> list) {
        if (list == null || list.isEmpty()) {
            return (root, query, cb) -> cb.conjunction();
        }
        return (root, query, cb) -> {
            assert query != null;
            Join<Y, Z> join = root.join(joinAttribute, JoinType.INNER).join(middleAttribute, JoinType.INNER);
            Predicate predicate = join.get(attribute).in(list);
            query.groupBy(root.get("id"));
            query.having(cb.equal(cb.count(root.get("id")), list.size()));
            return predicate;
        };
    }

    /**
     * Creates a specification that checks if an attribute is greater than a given element.
     * The element must not be null. if it is, the specification will return a conjunction (true).
     *
     * @param attribute the attribute to check
     * @param element   the element to compare the attribute to
     * @param <T>       the type of the element
     * @param <Y>       the type of the entity
     * @return a specification that checks if the attribute is greater than the element
     */
    public static <T extends Comparable<T>, Y> Specification<Y> hasAttributeGraterThan(String attribute, T element) {
        if (element == null) {
            return (root, query, cb) -> cb.conjunction();
        }
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.get(attribute), element);
    }

    /**
     * Creates a specification that checks if an attribute is less than a given element.
     * The element must not be null. if it is, the specification will return a conjunction (true).
     *
     * @param attribute the attribute to check
     * @param element   the element to compare the attribute to
     * @param <T>       the type of the element
     * @param <Y>       the type of the entity
     * @return a specification that checks if the attribute is less than the element
     */
    public static <T extends Comparable<T>, Y> Specification<Y> hasAttributeLessThan(String attribute, T element) {
        if (element == null) {
            return (root, query, cb) -> cb.conjunction();
        }
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.get(attribute), element);
    }

    /**
     * Get the Sort.Direction object from the direction string.
     * @param direction The direction string.
     * @return The Sort.Direction object.
     */
    public static Sort.Direction getSortDirection(String direction) {
        if (direction.equals("desc")) {
            return Sort.Direction.DESC;
        }
        return Sort.Direction.ASC;
    }

    /**
     * Get the Pageable object from the PageableRequest object.
     * @param request The PageableRequest object.
     * @return The Pageable object.
     */
    public static Pageable getPageable(PageableRequest request) {
        return PageRequest.of(request.getPage(), request.getPageSize(), Sort.by(getSortDirection(request.getDirection()), request.getSortBy()));
    }
}

