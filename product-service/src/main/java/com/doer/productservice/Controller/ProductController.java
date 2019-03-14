package com.doer.productservice.Controller;

import com.doer.productservice.Service.ProductService;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @RequestMapping("/products/{product_id}")
    public String getProduct(@PathVariable String product_id){
        return productService.getProduct(product_id).toString();
    }

}
