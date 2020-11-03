package tect.her.ccm.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import tect.her.ccm.web.rest.TestUtil;

public class DowngradingTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Downgrading.class);
        Downgrading downgrading1 = new Downgrading();
        downgrading1.setId(1L);
        Downgrading downgrading2 = new Downgrading();
        downgrading2.setId(downgrading1.getId());
        assertThat(downgrading1).isEqualTo(downgrading2);
        downgrading2.setId(2L);
        assertThat(downgrading1).isNotEqualTo(downgrading2);
        downgrading1.setId(null);
        assertThat(downgrading1).isNotEqualTo(downgrading2);
    }
}
