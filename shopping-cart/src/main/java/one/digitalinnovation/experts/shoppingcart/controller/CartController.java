package one.digitalinnovation.experts.shoppingcart.controller;

import one.digitalinnovation.experts.shoppingcart.model.Cart;
import one.digitalinnovation.experts.shoppingcart.model.Item;
import one.digitalinnovation.experts.shoppingcart.repository.CartRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/carts")
public class CartController {

    private final CartRepository cartRepository;

    public CartController(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    @PostMapping("/{id}")
    public ResponseEntity<?> addItem(@PathVariable("id") Integer id, @RequestBody Item item) {
        var savedCart = cartRepository.findById(id);
        Cart cart;
        if (savedCart.isEmpty()) {
            cart = new Cart();
            cart.setId(id);
        }
        else {
            cart = savedCart.get();
        }
        cart.getItems().add(item);
        return ResponseEntity.ok(cartRepository.save(cart));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> fingById(@PathVariable("id") Integer id) {
        return cartRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public void clear(@PathVariable("id") Integer id) {
        cartRepository.deleteById(id);
    }
}
