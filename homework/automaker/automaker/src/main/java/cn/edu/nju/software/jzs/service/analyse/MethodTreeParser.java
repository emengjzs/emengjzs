package cn.edu.nju.software.jzs.service.analyse;

import com.alibaba.fastjson.JSON;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by emengjzs on 2016/4/30.
 */
@Service
public class  MethodTreeParser {
    Logger log = LoggerFactory.getLogger(getClass());

    public MethodTree parse(File in) {
        try {
            return parse(new FileInputStream(in));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public MethodTree parse(InputStream in) {
        String text;
        try {
            text = IOUtils.toString(in).trim();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        String[] lines = text.split("\n");

        List<MethodInfo> list = new ArrayList<>(lines.length);
        for (String line : lines) {
            list.add(JSON.parseObject(line, MethodInfo.class));
        }
        return parse(list);
    }

    public MethodTree parse(List<MethodInfo> list) {
        log.debug(JSON.toJSONString(list, true));
        MethodTree root = new MethodTree();

        MethodTree current = root;

        for (MethodInfo info : list) {
            // log.info(JSON.toJSONString(info));
            if (! info.isEnd()) {
                MethodTree m = new MethodTree(info);
                current.addChildren(m);
                current = m;

            }
            else {
                assert info.isSameMethod(current);
                current.setDuration(info.getDuration());
                current = current.parent();
            }

        }
        log.debug("Tree \n" + JSON.toJSONString(root, true));
        return root;
    }



}