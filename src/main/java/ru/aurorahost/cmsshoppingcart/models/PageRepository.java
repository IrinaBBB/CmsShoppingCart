package ru.aurorahost.cmsshoppingcart.models;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.aurorahost.cmsshoppingcart.models.data.Page;


public interface PageRepository extends JpaRepository<Page, Integer> {

    Page findBySlug(String slug);
}
