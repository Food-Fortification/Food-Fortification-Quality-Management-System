package org.path.iam;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class IamServicesApplicationTests {
    @Test
    public void test() {
        IamServicesApplication.main(new String[] {});
        Assertions.assertNotNull(IamServicesApplication.class);
    }

}
