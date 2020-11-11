package tect.her.ccm.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import tect.her.ccm.web.rest.TestUtil;

public class DowngradingStepTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DowngradingStep.class);
        DowngradingStep downgradingStep1 = new DowngradingStep();
        downgradingStep1.setId(1L);
        DowngradingStep downgradingStep2 = new DowngradingStep();
        downgradingStep2.setId(downgradingStep1.getId());
        assertThat(downgradingStep1).isEqualTo(downgradingStep2);
        downgradingStep2.setId(2L);
        assertThat(downgradingStep1).isNotEqualTo(downgradingStep2);
        downgradingStep1.setId(null);
        assertThat(downgradingStep1).isNotEqualTo(downgradingStep2);
    }
}
