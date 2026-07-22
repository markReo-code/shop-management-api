package com.example.shopmanagement.staff;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class StaffUserService {

    private final StaffUserRepository staffUserRepository;

    public StaffUserService(StaffUserRepository staffUserRepository) {
        this.staffUserRepository = staffUserRepository;
    }

    public List<StaffUserResponse> getStaffUsers() {

        return staffUserRepository.findAll()
          .stream()
          .map(staffUser -> new StaffUserResponse(
            staffUser.getId(),
            staffUser.getName(),
            staffUser.getEmail(),
            staffUser.getShopName(),
            staffUser.getRole(),
            staffUser.isActive()
          ))
          .toList();
    }
}
