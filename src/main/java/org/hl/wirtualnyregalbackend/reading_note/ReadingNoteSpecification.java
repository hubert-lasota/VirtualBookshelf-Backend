package org.hl.wirtualnyregalbackend.reading_note;

import jakarta.persistence.criteria.Expression;
import org.hl.wirtualnyregalbackend.reading_note.entity.ReadingNote;
import org.springframework.data.jpa.domain.Specification;

class ReadingNoteSpecification {

    private ReadingNoteSpecification() {
    }

    public static Specification<ReadingNote> byReadingBookId(Long readingBookId) {
        return (root, query, cb) -> cb.equal(root.get("readingBook").get("id"), readingBookId);
    }

    public static Specification<ReadingNote> byQuery(String query) {
        return (root, cq, cb) -> {
            Expression<String> title = cb.lower(root.get("title"));
            Expression<String> content = cb.lower(root.get("content"));
            String pattern = "%" + query.toLowerCase() + "%";
            return cb.or(cb.like(title, pattern), cb.like(content, pattern));
        };
    }

}
