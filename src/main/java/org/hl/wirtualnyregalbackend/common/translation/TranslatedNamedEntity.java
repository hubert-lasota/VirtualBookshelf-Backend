package org.hl.wirtualnyregalbackend.common.translation;


/**
 * <p>Interface representing an entity that provides a translated name.
 *
 * <p>By implementing this interface, a class indicates that it supports
 * returning its name in a specific user locale.
 *
 * <p>This interface allows unified access to localized names across
 * different domain types, such as book formats, book series, categories, etc.
 *
 * <p>It enables shared logic in mappers or services to retrieve the appropriate
 * name without needing to know the exact type of the entity.
 */
public interface TranslatedNamedEntity {

    /**
     * Returns the translated name associated with this entity.
     *
     * @return an object containing translations of the entity's name
     */
    TranslatedName getTranslatedName();

}
