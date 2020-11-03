package tect.her.ccm.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import tect.her.ccm.web.rest.TestUtil;

public class ScoringDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ScoringDTO.class);
        ScoringDTO scoringDTO1 = new ScoringDTO();
        scoringDTO1.setId(1L);
        ScoringDTO scoringDTO2 = new ScoringDTO();
        assertThat(scoringDTO1).isNotEqualTo(scoringDTO2);
        scoringDTO2.setId(scoringDTO1.getId());
        assertThat(scoringDTO1).isEqualTo(scoringDTO2);
        scoringDTO2.setId(2L);
        assertThat(scoringDTO1).isNotEqualTo(scoringDTO2);
        scoringDTO1.setId(null);
        assertThat(scoringDTO1).isNotEqualTo(scoringDTO2);
    }
}
