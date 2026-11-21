package com.supplychainmanagement.service;


import com.supplychainmanagement.dto.request.ChatRequest;
import com.supplychainmanagement.dto.response.ChatResponse;
import com.supplychainmanagement.entity.Product;
import com.supplychainmanagement.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AIChatService {

    private final ProductRepository productRepository;

    public AIChatService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ChatResponse chat(ChatRequest request) {

        String message = request.message().toLowerCase();
        String answer;

        List<Product> products = productRepository.findAll();

        if (message.contains("low stock")) {

            long count = products.stream()
                    .filter(p -> p.getCurrentStock()
                            <= p.getReorderLevel())
                    .count();

            answer = "There are " + count
                    + " products with low or depleted stock.";

        } else if (message.contains("out of stock")) {

            long count = products.stream()
                    .filter(p -> p.getCurrentStock() <= 0)
                    .count();

            answer = "There are " + count
                    + " products currently out of stock.";

        } else if (message.contains("total products")) {

            answer = "Total products: " + products.size();

        } else {

            answer = "I can help with inventory questions such as "
                    + "'show low stock', 'out of stock', and "
                    + "'total products'.";
        }

        return new ChatResponse(
                request.message(),
                answer,
                LocalDateTime.now());
    }
}
