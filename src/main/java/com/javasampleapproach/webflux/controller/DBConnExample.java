package com.javasampleapproach.webflux.controller;

import com.javasampleapproach.webflux.model.PRef;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Component
public class DBConnExample
{

    @Autowired
    private DataSource dataSource;

    private static final Logger log = LoggerFactory.getLogger(DBConnExample.class);

    public List<PRef> getCount() throws Exception
    {
        List<PRef> pRefs = new ArrayList<>();
        String sql = "SELECT  PREF_CODE,PREF_VALUE FROM LYNKUSER_SITE_PREFERENCES WHERE LYNK_USER = 'DEMO'";
        Connection conn = conn = dataSource.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);

        ResultSet rs = ps.executeQuery();
        while (rs.next())
        {
            PRef pRef = new PRef();
            pRef.setPref_code(rs.getString("PREF_CODE"));
            pRef.setPref_code(rs.getString("PREF_VALUE"));
            pRefs.add(pRef);
        }
        Thread t = Thread.currentThread();
        log.info(t.getId() + "   id   name: " + t.getName() + "  active:" + Thread.activeCount() + " inside dao");
        Thread.sleep(5000);
        rs.close();
        ps.close();
        return pRefs;
    }
}
