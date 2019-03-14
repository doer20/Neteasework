package com.doer.productservice.Mapper;

import com.doer.productservice.Entities.Product;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductMapper {
    int deleteByPrimaryKey(String productId);

    int insert(Product record);

    int insertSelective(Product record);

    Product selectByPrimaryKey(String productId);

    List<Product> selectBriefList(@Param("offset") Integer offset, @Param("rowCount") Integer rowCount);

    Integer getCount();

    int updateByPrimaryKeySelective(Product record);

    int updateByPrimaryKey(Product record);
}