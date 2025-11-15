package com.codewithmosh.store.products;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final CategoryRepository categoryRepository;

    @GetMapping
    public List<ProductDto> getAllProducts(@RequestParam(name = "categoryId", required = false) Byte categoryId) {

        List<Product> productsFound;

        if (categoryId != null) {
            productsFound = productRepository.findAllByCategoryId(categoryId);
        }
        else {
            productsFound = productRepository.findAllWithCategory();
        }
        return productsFound
                .stream()
                .map(productMapper::toDto)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable Long id) {
         var product = productRepository.findById(id).orElse(null);
         if (product == null) {
             return ResponseEntity.notFound().build();
         }
         return ResponseEntity.ok(productMapper.toDto(product));
    }

    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDtoForCreateUpdate productDtoForCreateUpdate,
                                                    UriComponentsBuilder uriComponentsBuilder) {

        var product = productMapper.toEntity(productDtoForCreateUpdate);

        // может категории и не быть...
        var categoryRef = productDtoForCreateUpdate.getCategoryId();
        if (categoryRef != null) {
            var category = categoryRepository.findById(categoryRef).orElse(null);

            if (category != null) {
                product.setCategory(category);
            }
            else {
                return ResponseEntity.badRequest().build();
            }
        }

        productRepository.save(product);

        var productDto = productMapper.toDto(product);
        var uri = uriComponentsBuilder.path("/products/{id}").buildAndExpand(productDto.getId()).toUri();
        return ResponseEntity.created(uri).body(productDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> updateProduct(
            @PathVariable(name = "id") Long id,
            @RequestBody ProductDtoForCreateUpdate productDtoForCreateUpdate) {

        var product = productRepository.findById(id).orElse(null);
        if (product == null) {
            return ResponseEntity.notFound().build();
        }

        productMapper.update(productDtoForCreateUpdate, product);

        var categoryIdFromUpdateRequest = productDtoForCreateUpdate.getCategoryId();

        // если выбрана новая категория, устанавливаем ее
        if (!categoryIdFromUpdateRequest.equals(product.getCategory().getId())) {
            var category = categoryRepository.findById(categoryIdFromUpdateRequest).orElse(null);

            if (category != null) {
                product.setCategory(category);
            }
            else return ResponseEntity.badRequest().build();
        }

        productRepository.save(product);

        return ResponseEntity.ok(productMapper.toDto(product));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable(name = "id") Long id) {
        var product = productRepository.findById(id).orElse(null);
        if (product == null) {
            return ResponseEntity.notFound().build();
        }

        productRepository.delete(product);
        return ResponseEntity.noContent().build();
    }
}
