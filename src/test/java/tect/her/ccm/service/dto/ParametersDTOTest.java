package tect.her.ccm.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import tect.her.ccm.web.rest.TestUtil;

public class ParametersDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ParametersDTO.class);
        ParametersDTO parametersDTO1 = new ParametersDTO();
        parametersDTO1.setId(1L);
        ParametersDTO parametersDTO2 = new ParametersDTO();
        assertThat(parametersDTO1).isNotEqualTo(parametersDTO2);
        parametersDTO2.setId(parametersDTO1.getId());
        assertThat(parametersDTO1).isEqualTo(parametersDTO2);
        parametersDTO2.setId(2L);
        assertThat(parametersDTO1).isNotEqualTo(parametersDTO2);
        parametersDTO1.setId(null);
        assertThat(parametersDTO1).isNotEqualTo(parametersDTO2);
    }
}
