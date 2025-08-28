package org.hl.wirtualnyregalbackend.common.translation;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.util.Locale;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString
public class TranslatedName {

    @Column
    private String name;

    @Column(name = "language_code")
    private Locale language;

}
