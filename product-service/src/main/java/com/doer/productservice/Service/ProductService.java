package com.doer.productservice.Service;

import com.alibaba.fastjson.JSONObject;
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

    public List<Product> getUnsoldBriefList(){
        return mapper.getUnsoldBriefList();
    }

    public int getClosingNum(String product_id){
        return mapper.getClosingNum(product_id);
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

    public boolean addClosingNum(String product_id){
        boolean flag = true;
        Product item = getProductDetail(product_id);
        if (item == null){
            flag = false;
        }else {
            int closingNum = item.getClosingNum();
            closingNum += 1;
            item.setClosingNum(closingNum);
            flag = updateProduct(item);
        }
        return flag;
    }

    public String publicProduct(Product product){
        boolean flag = true;
        String id = mapper.selectAutoIncrementId();
        if (id == null) {
            id = "1";
        }else {
            id = id.substring(0,id.length()-2);
        }
        product.setProductId(id);
        try {
            mapper.insertSelective(product);
        }catch (Exception e){
            flag = false;
            e.printStackTrace();
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status",flag);
        jsonObject.put("productId",id);
        return jsonObject.toJSONString();
    }

    public boolean deleteProduct(String product_id){
        boolean flag = true;
        try {
            mapper.deleteByPrimaryKey(product_id);
        }catch (Exception e){
            flag = false;
            e.printStackTrace();
        }
        return flag;
    }

    public boolean deleteUnsoldProduct(String product_id){
        Product item = getProductDetail(product_id);
        if (item == null || item.getClosingNum() != 0){
            return false;
        }
        return deleteProduct(product_id);
    }

    public int getCount(){
        return mapper.getCount();
    }

}
