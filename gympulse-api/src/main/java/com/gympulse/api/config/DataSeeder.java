package com.gympulse.api.config;

import com.gympulse.api.models.CheckIn;
import com.gympulse.api.models.Member;
import com.gympulse.api.models.Membership;
import com.gympulse.api.models.MembershipPlan;
import com.gympulse.api.repositories.CheckInRepository;
import com.gympulse.api.repositories.MemberRepository;
import com.gympulse.api.repositories.MembershipPlanRepository;
import com.gympulse.api.repositories.MembershipRepository;
import lombok.RequiredArgsConstructor;
import net.datafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {
    private final MemberRepository memberRepository;
    private final MembershipPlanRepository planRepository;
    private final MembershipRepository membershipRepository;
    private final CheckInRepository checkInRepository;

    private final Faker faker = new Faker();

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if (memberRepository.count() == 0) {
            System.out.println("STARTING DATA SEEDING (GYMPULSE)...");
            seedData();
            System.out.println("DATA SEEDING COMPLETED SUCCESSFULLY.");
        }
    }

    private void seedData() {
        // Create plans
        MembershipPlan monthlyPlan = createPlan("Monthly Plan", 30, BigDecimal.valueOf(100.00));
        MembershipPlan quarterlyPlan = createPlan("Quarterly Plan", 90, BigDecimal.valueOf(250.00));
        MembershipPlan annualPlan = createPlan("Annual Plan", 365, BigDecimal.valueOf(800.00));

        List<MembershipPlan> plans = List.of(monthlyPlan, quarterlyPlan, annualPlan);
        planRepository.saveAll(plans);

        // Create 50 members
        List<Member> members = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            Member member = new Member();
            member.setDni(faker.number().digits(8)); // 8 digit DNI
            member.setFirstName(faker.name().firstName());
            member.setLastName(faker.name().lastName());
            member.setEmail(faker.internet().emailAddress());
            member.setPhone(faker.phoneNumber().cellPhone());
            members.add(member);
        }
        memberRepository.saveAll(members);

        // Create memberships
        // Scenery:
        // - 30 members with Active Plan
        // - 10 members with Expired Plan
        // - 10 members without any plan (Prospects)

        Random random = new Random();

        for (int i = 0; i < 40; i++) {
            Member member = members.get(i);
            MembershipPlan plan = plans.get(random.nextInt(plans.size()));

            Membership membership = new Membership();
            membership.setMember(member);
            membership.setPlan(plan);
            membership.setIsPaid(true);

            if (i < 30) {
                // Active memberships: start date
                membership.setStartDate(LocalDate.now().minusDays(random.nextInt(10)));
                membership.setEndDate(membership.getStartDate().plusDays(plan.getDurationDays()));
                member.setStatus("ACTIVE");
                // Generate check-ins for active members
                generateCheckIns(member);
            } else {
                // Expired memberships
                membership.setStartDate(LocalDate.now().minusDays(plan.getDurationDays() + 5));
                // End date in the past
                membership.setEndDate(LocalDate.now().minusDays(1));
            }
            membershipRepository.save(membership);
            memberRepository.save(member);
        }
    }

    private MembershipPlan createPlan(String name, int days, BigDecimal price) {
        MembershipPlan plan = new MembershipPlan();
        plan.setName(name);
        plan.setDurationDays(days);
        plan.setPrice(price);
        return plan;
    }

    private void generateCheckIns(Member member) {
        // Generate 5 assists in the last days
        for (int i = 0; i < 5; i++) {
            CheckIn checkIn = new CheckIn();
            checkIn.setMember(member);
            checkIn.setIsValid(true);
            checkInRepository.save(checkIn);
        }
    }
}
