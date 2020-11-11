package tect.her.ccm.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import tect.her.ccm.web.rest.TestUtil;

public class EngagementDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EngagementDTO.class);
        EngagementDTO engagementDTO1 = new EngagementDTO();
        engagementDTO1.setId(1L);
        EngagementDTO engagementDTO2 = new EngagementDTO();
        assertThat(engagementDTO1).isNotEqualTo(engagementDTO2);
        engagementDTO2.setId(engagementDTO1.getId());
        assertThat(engagementDTO1).isEqualTo(engagementDTO2);
        engagementDTO2.setId(2L);
        assertThat(engagementDTO1).isNotEqualTo(engagementDTO2);
        engagementDTO1.setId(null);
        assertThat(engagementDTO1).isNotEqualTo(engagementDTO2);
    }
}
