package cn.edu.nju.software.jzs;

import cn.edu.nju.software.jzs.service.analyse.*;
import com.alibaba.fastjson.JSON;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

import static org.hamcrest.core.IsEqual.equalTo;

/**
 * Created by emengjzs on 2016/4/30.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = AutomakerApplication.class)
public class AnalyseTest {

    Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    MethodTreeParser methodTreeParser;

    @Autowired
    AnalyseCodeGenerator analyseCodeGenerator;

    @Autowired
    PidFinder pidFinder;

    @Test
    public void test() {
        log.info(JSON.toJSONString(
                methodTreeParser.parse(new File("D:\\Program\\JAVA\\automaker\\src\\main\\resources\\time.json")), true)
        );
    }

    @Test
    public void testGene() throws IOException {
        AnalyseContext analyseContext = new AnalyseContext();
        analyseContext.setBasePackage("cn.edu.nju.software.jzs.sample");
        analyseContext.setModules("service", "controller");
        analyseContext.setName("sample");
        analyseCodeGenerator.generateCode(analyseContext);
    }

    @Test
    public void testPid() throws IOException {
        Assert.assertThat(pidFinder.getPid(8080), equalTo(12508
        ));
    }

    @Autowired
    AnalyseCodeRunner analyseCodeRunner;

    @Test
    public void testRun() throws IOException, InterruptedException {
        AnalyseContext analyseContext = new AnalyseContext();
        analyseContext.setBasePackage("cn.edu.nju.software.jzs.sample");
        analyseContext.setModules("service", "controller");
        analyseContext.setName("sample");
        analyseContext.setPid(9948);
        // analyseCodeRunner.runScript(new File("E:\\sample.java"), analyseContext);
        Thread.sleep(10000);
    }

    @Autowired
    AnalyseFacade analyseFacade;

    @Test
    @Rollback
    public void testRunTest()  {
        try {
            analyseFacade.analyse(1L);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
