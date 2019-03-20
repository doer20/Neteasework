package com.doer.productservice.Controller;

import com.doer.productservice.Entities.Product;
import com.doer.productservice.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @RequestMapping(value = "/detail/{productId}" ,method=RequestMethod.GET)
    public String getProduct(@PathVariable String productId){
        return productService.getProductDetail(productId).toString();
    }


    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String getCatalog(Integer offset,Integer rowCount){
        return productService.getBriefList(offset,rowCount).toString();
    }

    @RequestMapping(value = "/unsoldList", method = RequestMethod.GET)
    public String getUnsoldList(){
        return productService.getUnsoldBriefList().toString();
    }

    @RequestMapping(value = "/addClosing", method = RequestMethod.POST)
    public String addClosingNum(String productId){
        boolean flag = productService.addClosingNum(productId);
        return "{\"status\":"+flag+"}";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String updateProduct(@RequestBody Product product){
        boolean flag = productService.updateProduct(product);
        return "{\"status\":"+flag+"}";
    }

    @RequestMapping(value = "/addProduct", method = RequestMethod.POST)
    public String publicProduct(@RequestBody() Product product){
        String msg = productService.publicProduct(product);
        return msg;
    }

    @RequestMapping(value = "/deleteUnsold/{productId}", method = RequestMethod.DELETE)
    public String deleteUnsoldProduct(@PathVariable String productId){
        boolean flag = productService.deleteUnsoldProduct(productId);
        return "{\"status\":"+flag+"}";
    }

    @RequestMapping(value = "/count", method = RequestMethod.GET)
    public String getCount(){
        int count = productService.getCount();
        return String.valueOf(count);
    }

    @RequestMapping(value = "/uploadImg", method = RequestMethod.POST)
    public String uploadImg(){
        return null;
    }

    @Profile({"default","dev","test"})
    @RequestMapping(value = "/delete/{productId}", method = RequestMethod.DELETE)
    public String deleteProduct(@PathVariable String productId){
        boolean flag = productService.deleteProduct(productId);
        return "{\"status\":"+flag+"}";
    }

    @Profile({"default","dev","test"})
    @RequestMapping(value = "/getClosingNum", method = RequestMethod.GET)
    public String getClosingNum(String productId){
        int num = productService.getClosingNum(productId);
        return String.valueOf(num);
    }

}
