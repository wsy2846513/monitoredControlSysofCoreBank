package team.sjfw.monitoringSystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;

@Controller
public class Caller {
    private String twspAnalysisPath;
    private String twspSqlPath;
    private String briefingAnalysisPath;
    private String briefingSqlPath;

    @Autowired
    public Caller(Environment environment){
        this.twspAnalysisPath = environment.getProperty("program.twsp.analysisPath");
        this.twspSqlPath = environment.getProperty("program.twsp.sqlPath");
        this.briefingAnalysisPath = environment.getProperty("program.briefing.analysisPath");
        this.briefingSqlPath = environment.getProperty("program.briefing.sqlPath");
    }
}
