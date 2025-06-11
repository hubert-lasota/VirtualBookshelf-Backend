package org.hl.wirtualnyregalbackend.book_cover;

import org.hl.wirtualnyregalbackend.book_cover.entity.BookCover;
import org.hl.wirtualnyregalbackend.book_cover.entity.BookCoverBinary;
import org.hl.wirtualnyregalbackend.common.ServerInfoProvider;
import org.hl.wirtualnyregalbackend.common.exception.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class BookCoverService {

    private final Logger log = LoggerFactory.getLogger(BookCoverService.class);

    private final BookCoverRepository bookCoverRepository;
    private final ServerInfoProvider serverInfoProvider;

    BookCoverService(BookCoverRepository bookCoverRepository, ServerInfoProvider serverInfoProvider) {
        this.bookCoverRepository = bookCoverRepository;
        this.serverInfoProvider = serverInfoProvider;
    }


    public BookCover createBookCover(String coverUrl, MultipartFile coverFile) {
        if (coverUrl != null) {
            return new BookCover(coverUrl, null);
        } else if (coverFile != null && !coverFile.isEmpty()) {
            try {
                BookCoverBinary binaryCover = new BookCoverBinary(
                    coverFile.getBytes(),
                    coverFile.getContentType(),
                    coverFile.getOriginalFilename()
                );
                BookCover cover = new BookCover(null, binaryCover);
                bookCoverRepository.saveAndFlush(cover);
                String origin = serverInfoProvider.getOrigin();
                String url = origin + "/api/v1/book-covers/" + cover.getId();
                cover.setUrl(url);
                bookCoverRepository.save(cover);
                return cover;
            } catch (IOException exc) {
                log.error("Error while reading cover file.", exc);
                return null;
            }
        } else {
            return null;
        }
    }

    public BookCoverBinary findBookCoverBinaryByCoverId(Long coverId) {
        BookCover bookCover = bookCoverRepository.findById(coverId)
            .orElseThrow(() -> new EntityNotFoundException("BookCover with id: %d not found".formatted(coverId)));
        return bookCover.getCoverBinary();
    }

}
