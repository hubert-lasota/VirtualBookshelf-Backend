package org.hl.wirtualnyregalbackend.bookshelf;

import org.hl.wirtualnyregalbackend.auth.entity.User;
import org.hl.wirtualnyregalbackend.bookshelf.dto.BookshelfRequest;
import org.hl.wirtualnyregalbackend.bookshelf.dto.BookshelfResponse;
import org.hl.wirtualnyregalbackend.bookshelf.entity.Bookshelf;
import org.hl.wirtualnyregalbackend.user.UserTestDataProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class BookshelfCommandServiceTest {

    private BookshelfRepository bookshelfRepository;
    private BookshelfQueryService bookshelfQuery;
    private BookshelfCommandService bookshelfCommand;

    @BeforeEach
    void setUp() {
        bookshelfRepository = mock(BookshelfRepository.class);
        bookshelfQuery = mock(BookshelfQueryService.class);
        bookshelfCommand = new BookshelfCommandService(bookshelfRepository, bookshelfQuery);
    }


    @Test
    void shouldCreateBookshelf_whenRequestIsValid() {
        // Given
        User user = UserTestDataProvider.getRandomUser();
        BookshelfRequest request = BookshelfTestDataProvider.getBookshelfRequest();

        ArgumentCaptor<Bookshelf> captor = ArgumentCaptor.forClass(Bookshelf.class);

        // When
        BookshelfResponse response = bookshelfCommand.createBookshelf(request, user);

        // Then
        verify(bookshelfRepository).save(captor.capture());
        Bookshelf saved = captor.getValue();

        assertThat(response)
            .isNotNull()
            .extracting(BookshelfResponse::name, BookshelfResponse::type, BookshelfResponse::description)
            .containsExactly(request.name(), request.type(), request.description());

        assertThat(saved)
            .isNotNull()
            .extracting(Bookshelf::getName, Bookshelf::getType, Bookshelf::getDescription, Bookshelf::getUser)
            .containsExactly(request.name(), request.type(), request.description(), user);
    }

}
