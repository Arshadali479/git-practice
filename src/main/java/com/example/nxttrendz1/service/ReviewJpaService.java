/*
 *
 * You can use the following import statements
 * 
 * import org.springframework.beans.factory.annotation.Autowired;
 * import org.springframework.http.HttpStatus;
 * import org.springframework.stereotype.Service;
 * import org.springframework.web.server.ResponseStatusException;
 * import java.util.ArrayList;
 * import java.util.List;
 * 
 */

// Write your code here
package com.example.nxttrendz1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.*;

import com.example.nxttrendz1.model.Product;
import com.example.nxttrendz1.model.Review;
import com.example.nxttrendz1.repository.ReviewRepository;
import com.example.nxttrendz1.repository.ReviewJpaRepository;
import com.example.nxttrendz1.repository.ProductJpaRepository;

@Service
public class ReviewJpaService implements ReviewRepository {

    @Autowired
    private ReviewJpaRepository reviewJpaRepository;

    @Autowired
    private ProductJpaRepository productJpaRepository;

    @Override
    public ArrayList<Review> getReviews() {
        List<Review> reviewList = reviewJpaRepository.findAll();
        ArrayList<Review> reviews = new ArrayList<>(reviewList);

        for (Review review : reviews) {
            Product product = review.getProduct();
        }
        return reviews;
    }

    @Override
    public Review addReview(Review review) {
        int productId = review.getProduct().getProductId();
        try {            
            product = productJpaRepository.findById(productId).get();
            review.setProduct(product);

            reviewJpaRepository.save(review);
            return review;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Review getById(int reviewId) {
        try {
            Review review = reviewJpaRepository.findById(reviewId).get();
            Product product = review.getProduct();
            int productId = product.getProductId();
            Product newProduct = productJpaRepository.findById(productId).get();

            review.setProduct(newProduct);

            return review;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Review updateReview(int reviewId, Review review) {
        try {
            Review newReview = reviewJpaRepository.findById(reviewId).get();
            if (review.getReviewContent() != null) {
                newReview.setReviewContent(review.getReviewContent());
            }
            if (review.getRating() != 0) {
                newReview.setRating(review.getRating());
            }
            if (review.getProduct() != null) {
                Product product = review.getProduct();
                int productId = product.getProductId();
                Product newProduct = productJpaRepository.findById(productId).get();

                newReview.setProduct(newProduct);
            }

            reviewJpaRepository.save(newReview);
            return newReview;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public void deleteReview(int reviewId) {
        try {
            reviewJpaRepository.deleteById(reviewId);
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Product getReviewProduct(int reviewId) {
        try {
            Review review = reviewJpaRepository.findById(reviewId).get();
            return review.getProduct();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

}