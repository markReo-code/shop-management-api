package com.example.shopmanagement.staff;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("local")
public class StaffUserSeeder implements CommandLineRunner{
    private final StaffUserRepository staffUserRepository;

    public StaffUserSeeder(StaffUserRepository staffUserRepository) {
        this.staffUserRepository = staffUserRepository;
    }

    @Override
    public void run(String... args) {
        if (staffUserRepository.count() > 0) {
            System.out.println(
                "スタッフデータが存在するため、シード処理をスキップしました。"
            );
            return;
        }

        staffUserRepository.saveAll(List.of(
            new StaffUser(
                "森川直人",
                "naoto.morikawa@staff.example.com",
                "渋谷店",
                StaffRole.ADMIN,
                true,
                LocalDateTime.now()
            ),
            new StaffUser(
                "長谷川葵",
                "aoi.hasegawa@staff.example.com",
                "新宿店",
                StaffRole.MANAGER,
                true,
                LocalDateTime.now()
            ),
            new StaffUser(
                "石田真奈",
                "mana.ishida@staff.example.com",
                "銀座店",
                StaffRole.STAFF,
                true,
                LocalDateTime.now()
            ),
            new StaffUser(
                "岡本蓮",
                "ren.okamoto@staff.example.com",
                "横浜店",
                StaffRole.STAFF,
                false,
                LocalDateTime.now()
            )
        ));

        System.out.println("スタッフシードデータを4件登録しました。");
    }
    
}
