package com.tul.controller;

import com.tul.exceptions.ProductNotFoundException;
import com.tul.model.Product;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping(value="items/v1")
public class ProductController {
    private HashMap<Integer, Product> productRepo = new HashMap<Integer, Product>();
    public HashMap<Integer, Product> fetchProducts(){
        Product iPhone = new Product();
        iPhone.setProductId(1);
        iPhone.setProductName("iPhone");
        iPhone.setPrice(70000.00);

        Product appo = new Product();
        appo.setProductId(2);
        appo.setProductName("appo");
        appo.setPrice(10000.00);
        productRepo.put(1,iPhone);
        productRepo.put(2,appo);
        return productRepo;
    }

    @RequestMapping(value="/products")
    public ResponseEntity<Object>getProducts(){
        HashMap<Integer, Product> productRepo = fetchProducts();
        List<Product> products = new ArrayList<Product>();
        productRepo.forEach((k,v) -> products.add(v));
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @RequestMapping(value="/products/{id}")
    public ResponseEntity<Object>getProduct(@PathVariable("id") Integer id){
        long beginTime = System.currentTimeMillis();
        long responseTime;
        HashMap<Integer, Product> productRepo = fetchProducts();
        if (!productRepo.containsKey(id)){
            responseTime = System.currentTimeMillis() - beginTime;
            throw new ProductNotFoundException();
        }
        responseTime = System.currentTimeMillis() - beginTime;
        return new ResponseEntity<>(productRepo.get(id), HttpStatus.OK);
    }

    @RequestMapping(value="/products", method= RequestMethod.POST)
    public ResponseEntity<Object>createProduct(@RequestBody Product product){
        HashMap<Integer, Product> productRepo = fetchProducts();
        productRepo.put(product.getProductId(), product);
        return new ResponseEntity<>("Product is created successfully",HttpStatus.OK);
    }

    @RequestMapping(value="/products", method= RequestMethod.PUT)
    public ResponseEntity<Object>updateProduct(@RequestBody Product product){
        HashMap<Integer, Product> productRepo = fetchProducts();
        productRepo.remove(product.getProductId());
        productRepo.put(product.getProductId(), product);
        return new ResponseEntity<>("Product is updated successfully",HttpStatus.OK);
    }

    @RequestMapping(value="/products", method= RequestMethod.DELETE)
    public ResponseEntity<Object>deleteProduct(int productId){
        HashMap<Integer, Product> productRepo = fetchProducts();
        productRepo.remove(productId);
        return new ResponseEntity<>("Product is deleted successfully",HttpStatus.OK);
    }

}
