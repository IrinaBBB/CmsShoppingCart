package ru.aurorahost.cmsshoppingcart.models;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.validation.annotation.Validated;
import ru.aurorahost.cmsshoppingcart.models.data.Page;

@Validated
public interface PageRepository extends JpaRepository<Page, Integer> {
    Page findBySlug(String slug);

//    @Query(value = "SELECT * FROM pages WHERE id != :id AND slug = :slug ", nativeQuery = true)
//    Page findBySlug(int id, String slug);

    Page findBySlugAndIdNot(String slug, int id);
}
