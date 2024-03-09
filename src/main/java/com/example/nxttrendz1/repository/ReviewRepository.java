/*
 *
 * You can use the following import statements
 * 
 * import java.util.ArrayList;
 * 
 */

// Write your code here
package com.example.nxttrendz1.repository;

import java.util.*;
import com.example.nxttrendz1.model.Review;
import com.example.nxttrendz1.model.Product;

public interface ReviewRepository {
    ArrayList<Review> getReviews();

    Review addReview(Review review);

    Review getById(int reviewId);

    Review updateReview(int reviewId, Review review);

    void deleteReview(int reviewId);

    Product getReviewProduct(int reviewId);
}