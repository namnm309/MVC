/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package namnm.cart;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import namnm.product.ProductDTO;

/**
 *
 * @author Ngoc Lan
 */
public class CartBean implements Serializable {

    private Map<Integer, ProductDTO> items;

    public CartBean() {
        this.items = new HashMap<>();
    }

    public CartBean(Map<Integer, ProductDTO> items) {
        this.items = items;
    }

    public Map<Integer, ProductDTO> getItems() {
        return items;
    }

    public void addItemToCart(ProductDTO product) {
        if (product == null) {
            return;
        }
        if (this.items == null) {
            this.items = new HashMap<>();
        }
        int sku = product.getSku();
        if (this.items.containsKey(sku)) {
            int currentQuantity = this.items.get(sku).getQuantity();
            product.setQuantity(currentQuantity + product.getQuantity());
        }
        this.items.put(sku, product);
    }

    public void removeItemFromCart(int sku) {
        if (this.items != null && this.items.containsKey(sku)) {
            this.items.remove(sku);
            if (this.items.isEmpty()) {
                this.items = null;
            }
        }
    }

}
