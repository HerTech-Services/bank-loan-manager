package tect.her.ccm.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import tect.her.ccm.web.rest.TestUtil;

public class ScoringTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Scoring.class);
        Scoring scoring1 = new Scoring();
        scoring1.setId(1L);
        Scoring scoring2 = new Scoring();
        scoring2.setId(scoring1.getId());
        assertThat(scoring1).isEqualTo(scoring2);
        scoring2.setId(2L);
        assertThat(scoring1).isNotEqualTo(scoring2);
        scoring1.setId(null);
        assertThat(scoring1).isNotEqualTo(scoring2);
    }
}
