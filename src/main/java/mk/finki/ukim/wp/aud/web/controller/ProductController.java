package mk.finki.ukim.wp.aud.web.controller;


import mk.finki.ukim.wp.aud.model.Category;
import mk.finki.ukim.wp.aud.model.Manufacturer;
import mk.finki.ukim.wp.aud.model.Product;
import mk.finki.ukim.wp.aud.service.CategoryService;
import mk.finki.ukim.wp.aud.service.ManufacturerService;
import mk.finki.ukim.wp.aud.service.ProductService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;
    private final ManufacturerService manufacturerService;

    public ProductController(ProductService productService,
                             CategoryService categoryService,
                             ManufacturerService manufacturerService) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.manufacturerService = manufacturerService;
    }

    @GetMapping
    public String getProductPage(@RequestParam(required = false) String error, Model model) {
        if(error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }
        List<Product> products = this.productService.findAll();
        model.addAttribute("products", products);
        model.addAttribute("bodyContent", "products");
        return "master-template";
        //return "products";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        this.productService.deleteById(id);
        return "redirect:/products";
    }

    //add product
    @GetMapping("/add-form")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String addProductPage(Model model) {
        List<Category> categories = this.categoryService.listCategories();
        List<Manufacturer> manufacturers = this.manufacturerService.findAll();
        model.addAttribute("categories", categories);
        model.addAttribute("manufacturers", manufacturers);
        model.addAttribute("bodyContent", "add-product");
        //return "add-product";
        return "master-template";
    }

    //edit product
    @GetMapping("/edit-form/{id}")
    public String editProductPage(@PathVariable Long id, Model model) {
        if(this.productService.findById(id).isPresent()) {
            Product product = this.productService.findById(id).get();
            List<Category> categories = this.categoryService.listCategories();
            List<Manufacturer> manufacturers = this.manufacturerService.findAll();
            model.addAttribute("categories", categories);
            model.addAttribute("manufacturers", manufacturers);
            model.addAttribute("product", product);
            model.addAttribute("bodyContent", "add-product");
            return "master-template";
        }
        return "redirect:/products?error=ProductNotFound";
    }

    @PostMapping("/add")
    public String saveProduct(@RequestParam String name,
                              @RequestParam Double price,
                              @RequestParam Integer quantity,
                              @RequestParam Long category,
                              @RequestParam Long manufacturer ) {
        this.productService.save(name, price, quantity, category, manufacturer);
        return "redirect:/products";
    }

    @PostMapping("/find")
    public String findAllByText(@RequestParam(required = false) String text, Model model) {
        List<Product> products;

        if (text.isEmpty() || text == null) {
            products = this.productService.findAll();
        } else {
            products = this.productService.findAllByText(text);
        }

        model.addAttribute("products", products);
        model.addAttribute("bodyContent", "products");
        return "master-template";
    }
}

