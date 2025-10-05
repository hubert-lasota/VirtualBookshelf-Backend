package org.hl.wirtualnyregalbackend.user.dto;

import java.util.List;

public record UpdateGenrePreferencesRequest(List<Long> genrePreferenceIds) {
}
