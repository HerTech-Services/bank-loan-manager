package tect.her.ccm.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import tect.her.ccm.web.rest.TestUtil;

public class FilelistTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Filelist.class);
        Filelist filelist1 = new Filelist();
        filelist1.setId(1L);
        Filelist filelist2 = new Filelist();
        filelist2.setId(filelist1.getId());
        assertThat(filelist1).isEqualTo(filelist2);
        filelist2.setId(2L);
        assertThat(filelist1).isNotEqualTo(filelist2);
        filelist1.setId(null);
        assertThat(filelist1).isNotEqualTo(filelist2);
    }
}
