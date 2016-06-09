package cn.edu.nju.software.jzs.service.base.svn;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tmatesoft.svn.core.SVNDepth;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.wc.SVNClientManager;
import org.tmatesoft.svn.core.wc.SVNRevision;
import org.tmatesoft.svn.core.wc.SVNUpdateClient;

import java.io.File;

/**
 * Created by emengjzs on 2016/4/3.
 */
@Service
public class SVNService {

    final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    SVNClientManager svnClientManager;



    public void export(String url, String desc) throws SVNException {
        SVNURL svnURL = SVNURL.parseURIDecoded(url) ;

        File exportDict = new File(desc);
        logger.info("[SVN] Export {} to {}", url, desc);
        SVNUpdateClient updateClient = svnClientManager.getUpdateClient();
        updateClient.doExport(svnURL, exportDict, SVNRevision.HEAD, SVNRevision.HEAD,
                null, true, SVNDepth.INFINITY);
    }
}
