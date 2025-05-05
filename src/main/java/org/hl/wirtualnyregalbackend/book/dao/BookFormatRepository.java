package org.hl.wirtualnyregalbackend.book.dao;

import org.hl.wirtualnyregalbackend.book.model.entity.BookFormat;

public interface BookFormatRepository {

    BookFormat findById(Long id);

}
