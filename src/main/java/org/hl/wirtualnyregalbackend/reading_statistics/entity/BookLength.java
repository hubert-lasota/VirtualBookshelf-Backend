package org.hl.wirtualnyregalbackend.reading_statistics.entity;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum BookLength {
    SHORT(0, 200),
    MEDIUM(200, 400),
    LONG(400, 600),
    VERY_LONG(600, Integer.MAX_VALUE);

    private final int minPages;
    private final int maxPages;


    public static BookLength fromPageCount(int pageCount) {
        for (BookLength length : BookLength.values()) {
            if (length.matches(pageCount)) {
                return length;
            }
        }
        throw new IllegalArgumentException("Invalid page count: " + pageCount);
    }

    private boolean matches(int pageCount) {
        return pageCount >= minPages && pageCount < maxPages;
    }

}
