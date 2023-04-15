package sk.umb.eshop.cart.service;

import org.springframework.stereotype.Service;
import sk.umb.eshop.cart.persistence.entity.CartEntity;
import sk.umb.eshop.cart.persistence.repository.CartRepository;

import java.util.ArrayList;
import java.util.List;
@Service
public class CartService {
    private final CartRepository cartRepository;

    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public List<CartDetailDTO> getAllCarts() {
        return mapToDto(cartRepository.findAll());
    }


    public CartDetailDTO getCartById(Long cartId) {
        validateCartExists(cartId);
        return mapToDto(cartRepository.findById(cartId).get());
    }

    public Long createCart(CartRequestDTO cartRequestDTO) {
        return cartRepository.save(mapToEntity(cartRequestDTO)).getId();
    }

    private List<CartDetailDTO> mapToDto(List<CartEntity> cartEntities) {
        List<CartDetailDTO> dtos = new ArrayList<>();

        for (CartEntity ce : cartEntities) {
            CartDetailDTO dto = new CartDetailDTO();

            dto.setId(ce.getId());
            dto.setOrderId(ce.getOrderId());
            dto.setProductId(ce.getProductId());

            dtos.add(dto);
        }

        return dtos;
    }
    private CartDetailDTO mapToDto(CartEntity cartEntity) {
        CartDetailDTO dto = new CartDetailDTO();

        dto.setId(cartEntity.getId());
        dto.setProductId(cartEntity.getProductId());
        dto.setOrderId(cartEntity.getOrderId());


        return dto;
    }

    private CartEntity mapToEntity(CartRequestDTO dto) {
        CartEntity ce = new CartEntity();

        ce.setOrderId(dto.getOrderId());
        ce.setProductId(dto.getProductId());



        return ce;
    }

    public void updateCart(Long cartId, CartRequestDTO cartRequestDTO) {
        validateCartExists(cartId);

        CartEntity cartEntity = cartRepository.findById(cartId).get();

        if (cartRequestDTO.getOrderId() != null) {
            cartEntity.setOrderId(cartRequestDTO.getOrderId());
        }

        if (cartRequestDTO.getProductId() != null) {
            cartEntity.setProductId(cartRequestDTO.getProductId());
        }

        cartRepository.save(cartEntity);
    }

    private void validateCartExists(Long cartId) {
        if (! cartRepository.existsById(cartId)) {
            throw new IllegalArgumentException("CustomerId: " + cartId + " does not exists!");
        }
    }

    public void deleteCart(Long cartId) {
        cartRepository.deleteById(cartId);
    }
}
