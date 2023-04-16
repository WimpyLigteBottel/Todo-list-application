package nel.marco.db

import jakarta.persistence.criteria.CriteriaBuilder
import jakarta.persistence.criteria.CriteriaQuery
import jakarta.persistence.criteria.Predicate
import jakarta.persistence.criteria.Root
import org.springframework.data.jpa.domain.Specification
import java.time.LocalDate

class TaskSpecification(
    val ids: List<Long>? = emptyList(),
    val message: String? = null,
    val isBefore: LocalDate? = null,
    val isAfter: LocalDate? = null,
) : Specification<Task> {

    override fun toPredicate(root: Root<Task>, query: CriteriaQuery<*>, cb: CriteriaBuilder): Predicate {
        val predicates: MutableList<Predicate> = mutableListOf()

        addIdFilters(predicates, root)
        addMessageFilter(predicates, cb, root)
        addDateFilters(predicates, cb, root)

        return cb.and(*predicates.toTypedArray())
    }

    private fun addIdFilters(
        predicates: MutableList<Predicate>,
        root: Root<Task>
    ) {
        if (!ids.isNullOrEmpty()) {
            predicates.add(root.get<Long>("id").`in`(ids))
        }
    }

    private fun addMessageFilter(
        predicates: MutableList<Predicate>,
        cb: CriteriaBuilder,
        root: Root<Task>
    ) {
        if (message?.isNotBlank() == true) {
            predicates.add(cb.like(cb.lower(root.get("message")), "%$message%"))
        }
    }

    private fun addDateFilters(
        predicates: MutableList<Predicate>,
        cb: CriteriaBuilder,
        root: Root<Task>
    ) {
        if (isAfter != null && isBefore != null) {
            predicates.add(cb.between(root.get("created"), isAfter, isBefore))
        } else {
            if (isBefore != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("created"), isBefore))
            }
            if (isAfter != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("created"), isAfter))
            }
        }
    }

}