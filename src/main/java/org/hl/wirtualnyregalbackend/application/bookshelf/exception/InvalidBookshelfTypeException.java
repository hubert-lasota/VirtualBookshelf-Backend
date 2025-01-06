package org.hl.wirtualnyregalbackend.application.bookshelf.exception;

import org.hl.wirtualnyregalbackend.application.bookshelf.BookshelfType;

import java.util.List;

public class InvalidBookshelfTypeException extends RuntimeException {

   private final String invalidType;
   private final List<BookshelfType> validTypes = List.of(BookshelfType.values());

  public InvalidBookshelfTypeException(String type) {
      super("Invalid type: %s".formatted(type));
      this.invalidType = type;
    }

    public List<BookshelfType> getValidTypes() {
        return validTypes;
    }

    public String getInvalidType() {
        return invalidType;
    }

}
