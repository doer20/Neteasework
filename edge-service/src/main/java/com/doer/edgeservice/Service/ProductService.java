package com.doer.edgeservice.Service;

import com.doer.edgeservice.Config.FeignOauth2RequestInterceptor;
import com.doer.edgeservice.Entities.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "product-service", configuration = FeignOauth2RequestInterceptor.class)
public interface ProductService {

    @RequestMapping(value = "/detail/{productId}" ,method=RequestMethod.GET)
    String getProduct(@PathVariable(value = "productId") String productId);

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    String getCatalog(@RequestParam("offset") Integer offset,@RequestParam("rowCount") Integer rowCount);

    @RequestMapping(value = "/unsoldList", method = RequestMethod.GET)
    String getUnsoldList();

    @RequestMapping(value = "/addClosing", method = RequestMethod.POST)
    String addClosingNum(@RequestParam("productId") String productId);

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    String updateProduct(Product product);

    @RequestMapping(value = "/addProduct", method = RequestMethod.POST)
    String publicProduct(Product product);

    @RequestMapping(value = "/deleteUnsold/{productId}", method = RequestMethod.DELETE)
    String deleteUnsoldProduct(@PathVariable(value = "productId") String productId);

    @RequestMapping(value = "/count", method = RequestMethod.GET)
    String getCount();

    @RequestMapping(value = "/delete/{productId}", method = RequestMethod.DELETE)
    String deleteProduct(@PathVariable(value = "productId") String productId);

    @RequestMapping(value = "/getClosingNum", method = RequestMethod.GET)
    String getClosingNum(String productId);
}
