package com.macaelfitnesscenterback.fitnesscenterback.repository;

import com.macaelfitnesscenterback.fitnesscenterback.model.planes;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class PlanesRepositoryTest {

    @Autowired
    private planesRepository planesRepository;

    @Test
    void saveAndFindPlan() {
        planes plan = new planes();
        plan.setNombre("Plan Diario");
        plan.setPrecioDiario(BigDecimal.valueOf(10));

        planes saved = planesRepository.save(plan);

        assertThat(saved.getId()).isNotNull();
        assertThat(planesRepository.findById(saved.getId())).isPresent();
    }
}
