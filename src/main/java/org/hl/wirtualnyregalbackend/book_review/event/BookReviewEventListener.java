package org.hl.wirtualnyregalbackend.book_review.event;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class BookReviewEventListener {

    @EventListener
    public void handleBookReviewCreatedEvent(BookReviewCreatedEvent event) {

    }
}
