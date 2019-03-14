package com.doer.productservice.Controller;

import com.doer.productservice.Entities.Product;
import com.doer.productservice.Service.ProductService;
import org.hibernate.validator.constraints.pl.REGON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @RequestMapping(value = "/detail/{product_id}" ,method=RequestMethod.GET)
    public String getProduct(@PathVariable String product_id){
        return productService.getProductDetail(product_id).toString();
    }


    @RequestMapping(value = "/catalog", method = RequestMethod.GET)
    public String getCatalog(Integer offset,Integer rowCount){
        return productService.getBriefList(offset,rowCount).toString();
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String updateProduct(Product product){
        boolean flag = productService.updateProduct(product);
        return "{'status':"+flag+"}";
    }

    @RequestMapping(value = "/public", method = RequestMethod.POST)
    public String publicProduct(Product product){
        boolean flag = productService.publicProduct(product);
        return "{'status':"+flag+"}";
    }

    @RequestMapping(value = "/delete}", method = RequestMethod.POST)
    public String deleteProduct(String product_id){
        boolean flag = productService.deleteProduct(product_id);
        return "{'status':"+flag+"}";
    }

    @RequestMapping(value = "/count", method = RequestMethod.GET)
    public String getCount(){
        int count = productService.getCount();
        return String.valueOf(count);
    }

}
