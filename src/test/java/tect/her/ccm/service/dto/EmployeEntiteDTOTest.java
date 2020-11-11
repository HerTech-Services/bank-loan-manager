package tect.her.ccm.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import tect.her.ccm.web.rest.TestUtil;

public class EmployeEntiteDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmployeEntiteDTO.class);
        EmployeEntiteDTO employeEntiteDTO1 = new EmployeEntiteDTO();
        employeEntiteDTO1.setId(1L);
        EmployeEntiteDTO employeEntiteDTO2 = new EmployeEntiteDTO();
        assertThat(employeEntiteDTO1).isNotEqualTo(employeEntiteDTO2);
        employeEntiteDTO2.setId(employeEntiteDTO1.getId());
        assertThat(employeEntiteDTO1).isEqualTo(employeEntiteDTO2);
        employeEntiteDTO2.setId(2L);
        assertThat(employeEntiteDTO1).isNotEqualTo(employeEntiteDTO2);
        employeEntiteDTO1.setId(null);
        assertThat(employeEntiteDTO1).isNotEqualTo(employeEntiteDTO2);
    }
}
