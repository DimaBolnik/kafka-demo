package ru.dev.bolnik.kafkademo.service;

import ru.dev.bolnik.kafkademo.service.dto.CreateProductDto;

import java.util.concurrent.ExecutionException;

public interface ProductService {
    String createProduct(CreateProductDto createProductDto) throws ExecutionException, InterruptedException;
}
