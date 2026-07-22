package com.example.shopmanagement.staff;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/staff")
public class StaffUserController {

    private final StaffUserService staffUserService;

    public StaffUserController(
            StaffUserService staffUserService
    ) {
        this.staffUserService = staffUserService;
    }

    @GetMapping
    public List<StaffUserResponse> index() {
        return staffUserService.getStaffUsers();
    }
}
