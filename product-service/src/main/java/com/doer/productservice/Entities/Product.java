package com.doer.productservice.Entities;

public class Product {
    private String productId;

    private String title;

    private String content;

    private String summary;

    private String imageSrc;

    private Integer price;

    private Integer closingNum;

    public Product(){}

    public Product(String product_id,String title,String content,String summary,String image_src,int price,int closingNum){
        this.productId = product_id;
        this.title = title;
        this.content = content;
        this.summary = summary;
        this.imageSrc = image_src;
        this.price = price;
        this.closingNum = closingNum;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId == null ? null : productId.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary == null ? null : summary.trim();
    }

    public String getImageSrc() {
        return imageSrc;
    }

    public void setImageSrc(String imageSrc) {
        this.imageSrc = imageSrc == null ? null : imageSrc.trim();
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getClosingNum() {
        return closingNum;
    }

    public void setClosingNum(Integer closingNum) {
        this.closingNum = closingNum;
    }

    @Override
    public String toString() {
        return "{\"productId\":\""+productId+"\"" +
                ",\"title\":\""+title+"\"" +
                ",\"content\":\""+content+"\"" +
                ",\"summary\":\""+summary+"\"" +
                ",\"price\":"+price+"" +
                ",\"imageSrc\":\""+imageSrc+"\"" +
                ",\"closingNum\":"+closingNum+"}";
    }
}