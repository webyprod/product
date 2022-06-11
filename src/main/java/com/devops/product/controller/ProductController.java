package com.devops.product.controller;


import com.devops.product.dto.Coupon;
import com.devops.product.model.Product;
import com.devops.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/productapi")
public class ProductController {

    @Value("${coupon.code.url}")
    private String urlCouponCode;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    ProductRepository productRepository;

    @PostMapping
    public Product create (@RequestBody Product product) {

        Coupon coupon = restTemplate.getForObject(urlCouponCode + product.getCouponCode(), Coupon.class);
        product.setPrice(product.getPrice().subtract(coupon.getDiscount()));
        return productRepository.save(product);
    }

    @GetMapping("/products/{id}")
    public Product getProduct (@PathVariable("id") Long id) {
        return productRepository.findById(id).get();
    }


}
