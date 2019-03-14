package com.doer.productservice.Service;

import com.doer.productservice.Entities.Product;
import com.doer.productservice.Mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    @Autowired
    private ProductMapper mapper;

    public Product getProduct(String product_id){
        return mapper.selectByPrimaryKey(product_id);
    }
}
