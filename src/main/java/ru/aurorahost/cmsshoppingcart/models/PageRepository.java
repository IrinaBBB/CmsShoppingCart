package ru.aurorahost.cmsshoppingcart.models;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.aurorahost.cmsshoppingcart.models.data.Page;

import java.util.List;

public interface PageRepository extends JpaRepository<Page, Integer> {

}
