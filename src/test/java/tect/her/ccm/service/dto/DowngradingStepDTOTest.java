package tect.her.ccm.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import tect.her.ccm.web.rest.TestUtil;

public class DowngradingStepDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DowngradingStepDTO.class);
        DowngradingStepDTO downgradingStepDTO1 = new DowngradingStepDTO();
        downgradingStepDTO1.setId(1L);
        DowngradingStepDTO downgradingStepDTO2 = new DowngradingStepDTO();
        assertThat(downgradingStepDTO1).isNotEqualTo(downgradingStepDTO2);
        downgradingStepDTO2.setId(downgradingStepDTO1.getId());
        assertThat(downgradingStepDTO1).isEqualTo(downgradingStepDTO2);
        downgradingStepDTO2.setId(2L);
        assertThat(downgradingStepDTO1).isNotEqualTo(downgradingStepDTO2);
        downgradingStepDTO1.setId(null);
        assertThat(downgradingStepDTO1).isNotEqualTo(downgradingStepDTO2);
    }
}
