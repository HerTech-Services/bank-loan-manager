package tect.her.ccm.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import tect.her.ccm.web.rest.TestUtil;

public class FilelistEgagementTypeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FilelistEgagementType.class);
        FilelistEgagementType filelistEgagementType1 = new FilelistEgagementType();
        filelistEgagementType1.setId(1L);
        FilelistEgagementType filelistEgagementType2 = new FilelistEgagementType();
        filelistEgagementType2.setId(filelistEgagementType1.getId());
        assertThat(filelistEgagementType1).isEqualTo(filelistEgagementType2);
        filelistEgagementType2.setId(2L);
        assertThat(filelistEgagementType1).isNotEqualTo(filelistEgagementType2);
        filelistEgagementType1.setId(null);
        assertThat(filelistEgagementType1).isNotEqualTo(filelistEgagementType2);
    }
}
