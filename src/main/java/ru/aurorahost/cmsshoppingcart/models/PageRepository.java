package ru.aurorahost.cmsshoppingcart.models;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.validation.annotation.Validated;
import ru.aurorahost.cmsshoppingcart.models.data.Page;

@Validated
public interface PageRepository extends JpaRepository<Page, Integer> {
    Page findBySlug(String slug);
}
