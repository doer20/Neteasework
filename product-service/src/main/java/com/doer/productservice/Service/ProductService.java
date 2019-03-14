package com.doer.productservice.Service;

import com.doer.productservice.Entities.Product;
import com.doer.productservice.Mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductMapper mapper;

    public Product getProductDetail(String product_id){
        return mapper.selectByPrimaryKey(product_id);
    }

    public List<Product> getBriefList(Integer offset,Integer rowCount){
        return mapper.selectBriefList(offset,rowCount);
    }

    public boolean updateProduct(Product product){
        boolean flag = true;
        try {
            mapper.updateByPrimaryKeySelective(product);
        }catch (Exception e){
            flag = false;
            e.printStackTrace();
        }
        return flag;
    }

    public boolean publicProduct(Product product){
        boolean flag = true;
        try {
            mapper.insertSelective(product);
        }catch (Exception e){
            flag = false;
            e.printStackTrace();
        }
        return flag;
    }

    public boolean deleteProduct(String product_id){
        boolean flag = true;
        //TODO
        //1. check if exits product
        //2. check the product has not been sold;
        try {
            mapper.deleteByPrimaryKey(product_id);
        }catch (Exception e){
            flag = false;
            e.printStackTrace();
        }
        return flag;
    }

    public int getCount(){
        return mapper.getCount();
    }

}
