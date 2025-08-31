package org.hl.wirtualnyregalbackend.book_cover;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hl.wirtualnyregalbackend.book_cover.entity.BookCover;
import org.hl.wirtualnyregalbackend.book_cover.entity.BookCoverBinary;
import org.hl.wirtualnyregalbackend.book_cover.exception.BookCoverNotFoundException;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Slf4j
public class BookCoverService {

    private final BookCoverRepository bookCoverRepository;
    private final BookCoverUrlBuilder coverUrlBuilder;


    @Nullable
    @Transactional
    public BookCover createBookCover(@Nullable String coverUrl, @Nullable MultipartFile coverFile) {
        if (coverUrl != null && coverFile != null) {
            return null;
        }

        if (coverUrl != null) {
            return new BookCover(coverUrl, null);
        }

        try {
            BookCoverBinary binaryCover = new BookCoverBinary(coverFile);
            BookCover cover = new BookCover(null, binaryCover);
            bookCoverRepository.saveAndFlush(cover);
            cover.setUrl(coverUrlBuilder.buildCoverUrl(cover));
            return cover;
        } catch (IOException exc) {
            log.error("Error while reading cover file.", exc);
            return null;
        }

    }

    public BookCoverBinary findBookCoverBinaryByCoverId(Long coverId) throws BookCoverNotFoundException {
        return bookCoverRepository
            .findById(coverId)
            .orElseThrow(() -> {
                log.warn("BookCover not found with ID: {}", coverId);
                return new BookCoverNotFoundException(coverId);
            })
            .getCoverBinary();
    }

}
