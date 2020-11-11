package tect.her.ccm.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import tect.her.ccm.web.rest.TestUtil;

public class DowngradingDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DowngradingDTO.class);
        DowngradingDTO downgradingDTO1 = new DowngradingDTO();
        downgradingDTO1.setId(1L);
        DowngradingDTO downgradingDTO2 = new DowngradingDTO();
        assertThat(downgradingDTO1).isNotEqualTo(downgradingDTO2);
        downgradingDTO2.setId(downgradingDTO1.getId());
        assertThat(downgradingDTO1).isEqualTo(downgradingDTO2);
        downgradingDTO2.setId(2L);
        assertThat(downgradingDTO1).isNotEqualTo(downgradingDTO2);
        downgradingDTO1.setId(null);
        assertThat(downgradingDTO1).isNotEqualTo(downgradingDTO2);
    }
}
