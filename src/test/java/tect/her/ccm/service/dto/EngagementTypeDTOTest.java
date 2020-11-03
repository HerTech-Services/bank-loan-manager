package tect.her.ccm.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import tect.her.ccm.web.rest.TestUtil;

public class EngagementTypeDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EngagementTypeDTO.class);
        EngagementTypeDTO engagementTypeDTO1 = new EngagementTypeDTO();
        engagementTypeDTO1.setId(1L);
        EngagementTypeDTO engagementTypeDTO2 = new EngagementTypeDTO();
        assertThat(engagementTypeDTO1).isNotEqualTo(engagementTypeDTO2);
        engagementTypeDTO2.setId(engagementTypeDTO1.getId());
        assertThat(engagementTypeDTO1).isEqualTo(engagementTypeDTO2);
        engagementTypeDTO2.setId(2L);
        assertThat(engagementTypeDTO1).isNotEqualTo(engagementTypeDTO2);
        engagementTypeDTO1.setId(null);
        assertThat(engagementTypeDTO1).isNotEqualTo(engagementTypeDTO2);
    }
}
