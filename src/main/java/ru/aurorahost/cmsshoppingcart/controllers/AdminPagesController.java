package ru.aurorahost.cmsshoppingcart.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.aurorahost.cmsshoppingcart.models.PageRepository;
import ru.aurorahost.cmsshoppingcart.models.data.Page;

import javax.validation.Valid;
import java.util.List;
import java.util.Locale;

@Controller
@RequestMapping("/admin/pages")
public class AdminPagesController {

    private PageRepository pageRepo;

    public AdminPagesController(PageRepository pageRepo) {
        this.pageRepo = pageRepo;
    }

    @GetMapping
    public String index(Model model) {
        List<Page> pages = pageRepo.findAll();
        model.addAttribute("pages", pages);

        return "admin/pages/index";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute Page page) {
        //model.addAttribute("pages", new Page());
        return "admin/pages/add";
    }

    @PostMapping("/add")
    public String add(@Valid @ModelAttribute("page") Page page,
                      BindingResult bindingResult,
                      RedirectAttributes redirectAttributes,
                      Model model) {

        if (bindingResult.hasErrors()) {
            return "admin/pages/add";
        }

        redirectAttributes.addFlashAttribute("message", "Page added");
        redirectAttributes.addFlashAttribute("alertClass", "alert-success");

        String slug = page.getSlug().equals("") ? page.getTitle().toLowerCase(Locale.ROOT).replace(" ", "-")
                : page.getSlug().toLowerCase(Locale.ROOT).replace(" ", "-");

        Page slugExists = pageRepo.findBySlug(slug);

        if (slugExists != null) {
            redirectAttributes.addFlashAttribute("message", "A slug with this name already exists, please choose " +
                    "another");
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            redirectAttributes.addFlashAttribute("page", page);
        } else {
            page.setSlug(slug);
            page.setSorting(100);

            pageRepo.save(page);
        }
        return "redirect:/admin/pages/add";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable int id, Model model) {
        Page page = pageRepo.getOne(id);

        model.addAttribute("page", page);

        return "admin/pages/edit";
    }

    @PostMapping("/edit")
    public String edit(@Valid @ModelAttribute("page") Page page,
                      BindingResult bindingResult,
                      RedirectAttributes redirectAttributes,
                      Model model) {

        Page pageCurrent = pageRepo.getOne(page.getId());

        if (bindingResult.hasErrors()) {
            model.addAttribute("pageTitle", pageCurrent.getTitle());
            return "admin/pages/edit";
        }

        redirectAttributes.addFlashAttribute("message", "Page edited");
        redirectAttributes.addFlashAttribute("alertClass", "alert-success");

        String slug = page.getSlug().equals("") ? page.getTitle().toLowerCase(Locale.ROOT).replace(" ", "-")
                : page.getSlug().toLowerCase(Locale.ROOT).replace(" ", "-");

        Page slugExists = pageRepo.findBySlugAndIdNot(slug, page.getId());

        if (slugExists != null) {
            redirectAttributes.addFlashAttribute("message", "A slug with this name already exists, please choose " +
                    "another");
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            redirectAttributes.addFlashAttribute("page", page);
        } else {
            page.setSlug(slug);

            pageRepo.save(page);
        }
        return "redirect:/admin/pages/edit/" + page.getId();
    }

}
