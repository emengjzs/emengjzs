package cn.edu.nju.software.jzs.service.analyse;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by emengjzs on 2016/4/30.
 */
@Service
public class AnalyseCodeGenerator {

    public static final String scriptName = "timeAnalyse.vm";

    @Autowired
    VelocityEngine velocityEngine;

    public File generateCode(AnalyseContext analyseContext) throws IOException {

        File f = new File(analyseContext.getBasePath(), analyseContext.getName() + ".java");
        FileWriter fw = new FileWriter(f);
        VelocityEngineUtils.mergeTemplate(velocityEngine,
                scriptName, "utf-8", (JSONObject) JSON.toJSON(analyseContext),
                fw);
        fw.flush();
        fw.close();
        return f;
    }



}
