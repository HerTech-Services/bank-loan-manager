package tect.her.ccm.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import tect.her.ccm.web.rest.TestUtil;

public class EmployeEntiteTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmployeEntite.class);
        EmployeEntite employeEntite1 = new EmployeEntite();
        employeEntite1.setId(1L);
        EmployeEntite employeEntite2 = new EmployeEntite();
        employeEntite2.setId(employeEntite1.getId());
        assertThat(employeEntite1).isEqualTo(employeEntite2);
        employeEntite2.setId(2L);
        assertThat(employeEntite1).isNotEqualTo(employeEntite2);
        employeEntite1.setId(null);
        assertThat(employeEntite1).isNotEqualTo(employeEntite2);
    }
}
