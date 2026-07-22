package com.example.shopmanagement.staff;

public record StaffUserResponse(
    Long id,
    String name,
    String email,
    String shopName,
    StaffRole role,
    boolean active
) {

}
