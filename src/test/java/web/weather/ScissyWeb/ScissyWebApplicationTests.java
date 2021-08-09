package web.weather.ScissyWeb;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import web.weather.ScissyWeb.config.ScissyConfig;

@SpringBootTest
class ScissyWebApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	public void getConfigKey(){
		Assert.assertEquals(ScissyConfig.getOWMKey(), System.getenv("SCISSY_OWM_KEY"));
	}

}
